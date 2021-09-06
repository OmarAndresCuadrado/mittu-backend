/**
 * 
 */
package com.backend.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author MrRobot
 *
 */
@Entity
@Table(name = "banner")
public class BannerEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String urlBanner;

	private String colorBanner;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrlBanner() {
		return urlBanner;
	}

	public void setUrlBanner(String urlBanner) {
		this.urlBanner = urlBanner;
	}

	public String getColorBanner() {
		return colorBanner;
	}

	public void setColorBanner(String colorBanner) {
		this.colorBanner = colorBanner;
	}

}
