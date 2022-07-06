package com.gracefl.propfirm.service.impl;

import com.gracefl.propfirm.domain.TradersChallengeTracker;
import com.gracefl.propfirm.repository.TradersChallengeTrackerRepository;
import com.gracefl.propfirm.service.TradersChallengeTrackerService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link TradersChallengeTracker}.
 */
@Service
@Transactional
public class TradersChallengeTrackerServiceImpl implements TradersChallengeTrackerService {

    private final Logger log = LoggerFactory.getLogger(TradersChallengeTrackerServiceImpl.class);

    private final TradersChallengeTrackerRepository tradersChallengeTrackerRepository;

    public TradersChallengeTrackerServiceImpl(TradersChallengeTrackerRepository tradersChallengeTrackerRepository) {
        this.tradersChallengeTrackerRepository = tradersChallengeTrackerRepository;
    }

    @Override
    public TradersChallengeTracker save(TradersChallengeTracker tradersChallengeTracker) {
        log.debug("Request to save TradersChallengeTracker : {}", tradersChallengeTracker);
        return tradersChallengeTrackerRepository.save(tradersChallengeTracker);
    }

    @Override
    public TradersChallengeTracker update(TradersChallengeTracker tradersChallengeTracker) {
        log.debug("Request to save TradersChallengeTracker : {}", tradersChallengeTracker);
        return tradersChallengeTrackerRepository.save(tradersChallengeTracker);
    }

    @Override
    public Optional<TradersChallengeTracker> partialUpdate(TradersChallengeTracker tradersChallengeTracker) {
        log.debug("Request to partially update TradersChallengeTracker : {}", tradersChallengeTracker);

        return tradersChallengeTrackerRepository
            .findById(tradersChallengeTracker.getId())
            .map(existingTradersChallengeTracker -> {
                if (tradersChallengeTracker.getTradeChallengeName() != null) {
                    existingTradersChallengeTracker.setTradeChallengeName(tradersChallengeTracker.getTradeChallengeName());
                }
                if (tradersChallengeTracker.getStartDate() != null) {
                    existingTradersChallengeTracker.setStartDate(tradersChallengeTracker.getStartDate());
                }
                if (tradersChallengeTracker.getAccountDayStartBalance() != null) {
                    existingTradersChallengeTracker.setAccountDayStartBalance(tradersChallengeTracker.getAccountDayStartBalance());
                }
                if (tradersChallengeTracker.getAccountDayStartEquity() != null) {
                    existingTradersChallengeTracker.setAccountDayStartEquity(tradersChallengeTracker.getAccountDayStartEquity());
                }
                if (tradersChallengeTracker.getRunningMaxTotalDrawdown() != null) {
                    existingTradersChallengeTracker.setRunningMaxTotalDrawdown(tradersChallengeTracker.getRunningMaxTotalDrawdown());
                }
                if (tradersChallengeTracker.getRunningMaxDailyDrawdown() != null) {
                    existingTradersChallengeTracker.setRunningMaxDailyDrawdown(tradersChallengeTracker.getRunningMaxDailyDrawdown());
                }
                if (tradersChallengeTracker.getLowestDrawdownPoint() != null) {
                    existingTradersChallengeTracker.setLowestDrawdownPoint(tradersChallengeTracker.getLowestDrawdownPoint());
                }
                if (tradersChallengeTracker.getRulesViolated() != null) {
                    existingTradersChallengeTracker.setRulesViolated(tradersChallengeTracker.getRulesViolated());
                }
                if (tradersChallengeTracker.getRuleViolated() != null) {
                    existingTradersChallengeTracker.setRuleViolated(tradersChallengeTracker.getRuleViolated());
                }
                if (tradersChallengeTracker.getRuleViolatedDate() != null) {
                    existingTradersChallengeTracker.setRuleViolatedDate(tradersChallengeTracker.getRuleViolatedDate());
                }

                return existingTradersChallengeTracker;
            })
            .map(tradersChallengeTrackerRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TradersChallengeTracker> findAll(Pageable pageable) {
        log.debug("Request to get all TradersChallengeTrackers");
        return tradersChallengeTrackerRepository.findAll(pageable);
    }

    public Page<TradersChallengeTracker> findAllWithEagerRelationships(Pageable pageable) {
        return tradersChallengeTrackerRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TradersChallengeTracker> findOne(Long id) {
        log.debug("Request to get TradersChallengeTracker : {}", id);
        return tradersChallengeTrackerRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TradersChallengeTracker : {}", id);
        tradersChallengeTrackerRepository.deleteById(id);
    }
}
