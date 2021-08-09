package com.backend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.api.entity.FeedBackEntity;
import com.backend.api.serviceInterface.IFeedBackServiceInterface;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class FeedBackController {
	
	@Autowired
	private IFeedBackServiceInterface feedBackService;
	
	@GetMapping("/feed-back")
	public List<FeedBackEntity> listOfFeedBack(){
		return feedBackService.listOfFeedBack();
	}
	
	@PostMapping("/feed-back")
	public FeedBackEntity saveFeedBack(@RequestBody FeedBackEntity feedBackEntity ){
		return feedBackService.saveFeedBack(feedBackEntity);
	}

}
