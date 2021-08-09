package com.backend.api.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.backend.api.entity.TransferEntity;

public interface ITransferDao extends JpaRepository<TransferEntity, Long> {

	@Query(value = "select * from transfers where student_id = ?1", nativeQuery = true)
	public List<TransferEntity> listOfTransferFromStudent(Long idStudent);

	@Modifying(clearAutomatically = true)
	@Query(value = "insert into teacherhouse.transfers (cost, fecha_creacion, name, student_id, transfer_code) VALUES (?1, ?2, ?3, ?4, ?5);", nativeQuery = true)
	public void createTransfer(Double cost, Date fechaDeCreacion, String name, Long idStudent, String idTransfer);
	
	@Query(value = "select * from transfers where (fecha_creacion >= ?1 and fecha_creacion <= ?2);", nativeQuery = true)
	public List<TransferEntity> listOfTransfersFilterByDate(String initialDate, String endDate);
}
