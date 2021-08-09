package com.backend.api.serviceInterface;

import java.util.Date;
import java.util.List;

import com.backend.api.entity.TransferEntity;

public interface ITransferServiceInterface {
	
	public List<TransferEntity> listOfTransfers();
	
	public TransferEntity findTransferById(Long id);
	
	public TransferEntity saveTrasnfer(TransferEntity transfer);
	
	public List<TransferEntity> listOfTransferFromStudent(Long idStudent);
	
	public void createTransfer(Double cost, Date fechaDeCreacion, String name, Long idStudent, String idTransfer);
	
	public List<TransferEntity> listOfTransfersFilterByDate(String initialDate, String finalDate);

}
