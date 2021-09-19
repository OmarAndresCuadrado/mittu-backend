/**
 * 
 */
package com.backend.api.entity;

import java.io.Serializable;

/**
 * @author MrRobot
 *
 */
public class EmailRetirementToSentDto implements Serializable {

	private String idReference;

	private Long idTeacher;

	private String accountDetails;

	public String getIdReference() {
		return idReference;
	}

	public void setIdReference(String idReference) {
		this.idReference = idReference;
	}

	public Long getIdTeacher() {
		return idTeacher;
	}

	public void setIdTeacher(Long idTeacher) {
		this.idTeacher = idTeacher;
	}

	public String getAccountDetails() {
		return accountDetails;
	}

	public void setAccountDetails(String accountDetails) {
		this.accountDetails = accountDetails;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
