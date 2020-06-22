package com.app.share.web.rest;

import com.app.share.LetsShareApp;
import com.app.share.domain.MyPostResponse;
import com.app.share.repository.MyPostResponseRepository;
import com.app.share.service.MyPostResponseService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.app.share.domain.enumeration.MyPostReponseStatus;
/**
 * Integration tests for the {@link MyPostResponseResource} REST controller.
 */
@SpringBootTest(classes = LetsShareApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MyPostResponseResourceIT {

    private static final String DEFAULT_MY_POST_RESPONSE = "AAAAAAAAAA";
    private static final String UPDATED_MY_POST_RESPONSE = "BBBBBBBBBB";

    private static final Integer DEFAULT_POST_ID = 1;
    private static final Integer UPDATED_POST_ID = 2;

    private static final Integer DEFAULT_RESPONDER_ID = 1;
    private static final Integer UPDATED_RESPONDER_ID = 2;

    private static final MyPostReponseStatus DEFAULT_MY_POST_REPONSE_STATUS = MyPostReponseStatus.APPROVED;
    private static final MyPostReponseStatus UPDATED_MY_POST_REPONSE_STATUS = MyPostReponseStatus.REJECT;

    private static final Integer DEFAULT_CREATED_BY = 1;
    private static final Integer UPDATED_CREATED_BY = 2;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_MODIFIED_BY = 1;
    private static final Integer UPDATED_MODIFIED_BY = 2;

    private static final LocalDate DEFAULT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MyPostResponseRepository myPostResponseRepository;

    @Autowired
    private MyPostResponseService myPostResponseService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMyPostResponseMockMvc;

    private MyPostResponse myPostResponse;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyPostResponse createEntity(EntityManager em) {
        MyPostResponse myPostResponse = new MyPostResponse()
            .myPostResponse(DEFAULT_MY_POST_RESPONSE)
            .postId(DEFAULT_POST_ID)
            .responderId(DEFAULT_RESPONDER_ID)
            .myPostReponseStatus(DEFAULT_MY_POST_REPONSE_STATUS)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDate(DEFAULT_MODIFIED_DATE)
            .comments(DEFAULT_COMMENTS)
            .active(DEFAULT_ACTIVE);
        return myPostResponse;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyPostResponse createUpdatedEntity(EntityManager em) {
        MyPostResponse myPostResponse = new MyPostResponse()
            .myPostResponse(UPDATED_MY_POST_RESPONSE)
            .postId(UPDATED_POST_ID)
            .responderId(UPDATED_RESPONDER_ID)
            .myPostReponseStatus(UPDATED_MY_POST_REPONSE_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .comments(UPDATED_COMMENTS)
            .active(UPDATED_ACTIVE);
        return myPostResponse;
    }

    @BeforeEach
    public void initTest() {
        myPostResponse = createEntity(em);
    }

    @Test
    @Transactional
    public void createMyPostResponse() throws Exception {
        int databaseSizeBeforeCreate = myPostResponseRepository.findAll().size();
        // Create the MyPostResponse
        restMyPostResponseMockMvc.perform(post("/api/my-post-responses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(myPostResponse)))
            .andExpect(status().isCreated());

        // Validate the MyPostResponse in the database
        List<MyPostResponse> myPostResponseList = myPostResponseRepository.findAll();
        assertThat(myPostResponseList).hasSize(databaseSizeBeforeCreate + 1);
        MyPostResponse testMyPostResponse = myPostResponseList.get(myPostResponseList.size() - 1);
        assertThat(testMyPostResponse.getMyPostResponse()).isEqualTo(DEFAULT_MY_POST_RESPONSE);
        assertThat(testMyPostResponse.getPostId()).isEqualTo(DEFAULT_POST_ID);
        assertThat(testMyPostResponse.getResponderId()).isEqualTo(DEFAULT_RESPONDER_ID);
        assertThat(testMyPostResponse.getMyPostReponseStatus()).isEqualTo(DEFAULT_MY_POST_REPONSE_STATUS);
        assertThat(testMyPostResponse.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMyPostResponse.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testMyPostResponse.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testMyPostResponse.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testMyPostResponse.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testMyPostResponse.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMyPostResponseWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = myPostResponseRepository.findAll().size();

        // Create the MyPostResponse with an existing ID
        myPostResponse.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMyPostResponseMockMvc.perform(post("/api/my-post-responses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(myPostResponse)))
            .andExpect(status().isBadRequest());

        // Validate the MyPostResponse in the database
        List<MyPostResponse> myPostResponseList = myPostResponseRepository.findAll();
        assertThat(myPostResponseList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMyPostResponses() throws Exception {
        // Initialize the database
        myPostResponseRepository.saveAndFlush(myPostResponse);

        // Get all the myPostResponseList
        restMyPostResponseMockMvc.perform(get("/api/my-post-responses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(myPostResponse.getId().intValue())))
            .andExpect(jsonPath("$.[*].myPostResponse").value(hasItem(DEFAULT_MY_POST_RESPONSE)))
            .andExpect(jsonPath("$.[*].postId").value(hasItem(DEFAULT_POST_ID)))
            .andExpect(jsonPath("$.[*].responderId").value(hasItem(DEFAULT_RESPONDER_ID)))
            .andExpect(jsonPath("$.[*].myPostReponseStatus").value(hasItem(DEFAULT_MY_POST_REPONSE_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMyPostResponse() throws Exception {
        // Initialize the database
        myPostResponseRepository.saveAndFlush(myPostResponse);

        // Get the myPostResponse
        restMyPostResponseMockMvc.perform(get("/api/my-post-responses/{id}", myPostResponse.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(myPostResponse.getId().intValue()))
            .andExpect(jsonPath("$.myPostResponse").value(DEFAULT_MY_POST_RESPONSE))
            .andExpect(jsonPath("$.postId").value(DEFAULT_POST_ID))
            .andExpect(jsonPath("$.responderId").value(DEFAULT_RESPONDER_ID))
            .andExpect(jsonPath("$.myPostReponseStatus").value(DEFAULT_MY_POST_REPONSE_STATUS.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingMyPostResponse() throws Exception {
        // Get the myPostResponse
        restMyPostResponseMockMvc.perform(get("/api/my-post-responses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMyPostResponse() throws Exception {
        // Initialize the database
        myPostResponseService.save(myPostResponse);

        int databaseSizeBeforeUpdate = myPostResponseRepository.findAll().size();

        // Update the myPostResponse
        MyPostResponse updatedMyPostResponse = myPostResponseRepository.findById(myPostResponse.getId()).get();
        // Disconnect from session so that the updates on updatedMyPostResponse are not directly saved in db
        em.detach(updatedMyPostResponse);
        updatedMyPostResponse
            .myPostResponse(UPDATED_MY_POST_RESPONSE)
            .postId(UPDATED_POST_ID)
            .responderId(UPDATED_RESPONDER_ID)
            .myPostReponseStatus(UPDATED_MY_POST_REPONSE_STATUS)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .comments(UPDATED_COMMENTS)
            .active(UPDATED_ACTIVE);

        restMyPostResponseMockMvc.perform(put("/api/my-post-responses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMyPostResponse)))
            .andExpect(status().isOk());

        // Validate the MyPostResponse in the database
        List<MyPostResponse> myPostResponseList = myPostResponseRepository.findAll();
        assertThat(myPostResponseList).hasSize(databaseSizeBeforeUpdate);
        MyPostResponse testMyPostResponse = myPostResponseList.get(myPostResponseList.size() - 1);
        assertThat(testMyPostResponse.getMyPostResponse()).isEqualTo(UPDATED_MY_POST_RESPONSE);
        assertThat(testMyPostResponse.getPostId()).isEqualTo(UPDATED_POST_ID);
        assertThat(testMyPostResponse.getResponderId()).isEqualTo(UPDATED_RESPONDER_ID);
        assertThat(testMyPostResponse.getMyPostReponseStatus()).isEqualTo(UPDATED_MY_POST_REPONSE_STATUS);
        assertThat(testMyPostResponse.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMyPostResponse.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMyPostResponse.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testMyPostResponse.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testMyPostResponse.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testMyPostResponse.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMyPostResponse() throws Exception {
        int databaseSizeBeforeUpdate = myPostResponseRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMyPostResponseMockMvc.perform(put("/api/my-post-responses")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(myPostResponse)))
            .andExpect(status().isBadRequest());

        // Validate the MyPostResponse in the database
        List<MyPostResponse> myPostResponseList = myPostResponseRepository.findAll();
        assertThat(myPostResponseList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMyPostResponse() throws Exception {
        // Initialize the database
        myPostResponseService.save(myPostResponse);

        int databaseSizeBeforeDelete = myPostResponseRepository.findAll().size();

        // Delete the myPostResponse
        restMyPostResponseMockMvc.perform(delete("/api/my-post-responses/{id}", myPostResponse.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MyPostResponse> myPostResponseList = myPostResponseRepository.findAll();
        assertThat(myPostResponseList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
