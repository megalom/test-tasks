package ru.megalomaniac.securities.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name = "trading_history")
public class TradingHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // Торговый код
    @Column(name = "secid")
    @NotBlank(message = "{TradingHistory.secid.notblank}")
    @Pattern(regexp = "[a-zA-Z0-9]{4,18}",message = "{TradingHistory.secid.pattern}")
    private String secid;

    // Дата торгов
    @Column(name = "tradedate")
    @NotNull(message = "{TradingHistory.tradedate.notempty}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date tradedate;

    // Количество сделок за день
    @Column(name = "numtrades")
    @DecimalMin(value = "0",message = "{TradingHistory.numtrades.min}")
    //@Pattern( regexp = "[0-9]+", message = "Поле должно содержать целые числа.")
    private double numtrades;

    // Цена предторгового периода/Цена аукциона открытия
    @Column(name = "open")
    @DecimalMin(value = "0",message = "{TradingHistory.open.min}")
    private double open;

    // Цена закрытия
    @Column(name = "close")
    @DecimalMin(value = "0",message = "{TradingHistory.close.min}")
    private double close;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="secid", referencedColumnName = "secid",nullable = false,insertable = false,updatable = false)
    SecuritiesInfo securitiesInfo;

    public TradingHistory() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSecid() {
        return secid;
    }

    public void setSecid(String secid) {
        this.secid = secid;
    }

    public Date getTradedate() {
        return tradedate;
    }

    public void setTradedate(Date tradedate) {
        this.tradedate = tradedate;
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

    public SecuritiesInfo getSecuritiesInfo() {
        return securitiesInfo;
    }

    public void setSecuritiesInfo(SecuritiesInfo securitiesInfo) {
        this.securitiesInfo = securitiesInfo;
    }

    @Override
    public String toString() {
        return "TradingHistory{" +
                "id=" + id +
                ", secId='" + secid + '\'' +
                ", tradeDate='" + tradedate + '\'' +
                ", numtrades=" + numtrades +
                ", open=" + open +
                ", close=" + close +
                '}';
    }
}
