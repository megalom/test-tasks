package ru.megalomaniac.securities.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "trading_history")
public class TradingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "secid")
    private String secId;

    @Column(name = "tradedate")
    private Date tradeDate;

    @Column(name = "numtrades")
    private double numtrades;

    @Column(name = "open")
    private double open;

    @Column(name = "close")
    private double close;

    @ManyToOne(fetch = FetchType.LAZY)
    SecuritiesInfo securitiesInfo;

    public TradingHistory() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSecId() {
        return secId;
    }

    public void setSecId(String secId) {
        this.secId = secId;
    }

    public Date getTradeDate() {
        return tradeDate;
    }

    public void setTradeDate(Date tradeDate) {
        this.tradeDate = tradeDate;
    }

    public double getNumtrades() {
        return numtrades;
    }

    public void setNumtrades(double numtrades) {
        this.numtrades = numtrades;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    @Override
    public String toString() {
        return "TradingHistory{" +
                "id=" + id +
                ", secId='" + secId + '\'' +
                ", tradeDate='" + tradeDate + '\'' +
                ", numtrades=" + numtrades +
                ", open=" + open +
                ", close=" + close +
                '}';
    }
}
