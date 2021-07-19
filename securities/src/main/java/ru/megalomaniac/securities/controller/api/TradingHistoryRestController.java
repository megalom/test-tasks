package ru.megalomaniac.securities.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.megalomaniac.securities.exceptions.SecuritiesNotFoundException;
import ru.megalomaniac.securities.model.TradingHistory;
import ru.megalomaniac.securities.model.TradingHistoryXmlString;
import ru.megalomaniac.securities.service.SecuritiesInfoService;
import ru.megalomaniac.securities.service.TradingHistoryService;
import ru.megalomaniac.securities.xml.securitiesdocument.XmlDocument;
import ru.megalomaniac.securities.xml.securitiesdocument.XmlDocumentType;
import ru.megalomaniac.securities.xml.securitiesdocument.XmlImport;

import java.util.List;

@RestController
@RequestMapping("/api/history")
public class TradingHistoryRestController {

    @Autowired
    private TradingHistoryService tradingHistoryService;
    @Autowired
    private SecuritiesInfoService securitiesInfoService;

    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<XmlDocument> findAll() {

        ResponseEntity<XmlDocument> responseEntity;

        try {
            List<TradingHistory> tradingHistories = tradingHistoryService.findAll();
            if (tradingHistories.isEmpty()) {
                responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            else {
                XmlDocument xmlDocument = XmlImport.getDocument(tradingHistories, XmlDocumentType.HISTORY);
                responseEntity = new ResponseEntity<>(xmlDocument, HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<TradingHistoryXmlString> findById(@PathVariable("id") int id) {

        ResponseEntity<TradingHistoryXmlString> responseEntity=null;

        try {
            TradingHistory tradingHistory = tradingHistoryService.findById(id);
            TradingHistoryXmlString tradingHistoryXmlString = new TradingHistoryXmlString(tradingHistory);
            responseEntity = new ResponseEntity<>(tradingHistoryXmlString, HttpStatus.OK);
        }
        catch (SecuritiesNotFoundException e){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> postSecurities(@RequestBody TradingHistoryXmlString tradingHistoryXmlString) {
        try {
            TradingHistory tradingHistory = tradingHistoryXmlString.convertToTradingHistory();
            tradingHistory.setSecuritiesInfo(securitiesInfoService.findBySecid(tradingHistory.getSecid()));
            if(tradingHistory!=null) {
                tradingHistory.setId(0);
                tradingHistoryService.save(tradingHistory);
            }
            else
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> updateSecurities(@RequestBody TradingHistoryXmlString tradingHistoryXmlString) {
        try {
            TradingHistory tradingHistory = tradingHistoryXmlString.convertToTradingHistory();
            if(tradingHistory!=null&&tradingHistoryService.existById(tradingHistory.getId()))
                tradingHistoryService.save(tradingHistory);
            else
                return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSecurities(@PathVariable("id") int id) {
        try {
            tradingHistoryService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
