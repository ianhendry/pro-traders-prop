package com.gracefl.propfirm.repository;

import com.gracefl.propfirm.domain.BillingDetails;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the BillingDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillingDetailsRepository extends JpaRepository<BillingDetails, Long>, JpaSpecificationExecutor<BillingDetails> {}
