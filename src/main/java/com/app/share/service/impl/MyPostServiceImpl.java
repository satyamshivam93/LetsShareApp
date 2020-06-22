package com.app.share.service.impl;

import com.app.share.service.MyPostService;
import com.app.share.domain.MyPost;
import com.app.share.repository.MyPostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MyPost}.
 */
@Service
@Transactional
public class MyPostServiceImpl implements MyPostService {

    private final Logger log = LoggerFactory.getLogger(MyPostServiceImpl.class);

    private final MyPostRepository myPostRepository;

    public MyPostServiceImpl(MyPostRepository myPostRepository) {
        this.myPostRepository = myPostRepository;
    }

    /**
     * Save a myPost.
     *
     * @param myPost the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MyPost save(MyPost myPost) {
        log.debug("Request to save MyPost : {}", myPost);
        return myPostRepository.save(myPost);
    }

    /**
     * Get all the myPosts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MyPost> findAll(Pageable pageable) {
        log.debug("Request to get all MyPosts");
        return myPostRepository.findAll(pageable);
    }


    /**
     * Get one myPost by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MyPost> findOne(Long id) {
        log.debug("Request to get MyPost : {}", id);
        return myPostRepository.findById(id);
    }

    /**
     * Delete the myPost by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MyPost : {}", id);
        myPostRepository.deleteById(id);
    }
}
