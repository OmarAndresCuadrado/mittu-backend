package com.backend.api.serviceImplement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.api.dao.IRetirementDao;
import com.backend.api.entity.RetirementEntity;
import com.backend.api.serviceInterface.IRetirementServiceInterface;

@Service
public class RetirementServiceImpl implements IRetirementServiceInterface {

	@Autowired
	private IRetirementDao retirementDao;

	@Override
	@Transactional(readOnly = true)
	public List<RetirementEntity> listOfRetirments() {
		return retirementDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public RetirementEntity findRetirementById(Long id) {
		return retirementDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public RetirementEntity saveRetirement(RetirementEntity retirement) {
		return retirementDao.save(retirement);
	}

	@Override
	public List<RetirementEntity> listOfRetirementsFilterByDate(String initialDate, String finalDate) {
		return retirementDao.listOfRetirementsFilterByDate(initialDate, finalDate);
	}

}
