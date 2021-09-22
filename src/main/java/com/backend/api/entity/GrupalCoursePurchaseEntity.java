/**
 * 
 */
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

/**
 * @author MrRobot
 *
 */

@Entity
@Table(name = "grupal_course_purchases")
public class GrupalCoursePurchaseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long idTeacher;

	private Long idStudent;

	private String grupalCourseName;

	private String gruaplCourseCost;

	private String moneyForTeacher;

	private String moneyForPlataform;

	public Date getFechaDeCreacion() {
		return fechaDeCreacion;
	}

	public void setFechaDeCreacion(Date fechaDeCreacion) {
		this.fechaDeCreacion = fechaDeCreacion;
	}

	@NotNull(message = "no puede estar vacio")
	@Column(name = "fecha_creacion")
	@Temporal(TemporalType.DATE)
	private Date fechaDeCreacion;

	@PrePersist
	public void prePersist() {
		this.fechaDeCreacion = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdTeacher() {
		return idTeacher;
	}

	public void setIdTeacher(Long idTeacher) {
		this.idTeacher = idTeacher;
	}

	public Long getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
	}

	public String getGrupalCourseName() {
		return grupalCourseName;
	}

	public void setGrupalCourseName(String grupalCourseName) {
		this.grupalCourseName = grupalCourseName;
	}

	public String getGruaplCourseCost() {
		return gruaplCourseCost;
	}

	public void setGruaplCourseCost(String gruaplCourseCost) {
		this.gruaplCourseCost = gruaplCourseCost;
	}

	public String getMoneyForTeacher() {
		return moneyForTeacher;
	}

	public void setMoneyForTeacher(String moneyForTeacher) {
		this.moneyForTeacher = moneyForTeacher;
	}

	public String getMoneyForPlataform() {
		return moneyForPlataform;
	}

	public void setMoneyForPlataform(String moneyForPlataform) {
		this.moneyForPlataform = moneyForPlataform;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
