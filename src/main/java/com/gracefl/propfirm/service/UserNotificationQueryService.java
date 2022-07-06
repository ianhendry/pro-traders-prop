package com.gracefl.propfirm.service;

import com.gracefl.propfirm.domain.*; // for static metamodels
import com.gracefl.propfirm.domain.UserNotification;
import com.gracefl.propfirm.repository.UserNotificationRepository;
import com.gracefl.propfirm.service.criteria.UserNotificationCriteria;
import java.util.List;
import javax.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link UserNotification} entities in the database.
 * The main input is a {@link UserNotificationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserNotification} or a {@link Page} of {@link UserNotification} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserNotificationQueryService extends QueryService<UserNotification> {

    private final Logger log = LoggerFactory.getLogger(UserNotificationQueryService.class);

    private final UserNotificationRepository userNotificationRepository;

    public UserNotificationQueryService(UserNotificationRepository userNotificationRepository) {
        this.userNotificationRepository = userNotificationRepository;
    }

    /**
     * Return a {@link List} of {@link UserNotification} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserNotification> findByCriteria(UserNotificationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserNotification> specification = createSpecification(criteria);
        return userNotificationRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserNotification} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserNotification> findByCriteria(UserNotificationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserNotification> specification = createSpecification(criteria);
        return userNotificationRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserNotificationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserNotification> specification = createSpecification(criteria);
        return userNotificationRepository.count(specification);
    }

    /**
     * Function to convert {@link UserNotificationCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserNotification> createSpecification(UserNotificationCriteria criteria) {
        Specification<UserNotification> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserNotification_.id));
            }
            if (criteria.getCommentTitle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCommentTitle(), UserNotification_.commentTitle));
            }
            if (criteria.getDateAdded() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateAdded(), UserNotification_.dateAdded));
            }
            if (criteria.getMakePublicVisibleOnSite() != null) {
                specification =
                    specification.and(buildSpecification(criteria.getMakePublicVisibleOnSite(), UserNotification_.makePublicVisibleOnSite));
            }
            if (criteria.getTradersChallengeTrackerId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getTradersChallengeTrackerId(),
                            root -> root.join(UserNotification_.tradersChallengeTracker, JoinType.LEFT).get(TradersChallengeTracker_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
