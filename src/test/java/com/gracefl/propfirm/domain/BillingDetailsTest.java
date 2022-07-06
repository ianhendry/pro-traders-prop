package com.gracefl.propfirm.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.gracefl.propfirm.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BillingDetailsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(BillingDetails.class);
        BillingDetails billingDetails1 = new BillingDetails();
        billingDetails1.setId(1L);
        BillingDetails billingDetails2 = new BillingDetails();
        billingDetails2.setId(billingDetails1.getId());
        assertThat(billingDetails1).isEqualTo(billingDetails2);
        billingDetails2.setId(2L);
        assertThat(billingDetails1).isNotEqualTo(billingDetails2);
        billingDetails1.setId(null);
        assertThat(billingDetails1).isNotEqualTo(billingDetails2);
    }
}
