package com.backend.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.api.entity.FeedBackEntity;

public interface IFeedBackDao extends JpaRepository<FeedBackEntity, Long> {
	

}
