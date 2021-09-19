package com.backend.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.backend.api.entity.RetirementEntity;

public interface IRetirementDao extends JpaRepository<RetirementEntity, Long> {

	@Query(value = "select * from retirements where (fecha_creacion >= ?1 and fecha_creacion <= ?2);", nativeQuery = true)
	public List<RetirementEntity> listOfRetirementsFilterByDate(String initialDate, String endDate);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "update teacherhouse.retirements SET teacher_id = ?1 where teacher_identifier = ?1", nativeQuery = true)
	public void updateRetirementOwner(String teacher_identifier);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "update teacherhouse.retirements SET already_paid = true where retirement_id = ?1", nativeQuery = true)
	public void updateRetirementState(String retirementId);

}
