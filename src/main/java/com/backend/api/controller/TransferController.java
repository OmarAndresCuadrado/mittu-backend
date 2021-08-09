package com.backend.api.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.api.entity.TransferEntity;
import com.backend.api.serviceInterface.ITransferServiceInterface;
import com.backend.api.serviceInterface.IUsuarioInterface;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class TransferController {

	private final Logger log = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	private ITransferServiceInterface transferService;
	
	@Autowired
	private IUsuarioInterface userService;

	@GetMapping("/transfer")
	public List<TransferEntity> listOfTransfers() {
		return transferService.listOfTransfers();
	}

	@GetMapping("/transfer/{id}")
	public TransferEntity findTransferById(@PathVariable Long id) {
		return transferService.findTransferById(id);
	}

	@PostMapping("/transfer")
	public TransferEntity createTrasnfer(@RequestBody TransferEntity transfer) {
		log.info("Transaccion realizada a las horas: " + executionTime());
		return transferService.saveTrasnfer(transfer);
	}
	
	@PostMapping("/transfer/create")
	public void createTrasnferToStudent(@RequestBody TransferEntity transfer) {
		log.info("Transaccion realizada a las horas: " + executionTime());
		System.out.println(transfer.getCost());
		System.out.println(transfer.getFechaDeCreacion());
		System.out.println(transfer.getName());
		System.out.println(transfer.getIdStudent());
		System.out.println(transfer.getTransferCode());
		transferService.createTransfer(transfer.getCost(), transfer.getFechaDeCreacion(), transfer.getName(), transfer.getIdStudent(), transfer.getTransferCode());
	}


	public String executionTime() {
		TimeZone timeZone = TimeZone.getTimeZone("GMT");
		SimpleDateFormat date_format = new SimpleDateFormat("dd-MM-yyyy EEEE hh:mm:ss a");
		Date date = new Date();
		String current_date_time = date_format.format(date);
		timeZone = TimeZone.getTimeZone("Asia/Kolkata");
		date_format.setTimeZone(timeZone);
		return current_date_time;
	}
	
	@GetMapping("/transfer/list/{idStudent}")
	public List<TransferEntity> listOfTransfersFromStudent(@PathVariable Long idStudent) {
		return transferService.listOfTransferFromStudent(idStudent);
	}
	
	@GetMapping("/transfer/admin")
	public Double plataformMoney() {
		return userService.getAdministraitorMoney();
	}
}