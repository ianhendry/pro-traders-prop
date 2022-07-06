package com.gracefl.propfirm.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import org.springdoc.api.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.gracefl.propfirm.domain.TradersChallengeTracker} entity. This class is used
 * in {@link com.gracefl.propfirm.web.rest.TradersChallengeTrackerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /traders-challenge-trackers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class TradersChallengeTrackerCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter tradeChallengeName;

    private InstantFilter startDate;

    private DoubleFilter accountDayStartBalance;

    private DoubleFilter accountDayStartEquity;

    private DoubleFilter runningMaxTotalDrawdown;

    private DoubleFilter runningMaxDailyDrawdown;

    private DoubleFilter lowestDrawdownPoint;

    private BooleanFilter rulesViolated;

    private StringFilter ruleViolated;

    private InstantFilter ruleViolatedDate;

    private LongFilter mt4AccountId;

    private LongFilter challengeTypeId;

    private LongFilter userId;

    private LongFilter userNotificationId;

    private Boolean distinct;

    public TradersChallengeTrackerCriteria() {}

    public TradersChallengeTrackerCriteria(TradersChallengeTrackerCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.tradeChallengeName = other.tradeChallengeName == null ? null : other.tradeChallengeName.copy();
        this.startDate = other.startDate == null ? null : other.startDate.copy();
        this.accountDayStartBalance = other.accountDayStartBalance == null ? null : other.accountDayStartBalance.copy();
        this.accountDayStartEquity = other.accountDayStartEquity == null ? null : other.accountDayStartEquity.copy();
        this.runningMaxTotalDrawdown = other.runningMaxTotalDrawdown == null ? null : other.runningMaxTotalDrawdown.copy();
        this.runningMaxDailyDrawdown = other.runningMaxDailyDrawdown == null ? null : other.runningMaxDailyDrawdown.copy();
        this.lowestDrawdownPoint = other.lowestDrawdownPoint == null ? null : other.lowestDrawdownPoint.copy();
        this.rulesViolated = other.rulesViolated == null ? null : other.rulesViolated.copy();
        this.ruleViolated = other.ruleViolated == null ? null : other.ruleViolated.copy();
        this.ruleViolatedDate = other.ruleViolatedDate == null ? null : other.ruleViolatedDate.copy();
        this.mt4AccountId = other.mt4AccountId == null ? null : other.mt4AccountId.copy();
        this.challengeTypeId = other.challengeTypeId == null ? null : other.challengeTypeId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.userNotificationId = other.userNotificationId == null ? null : other.userNotificationId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public TradersChallengeTrackerCriteria copy() {
        return new TradersChallengeTrackerCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTradeChallengeName() {
        return tradeChallengeName;
    }

    public StringFilter tradeChallengeName() {
        if (tradeChallengeName == null) {
            tradeChallengeName = new StringFilter();
        }
        return tradeChallengeName;
    }

    public void setTradeChallengeName(StringFilter tradeChallengeName) {
        this.tradeChallengeName = tradeChallengeName;
    }

    public InstantFilter getStartDate() {
        return startDate;
    }

    public InstantFilter startDate() {
        if (startDate == null) {
            startDate = new InstantFilter();
        }
        return startDate;
    }

    public void setStartDate(InstantFilter startDate) {
        this.startDate = startDate;
    }

    public DoubleFilter getAccountDayStartBalance() {
        return accountDayStartBalance;
    }

    public DoubleFilter accountDayStartBalance() {
        if (accountDayStartBalance == null) {
            accountDayStartBalance = new DoubleFilter();
        }
        return accountDayStartBalance;
    }

    public void setAccountDayStartBalance(DoubleFilter accountDayStartBalance) {
        this.accountDayStartBalance = accountDayStartBalance;
    }

    public DoubleFilter getAccountDayStartEquity() {
        return accountDayStartEquity;
    }

    public DoubleFilter accountDayStartEquity() {
        if (accountDayStartEquity == null) {
            accountDayStartEquity = new DoubleFilter();
        }
        return accountDayStartEquity;
    }

    public void setAccountDayStartEquity(DoubleFilter accountDayStartEquity) {
        this.accountDayStartEquity = accountDayStartEquity;
    }

    public DoubleFilter getRunningMaxTotalDrawdown() {
        return runningMaxTotalDrawdown;
    }

    public DoubleFilter runningMaxTotalDrawdown() {
        if (runningMaxTotalDrawdown == null) {
            runningMaxTotalDrawdown = new DoubleFilter();
        }
        return runningMaxTotalDrawdown;
    }

    public void setRunningMaxTotalDrawdown(DoubleFilter runningMaxTotalDrawdown) {
        this.runningMaxTotalDrawdown = runningMaxTotalDrawdown;
    }

    public DoubleFilter getRunningMaxDailyDrawdown() {
        return runningMaxDailyDrawdown;
    }

    public DoubleFilter runningMaxDailyDrawdown() {
        if (runningMaxDailyDrawdown == null) {
            runningMaxDailyDrawdown = new DoubleFilter();
        }
        return runningMaxDailyDrawdown;
    }

    public void setRunningMaxDailyDrawdown(DoubleFilter runningMaxDailyDrawdown) {
        this.runningMaxDailyDrawdown = runningMaxDailyDrawdown;
    }

    public DoubleFilter getLowestDrawdownPoint() {
        return lowestDrawdownPoint;
    }

    public DoubleFilter lowestDrawdownPoint() {
        if (lowestDrawdownPoint == null) {
            lowestDrawdownPoint = new DoubleFilter();
        }
        return lowestDrawdownPoint;
    }

    public void setLowestDrawdownPoint(DoubleFilter lowestDrawdownPoint) {
        this.lowestDrawdownPoint = lowestDrawdownPoint;
    }

    public BooleanFilter getRulesViolated() {
        return rulesViolated;
    }

    public BooleanFilter rulesViolated() {
        if (rulesViolated == null) {
            rulesViolated = new BooleanFilter();
        }
        return rulesViolated;
    }

    public void setRulesViolated(BooleanFilter rulesViolated) {
        this.rulesViolated = rulesViolated;
    }

    public StringFilter getRuleViolated() {
        return ruleViolated;
    }

    public StringFilter ruleViolated() {
        if (ruleViolated == null) {
            ruleViolated = new StringFilter();
        }
        return ruleViolated;
    }

    public void setRuleViolated(StringFilter ruleViolated) {
        this.ruleViolated = ruleViolated;
    }

    public InstantFilter getRuleViolatedDate() {
        return ruleViolatedDate;
    }

    public InstantFilter ruleViolatedDate() {
        if (ruleViolatedDate == null) {
            ruleViolatedDate = new InstantFilter();
        }
        return ruleViolatedDate;
    }

    public void setRuleViolatedDate(InstantFilter ruleViolatedDate) {
        this.ruleViolatedDate = ruleViolatedDate;
    }

    public LongFilter getMt4AccountId() {
        return mt4AccountId;
    }

    public LongFilter mt4AccountId() {
        if (mt4AccountId == null) {
            mt4AccountId = new LongFilter();
        }
        return mt4AccountId;
    }

    public void setMt4AccountId(LongFilter mt4AccountId) {
        this.mt4AccountId = mt4AccountId;
    }

    public LongFilter getChallengeTypeId() {
        return challengeTypeId;
    }

    public LongFilter challengeTypeId() {
        if (challengeTypeId == null) {
            challengeTypeId = new LongFilter();
        }
        return challengeTypeId;
    }

    public void setChallengeTypeId(LongFilter challengeTypeId) {
        this.challengeTypeId = challengeTypeId;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public LongFilter userId() {
        if (userId == null) {
            userId = new LongFilter();
        }
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    public LongFilter getUserNotificationId() {
        return userNotificationId;
    }

    public LongFilter userNotificationId() {
        if (userNotificationId == null) {
            userNotificationId = new LongFilter();
        }
        return userNotificationId;
    }

    public void setUserNotificationId(LongFilter userNotificationId) {
        this.userNotificationId = userNotificationId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TradersChallengeTrackerCriteria that = (TradersChallengeTrackerCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(tradeChallengeName, that.tradeChallengeName) &&
            Objects.equals(startDate, that.startDate) &&
            Objects.equals(accountDayStartBalance, that.accountDayStartBalance) &&
            Objects.equals(accountDayStartEquity, that.accountDayStartEquity) &&
            Objects.equals(runningMaxTotalDrawdown, that.runningMaxTotalDrawdown) &&
            Objects.equals(runningMaxDailyDrawdown, that.runningMaxDailyDrawdown) &&
            Objects.equals(lowestDrawdownPoint, that.lowestDrawdownPoint) &&
            Objects.equals(rulesViolated, that.rulesViolated) &&
            Objects.equals(ruleViolated, that.ruleViolated) &&
            Objects.equals(ruleViolatedDate, that.ruleViolatedDate) &&
            Objects.equals(mt4AccountId, that.mt4AccountId) &&
            Objects.equals(challengeTypeId, that.challengeTypeId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(userNotificationId, that.userNotificationId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            tradeChallengeName,
            startDate,
            accountDayStartBalance,
            accountDayStartEquity,
            runningMaxTotalDrawdown,
            runningMaxDailyDrawdown,
            lowestDrawdownPoint,
            rulesViolated,
            ruleViolated,
            ruleViolatedDate,
            mt4AccountId,
            challengeTypeId,
            userId,
            userNotificationId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TradersChallengeTrackerCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (tradeChallengeName != null ? "tradeChallengeName=" + tradeChallengeName + ", " : "") +
            (startDate != null ? "startDate=" + startDate + ", " : "") +
            (accountDayStartBalance != null ? "accountDayStartBalance=" + accountDayStartBalance + ", " : "") +
            (accountDayStartEquity != null ? "accountDayStartEquity=" + accountDayStartEquity + ", " : "") +
            (runningMaxTotalDrawdown != null ? "runningMaxTotalDrawdown=" + runningMaxTotalDrawdown + ", " : "") +
            (runningMaxDailyDrawdown != null ? "runningMaxDailyDrawdown=" + runningMaxDailyDrawdown + ", " : "") +
            (lowestDrawdownPoint != null ? "lowestDrawdownPoint=" + lowestDrawdownPoint + ", " : "") +
            (rulesViolated != null ? "rulesViolated=" + rulesViolated + ", " : "") +
            (ruleViolated != null ? "ruleViolated=" + ruleViolated + ", " : "") +
            (ruleViolatedDate != null ? "ruleViolatedDate=" + ruleViolatedDate + ", " : "") +
            (mt4AccountId != null ? "mt4AccountId=" + mt4AccountId + ", " : "") +
            (challengeTypeId != null ? "challengeTypeId=" + challengeTypeId + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (userNotificationId != null ? "userNotificationId=" + userNotificationId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
