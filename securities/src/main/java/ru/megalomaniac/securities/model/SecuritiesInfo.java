package ru.megalomaniac.securities.model;

import ru.megalomaniac.securities.validation.UniqueSecIdConstraint;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "securities_info")
public class SecuritiesInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    // Торговый код
    @Column(name = "secid")
    @NotBlank(message = "Поле не должно быть пустым")
    @Pattern(regexp = "[a-zA-Z0-9]{4,18}",message = "От 4 до 18 символов")
    private String secid;

    // Регистрационный номер
    @Column(name = "regnumber")
    @Pattern(regexp = "\\d-\\d{2}-\\d{5}-[A-Z]",
            message = "Должно содержать значение вида 1-11-11111-A")
    private String regnumber;

    // Наименование ценной бумаги
    @Column(name = "name")
    @Pattern(regexp = "[\"-?!,.а-яА-ЯёЁ0-9\\sa-zA-Z()]{3,}",
            message = "Не менее 3х символов.")
    private String name;

    // Полное фирменное наименование/наименование эмитента, управляющей компании,
    // управляющего ипотечным покрытием
    @Column(name = "emitent_title")
    @Pattern(regexp = "[\"-?!,.а-яА-ЯёЁ0-9\\sa-zA-Z()]{3,}",
            message = "Не менее 3х символов.")
    private String emitentTitle;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "securitiesInfo",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<TradingHistory> tradingHistory = new HashSet<>();


    public SecuritiesInfo() {
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

    public String getRegnumber() {
        return regnumber;
    }

    public void setRegnumber(String regnumber) {
        this.regnumber = regnumber;
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

    public Set<TradingHistory> getTradingHistory() {
        return tradingHistory;
    }

    public void setTradingHistory(Set<TradingHistory> tradingHistory) {
        if (this.tradingHistory == null) {
            this.tradingHistory = tradingHistory;
        }
        // чтобы не получить current modification exception
        else if (this.tradingHistory != tradingHistory) {
            this.tradingHistory.clear();
            if (tradingHistory != null) {
                this.tradingHistory.addAll(tradingHistory);
            }
        }
    }

    @Override
    public String toString() {
        return "SecuritiesInfo{" +
                "id=" + id +
                ", secId='" + secid + '\'' +
                ", regNumber='" + regnumber + '\'' +
                ", Name='" + name + '\'' +
                ", emitentTitle='" + emitentTitle + '\'' +
                '}';
    }
}
