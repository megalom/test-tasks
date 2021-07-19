package ru.megalomaniac.securities.xml;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ru.megalomaniac.securities.model.SecuritiesInfo;
import ru.megalomaniac.securities.model.TradingHistory;
import ru.megalomaniac.securities.service.SecuritiesInfoService;
import ru.megalomaniac.securities.service.TradingHistoryService;
import ru.megalomaniac.securities.xml.SAXHandler;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SecuritiesXmlImport extends DefaultHandler{
    @Autowired
    SecuritiesInfoService securitiesInfoService;

    @Autowired
    TradingHistoryService tradingHistoryService;

    private List<SecuritiesInfo> securitiesInfos= new ArrayList<>();
    private List<TradingHistory> tradingHistory=new ArrayList<>();
    private List<String> errors=new ArrayList<>();

    public boolean importFromFile(InputStream is){
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        try{
            SAXParser parser = parserFactory.newSAXParser();
            SAXHandler handler = new SAXHandler();
            parser.parse(is,handler);
            tradingHistory = handler.getTradingHistory();
            securitiesInfos = handler.getSecurities();
            errors = handler.getErrors();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void saveToDb(){
        if(securitiesInfos.size()>0)
            securitiesInfos.stream().forEach((sInfo)->{
                if(!securitiesInfoService.existsBySecid(sInfo.getSecid()))
                    securitiesInfoService.save(sInfo);
                else{
                    errors.add("Ценная бумага с индексом "+sInfo.getSecid()+" уже существует");
                }
            });

        if(tradingHistory.size()>0){
            tradingHistory.stream().forEach((tHistory)->{
                SecuritiesInfo sec =securitiesInfoService.findBySecid(tHistory.getSecid());
                // Если ценная бумага существует то сохраняем историю операции
                if(sec!=null) {
                    tradingHistoryService.save(tHistory);
                }
                // Иначе попробуем отискать на московской бирже
                else {
                    SecuritiesInfo secFromMoex = loadFromMoex(tHistory.getSecid());
                    if(secFromMoex!=null) {
                        securitiesInfoService.save(secFromMoex);
                        tradingHistoryService.save(tHistory);
                    }
                    else{
                        errors.add("Ценная бумага с кодом "+tHistory.getSecid()+"не найдена.");
                    }
                }

            });
        }

        securitiesInfos.clear();
        tradingHistory.clear();
    }

    public List<String> getErrors(){
        return errors;
    }

    public int getRecordsCount(){
        return securitiesInfos.size()+tradingHistory.size();
    }

    // Загрузка ценной бумаги с московской биржи
    private SecuritiesInfo loadFromMoex(String secid){

        List<SecuritiesInfo> securities=new ArrayList<>();

        // REST запрос к бирже для получения информации о ценных бумагах
        RestTemplate restTemplate = new RestTemplate();
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        String resourceUrl
                = "http://iss.moex.com/iss/securities.xml?q="+secid;
        ResponseEntity<String> response = null;

        try {
            response = restTemplate.getForEntity(resourceUrl, String.class);
        }
        catch(RestClientException e){
            System.out.println("moex resource is unreachable.");
        }

        if((response!=null)&&(response.getStatusCode().equals(HttpStatus.OK)))
        {
            try(InputStream is = new ByteArrayInputStream(response.getBody().getBytes(StandardCharsets.UTF_8))) {
                SAXParser parser = parserFactory.newSAXParser();
                SAXHandler handler = new SAXHandler();
                parser.parse(is,handler);
                securities=handler.getSecurities().stream()
                        .filter(sec->sec.getSecid().equalsIgnoreCase(secid))
                        .collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SAXException e) {
                e.printStackTrace();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        }
        if(securities.size()==0)
            return null;
        return securities.get(0);
    }

}


