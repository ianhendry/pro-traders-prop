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
 * Criteria class for the {@link com.gracefl.propfirm.domain.BillingDetails} entity. This class is used
 * in {@link com.gracefl.propfirm.web.rest.BillingDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /billing-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class BillingDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter contactName;

    private StringFilter address1;

    private StringFilter address2;

    private StringFilter address3;

    private StringFilter address4;

    private StringFilter address5;

    private StringFilter address6;

    private StringFilter dialCode;

    private StringFilter phoneNumber;

    private StringFilter messengerId;

    private InstantFilter dateAdded;

    private BooleanFilter inActive;

    private InstantFilter inActiveDate;

    private LongFilter siteAccountId;

    private Boolean distinct;

    public BillingDetailsCriteria() {}

    public BillingDetailsCriteria(BillingDetailsCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.contactName = other.contactName == null ? null : other.contactName.copy();
        this.address1 = other.address1 == null ? null : other.address1.copy();
        this.address2 = other.address2 == null ? null : other.address2.copy();
        this.address3 = other.address3 == null ? null : other.address3.copy();
        this.address4 = other.address4 == null ? null : other.address4.copy();
        this.address5 = other.address5 == null ? null : other.address5.copy();
        this.address6 = other.address6 == null ? null : other.address6.copy();
        this.dialCode = other.dialCode == null ? null : other.dialCode.copy();
        this.phoneNumber = other.phoneNumber == null ? null : other.phoneNumber.copy();
        this.messengerId = other.messengerId == null ? null : other.messengerId.copy();
        this.dateAdded = other.dateAdded == null ? null : other.dateAdded.copy();
        this.inActive = other.inActive == null ? null : other.inActive.copy();
        this.inActiveDate = other.inActiveDate == null ? null : other.inActiveDate.copy();
        this.siteAccountId = other.siteAccountId == null ? null : other.siteAccountId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public BillingDetailsCriteria copy() {
        return new BillingDetailsCriteria(this);
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

    public StringFilter getContactName() {
        return contactName;
    }

    public StringFilter contactName() {
        if (contactName == null) {
            contactName = new StringFilter();
        }
        return contactName;
    }

    public void setContactName(StringFilter contactName) {
        this.contactName = contactName;
    }

    public StringFilter getAddress1() {
        return address1;
    }

    public StringFilter address1() {
        if (address1 == null) {
            address1 = new StringFilter();
        }
        return address1;
    }

    public void setAddress1(StringFilter address1) {
        this.address1 = address1;
    }

    public StringFilter getAddress2() {
        return address2;
    }

    public StringFilter address2() {
        if (address2 == null) {
            address2 = new StringFilter();
        }
        return address2;
    }

    public void setAddress2(StringFilter address2) {
        this.address2 = address2;
    }

    public StringFilter getAddress3() {
        return address3;
    }

    public StringFilter address3() {
        if (address3 == null) {
            address3 = new StringFilter();
        }
        return address3;
    }

    public void setAddress3(StringFilter address3) {
        this.address3 = address3;
    }

    public StringFilter getAddress4() {
        return address4;
    }

    public StringFilter address4() {
        if (address4 == null) {
            address4 = new StringFilter();
        }
        return address4;
    }

    public void setAddress4(StringFilter address4) {
        this.address4 = address4;
    }

    public StringFilter getAddress5() {
        return address5;
    }

    public StringFilter address5() {
        if (address5 == null) {
            address5 = new StringFilter();
        }
        return address5;
    }

    public void setAddress5(StringFilter address5) {
        this.address5 = address5;
    }

    public StringFilter getAddress6() {
        return address6;
    }

    public StringFilter address6() {
        if (address6 == null) {
            address6 = new StringFilter();
        }
        return address6;
    }

    public void setAddress6(StringFilter address6) {
        this.address6 = address6;
    }

    public StringFilter getDialCode() {
        return dialCode;
    }

    public StringFilter dialCode() {
        if (dialCode == null) {
            dialCode = new StringFilter();
        }
        return dialCode;
    }

    public void setDialCode(StringFilter dialCode) {
        this.dialCode = dialCode;
    }

    public StringFilter getPhoneNumber() {
        return phoneNumber;
    }

    public StringFilter phoneNumber() {
        if (phoneNumber == null) {
            phoneNumber = new StringFilter();
        }
        return phoneNumber;
    }

    public void setPhoneNumber(StringFilter phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public StringFilter getMessengerId() {
        return messengerId;
    }

    public StringFilter messengerId() {
        if (messengerId == null) {
            messengerId = new StringFilter();
        }
        return messengerId;
    }

    public void setMessengerId(StringFilter messengerId) {
        this.messengerId = messengerId;
    }

    public InstantFilter getDateAdded() {
        return dateAdded;
    }

    public InstantFilter dateAdded() {
        if (dateAdded == null) {
            dateAdded = new InstantFilter();
        }
        return dateAdded;
    }

    public void setDateAdded(InstantFilter dateAdded) {
        this.dateAdded = dateAdded;
    }

    public BooleanFilter getInActive() {
        return inActive;
    }

    public BooleanFilter inActive() {
        if (inActive == null) {
            inActive = new BooleanFilter();
        }
        return inActive;
    }

    public void setInActive(BooleanFilter inActive) {
        this.inActive = inActive;
    }

    public InstantFilter getInActiveDate() {
        return inActiveDate;
    }

    public InstantFilter inActiveDate() {
        if (inActiveDate == null) {
            inActiveDate = new InstantFilter();
        }
        return inActiveDate;
    }

    public void setInActiveDate(InstantFilter inActiveDate) {
        this.inActiveDate = inActiveDate;
    }

    public LongFilter getSiteAccountId() {
        return siteAccountId;
    }

    public LongFilter siteAccountId() {
        if (siteAccountId == null) {
            siteAccountId = new LongFilter();
        }
        return siteAccountId;
    }

    public void setSiteAccountId(LongFilter siteAccountId) {
        this.siteAccountId = siteAccountId;
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
        final BillingDetailsCriteria that = (BillingDetailsCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(contactName, that.contactName) &&
            Objects.equals(address1, that.address1) &&
            Objects.equals(address2, that.address2) &&
            Objects.equals(address3, that.address3) &&
            Objects.equals(address4, that.address4) &&
            Objects.equals(address5, that.address5) &&
            Objects.equals(address6, that.address6) &&
            Objects.equals(dialCode, that.dialCode) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(messengerId, that.messengerId) &&
            Objects.equals(dateAdded, that.dateAdded) &&
            Objects.equals(inActive, that.inActive) &&
            Objects.equals(inActiveDate, that.inActiveDate) &&
            Objects.equals(siteAccountId, that.siteAccountId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            contactName,
            address1,
            address2,
            address3,
            address4,
            address5,
            address6,
            dialCode,
            phoneNumber,
            messengerId,
            dateAdded,
            inActive,
            inActiveDate,
            siteAccountId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "BillingDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (contactName != null ? "contactName=" + contactName + ", " : "") +
            (address1 != null ? "address1=" + address1 + ", " : "") +
            (address2 != null ? "address2=" + address2 + ", " : "") +
            (address3 != null ? "address3=" + address3 + ", " : "") +
            (address4 != null ? "address4=" + address4 + ", " : "") +
            (address5 != null ? "address5=" + address5 + ", " : "") +
            (address6 != null ? "address6=" + address6 + ", " : "") +
            (dialCode != null ? "dialCode=" + dialCode + ", " : "") +
            (phoneNumber != null ? "phoneNumber=" + phoneNumber + ", " : "") +
            (messengerId != null ? "messengerId=" + messengerId + ", " : "") +
            (dateAdded != null ? "dateAdded=" + dateAdded + ", " : "") +
            (inActive != null ? "inActive=" + inActive + ", " : "") +
            (inActiveDate != null ? "inActiveDate=" + inActiveDate + ", " : "") +
            (siteAccountId != null ? "siteAccountId=" + siteAccountId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
