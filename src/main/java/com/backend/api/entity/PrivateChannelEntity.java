package com.backend.api.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "private_chanel")
public class PrivateChannelEntity implements Serializable{
	


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Long idStudent;

	private Boolean onChat;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdStudent() {
		return idStudent;
	}

	public void setIdStudent(Long idStudent) {
		this.idStudent = idStudent;
	}

	public Boolean getOnChat() {
		return onChat;
	}

	public void setOnChat(Boolean onChat) {
		this.onChat = onChat;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

}
