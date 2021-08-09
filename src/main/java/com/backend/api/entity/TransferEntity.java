package com.backend.api.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "transfers")
public class TransferEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private Double cost;

	private Long IdStudent;

	private String transferCode;

	@NotNull(message = "no puede estar vacio")
	@Column(name = "fecha_creacion")
	@Temporal(TemporalType.DATE)
	private Date fechaDeCreacion;

	@PrePersist
	public void prePersist() {
		this.fechaDeCreacion = new Date();
	}

	public TransferEntity() {
		this.prePersist();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Date getFechaDeCreacion() {
		return fechaDeCreacion;
	}

	public void setFechaDeCreacion(Date fechaDeCreacion) {
		this.fechaDeCreacion = fechaDeCreacion;
	}

	public Long getIdStudent() {
		return IdStudent;
	}

	public void setIdStudent(Long idStudent) {
		IdStudent = idStudent;
	}

	public String getTransferCode() {
		return transferCode;
	}

	public void setTransferCode(String transferCode) {
		this.transferCode = transferCode;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
