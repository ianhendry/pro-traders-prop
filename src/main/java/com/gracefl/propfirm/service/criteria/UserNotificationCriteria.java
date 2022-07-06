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
 * Criteria class for the {@link com.gracefl.propfirm.domain.UserNotification} entity. This class is used
 * in {@link com.gracefl.propfirm.web.rest.UserNotificationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /user-notifications?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
public class UserNotificationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter commentTitle;

    private InstantFilter dateAdded;

    private BooleanFilter makePublicVisibleOnSite;

    private LongFilter tradersChallengeTrackerId;

    private Boolean distinct;

    public UserNotificationCriteria() {}

    public UserNotificationCriteria(UserNotificationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.commentTitle = other.commentTitle == null ? null : other.commentTitle.copy();
        this.dateAdded = other.dateAdded == null ? null : other.dateAdded.copy();
        this.makePublicVisibleOnSite = other.makePublicVisibleOnSite == null ? null : other.makePublicVisibleOnSite.copy();
        this.tradersChallengeTrackerId = other.tradersChallengeTrackerId == null ? null : other.tradersChallengeTrackerId.copy();
        this.distinct = other.distinct;
    }

    @Override
    public UserNotificationCriteria copy() {
        return new UserNotificationCriteria(this);
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

    public StringFilter getCommentTitle() {
        return commentTitle;
    }

    public StringFilter commentTitle() {
        if (commentTitle == null) {
            commentTitle = new StringFilter();
        }
        return commentTitle;
    }

    public void setCommentTitle(StringFilter commentTitle) {
        this.commentTitle = commentTitle;
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

    public BooleanFilter getMakePublicVisibleOnSite() {
        return makePublicVisibleOnSite;
    }

    public BooleanFilter makePublicVisibleOnSite() {
        if (makePublicVisibleOnSite == null) {
            makePublicVisibleOnSite = new BooleanFilter();
        }
        return makePublicVisibleOnSite;
    }

    public void setMakePublicVisibleOnSite(BooleanFilter makePublicVisibleOnSite) {
        this.makePublicVisibleOnSite = makePublicVisibleOnSite;
    }

    public LongFilter getTradersChallengeTrackerId() {
        return tradersChallengeTrackerId;
    }

    public LongFilter tradersChallengeTrackerId() {
        if (tradersChallengeTrackerId == null) {
            tradersChallengeTrackerId = new LongFilter();
        }
        return tradersChallengeTrackerId;
    }

    public void setTradersChallengeTrackerId(LongFilter tradersChallengeTrackerId) {
        this.tradersChallengeTrackerId = tradersChallengeTrackerId;
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
        final UserNotificationCriteria that = (UserNotificationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(commentTitle, that.commentTitle) &&
            Objects.equals(dateAdded, that.dateAdded) &&
            Objects.equals(makePublicVisibleOnSite, that.makePublicVisibleOnSite) &&
            Objects.equals(tradersChallengeTrackerId, that.tradersChallengeTrackerId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, commentTitle, dateAdded, makePublicVisibleOnSite, tradersChallengeTrackerId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserNotificationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (commentTitle != null ? "commentTitle=" + commentTitle + ", " : "") +
            (dateAdded != null ? "dateAdded=" + dateAdded + ", " : "") +
            (makePublicVisibleOnSite != null ? "makePublicVisibleOnSite=" + makePublicVisibleOnSite + ", " : "") +
            (tradersChallengeTrackerId != null ? "tradersChallengeTrackerId=" + tradersChallengeTrackerId + ", " : "") +
            (distinct != null ? "distinct=" + distinct + ", " : "") +
            "}";
    }
}
