/**
 * 
 */
package com.backend.api.serviceImplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.api.dao.IBannerDao;
import com.backend.api.entity.BannerEntity;
import com.backend.api.serviceInterface.IBannerServiceInterface;

/**
 * @author MrRobot
 *
 */

@Service
public class BannerServiceImpl implements IBannerServiceInterface {
	
	
	@Autowired
	private IBannerDao bannerDao;

	@Override
	public BannerEntity saveBanner(BannerEntity bannerEntity) {
		return bannerDao.save(bannerEntity);
	}

	@Override
	public BannerEntity findBanner(Long bannerId) {
		return bannerDao.findById(bannerId).orElse(null);
	}

	
}
