package com.backend.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.api.entity.RetirementEntity;

public interface IRetirementDao extends JpaRepository<RetirementEntity, Long> {

	@Query(value = "select * from retirements where (fecha_creacion >= ?1 and fecha_creacion <= ?2);", nativeQuery = true)
	public List<RetirementEntity> listOfRetirementsFilterByDate(String initialDate, String endDate);

}
