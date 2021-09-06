/**
 * 
 */
package com.backend.api.serviceInterface;

import com.backend.api.entity.BannerEntity;

/**
 * @author MrRobot
 *
 */
public interface IBannerServiceInterface {

	public BannerEntity saveBanner(BannerEntity bannerEntity);

	public BannerEntity findBanner(Long bannerId);

}
