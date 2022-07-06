package com.gracefl.propfirm.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A UserNotification.
 */
@Entity
@Table(name = "user_notification")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserNotification implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "comment_title", nullable = false)
    private String commentTitle;

    @Lob
    @Column(name = "comment_body")
    private String commentBody;

    @Lob
    @Column(name = "comment_media")
    private byte[] commentMedia;

    @Column(name = "comment_media_content_type")
    private String commentMediaContentType;

    @NotNull
    @Column(name = "date_added", nullable = false)
    private Instant dateAdded;

    @Column(name = "make_public_visible_on_site")
    private Boolean makePublicVisibleOnSite;

    @ManyToOne
    @JsonIgnoreProperties(value = { "mt4Account", "challengeType", "user", "userNotifications" }, allowSetters = true)
    private TradersChallengeTracker tradersChallengeTracker;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public UserNotification id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCommentTitle() {
        return this.commentTitle;
    }

    public UserNotification commentTitle(String commentTitle) {
        this.setCommentTitle(commentTitle);
        return this;
    }

    public void setCommentTitle(String commentTitle) {
        this.commentTitle = commentTitle;
    }

    public String getCommentBody() {
        return this.commentBody;
    }

    public UserNotification commentBody(String commentBody) {
        this.setCommentBody(commentBody);
        return this;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public byte[] getCommentMedia() {
        return this.commentMedia;
    }

    public UserNotification commentMedia(byte[] commentMedia) {
        this.setCommentMedia(commentMedia);
        return this;
    }

    public void setCommentMedia(byte[] commentMedia) {
        this.commentMedia = commentMedia;
    }

    public String getCommentMediaContentType() {
        return this.commentMediaContentType;
    }

    public UserNotification commentMediaContentType(String commentMediaContentType) {
        this.commentMediaContentType = commentMediaContentType;
        return this;
    }

    public void setCommentMediaContentType(String commentMediaContentType) {
        this.commentMediaContentType = commentMediaContentType;
    }

    public Instant getDateAdded() {
        return this.dateAdded;
    }

    public UserNotification dateAdded(Instant dateAdded) {
        this.setDateAdded(dateAdded);
        return this;
    }

    public void setDateAdded(Instant dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Boolean getMakePublicVisibleOnSite() {
        return this.makePublicVisibleOnSite;
    }

    public UserNotification makePublicVisibleOnSite(Boolean makePublicVisibleOnSite) {
        this.setMakePublicVisibleOnSite(makePublicVisibleOnSite);
        return this;
    }

    public void setMakePublicVisibleOnSite(Boolean makePublicVisibleOnSite) {
        this.makePublicVisibleOnSite = makePublicVisibleOnSite;
    }

    public TradersChallengeTracker getTradersChallengeTracker() {
        return this.tradersChallengeTracker;
    }

    public void setTradersChallengeTracker(TradersChallengeTracker tradersChallengeTracker) {
        this.tradersChallengeTracker = tradersChallengeTracker;
    }

    public UserNotification tradersChallengeTracker(TradersChallengeTracker tradersChallengeTracker) {
        this.setTradersChallengeTracker(tradersChallengeTracker);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserNotification)) {
            return false;
        }
        return id != null && id.equals(((UserNotification) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserNotification{" +
            "id=" + getId() +
            ", commentTitle='" + getCommentTitle() + "'" +
            ", commentBody='" + getCommentBody() + "'" +
            ", commentMedia='" + getCommentMedia() + "'" +
            ", commentMediaContentType='" + getCommentMediaContentType() + "'" +
            ", dateAdded='" + getDateAdded() + "'" +
            ", makePublicVisibleOnSite='" + getMakePublicVisibleOnSite() + "'" +
            "}";
    }
}
