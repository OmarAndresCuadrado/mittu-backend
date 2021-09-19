package com.backend.api.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

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
@Table(name = "retirements")
public class RetirementEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long teacherIdentifier;

	private String name;

	private Double cost;

	private Boolean alreadyPaid;

	private String retirementId;

	@NotNull(message = "no puede estar vacio")
	@Column(name = "fecha_creacion")
	@Temporal(TemporalType.DATE)
	private Date fechaDeCreacion;

	@PrePersist
	public void prePersist() {
		this.fechaDeCreacion = new Date();
	}

	public RetirementEntity() {
		this.prePersist();
		this.retirementId = this.randomIdGeneration();
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

	public Boolean getAlreadyPaid() {
		return alreadyPaid;
	}

	public void setAlreadyPaid(Boolean alreadyPaid) {
		this.alreadyPaid = alreadyPaid;
	}

	public Date getFechaDeCreacion() {
		return fechaDeCreacion;
	}

	public void setFechaDeCreacion(Date fechaDeCreacion) {
		this.fechaDeCreacion = fechaDeCreacion;
	}

	public String getRetirementId() {
		return retirementId;
	}

	public void setRetirementId(String retirementId) {
		this.retirementId = retirementId;
	}

	public Long getTeacherIdentifier() {
		return teacherIdentifier;
	}

	public void setTeacherIdentifier(Long teacherIdentifier) {
		this.teacherIdentifier = teacherIdentifier;
	}

	private String randomIdGeneration() {
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < 18) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
