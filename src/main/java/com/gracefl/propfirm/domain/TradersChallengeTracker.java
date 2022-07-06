package com.gracefl.propfirm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A TradersChallengeTracker.
 */
@Entity
@Table(name = "traders_challenge_tracker")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TradersChallengeTracker implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "trade_challenge_name")
    private String tradeChallengeName;

    @Column(name = "start_date")
    private Instant startDate;

    @Column(name = "account_day_start_balance")
    private Double accountDayStartBalance;

    @Column(name = "account_day_start_equity")
    private Double accountDayStartEquity;

    @Column(name = "running_max_total_drawdown")
    private Double runningMaxTotalDrawdown;

    @Column(name = "running_max_daily_drawdown")
    private Double runningMaxDailyDrawdown;

    @Column(name = "lowest_drawdown_point")
    private Double lowestDrawdownPoint;

    @Column(name = "rules_violated")
    private Boolean rulesViolated;

    @Column(name = "rule_violated")
    private String ruleViolated;

    @Column(name = "rule_violated_date")
    private Instant ruleViolatedDate;

    @JsonIgnoreProperties(value = { "mt4Trades", "accountDataTimeSeries" }, allowSetters = true)
    @OneToOne
    @JoinColumn(unique = true)
    private Mt4Account mt4Account;

    @ManyToOne
    @JsonIgnoreProperties(value = { "siteAccount" }, allowSetters = true)
    private ChallengeType challengeType;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "tradersChallengeTracker")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tradersChallengeTracker" }, allowSetters = true)
    private Set<UserNotification> userNotifications = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public TradersChallengeTracker id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTradeChallengeName() {
        return this.tradeChallengeName;
    }

    public TradersChallengeTracker tradeChallengeName(String tradeChallengeName) {
        this.setTradeChallengeName(tradeChallengeName);
        return this;
    }

    public void setTradeChallengeName(String tradeChallengeName) {
        this.tradeChallengeName = tradeChallengeName;
    }

    public Instant getStartDate() {
        return this.startDate;
    }

    public TradersChallengeTracker startDate(Instant startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Double getAccountDayStartBalance() {
        return this.accountDayStartBalance;
    }

    public TradersChallengeTracker accountDayStartBalance(Double accountDayStartBalance) {
        this.setAccountDayStartBalance(accountDayStartBalance);
        return this;
    }

    public void setAccountDayStartBalance(Double accountDayStartBalance) {
        this.accountDayStartBalance = accountDayStartBalance;
    }

    public Double getAccountDayStartEquity() {
        return this.accountDayStartEquity;
    }

    public TradersChallengeTracker accountDayStartEquity(Double accountDayStartEquity) {
        this.setAccountDayStartEquity(accountDayStartEquity);
        return this;
    }

    public void setAccountDayStartEquity(Double accountDayStartEquity) {
        this.accountDayStartEquity = accountDayStartEquity;
    }

    public Double getRunningMaxTotalDrawdown() {
        return this.runningMaxTotalDrawdown;
    }

    public TradersChallengeTracker runningMaxTotalDrawdown(Double runningMaxTotalDrawdown) {
        this.setRunningMaxTotalDrawdown(runningMaxTotalDrawdown);
        return this;
    }

    public void setRunningMaxTotalDrawdown(Double runningMaxTotalDrawdown) {
        this.runningMaxTotalDrawdown = runningMaxTotalDrawdown;
    }

    public Double getRunningMaxDailyDrawdown() {
        return this.runningMaxDailyDrawdown;
    }

    public TradersChallengeTracker runningMaxDailyDrawdown(Double runningMaxDailyDrawdown) {
        this.setRunningMaxDailyDrawdown(runningMaxDailyDrawdown);
        return this;
    }

    public void setRunningMaxDailyDrawdown(Double runningMaxDailyDrawdown) {
        this.runningMaxDailyDrawdown = runningMaxDailyDrawdown;
    }

    public Double getLowestDrawdownPoint() {
        return this.lowestDrawdownPoint;
    }

    public TradersChallengeTracker lowestDrawdownPoint(Double lowestDrawdownPoint) {
        this.setLowestDrawdownPoint(lowestDrawdownPoint);
        return this;
    }

    public void setLowestDrawdownPoint(Double lowestDrawdownPoint) {
        this.lowestDrawdownPoint = lowestDrawdownPoint;
    }

    public Boolean getRulesViolated() {
        return this.rulesViolated;
    }

    public TradersChallengeTracker rulesViolated(Boolean rulesViolated) {
        this.setRulesViolated(rulesViolated);
        return this;
    }

    public void setRulesViolated(Boolean rulesViolated) {
        this.rulesViolated = rulesViolated;
    }

    public String getRuleViolated() {
        return this.ruleViolated;
    }

    public TradersChallengeTracker ruleViolated(String ruleViolated) {
        this.setRuleViolated(ruleViolated);
        return this;
    }

    public void setRuleViolated(String ruleViolated) {
        this.ruleViolated = ruleViolated;
    }

    public Instant getRuleViolatedDate() {
        return this.ruleViolatedDate;
    }

    public TradersChallengeTracker ruleViolatedDate(Instant ruleViolatedDate) {
        this.setRuleViolatedDate(ruleViolatedDate);
        return this;
    }

    public void setRuleViolatedDate(Instant ruleViolatedDate) {
        this.ruleViolatedDate = ruleViolatedDate;
    }

    public Mt4Account getMt4Account() {
        return this.mt4Account;
    }

    public void setMt4Account(Mt4Account mt4Account) {
        this.mt4Account = mt4Account;
    }

    public TradersChallengeTracker mt4Account(Mt4Account mt4Account) {
        this.setMt4Account(mt4Account);
        return this;
    }

    public ChallengeType getChallengeType() {
        return this.challengeType;
    }

    public void setChallengeType(ChallengeType challengeType) {
        this.challengeType = challengeType;
    }

    public TradersChallengeTracker challengeType(ChallengeType challengeType) {
        this.setChallengeType(challengeType);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TradersChallengeTracker user(User user) {
        this.setUser(user);
        return this;
    }

    public Set<UserNotification> getUserNotifications() {
        return this.userNotifications;
    }

    public void setUserNotifications(Set<UserNotification> userNotifications) {
        if (this.userNotifications != null) {
            this.userNotifications.forEach(i -> i.setTradersChallengeTracker(null));
        }
        if (userNotifications != null) {
            userNotifications.forEach(i -> i.setTradersChallengeTracker(this));
        }
        this.userNotifications = userNotifications;
    }

    public TradersChallengeTracker userNotifications(Set<UserNotification> userNotifications) {
        this.setUserNotifications(userNotifications);
        return this;
    }

    public TradersChallengeTracker addUserNotification(UserNotification userNotification) {
        this.userNotifications.add(userNotification);
        userNotification.setTradersChallengeTracker(this);
        return this;
    }

    public TradersChallengeTracker removeUserNotification(UserNotification userNotification) {
        this.userNotifications.remove(userNotification);
        userNotification.setTradersChallengeTracker(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TradersChallengeTracker)) {
            return false;
        }
        return id != null && id.equals(((TradersChallengeTracker) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TradersChallengeTracker{" +
            "id=" + getId() +
            ", tradeChallengeName='" + getTradeChallengeName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", accountDayStartBalance=" + getAccountDayStartBalance() +
            ", accountDayStartEquity=" + getAccountDayStartEquity() +
            ", runningMaxTotalDrawdown=" + getRunningMaxTotalDrawdown() +
            ", runningMaxDailyDrawdown=" + getRunningMaxDailyDrawdown() +
            ", lowestDrawdownPoint=" + getLowestDrawdownPoint() +
            ", rulesViolated='" + getRulesViolated() + "'" +
            ", ruleViolated='" + getRuleViolated() + "'" +
            ", ruleViolatedDate='" + getRuleViolatedDate() + "'" +
            "}";
    }
}
