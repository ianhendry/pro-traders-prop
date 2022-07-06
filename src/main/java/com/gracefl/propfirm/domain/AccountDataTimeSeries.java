package com.gracefl.propfirm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A AccountDataTimeSeries.
 */
@Entity
@Table(name = "account_data_time_series")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AccountDataTimeSeries implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date_stamp")
    private Instant dateStamp;

    @Column(name = "account_balance")
    private Double accountBalance;

    @Column(name = "account_equity")
    private Double accountEquity;

    @Column(name = "account_credit")
    private Double accountCredit;

    @Column(name = "account_free_margin")
    private Double accountFreeMargin;

    @Column(name = "account_stopout_level")
    private Integer accountStopoutLevel;

    @Column(name = "open_lots")
    private Double openLots;

    @Column(name = "open_number_of_trades")
    private Integer openNumberOfTrades;

    @ManyToOne
    @JsonIgnoreProperties(value = { "mt4Trades", "accountDataTimeSeries" }, allowSetters = true)
    private Mt4Account mt4Account;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public AccountDataTimeSeries id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateStamp() {
        return this.dateStamp;
    }

    public AccountDataTimeSeries dateStamp(Instant dateStamp) {
        this.setDateStamp(dateStamp);
        return this;
    }

    public void setDateStamp(Instant dateStamp) {
        this.dateStamp = dateStamp;
    }

    public Double getAccountBalance() {
        return this.accountBalance;
    }

    public AccountDataTimeSeries accountBalance(Double accountBalance) {
        this.setAccountBalance(accountBalance);
        return this;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Double getAccountEquity() {
        return this.accountEquity;
    }

    public AccountDataTimeSeries accountEquity(Double accountEquity) {
        this.setAccountEquity(accountEquity);
        return this;
    }

    public void setAccountEquity(Double accountEquity) {
        this.accountEquity = accountEquity;
    }

    public Double getAccountCredit() {
        return this.accountCredit;
    }

    public AccountDataTimeSeries accountCredit(Double accountCredit) {
        this.setAccountCredit(accountCredit);
        return this;
    }

    public void setAccountCredit(Double accountCredit) {
        this.accountCredit = accountCredit;
    }

    public Double getAccountFreeMargin() {
        return this.accountFreeMargin;
    }

    public AccountDataTimeSeries accountFreeMargin(Double accountFreeMargin) {
        this.setAccountFreeMargin(accountFreeMargin);
        return this;
    }

    public void setAccountFreeMargin(Double accountFreeMargin) {
        this.accountFreeMargin = accountFreeMargin;
    }

    public Integer getAccountStopoutLevel() {
        return this.accountStopoutLevel;
    }

    public AccountDataTimeSeries accountStopoutLevel(Integer accountStopoutLevel) {
        this.setAccountStopoutLevel(accountStopoutLevel);
        return this;
    }

    public void setAccountStopoutLevel(Integer accountStopoutLevel) {
        this.accountStopoutLevel = accountStopoutLevel;
    }

    public Double getOpenLots() {
        return this.openLots;
    }

    public AccountDataTimeSeries openLots(Double openLots) {
        this.setOpenLots(openLots);
        return this;
    }

    public void setOpenLots(Double openLots) {
        this.openLots = openLots;
    }

    public Integer getOpenNumberOfTrades() {
        return this.openNumberOfTrades;
    }

    public AccountDataTimeSeries openNumberOfTrades(Integer openNumberOfTrades) {
        this.setOpenNumberOfTrades(openNumberOfTrades);
        return this;
    }

    public void setOpenNumberOfTrades(Integer openNumberOfTrades) {
        this.openNumberOfTrades = openNumberOfTrades;
    }

    public Mt4Account getMt4Account() {
        return this.mt4Account;
    }

    public void setMt4Account(Mt4Account mt4Account) {
        this.mt4Account = mt4Account;
    }

    public AccountDataTimeSeries mt4Account(Mt4Account mt4Account) {
        this.setMt4Account(mt4Account);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AccountDataTimeSeries)) {
            return false;
        }
        return id != null && id.equals(((AccountDataTimeSeries) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AccountDataTimeSeries{" +
            "id=" + getId() +
            ", dateStamp='" + getDateStamp() + "'" +
            ", accountBalance=" + getAccountBalance() +
            ", accountEquity=" + getAccountEquity() +
            ", accountCredit=" + getAccountCredit() +
            ", accountFreeMargin=" + getAccountFreeMargin() +
            ", accountStopoutLevel=" + getAccountStopoutLevel() +
            ", openLots=" + getOpenLots() +
            ", openNumberOfTrades=" + getOpenNumberOfTrades() +
            "}";
    }
}
