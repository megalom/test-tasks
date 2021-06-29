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
                System.out.println(sInfo.getSecid());
                if(!securitiesInfoService.existsBySecid(sInfo.getSecid()))
                    securitiesInfoService.save(sInfo);
                else{
                    errors.add("Ценная бумага с индексом "+sInfo.getSecid()+" уже существует");
                }
            });

        if(tradingHistory.size()>0){
            tradingHistory.stream().forEach((tHistory)->{
                //System.out.println(tHistory);
                /*if(tHistory == null)
                    System.out.println("thistory is null");
                if(tHistory.getSecid() == null)
                    System.out.println("thistory secid is null");*/
                SecuritiesInfo sec =securitiesInfoService.findBySecid(tHistory.getSecid());
                if(sec!=null) {
                    System.out.println("found " + sec.getSecid());
                    tradingHistoryService.save(tHistory);
                }
                else {
                    System.out.println("not found " + tHistory.getSecid()+" loading from moex.");
                    SecuritiesInfo secFromMoex = loadFromMoex(tHistory.getSecid());
                    if(secFromMoex!=null) {
                        System.out.println("found on moex securities - " + secFromMoex.getSecid());
                        securitiesInfoService.save(secFromMoex);
                        tradingHistoryService.save(tHistory);
                    }
                    else
                        System.out.println("securities "+tHistory.getSecid()+" not found on moex.");
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

    private SecuritiesInfo loadFromMoex(String secid){
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


        List<SecuritiesInfo> securities=new ArrayList<>();
        if(response!=null)
        if(response.getStatusCode().equals(HttpStatus.OK)) {
            try(InputStream is = new ByteArrayInputStream(response.getBody().getBytes(StandardCharsets.UTF_8))) {
                SAXParser parser = parserFactory.newSAXParser();
                SAXHandler handler = new SAXHandler();
                parser.parse(is,handler);
                securities=handler.getSecurities().stream()
                        .filter(sec->sec.getSecid().equalsIgnoreCase(secid))
                        .collect(Collectors.toList());
                System.out.println("Securities data on moex available: ");
                if(securities.size()>0)
                    securities.stream().forEach(System.out::println);
                else
                    System.out.println("0");

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


