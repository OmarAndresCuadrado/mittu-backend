package com.backend.api.serviceInterface;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.backend.api.entity.RetirementEntity;

public interface IRetirementServiceInterface {

	public List<RetirementEntity> listOfRetirments();
	
	public RetirementEntity findRetirementById(Long id);
	
	public RetirementEntity saveRetirement(RetirementEntity retirement);
	
	public List<RetirementEntity> listOfRetirementsFilterByDate(String initialDate, String finalDate);
	
	public void updateRetirementOwner(String teacher_identifier);
	
	public void updateRetirementState(String retirementId);
	
	public void sentEmailRetirement(String idReference, Long idTeacher, MultipartFile transferInformation);
	
	public void sentEmailForChangeStateRetirement(String idReference, Long idTeacher, MultipartFile paymentSupport);
		
}
