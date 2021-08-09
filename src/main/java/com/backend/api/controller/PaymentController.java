package com.backend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.api.entity.PaymentEntity;
import com.backend.api.serviceImplement.PaymentServiceImpl;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class PaymentController {

	@Autowired
	private PaymentServiceImpl paymentService;
	
	
	@GetMapping("/payment")
	public List<PaymentEntity> listOfPayments() {
		return paymentService.listOfPayments();
	}
	
	public List<PaymentEntity> listOfPaymentsForStudent() {
		return null;
	}

}
