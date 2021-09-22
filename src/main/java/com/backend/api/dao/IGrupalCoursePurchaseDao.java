/**
 * 
 */
package com.backend.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.api.entity.GrupalCoursePurchaseEntity;

/**
 * @author MrRobot
 *
 */
public interface IGrupalCoursePurchaseDao extends JpaRepository<GrupalCoursePurchaseEntity, Long> {
	
	@Query(value = "select * from grupal_course_purchases where (fecha_creacion >= ?1 and fecha_creacion <= ?2);", nativeQuery = true)
	public List<GrupalCoursePurchaseEntity> getListOfGrupalCoursesPurchasesFilterByDate(String initalDate, String endDate);

}
