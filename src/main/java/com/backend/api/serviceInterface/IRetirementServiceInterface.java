package com.backend.api.serviceInterface;

import java.util.List;

import com.backend.api.entity.RetirementEntity;

public interface IRetirementServiceInterface {

	public List<RetirementEntity> listOfRetirments();
	
	public RetirementEntity findRetirementById(Long id);
	
	public RetirementEntity saveRetirement(RetirementEntity retirement);
	
	public List<RetirementEntity> listOfRetirementsFilterByDate(String initialDate, String finalDate);
		
}
