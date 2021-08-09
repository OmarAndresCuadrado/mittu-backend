package com.backend.api.serviceInterface;

import java.util.List;

import com.backend.api.entity.PaymentEntity;

public interface IPaymentServiceInterface  {

	public List<PaymentEntity> listOfPayments();
}
