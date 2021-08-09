package com.backend.api.serviceImplement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.api.dao.PaymentDao;
import com.backend.api.entity.PaymentEntity;
import com.backend.api.serviceInterface.IPaymentServiceInterface;

@Service
public class PaymentServiceImpl implements IPaymentServiceInterface {
	
	@Autowired
	private PaymentDao paymentDao;

	@Override 
	@Transactional(readOnly = true)
	public List<PaymentEntity> listOfPayments() {
		return paymentDao.findAll();
	}

}
