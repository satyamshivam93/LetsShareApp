package com.app.share.web.rest;

import com.app.share.LetsShareApp;
import com.app.share.domain.MyPost;
import com.app.share.repository.MyPostRepository;
import com.app.share.service.MyPostService;

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

import com.app.share.domain.enumeration.UserType;
import com.app.share.domain.enumeration.ItemType;
import com.app.share.domain.enumeration.MyPostStatus;
/**
 * Integration tests for the {@link MyPostResource} REST controller.
 */
@SpringBootTest(classes = LetsShareApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class MyPostResourceIT {

    private static final UserType DEFAULT_USER_TYPE = UserType.DONOR;
    private static final UserType UPDATED_USER_TYPE = UserType.RECEIVER;

    private static final ItemType DEFAULT_ITEM_TYPE = ItemType.BOOKS;
    private static final ItemType UPDATED_ITEM_TYPE = ItemType.CLOTHS;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_CREATED_BY = 1;
    private static final Integer UPDATED_CREATED_BY = 2;

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_MODIFIED_BY = 1;
    private static final Integer UPDATED_MODIFIED_BY = 2;

    private static final LocalDate DEFAULT_MODIFIED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_MODIFIED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final MyPostStatus DEFAULT_MY_POST_STATUS = MyPostStatus.AVAILABLE;
    private static final MyPostStatus UPDATED_MY_POST_STATUS = MyPostStatus.UNAVAILABLE;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    @Autowired
    private MyPostRepository myPostRepository;

    @Autowired
    private MyPostService myPostService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMyPostMockMvc;

    private MyPost myPost;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyPost createEntity(EntityManager em) {
        MyPost myPost = new MyPost()
            .userType(DEFAULT_USER_TYPE)
            .itemType(DEFAULT_ITEM_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .modifiedBy(DEFAULT_MODIFIED_BY)
            .modifiedDate(DEFAULT_MODIFIED_DATE)
            .myPostStatus(DEFAULT_MY_POST_STATUS)
            .active(DEFAULT_ACTIVE);
        return myPost;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MyPost createUpdatedEntity(EntityManager em) {
        MyPost myPost = new MyPost()
            .userType(UPDATED_USER_TYPE)
            .itemType(UPDATED_ITEM_TYPE)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .myPostStatus(UPDATED_MY_POST_STATUS)
            .active(UPDATED_ACTIVE);
        return myPost;
    }

    @BeforeEach
    public void initTest() {
        myPost = createEntity(em);
    }

    @Test
    @Transactional
    public void createMyPost() throws Exception {
        int databaseSizeBeforeCreate = myPostRepository.findAll().size();
        // Create the MyPost
        restMyPostMockMvc.perform(post("/api/my-posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(myPost)))
            .andExpect(status().isCreated());

        // Validate the MyPost in the database
        List<MyPost> myPostList = myPostRepository.findAll();
        assertThat(myPostList).hasSize(databaseSizeBeforeCreate + 1);
        MyPost testMyPost = myPostList.get(myPostList.size() - 1);
        assertThat(testMyPost.getUserType()).isEqualTo(DEFAULT_USER_TYPE);
        assertThat(testMyPost.getItemType()).isEqualTo(DEFAULT_ITEM_TYPE);
        assertThat(testMyPost.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMyPost.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testMyPost.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testMyPost.getModifiedBy()).isEqualTo(DEFAULT_MODIFIED_BY);
        assertThat(testMyPost.getModifiedDate()).isEqualTo(DEFAULT_MODIFIED_DATE);
        assertThat(testMyPost.getMyPostStatus()).isEqualTo(DEFAULT_MY_POST_STATUS);
        assertThat(testMyPost.isActive()).isEqualTo(DEFAULT_ACTIVE);
    }

    @Test
    @Transactional
    public void createMyPostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = myPostRepository.findAll().size();

        // Create the MyPost with an existing ID
        myPost.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMyPostMockMvc.perform(post("/api/my-posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(myPost)))
            .andExpect(status().isBadRequest());

        // Validate the MyPost in the database
        List<MyPost> myPostList = myPostRepository.findAll();
        assertThat(myPostList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMyPosts() throws Exception {
        // Initialize the database
        myPostRepository.saveAndFlush(myPost);

        // Get all the myPostList
        restMyPostMockMvc.perform(get("/api/my-posts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(myPost.getId().intValue())))
            .andExpect(jsonPath("$.[*].userType").value(hasItem(DEFAULT_USER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].itemType").value(hasItem(DEFAULT_ITEM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].modifiedBy").value(hasItem(DEFAULT_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].modifiedDate").value(hasItem(DEFAULT_MODIFIED_DATE.toString())))
            .andExpect(jsonPath("$.[*].myPostStatus").value(hasItem(DEFAULT_MY_POST_STATUS.toString())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMyPost() throws Exception {
        // Initialize the database
        myPostRepository.saveAndFlush(myPost);

        // Get the myPost
        restMyPostMockMvc.perform(get("/api/my-posts/{id}", myPost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(myPost.getId().intValue()))
            .andExpect(jsonPath("$.userType").value(DEFAULT_USER_TYPE.toString()))
            .andExpect(jsonPath("$.itemType").value(DEFAULT_ITEM_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.modifiedBy").value(DEFAULT_MODIFIED_BY))
            .andExpect(jsonPath("$.modifiedDate").value(DEFAULT_MODIFIED_DATE.toString()))
            .andExpect(jsonPath("$.myPostStatus").value(DEFAULT_MY_POST_STATUS.toString()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()));
    }
    @Test
    @Transactional
    public void getNonExistingMyPost() throws Exception {
        // Get the myPost
        restMyPostMockMvc.perform(get("/api/my-posts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMyPost() throws Exception {
        // Initialize the database
        myPostService.save(myPost);

        int databaseSizeBeforeUpdate = myPostRepository.findAll().size();

        // Update the myPost
        MyPost updatedMyPost = myPostRepository.findById(myPost.getId()).get();
        // Disconnect from session so that the updates on updatedMyPost are not directly saved in db
        em.detach(updatedMyPost);
        updatedMyPost
            .userType(UPDATED_USER_TYPE)
            .itemType(UPDATED_ITEM_TYPE)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .modifiedBy(UPDATED_MODIFIED_BY)
            .modifiedDate(UPDATED_MODIFIED_DATE)
            .myPostStatus(UPDATED_MY_POST_STATUS)
            .active(UPDATED_ACTIVE);

        restMyPostMockMvc.perform(put("/api/my-posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMyPost)))
            .andExpect(status().isOk());

        // Validate the MyPost in the database
        List<MyPost> myPostList = myPostRepository.findAll();
        assertThat(myPostList).hasSize(databaseSizeBeforeUpdate);
        MyPost testMyPost = myPostList.get(myPostList.size() - 1);
        assertThat(testMyPost.getUserType()).isEqualTo(UPDATED_USER_TYPE);
        assertThat(testMyPost.getItemType()).isEqualTo(UPDATED_ITEM_TYPE);
        assertThat(testMyPost.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMyPost.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testMyPost.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testMyPost.getModifiedBy()).isEqualTo(UPDATED_MODIFIED_BY);
        assertThat(testMyPost.getModifiedDate()).isEqualTo(UPDATED_MODIFIED_DATE);
        assertThat(testMyPost.getMyPostStatus()).isEqualTo(UPDATED_MY_POST_STATUS);
        assertThat(testMyPost.isActive()).isEqualTo(UPDATED_ACTIVE);
    }

    @Test
    @Transactional
    public void updateNonExistingMyPost() throws Exception {
        int databaseSizeBeforeUpdate = myPostRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMyPostMockMvc.perform(put("/api/my-posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(myPost)))
            .andExpect(status().isBadRequest());

        // Validate the MyPost in the database
        List<MyPost> myPostList = myPostRepository.findAll();
        assertThat(myPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMyPost() throws Exception {
        // Initialize the database
        myPostService.save(myPost);

        int databaseSizeBeforeDelete = myPostRepository.findAll().size();

        // Delete the myPost
        restMyPostMockMvc.perform(delete("/api/my-posts/{id}", myPost.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MyPost> myPostList = myPostRepository.findAll();
        assertThat(myPostList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
