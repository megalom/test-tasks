package ru.megalomaniac.securities.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "securities_info")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SecuritiesInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "secid")
    private String secId;

    @Column(name = "regnumber")
    private String regNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "emitent_title")
    private String emitentTitle;

    @OneToMany(mappedBy = "secId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TradingHistory> tradingHistory;



    public SecuritiesInfo() {
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

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmitentTitle() {
        return emitentTitle;
    }

    public void setEmitentTitle(String emitentTitle) {
        this.emitentTitle = emitentTitle;
    }

    @Override
    public String toString() {
        return "SecuritiesInfo{" +
                "id=" + id +
                ", secId='" + secId + '\'' +
                ", regNumber='" + regNumber + '\'' +
                ", Name='" + name + '\'' +
                ", emitentTitle='" + emitentTitle + '\'' +
                '}';
    }
}
