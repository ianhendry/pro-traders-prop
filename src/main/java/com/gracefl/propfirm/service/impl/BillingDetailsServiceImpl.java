package com.gracefl.propfirm.service.impl;

import com.gracefl.propfirm.domain.BillingDetails;
import com.gracefl.propfirm.repository.BillingDetailsRepository;
import com.gracefl.propfirm.service.BillingDetailsService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link BillingDetails}.
 */
@Service
@Transactional
public class BillingDetailsServiceImpl implements BillingDetailsService {

    private final Logger log = LoggerFactory.getLogger(BillingDetailsServiceImpl.class);

    private final BillingDetailsRepository billingDetailsRepository;

    public BillingDetailsServiceImpl(BillingDetailsRepository billingDetailsRepository) {
        this.billingDetailsRepository = billingDetailsRepository;
    }

    @Override
    public BillingDetails save(BillingDetails billingDetails) {
        log.debug("Request to save BillingDetails : {}", billingDetails);
        return billingDetailsRepository.save(billingDetails);
    }

    @Override
    public BillingDetails update(BillingDetails billingDetails) {
        log.debug("Request to save BillingDetails : {}", billingDetails);
        return billingDetailsRepository.save(billingDetails);
    }

    @Override
    public Optional<BillingDetails> partialUpdate(BillingDetails billingDetails) {
        log.debug("Request to partially update BillingDetails : {}", billingDetails);

        return billingDetailsRepository
            .findById(billingDetails.getId())
            .map(existingBillingDetails -> {
                if (billingDetails.getContactName() != null) {
                    existingBillingDetails.setContactName(billingDetails.getContactName());
                }
                if (billingDetails.getAddress1() != null) {
                    existingBillingDetails.setAddress1(billingDetails.getAddress1());
                }
                if (billingDetails.getAddress2() != null) {
                    existingBillingDetails.setAddress2(billingDetails.getAddress2());
                }
                if (billingDetails.getAddress3() != null) {
                    existingBillingDetails.setAddress3(billingDetails.getAddress3());
                }
                if (billingDetails.getAddress4() != null) {
                    existingBillingDetails.setAddress4(billingDetails.getAddress4());
                }
                if (billingDetails.getAddress5() != null) {
                    existingBillingDetails.setAddress5(billingDetails.getAddress5());
                }
                if (billingDetails.getAddress6() != null) {
                    existingBillingDetails.setAddress6(billingDetails.getAddress6());
                }
                if (billingDetails.getDialCode() != null) {
                    existingBillingDetails.setDialCode(billingDetails.getDialCode());
                }
                if (billingDetails.getPhoneNumber() != null) {
                    existingBillingDetails.setPhoneNumber(billingDetails.getPhoneNumber());
                }
                if (billingDetails.getMessengerId() != null) {
                    existingBillingDetails.setMessengerId(billingDetails.getMessengerId());
                }
                if (billingDetails.getDateAdded() != null) {
                    existingBillingDetails.setDateAdded(billingDetails.getDateAdded());
                }
                if (billingDetails.getInActive() != null) {
                    existingBillingDetails.setInActive(billingDetails.getInActive());
                }
                if (billingDetails.getInActiveDate() != null) {
                    existingBillingDetails.setInActiveDate(billingDetails.getInActiveDate());
                }

                return existingBillingDetails;
            })
            .map(billingDetailsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BillingDetails> findAll() {
        log.debug("Request to get all BillingDetails");
        return billingDetailsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BillingDetails> findOne(Long id) {
        log.debug("Request to get BillingDetails : {}", id);
        return billingDetailsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BillingDetails : {}", id);
        billingDetailsRepository.deleteById(id);
    }
}
