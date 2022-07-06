package com.gracefl.propfirm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gracefl.propfirm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TradersChallengeTrackerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TradersChallengeTracker.class);
        TradersChallengeTracker tradersChallengeTracker1 = new TradersChallengeTracker();
        tradersChallengeTracker1.setId(1L);
        TradersChallengeTracker tradersChallengeTracker2 = new TradersChallengeTracker();
        tradersChallengeTracker2.setId(tradersChallengeTracker1.getId());
        assertThat(tradersChallengeTracker1).isEqualTo(tradersChallengeTracker2);
        tradersChallengeTracker2.setId(2L);
        assertThat(tradersChallengeTracker1).isNotEqualTo(tradersChallengeTracker2);
        tradersChallengeTracker1.setId(null);
        assertThat(tradersChallengeTracker1).isNotEqualTo(tradersChallengeTracker2);
    }
}
