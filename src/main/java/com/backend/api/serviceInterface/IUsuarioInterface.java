package com.backend.api.serviceInterface;

import java.math.BigInteger;
import java.util.List;

import com.backend.api.entity.Usuario;

public interface IUsuarioInterface {

	public Usuario findUserById(Long id);

	public List<Usuario> listOfAllUsers();

	public Usuario saveUser(Usuario usuarioEntity);

	public void deleteUser(Long id);

	Usuario findByUsername(String username);

	public void findTeacherCreatedAndSetRole(Long id);

	public void findStudentCreatedAndSetRole(Long id);

	public void disableStudentUser(Long id);

	public void enableStudentUser(Long id);

	public void disableTeacherUser(Long id);

	public void enableTeacherUser(Long id);
	
	public Long findTeacherByUserId(Long id);
	
	public Long findStudentByUserId(Long id);
	
	public List<BigInteger> findSubjectsFromStudent(Long id);
	
	public Long findAdminId();
	
	public void setPlataformMoney(Double money);
	
	public Double getAdministraitorMoney();

}
