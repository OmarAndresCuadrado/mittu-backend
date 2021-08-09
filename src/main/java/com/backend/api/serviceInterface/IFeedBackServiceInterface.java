package com.backend.api.serviceInterface;

import java.util.List;

import com.backend.api.entity.FeedBackEntity;

public interface IFeedBackServiceInterface {

	public List<FeedBackEntity> listOfFeedBack();

	public FeedBackEntity saveFeedBack(FeedBackEntity feedBackEntity);

}
