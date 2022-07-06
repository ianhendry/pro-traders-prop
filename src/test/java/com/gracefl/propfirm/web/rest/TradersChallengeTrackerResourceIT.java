package com.gracefl.propfirm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gracefl.propfirm.IntegrationTest;
import com.gracefl.propfirm.domain.ChallengeType;
import com.gracefl.propfirm.domain.Mt4Account;
import com.gracefl.propfirm.domain.TradersChallengeTracker;
import com.gracefl.propfirm.domain.User;
import com.gracefl.propfirm.domain.UserNotification;
import com.gracefl.propfirm.repository.TradersChallengeTrackerRepository;
import com.gracefl.propfirm.service.TradersChallengeTrackerService;
import com.gracefl.propfirm.service.criteria.TradersChallengeTrackerCriteria;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TradersChallengeTrackerResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class TradersChallengeTrackerResourceIT {

    private static final String DEFAULT_TRADE_CHALLENGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_TRADE_CHALLENGE_NAME = "BBBBBBBBBB";

    private static final Instant DEFAULT_START_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_ACCOUNT_DAY_START_BALANCE = 1D;
    private static final Double UPDATED_ACCOUNT_DAY_START_BALANCE = 2D;
    private static final Double SMALLER_ACCOUNT_DAY_START_BALANCE = 1D - 1D;

    private static final Double DEFAULT_ACCOUNT_DAY_START_EQUITY = 1D;
    private static final Double UPDATED_ACCOUNT_DAY_START_EQUITY = 2D;
    private static final Double SMALLER_ACCOUNT_DAY_START_EQUITY = 1D - 1D;

    private static final Double DEFAULT_RUNNING_MAX_TOTAL_DRAWDOWN = 1D;
    private static final Double UPDATED_RUNNING_MAX_TOTAL_DRAWDOWN = 2D;
    private static final Double SMALLER_RUNNING_MAX_TOTAL_DRAWDOWN = 1D - 1D;

    private static final Double DEFAULT_RUNNING_MAX_DAILY_DRAWDOWN = 1D;
    private static final Double UPDATED_RUNNING_MAX_DAILY_DRAWDOWN = 2D;
    private static final Double SMALLER_RUNNING_MAX_DAILY_DRAWDOWN = 1D - 1D;

    private static final Double DEFAULT_LOWEST_DRAWDOWN_POINT = 1D;
    private static final Double UPDATED_LOWEST_DRAWDOWN_POINT = 2D;
    private static final Double SMALLER_LOWEST_DRAWDOWN_POINT = 1D - 1D;

    private static final Boolean DEFAULT_RULES_VIOLATED = false;
    private static final Boolean UPDATED_RULES_VIOLATED = true;

    private static final String DEFAULT_RULE_VIOLATED = "AAAAAAAAAA";
    private static final String UPDATED_RULE_VIOLATED = "BBBBBBBBBB";

    private static final Instant DEFAULT_RULE_VIOLATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_RULE_VIOLATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/traders-challenge-trackers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private TradersChallengeTrackerRepository tradersChallengeTrackerRepository;

    @Mock
    private TradersChallengeTrackerRepository tradersChallengeTrackerRepositoryMock;

    @Mock
    private TradersChallengeTrackerService tradersChallengeTrackerServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTradersChallengeTrackerMockMvc;

    private TradersChallengeTracker tradersChallengeTracker;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TradersChallengeTracker createEntity(EntityManager em) {
        TradersChallengeTracker tradersChallengeTracker = new TradersChallengeTracker()
            .tradeChallengeName(DEFAULT_TRADE_CHALLENGE_NAME)
            .startDate(DEFAULT_START_DATE)
            .accountDayStartBalance(DEFAULT_ACCOUNT_DAY_START_BALANCE)
            .accountDayStartEquity(DEFAULT_ACCOUNT_DAY_START_EQUITY)
            .runningMaxTotalDrawdown(DEFAULT_RUNNING_MAX_TOTAL_DRAWDOWN)
            .runningMaxDailyDrawdown(DEFAULT_RUNNING_MAX_DAILY_DRAWDOWN)
            .lowestDrawdownPoint(DEFAULT_LOWEST_DRAWDOWN_POINT)
            .rulesViolated(DEFAULT_RULES_VIOLATED)
            .ruleViolated(DEFAULT_RULE_VIOLATED)
            .ruleViolatedDate(DEFAULT_RULE_VIOLATED_DATE);
        return tradersChallengeTracker;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TradersChallengeTracker createUpdatedEntity(EntityManager em) {
        TradersChallengeTracker tradersChallengeTracker = new TradersChallengeTracker()
            .tradeChallengeName(UPDATED_TRADE_CHALLENGE_NAME)
            .startDate(UPDATED_START_DATE)
            .accountDayStartBalance(UPDATED_ACCOUNT_DAY_START_BALANCE)
            .accountDayStartEquity(UPDATED_ACCOUNT_DAY_START_EQUITY)
            .runningMaxTotalDrawdown(UPDATED_RUNNING_MAX_TOTAL_DRAWDOWN)
            .runningMaxDailyDrawdown(UPDATED_RUNNING_MAX_DAILY_DRAWDOWN)
            .lowestDrawdownPoint(UPDATED_LOWEST_DRAWDOWN_POINT)
            .rulesViolated(UPDATED_RULES_VIOLATED)
            .ruleViolated(UPDATED_RULE_VIOLATED)
            .ruleViolatedDate(UPDATED_RULE_VIOLATED_DATE);
        return tradersChallengeTracker;
    }

    @BeforeEach
    public void initTest() {
        tradersChallengeTracker = createEntity(em);
    }

    @Test
    @Transactional
    void createTradersChallengeTracker() throws Exception {
        int databaseSizeBeforeCreate = tradersChallengeTrackerRepository.findAll().size();
        // Create the TradersChallengeTracker
        restTradersChallengeTrackerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tradersChallengeTracker))
            )
            .andExpect(status().isCreated());

        // Validate the TradersChallengeTracker in the database
        List<TradersChallengeTracker> tradersChallengeTrackerList = tradersChallengeTrackerRepository.findAll();
        assertThat(tradersChallengeTrackerList).hasSize(databaseSizeBeforeCreate + 1);
        TradersChallengeTracker testTradersChallengeTracker = tradersChallengeTrackerList.get(tradersChallengeTrackerList.size() - 1);
        assertThat(testTradersChallengeTracker.getTradeChallengeName()).isEqualTo(DEFAULT_TRADE_CHALLENGE_NAME);
        assertThat(testTradersChallengeTracker.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTradersChallengeTracker.getAccountDayStartBalance()).isEqualTo(DEFAULT_ACCOUNT_DAY_START_BALANCE);
        assertThat(testTradersChallengeTracker.getAccountDayStartEquity()).isEqualTo(DEFAULT_ACCOUNT_DAY_START_EQUITY);
        assertThat(testTradersChallengeTracker.getRunningMaxTotalDrawdown()).isEqualTo(DEFAULT_RUNNING_MAX_TOTAL_DRAWDOWN);
        assertThat(testTradersChallengeTracker.getRunningMaxDailyDrawdown()).isEqualTo(DEFAULT_RUNNING_MAX_DAILY_DRAWDOWN);
        assertThat(testTradersChallengeTracker.getLowestDrawdownPoint()).isEqualTo(DEFAULT_LOWEST_DRAWDOWN_POINT);
        assertThat(testTradersChallengeTracker.getRulesViolated()).isEqualTo(DEFAULT_RULES_VIOLATED);
        assertThat(testTradersChallengeTracker.getRuleViolated()).isEqualTo(DEFAULT_RULE_VIOLATED);
        assertThat(testTradersChallengeTracker.getRuleViolatedDate()).isEqualTo(DEFAULT_RULE_VIOLATED_DATE);
    }

    @Test
    @Transactional
    void createTradersChallengeTrackerWithExistingId() throws Exception {
        // Create the TradersChallengeTracker with an existing ID
        tradersChallengeTracker.setId(1L);

        int databaseSizeBeforeCreate = tradersChallengeTrackerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTradersChallengeTrackerMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tradersChallengeTracker))
            )
            .andExpect(status().isBadRequest());

        // Validate the TradersChallengeTracker in the database
        List<TradersChallengeTracker> tradersChallengeTrackerList = tradersChallengeTrackerRepository.findAll();
        assertThat(tradersChallengeTrackerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackers() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList
        restTradersChallengeTrackerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tradersChallengeTracker.getId().intValue())))
            .andExpect(jsonPath("$.[*].tradeChallengeName").value(hasItem(DEFAULT_TRADE_CHALLENGE_NAME)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].accountDayStartBalance").value(hasItem(DEFAULT_ACCOUNT_DAY_START_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].accountDayStartEquity").value(hasItem(DEFAULT_ACCOUNT_DAY_START_EQUITY.doubleValue())))
            .andExpect(jsonPath("$.[*].runningMaxTotalDrawdown").value(hasItem(DEFAULT_RUNNING_MAX_TOTAL_DRAWDOWN.doubleValue())))
            .andExpect(jsonPath("$.[*].runningMaxDailyDrawdown").value(hasItem(DEFAULT_RUNNING_MAX_DAILY_DRAWDOWN.doubleValue())))
            .andExpect(jsonPath("$.[*].lowestDrawdownPoint").value(hasItem(DEFAULT_LOWEST_DRAWDOWN_POINT.doubleValue())))
            .andExpect(jsonPath("$.[*].rulesViolated").value(hasItem(DEFAULT_RULES_VIOLATED.booleanValue())))
            .andExpect(jsonPath("$.[*].ruleViolated").value(hasItem(DEFAULT_RULE_VIOLATED)))
            .andExpect(jsonPath("$.[*].ruleViolatedDate").value(hasItem(DEFAULT_RULE_VIOLATED_DATE.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTradersChallengeTrackersWithEagerRelationshipsIsEnabled() throws Exception {
        when(tradersChallengeTrackerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTradersChallengeTrackerMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tradersChallengeTrackerServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllTradersChallengeTrackersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(tradersChallengeTrackerServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restTradersChallengeTrackerMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(tradersChallengeTrackerServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getTradersChallengeTracker() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get the tradersChallengeTracker
        restTradersChallengeTrackerMockMvc
            .perform(get(ENTITY_API_URL_ID, tradersChallengeTracker.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tradersChallengeTracker.getId().intValue()))
            .andExpect(jsonPath("$.tradeChallengeName").value(DEFAULT_TRADE_CHALLENGE_NAME))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.accountDayStartBalance").value(DEFAULT_ACCOUNT_DAY_START_BALANCE.doubleValue()))
            .andExpect(jsonPath("$.accountDayStartEquity").value(DEFAULT_ACCOUNT_DAY_START_EQUITY.doubleValue()))
            .andExpect(jsonPath("$.runningMaxTotalDrawdown").value(DEFAULT_RUNNING_MAX_TOTAL_DRAWDOWN.doubleValue()))
            .andExpect(jsonPath("$.runningMaxDailyDrawdown").value(DEFAULT_RUNNING_MAX_DAILY_DRAWDOWN.doubleValue()))
            .andExpect(jsonPath("$.lowestDrawdownPoint").value(DEFAULT_LOWEST_DRAWDOWN_POINT.doubleValue()))
            .andExpect(jsonPath("$.rulesViolated").value(DEFAULT_RULES_VIOLATED.booleanValue()))
            .andExpect(jsonPath("$.ruleViolated").value(DEFAULT_RULE_VIOLATED))
            .andExpect(jsonPath("$.ruleViolatedDate").value(DEFAULT_RULE_VIOLATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getTradersChallengeTrackersByIdFiltering() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        Long id = tradersChallengeTracker.getId();

        defaultTradersChallengeTrackerShouldBeFound("id.equals=" + id);
        defaultTradersChallengeTrackerShouldNotBeFound("id.notEquals=" + id);

        defaultTradersChallengeTrackerShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTradersChallengeTrackerShouldNotBeFound("id.greaterThan=" + id);

        defaultTradersChallengeTrackerShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTradersChallengeTrackerShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByTradeChallengeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where tradeChallengeName equals to DEFAULT_TRADE_CHALLENGE_NAME
        defaultTradersChallengeTrackerShouldBeFound("tradeChallengeName.equals=" + DEFAULT_TRADE_CHALLENGE_NAME);

        // Get all the tradersChallengeTrackerList where tradeChallengeName equals to UPDATED_TRADE_CHALLENGE_NAME
        defaultTradersChallengeTrackerShouldNotBeFound("tradeChallengeName.equals=" + UPDATED_TRADE_CHALLENGE_NAME);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByTradeChallengeNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where tradeChallengeName not equals to DEFAULT_TRADE_CHALLENGE_NAME
        defaultTradersChallengeTrackerShouldNotBeFound("tradeChallengeName.notEquals=" + DEFAULT_TRADE_CHALLENGE_NAME);

        // Get all the tradersChallengeTrackerList where tradeChallengeName not equals to UPDATED_TRADE_CHALLENGE_NAME
        defaultTradersChallengeTrackerShouldBeFound("tradeChallengeName.notEquals=" + UPDATED_TRADE_CHALLENGE_NAME);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByTradeChallengeNameIsInShouldWork() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where tradeChallengeName in DEFAULT_TRADE_CHALLENGE_NAME or UPDATED_TRADE_CHALLENGE_NAME
        defaultTradersChallengeTrackerShouldBeFound(
            "tradeChallengeName.in=" + DEFAULT_TRADE_CHALLENGE_NAME + "," + UPDATED_TRADE_CHALLENGE_NAME
        );

        // Get all the tradersChallengeTrackerList where tradeChallengeName equals to UPDATED_TRADE_CHALLENGE_NAME
        defaultTradersChallengeTrackerShouldNotBeFound("tradeChallengeName.in=" + UPDATED_TRADE_CHALLENGE_NAME);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByTradeChallengeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where tradeChallengeName is not null
        defaultTradersChallengeTrackerShouldBeFound("tradeChallengeName.specified=true");

        // Get all the tradersChallengeTrackerList where tradeChallengeName is null
        defaultTradersChallengeTrackerShouldNotBeFound("tradeChallengeName.specified=false");
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByTradeChallengeNameContainsSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where tradeChallengeName contains DEFAULT_TRADE_CHALLENGE_NAME
        defaultTradersChallengeTrackerShouldBeFound("tradeChallengeName.contains=" + DEFAULT_TRADE_CHALLENGE_NAME);

        // Get all the tradersChallengeTrackerList where tradeChallengeName contains UPDATED_TRADE_CHALLENGE_NAME
        defaultTradersChallengeTrackerShouldNotBeFound("tradeChallengeName.contains=" + UPDATED_TRADE_CHALLENGE_NAME);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByTradeChallengeNameNotContainsSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where tradeChallengeName does not contain DEFAULT_TRADE_CHALLENGE_NAME
        defaultTradersChallengeTrackerShouldNotBeFound("tradeChallengeName.doesNotContain=" + DEFAULT_TRADE_CHALLENGE_NAME);

        // Get all the tradersChallengeTrackerList where tradeChallengeName does not contain UPDATED_TRADE_CHALLENGE_NAME
        defaultTradersChallengeTrackerShouldBeFound("tradeChallengeName.doesNotContain=" + UPDATED_TRADE_CHALLENGE_NAME);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where startDate equals to DEFAULT_START_DATE
        defaultTradersChallengeTrackerShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the tradersChallengeTrackerList where startDate equals to UPDATED_START_DATE
        defaultTradersChallengeTrackerShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByStartDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where startDate not equals to DEFAULT_START_DATE
        defaultTradersChallengeTrackerShouldNotBeFound("startDate.notEquals=" + DEFAULT_START_DATE);

        // Get all the tradersChallengeTrackerList where startDate not equals to UPDATED_START_DATE
        defaultTradersChallengeTrackerShouldBeFound("startDate.notEquals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultTradersChallengeTrackerShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the tradersChallengeTrackerList where startDate equals to UPDATED_START_DATE
        defaultTradersChallengeTrackerShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where startDate is not null
        defaultTradersChallengeTrackerShouldBeFound("startDate.specified=true");

        // Get all the tradersChallengeTrackerList where startDate is null
        defaultTradersChallengeTrackerShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByAccountDayStartBalanceIsEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where accountDayStartBalance equals to DEFAULT_ACCOUNT_DAY_START_BALANCE
        defaultTradersChallengeTrackerShouldBeFound("accountDayStartBalance.equals=" + DEFAULT_ACCOUNT_DAY_START_BALANCE);

        // Get all the tradersChallengeTrackerList where accountDayStartBalance equals to UPDATED_ACCOUNT_DAY_START_BALANCE
        defaultTradersChallengeTrackerShouldNotBeFound("accountDayStartBalance.equals=" + UPDATED_ACCOUNT_DAY_START_BALANCE);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByAccountDayStartBalanceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where accountDayStartBalance not equals to DEFAULT_ACCOUNT_DAY_START_BALANCE
        defaultTradersChallengeTrackerShouldNotBeFound("accountDayStartBalance.notEquals=" + DEFAULT_ACCOUNT_DAY_START_BALANCE);

        // Get all the tradersChallengeTrackerList where accountDayStartBalance not equals to UPDATED_ACCOUNT_DAY_START_BALANCE
        defaultTradersChallengeTrackerShouldBeFound("accountDayStartBalance.notEquals=" + UPDATED_ACCOUNT_DAY_START_BALANCE);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByAccountDayStartBalanceIsInShouldWork() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where accountDayStartBalance in DEFAULT_ACCOUNT_DAY_START_BALANCE or UPDATED_ACCOUNT_DAY_START_BALANCE
        defaultTradersChallengeTrackerShouldBeFound(
            "accountDayStartBalance.in=" + DEFAULT_ACCOUNT_DAY_START_BALANCE + "," + UPDATED_ACCOUNT_DAY_START_BALANCE
        );

        // Get all the tradersChallengeTrackerList where accountDayStartBalance equals to UPDATED_ACCOUNT_DAY_START_BALANCE
        defaultTradersChallengeTrackerShouldNotBeFound("accountDayStartBalance.in=" + UPDATED_ACCOUNT_DAY_START_BALANCE);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByAccountDayStartBalanceIsNullOrNotNull() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where accountDayStartBalance is not null
        defaultTradersChallengeTrackerShouldBeFound("accountDayStartBalance.specified=true");

        // Get all the tradersChallengeTrackerList where accountDayStartBalance is null
        defaultTradersChallengeTrackerShouldNotBeFound("accountDayStartBalance.specified=false");
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByAccountDayStartBalanceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where accountDayStartBalance is greater than or equal to DEFAULT_ACCOUNT_DAY_START_BALANCE
        defaultTradersChallengeTrackerShouldBeFound("accountDayStartBalance.greaterThanOrEqual=" + DEFAULT_ACCOUNT_DAY_START_BALANCE);

        // Get all the tradersChallengeTrackerList where accountDayStartBalance is greater than or equal to UPDATED_ACCOUNT_DAY_START_BALANCE
        defaultTradersChallengeTrackerShouldNotBeFound("accountDayStartBalance.greaterThanOrEqual=" + UPDATED_ACCOUNT_DAY_START_BALANCE);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByAccountDayStartBalanceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where accountDayStartBalance is less than or equal to DEFAULT_ACCOUNT_DAY_START_BALANCE
        defaultTradersChallengeTrackerShouldBeFound("accountDayStartBalance.lessThanOrEqual=" + DEFAULT_ACCOUNT_DAY_START_BALANCE);

        // Get all the tradersChallengeTrackerList where accountDayStartBalance is less than or equal to SMALLER_ACCOUNT_DAY_START_BALANCE
        defaultTradersChallengeTrackerShouldNotBeFound("accountDayStartBalance.lessThanOrEqual=" + SMALLER_ACCOUNT_DAY_START_BALANCE);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByAccountDayStartBalanceIsLessThanSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where accountDayStartBalance is less than DEFAULT_ACCOUNT_DAY_START_BALANCE
        defaultTradersChallengeTrackerShouldNotBeFound("accountDayStartBalance.lessThan=" + DEFAULT_ACCOUNT_DAY_START_BALANCE);

        // Get all the tradersChallengeTrackerList where accountDayStartBalance is less than UPDATED_ACCOUNT_DAY_START_BALANCE
        defaultTradersChallengeTrackerShouldBeFound("accountDayStartBalance.lessThan=" + UPDATED_ACCOUNT_DAY_START_BALANCE);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByAccountDayStartBalanceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where accountDayStartBalance is greater than DEFAULT_ACCOUNT_DAY_START_BALANCE
        defaultTradersChallengeTrackerShouldNotBeFound("accountDayStartBalance.greaterThan=" + DEFAULT_ACCOUNT_DAY_START_BALANCE);

        // Get all the tradersChallengeTrackerList where accountDayStartBalance is greater than SMALLER_ACCOUNT_DAY_START_BALANCE
        defaultTradersChallengeTrackerShouldBeFound("accountDayStartBalance.greaterThan=" + SMALLER_ACCOUNT_DAY_START_BALANCE);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByAccountDayStartEquityIsEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where accountDayStartEquity equals to DEFAULT_ACCOUNT_DAY_START_EQUITY
        defaultTradersChallengeTrackerShouldBeFound("accountDayStartEquity.equals=" + DEFAULT_ACCOUNT_DAY_START_EQUITY);

        // Get all the tradersChallengeTrackerList where accountDayStartEquity equals to UPDATED_ACCOUNT_DAY_START_EQUITY
        defaultTradersChallengeTrackerShouldNotBeFound("accountDayStartEquity.equals=" + UPDATED_ACCOUNT_DAY_START_EQUITY);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByAccountDayStartEquityIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where accountDayStartEquity not equals to DEFAULT_ACCOUNT_DAY_START_EQUITY
        defaultTradersChallengeTrackerShouldNotBeFound("accountDayStartEquity.notEquals=" + DEFAULT_ACCOUNT_DAY_START_EQUITY);

        // Get all the tradersChallengeTrackerList where accountDayStartEquity not equals to UPDATED_ACCOUNT_DAY_START_EQUITY
        defaultTradersChallengeTrackerShouldBeFound("accountDayStartEquity.notEquals=" + UPDATED_ACCOUNT_DAY_START_EQUITY);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByAccountDayStartEquityIsInShouldWork() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where accountDayStartEquity in DEFAULT_ACCOUNT_DAY_START_EQUITY or UPDATED_ACCOUNT_DAY_START_EQUITY
        defaultTradersChallengeTrackerShouldBeFound(
            "accountDayStartEquity.in=" + DEFAULT_ACCOUNT_DAY_START_EQUITY + "," + UPDATED_ACCOUNT_DAY_START_EQUITY
        );

        // Get all the tradersChallengeTrackerList where accountDayStartEquity equals to UPDATED_ACCOUNT_DAY_START_EQUITY
        defaultTradersChallengeTrackerShouldNotBeFound("accountDayStartEquity.in=" + UPDATED_ACCOUNT_DAY_START_EQUITY);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByAccountDayStartEquityIsNullOrNotNull() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where accountDayStartEquity is not null
        defaultTradersChallengeTrackerShouldBeFound("accountDayStartEquity.specified=true");

        // Get all the tradersChallengeTrackerList where accountDayStartEquity is null
        defaultTradersChallengeTrackerShouldNotBeFound("accountDayStartEquity.specified=false");
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByAccountDayStartEquityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where accountDayStartEquity is greater than or equal to DEFAULT_ACCOUNT_DAY_START_EQUITY
        defaultTradersChallengeTrackerShouldBeFound("accountDayStartEquity.greaterThanOrEqual=" + DEFAULT_ACCOUNT_DAY_START_EQUITY);

        // Get all the tradersChallengeTrackerList where accountDayStartEquity is greater than or equal to UPDATED_ACCOUNT_DAY_START_EQUITY
        defaultTradersChallengeTrackerShouldNotBeFound("accountDayStartEquity.greaterThanOrEqual=" + UPDATED_ACCOUNT_DAY_START_EQUITY);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByAccountDayStartEquityIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where accountDayStartEquity is less than or equal to DEFAULT_ACCOUNT_DAY_START_EQUITY
        defaultTradersChallengeTrackerShouldBeFound("accountDayStartEquity.lessThanOrEqual=" + DEFAULT_ACCOUNT_DAY_START_EQUITY);

        // Get all the tradersChallengeTrackerList where accountDayStartEquity is less than or equal to SMALLER_ACCOUNT_DAY_START_EQUITY
        defaultTradersChallengeTrackerShouldNotBeFound("accountDayStartEquity.lessThanOrEqual=" + SMALLER_ACCOUNT_DAY_START_EQUITY);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByAccountDayStartEquityIsLessThanSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where accountDayStartEquity is less than DEFAULT_ACCOUNT_DAY_START_EQUITY
        defaultTradersChallengeTrackerShouldNotBeFound("accountDayStartEquity.lessThan=" + DEFAULT_ACCOUNT_DAY_START_EQUITY);

        // Get all the tradersChallengeTrackerList where accountDayStartEquity is less than UPDATED_ACCOUNT_DAY_START_EQUITY
        defaultTradersChallengeTrackerShouldBeFound("accountDayStartEquity.lessThan=" + UPDATED_ACCOUNT_DAY_START_EQUITY);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByAccountDayStartEquityIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where accountDayStartEquity is greater than DEFAULT_ACCOUNT_DAY_START_EQUITY
        defaultTradersChallengeTrackerShouldNotBeFound("accountDayStartEquity.greaterThan=" + DEFAULT_ACCOUNT_DAY_START_EQUITY);

        // Get all the tradersChallengeTrackerList where accountDayStartEquity is greater than SMALLER_ACCOUNT_DAY_START_EQUITY
        defaultTradersChallengeTrackerShouldBeFound("accountDayStartEquity.greaterThan=" + SMALLER_ACCOUNT_DAY_START_EQUITY);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRunningMaxTotalDrawdownIsEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where runningMaxTotalDrawdown equals to DEFAULT_RUNNING_MAX_TOTAL_DRAWDOWN
        defaultTradersChallengeTrackerShouldBeFound("runningMaxTotalDrawdown.equals=" + DEFAULT_RUNNING_MAX_TOTAL_DRAWDOWN);

        // Get all the tradersChallengeTrackerList where runningMaxTotalDrawdown equals to UPDATED_RUNNING_MAX_TOTAL_DRAWDOWN
        defaultTradersChallengeTrackerShouldNotBeFound("runningMaxTotalDrawdown.equals=" + UPDATED_RUNNING_MAX_TOTAL_DRAWDOWN);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRunningMaxTotalDrawdownIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where runningMaxTotalDrawdown not equals to DEFAULT_RUNNING_MAX_TOTAL_DRAWDOWN
        defaultTradersChallengeTrackerShouldNotBeFound("runningMaxTotalDrawdown.notEquals=" + DEFAULT_RUNNING_MAX_TOTAL_DRAWDOWN);

        // Get all the tradersChallengeTrackerList where runningMaxTotalDrawdown not equals to UPDATED_RUNNING_MAX_TOTAL_DRAWDOWN
        defaultTradersChallengeTrackerShouldBeFound("runningMaxTotalDrawdown.notEquals=" + UPDATED_RUNNING_MAX_TOTAL_DRAWDOWN);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRunningMaxTotalDrawdownIsInShouldWork() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where runningMaxTotalDrawdown in DEFAULT_RUNNING_MAX_TOTAL_DRAWDOWN or UPDATED_RUNNING_MAX_TOTAL_DRAWDOWN
        defaultTradersChallengeTrackerShouldBeFound(
            "runningMaxTotalDrawdown.in=" + DEFAULT_RUNNING_MAX_TOTAL_DRAWDOWN + "," + UPDATED_RUNNING_MAX_TOTAL_DRAWDOWN
        );

        // Get all the tradersChallengeTrackerList where runningMaxTotalDrawdown equals to UPDATED_RUNNING_MAX_TOTAL_DRAWDOWN
        defaultTradersChallengeTrackerShouldNotBeFound("runningMaxTotalDrawdown.in=" + UPDATED_RUNNING_MAX_TOTAL_DRAWDOWN);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRunningMaxTotalDrawdownIsNullOrNotNull() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where runningMaxTotalDrawdown is not null
        defaultTradersChallengeTrackerShouldBeFound("runningMaxTotalDrawdown.specified=true");

        // Get all the tradersChallengeTrackerList where runningMaxTotalDrawdown is null
        defaultTradersChallengeTrackerShouldNotBeFound("runningMaxTotalDrawdown.specified=false");
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRunningMaxTotalDrawdownIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where runningMaxTotalDrawdown is greater than or equal to DEFAULT_RUNNING_MAX_TOTAL_DRAWDOWN
        defaultTradersChallengeTrackerShouldBeFound("runningMaxTotalDrawdown.greaterThanOrEqual=" + DEFAULT_RUNNING_MAX_TOTAL_DRAWDOWN);

        // Get all the tradersChallengeTrackerList where runningMaxTotalDrawdown is greater than or equal to UPDATED_RUNNING_MAX_TOTAL_DRAWDOWN
        defaultTradersChallengeTrackerShouldNotBeFound("runningMaxTotalDrawdown.greaterThanOrEqual=" + UPDATED_RUNNING_MAX_TOTAL_DRAWDOWN);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRunningMaxTotalDrawdownIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where runningMaxTotalDrawdown is less than or equal to DEFAULT_RUNNING_MAX_TOTAL_DRAWDOWN
        defaultTradersChallengeTrackerShouldBeFound("runningMaxTotalDrawdown.lessThanOrEqual=" + DEFAULT_RUNNING_MAX_TOTAL_DRAWDOWN);

        // Get all the tradersChallengeTrackerList where runningMaxTotalDrawdown is less than or equal to SMALLER_RUNNING_MAX_TOTAL_DRAWDOWN
        defaultTradersChallengeTrackerShouldNotBeFound("runningMaxTotalDrawdown.lessThanOrEqual=" + SMALLER_RUNNING_MAX_TOTAL_DRAWDOWN);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRunningMaxTotalDrawdownIsLessThanSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where runningMaxTotalDrawdown is less than DEFAULT_RUNNING_MAX_TOTAL_DRAWDOWN
        defaultTradersChallengeTrackerShouldNotBeFound("runningMaxTotalDrawdown.lessThan=" + DEFAULT_RUNNING_MAX_TOTAL_DRAWDOWN);

        // Get all the tradersChallengeTrackerList where runningMaxTotalDrawdown is less than UPDATED_RUNNING_MAX_TOTAL_DRAWDOWN
        defaultTradersChallengeTrackerShouldBeFound("runningMaxTotalDrawdown.lessThan=" + UPDATED_RUNNING_MAX_TOTAL_DRAWDOWN);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRunningMaxTotalDrawdownIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where runningMaxTotalDrawdown is greater than DEFAULT_RUNNING_MAX_TOTAL_DRAWDOWN
        defaultTradersChallengeTrackerShouldNotBeFound("runningMaxTotalDrawdown.greaterThan=" + DEFAULT_RUNNING_MAX_TOTAL_DRAWDOWN);

        // Get all the tradersChallengeTrackerList where runningMaxTotalDrawdown is greater than SMALLER_RUNNING_MAX_TOTAL_DRAWDOWN
        defaultTradersChallengeTrackerShouldBeFound("runningMaxTotalDrawdown.greaterThan=" + SMALLER_RUNNING_MAX_TOTAL_DRAWDOWN);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRunningMaxDailyDrawdownIsEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where runningMaxDailyDrawdown equals to DEFAULT_RUNNING_MAX_DAILY_DRAWDOWN
        defaultTradersChallengeTrackerShouldBeFound("runningMaxDailyDrawdown.equals=" + DEFAULT_RUNNING_MAX_DAILY_DRAWDOWN);

        // Get all the tradersChallengeTrackerList where runningMaxDailyDrawdown equals to UPDATED_RUNNING_MAX_DAILY_DRAWDOWN
        defaultTradersChallengeTrackerShouldNotBeFound("runningMaxDailyDrawdown.equals=" + UPDATED_RUNNING_MAX_DAILY_DRAWDOWN);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRunningMaxDailyDrawdownIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where runningMaxDailyDrawdown not equals to DEFAULT_RUNNING_MAX_DAILY_DRAWDOWN
        defaultTradersChallengeTrackerShouldNotBeFound("runningMaxDailyDrawdown.notEquals=" + DEFAULT_RUNNING_MAX_DAILY_DRAWDOWN);

        // Get all the tradersChallengeTrackerList where runningMaxDailyDrawdown not equals to UPDATED_RUNNING_MAX_DAILY_DRAWDOWN
        defaultTradersChallengeTrackerShouldBeFound("runningMaxDailyDrawdown.notEquals=" + UPDATED_RUNNING_MAX_DAILY_DRAWDOWN);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRunningMaxDailyDrawdownIsInShouldWork() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where runningMaxDailyDrawdown in DEFAULT_RUNNING_MAX_DAILY_DRAWDOWN or UPDATED_RUNNING_MAX_DAILY_DRAWDOWN
        defaultTradersChallengeTrackerShouldBeFound(
            "runningMaxDailyDrawdown.in=" + DEFAULT_RUNNING_MAX_DAILY_DRAWDOWN + "," + UPDATED_RUNNING_MAX_DAILY_DRAWDOWN
        );

        // Get all the tradersChallengeTrackerList where runningMaxDailyDrawdown equals to UPDATED_RUNNING_MAX_DAILY_DRAWDOWN
        defaultTradersChallengeTrackerShouldNotBeFound("runningMaxDailyDrawdown.in=" + UPDATED_RUNNING_MAX_DAILY_DRAWDOWN);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRunningMaxDailyDrawdownIsNullOrNotNull() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where runningMaxDailyDrawdown is not null
        defaultTradersChallengeTrackerShouldBeFound("runningMaxDailyDrawdown.specified=true");

        // Get all the tradersChallengeTrackerList where runningMaxDailyDrawdown is null
        defaultTradersChallengeTrackerShouldNotBeFound("runningMaxDailyDrawdown.specified=false");
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRunningMaxDailyDrawdownIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where runningMaxDailyDrawdown is greater than or equal to DEFAULT_RUNNING_MAX_DAILY_DRAWDOWN
        defaultTradersChallengeTrackerShouldBeFound("runningMaxDailyDrawdown.greaterThanOrEqual=" + DEFAULT_RUNNING_MAX_DAILY_DRAWDOWN);

        // Get all the tradersChallengeTrackerList where runningMaxDailyDrawdown is greater than or equal to UPDATED_RUNNING_MAX_DAILY_DRAWDOWN
        defaultTradersChallengeTrackerShouldNotBeFound("runningMaxDailyDrawdown.greaterThanOrEqual=" + UPDATED_RUNNING_MAX_DAILY_DRAWDOWN);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRunningMaxDailyDrawdownIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where runningMaxDailyDrawdown is less than or equal to DEFAULT_RUNNING_MAX_DAILY_DRAWDOWN
        defaultTradersChallengeTrackerShouldBeFound("runningMaxDailyDrawdown.lessThanOrEqual=" + DEFAULT_RUNNING_MAX_DAILY_DRAWDOWN);

        // Get all the tradersChallengeTrackerList where runningMaxDailyDrawdown is less than or equal to SMALLER_RUNNING_MAX_DAILY_DRAWDOWN
        defaultTradersChallengeTrackerShouldNotBeFound("runningMaxDailyDrawdown.lessThanOrEqual=" + SMALLER_RUNNING_MAX_DAILY_DRAWDOWN);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRunningMaxDailyDrawdownIsLessThanSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where runningMaxDailyDrawdown is less than DEFAULT_RUNNING_MAX_DAILY_DRAWDOWN
        defaultTradersChallengeTrackerShouldNotBeFound("runningMaxDailyDrawdown.lessThan=" + DEFAULT_RUNNING_MAX_DAILY_DRAWDOWN);

        // Get all the tradersChallengeTrackerList where runningMaxDailyDrawdown is less than UPDATED_RUNNING_MAX_DAILY_DRAWDOWN
        defaultTradersChallengeTrackerShouldBeFound("runningMaxDailyDrawdown.lessThan=" + UPDATED_RUNNING_MAX_DAILY_DRAWDOWN);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRunningMaxDailyDrawdownIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where runningMaxDailyDrawdown is greater than DEFAULT_RUNNING_MAX_DAILY_DRAWDOWN
        defaultTradersChallengeTrackerShouldNotBeFound("runningMaxDailyDrawdown.greaterThan=" + DEFAULT_RUNNING_MAX_DAILY_DRAWDOWN);

        // Get all the tradersChallengeTrackerList where runningMaxDailyDrawdown is greater than SMALLER_RUNNING_MAX_DAILY_DRAWDOWN
        defaultTradersChallengeTrackerShouldBeFound("runningMaxDailyDrawdown.greaterThan=" + SMALLER_RUNNING_MAX_DAILY_DRAWDOWN);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByLowestDrawdownPointIsEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where lowestDrawdownPoint equals to DEFAULT_LOWEST_DRAWDOWN_POINT
        defaultTradersChallengeTrackerShouldBeFound("lowestDrawdownPoint.equals=" + DEFAULT_LOWEST_DRAWDOWN_POINT);

        // Get all the tradersChallengeTrackerList where lowestDrawdownPoint equals to UPDATED_LOWEST_DRAWDOWN_POINT
        defaultTradersChallengeTrackerShouldNotBeFound("lowestDrawdownPoint.equals=" + UPDATED_LOWEST_DRAWDOWN_POINT);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByLowestDrawdownPointIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where lowestDrawdownPoint not equals to DEFAULT_LOWEST_DRAWDOWN_POINT
        defaultTradersChallengeTrackerShouldNotBeFound("lowestDrawdownPoint.notEquals=" + DEFAULT_LOWEST_DRAWDOWN_POINT);

        // Get all the tradersChallengeTrackerList where lowestDrawdownPoint not equals to UPDATED_LOWEST_DRAWDOWN_POINT
        defaultTradersChallengeTrackerShouldBeFound("lowestDrawdownPoint.notEquals=" + UPDATED_LOWEST_DRAWDOWN_POINT);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByLowestDrawdownPointIsInShouldWork() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where lowestDrawdownPoint in DEFAULT_LOWEST_DRAWDOWN_POINT or UPDATED_LOWEST_DRAWDOWN_POINT
        defaultTradersChallengeTrackerShouldBeFound(
            "lowestDrawdownPoint.in=" + DEFAULT_LOWEST_DRAWDOWN_POINT + "," + UPDATED_LOWEST_DRAWDOWN_POINT
        );

        // Get all the tradersChallengeTrackerList where lowestDrawdownPoint equals to UPDATED_LOWEST_DRAWDOWN_POINT
        defaultTradersChallengeTrackerShouldNotBeFound("lowestDrawdownPoint.in=" + UPDATED_LOWEST_DRAWDOWN_POINT);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByLowestDrawdownPointIsNullOrNotNull() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where lowestDrawdownPoint is not null
        defaultTradersChallengeTrackerShouldBeFound("lowestDrawdownPoint.specified=true");

        // Get all the tradersChallengeTrackerList where lowestDrawdownPoint is null
        defaultTradersChallengeTrackerShouldNotBeFound("lowestDrawdownPoint.specified=false");
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByLowestDrawdownPointIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where lowestDrawdownPoint is greater than or equal to DEFAULT_LOWEST_DRAWDOWN_POINT
        defaultTradersChallengeTrackerShouldBeFound("lowestDrawdownPoint.greaterThanOrEqual=" + DEFAULT_LOWEST_DRAWDOWN_POINT);

        // Get all the tradersChallengeTrackerList where lowestDrawdownPoint is greater than or equal to UPDATED_LOWEST_DRAWDOWN_POINT
        defaultTradersChallengeTrackerShouldNotBeFound("lowestDrawdownPoint.greaterThanOrEqual=" + UPDATED_LOWEST_DRAWDOWN_POINT);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByLowestDrawdownPointIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where lowestDrawdownPoint is less than or equal to DEFAULT_LOWEST_DRAWDOWN_POINT
        defaultTradersChallengeTrackerShouldBeFound("lowestDrawdownPoint.lessThanOrEqual=" + DEFAULT_LOWEST_DRAWDOWN_POINT);

        // Get all the tradersChallengeTrackerList where lowestDrawdownPoint is less than or equal to SMALLER_LOWEST_DRAWDOWN_POINT
        defaultTradersChallengeTrackerShouldNotBeFound("lowestDrawdownPoint.lessThanOrEqual=" + SMALLER_LOWEST_DRAWDOWN_POINT);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByLowestDrawdownPointIsLessThanSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where lowestDrawdownPoint is less than DEFAULT_LOWEST_DRAWDOWN_POINT
        defaultTradersChallengeTrackerShouldNotBeFound("lowestDrawdownPoint.lessThan=" + DEFAULT_LOWEST_DRAWDOWN_POINT);

        // Get all the tradersChallengeTrackerList where lowestDrawdownPoint is less than UPDATED_LOWEST_DRAWDOWN_POINT
        defaultTradersChallengeTrackerShouldBeFound("lowestDrawdownPoint.lessThan=" + UPDATED_LOWEST_DRAWDOWN_POINT);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByLowestDrawdownPointIsGreaterThanSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where lowestDrawdownPoint is greater than DEFAULT_LOWEST_DRAWDOWN_POINT
        defaultTradersChallengeTrackerShouldNotBeFound("lowestDrawdownPoint.greaterThan=" + DEFAULT_LOWEST_DRAWDOWN_POINT);

        // Get all the tradersChallengeTrackerList where lowestDrawdownPoint is greater than SMALLER_LOWEST_DRAWDOWN_POINT
        defaultTradersChallengeTrackerShouldBeFound("lowestDrawdownPoint.greaterThan=" + SMALLER_LOWEST_DRAWDOWN_POINT);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRulesViolatedIsEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where rulesViolated equals to DEFAULT_RULES_VIOLATED
        defaultTradersChallengeTrackerShouldBeFound("rulesViolated.equals=" + DEFAULT_RULES_VIOLATED);

        // Get all the tradersChallengeTrackerList where rulesViolated equals to UPDATED_RULES_VIOLATED
        defaultTradersChallengeTrackerShouldNotBeFound("rulesViolated.equals=" + UPDATED_RULES_VIOLATED);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRulesViolatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where rulesViolated not equals to DEFAULT_RULES_VIOLATED
        defaultTradersChallengeTrackerShouldNotBeFound("rulesViolated.notEquals=" + DEFAULT_RULES_VIOLATED);

        // Get all the tradersChallengeTrackerList where rulesViolated not equals to UPDATED_RULES_VIOLATED
        defaultTradersChallengeTrackerShouldBeFound("rulesViolated.notEquals=" + UPDATED_RULES_VIOLATED);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRulesViolatedIsInShouldWork() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where rulesViolated in DEFAULT_RULES_VIOLATED or UPDATED_RULES_VIOLATED
        defaultTradersChallengeTrackerShouldBeFound("rulesViolated.in=" + DEFAULT_RULES_VIOLATED + "," + UPDATED_RULES_VIOLATED);

        // Get all the tradersChallengeTrackerList where rulesViolated equals to UPDATED_RULES_VIOLATED
        defaultTradersChallengeTrackerShouldNotBeFound("rulesViolated.in=" + UPDATED_RULES_VIOLATED);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRulesViolatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where rulesViolated is not null
        defaultTradersChallengeTrackerShouldBeFound("rulesViolated.specified=true");

        // Get all the tradersChallengeTrackerList where rulesViolated is null
        defaultTradersChallengeTrackerShouldNotBeFound("rulesViolated.specified=false");
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRuleViolatedIsEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where ruleViolated equals to DEFAULT_RULE_VIOLATED
        defaultTradersChallengeTrackerShouldBeFound("ruleViolated.equals=" + DEFAULT_RULE_VIOLATED);

        // Get all the tradersChallengeTrackerList where ruleViolated equals to UPDATED_RULE_VIOLATED
        defaultTradersChallengeTrackerShouldNotBeFound("ruleViolated.equals=" + UPDATED_RULE_VIOLATED);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRuleViolatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where ruleViolated not equals to DEFAULT_RULE_VIOLATED
        defaultTradersChallengeTrackerShouldNotBeFound("ruleViolated.notEquals=" + DEFAULT_RULE_VIOLATED);

        // Get all the tradersChallengeTrackerList where ruleViolated not equals to UPDATED_RULE_VIOLATED
        defaultTradersChallengeTrackerShouldBeFound("ruleViolated.notEquals=" + UPDATED_RULE_VIOLATED);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRuleViolatedIsInShouldWork() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where ruleViolated in DEFAULT_RULE_VIOLATED or UPDATED_RULE_VIOLATED
        defaultTradersChallengeTrackerShouldBeFound("ruleViolated.in=" + DEFAULT_RULE_VIOLATED + "," + UPDATED_RULE_VIOLATED);

        // Get all the tradersChallengeTrackerList where ruleViolated equals to UPDATED_RULE_VIOLATED
        defaultTradersChallengeTrackerShouldNotBeFound("ruleViolated.in=" + UPDATED_RULE_VIOLATED);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRuleViolatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where ruleViolated is not null
        defaultTradersChallengeTrackerShouldBeFound("ruleViolated.specified=true");

        // Get all the tradersChallengeTrackerList where ruleViolated is null
        defaultTradersChallengeTrackerShouldNotBeFound("ruleViolated.specified=false");
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRuleViolatedContainsSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where ruleViolated contains DEFAULT_RULE_VIOLATED
        defaultTradersChallengeTrackerShouldBeFound("ruleViolated.contains=" + DEFAULT_RULE_VIOLATED);

        // Get all the tradersChallengeTrackerList where ruleViolated contains UPDATED_RULE_VIOLATED
        defaultTradersChallengeTrackerShouldNotBeFound("ruleViolated.contains=" + UPDATED_RULE_VIOLATED);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRuleViolatedNotContainsSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where ruleViolated does not contain DEFAULT_RULE_VIOLATED
        defaultTradersChallengeTrackerShouldNotBeFound("ruleViolated.doesNotContain=" + DEFAULT_RULE_VIOLATED);

        // Get all the tradersChallengeTrackerList where ruleViolated does not contain UPDATED_RULE_VIOLATED
        defaultTradersChallengeTrackerShouldBeFound("ruleViolated.doesNotContain=" + UPDATED_RULE_VIOLATED);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRuleViolatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where ruleViolatedDate equals to DEFAULT_RULE_VIOLATED_DATE
        defaultTradersChallengeTrackerShouldBeFound("ruleViolatedDate.equals=" + DEFAULT_RULE_VIOLATED_DATE);

        // Get all the tradersChallengeTrackerList where ruleViolatedDate equals to UPDATED_RULE_VIOLATED_DATE
        defaultTradersChallengeTrackerShouldNotBeFound("ruleViolatedDate.equals=" + UPDATED_RULE_VIOLATED_DATE);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRuleViolatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where ruleViolatedDate not equals to DEFAULT_RULE_VIOLATED_DATE
        defaultTradersChallengeTrackerShouldNotBeFound("ruleViolatedDate.notEquals=" + DEFAULT_RULE_VIOLATED_DATE);

        // Get all the tradersChallengeTrackerList where ruleViolatedDate not equals to UPDATED_RULE_VIOLATED_DATE
        defaultTradersChallengeTrackerShouldBeFound("ruleViolatedDate.notEquals=" + UPDATED_RULE_VIOLATED_DATE);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRuleViolatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where ruleViolatedDate in DEFAULT_RULE_VIOLATED_DATE or UPDATED_RULE_VIOLATED_DATE
        defaultTradersChallengeTrackerShouldBeFound("ruleViolatedDate.in=" + DEFAULT_RULE_VIOLATED_DATE + "," + UPDATED_RULE_VIOLATED_DATE);

        // Get all the tradersChallengeTrackerList where ruleViolatedDate equals to UPDATED_RULE_VIOLATED_DATE
        defaultTradersChallengeTrackerShouldNotBeFound("ruleViolatedDate.in=" + UPDATED_RULE_VIOLATED_DATE);
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByRuleViolatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        // Get all the tradersChallengeTrackerList where ruleViolatedDate is not null
        defaultTradersChallengeTrackerShouldBeFound("ruleViolatedDate.specified=true");

        // Get all the tradersChallengeTrackerList where ruleViolatedDate is null
        defaultTradersChallengeTrackerShouldNotBeFound("ruleViolatedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByMt4AccountIsEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);
        Mt4Account mt4Account;
        if (TestUtil.findAll(em, Mt4Account.class).isEmpty()) {
            mt4Account = Mt4AccountResourceIT.createEntity(em);
            em.persist(mt4Account);
            em.flush();
        } else {
            mt4Account = TestUtil.findAll(em, Mt4Account.class).get(0);
        }
        em.persist(mt4Account);
        em.flush();
        tradersChallengeTracker.setMt4Account(mt4Account);
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);
        Long mt4AccountId = mt4Account.getId();

        // Get all the tradersChallengeTrackerList where mt4Account equals to mt4AccountId
        defaultTradersChallengeTrackerShouldBeFound("mt4AccountId.equals=" + mt4AccountId);

        // Get all the tradersChallengeTrackerList where mt4Account equals to (mt4AccountId + 1)
        defaultTradersChallengeTrackerShouldNotBeFound("mt4AccountId.equals=" + (mt4AccountId + 1));
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByChallengeTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);
        ChallengeType challengeType;
        if (TestUtil.findAll(em, ChallengeType.class).isEmpty()) {
            challengeType = ChallengeTypeResourceIT.createEntity(em);
            em.persist(challengeType);
            em.flush();
        } else {
            challengeType = TestUtil.findAll(em, ChallengeType.class).get(0);
        }
        em.persist(challengeType);
        em.flush();
        tradersChallengeTracker.setChallengeType(challengeType);
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);
        Long challengeTypeId = challengeType.getId();

        // Get all the tradersChallengeTrackerList where challengeType equals to challengeTypeId
        defaultTradersChallengeTrackerShouldBeFound("challengeTypeId.equals=" + challengeTypeId);

        // Get all the tradersChallengeTrackerList where challengeType equals to (challengeTypeId + 1)
        defaultTradersChallengeTrackerShouldNotBeFound("challengeTypeId.equals=" + (challengeTypeId + 1));
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);
        User user;
        if (TestUtil.findAll(em, User.class).isEmpty()) {
            user = UserResourceIT.createEntity(em);
            em.persist(user);
            em.flush();
        } else {
            user = TestUtil.findAll(em, User.class).get(0);
        }
        em.persist(user);
        em.flush();
        tradersChallengeTracker.setUser(user);
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);
        Long userId = user.getId();

        // Get all the tradersChallengeTrackerList where user equals to userId
        defaultTradersChallengeTrackerShouldBeFound("userId.equals=" + userId);

        // Get all the tradersChallengeTrackerList where user equals to (userId + 1)
        defaultTradersChallengeTrackerShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    @Test
    @Transactional
    void getAllTradersChallengeTrackersByUserNotificationIsEqualToSomething() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);
        UserNotification userNotification;
        if (TestUtil.findAll(em, UserNotification.class).isEmpty()) {
            userNotification = UserNotificationResourceIT.createEntity(em);
            em.persist(userNotification);
            em.flush();
        } else {
            userNotification = TestUtil.findAll(em, UserNotification.class).get(0);
        }
        em.persist(userNotification);
        em.flush();
        tradersChallengeTracker.addUserNotification(userNotification);
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);
        Long userNotificationId = userNotification.getId();

        // Get all the tradersChallengeTrackerList where userNotification equals to userNotificationId
        defaultTradersChallengeTrackerShouldBeFound("userNotificationId.equals=" + userNotificationId);

        // Get all the tradersChallengeTrackerList where userNotification equals to (userNotificationId + 1)
        defaultTradersChallengeTrackerShouldNotBeFound("userNotificationId.equals=" + (userNotificationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTradersChallengeTrackerShouldBeFound(String filter) throws Exception {
        restTradersChallengeTrackerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tradersChallengeTracker.getId().intValue())))
            .andExpect(jsonPath("$.[*].tradeChallengeName").value(hasItem(DEFAULT_TRADE_CHALLENGE_NAME)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].accountDayStartBalance").value(hasItem(DEFAULT_ACCOUNT_DAY_START_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].accountDayStartEquity").value(hasItem(DEFAULT_ACCOUNT_DAY_START_EQUITY.doubleValue())))
            .andExpect(jsonPath("$.[*].runningMaxTotalDrawdown").value(hasItem(DEFAULT_RUNNING_MAX_TOTAL_DRAWDOWN.doubleValue())))
            .andExpect(jsonPath("$.[*].runningMaxDailyDrawdown").value(hasItem(DEFAULT_RUNNING_MAX_DAILY_DRAWDOWN.doubleValue())))
            .andExpect(jsonPath("$.[*].lowestDrawdownPoint").value(hasItem(DEFAULT_LOWEST_DRAWDOWN_POINT.doubleValue())))
            .andExpect(jsonPath("$.[*].rulesViolated").value(hasItem(DEFAULT_RULES_VIOLATED.booleanValue())))
            .andExpect(jsonPath("$.[*].ruleViolated").value(hasItem(DEFAULT_RULE_VIOLATED)))
            .andExpect(jsonPath("$.[*].ruleViolatedDate").value(hasItem(DEFAULT_RULE_VIOLATED_DATE.toString())));

        // Check, that the count call also returns 1
        restTradersChallengeTrackerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTradersChallengeTrackerShouldNotBeFound(String filter) throws Exception {
        restTradersChallengeTrackerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTradersChallengeTrackerMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingTradersChallengeTracker() throws Exception {
        // Get the tradersChallengeTracker
        restTradersChallengeTrackerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewTradersChallengeTracker() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        int databaseSizeBeforeUpdate = tradersChallengeTrackerRepository.findAll().size();

        // Update the tradersChallengeTracker
        TradersChallengeTracker updatedTradersChallengeTracker = tradersChallengeTrackerRepository
            .findById(tradersChallengeTracker.getId())
            .get();
        // Disconnect from session so that the updates on updatedTradersChallengeTracker are not directly saved in db
        em.detach(updatedTradersChallengeTracker);
        updatedTradersChallengeTracker
            .tradeChallengeName(UPDATED_TRADE_CHALLENGE_NAME)
            .startDate(UPDATED_START_DATE)
            .accountDayStartBalance(UPDATED_ACCOUNT_DAY_START_BALANCE)
            .accountDayStartEquity(UPDATED_ACCOUNT_DAY_START_EQUITY)
            .runningMaxTotalDrawdown(UPDATED_RUNNING_MAX_TOTAL_DRAWDOWN)
            .runningMaxDailyDrawdown(UPDATED_RUNNING_MAX_DAILY_DRAWDOWN)
            .lowestDrawdownPoint(UPDATED_LOWEST_DRAWDOWN_POINT)
            .rulesViolated(UPDATED_RULES_VIOLATED)
            .ruleViolated(UPDATED_RULE_VIOLATED)
            .ruleViolatedDate(UPDATED_RULE_VIOLATED_DATE);

        restTradersChallengeTrackerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedTradersChallengeTracker.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedTradersChallengeTracker))
            )
            .andExpect(status().isOk());

        // Validate the TradersChallengeTracker in the database
        List<TradersChallengeTracker> tradersChallengeTrackerList = tradersChallengeTrackerRepository.findAll();
        assertThat(tradersChallengeTrackerList).hasSize(databaseSizeBeforeUpdate);
        TradersChallengeTracker testTradersChallengeTracker = tradersChallengeTrackerList.get(tradersChallengeTrackerList.size() - 1);
        assertThat(testTradersChallengeTracker.getTradeChallengeName()).isEqualTo(UPDATED_TRADE_CHALLENGE_NAME);
        assertThat(testTradersChallengeTracker.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTradersChallengeTracker.getAccountDayStartBalance()).isEqualTo(UPDATED_ACCOUNT_DAY_START_BALANCE);
        assertThat(testTradersChallengeTracker.getAccountDayStartEquity()).isEqualTo(UPDATED_ACCOUNT_DAY_START_EQUITY);
        assertThat(testTradersChallengeTracker.getRunningMaxTotalDrawdown()).isEqualTo(UPDATED_RUNNING_MAX_TOTAL_DRAWDOWN);
        assertThat(testTradersChallengeTracker.getRunningMaxDailyDrawdown()).isEqualTo(UPDATED_RUNNING_MAX_DAILY_DRAWDOWN);
        assertThat(testTradersChallengeTracker.getLowestDrawdownPoint()).isEqualTo(UPDATED_LOWEST_DRAWDOWN_POINT);
        assertThat(testTradersChallengeTracker.getRulesViolated()).isEqualTo(UPDATED_RULES_VIOLATED);
        assertThat(testTradersChallengeTracker.getRuleViolated()).isEqualTo(UPDATED_RULE_VIOLATED);
        assertThat(testTradersChallengeTracker.getRuleViolatedDate()).isEqualTo(UPDATED_RULE_VIOLATED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingTradersChallengeTracker() throws Exception {
        int databaseSizeBeforeUpdate = tradersChallengeTrackerRepository.findAll().size();
        tradersChallengeTracker.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTradersChallengeTrackerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tradersChallengeTracker.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tradersChallengeTracker))
            )
            .andExpect(status().isBadRequest());

        // Validate the TradersChallengeTracker in the database
        List<TradersChallengeTracker> tradersChallengeTrackerList = tradersChallengeTrackerRepository.findAll();
        assertThat(tradersChallengeTrackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTradersChallengeTracker() throws Exception {
        int databaseSizeBeforeUpdate = tradersChallengeTrackerRepository.findAll().size();
        tradersChallengeTracker.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTradersChallengeTrackerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tradersChallengeTracker))
            )
            .andExpect(status().isBadRequest());

        // Validate the TradersChallengeTracker in the database
        List<TradersChallengeTracker> tradersChallengeTrackerList = tradersChallengeTrackerRepository.findAll();
        assertThat(tradersChallengeTrackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTradersChallengeTracker() throws Exception {
        int databaseSizeBeforeUpdate = tradersChallengeTrackerRepository.findAll().size();
        tradersChallengeTracker.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTradersChallengeTrackerMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(tradersChallengeTracker))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TradersChallengeTracker in the database
        List<TradersChallengeTracker> tradersChallengeTrackerList = tradersChallengeTrackerRepository.findAll();
        assertThat(tradersChallengeTrackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTradersChallengeTrackerWithPatch() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        int databaseSizeBeforeUpdate = tradersChallengeTrackerRepository.findAll().size();

        // Update the tradersChallengeTracker using partial update
        TradersChallengeTracker partialUpdatedTradersChallengeTracker = new TradersChallengeTracker();
        partialUpdatedTradersChallengeTracker.setId(tradersChallengeTracker.getId());

        partialUpdatedTradersChallengeTracker
            .accountDayStartEquity(UPDATED_ACCOUNT_DAY_START_EQUITY)
            .runningMaxDailyDrawdown(UPDATED_RUNNING_MAX_DAILY_DRAWDOWN)
            .ruleViolatedDate(UPDATED_RULE_VIOLATED_DATE);

        restTradersChallengeTrackerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTradersChallengeTracker.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTradersChallengeTracker))
            )
            .andExpect(status().isOk());

        // Validate the TradersChallengeTracker in the database
        List<TradersChallengeTracker> tradersChallengeTrackerList = tradersChallengeTrackerRepository.findAll();
        assertThat(tradersChallengeTrackerList).hasSize(databaseSizeBeforeUpdate);
        TradersChallengeTracker testTradersChallengeTracker = tradersChallengeTrackerList.get(tradersChallengeTrackerList.size() - 1);
        assertThat(testTradersChallengeTracker.getTradeChallengeName()).isEqualTo(DEFAULT_TRADE_CHALLENGE_NAME);
        assertThat(testTradersChallengeTracker.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testTradersChallengeTracker.getAccountDayStartBalance()).isEqualTo(DEFAULT_ACCOUNT_DAY_START_BALANCE);
        assertThat(testTradersChallengeTracker.getAccountDayStartEquity()).isEqualTo(UPDATED_ACCOUNT_DAY_START_EQUITY);
        assertThat(testTradersChallengeTracker.getRunningMaxTotalDrawdown()).isEqualTo(DEFAULT_RUNNING_MAX_TOTAL_DRAWDOWN);
        assertThat(testTradersChallengeTracker.getRunningMaxDailyDrawdown()).isEqualTo(UPDATED_RUNNING_MAX_DAILY_DRAWDOWN);
        assertThat(testTradersChallengeTracker.getLowestDrawdownPoint()).isEqualTo(DEFAULT_LOWEST_DRAWDOWN_POINT);
        assertThat(testTradersChallengeTracker.getRulesViolated()).isEqualTo(DEFAULT_RULES_VIOLATED);
        assertThat(testTradersChallengeTracker.getRuleViolated()).isEqualTo(DEFAULT_RULE_VIOLATED);
        assertThat(testTradersChallengeTracker.getRuleViolatedDate()).isEqualTo(UPDATED_RULE_VIOLATED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateTradersChallengeTrackerWithPatch() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        int databaseSizeBeforeUpdate = tradersChallengeTrackerRepository.findAll().size();

        // Update the tradersChallengeTracker using partial update
        TradersChallengeTracker partialUpdatedTradersChallengeTracker = new TradersChallengeTracker();
        partialUpdatedTradersChallengeTracker.setId(tradersChallengeTracker.getId());

        partialUpdatedTradersChallengeTracker
            .tradeChallengeName(UPDATED_TRADE_CHALLENGE_NAME)
            .startDate(UPDATED_START_DATE)
            .accountDayStartBalance(UPDATED_ACCOUNT_DAY_START_BALANCE)
            .accountDayStartEquity(UPDATED_ACCOUNT_DAY_START_EQUITY)
            .runningMaxTotalDrawdown(UPDATED_RUNNING_MAX_TOTAL_DRAWDOWN)
            .runningMaxDailyDrawdown(UPDATED_RUNNING_MAX_DAILY_DRAWDOWN)
            .lowestDrawdownPoint(UPDATED_LOWEST_DRAWDOWN_POINT)
            .rulesViolated(UPDATED_RULES_VIOLATED)
            .ruleViolated(UPDATED_RULE_VIOLATED)
            .ruleViolatedDate(UPDATED_RULE_VIOLATED_DATE);

        restTradersChallengeTrackerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTradersChallengeTracker.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedTradersChallengeTracker))
            )
            .andExpect(status().isOk());

        // Validate the TradersChallengeTracker in the database
        List<TradersChallengeTracker> tradersChallengeTrackerList = tradersChallengeTrackerRepository.findAll();
        assertThat(tradersChallengeTrackerList).hasSize(databaseSizeBeforeUpdate);
        TradersChallengeTracker testTradersChallengeTracker = tradersChallengeTrackerList.get(tradersChallengeTrackerList.size() - 1);
        assertThat(testTradersChallengeTracker.getTradeChallengeName()).isEqualTo(UPDATED_TRADE_CHALLENGE_NAME);
        assertThat(testTradersChallengeTracker.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testTradersChallengeTracker.getAccountDayStartBalance()).isEqualTo(UPDATED_ACCOUNT_DAY_START_BALANCE);
        assertThat(testTradersChallengeTracker.getAccountDayStartEquity()).isEqualTo(UPDATED_ACCOUNT_DAY_START_EQUITY);
        assertThat(testTradersChallengeTracker.getRunningMaxTotalDrawdown()).isEqualTo(UPDATED_RUNNING_MAX_TOTAL_DRAWDOWN);
        assertThat(testTradersChallengeTracker.getRunningMaxDailyDrawdown()).isEqualTo(UPDATED_RUNNING_MAX_DAILY_DRAWDOWN);
        assertThat(testTradersChallengeTracker.getLowestDrawdownPoint()).isEqualTo(UPDATED_LOWEST_DRAWDOWN_POINT);
        assertThat(testTradersChallengeTracker.getRulesViolated()).isEqualTo(UPDATED_RULES_VIOLATED);
        assertThat(testTradersChallengeTracker.getRuleViolated()).isEqualTo(UPDATED_RULE_VIOLATED);
        assertThat(testTradersChallengeTracker.getRuleViolatedDate()).isEqualTo(UPDATED_RULE_VIOLATED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingTradersChallengeTracker() throws Exception {
        int databaseSizeBeforeUpdate = tradersChallengeTrackerRepository.findAll().size();
        tradersChallengeTracker.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTradersChallengeTrackerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tradersChallengeTracker.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tradersChallengeTracker))
            )
            .andExpect(status().isBadRequest());

        // Validate the TradersChallengeTracker in the database
        List<TradersChallengeTracker> tradersChallengeTrackerList = tradersChallengeTrackerRepository.findAll();
        assertThat(tradersChallengeTrackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTradersChallengeTracker() throws Exception {
        int databaseSizeBeforeUpdate = tradersChallengeTrackerRepository.findAll().size();
        tradersChallengeTracker.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTradersChallengeTrackerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tradersChallengeTracker))
            )
            .andExpect(status().isBadRequest());

        // Validate the TradersChallengeTracker in the database
        List<TradersChallengeTracker> tradersChallengeTrackerList = tradersChallengeTrackerRepository.findAll();
        assertThat(tradersChallengeTrackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTradersChallengeTracker() throws Exception {
        int databaseSizeBeforeUpdate = tradersChallengeTrackerRepository.findAll().size();
        tradersChallengeTracker.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTradersChallengeTrackerMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(tradersChallengeTracker))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the TradersChallengeTracker in the database
        List<TradersChallengeTracker> tradersChallengeTrackerList = tradersChallengeTrackerRepository.findAll();
        assertThat(tradersChallengeTrackerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTradersChallengeTracker() throws Exception {
        // Initialize the database
        tradersChallengeTrackerRepository.saveAndFlush(tradersChallengeTracker);

        int databaseSizeBeforeDelete = tradersChallengeTrackerRepository.findAll().size();

        // Delete the tradersChallengeTracker
        restTradersChallengeTrackerMockMvc
            .perform(delete(ENTITY_API_URL_ID, tradersChallengeTracker.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TradersChallengeTracker> tradersChallengeTrackerList = tradersChallengeTrackerRepository.findAll();
        assertThat(tradersChallengeTrackerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
