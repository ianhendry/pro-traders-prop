package com.gracefl.propfirm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gracefl.propfirm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UserNotificationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserNotification.class);
        UserNotification userNotification1 = new UserNotification();
        userNotification1.setId(1L);
        UserNotification userNotification2 = new UserNotification();
        userNotification2.setId(userNotification1.getId());
        assertThat(userNotification1).isEqualTo(userNotification2);
        userNotification2.setId(2L);
        assertThat(userNotification1).isNotEqualTo(userNotification2);
        userNotification1.setId(null);
        assertThat(userNotification1).isNotEqualTo(userNotification2);
    }
}
