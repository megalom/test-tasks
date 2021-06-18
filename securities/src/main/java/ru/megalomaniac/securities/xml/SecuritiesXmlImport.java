package ru.megalomaniac.securities.xml;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ru.megalomaniac.securities.model.SecuritiesInfo;
import ru.megalomaniac.securities.model.TradingHistory;
import ru.megalomaniac.securities.repository.SecuritiesInfoRepository;
import ru.megalomaniac.securities.repository.TradingHistoryRepository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class SecuritiesXmlImport extends DefaultHandler{
    @Autowired
    SecuritiesInfoRepository securitiesInfoRepository;

    @Autowired
    TradingHistoryRepository tradingHistoryRepository;

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
            securitiesInfos.stream().forEach((sInfo)->securitiesInfoRepository.save(sInfo));
        if(tradingHistory.size()>0)
            tradingHistory.stream().forEach((tHistory)->tradingHistoryRepository.save(tHistory));
        securitiesInfos.clear();
        tradingHistory.clear();
    }

    public List<String> getErrors(){
        return errors;
    }

    public int getRecordsCount(){
        return securitiesInfos.size()+tradingHistory.size();
    }

}


