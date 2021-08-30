package com.backend.api.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "students")
public class StudentEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long idUser;

	@Column(unique = true)
	private String username;

	@NotEmpty(message = "no puede estar vacio")
	@Size(min = 6, max = 60, message = "el tamaño tiene que estar entre 6 y 60")
	@Column(length = 60)
	private String password;

	private Boolean enabled;

	private Boolean enabledPlatform;

	private Double money;

	private String city;

	private String picture;

	@NotEmpty(message = "no puede estar vacio")
	@Size(min = 2, max = 40, message = "el tamaño tiene que estar entre 2 y 40")
	private String name;

	@NotEmpty(message = "no puede estar vacio")
	@Size(min = 2, max = 40, message = "el tamaño tiene que estar entre 2 y 40")
	private String lastName;

	@NotEmpty(message = "no puede estar vacio")
	@Size(min = 6, max = 10, message = "el tamaño tiene que estar entre 6 y 10")
	private String phone;

	@Column(name = "terms_and_conditions")
	private Boolean TermsAndConditions;

	@NotEmpty(message = "no puede estar vacio")
	@Size(min = 12, max = 50, message = "el tamaño tiene que estar entre 12 y 50")
	@Email(message = "no es una dirección de correo bien formada")
	@Column(unique = true)
	private String email;

	@NotNull(message = "no puede estar vacio")
	@Column(name = "fecha_de_creacion")
	@Temporal(TemporalType.DATE)
	private Date fechaDeCreacion;

	private Integer time;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "student_id")
	private List<TransferEntity> transfers;

	public StudentEntity() {
		prePersist();
		transfers = new ArrayList<>();
	}

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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Boolean getEnabledPlatform() {
		return enabledPlatform;
	}

	public void setEnabledPlatform(Boolean enabledPlatform) {
		this.enabledPlatform = enabledPlatform;
	}

	public Boolean getTermsAndConditions() {
		return TermsAndConditions;
	}

	public void setTermsAndConditions(Boolean termsAndConditions) {
		TermsAndConditions = termsAndConditions;
	}

	public Date getFechaDeCreacion() {
		return fechaDeCreacion;
	}

	public void setFechaDeCreacion(Date fechaDeCreacion) {
		this.fechaDeCreacion = fechaDeCreacion;
	}

	public Long getIdUser() {
		return idUser;
	}

	public void setIdUser(Long idUser) {
		this.idUser = idUser;
	}

	public List<TransferEntity> getTransfers() {
		return transfers;
	}

	public void setTransfers(List<TransferEntity> transfers) {
		this.transfers = transfers;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
}
