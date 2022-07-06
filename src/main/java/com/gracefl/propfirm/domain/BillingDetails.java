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
 * A BillingDetails.
 */
@Entity
@Table(name = "billing_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class BillingDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "contact_name")
    private String contactName;

    @Column(name = "address_1")
    private String address1;

    @Column(name = "address_2")
    private String address2;

    @Column(name = "address_3")
    private String address3;

    @Column(name = "address_4")
    private String address4;

    @Column(name = "address_5")
    private String address5;

    @Column(name = "address_6")
    private String address6;

    @Column(name = "dial_code")
    private String dialCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "messenger_id")
    private String messengerId;

    @Column(name = "date_added")
    private Instant dateAdded;

    @Column(name = "in_active")
    private Boolean inActive;

    @Column(name = "in_active_date")
    private Instant inActiveDate;

    @OneToMany(mappedBy = "addressDetails")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "user", "addressDetails", "user" }, allowSetters = true)
    private Set<SiteAccount> siteAccounts = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public BillingDetails id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContactName() {
        return this.contactName;
    }

    public BillingDetails contactName(String contactName) {
        this.setContactName(contactName);
        return this;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getAddress1() {
        return this.address1;
    }

    public BillingDetails address1(String address1) {
        this.setAddress1(address1);
        return this;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return this.address2;
    }

    public BillingDetails address2(String address2) {
        this.setAddress2(address2);
        return this;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return this.address3;
    }

    public BillingDetails address3(String address3) {
        this.setAddress3(address3);
        return this;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getAddress4() {
        return this.address4;
    }

    public BillingDetails address4(String address4) {
        this.setAddress4(address4);
        return this;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    public String getAddress5() {
        return this.address5;
    }

    public BillingDetails address5(String address5) {
        this.setAddress5(address5);
        return this;
    }

    public void setAddress5(String address5) {
        this.address5 = address5;
    }

    public String getAddress6() {
        return this.address6;
    }

    public BillingDetails address6(String address6) {
        this.setAddress6(address6);
        return this;
    }

    public void setAddress6(String address6) {
        this.address6 = address6;
    }

    public String getDialCode() {
        return this.dialCode;
    }

    public BillingDetails dialCode(String dialCode) {
        this.setDialCode(dialCode);
        return this;
    }

    public void setDialCode(String dialCode) {
        this.dialCode = dialCode;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public BillingDetails phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMessengerId() {
        return this.messengerId;
    }

    public BillingDetails messengerId(String messengerId) {
        this.setMessengerId(messengerId);
        return this;
    }

    public void setMessengerId(String messengerId) {
        this.messengerId = messengerId;
    }

    public Instant getDateAdded() {
        return this.dateAdded;
    }

    public BillingDetails dateAdded(Instant dateAdded) {
        this.setDateAdded(dateAdded);
        return this;
    }

    public void setDateAdded(Instant dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Boolean getInActive() {
        return this.inActive;
    }

    public BillingDetails inActive(Boolean inActive) {
        this.setInActive(inActive);
        return this;
    }

    public void setInActive(Boolean inActive) {
        this.inActive = inActive;
    }

    public Instant getInActiveDate() {
        return this.inActiveDate;
    }

    public BillingDetails inActiveDate(Instant inActiveDate) {
        this.setInActiveDate(inActiveDate);
        return this;
    }

    public void setInActiveDate(Instant inActiveDate) {
        this.inActiveDate = inActiveDate;
    }

    public Set<SiteAccount> getSiteAccounts() {
        return this.siteAccounts;
    }

    public void setSiteAccounts(Set<SiteAccount> siteAccounts) {
        if (this.siteAccounts != null) {
            this.siteAccounts.forEach(i -> i.setAddressDetails(null));
        }
        if (siteAccounts != null) {
            siteAccounts.forEach(i -> i.setAddressDetails(this));
        }
        this.siteAccounts = siteAccounts;
    }

    public BillingDetails siteAccounts(Set<SiteAccount> siteAccounts) {
        this.setSiteAccounts(siteAccounts);
        return this;
    }

    public BillingDetails addSiteAccount(SiteAccount siteAccount) {
        this.siteAccounts.add(siteAccount);
        siteAccount.setAddressDetails(this);
        return this;
    }

    public BillingDetails removeSiteAccount(SiteAccount siteAccount) {
        this.siteAccounts.remove(siteAccount);
        siteAccount.setAddressDetails(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BillingDetails)) {
            return false;
        }
        return id != null && id.equals(((BillingDetails) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BillingDetails{" +
            "id=" + getId() +
            ", contactName='" + getContactName() + "'" +
            ", address1='" + getAddress1() + "'" +
            ", address2='" + getAddress2() + "'" +
            ", address3='" + getAddress3() + "'" +
            ", address4='" + getAddress4() + "'" +
            ", address5='" + getAddress5() + "'" +
            ", address6='" + getAddress6() + "'" +
            ", dialCode='" + getDialCode() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", messengerId='" + getMessengerId() + "'" +
            ", dateAdded='" + getDateAdded() + "'" +
            ", inActive='" + getInActive() + "'" +
            ", inActiveDate='" + getInActiveDate() + "'" +
            "}";
    }
}
