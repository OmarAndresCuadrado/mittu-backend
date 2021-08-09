package com.backend.api.serviceImplement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.api.dao.IDetailsDao;
import com.backend.api.entity.DetailsEntity;
import com.backend.api.serviceInterface.IDetailDaoServiceInterface;

@Service
public class DetailServiceImpl implements IDetailDaoServiceInterface {

	@Autowired
	private IDetailsDao detailsDao;

	@Override
	public List<DetailsEntity> listOfDetailsFilterByDate(String initalDate, String endDate) {
		return detailsDao.getListOfDetailsFilterByDate(initalDate, endDate);
	}

	@Override
	public void saveDetail(DetailsEntity details) {
		detailsDao.save(details);
	}

}
