package com.gracefl.propfirm.repository;

import com.gracefl.propfirm.domain.UserNotification;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the UserNotification entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, Long>, JpaSpecificationExecutor<UserNotification> {}
