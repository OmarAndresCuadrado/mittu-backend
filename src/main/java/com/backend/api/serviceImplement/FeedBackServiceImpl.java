package com.backend.api.serviceImplement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.api.dao.IFeedBackDao;
import com.backend.api.entity.FeedBackEntity;
import com.backend.api.serviceInterface.IFeedBackServiceInterface;


@Service
public class FeedBackServiceImpl implements IFeedBackServiceInterface {
	
	@Autowired
	private IFeedBackDao feedBackDao;

	@Override
	public List<FeedBackEntity> listOfFeedBack() {
		return feedBackDao.findAll();
	}

	@Override
	public FeedBackEntity saveFeedBack(FeedBackEntity feedBackEntity) {
		return feedBackDao.save(feedBackEntity);
	}
	

}
