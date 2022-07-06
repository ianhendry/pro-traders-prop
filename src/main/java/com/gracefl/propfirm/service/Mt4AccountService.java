package com.gracefl.propfirm.service;

import com.gracefl.propfirm.domain.Mt4Account;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Mt4Account}.
 */
public interface Mt4AccountService {
    /**
     * Save a mt4Account.
     *
     * @param mt4Account the entity to save.
     * @return the persisted entity.
     */
    Mt4Account save(Mt4Account mt4Account);

    /**
     * Updates a mt4Account.
     *
     * @param mt4Account the entity to update.
     * @return the persisted entity.
     */
    Mt4Account update(Mt4Account mt4Account);

    /**
     * Partially updates a mt4Account.
     *
     * @param mt4Account the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Mt4Account> partialUpdate(Mt4Account mt4Account);

    /**
     * Get all the mt4Accounts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Mt4Account> findAll(Pageable pageable);

    /**
     * Get the "id" mt4Account.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Mt4Account> findOne(Long id);

    /**
     * Delete the "id" mt4Account.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
