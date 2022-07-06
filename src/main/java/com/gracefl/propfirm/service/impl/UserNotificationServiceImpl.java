package com.gracefl.propfirm.service.impl;

import com.gracefl.propfirm.domain.UserNotification;
import com.gracefl.propfirm.repository.UserNotificationRepository;
import com.gracefl.propfirm.service.UserNotificationService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link UserNotification}.
 */
@Service
@Transactional
public class UserNotificationServiceImpl implements UserNotificationService {

    private final Logger log = LoggerFactory.getLogger(UserNotificationServiceImpl.class);

    private final UserNotificationRepository userNotificationRepository;

    public UserNotificationServiceImpl(UserNotificationRepository userNotificationRepository) {
        this.userNotificationRepository = userNotificationRepository;
    }

    @Override
    public UserNotification save(UserNotification userNotification) {
        log.debug("Request to save UserNotification : {}", userNotification);
        return userNotificationRepository.save(userNotification);
    }

    @Override
    public UserNotification update(UserNotification userNotification) {
        log.debug("Request to save UserNotification : {}", userNotification);
        return userNotificationRepository.save(userNotification);
    }

    @Override
    public Optional<UserNotification> partialUpdate(UserNotification userNotification) {
        log.debug("Request to partially update UserNotification : {}", userNotification);

        return userNotificationRepository
            .findById(userNotification.getId())
            .map(existingUserNotification -> {
                if (userNotification.getCommentTitle() != null) {
                    existingUserNotification.setCommentTitle(userNotification.getCommentTitle());
                }
                if (userNotification.getCommentBody() != null) {
                    existingUserNotification.setCommentBody(userNotification.getCommentBody());
                }
                if (userNotification.getCommentMedia() != null) {
                    existingUserNotification.setCommentMedia(userNotification.getCommentMedia());
                }
                if (userNotification.getCommentMediaContentType() != null) {
                    existingUserNotification.setCommentMediaContentType(userNotification.getCommentMediaContentType());
                }
                if (userNotification.getDateAdded() != null) {
                    existingUserNotification.setDateAdded(userNotification.getDateAdded());
                }
                if (userNotification.getMakePublicVisibleOnSite() != null) {
                    existingUserNotification.setMakePublicVisibleOnSite(userNotification.getMakePublicVisibleOnSite());
                }

                return existingUserNotification;
            })
            .map(userNotificationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserNotification> findAll(Pageable pageable) {
        log.debug("Request to get all UserNotifications");
        return userNotificationRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserNotification> findOne(Long id) {
        log.debug("Request to get UserNotification : {}", id);
        return userNotificationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserNotification : {}", id);
        userNotificationRepository.deleteById(id);
    }
}
