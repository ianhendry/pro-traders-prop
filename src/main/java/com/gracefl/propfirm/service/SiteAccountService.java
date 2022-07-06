package com.gracefl.propfirm.service;

import com.gracefl.propfirm.domain.SiteAccount;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link SiteAccount}.
 */
public interface SiteAccountService {
    /**
     * Save a siteAccount.
     *
     * @param siteAccount the entity to save.
     * @return the persisted entity.
     */
    SiteAccount save(SiteAccount siteAccount);

    /**
     * Updates a siteAccount.
     *
     * @param siteAccount the entity to update.
     * @return the persisted entity.
     */
    SiteAccount update(SiteAccount siteAccount);

    /**
     * Partially updates a siteAccount.
     *
     * @param siteAccount the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SiteAccount> partialUpdate(SiteAccount siteAccount);

    /**
     * Get all the siteAccounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SiteAccount> findAll(Pageable pageable);

    /**
     * Get all the siteAccounts with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SiteAccount> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" siteAccount.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SiteAccount> findOne(Long id);

    /**
     * Delete the "id" siteAccount.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
