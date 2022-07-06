package com.gracefl.propfirm.service;

import com.gracefl.propfirm.domain.*; // for static metamodels
import com.gracefl.propfirm.domain.TradersChallengeTracker;
import com.gracefl.propfirm.repository.TradersChallengeTrackerRepository;
import com.gracefl.propfirm.service.criteria.TradersChallengeTrackerCriteria;
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
 * Service for executing complex queries for {@link TradersChallengeTracker} entities in the database.
 * The main input is a {@link TradersChallengeTrackerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TradersChallengeTracker} or a {@link Page} of {@link TradersChallengeTracker} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TradersChallengeTrackerQueryService extends QueryService<TradersChallengeTracker> {

    private final Logger log = LoggerFactory.getLogger(TradersChallengeTrackerQueryService.class);

    private final TradersChallengeTrackerRepository tradersChallengeTrackerRepository;

    public TradersChallengeTrackerQueryService(TradersChallengeTrackerRepository tradersChallengeTrackerRepository) {
        this.tradersChallengeTrackerRepository = tradersChallengeTrackerRepository;
    }

    /**
     * Return a {@link List} of {@link TradersChallengeTracker} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TradersChallengeTracker> findByCriteria(TradersChallengeTrackerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TradersChallengeTracker> specification = createSpecification(criteria);
        return tradersChallengeTrackerRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TradersChallengeTracker} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TradersChallengeTracker> findByCriteria(TradersChallengeTrackerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TradersChallengeTracker> specification = createSpecification(criteria);
        return tradersChallengeTrackerRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TradersChallengeTrackerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TradersChallengeTracker> specification = createSpecification(criteria);
        return tradersChallengeTrackerRepository.count(specification);
    }

    /**
     * Function to convert {@link TradersChallengeTrackerCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TradersChallengeTracker> createSpecification(TradersChallengeTrackerCriteria criteria) {
        Specification<TradersChallengeTracker> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TradersChallengeTracker_.id));
            }
            if (criteria.getTradeChallengeName() != null) {
                specification =
                    specification.and(
                        buildStringSpecification(criteria.getTradeChallengeName(), TradersChallengeTracker_.tradeChallengeName)
                    );
            }
            if (criteria.getStartDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStartDate(), TradersChallengeTracker_.startDate));
            }
            if (criteria.getAccountDayStartBalance() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getAccountDayStartBalance(), TradersChallengeTracker_.accountDayStartBalance)
                    );
            }
            if (criteria.getAccountDayStartEquity() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getAccountDayStartEquity(), TradersChallengeTracker_.accountDayStartEquity)
                    );
            }
            if (criteria.getRunningMaxTotalDrawdown() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getRunningMaxTotalDrawdown(), TradersChallengeTracker_.runningMaxTotalDrawdown)
                    );
            }
            if (criteria.getRunningMaxDailyDrawdown() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getRunningMaxDailyDrawdown(), TradersChallengeTracker_.runningMaxDailyDrawdown)
                    );
            }
            if (criteria.getLowestDrawdownPoint() != null) {
                specification =
                    specification.and(
                        buildRangeSpecification(criteria.getLowestDrawdownPoint(), TradersChallengeTracker_.lowestDrawdownPoint)
                    );
            }
            if (criteria.getRulesViolated() != null) {
                specification = specification.and(buildSpecification(criteria.getRulesViolated(), TradersChallengeTracker_.rulesViolated));
            }
            if (criteria.getRuleViolated() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getRuleViolated(), TradersChallengeTracker_.ruleViolated));
            }
            if (criteria.getRuleViolatedDate() != null) {
                specification =
                    specification.and(buildRangeSpecification(criteria.getRuleViolatedDate(), TradersChallengeTracker_.ruleViolatedDate));
            }
            if (criteria.getMt4AccountId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getMt4AccountId(),
                            root -> root.join(TradersChallengeTracker_.mt4Account, JoinType.LEFT).get(Mt4Account_.id)
                        )
                    );
            }
            if (criteria.getChallengeTypeId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getChallengeTypeId(),
                            root -> root.join(TradersChallengeTracker_.challengeType, JoinType.LEFT).get(ChallengeType_.id)
                        )
                    );
            }
            if (criteria.getUserId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserId(),
                            root -> root.join(TradersChallengeTracker_.user, JoinType.LEFT).get(User_.id)
                        )
                    );
            }
            if (criteria.getUserNotificationId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getUserNotificationId(),
                            root -> root.join(TradersChallengeTracker_.userNotifications, JoinType.LEFT).get(UserNotification_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
