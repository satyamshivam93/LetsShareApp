package com.app.share.service.impl;

import com.app.share.service.MyPostResponseService;
import com.app.share.domain.MyPostResponse;
import com.app.share.repository.MyPostResponseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MyPostResponse}.
 */
@Service
@Transactional
public class MyPostResponseServiceImpl implements MyPostResponseService {

    private final Logger log = LoggerFactory.getLogger(MyPostResponseServiceImpl.class);

    private final MyPostResponseRepository myPostResponseRepository;

    public MyPostResponseServiceImpl(MyPostResponseRepository myPostResponseRepository) {
        this.myPostResponseRepository = myPostResponseRepository;
    }

    /**
     * Save a myPostResponse.
     *
     * @param myPostResponse the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MyPostResponse save(MyPostResponse myPostResponse) {
        log.debug("Request to save MyPostResponse : {}", myPostResponse);
        return myPostResponseRepository.save(myPostResponse);
    }

    /**
     * Get all the myPostResponses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MyPostResponse> findAll(Pageable pageable) {
        log.debug("Request to get all MyPostResponses");
        return myPostResponseRepository.findAll(pageable);
    }


    /**
     * Get one myPostResponse by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MyPostResponse> findOne(Long id) {
        log.debug("Request to get MyPostResponse : {}", id);
        return myPostResponseRepository.findById(id);
    }

    /**
     * Delete the myPostResponse by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MyPostResponse : {}", id);
        myPostResponseRepository.deleteById(id);
    }
}
