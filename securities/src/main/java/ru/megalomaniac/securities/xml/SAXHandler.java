package ru.megalomaniac.securities.xml;

import org.w3c.dom.Document;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ru.megalomaniac.securities.model.SecuritiesInfo;
import ru.megalomaniac.securities.model.TradingHistory;

import javax.xml.crypto.Data;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class SAXHandler extends DefaultHandler {
    private List<SecuritiesInfo> securities = new ArrayList<>();
    private List<TradingHistory> tradingHistories = new ArrayList<>();
    private SecuritiesInfo securitiesInfo = null;
    private TradingHistory tradingHistory = null;
    private List<String> errors = new ArrayList<>();
    private Locator locator;

    // Список типов данных в xml файле
    private enum DataType {
        UNKNOWN, SECURITIES, HISTORY;
    }

    ;

    private DataType dataType = DataType.UNKNOWN;

    @Override
    public void setDocumentLocator(Locator locator) {
        this.locator = locator;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // перебор тегов
        switch (qName) {
            // определение типа загружаеммых данных из тега <data id="">
            case "data":
                System.out.println("Find data tag, id =" + attributes.getValue("id"));
                // выбор типа данных из атрибута id
                switch (attributes.getValue("id")) {
                    case "securities":
                        dataType = DataType.SECURITIES;
                        break;
                    case "history":
                        dataType = DataType.HISTORY;
                        break;
                    default:
                        break;
                }
                break;

            // загрузка данных об объекте из атрибутов тега <row ...>
            case "row":
                switch (dataType) {
                    case SECURITIES:
                        securitiesInfo = getSecurities(attributes);
                        break;
                    case HISTORY:
                        tradingHistory = getTradingHistory(attributes);
                        break;
                    default:
                        break;
                }
                break;

            default:
                break;
        }
    }

    private SecuritiesInfo getSecurities(Attributes attributes){
        SecuritiesInfo result = new SecuritiesInfo();
        securitiesInfo.setId(getLong(
                attributes.getValue("id"),"id",locator.getLineNumber()
        ));
        securitiesInfo.setSecId(attributes.getValue("secid"));
        securitiesInfo.setName(attributes.getValue("name"));
        securitiesInfo.setRegNumber(attributes.getValue("regnumber"));
        securitiesInfo.setEmitentTitle(attributes.getValue("emitent_title"));
        return result;
    }

    private TradingHistory getTradingHistory(Attributes attributes) {
        TradingHistory result = new TradingHistory();
        result.setId(0);
        result.setSecId(attributes.getValue("SECID"));

        result.setNumtrades(getDouble(
                attributes.getValue("NUMTRADES"), "NUMTRADES", locator.getLineNumber()
        ));

        result.setTradeDate(getDate(
                attributes.getValue("TRADEDATE"),"TRADEDATE", locator.getLineNumber()
        ));

        result.setOpen(getDouble(
                attributes.getValue("OPEN"), "OPEN", locator.getLineNumber()
        ));

        result.setClose(getDouble(
                attributes.getValue("CLOSE"), "CLOSE", locator.getLineNumber()
        ));

        return result;
    }


    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            // если дошли до закрывающего тега добавляем объект в список
            case "row":
                switch (dataType) {
                    case SECURITIES:
                        securities.add(securitiesInfo);
                        securitiesInfo = null;
                        break;
                    case HISTORY:
                        tradingHistories.add(tradingHistory);
                        tradingHistory = null;
                        break;
                }
                break;
            case "data":
                dataType = DataType.UNKNOWN;
            default:
                break;
        }
    }

    @Override
    public void endDocument() throws SAXException {
        /*System.out.println("Loaded securities: " + securities.size());
        System.out.println("Loaded histories: " + tradingHistories.size());
        System.out.println("Errors:" + errors);*/
    }

    public List<SecuritiesInfo> getSecurities() {
        return securities;
    }

    public List<TradingHistory> getTradingHistory() {
        return tradingHistories;
    }

    public List<String> getErrors(){
        return errors;
    }

    // Приведение типов с проверкой строки и запись ошибок в errors
    private Long getLong(String str, String attributeName, int lineNumber) {
        Long result = 0L;
        if (str.matches("\\d+"))
            result = Long.parseLong(str);
        else
            errors.add(
                    new StringBuilder().append("unexpected value: ").append(str).append(" for attribute ")
                    .append(attributeName).append(" at line ").append(lineNumber)
                    .append(" setting to 0\n").toString()
            );
        return result;
    }

    private Date getDate(String str, String attributeName, int lineNumber) {
        Date result = Date.valueOf("1970-01-01");
        if (str.matches("\\d{4}-\\d{2}-\\d{2}"))
            result = Date.valueOf(str);
        else {
            if(str.isEmpty())
                str="\"\"";
            errors.add(
                    new StringBuilder().append("unexpected value: ").append(str).append(" for attribute ")
                    .append(attributeName).append(" at line ").append(lineNumber)
                    .append(" setting to 1970-01-01\n").toString()
            );
        }
        return result;
    }

    private Double getDouble(String str, String attributeName, int lineNumber) {
        Double result = 0.0;
        if (str.matches("\\d+.\\d+|\\d+"))
            result = Double.parseDouble(str);
        else {
            if(str.isEmpty())
                str="\"\"";
            errors.add(
                    new StringBuilder().append("unexpected value: ").append(str).append(" for attribute ")
                    .append(attributeName).append(" at line ").append(lineNumber)
                    .append(" setting to 0.0\n").toString()
            );
        }
        return result;
    }
}