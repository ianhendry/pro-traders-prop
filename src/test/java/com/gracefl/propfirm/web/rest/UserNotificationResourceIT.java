package com.gracefl.propfirm.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.gracefl.propfirm.IntegrationTest;
import com.gracefl.propfirm.domain.TradersChallengeTracker;
import com.gracefl.propfirm.domain.UserNotification;
import com.gracefl.propfirm.repository.UserNotificationRepository;
import com.gracefl.propfirm.service.criteria.UserNotificationCriteria;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link UserNotificationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UserNotificationResourceIT {

    private static final String DEFAULT_COMMENT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENT_BODY = "AAAAAAAAAA";
    private static final String UPDATED_COMMENT_BODY = "BBBBBBBBBB";

    private static final byte[] DEFAULT_COMMENT_MEDIA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_COMMENT_MEDIA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_COMMENT_MEDIA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_COMMENT_MEDIA_CONTENT_TYPE = "image/png";

    private static final Instant DEFAULT_DATE_ADDED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_ADDED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_MAKE_PUBLIC_VISIBLE_ON_SITE = false;
    private static final Boolean UPDATED_MAKE_PUBLIC_VISIBLE_ON_SITE = true;

    private static final String ENTITY_API_URL = "/api/user-notifications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UserNotificationRepository userNotificationRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserNotificationMockMvc;

    private UserNotification userNotification;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserNotification createEntity(EntityManager em) {
        UserNotification userNotification = new UserNotification()
            .commentTitle(DEFAULT_COMMENT_TITLE)
            .commentBody(DEFAULT_COMMENT_BODY)
            .commentMedia(DEFAULT_COMMENT_MEDIA)
            .commentMediaContentType(DEFAULT_COMMENT_MEDIA_CONTENT_TYPE)
            .dateAdded(DEFAULT_DATE_ADDED)
            .makePublicVisibleOnSite(DEFAULT_MAKE_PUBLIC_VISIBLE_ON_SITE);
        return userNotification;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserNotification createUpdatedEntity(EntityManager em) {
        UserNotification userNotification = new UserNotification()
            .commentTitle(UPDATED_COMMENT_TITLE)
            .commentBody(UPDATED_COMMENT_BODY)
            .commentMedia(UPDATED_COMMENT_MEDIA)
            .commentMediaContentType(UPDATED_COMMENT_MEDIA_CONTENT_TYPE)
            .dateAdded(UPDATED_DATE_ADDED)
            .makePublicVisibleOnSite(UPDATED_MAKE_PUBLIC_VISIBLE_ON_SITE);
        return userNotification;
    }

    @BeforeEach
    public void initTest() {
        userNotification = createEntity(em);
    }

    @Test
    @Transactional
    void createUserNotification() throws Exception {
        int databaseSizeBeforeCreate = userNotificationRepository.findAll().size();
        // Create the UserNotification
        restUserNotificationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userNotification))
            )
            .andExpect(status().isCreated());

        // Validate the UserNotification in the database
        List<UserNotification> userNotificationList = userNotificationRepository.findAll();
        assertThat(userNotificationList).hasSize(databaseSizeBeforeCreate + 1);
        UserNotification testUserNotification = userNotificationList.get(userNotificationList.size() - 1);
        assertThat(testUserNotification.getCommentTitle()).isEqualTo(DEFAULT_COMMENT_TITLE);
        assertThat(testUserNotification.getCommentBody()).isEqualTo(DEFAULT_COMMENT_BODY);
        assertThat(testUserNotification.getCommentMedia()).isEqualTo(DEFAULT_COMMENT_MEDIA);
        assertThat(testUserNotification.getCommentMediaContentType()).isEqualTo(DEFAULT_COMMENT_MEDIA_CONTENT_TYPE);
        assertThat(testUserNotification.getDateAdded()).isEqualTo(DEFAULT_DATE_ADDED);
        assertThat(testUserNotification.getMakePublicVisibleOnSite()).isEqualTo(DEFAULT_MAKE_PUBLIC_VISIBLE_ON_SITE);
    }

    @Test
    @Transactional
    void createUserNotificationWithExistingId() throws Exception {
        // Create the UserNotification with an existing ID
        userNotification.setId(1L);

        int databaseSizeBeforeCreate = userNotificationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserNotificationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userNotification))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserNotification in the database
        List<UserNotification> userNotificationList = userNotificationRepository.findAll();
        assertThat(userNotificationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCommentTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = userNotificationRepository.findAll().size();
        // set the field null
        userNotification.setCommentTitle(null);

        // Create the UserNotification, which fails.

        restUserNotificationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userNotification))
            )
            .andExpect(status().isBadRequest());

        List<UserNotification> userNotificationList = userNotificationRepository.findAll();
        assertThat(userNotificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateAddedIsRequired() throws Exception {
        int databaseSizeBeforeTest = userNotificationRepository.findAll().size();
        // set the field null
        userNotification.setDateAdded(null);

        // Create the UserNotification, which fails.

        restUserNotificationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userNotification))
            )
            .andExpect(status().isBadRequest());

        List<UserNotification> userNotificationList = userNotificationRepository.findAll();
        assertThat(userNotificationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllUserNotifications() throws Exception {
        // Initialize the database
        userNotificationRepository.saveAndFlush(userNotification);

        // Get all the userNotificationList
        restUserNotificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userNotification.getId().intValue())))
            .andExpect(jsonPath("$.[*].commentTitle").value(hasItem(DEFAULT_COMMENT_TITLE)))
            .andExpect(jsonPath("$.[*].commentBody").value(hasItem(DEFAULT_COMMENT_BODY.toString())))
            .andExpect(jsonPath("$.[*].commentMediaContentType").value(hasItem(DEFAULT_COMMENT_MEDIA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].commentMedia").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMMENT_MEDIA))))
            .andExpect(jsonPath("$.[*].dateAdded").value(hasItem(DEFAULT_DATE_ADDED.toString())))
            .andExpect(jsonPath("$.[*].makePublicVisibleOnSite").value(hasItem(DEFAULT_MAKE_PUBLIC_VISIBLE_ON_SITE.booleanValue())));
    }

    @Test
    @Transactional
    void getUserNotification() throws Exception {
        // Initialize the database
        userNotificationRepository.saveAndFlush(userNotification);

        // Get the userNotification
        restUserNotificationMockMvc
            .perform(get(ENTITY_API_URL_ID, userNotification.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userNotification.getId().intValue()))
            .andExpect(jsonPath("$.commentTitle").value(DEFAULT_COMMENT_TITLE))
            .andExpect(jsonPath("$.commentBody").value(DEFAULT_COMMENT_BODY.toString()))
            .andExpect(jsonPath("$.commentMediaContentType").value(DEFAULT_COMMENT_MEDIA_CONTENT_TYPE))
            .andExpect(jsonPath("$.commentMedia").value(Base64Utils.encodeToString(DEFAULT_COMMENT_MEDIA)))
            .andExpect(jsonPath("$.dateAdded").value(DEFAULT_DATE_ADDED.toString()))
            .andExpect(jsonPath("$.makePublicVisibleOnSite").value(DEFAULT_MAKE_PUBLIC_VISIBLE_ON_SITE.booleanValue()));
    }

    @Test
    @Transactional
    void getUserNotificationsByIdFiltering() throws Exception {
        // Initialize the database
        userNotificationRepository.saveAndFlush(userNotification);

        Long id = userNotification.getId();

        defaultUserNotificationShouldBeFound("id.equals=" + id);
        defaultUserNotificationShouldNotBeFound("id.notEquals=" + id);

        defaultUserNotificationShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUserNotificationShouldNotBeFound("id.greaterThan=" + id);

        defaultUserNotificationShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUserNotificationShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllUserNotificationsByCommentTitleIsEqualToSomething() throws Exception {
        // Initialize the database
        userNotificationRepository.saveAndFlush(userNotification);

        // Get all the userNotificationList where commentTitle equals to DEFAULT_COMMENT_TITLE
        defaultUserNotificationShouldBeFound("commentTitle.equals=" + DEFAULT_COMMENT_TITLE);

        // Get all the userNotificationList where commentTitle equals to UPDATED_COMMENT_TITLE
        defaultUserNotificationShouldNotBeFound("commentTitle.equals=" + UPDATED_COMMENT_TITLE);
    }

    @Test
    @Transactional
    void getAllUserNotificationsByCommentTitleIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userNotificationRepository.saveAndFlush(userNotification);

        // Get all the userNotificationList where commentTitle not equals to DEFAULT_COMMENT_TITLE
        defaultUserNotificationShouldNotBeFound("commentTitle.notEquals=" + DEFAULT_COMMENT_TITLE);

        // Get all the userNotificationList where commentTitle not equals to UPDATED_COMMENT_TITLE
        defaultUserNotificationShouldBeFound("commentTitle.notEquals=" + UPDATED_COMMENT_TITLE);
    }

    @Test
    @Transactional
    void getAllUserNotificationsByCommentTitleIsInShouldWork() throws Exception {
        // Initialize the database
        userNotificationRepository.saveAndFlush(userNotification);

        // Get all the userNotificationList where commentTitle in DEFAULT_COMMENT_TITLE or UPDATED_COMMENT_TITLE
        defaultUserNotificationShouldBeFound("commentTitle.in=" + DEFAULT_COMMENT_TITLE + "," + UPDATED_COMMENT_TITLE);

        // Get all the userNotificationList where commentTitle equals to UPDATED_COMMENT_TITLE
        defaultUserNotificationShouldNotBeFound("commentTitle.in=" + UPDATED_COMMENT_TITLE);
    }

    @Test
    @Transactional
    void getAllUserNotificationsByCommentTitleIsNullOrNotNull() throws Exception {
        // Initialize the database
        userNotificationRepository.saveAndFlush(userNotification);

        // Get all the userNotificationList where commentTitle is not null
        defaultUserNotificationShouldBeFound("commentTitle.specified=true");

        // Get all the userNotificationList where commentTitle is null
        defaultUserNotificationShouldNotBeFound("commentTitle.specified=false");
    }

    @Test
    @Transactional
    void getAllUserNotificationsByCommentTitleContainsSomething() throws Exception {
        // Initialize the database
        userNotificationRepository.saveAndFlush(userNotification);

        // Get all the userNotificationList where commentTitle contains DEFAULT_COMMENT_TITLE
        defaultUserNotificationShouldBeFound("commentTitle.contains=" + DEFAULT_COMMENT_TITLE);

        // Get all the userNotificationList where commentTitle contains UPDATED_COMMENT_TITLE
        defaultUserNotificationShouldNotBeFound("commentTitle.contains=" + UPDATED_COMMENT_TITLE);
    }

    @Test
    @Transactional
    void getAllUserNotificationsByCommentTitleNotContainsSomething() throws Exception {
        // Initialize the database
        userNotificationRepository.saveAndFlush(userNotification);

        // Get all the userNotificationList where commentTitle does not contain DEFAULT_COMMENT_TITLE
        defaultUserNotificationShouldNotBeFound("commentTitle.doesNotContain=" + DEFAULT_COMMENT_TITLE);

        // Get all the userNotificationList where commentTitle does not contain UPDATED_COMMENT_TITLE
        defaultUserNotificationShouldBeFound("commentTitle.doesNotContain=" + UPDATED_COMMENT_TITLE);
    }

    @Test
    @Transactional
    void getAllUserNotificationsByDateAddedIsEqualToSomething() throws Exception {
        // Initialize the database
        userNotificationRepository.saveAndFlush(userNotification);

        // Get all the userNotificationList where dateAdded equals to DEFAULT_DATE_ADDED
        defaultUserNotificationShouldBeFound("dateAdded.equals=" + DEFAULT_DATE_ADDED);

        // Get all the userNotificationList where dateAdded equals to UPDATED_DATE_ADDED
        defaultUserNotificationShouldNotBeFound("dateAdded.equals=" + UPDATED_DATE_ADDED);
    }

    @Test
    @Transactional
    void getAllUserNotificationsByDateAddedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userNotificationRepository.saveAndFlush(userNotification);

        // Get all the userNotificationList where dateAdded not equals to DEFAULT_DATE_ADDED
        defaultUserNotificationShouldNotBeFound("dateAdded.notEquals=" + DEFAULT_DATE_ADDED);

        // Get all the userNotificationList where dateAdded not equals to UPDATED_DATE_ADDED
        defaultUserNotificationShouldBeFound("dateAdded.notEquals=" + UPDATED_DATE_ADDED);
    }

    @Test
    @Transactional
    void getAllUserNotificationsByDateAddedIsInShouldWork() throws Exception {
        // Initialize the database
        userNotificationRepository.saveAndFlush(userNotification);

        // Get all the userNotificationList where dateAdded in DEFAULT_DATE_ADDED or UPDATED_DATE_ADDED
        defaultUserNotificationShouldBeFound("dateAdded.in=" + DEFAULT_DATE_ADDED + "," + UPDATED_DATE_ADDED);

        // Get all the userNotificationList where dateAdded equals to UPDATED_DATE_ADDED
        defaultUserNotificationShouldNotBeFound("dateAdded.in=" + UPDATED_DATE_ADDED);
    }

    @Test
    @Transactional
    void getAllUserNotificationsByDateAddedIsNullOrNotNull() throws Exception {
        // Initialize the database
        userNotificationRepository.saveAndFlush(userNotification);

        // Get all the userNotificationList where dateAdded is not null
        defaultUserNotificationShouldBeFound("dateAdded.specified=true");

        // Get all the userNotificationList where dateAdded is null
        defaultUserNotificationShouldNotBeFound("dateAdded.specified=false");
    }

    @Test
    @Transactional
    void getAllUserNotificationsByMakePublicVisibleOnSiteIsEqualToSomething() throws Exception {
        // Initialize the database
        userNotificationRepository.saveAndFlush(userNotification);

        // Get all the userNotificationList where makePublicVisibleOnSite equals to DEFAULT_MAKE_PUBLIC_VISIBLE_ON_SITE
        defaultUserNotificationShouldBeFound("makePublicVisibleOnSite.equals=" + DEFAULT_MAKE_PUBLIC_VISIBLE_ON_SITE);

        // Get all the userNotificationList where makePublicVisibleOnSite equals to UPDATED_MAKE_PUBLIC_VISIBLE_ON_SITE
        defaultUserNotificationShouldNotBeFound("makePublicVisibleOnSite.equals=" + UPDATED_MAKE_PUBLIC_VISIBLE_ON_SITE);
    }

    @Test
    @Transactional
    void getAllUserNotificationsByMakePublicVisibleOnSiteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        userNotificationRepository.saveAndFlush(userNotification);

        // Get all the userNotificationList where makePublicVisibleOnSite not equals to DEFAULT_MAKE_PUBLIC_VISIBLE_ON_SITE
        defaultUserNotificationShouldNotBeFound("makePublicVisibleOnSite.notEquals=" + DEFAULT_MAKE_PUBLIC_VISIBLE_ON_SITE);

        // Get all the userNotificationList where makePublicVisibleOnSite not equals to UPDATED_MAKE_PUBLIC_VISIBLE_ON_SITE
        defaultUserNotificationShouldBeFound("makePublicVisibleOnSite.notEquals=" + UPDATED_MAKE_PUBLIC_VISIBLE_ON_SITE);
    }

    @Test
    @Transactional
    void getAllUserNotificationsByMakePublicVisibleOnSiteIsInShouldWork() throws Exception {
        // Initialize the database
        userNotificationRepository.saveAndFlush(userNotification);

        // Get all the userNotificationList where makePublicVisibleOnSite in DEFAULT_MAKE_PUBLIC_VISIBLE_ON_SITE or UPDATED_MAKE_PUBLIC_VISIBLE_ON_SITE
        defaultUserNotificationShouldBeFound(
            "makePublicVisibleOnSite.in=" + DEFAULT_MAKE_PUBLIC_VISIBLE_ON_SITE + "," + UPDATED_MAKE_PUBLIC_VISIBLE_ON_SITE
        );

        // Get all the userNotificationList where makePublicVisibleOnSite equals to UPDATED_MAKE_PUBLIC_VISIBLE_ON_SITE
        defaultUserNotificationShouldNotBeFound("makePublicVisibleOnSite.in=" + UPDATED_MAKE_PUBLIC_VISIBLE_ON_SITE);
    }

    @Test
    @Transactional
    void getAllUserNotificationsByMakePublicVisibleOnSiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        userNotificationRepository.saveAndFlush(userNotification);

        // Get all the userNotificationList where makePublicVisibleOnSite is not null
        defaultUserNotificationShouldBeFound("makePublicVisibleOnSite.specified=true");

        // Get all the userNotificationList where makePublicVisibleOnSite is null
        defaultUserNotificationShouldNotBeFound("makePublicVisibleOnSite.specified=false");
    }

    @Test
    @Transactional
    void getAllUserNotificationsByTradersChallengeTrackerIsEqualToSomething() throws Exception {
        // Initialize the database
        userNotificationRepository.saveAndFlush(userNotification);
        TradersChallengeTracker tradersChallengeTracker;
        if (TestUtil.findAll(em, TradersChallengeTracker.class).isEmpty()) {
            tradersChallengeTracker = TradersChallengeTrackerResourceIT.createEntity(em);
            em.persist(tradersChallengeTracker);
            em.flush();
        } else {
            tradersChallengeTracker = TestUtil.findAll(em, TradersChallengeTracker.class).get(0);
        }
        em.persist(tradersChallengeTracker);
        em.flush();
        userNotification.setTradersChallengeTracker(tradersChallengeTracker);
        userNotificationRepository.saveAndFlush(userNotification);
        Long tradersChallengeTrackerId = tradersChallengeTracker.getId();

        // Get all the userNotificationList where tradersChallengeTracker equals to tradersChallengeTrackerId
        defaultUserNotificationShouldBeFound("tradersChallengeTrackerId.equals=" + tradersChallengeTrackerId);

        // Get all the userNotificationList where tradersChallengeTracker equals to (tradersChallengeTrackerId + 1)
        defaultUserNotificationShouldNotBeFound("tradersChallengeTrackerId.equals=" + (tradersChallengeTrackerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserNotificationShouldBeFound(String filter) throws Exception {
        restUserNotificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userNotification.getId().intValue())))
            .andExpect(jsonPath("$.[*].commentTitle").value(hasItem(DEFAULT_COMMENT_TITLE)))
            .andExpect(jsonPath("$.[*].commentBody").value(hasItem(DEFAULT_COMMENT_BODY.toString())))
            .andExpect(jsonPath("$.[*].commentMediaContentType").value(hasItem(DEFAULT_COMMENT_MEDIA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].commentMedia").value(hasItem(Base64Utils.encodeToString(DEFAULT_COMMENT_MEDIA))))
            .andExpect(jsonPath("$.[*].dateAdded").value(hasItem(DEFAULT_DATE_ADDED.toString())))
            .andExpect(jsonPath("$.[*].makePublicVisibleOnSite").value(hasItem(DEFAULT_MAKE_PUBLIC_VISIBLE_ON_SITE.booleanValue())));

        // Check, that the count call also returns 1
        restUserNotificationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserNotificationShouldNotBeFound(String filter) throws Exception {
        restUserNotificationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserNotificationMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingUserNotification() throws Exception {
        // Get the userNotification
        restUserNotificationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUserNotification() throws Exception {
        // Initialize the database
        userNotificationRepository.saveAndFlush(userNotification);

        int databaseSizeBeforeUpdate = userNotificationRepository.findAll().size();

        // Update the userNotification
        UserNotification updatedUserNotification = userNotificationRepository.findById(userNotification.getId()).get();
        // Disconnect from session so that the updates on updatedUserNotification are not directly saved in db
        em.detach(updatedUserNotification);
        updatedUserNotification
            .commentTitle(UPDATED_COMMENT_TITLE)
            .commentBody(UPDATED_COMMENT_BODY)
            .commentMedia(UPDATED_COMMENT_MEDIA)
            .commentMediaContentType(UPDATED_COMMENT_MEDIA_CONTENT_TYPE)
            .dateAdded(UPDATED_DATE_ADDED)
            .makePublicVisibleOnSite(UPDATED_MAKE_PUBLIC_VISIBLE_ON_SITE);

        restUserNotificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedUserNotification.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedUserNotification))
            )
            .andExpect(status().isOk());

        // Validate the UserNotification in the database
        List<UserNotification> userNotificationList = userNotificationRepository.findAll();
        assertThat(userNotificationList).hasSize(databaseSizeBeforeUpdate);
        UserNotification testUserNotification = userNotificationList.get(userNotificationList.size() - 1);
        assertThat(testUserNotification.getCommentTitle()).isEqualTo(UPDATED_COMMENT_TITLE);
        assertThat(testUserNotification.getCommentBody()).isEqualTo(UPDATED_COMMENT_BODY);
        assertThat(testUserNotification.getCommentMedia()).isEqualTo(UPDATED_COMMENT_MEDIA);
        assertThat(testUserNotification.getCommentMediaContentType()).isEqualTo(UPDATED_COMMENT_MEDIA_CONTENT_TYPE);
        assertThat(testUserNotification.getDateAdded()).isEqualTo(UPDATED_DATE_ADDED);
        assertThat(testUserNotification.getMakePublicVisibleOnSite()).isEqualTo(UPDATED_MAKE_PUBLIC_VISIBLE_ON_SITE);
    }

    @Test
    @Transactional
    void putNonExistingUserNotification() throws Exception {
        int databaseSizeBeforeUpdate = userNotificationRepository.findAll().size();
        userNotification.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserNotificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, userNotification.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userNotification))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserNotification in the database
        List<UserNotification> userNotificationList = userNotificationRepository.findAll();
        assertThat(userNotificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUserNotification() throws Exception {
        int databaseSizeBeforeUpdate = userNotificationRepository.findAll().size();
        userNotification.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserNotificationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(userNotification))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserNotification in the database
        List<UserNotification> userNotificationList = userNotificationRepository.findAll();
        assertThat(userNotificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUserNotification() throws Exception {
        int databaseSizeBeforeUpdate = userNotificationRepository.findAll().size();
        userNotification.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserNotificationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(userNotification))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserNotification in the database
        List<UserNotification> userNotificationList = userNotificationRepository.findAll();
        assertThat(userNotificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUserNotificationWithPatch() throws Exception {
        // Initialize the database
        userNotificationRepository.saveAndFlush(userNotification);

        int databaseSizeBeforeUpdate = userNotificationRepository.findAll().size();

        // Update the userNotification using partial update
        UserNotification partialUpdatedUserNotification = new UserNotification();
        partialUpdatedUserNotification.setId(userNotification.getId());

        partialUpdatedUserNotification
            .commentBody(UPDATED_COMMENT_BODY)
            .commentMedia(UPDATED_COMMENT_MEDIA)
            .commentMediaContentType(UPDATED_COMMENT_MEDIA_CONTENT_TYPE)
            .makePublicVisibleOnSite(UPDATED_MAKE_PUBLIC_VISIBLE_ON_SITE);

        restUserNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserNotification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserNotification))
            )
            .andExpect(status().isOk());

        // Validate the UserNotification in the database
        List<UserNotification> userNotificationList = userNotificationRepository.findAll();
        assertThat(userNotificationList).hasSize(databaseSizeBeforeUpdate);
        UserNotification testUserNotification = userNotificationList.get(userNotificationList.size() - 1);
        assertThat(testUserNotification.getCommentTitle()).isEqualTo(DEFAULT_COMMENT_TITLE);
        assertThat(testUserNotification.getCommentBody()).isEqualTo(UPDATED_COMMENT_BODY);
        assertThat(testUserNotification.getCommentMedia()).isEqualTo(UPDATED_COMMENT_MEDIA);
        assertThat(testUserNotification.getCommentMediaContentType()).isEqualTo(UPDATED_COMMENT_MEDIA_CONTENT_TYPE);
        assertThat(testUserNotification.getDateAdded()).isEqualTo(DEFAULT_DATE_ADDED);
        assertThat(testUserNotification.getMakePublicVisibleOnSite()).isEqualTo(UPDATED_MAKE_PUBLIC_VISIBLE_ON_SITE);
    }

    @Test
    @Transactional
    void fullUpdateUserNotificationWithPatch() throws Exception {
        // Initialize the database
        userNotificationRepository.saveAndFlush(userNotification);

        int databaseSizeBeforeUpdate = userNotificationRepository.findAll().size();

        // Update the userNotification using partial update
        UserNotification partialUpdatedUserNotification = new UserNotification();
        partialUpdatedUserNotification.setId(userNotification.getId());

        partialUpdatedUserNotification
            .commentTitle(UPDATED_COMMENT_TITLE)
            .commentBody(UPDATED_COMMENT_BODY)
            .commentMedia(UPDATED_COMMENT_MEDIA)
            .commentMediaContentType(UPDATED_COMMENT_MEDIA_CONTENT_TYPE)
            .dateAdded(UPDATED_DATE_ADDED)
            .makePublicVisibleOnSite(UPDATED_MAKE_PUBLIC_VISIBLE_ON_SITE);

        restUserNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUserNotification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUserNotification))
            )
            .andExpect(status().isOk());

        // Validate the UserNotification in the database
        List<UserNotification> userNotificationList = userNotificationRepository.findAll();
        assertThat(userNotificationList).hasSize(databaseSizeBeforeUpdate);
        UserNotification testUserNotification = userNotificationList.get(userNotificationList.size() - 1);
        assertThat(testUserNotification.getCommentTitle()).isEqualTo(UPDATED_COMMENT_TITLE);
        assertThat(testUserNotification.getCommentBody()).isEqualTo(UPDATED_COMMENT_BODY);
        assertThat(testUserNotification.getCommentMedia()).isEqualTo(UPDATED_COMMENT_MEDIA);
        assertThat(testUserNotification.getCommentMediaContentType()).isEqualTo(UPDATED_COMMENT_MEDIA_CONTENT_TYPE);
        assertThat(testUserNotification.getDateAdded()).isEqualTo(UPDATED_DATE_ADDED);
        assertThat(testUserNotification.getMakePublicVisibleOnSite()).isEqualTo(UPDATED_MAKE_PUBLIC_VISIBLE_ON_SITE);
    }

    @Test
    @Transactional
    void patchNonExistingUserNotification() throws Exception {
        int databaseSizeBeforeUpdate = userNotificationRepository.findAll().size();
        userNotification.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, userNotification.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userNotification))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserNotification in the database
        List<UserNotification> userNotificationList = userNotificationRepository.findAll();
        assertThat(userNotificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUserNotification() throws Exception {
        int databaseSizeBeforeUpdate = userNotificationRepository.findAll().size();
        userNotification.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userNotification))
            )
            .andExpect(status().isBadRequest());

        // Validate the UserNotification in the database
        List<UserNotification> userNotificationList = userNotificationRepository.findAll();
        assertThat(userNotificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUserNotification() throws Exception {
        int databaseSizeBeforeUpdate = userNotificationRepository.findAll().size();
        userNotification.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUserNotificationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(userNotification))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UserNotification in the database
        List<UserNotification> userNotificationList = userNotificationRepository.findAll();
        assertThat(userNotificationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUserNotification() throws Exception {
        // Initialize the database
        userNotificationRepository.saveAndFlush(userNotification);

        int databaseSizeBeforeDelete = userNotificationRepository.findAll().size();

        // Delete the userNotification
        restUserNotificationMockMvc
            .perform(delete(ENTITY_API_URL_ID, userNotification.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserNotification> userNotificationList = userNotificationRepository.findAll();
        assertThat(userNotificationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
