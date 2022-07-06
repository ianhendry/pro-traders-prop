package com.gracefl.propfirm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gracefl.propfirm.IntegrationTest;
import com.gracefl.propfirm.domain.BillingDetails;
import com.gracefl.propfirm.domain.SiteAccount;
import com.gracefl.propfirm.repository.BillingDetailsRepository;
import com.gracefl.propfirm.service.criteria.BillingDetailsCriteria;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link BillingDetailsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BillingDetailsResourceIT {

    private static final String DEFAULT_CONTACT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_1 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_1 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_2 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_2 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_3 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_3 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_4 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_4 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_5 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_5 = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS_6 = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS_6 = "BBBBBBBBBB";

    private static final String DEFAULT_DIAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_DIAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_MESSENGER_ID = "AAAAAAAAAA";
    private static final String UPDATED_MESSENGER_ID = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE_ADDED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_ADDED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IN_ACTIVE = false;
    private static final Boolean UPDATED_IN_ACTIVE = true;

    private static final Instant DEFAULT_IN_ACTIVE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_IN_ACTIVE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/billing-details";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BillingDetailsRepository billingDetailsRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBillingDetailsMockMvc;

    private BillingDetails billingDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BillingDetails createEntity(EntityManager em) {
        BillingDetails billingDetails = new BillingDetails()
            .contactName(DEFAULT_CONTACT_NAME)
            .address1(DEFAULT_ADDRESS_1)
            .address2(DEFAULT_ADDRESS_2)
            .address3(DEFAULT_ADDRESS_3)
            .address4(DEFAULT_ADDRESS_4)
            .address5(DEFAULT_ADDRESS_5)
            .address6(DEFAULT_ADDRESS_6)
            .dialCode(DEFAULT_DIAL_CODE)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .messengerId(DEFAULT_MESSENGER_ID)
            .dateAdded(DEFAULT_DATE_ADDED)
            .inActive(DEFAULT_IN_ACTIVE)
            .inActiveDate(DEFAULT_IN_ACTIVE_DATE);
        return billingDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static BillingDetails createUpdatedEntity(EntityManager em) {
        BillingDetails billingDetails = new BillingDetails()
            .contactName(UPDATED_CONTACT_NAME)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .address3(UPDATED_ADDRESS_3)
            .address4(UPDATED_ADDRESS_4)
            .address5(UPDATED_ADDRESS_5)
            .address6(UPDATED_ADDRESS_6)
            .dialCode(UPDATED_DIAL_CODE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .messengerId(UPDATED_MESSENGER_ID)
            .dateAdded(UPDATED_DATE_ADDED)
            .inActive(UPDATED_IN_ACTIVE)
            .inActiveDate(UPDATED_IN_ACTIVE_DATE);
        return billingDetails;
    }

    @BeforeEach
    public void initTest() {
        billingDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createBillingDetails() throws Exception {
        int databaseSizeBeforeCreate = billingDetailsRepository.findAll().size();
        // Create the BillingDetails
        restBillingDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billingDetails))
            )
            .andExpect(status().isCreated());

        // Validate the BillingDetails in the database
        List<BillingDetails> billingDetailsList = billingDetailsRepository.findAll();
        assertThat(billingDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        BillingDetails testBillingDetails = billingDetailsList.get(billingDetailsList.size() - 1);
        assertThat(testBillingDetails.getContactName()).isEqualTo(DEFAULT_CONTACT_NAME);
        assertThat(testBillingDetails.getAddress1()).isEqualTo(DEFAULT_ADDRESS_1);
        assertThat(testBillingDetails.getAddress2()).isEqualTo(DEFAULT_ADDRESS_2);
        assertThat(testBillingDetails.getAddress3()).isEqualTo(DEFAULT_ADDRESS_3);
        assertThat(testBillingDetails.getAddress4()).isEqualTo(DEFAULT_ADDRESS_4);
        assertThat(testBillingDetails.getAddress5()).isEqualTo(DEFAULT_ADDRESS_5);
        assertThat(testBillingDetails.getAddress6()).isEqualTo(DEFAULT_ADDRESS_6);
        assertThat(testBillingDetails.getDialCode()).isEqualTo(DEFAULT_DIAL_CODE);
        assertThat(testBillingDetails.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testBillingDetails.getMessengerId()).isEqualTo(DEFAULT_MESSENGER_ID);
        assertThat(testBillingDetails.getDateAdded()).isEqualTo(DEFAULT_DATE_ADDED);
        assertThat(testBillingDetails.getInActive()).isEqualTo(DEFAULT_IN_ACTIVE);
        assertThat(testBillingDetails.getInActiveDate()).isEqualTo(DEFAULT_IN_ACTIVE_DATE);
    }

    @Test
    @Transactional
    void createBillingDetailsWithExistingId() throws Exception {
        // Create the BillingDetails with an existing ID
        billingDetails.setId(1L);

        int databaseSizeBeforeCreate = billingDetailsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBillingDetailsMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billingDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the BillingDetails in the database
        List<BillingDetails> billingDetailsList = billingDetailsRepository.findAll();
        assertThat(billingDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllBillingDetails() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList
        restBillingDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billingDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].contactName").value(hasItem(DEFAULT_CONTACT_NAME)))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].address3").value(hasItem(DEFAULT_ADDRESS_3)))
            .andExpect(jsonPath("$.[*].address4").value(hasItem(DEFAULT_ADDRESS_4)))
            .andExpect(jsonPath("$.[*].address5").value(hasItem(DEFAULT_ADDRESS_5)))
            .andExpect(jsonPath("$.[*].address6").value(hasItem(DEFAULT_ADDRESS_6)))
            .andExpect(jsonPath("$.[*].dialCode").value(hasItem(DEFAULT_DIAL_CODE)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].messengerId").value(hasItem(DEFAULT_MESSENGER_ID)))
            .andExpect(jsonPath("$.[*].dateAdded").value(hasItem(DEFAULT_DATE_ADDED.toString())))
            .andExpect(jsonPath("$.[*].inActive").value(hasItem(DEFAULT_IN_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].inActiveDate").value(hasItem(DEFAULT_IN_ACTIVE_DATE.toString())));
    }

    @Test
    @Transactional
    void getBillingDetails() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get the billingDetails
        restBillingDetailsMockMvc
            .perform(get(ENTITY_API_URL_ID, billingDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(billingDetails.getId().intValue()))
            .andExpect(jsonPath("$.contactName").value(DEFAULT_CONTACT_NAME))
            .andExpect(jsonPath("$.address1").value(DEFAULT_ADDRESS_1))
            .andExpect(jsonPath("$.address2").value(DEFAULT_ADDRESS_2))
            .andExpect(jsonPath("$.address3").value(DEFAULT_ADDRESS_3))
            .andExpect(jsonPath("$.address4").value(DEFAULT_ADDRESS_4))
            .andExpect(jsonPath("$.address5").value(DEFAULT_ADDRESS_5))
            .andExpect(jsonPath("$.address6").value(DEFAULT_ADDRESS_6))
            .andExpect(jsonPath("$.dialCode").value(DEFAULT_DIAL_CODE))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER))
            .andExpect(jsonPath("$.messengerId").value(DEFAULT_MESSENGER_ID))
            .andExpect(jsonPath("$.dateAdded").value(DEFAULT_DATE_ADDED.toString()))
            .andExpect(jsonPath("$.inActive").value(DEFAULT_IN_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.inActiveDate").value(DEFAULT_IN_ACTIVE_DATE.toString()));
    }

    @Test
    @Transactional
    void getBillingDetailsByIdFiltering() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        Long id = billingDetails.getId();

        defaultBillingDetailsShouldBeFound("id.equals=" + id);
        defaultBillingDetailsShouldNotBeFound("id.notEquals=" + id);

        defaultBillingDetailsShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBillingDetailsShouldNotBeFound("id.greaterThan=" + id);

        defaultBillingDetailsShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBillingDetailsShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByContactNameIsEqualToSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where contactName equals to DEFAULT_CONTACT_NAME
        defaultBillingDetailsShouldBeFound("contactName.equals=" + DEFAULT_CONTACT_NAME);

        // Get all the billingDetailsList where contactName equals to UPDATED_CONTACT_NAME
        defaultBillingDetailsShouldNotBeFound("contactName.equals=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByContactNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where contactName not equals to DEFAULT_CONTACT_NAME
        defaultBillingDetailsShouldNotBeFound("contactName.notEquals=" + DEFAULT_CONTACT_NAME);

        // Get all the billingDetailsList where contactName not equals to UPDATED_CONTACT_NAME
        defaultBillingDetailsShouldBeFound("contactName.notEquals=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByContactNameIsInShouldWork() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where contactName in DEFAULT_CONTACT_NAME or UPDATED_CONTACT_NAME
        defaultBillingDetailsShouldBeFound("contactName.in=" + DEFAULT_CONTACT_NAME + "," + UPDATED_CONTACT_NAME);

        // Get all the billingDetailsList where contactName equals to UPDATED_CONTACT_NAME
        defaultBillingDetailsShouldNotBeFound("contactName.in=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByContactNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where contactName is not null
        defaultBillingDetailsShouldBeFound("contactName.specified=true");

        // Get all the billingDetailsList where contactName is null
        defaultBillingDetailsShouldNotBeFound("contactName.specified=false");
    }

    @Test
    @Transactional
    void getAllBillingDetailsByContactNameContainsSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where contactName contains DEFAULT_CONTACT_NAME
        defaultBillingDetailsShouldBeFound("contactName.contains=" + DEFAULT_CONTACT_NAME);

        // Get all the billingDetailsList where contactName contains UPDATED_CONTACT_NAME
        defaultBillingDetailsShouldNotBeFound("contactName.contains=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByContactNameNotContainsSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where contactName does not contain DEFAULT_CONTACT_NAME
        defaultBillingDetailsShouldNotBeFound("contactName.doesNotContain=" + DEFAULT_CONTACT_NAME);

        // Get all the billingDetailsList where contactName does not contain UPDATED_CONTACT_NAME
        defaultBillingDetailsShouldBeFound("contactName.doesNotContain=" + UPDATED_CONTACT_NAME);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress1IsEqualToSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address1 equals to DEFAULT_ADDRESS_1
        defaultBillingDetailsShouldBeFound("address1.equals=" + DEFAULT_ADDRESS_1);

        // Get all the billingDetailsList where address1 equals to UPDATED_ADDRESS_1
        defaultBillingDetailsShouldNotBeFound("address1.equals=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress1IsNotEqualToSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address1 not equals to DEFAULT_ADDRESS_1
        defaultBillingDetailsShouldNotBeFound("address1.notEquals=" + DEFAULT_ADDRESS_1);

        // Get all the billingDetailsList where address1 not equals to UPDATED_ADDRESS_1
        defaultBillingDetailsShouldBeFound("address1.notEquals=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress1IsInShouldWork() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address1 in DEFAULT_ADDRESS_1 or UPDATED_ADDRESS_1
        defaultBillingDetailsShouldBeFound("address1.in=" + DEFAULT_ADDRESS_1 + "," + UPDATED_ADDRESS_1);

        // Get all the billingDetailsList where address1 equals to UPDATED_ADDRESS_1
        defaultBillingDetailsShouldNotBeFound("address1.in=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress1IsNullOrNotNull() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address1 is not null
        defaultBillingDetailsShouldBeFound("address1.specified=true");

        // Get all the billingDetailsList where address1 is null
        defaultBillingDetailsShouldNotBeFound("address1.specified=false");
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress1ContainsSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address1 contains DEFAULT_ADDRESS_1
        defaultBillingDetailsShouldBeFound("address1.contains=" + DEFAULT_ADDRESS_1);

        // Get all the billingDetailsList where address1 contains UPDATED_ADDRESS_1
        defaultBillingDetailsShouldNotBeFound("address1.contains=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress1NotContainsSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address1 does not contain DEFAULT_ADDRESS_1
        defaultBillingDetailsShouldNotBeFound("address1.doesNotContain=" + DEFAULT_ADDRESS_1);

        // Get all the billingDetailsList where address1 does not contain UPDATED_ADDRESS_1
        defaultBillingDetailsShouldBeFound("address1.doesNotContain=" + UPDATED_ADDRESS_1);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress2IsEqualToSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address2 equals to DEFAULT_ADDRESS_2
        defaultBillingDetailsShouldBeFound("address2.equals=" + DEFAULT_ADDRESS_2);

        // Get all the billingDetailsList where address2 equals to UPDATED_ADDRESS_2
        defaultBillingDetailsShouldNotBeFound("address2.equals=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress2IsNotEqualToSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address2 not equals to DEFAULT_ADDRESS_2
        defaultBillingDetailsShouldNotBeFound("address2.notEquals=" + DEFAULT_ADDRESS_2);

        // Get all the billingDetailsList where address2 not equals to UPDATED_ADDRESS_2
        defaultBillingDetailsShouldBeFound("address2.notEquals=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress2IsInShouldWork() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address2 in DEFAULT_ADDRESS_2 or UPDATED_ADDRESS_2
        defaultBillingDetailsShouldBeFound("address2.in=" + DEFAULT_ADDRESS_2 + "," + UPDATED_ADDRESS_2);

        // Get all the billingDetailsList where address2 equals to UPDATED_ADDRESS_2
        defaultBillingDetailsShouldNotBeFound("address2.in=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress2IsNullOrNotNull() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address2 is not null
        defaultBillingDetailsShouldBeFound("address2.specified=true");

        // Get all the billingDetailsList where address2 is null
        defaultBillingDetailsShouldNotBeFound("address2.specified=false");
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress2ContainsSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address2 contains DEFAULT_ADDRESS_2
        defaultBillingDetailsShouldBeFound("address2.contains=" + DEFAULT_ADDRESS_2);

        // Get all the billingDetailsList where address2 contains UPDATED_ADDRESS_2
        defaultBillingDetailsShouldNotBeFound("address2.contains=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress2NotContainsSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address2 does not contain DEFAULT_ADDRESS_2
        defaultBillingDetailsShouldNotBeFound("address2.doesNotContain=" + DEFAULT_ADDRESS_2);

        // Get all the billingDetailsList where address2 does not contain UPDATED_ADDRESS_2
        defaultBillingDetailsShouldBeFound("address2.doesNotContain=" + UPDATED_ADDRESS_2);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress3IsEqualToSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address3 equals to DEFAULT_ADDRESS_3
        defaultBillingDetailsShouldBeFound("address3.equals=" + DEFAULT_ADDRESS_3);

        // Get all the billingDetailsList where address3 equals to UPDATED_ADDRESS_3
        defaultBillingDetailsShouldNotBeFound("address3.equals=" + UPDATED_ADDRESS_3);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress3IsNotEqualToSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address3 not equals to DEFAULT_ADDRESS_3
        defaultBillingDetailsShouldNotBeFound("address3.notEquals=" + DEFAULT_ADDRESS_3);

        // Get all the billingDetailsList where address3 not equals to UPDATED_ADDRESS_3
        defaultBillingDetailsShouldBeFound("address3.notEquals=" + UPDATED_ADDRESS_3);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress3IsInShouldWork() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address3 in DEFAULT_ADDRESS_3 or UPDATED_ADDRESS_3
        defaultBillingDetailsShouldBeFound("address3.in=" + DEFAULT_ADDRESS_3 + "," + UPDATED_ADDRESS_3);

        // Get all the billingDetailsList where address3 equals to UPDATED_ADDRESS_3
        defaultBillingDetailsShouldNotBeFound("address3.in=" + UPDATED_ADDRESS_3);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress3IsNullOrNotNull() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address3 is not null
        defaultBillingDetailsShouldBeFound("address3.specified=true");

        // Get all the billingDetailsList where address3 is null
        defaultBillingDetailsShouldNotBeFound("address3.specified=false");
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress3ContainsSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address3 contains DEFAULT_ADDRESS_3
        defaultBillingDetailsShouldBeFound("address3.contains=" + DEFAULT_ADDRESS_3);

        // Get all the billingDetailsList where address3 contains UPDATED_ADDRESS_3
        defaultBillingDetailsShouldNotBeFound("address3.contains=" + UPDATED_ADDRESS_3);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress3NotContainsSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address3 does not contain DEFAULT_ADDRESS_3
        defaultBillingDetailsShouldNotBeFound("address3.doesNotContain=" + DEFAULT_ADDRESS_3);

        // Get all the billingDetailsList where address3 does not contain UPDATED_ADDRESS_3
        defaultBillingDetailsShouldBeFound("address3.doesNotContain=" + UPDATED_ADDRESS_3);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress4IsEqualToSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address4 equals to DEFAULT_ADDRESS_4
        defaultBillingDetailsShouldBeFound("address4.equals=" + DEFAULT_ADDRESS_4);

        // Get all the billingDetailsList where address4 equals to UPDATED_ADDRESS_4
        defaultBillingDetailsShouldNotBeFound("address4.equals=" + UPDATED_ADDRESS_4);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress4IsNotEqualToSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address4 not equals to DEFAULT_ADDRESS_4
        defaultBillingDetailsShouldNotBeFound("address4.notEquals=" + DEFAULT_ADDRESS_4);

        // Get all the billingDetailsList where address4 not equals to UPDATED_ADDRESS_4
        defaultBillingDetailsShouldBeFound("address4.notEquals=" + UPDATED_ADDRESS_4);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress4IsInShouldWork() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address4 in DEFAULT_ADDRESS_4 or UPDATED_ADDRESS_4
        defaultBillingDetailsShouldBeFound("address4.in=" + DEFAULT_ADDRESS_4 + "," + UPDATED_ADDRESS_4);

        // Get all the billingDetailsList where address4 equals to UPDATED_ADDRESS_4
        defaultBillingDetailsShouldNotBeFound("address4.in=" + UPDATED_ADDRESS_4);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress4IsNullOrNotNull() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address4 is not null
        defaultBillingDetailsShouldBeFound("address4.specified=true");

        // Get all the billingDetailsList where address4 is null
        defaultBillingDetailsShouldNotBeFound("address4.specified=false");
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress4ContainsSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address4 contains DEFAULT_ADDRESS_4
        defaultBillingDetailsShouldBeFound("address4.contains=" + DEFAULT_ADDRESS_4);

        // Get all the billingDetailsList where address4 contains UPDATED_ADDRESS_4
        defaultBillingDetailsShouldNotBeFound("address4.contains=" + UPDATED_ADDRESS_4);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress4NotContainsSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address4 does not contain DEFAULT_ADDRESS_4
        defaultBillingDetailsShouldNotBeFound("address4.doesNotContain=" + DEFAULT_ADDRESS_4);

        // Get all the billingDetailsList where address4 does not contain UPDATED_ADDRESS_4
        defaultBillingDetailsShouldBeFound("address4.doesNotContain=" + UPDATED_ADDRESS_4);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress5IsEqualToSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address5 equals to DEFAULT_ADDRESS_5
        defaultBillingDetailsShouldBeFound("address5.equals=" + DEFAULT_ADDRESS_5);

        // Get all the billingDetailsList where address5 equals to UPDATED_ADDRESS_5
        defaultBillingDetailsShouldNotBeFound("address5.equals=" + UPDATED_ADDRESS_5);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress5IsNotEqualToSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address5 not equals to DEFAULT_ADDRESS_5
        defaultBillingDetailsShouldNotBeFound("address5.notEquals=" + DEFAULT_ADDRESS_5);

        // Get all the billingDetailsList where address5 not equals to UPDATED_ADDRESS_5
        defaultBillingDetailsShouldBeFound("address5.notEquals=" + UPDATED_ADDRESS_5);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress5IsInShouldWork() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address5 in DEFAULT_ADDRESS_5 or UPDATED_ADDRESS_5
        defaultBillingDetailsShouldBeFound("address5.in=" + DEFAULT_ADDRESS_5 + "," + UPDATED_ADDRESS_5);

        // Get all the billingDetailsList where address5 equals to UPDATED_ADDRESS_5
        defaultBillingDetailsShouldNotBeFound("address5.in=" + UPDATED_ADDRESS_5);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress5IsNullOrNotNull() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address5 is not null
        defaultBillingDetailsShouldBeFound("address5.specified=true");

        // Get all the billingDetailsList where address5 is null
        defaultBillingDetailsShouldNotBeFound("address5.specified=false");
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress5ContainsSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address5 contains DEFAULT_ADDRESS_5
        defaultBillingDetailsShouldBeFound("address5.contains=" + DEFAULT_ADDRESS_5);

        // Get all the billingDetailsList where address5 contains UPDATED_ADDRESS_5
        defaultBillingDetailsShouldNotBeFound("address5.contains=" + UPDATED_ADDRESS_5);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress5NotContainsSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address5 does not contain DEFAULT_ADDRESS_5
        defaultBillingDetailsShouldNotBeFound("address5.doesNotContain=" + DEFAULT_ADDRESS_5);

        // Get all the billingDetailsList where address5 does not contain UPDATED_ADDRESS_5
        defaultBillingDetailsShouldBeFound("address5.doesNotContain=" + UPDATED_ADDRESS_5);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress6IsEqualToSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address6 equals to DEFAULT_ADDRESS_6
        defaultBillingDetailsShouldBeFound("address6.equals=" + DEFAULT_ADDRESS_6);

        // Get all the billingDetailsList where address6 equals to UPDATED_ADDRESS_6
        defaultBillingDetailsShouldNotBeFound("address6.equals=" + UPDATED_ADDRESS_6);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress6IsNotEqualToSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address6 not equals to DEFAULT_ADDRESS_6
        defaultBillingDetailsShouldNotBeFound("address6.notEquals=" + DEFAULT_ADDRESS_6);

        // Get all the billingDetailsList where address6 not equals to UPDATED_ADDRESS_6
        defaultBillingDetailsShouldBeFound("address6.notEquals=" + UPDATED_ADDRESS_6);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress6IsInShouldWork() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address6 in DEFAULT_ADDRESS_6 or UPDATED_ADDRESS_6
        defaultBillingDetailsShouldBeFound("address6.in=" + DEFAULT_ADDRESS_6 + "," + UPDATED_ADDRESS_6);

        // Get all the billingDetailsList where address6 equals to UPDATED_ADDRESS_6
        defaultBillingDetailsShouldNotBeFound("address6.in=" + UPDATED_ADDRESS_6);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress6IsNullOrNotNull() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address6 is not null
        defaultBillingDetailsShouldBeFound("address6.specified=true");

        // Get all the billingDetailsList where address6 is null
        defaultBillingDetailsShouldNotBeFound("address6.specified=false");
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress6ContainsSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address6 contains DEFAULT_ADDRESS_6
        defaultBillingDetailsShouldBeFound("address6.contains=" + DEFAULT_ADDRESS_6);

        // Get all the billingDetailsList where address6 contains UPDATED_ADDRESS_6
        defaultBillingDetailsShouldNotBeFound("address6.contains=" + UPDATED_ADDRESS_6);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByAddress6NotContainsSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where address6 does not contain DEFAULT_ADDRESS_6
        defaultBillingDetailsShouldNotBeFound("address6.doesNotContain=" + DEFAULT_ADDRESS_6);

        // Get all the billingDetailsList where address6 does not contain UPDATED_ADDRESS_6
        defaultBillingDetailsShouldBeFound("address6.doesNotContain=" + UPDATED_ADDRESS_6);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByDialCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where dialCode equals to DEFAULT_DIAL_CODE
        defaultBillingDetailsShouldBeFound("dialCode.equals=" + DEFAULT_DIAL_CODE);

        // Get all the billingDetailsList where dialCode equals to UPDATED_DIAL_CODE
        defaultBillingDetailsShouldNotBeFound("dialCode.equals=" + UPDATED_DIAL_CODE);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByDialCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where dialCode not equals to DEFAULT_DIAL_CODE
        defaultBillingDetailsShouldNotBeFound("dialCode.notEquals=" + DEFAULT_DIAL_CODE);

        // Get all the billingDetailsList where dialCode not equals to UPDATED_DIAL_CODE
        defaultBillingDetailsShouldBeFound("dialCode.notEquals=" + UPDATED_DIAL_CODE);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByDialCodeIsInShouldWork() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where dialCode in DEFAULT_DIAL_CODE or UPDATED_DIAL_CODE
        defaultBillingDetailsShouldBeFound("dialCode.in=" + DEFAULT_DIAL_CODE + "," + UPDATED_DIAL_CODE);

        // Get all the billingDetailsList where dialCode equals to UPDATED_DIAL_CODE
        defaultBillingDetailsShouldNotBeFound("dialCode.in=" + UPDATED_DIAL_CODE);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByDialCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where dialCode is not null
        defaultBillingDetailsShouldBeFound("dialCode.specified=true");

        // Get all the billingDetailsList where dialCode is null
        defaultBillingDetailsShouldNotBeFound("dialCode.specified=false");
    }

    @Test
    @Transactional
    void getAllBillingDetailsByDialCodeContainsSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where dialCode contains DEFAULT_DIAL_CODE
        defaultBillingDetailsShouldBeFound("dialCode.contains=" + DEFAULT_DIAL_CODE);

        // Get all the billingDetailsList where dialCode contains UPDATED_DIAL_CODE
        defaultBillingDetailsShouldNotBeFound("dialCode.contains=" + UPDATED_DIAL_CODE);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByDialCodeNotContainsSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where dialCode does not contain DEFAULT_DIAL_CODE
        defaultBillingDetailsShouldNotBeFound("dialCode.doesNotContain=" + DEFAULT_DIAL_CODE);

        // Get all the billingDetailsList where dialCode does not contain UPDATED_DIAL_CODE
        defaultBillingDetailsShouldBeFound("dialCode.doesNotContain=" + UPDATED_DIAL_CODE);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByPhoneNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where phoneNumber equals to DEFAULT_PHONE_NUMBER
        defaultBillingDetailsShouldBeFound("phoneNumber.equals=" + DEFAULT_PHONE_NUMBER);

        // Get all the billingDetailsList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultBillingDetailsShouldNotBeFound("phoneNumber.equals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByPhoneNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where phoneNumber not equals to DEFAULT_PHONE_NUMBER
        defaultBillingDetailsShouldNotBeFound("phoneNumber.notEquals=" + DEFAULT_PHONE_NUMBER);

        // Get all the billingDetailsList where phoneNumber not equals to UPDATED_PHONE_NUMBER
        defaultBillingDetailsShouldBeFound("phoneNumber.notEquals=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByPhoneNumberIsInShouldWork() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where phoneNumber in DEFAULT_PHONE_NUMBER or UPDATED_PHONE_NUMBER
        defaultBillingDetailsShouldBeFound("phoneNumber.in=" + DEFAULT_PHONE_NUMBER + "," + UPDATED_PHONE_NUMBER);

        // Get all the billingDetailsList where phoneNumber equals to UPDATED_PHONE_NUMBER
        defaultBillingDetailsShouldNotBeFound("phoneNumber.in=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByPhoneNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where phoneNumber is not null
        defaultBillingDetailsShouldBeFound("phoneNumber.specified=true");

        // Get all the billingDetailsList where phoneNumber is null
        defaultBillingDetailsShouldNotBeFound("phoneNumber.specified=false");
    }

    @Test
    @Transactional
    void getAllBillingDetailsByPhoneNumberContainsSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where phoneNumber contains DEFAULT_PHONE_NUMBER
        defaultBillingDetailsShouldBeFound("phoneNumber.contains=" + DEFAULT_PHONE_NUMBER);

        // Get all the billingDetailsList where phoneNumber contains UPDATED_PHONE_NUMBER
        defaultBillingDetailsShouldNotBeFound("phoneNumber.contains=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByPhoneNumberNotContainsSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where phoneNumber does not contain DEFAULT_PHONE_NUMBER
        defaultBillingDetailsShouldNotBeFound("phoneNumber.doesNotContain=" + DEFAULT_PHONE_NUMBER);

        // Get all the billingDetailsList where phoneNumber does not contain UPDATED_PHONE_NUMBER
        defaultBillingDetailsShouldBeFound("phoneNumber.doesNotContain=" + UPDATED_PHONE_NUMBER);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByMessengerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where messengerId equals to DEFAULT_MESSENGER_ID
        defaultBillingDetailsShouldBeFound("messengerId.equals=" + DEFAULT_MESSENGER_ID);

        // Get all the billingDetailsList where messengerId equals to UPDATED_MESSENGER_ID
        defaultBillingDetailsShouldNotBeFound("messengerId.equals=" + UPDATED_MESSENGER_ID);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByMessengerIdIsNotEqualToSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where messengerId not equals to DEFAULT_MESSENGER_ID
        defaultBillingDetailsShouldNotBeFound("messengerId.notEquals=" + DEFAULT_MESSENGER_ID);

        // Get all the billingDetailsList where messengerId not equals to UPDATED_MESSENGER_ID
        defaultBillingDetailsShouldBeFound("messengerId.notEquals=" + UPDATED_MESSENGER_ID);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByMessengerIdIsInShouldWork() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where messengerId in DEFAULT_MESSENGER_ID or UPDATED_MESSENGER_ID
        defaultBillingDetailsShouldBeFound("messengerId.in=" + DEFAULT_MESSENGER_ID + "," + UPDATED_MESSENGER_ID);

        // Get all the billingDetailsList where messengerId equals to UPDATED_MESSENGER_ID
        defaultBillingDetailsShouldNotBeFound("messengerId.in=" + UPDATED_MESSENGER_ID);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByMessengerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where messengerId is not null
        defaultBillingDetailsShouldBeFound("messengerId.specified=true");

        // Get all the billingDetailsList where messengerId is null
        defaultBillingDetailsShouldNotBeFound("messengerId.specified=false");
    }

    @Test
    @Transactional
    void getAllBillingDetailsByMessengerIdContainsSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where messengerId contains DEFAULT_MESSENGER_ID
        defaultBillingDetailsShouldBeFound("messengerId.contains=" + DEFAULT_MESSENGER_ID);

        // Get all the billingDetailsList where messengerId contains UPDATED_MESSENGER_ID
        defaultBillingDetailsShouldNotBeFound("messengerId.contains=" + UPDATED_MESSENGER_ID);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByMessengerIdNotContainsSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where messengerId does not contain DEFAULT_MESSENGER_ID
        defaultBillingDetailsShouldNotBeFound("messengerId.doesNotContain=" + DEFAULT_MESSENGER_ID);

        // Get all the billingDetailsList where messengerId does not contain UPDATED_MESSENGER_ID
        defaultBillingDetailsShouldBeFound("messengerId.doesNotContain=" + UPDATED_MESSENGER_ID);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByDateAddedIsEqualToSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where dateAdded equals to DEFAULT_DATE_ADDED
        defaultBillingDetailsShouldBeFound("dateAdded.equals=" + DEFAULT_DATE_ADDED);

        // Get all the billingDetailsList where dateAdded equals to UPDATED_DATE_ADDED
        defaultBillingDetailsShouldNotBeFound("dateAdded.equals=" + UPDATED_DATE_ADDED);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByDateAddedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where dateAdded not equals to DEFAULT_DATE_ADDED
        defaultBillingDetailsShouldNotBeFound("dateAdded.notEquals=" + DEFAULT_DATE_ADDED);

        // Get all the billingDetailsList where dateAdded not equals to UPDATED_DATE_ADDED
        defaultBillingDetailsShouldBeFound("dateAdded.notEquals=" + UPDATED_DATE_ADDED);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByDateAddedIsInShouldWork() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where dateAdded in DEFAULT_DATE_ADDED or UPDATED_DATE_ADDED
        defaultBillingDetailsShouldBeFound("dateAdded.in=" + DEFAULT_DATE_ADDED + "," + UPDATED_DATE_ADDED);

        // Get all the billingDetailsList where dateAdded equals to UPDATED_DATE_ADDED
        defaultBillingDetailsShouldNotBeFound("dateAdded.in=" + UPDATED_DATE_ADDED);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByDateAddedIsNullOrNotNull() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where dateAdded is not null
        defaultBillingDetailsShouldBeFound("dateAdded.specified=true");

        // Get all the billingDetailsList where dateAdded is null
        defaultBillingDetailsShouldNotBeFound("dateAdded.specified=false");
    }

    @Test
    @Transactional
    void getAllBillingDetailsByInActiveIsEqualToSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where inActive equals to DEFAULT_IN_ACTIVE
        defaultBillingDetailsShouldBeFound("inActive.equals=" + DEFAULT_IN_ACTIVE);

        // Get all the billingDetailsList where inActive equals to UPDATED_IN_ACTIVE
        defaultBillingDetailsShouldNotBeFound("inActive.equals=" + UPDATED_IN_ACTIVE);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByInActiveIsNotEqualToSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where inActive not equals to DEFAULT_IN_ACTIVE
        defaultBillingDetailsShouldNotBeFound("inActive.notEquals=" + DEFAULT_IN_ACTIVE);

        // Get all the billingDetailsList where inActive not equals to UPDATED_IN_ACTIVE
        defaultBillingDetailsShouldBeFound("inActive.notEquals=" + UPDATED_IN_ACTIVE);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByInActiveIsInShouldWork() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where inActive in DEFAULT_IN_ACTIVE or UPDATED_IN_ACTIVE
        defaultBillingDetailsShouldBeFound("inActive.in=" + DEFAULT_IN_ACTIVE + "," + UPDATED_IN_ACTIVE);

        // Get all the billingDetailsList where inActive equals to UPDATED_IN_ACTIVE
        defaultBillingDetailsShouldNotBeFound("inActive.in=" + UPDATED_IN_ACTIVE);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByInActiveIsNullOrNotNull() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where inActive is not null
        defaultBillingDetailsShouldBeFound("inActive.specified=true");

        // Get all the billingDetailsList where inActive is null
        defaultBillingDetailsShouldNotBeFound("inActive.specified=false");
    }

    @Test
    @Transactional
    void getAllBillingDetailsByInActiveDateIsEqualToSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where inActiveDate equals to DEFAULT_IN_ACTIVE_DATE
        defaultBillingDetailsShouldBeFound("inActiveDate.equals=" + DEFAULT_IN_ACTIVE_DATE);

        // Get all the billingDetailsList where inActiveDate equals to UPDATED_IN_ACTIVE_DATE
        defaultBillingDetailsShouldNotBeFound("inActiveDate.equals=" + UPDATED_IN_ACTIVE_DATE);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByInActiveDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where inActiveDate not equals to DEFAULT_IN_ACTIVE_DATE
        defaultBillingDetailsShouldNotBeFound("inActiveDate.notEquals=" + DEFAULT_IN_ACTIVE_DATE);

        // Get all the billingDetailsList where inActiveDate not equals to UPDATED_IN_ACTIVE_DATE
        defaultBillingDetailsShouldBeFound("inActiveDate.notEquals=" + UPDATED_IN_ACTIVE_DATE);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByInActiveDateIsInShouldWork() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where inActiveDate in DEFAULT_IN_ACTIVE_DATE or UPDATED_IN_ACTIVE_DATE
        defaultBillingDetailsShouldBeFound("inActiveDate.in=" + DEFAULT_IN_ACTIVE_DATE + "," + UPDATED_IN_ACTIVE_DATE);

        // Get all the billingDetailsList where inActiveDate equals to UPDATED_IN_ACTIVE_DATE
        defaultBillingDetailsShouldNotBeFound("inActiveDate.in=" + UPDATED_IN_ACTIVE_DATE);
    }

    @Test
    @Transactional
    void getAllBillingDetailsByInActiveDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        // Get all the billingDetailsList where inActiveDate is not null
        defaultBillingDetailsShouldBeFound("inActiveDate.specified=true");

        // Get all the billingDetailsList where inActiveDate is null
        defaultBillingDetailsShouldNotBeFound("inActiveDate.specified=false");
    }

    @Test
    @Transactional
    void getAllBillingDetailsBySiteAccountIsEqualToSomething() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);
        SiteAccount siteAccount;
        if (TestUtil.findAll(em, SiteAccount.class).isEmpty()) {
            siteAccount = SiteAccountResourceIT.createEntity(em);
            em.persist(siteAccount);
            em.flush();
        } else {
            siteAccount = TestUtil.findAll(em, SiteAccount.class).get(0);
        }
        em.persist(siteAccount);
        em.flush();
        billingDetails.addSiteAccount(siteAccount);
        billingDetailsRepository.saveAndFlush(billingDetails);
        Long siteAccountId = siteAccount.getId();

        // Get all the billingDetailsList where siteAccount equals to siteAccountId
        defaultBillingDetailsShouldBeFound("siteAccountId.equals=" + siteAccountId);

        // Get all the billingDetailsList where siteAccount equals to (siteAccountId + 1)
        defaultBillingDetailsShouldNotBeFound("siteAccountId.equals=" + (siteAccountId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBillingDetailsShouldBeFound(String filter) throws Exception {
        restBillingDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(billingDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].contactName").value(hasItem(DEFAULT_CONTACT_NAME)))
            .andExpect(jsonPath("$.[*].address1").value(hasItem(DEFAULT_ADDRESS_1)))
            .andExpect(jsonPath("$.[*].address2").value(hasItem(DEFAULT_ADDRESS_2)))
            .andExpect(jsonPath("$.[*].address3").value(hasItem(DEFAULT_ADDRESS_3)))
            .andExpect(jsonPath("$.[*].address4").value(hasItem(DEFAULT_ADDRESS_4)))
            .andExpect(jsonPath("$.[*].address5").value(hasItem(DEFAULT_ADDRESS_5)))
            .andExpect(jsonPath("$.[*].address6").value(hasItem(DEFAULT_ADDRESS_6)))
            .andExpect(jsonPath("$.[*].dialCode").value(hasItem(DEFAULT_DIAL_CODE)))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER)))
            .andExpect(jsonPath("$.[*].messengerId").value(hasItem(DEFAULT_MESSENGER_ID)))
            .andExpect(jsonPath("$.[*].dateAdded").value(hasItem(DEFAULT_DATE_ADDED.toString())))
            .andExpect(jsonPath("$.[*].inActive").value(hasItem(DEFAULT_IN_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].inActiveDate").value(hasItem(DEFAULT_IN_ACTIVE_DATE.toString())));

        // Check, that the count call also returns 1
        restBillingDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBillingDetailsShouldNotBeFound(String filter) throws Exception {
        restBillingDetailsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBillingDetailsMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingBillingDetails() throws Exception {
        // Get the billingDetails
        restBillingDetailsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBillingDetails() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        int databaseSizeBeforeUpdate = billingDetailsRepository.findAll().size();

        // Update the billingDetails
        BillingDetails updatedBillingDetails = billingDetailsRepository.findById(billingDetails.getId()).get();
        // Disconnect from session so that the updates on updatedBillingDetails are not directly saved in db
        em.detach(updatedBillingDetails);
        updatedBillingDetails
            .contactName(UPDATED_CONTACT_NAME)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .address3(UPDATED_ADDRESS_3)
            .address4(UPDATED_ADDRESS_4)
            .address5(UPDATED_ADDRESS_5)
            .address6(UPDATED_ADDRESS_6)
            .dialCode(UPDATED_DIAL_CODE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .messengerId(UPDATED_MESSENGER_ID)
            .dateAdded(UPDATED_DATE_ADDED)
            .inActive(UPDATED_IN_ACTIVE)
            .inActiveDate(UPDATED_IN_ACTIVE_DATE);

        restBillingDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedBillingDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedBillingDetails))
            )
            .andExpect(status().isOk());

        // Validate the BillingDetails in the database
        List<BillingDetails> billingDetailsList = billingDetailsRepository.findAll();
        assertThat(billingDetailsList).hasSize(databaseSizeBeforeUpdate);
        BillingDetails testBillingDetails = billingDetailsList.get(billingDetailsList.size() - 1);
        assertThat(testBillingDetails.getContactName()).isEqualTo(UPDATED_CONTACT_NAME);
        assertThat(testBillingDetails.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testBillingDetails.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testBillingDetails.getAddress3()).isEqualTo(UPDATED_ADDRESS_3);
        assertThat(testBillingDetails.getAddress4()).isEqualTo(UPDATED_ADDRESS_4);
        assertThat(testBillingDetails.getAddress5()).isEqualTo(UPDATED_ADDRESS_5);
        assertThat(testBillingDetails.getAddress6()).isEqualTo(UPDATED_ADDRESS_6);
        assertThat(testBillingDetails.getDialCode()).isEqualTo(UPDATED_DIAL_CODE);
        assertThat(testBillingDetails.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testBillingDetails.getMessengerId()).isEqualTo(UPDATED_MESSENGER_ID);
        assertThat(testBillingDetails.getDateAdded()).isEqualTo(UPDATED_DATE_ADDED);
        assertThat(testBillingDetails.getInActive()).isEqualTo(UPDATED_IN_ACTIVE);
        assertThat(testBillingDetails.getInActiveDate()).isEqualTo(UPDATED_IN_ACTIVE_DATE);
    }

    @Test
    @Transactional
    void putNonExistingBillingDetails() throws Exception {
        int databaseSizeBeforeUpdate = billingDetailsRepository.findAll().size();
        billingDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillingDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, billingDetails.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(billingDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the BillingDetails in the database
        List<BillingDetails> billingDetailsList = billingDetailsRepository.findAll();
        assertThat(billingDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBillingDetails() throws Exception {
        int databaseSizeBeforeUpdate = billingDetailsRepository.findAll().size();
        billingDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillingDetailsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(billingDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the BillingDetails in the database
        List<BillingDetails> billingDetailsList = billingDetailsRepository.findAll();
        assertThat(billingDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBillingDetails() throws Exception {
        int databaseSizeBeforeUpdate = billingDetailsRepository.findAll().size();
        billingDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillingDetailsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(billingDetails)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the BillingDetails in the database
        List<BillingDetails> billingDetailsList = billingDetailsRepository.findAll();
        assertThat(billingDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBillingDetailsWithPatch() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        int databaseSizeBeforeUpdate = billingDetailsRepository.findAll().size();

        // Update the billingDetails using partial update
        BillingDetails partialUpdatedBillingDetails = new BillingDetails();
        partialUpdatedBillingDetails.setId(billingDetails.getId());

        partialUpdatedBillingDetails
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .address3(UPDATED_ADDRESS_3)
            .address5(UPDATED_ADDRESS_5)
            .address6(UPDATED_ADDRESS_6)
            .dialCode(UPDATED_DIAL_CODE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .messengerId(UPDATED_MESSENGER_ID)
            .dateAdded(UPDATED_DATE_ADDED)
            .inActive(UPDATED_IN_ACTIVE);

        restBillingDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBillingDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBillingDetails))
            )
            .andExpect(status().isOk());

        // Validate the BillingDetails in the database
        List<BillingDetails> billingDetailsList = billingDetailsRepository.findAll();
        assertThat(billingDetailsList).hasSize(databaseSizeBeforeUpdate);
        BillingDetails testBillingDetails = billingDetailsList.get(billingDetailsList.size() - 1);
        assertThat(testBillingDetails.getContactName()).isEqualTo(DEFAULT_CONTACT_NAME);
        assertThat(testBillingDetails.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testBillingDetails.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testBillingDetails.getAddress3()).isEqualTo(UPDATED_ADDRESS_3);
        assertThat(testBillingDetails.getAddress4()).isEqualTo(DEFAULT_ADDRESS_4);
        assertThat(testBillingDetails.getAddress5()).isEqualTo(UPDATED_ADDRESS_5);
        assertThat(testBillingDetails.getAddress6()).isEqualTo(UPDATED_ADDRESS_6);
        assertThat(testBillingDetails.getDialCode()).isEqualTo(UPDATED_DIAL_CODE);
        assertThat(testBillingDetails.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testBillingDetails.getMessengerId()).isEqualTo(UPDATED_MESSENGER_ID);
        assertThat(testBillingDetails.getDateAdded()).isEqualTo(UPDATED_DATE_ADDED);
        assertThat(testBillingDetails.getInActive()).isEqualTo(UPDATED_IN_ACTIVE);
        assertThat(testBillingDetails.getInActiveDate()).isEqualTo(DEFAULT_IN_ACTIVE_DATE);
    }

    @Test
    @Transactional
    void fullUpdateBillingDetailsWithPatch() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        int databaseSizeBeforeUpdate = billingDetailsRepository.findAll().size();

        // Update the billingDetails using partial update
        BillingDetails partialUpdatedBillingDetails = new BillingDetails();
        partialUpdatedBillingDetails.setId(billingDetails.getId());

        partialUpdatedBillingDetails
            .contactName(UPDATED_CONTACT_NAME)
            .address1(UPDATED_ADDRESS_1)
            .address2(UPDATED_ADDRESS_2)
            .address3(UPDATED_ADDRESS_3)
            .address4(UPDATED_ADDRESS_4)
            .address5(UPDATED_ADDRESS_5)
            .address6(UPDATED_ADDRESS_6)
            .dialCode(UPDATED_DIAL_CODE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .messengerId(UPDATED_MESSENGER_ID)
            .dateAdded(UPDATED_DATE_ADDED)
            .inActive(UPDATED_IN_ACTIVE)
            .inActiveDate(UPDATED_IN_ACTIVE_DATE);

        restBillingDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBillingDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBillingDetails))
            )
            .andExpect(status().isOk());

        // Validate the BillingDetails in the database
        List<BillingDetails> billingDetailsList = billingDetailsRepository.findAll();
        assertThat(billingDetailsList).hasSize(databaseSizeBeforeUpdate);
        BillingDetails testBillingDetails = billingDetailsList.get(billingDetailsList.size() - 1);
        assertThat(testBillingDetails.getContactName()).isEqualTo(UPDATED_CONTACT_NAME);
        assertThat(testBillingDetails.getAddress1()).isEqualTo(UPDATED_ADDRESS_1);
        assertThat(testBillingDetails.getAddress2()).isEqualTo(UPDATED_ADDRESS_2);
        assertThat(testBillingDetails.getAddress3()).isEqualTo(UPDATED_ADDRESS_3);
        assertThat(testBillingDetails.getAddress4()).isEqualTo(UPDATED_ADDRESS_4);
        assertThat(testBillingDetails.getAddress5()).isEqualTo(UPDATED_ADDRESS_5);
        assertThat(testBillingDetails.getAddress6()).isEqualTo(UPDATED_ADDRESS_6);
        assertThat(testBillingDetails.getDialCode()).isEqualTo(UPDATED_DIAL_CODE);
        assertThat(testBillingDetails.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testBillingDetails.getMessengerId()).isEqualTo(UPDATED_MESSENGER_ID);
        assertThat(testBillingDetails.getDateAdded()).isEqualTo(UPDATED_DATE_ADDED);
        assertThat(testBillingDetails.getInActive()).isEqualTo(UPDATED_IN_ACTIVE);
        assertThat(testBillingDetails.getInActiveDate()).isEqualTo(UPDATED_IN_ACTIVE_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingBillingDetails() throws Exception {
        int databaseSizeBeforeUpdate = billingDetailsRepository.findAll().size();
        billingDetails.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBillingDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, billingDetails.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(billingDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the BillingDetails in the database
        List<BillingDetails> billingDetailsList = billingDetailsRepository.findAll();
        assertThat(billingDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBillingDetails() throws Exception {
        int databaseSizeBeforeUpdate = billingDetailsRepository.findAll().size();
        billingDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillingDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(billingDetails))
            )
            .andExpect(status().isBadRequest());

        // Validate the BillingDetails in the database
        List<BillingDetails> billingDetailsList = billingDetailsRepository.findAll();
        assertThat(billingDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBillingDetails() throws Exception {
        int databaseSizeBeforeUpdate = billingDetailsRepository.findAll().size();
        billingDetails.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBillingDetailsMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(billingDetails))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the BillingDetails in the database
        List<BillingDetails> billingDetailsList = billingDetailsRepository.findAll();
        assertThat(billingDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBillingDetails() throws Exception {
        // Initialize the database
        billingDetailsRepository.saveAndFlush(billingDetails);

        int databaseSizeBeforeDelete = billingDetailsRepository.findAll().size();

        // Delete the billingDetails
        restBillingDetailsMockMvc
            .perform(delete(ENTITY_API_URL_ID, billingDetails.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<BillingDetails> billingDetailsList = billingDetailsRepository.findAll();
        assertThat(billingDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
