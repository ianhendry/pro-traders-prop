package com.gracefl.propfirm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SiteAccount.
 */
@Entity
@Table(name = "site_account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SiteAccount implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "account_name")
    private String accountName;

    @Lob
    @Column(name = "user_picture")
    private byte[] userPicture;

    @Column(name = "user_picture_content_type")
    private String userPictureContentType;

    @Lob
    @Column(name = "user_bio")
    private String userBio;

    @Column(name = "in_active")
    private Boolean inActive;

    @Column(name = "in_active_date")
    private Instant inActiveDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "siteAccounts" }, allowSetters = true)
    private BillingDetails addressDetails;

    @OneToOne
    @JoinColumn(unique = true)
    @ManyToOne
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SiteAccount id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountName() {
        return this.accountName;
    }

    public SiteAccount accountName(String accountName) {
        this.setAccountName(accountName);
        return this;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public byte[] getUserPicture() {
        return this.userPicture;
    }

    public SiteAccount userPicture(byte[] userPicture) {
        this.setUserPicture(userPicture);
        return this;
    }

    public void setUserPicture(byte[] userPicture) {
        this.userPicture = userPicture;
    }

    public String getUserPictureContentType() {
        return this.userPictureContentType;
    }

    public SiteAccount userPictureContentType(String userPictureContentType) {
        this.userPictureContentType = userPictureContentType;
        return this;
    }

    public void setUserPictureContentType(String userPictureContentType) {
        this.userPictureContentType = userPictureContentType;
    }

    public String getUserBio() {
        return this.userBio;
    }

    public SiteAccount userBio(String userBio) {
        this.setUserBio(userBio);
        return this;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public Boolean getInActive() {
        return this.inActive;
    }

    public SiteAccount inActive(Boolean inActive) {
        this.setInActive(inActive);
        return this;
    }

    public void setInActive(Boolean inActive) {
        this.inActive = inActive;
    }

    public Instant getInActiveDate() {
        return this.inActiveDate;
    }

    public SiteAccount inActiveDate(Instant inActiveDate) {
        this.setInActiveDate(inActiveDate);
        return this;
    }

    public void setInActiveDate(Instant inActiveDate) {
        this.inActiveDate = inActiveDate;
    }

    public BillingDetails getAddressDetails() {
        return this.addressDetails;
    }

    public void setAddressDetails(BillingDetails billingDetails) {
        this.addressDetails = billingDetails;
    }

    public SiteAccount addressDetails(BillingDetails billingDetails) {
        this.setAddressDetails(billingDetails);
        return this;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public SiteAccount user(User user) {
        this.setUser(user);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SiteAccount)) {
            return false;
        }
        return id != null && id.equals(((SiteAccount) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SiteAccount{" +
            "id=" + getId() +
            ", accountName='" + getAccountName() + "'" +
            ", userPicture='" + getUserPicture() + "'" +
            ", userPictureContentType='" + getUserPictureContentType() + "'" +
            ", userBio='" + getUserBio() + "'" +
            ", inActive='" + getInActive() + "'" +
            ", inActiveDate='" + getInActiveDate() + "'" +
            "}";
    }
}
