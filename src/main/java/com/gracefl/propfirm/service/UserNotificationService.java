package com.gracefl.propfirm.service;

import com.gracefl.propfirm.domain.UserNotification;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link UserNotification}.
 */
public interface UserNotificationService {
    /**
     * Save a userNotification.
     *
     * @param userNotification the entity to save.
     * @return the persisted entity.
     */
    UserNotification save(UserNotification userNotification);

    /**
     * Updates a userNotification.
     *
     * @param userNotification the entity to update.
     * @return the persisted entity.
     */
    UserNotification update(UserNotification userNotification);

    /**
     * Partially updates a userNotification.
     *
     * @param userNotification the entity to update partially.
     * @return the persisted entity.
     */
    Optional<UserNotification> partialUpdate(UserNotification userNotification);

    /**
     * Get all the userNotifications.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UserNotification> findAll(Pageable pageable);

    /**
     * Get the "id" userNotification.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserNotification> findOne(Long id);

    /**
     * Delete the "id" userNotification.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
