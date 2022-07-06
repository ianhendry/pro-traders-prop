package com.gracefl.propfirm.service;

import com.gracefl.propfirm.domain.TradersChallengeTracker;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link TradersChallengeTracker}.
 */
public interface TradersChallengeTrackerService {
    /**
     * Save a tradersChallengeTracker.
     *
     * @param tradersChallengeTracker the entity to save.
     * @return the persisted entity.
     */
    TradersChallengeTracker save(TradersChallengeTracker tradersChallengeTracker);

    /**
     * Updates a tradersChallengeTracker.
     *
     * @param tradersChallengeTracker the entity to update.
     * @return the persisted entity.
     */
    TradersChallengeTracker update(TradersChallengeTracker tradersChallengeTracker);

    /**
     * Partially updates a tradersChallengeTracker.
     *
     * @param tradersChallengeTracker the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TradersChallengeTracker> partialUpdate(TradersChallengeTracker tradersChallengeTracker);

    /**
     * Get all the tradersChallengeTrackers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TradersChallengeTracker> findAll(Pageable pageable);

    /**
     * Get all the tradersChallengeTrackers with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TradersChallengeTracker> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" tradersChallengeTracker.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TradersChallengeTracker> findOne(Long id);

    /**
     * Delete the "id" tradersChallengeTracker.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
