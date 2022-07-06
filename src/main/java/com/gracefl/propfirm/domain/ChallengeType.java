package com.gracefl.propfirm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ChallengeType.
 */
@Entity
@Table(name = "challenge_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ChallengeType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "challenge_type_name")
    private String challengeTypeName;

    @Lob
    @Column(name = "price")
    private byte[] price;

    @Column(name = "price_content_type")
    private String priceContentType;

    @Column(name = "refund_after_complete")
    private Boolean refundAfterComplete;

    @Column(name = "account_size")
    private Integer accountSize;

    @Column(name = "profit_target_phase_one")
    private Integer profitTargetPhaseOne;

    @Column(name = "profit_target_phase_two")
    private Integer profitTargetPhaseTwo;

    @Column(name = "duration_days_phase_one")
    private Integer durationDaysPhaseOne;

    @Column(name = "duration_days_phase_two")
    private Integer durationDaysPhaseTwo;

    @Column(name = "max_daily_drawdown")
    private Integer maxDailyDrawdown;

    @Column(name = "max_total_draw_down")
    private Integer maxTotalDrawDown;

    @Column(name = "profit_split_ratio")
    private Integer profitSplitRatio;

    @Column(name = "free_retry")
    private Boolean freeRetry;

    @Lob
    @Column(name = "user_bio")
    private String userBio;

    @Column(name = "in_active")
    private Boolean inActive;

    @Column(name = "in_active_date")
    private Instant inActiveDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "addressDetails", "user" }, allowSetters = true)
    private SiteAccount siteAccount;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ChallengeType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChallengeTypeName() {
        return this.challengeTypeName;
    }

    public ChallengeType challengeTypeName(String challengeTypeName) {
        this.setChallengeTypeName(challengeTypeName);
        return this;
    }

    public void setChallengeTypeName(String challengeTypeName) {
        this.challengeTypeName = challengeTypeName;
    }

    public byte[] getPrice() {
        return this.price;
    }

    public ChallengeType price(byte[] price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(byte[] price) {
        this.price = price;
    }

    public String getPriceContentType() {
        return this.priceContentType;
    }

    public ChallengeType priceContentType(String priceContentType) {
        this.priceContentType = priceContentType;
        return this;
    }

    public void setPriceContentType(String priceContentType) {
        this.priceContentType = priceContentType;
    }

    public Boolean getRefundAfterComplete() {
        return this.refundAfterComplete;
    }

    public ChallengeType refundAfterComplete(Boolean refundAfterComplete) {
        this.setRefundAfterComplete(refundAfterComplete);
        return this;
    }

    public void setRefundAfterComplete(Boolean refundAfterComplete) {
        this.refundAfterComplete = refundAfterComplete;
    }

    public Integer getAccountSize() {
        return this.accountSize;
    }

    public ChallengeType accountSize(Integer accountSize) {
        this.setAccountSize(accountSize);
        return this;
    }

    public void setAccountSize(Integer accountSize) {
        this.accountSize = accountSize;
    }

    public Integer getProfitTargetPhaseOne() {
        return this.profitTargetPhaseOne;
    }

    public ChallengeType profitTargetPhaseOne(Integer profitTargetPhaseOne) {
        this.setProfitTargetPhaseOne(profitTargetPhaseOne);
        return this;
    }

    public void setProfitTargetPhaseOne(Integer profitTargetPhaseOne) {
        this.profitTargetPhaseOne = profitTargetPhaseOne;
    }

    public Integer getProfitTargetPhaseTwo() {
        return this.profitTargetPhaseTwo;
    }

    public ChallengeType profitTargetPhaseTwo(Integer profitTargetPhaseTwo) {
        this.setProfitTargetPhaseTwo(profitTargetPhaseTwo);
        return this;
    }

    public void setProfitTargetPhaseTwo(Integer profitTargetPhaseTwo) {
        this.profitTargetPhaseTwo = profitTargetPhaseTwo;
    }

    public Integer getDurationDaysPhaseOne() {
        return this.durationDaysPhaseOne;
    }

    public ChallengeType durationDaysPhaseOne(Integer durationDaysPhaseOne) {
        this.setDurationDaysPhaseOne(durationDaysPhaseOne);
        return this;
    }

    public void setDurationDaysPhaseOne(Integer durationDaysPhaseOne) {
        this.durationDaysPhaseOne = durationDaysPhaseOne;
    }

    public Integer getDurationDaysPhaseTwo() {
        return this.durationDaysPhaseTwo;
    }

    public ChallengeType durationDaysPhaseTwo(Integer durationDaysPhaseTwo) {
        this.setDurationDaysPhaseTwo(durationDaysPhaseTwo);
        return this;
    }

    public void setDurationDaysPhaseTwo(Integer durationDaysPhaseTwo) {
        this.durationDaysPhaseTwo = durationDaysPhaseTwo;
    }

    public Integer getMaxDailyDrawdown() {
        return this.maxDailyDrawdown;
    }

    public ChallengeType maxDailyDrawdown(Integer maxDailyDrawdown) {
        this.setMaxDailyDrawdown(maxDailyDrawdown);
        return this;
    }

    public void setMaxDailyDrawdown(Integer maxDailyDrawdown) {
        this.maxDailyDrawdown = maxDailyDrawdown;
    }

    public Integer getMaxTotalDrawDown() {
        return this.maxTotalDrawDown;
    }

    public ChallengeType maxTotalDrawDown(Integer maxTotalDrawDown) {
        this.setMaxTotalDrawDown(maxTotalDrawDown);
        return this;
    }

    public void setMaxTotalDrawDown(Integer maxTotalDrawDown) {
        this.maxTotalDrawDown = maxTotalDrawDown;
    }

    public Integer getProfitSplitRatio() {
        return this.profitSplitRatio;
    }

    public ChallengeType profitSplitRatio(Integer profitSplitRatio) {
        this.setProfitSplitRatio(profitSplitRatio);
        return this;
    }

    public void setProfitSplitRatio(Integer profitSplitRatio) {
        this.profitSplitRatio = profitSplitRatio;
    }

    public Boolean getFreeRetry() {
        return this.freeRetry;
    }

    public ChallengeType freeRetry(Boolean freeRetry) {
        this.setFreeRetry(freeRetry);
        return this;
    }

    public void setFreeRetry(Boolean freeRetry) {
        this.freeRetry = freeRetry;
    }

    public String getUserBio() {
        return this.userBio;
    }

    public ChallengeType userBio(String userBio) {
        this.setUserBio(userBio);
        return this;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public Boolean getInActive() {
        return this.inActive;
    }

    public ChallengeType inActive(Boolean inActive) {
        this.setInActive(inActive);
        return this;
    }

    public void setInActive(Boolean inActive) {
        this.inActive = inActive;
    }

    public Instant getInActiveDate() {
        return this.inActiveDate;
    }

    public ChallengeType inActiveDate(Instant inActiveDate) {
        this.setInActiveDate(inActiveDate);
        return this;
    }

    public void setInActiveDate(Instant inActiveDate) {
        this.inActiveDate = inActiveDate;
    }

    public SiteAccount getSiteAccount() {
        return this.siteAccount;
    }

    public void setSiteAccount(SiteAccount siteAccount) {
        this.siteAccount = siteAccount;
    }

    public ChallengeType siteAccount(SiteAccount siteAccount) {
        this.setSiteAccount(siteAccount);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ChallengeType)) {
            return false;
        }
        return id != null && id.equals(((ChallengeType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ChallengeType{" +
            "id=" + getId() +
            ", challengeTypeName='" + getChallengeTypeName() + "'" +
            ", price='" + getPrice() + "'" +
            ", priceContentType='" + getPriceContentType() + "'" +
            ", refundAfterComplete='" + getRefundAfterComplete() + "'" +
            ", accountSize=" + getAccountSize() +
            ", profitTargetPhaseOne=" + getProfitTargetPhaseOne() +
            ", profitTargetPhaseTwo=" + getProfitTargetPhaseTwo() +
            ", durationDaysPhaseOne=" + getDurationDaysPhaseOne() +
            ", durationDaysPhaseTwo=" + getDurationDaysPhaseTwo() +
            ", maxDailyDrawdown=" + getMaxDailyDrawdown() +
            ", maxTotalDrawDown=" + getMaxTotalDrawDown() +
            ", profitSplitRatio=" + getProfitSplitRatio() +
            ", freeRetry='" + getFreeRetry() + "'" +
            ", userBio='" + getUserBio() + "'" +
            ", inActive='" + getInActive() + "'" +
            ", inActiveDate='" + getInActiveDate() + "'" +
            "}";
    }
}
