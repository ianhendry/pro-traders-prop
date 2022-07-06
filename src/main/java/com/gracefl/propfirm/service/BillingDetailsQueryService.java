package com.gracefl.propfirm.service;

import com.gracefl.propfirm.domain.*; // for static metamodels
import com.gracefl.propfirm.domain.BillingDetails;
import com.gracefl.propfirm.repository.BillingDetailsRepository;
import com.gracefl.propfirm.service.criteria.BillingDetailsCriteria;
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
 * Service for executing complex queries for {@link BillingDetails} entities in the database.
 * The main input is a {@link BillingDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link BillingDetails} or a {@link Page} of {@link BillingDetails} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class BillingDetailsQueryService extends QueryService<BillingDetails> {

    private final Logger log = LoggerFactory.getLogger(BillingDetailsQueryService.class);

    private final BillingDetailsRepository billingDetailsRepository;

    public BillingDetailsQueryService(BillingDetailsRepository billingDetailsRepository) {
        this.billingDetailsRepository = billingDetailsRepository;
    }

    /**
     * Return a {@link List} of {@link BillingDetails} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<BillingDetails> findByCriteria(BillingDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<BillingDetails> specification = createSpecification(criteria);
        return billingDetailsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link BillingDetails} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<BillingDetails> findByCriteria(BillingDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<BillingDetails> specification = createSpecification(criteria);
        return billingDetailsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(BillingDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<BillingDetails> specification = createSpecification(criteria);
        return billingDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link BillingDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<BillingDetails> createSpecification(BillingDetailsCriteria criteria) {
        Specification<BillingDetails> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), BillingDetails_.id));
            }
            if (criteria.getContactName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContactName(), BillingDetails_.contactName));
            }
            if (criteria.getAddress1() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress1(), BillingDetails_.address1));
            }
            if (criteria.getAddress2() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress2(), BillingDetails_.address2));
            }
            if (criteria.getAddress3() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress3(), BillingDetails_.address3));
            }
            if (criteria.getAddress4() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress4(), BillingDetails_.address4));
            }
            if (criteria.getAddress5() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress5(), BillingDetails_.address5));
            }
            if (criteria.getAddress6() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress6(), BillingDetails_.address6));
            }
            if (criteria.getDialCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDialCode(), BillingDetails_.dialCode));
            }
            if (criteria.getPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhoneNumber(), BillingDetails_.phoneNumber));
            }
            if (criteria.getMessengerId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMessengerId(), BillingDetails_.messengerId));
            }
            if (criteria.getDateAdded() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateAdded(), BillingDetails_.dateAdded));
            }
            if (criteria.getInActive() != null) {
                specification = specification.and(buildSpecification(criteria.getInActive(), BillingDetails_.inActive));
            }
            if (criteria.getInActiveDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInActiveDate(), BillingDetails_.inActiveDate));
            }
            if (criteria.getSiteAccountId() != null) {
                specification =
                    specification.and(
                        buildSpecification(
                            criteria.getSiteAccountId(),
                            root -> root.join(BillingDetails_.siteAccounts, JoinType.LEFT).get(SiteAccount_.id)
                        )
                    );
            }
        }
        return specification;
    }
}
