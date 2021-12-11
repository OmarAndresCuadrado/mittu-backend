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
@Table(name = "teachers")
public class TeacherEntity implements Serializable {

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

	@NotEmpty(message = "no puede estar vacio")
	@Size(min = 2, max = 40, message = "el tamaño tiene que estar entre 6 y 40")
	private String name;

	@NotEmpty(message = "no puede estar vacio")
	@Size(min = 2, max = 40, message = "el tamaño tiene que estar entre 6 y 40")
	private String lastName;

	private String phone;

	private Boolean enabledPlatform;

	private String meetUrl;

	private Boolean busy;

	private Boolean onChat;

	private String picture;

	private Integer time;

	private Double hourCost;

	private String profile;

	private Double money;

	private String education;

	@Column(length = 2500)
	private String description;

	private Double calification;

	private Integer countStudent;

	private String city;

	private String deparament;

	@NotEmpty(message = "no puede estar vacio")
	@Size(min = 12, max = 50, message = "el tamaño tiene que estar entre 12 y 50")
	@Email(message = "no es una dirección de correo bien formada")
	@Column(unique = true)
	private String email;

	@Column(name = "terms_and_conditions")
	private Boolean TermsAndConditions;

	@NotNull(message = "no puede estar vacio")
	@Column(name = "fecha_de_creacion")
	@Temporal(TemporalType.DATE)
	private Date fechaDeCreacion;

	@PrePersist
	public void prePersist() {
		this.fechaDeCreacion = new Date();
	}

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "teacher_id")
	private List<CourseEntity> courses;

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "teacher_id")
	private List<RetirementEntity> retirements;

	public TeacherEntity() {
		prePersist();
		courses = new ArrayList<>();
		retirements = new ArrayList<>();
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

	public List<CourseEntity> getCourses() {
		return courses;
	}

	public void setCourses(List<CourseEntity> courses) {
		this.courses = courses;
	}

	public String getMeetUrl() {
		return meetUrl;
	}

	public void setMeetUrl(String meetUrl) {
		this.meetUrl = meetUrl;
	}

	public Boolean getBusy() {
		return busy;
	}

	public void setBusy(Boolean busy) {
		this.busy = busy;
	}

	public Boolean getOnChat() {
		return onChat;
	}

	public void setOnChat(Boolean onChat) {
		this.onChat = onChat;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public List<RetirementEntity> getRetirements() {
		return retirements;
	}

	public void setRetirements(List<RetirementEntity> retirements) {
		this.retirements = retirements;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public Double getHourCost() {
		return hourCost;
	}

	public void setHourCost(Double hourCost) {
		this.hourCost = hourCost;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getCalification() {
		return calification;
	}

	public void setCalification(Double calification) {
		this.calification = calification;
	}

	public Integer getCountStudent() {
		return countStudent;
	}

	public void setCountStudent(Integer countStudent) {
		this.countStudent = countStudent;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDeparament() {
		return deparament;
	}

	public void setDeparament(String deparament) {
		this.deparament = deparament;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
