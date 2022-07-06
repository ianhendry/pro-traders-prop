package com.gracefl.propfirm.service;

import com.gracefl.propfirm.domain.BillingDetails;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link BillingDetails}.
 */
public interface BillingDetailsService {
    /**
     * Save a billingDetails.
     *
     * @param billingDetails the entity to save.
     * @return the persisted entity.
     */
    BillingDetails save(BillingDetails billingDetails);

    /**
     * Updates a billingDetails.
     *
     * @param billingDetails the entity to update.
     * @return the persisted entity.
     */
    BillingDetails update(BillingDetails billingDetails);

    /**
     * Partially updates a billingDetails.
     *
     * @param billingDetails the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BillingDetails> partialUpdate(BillingDetails billingDetails);

    /**
     * Get all the billingDetails.
     *
     * @return the list of entities.
     */
    List<BillingDetails> findAll();

    /**
     * Get the "id" billingDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BillingDetails> findOne(Long id);

    /**
     * Delete the "id" billingDetails.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
