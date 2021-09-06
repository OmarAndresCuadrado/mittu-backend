/**
 * 
 */
package com.backend.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.api.entity.BannerEntity;

/**
 * @author MrRobot
 *
 */
public interface IBannerDao extends JpaRepository<BannerEntity, Long> {

}
