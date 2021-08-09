package com.backend.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.api.entity.PaymentEntity;

public interface PaymentDao extends JpaRepository<PaymentEntity, Long> {
	

	

}
