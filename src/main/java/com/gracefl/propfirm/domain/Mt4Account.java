package com.gracefl.propfirm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gracefl.propfirm.domain.enumeration.ACCOUNTTYPE;
import com.gracefl.propfirm.domain.enumeration.BROKER;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Mt4Account.
 */
@Entity
@Table(name = "mt_4_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Mt4Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "account_id")
    private String accountID;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private ACCOUNTTYPE accountType;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_broker")
    private BROKER accountBroker;

    @Column(name = "account_login")
    private String accountLogin;

    @Column(name = "account_password")
    private String accountPassword;

    @Column(name = "account_investor_login")
    private String accountInvestorLogin;

    @Column(name = "account_investor_password")
    private String accountInvestorPassword;

    @Column(name = "account_real")
    private Boolean accountReal;

    @Column(name = "account_info_double")
    private Double accountInfoDouble;

    @Column(name = "account_info_integer")
    private Integer accountInfoInteger;

    @Column(name = "account_info_string")
    private String accountInfoString;

    @Column(name = "account_balance")
    private Double accountBalance;

    @Column(name = "account_credit")
    private Double accountCredit;

    @Column(name = "account_company")
    private String accountCompany;

    @Column(name = "account_currency")
    private String accountCurrency;

    @Column(name = "account_equity")
    private Double accountEquity;

    @Column(name = "account_free_margin")
    private Double accountFreeMargin;

    @Column(name = "account_free_margin_check")
    private Double accountFreeMarginCheck;

    @Column(name = "account_free_margin_mode")
    private Double accountFreeMarginMode;

    @Column(name = "account_leverage")
    private Integer accountLeverage;

    @Column(name = "account_margin")
    private Double accountMargin;

    @Column(name = "account_name")
    private String accountName;

    @Column(name = "account_number")
    private Integer accountNumber;

    @Column(name = "account_profit")
    private Double accountProfit;

    @Column(name = "account_server")
    private String accountServer;

    @Column(name = "account_stopout_level")
    private Integer accountStopoutLevel;

    @Column(name = "account_stopout_mode")
    private Integer accountStopoutMode;

    @Column(name = "in_active")
    private Boolean inActive;

    @Column(name = "in_active_date")
    private Instant inActiveDate;

    @OneToMany(mappedBy = "mt4Account")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mt4Account" }, allowSetters = true)
    private Set<Mt4Trade> mt4Trades = new HashSet<>();

    @OneToMany(mappedBy = "mt4Account")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "mt4Account" }, allowSetters = true)
    private Set<AccountDataTimeSeries> accountDataTimeSeries = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Mt4Account id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountID() {
        return this.accountID;
    }

    public Mt4Account accountID(String accountID) {
        this.setAccountID(accountID);
        return this;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public ACCOUNTTYPE getAccountType() {
        return this.accountType;
    }

    public Mt4Account accountType(ACCOUNTTYPE accountType) {
        this.setAccountType(accountType);
        return this;
    }

    public void setAccountType(ACCOUNTTYPE accountType) {
        this.accountType = accountType;
    }

    public BROKER getAccountBroker() {
        return this.accountBroker;
    }

    public Mt4Account accountBroker(BROKER accountBroker) {
        this.setAccountBroker(accountBroker);
        return this;
    }

    public void setAccountBroker(BROKER accountBroker) {
        this.accountBroker = accountBroker;
    }

    public String getAccountLogin() {
        return this.accountLogin;
    }

    public Mt4Account accountLogin(String accountLogin) {
        this.setAccountLogin(accountLogin);
        return this;
    }

    public void setAccountLogin(String accountLogin) {
        this.accountLogin = accountLogin;
    }

    public String getAccountPassword() {
        return this.accountPassword;
    }

    public Mt4Account accountPassword(String accountPassword) {
        this.setAccountPassword(accountPassword);
        return this;
    }

    public void setAccountPassword(String accountPassword) {
        this.accountPassword = accountPassword;
    }

    public String getAccountInvestorLogin() {
        return this.accountInvestorLogin;
    }

    public Mt4Account accountInvestorLogin(String accountInvestorLogin) {
        this.setAccountInvestorLogin(accountInvestorLogin);
        return this;
    }

    public void setAccountInvestorLogin(String accountInvestorLogin) {
        this.accountInvestorLogin = accountInvestorLogin;
    }

    public String getAccountInvestorPassword() {
        return this.accountInvestorPassword;
    }

    public Mt4Account accountInvestorPassword(String accountInvestorPassword) {
        this.setAccountInvestorPassword(accountInvestorPassword);
        return this;
    }

    public void setAccountInvestorPassword(String accountInvestorPassword) {
        this.accountInvestorPassword = accountInvestorPassword;
    }

    public Boolean getAccountReal() {
        return this.accountReal;
    }

    public Mt4Account accountReal(Boolean accountReal) {
        this.setAccountReal(accountReal);
        return this;
    }

    public void setAccountReal(Boolean accountReal) {
        this.accountReal = accountReal;
    }

    public Double getAccountInfoDouble() {
        return this.accountInfoDouble;
    }

    public Mt4Account accountInfoDouble(Double accountInfoDouble) {
        this.setAccountInfoDouble(accountInfoDouble);
        return this;
    }

    public void setAccountInfoDouble(Double accountInfoDouble) {
        this.accountInfoDouble = accountInfoDouble;
    }

    public Integer getAccountInfoInteger() {
        return this.accountInfoInteger;
    }

    public Mt4Account accountInfoInteger(Integer accountInfoInteger) {
        this.setAccountInfoInteger(accountInfoInteger);
        return this;
    }

    public void setAccountInfoInteger(Integer accountInfoInteger) {
        this.accountInfoInteger = accountInfoInteger;
    }

    public String getAccountInfoString() {
        return this.accountInfoString;
    }

    public Mt4Account accountInfoString(String accountInfoString) {
        this.setAccountInfoString(accountInfoString);
        return this;
    }

    public void setAccountInfoString(String accountInfoString) {
        this.accountInfoString = accountInfoString;
    }

    public Double getAccountBalance() {
        return this.accountBalance;
    }

    public Mt4Account accountBalance(Double accountBalance) {
        this.setAccountBalance(accountBalance);
        return this;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Double getAccountCredit() {
        return this.accountCredit;
    }

    public Mt4Account accountCredit(Double accountCredit) {
        this.setAccountCredit(accountCredit);
        return this;
    }

    public void setAccountCredit(Double accountCredit) {
        this.accountCredit = accountCredit;
    }

    public String getAccountCompany() {
        return this.accountCompany;
    }

    public Mt4Account accountCompany(String accountCompany) {
        this.setAccountCompany(accountCompany);
        return this;
    }

    public void setAccountCompany(String accountCompany) {
        this.accountCompany = accountCompany;
    }

    public String getAccountCurrency() {
        return this.accountCurrency;
    }

    public Mt4Account accountCurrency(String accountCurrency) {
        this.setAccountCurrency(accountCurrency);
        return this;
    }

    public void setAccountCurrency(String accountCurrency) {
        this.accountCurrency = accountCurrency;
    }

    public Double getAccountEquity() {
        return this.accountEquity;
    }

    public Mt4Account accountEquity(Double accountEquity) {
        this.setAccountEquity(accountEquity);
        return this;
    }

    public void setAccountEquity(Double accountEquity) {
        this.accountEquity = accountEquity;
    }

    public Double getAccountFreeMargin() {
        return this.accountFreeMargin;
    }

    public Mt4Account accountFreeMargin(Double accountFreeMargin) {
        this.setAccountFreeMargin(accountFreeMargin);
        return this;
    }

    public void setAccountFreeMargin(Double accountFreeMargin) {
        this.accountFreeMargin = accountFreeMargin;
    }

    public Double getAccountFreeMarginCheck() {
        return this.accountFreeMarginCheck;
    }

    public Mt4Account accountFreeMarginCheck(Double accountFreeMarginCheck) {
        this.setAccountFreeMarginCheck(accountFreeMarginCheck);
        return this;
    }

    public void setAccountFreeMarginCheck(Double accountFreeMarginCheck) {
        this.accountFreeMarginCheck = accountFreeMarginCheck;
    }

    public Double getAccountFreeMarginMode() {
        return this.accountFreeMarginMode;
    }

    public Mt4Account accountFreeMarginMode(Double accountFreeMarginMode) {
        this.setAccountFreeMarginMode(accountFreeMarginMode);
        return this;
    }

    public void setAccountFreeMarginMode(Double accountFreeMarginMode) {
        this.accountFreeMarginMode = accountFreeMarginMode;
    }

    public Integer getAccountLeverage() {
        return this.accountLeverage;
    }

    public Mt4Account accountLeverage(Integer accountLeverage) {
        this.setAccountLeverage(accountLeverage);
        return this;
    }

    public void setAccountLeverage(Integer accountLeverage) {
        this.accountLeverage = accountLeverage;
    }

    public Double getAccountMargin() {
        return this.accountMargin;
    }

    public Mt4Account accountMargin(Double accountMargin) {
        this.setAccountMargin(accountMargin);
        return this;
    }

    public void setAccountMargin(Double accountMargin) {
        this.accountMargin = accountMargin;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public Mt4Account accountName(String accountName) {
        this.setAccountName(accountName);
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getAccountNumber() {
        return this.accountNumber;
    }

    public Mt4Account accountNumber(Integer accountNumber) {
        this.setAccountNumber(accountNumber);
        return this;
    }

    public void setAccountNumber(Integer accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Double getAccountProfit() {
        return this.accountProfit;
    }

    public Mt4Account accountProfit(Double accountProfit) {
        this.setAccountProfit(accountProfit);
        return this;
    }

    public void setAccountProfit(Double accountProfit) {
        this.accountProfit = accountProfit;
    }

    public String getAccountServer() {
        return this.accountServer;
    }

    public Mt4Account accountServer(String accountServer) {
        this.setAccountServer(accountServer);
        return this;
    }

    public void setAccountServer(String accountServer) {
        this.accountServer = accountServer;
    }

    public Integer getAccountStopoutLevel() {
        return this.accountStopoutLevel;
    }

    public Mt4Account accountStopoutLevel(Integer accountStopoutLevel) {
        this.setAccountStopoutLevel(accountStopoutLevel);
        return this;
    }

    public void setAccountStopoutLevel(Integer accountStopoutLevel) {
        this.accountStopoutLevel = accountStopoutLevel;
    }

    public Integer getAccountStopoutMode() {
        return this.accountStopoutMode;
    }

    public Mt4Account accountStopoutMode(Integer accountStopoutMode) {
        this.setAccountStopoutMode(accountStopoutMode);
        return this;
    }

    public void setAccountStopoutMode(Integer accountStopoutMode) {
        this.accountStopoutMode = accountStopoutMode;
    }

    public Boolean getInActive() {
        return this.inActive;
    }

    public Mt4Account inActive(Boolean inActive) {
        this.setInActive(inActive);
        return this;
    }

    public void setInActive(Boolean inActive) {
        this.inActive = inActive;
    }

    public Instant getInActiveDate() {
        return this.inActiveDate;
    }

    public Mt4Account inActiveDate(Instant inActiveDate) {
        this.setInActiveDate(inActiveDate);
        return this;
    }

    public void setInActiveDate(Instant inActiveDate) {
        this.inActiveDate = inActiveDate;
    }

    public Set<Mt4Trade> getMt4Trades() {
        return this.mt4Trades;
    }

    public void setMt4Trades(Set<Mt4Trade> mt4Trades) {
        if (this.mt4Trades != null) {
            this.mt4Trades.forEach(i -> i.setMt4Account(null));
        }
        if (mt4Trades != null) {
            mt4Trades.forEach(i -> i.setMt4Account(this));
        }
        this.mt4Trades = mt4Trades;
    }

    public Mt4Account mt4Trades(Set<Mt4Trade> mt4Trades) {
        this.setMt4Trades(mt4Trades);
        return this;
    }

    public Mt4Account addMt4Trade(Mt4Trade mt4Trade) {
        this.mt4Trades.add(mt4Trade);
        mt4Trade.setMt4Account(this);
        return this;
    }

    public Mt4Account removeMt4Trade(Mt4Trade mt4Trade) {
        this.mt4Trades.remove(mt4Trade);
        mt4Trade.setMt4Account(null);
        return this;
    }

    public Set<AccountDataTimeSeries> getAccountDataTimeSeries() {
        return this.accountDataTimeSeries;
    }

    public void setAccountDataTimeSeries(Set<AccountDataTimeSeries> accountDataTimeSeries) {
        if (this.accountDataTimeSeries != null) {
            this.accountDataTimeSeries.forEach(i -> i.setMt4Account(null));
        }
        if (accountDataTimeSeries != null) {
            accountDataTimeSeries.forEach(i -> i.setMt4Account(this));
        }
        this.accountDataTimeSeries = accountDataTimeSeries;
    }

    public Mt4Account accountDataTimeSeries(Set<AccountDataTimeSeries> accountDataTimeSeries) {
        this.setAccountDataTimeSeries(accountDataTimeSeries);
        return this;
    }

    public Mt4Account addAccountDataTimeSeries(AccountDataTimeSeries accountDataTimeSeries) {
        this.accountDataTimeSeries.add(accountDataTimeSeries);
        accountDataTimeSeries.setMt4Account(this);
        return this;
    }

    public Mt4Account removeAccountDataTimeSeries(AccountDataTimeSeries accountDataTimeSeries) {
        this.accountDataTimeSeries.remove(accountDataTimeSeries);
        accountDataTimeSeries.setMt4Account(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mt4Account)) {
            return false;
        }
        return id != null && id.equals(((Mt4Account) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Mt4Account{" +
            "id=" + getId() +
            ", accountID='" + getAccountID() + "'" +
            ", accountType='" + getAccountType() + "'" +
            ", accountBroker='" + getAccountBroker() + "'" +
            ", accountLogin='" + getAccountLogin() + "'" +
            ", accountPassword='" + getAccountPassword() + "'" +
            ", accountInvestorLogin='" + getAccountInvestorLogin() + "'" +
            ", accountInvestorPassword='" + getAccountInvestorPassword() + "'" +
            ", accountReal='" + getAccountReal() + "'" +
            ", accountInfoDouble=" + getAccountInfoDouble() +
            ", accountInfoInteger=" + getAccountInfoInteger() +
            ", accountInfoString='" + getAccountInfoString() + "'" +
            ", accountBalance=" + getAccountBalance() +
            ", accountCredit=" + getAccountCredit() +
            ", accountCompany='" + getAccountCompany() + "'" +
            ", accountCurrency='" + getAccountCurrency() + "'" +
            ", accountEquity=" + getAccountEquity() +
            ", accountFreeMargin=" + getAccountFreeMargin() +
            ", accountFreeMarginCheck=" + getAccountFreeMarginCheck() +
            ", accountFreeMarginMode=" + getAccountFreeMarginMode() +
            ", accountLeverage=" + getAccountLeverage() +
            ", accountMargin=" + getAccountMargin() +
            ", accountName='" + getAccountName() + "'" +
            ", accountNumber=" + getAccountNumber() +
            ", accountProfit=" + getAccountProfit() +
            ", accountServer='" + getAccountServer() + "'" +
            ", accountStopoutLevel=" + getAccountStopoutLevel() +
            ", accountStopoutMode=" + getAccountStopoutMode() +
            ", inActive='" + getInActive() + "'" +
            ", inActiveDate='" + getInActiveDate() + "'" +
            "}";
    }
}
