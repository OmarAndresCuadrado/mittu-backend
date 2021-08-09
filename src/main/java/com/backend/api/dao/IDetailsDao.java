package com.backend.api.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.backend.api.entity.DetailsEntity;

public interface IDetailsDao extends JpaRepository<DetailsEntity, Long> {
	
	@Query(value = "select * from details where (fecha_de_creacion >= ?1 and fecha_de_creacion <= ?2);", nativeQuery = true)
	public List<DetailsEntity> getListOfDetailsFilterByDate(String initalDate, String endDate);

}
