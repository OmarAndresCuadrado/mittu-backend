package com.backend.api.serviceImplement;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.api.dao.ITransferDao;
import com.backend.api.entity.TransferEntity;
import com.backend.api.serviceInterface.ITransferServiceInterface;

@Service
public class TransferServiceImpl implements ITransferServiceInterface {

	@Autowired
	private ITransferDao transferService;

	@Override
	@Transactional(readOnly = true)
	public List<TransferEntity> listOfTransfers() {
		return transferService.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public TransferEntity findTransferById(Long id) {
		return transferService.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public TransferEntity saveTrasnfer(TransferEntity transfer) {
		return transferService.save(transfer);
	}

	@Override
	public List<TransferEntity> listOfTransferFromStudent(Long idStudent) {
		return transferService.listOfTransferFromStudent(idStudent);
	}

	@Override
	@Transactional
	public void createTransfer(Double cost, Date fechaDeCreacion, String name, Long idStudent, String idTransfer) {
		transferService.createTransfer(cost, fechaDeCreacion, name, idStudent, idTransfer);
	}

	@Override
	public List<TransferEntity> listOfTransfersFilterByDate(String initialDate, String finalDate) {
		return transferService.listOfTransfersFilterByDate(initialDate, finalDate);
	}
}
