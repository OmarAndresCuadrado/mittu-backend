package com.backend.api.serviceInterface;

import java.util.List;

import com.backend.api.entity.DetailsEntity;

public interface IDetailDaoServiceInterface {

	public List<DetailsEntity> listOfDetailsFilterByDate(String initalDate, String endDate);

	public void saveDetail(DetailsEntity details);

}
