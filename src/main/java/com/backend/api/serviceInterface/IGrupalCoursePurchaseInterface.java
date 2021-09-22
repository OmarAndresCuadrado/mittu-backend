/**
 * 
 */
package com.backend.api.serviceInterface;

import java.util.List;

import com.backend.api.entity.GrupalCoursePurchaseEntity;

/**
 * @author MrRobot
 *
 */
public interface IGrupalCoursePurchaseInterface {

	public List<GrupalCoursePurchaseEntity> grupalCoursesPurchases();

	public GrupalCoursePurchaseEntity findGrupalCoursePurchaseById(Long id);

	public GrupalCoursePurchaseEntity saveGrupalCoursePurchase(GrupalCoursePurchaseEntity grupalCoursePurchaseEntity);

	public List<GrupalCoursePurchaseEntity> getListOfGrupalCoursesPurchasesFilterByDate(String initalDate, String endDate);
}
