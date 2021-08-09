package com.backend.api.serviceImplement;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.api.dao.IUsuarioDao;
import com.backend.api.entity.Usuario;
import com.backend.api.serviceInterface.IUsuarioInterface;

@Service
public class UsuarioServiceImpl implements IUsuarioInterface, UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(UsuarioServiceImpl.class);

	@Autowired
	private IUsuarioDao usuarioDao;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = usuarioDao.findByUsername(username);

		if (usuario == null) {
			logger.error("Error en el login: no existe el usuario '" + username + "' en el sistema!");
			throw new UsernameNotFoundException(
					"Error en el login: no existe el usuario '" + username + "' en el sistema!");
		}

		List<GrantedAuthority> authorities = usuario.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getNombre()))
				.peek(authority -> logger.info("Role: " + authority.getAuthority())).collect(Collectors.toList());

		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true,
				authorities);
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findByUsername(String username) {
		return usuarioDao.findByUsername(username);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> listOfAllUsers() {
		return (List<Usuario>) usuarioDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Usuario findUserById(Long id) {
		return usuarioDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Usuario saveUser(Usuario usuarioEntity) {
		return usuarioDao.save(usuarioEntity);
	}

	@Override
	@Transactional
	public void findTeacherCreatedAndSetRole(Long id) {
		usuarioDao.findTeacherCreatedAndSetRole(id);
	}

	@Override
	@Transactional
	public void findStudentCreatedAndSetRole(Long id) {
		usuarioDao.findStudentCreatedAndSetRole(id);
	}

	@Override
	@Transactional
	public void disableStudentUser(Long id) {
		usuarioDao.disableStudentUser(id);
	}
	
	@Override
	@Transactional
	public void enableStudentUser(Long id) {
		usuarioDao.enableStudentUser(id);
	}
	
	@Override
	@Transactional
	public void disableTeacherUser(Long id) {
		usuarioDao.disableTeacherUser(id);
	}
	
	@Override
	@Transactional
	public void enableTeacherUser(Long id) {
		usuarioDao.enableTeacherUser(id);
	}

	@Override
	@Transactional
	public Long findTeacherByUserId(Long id) {
		return usuarioDao.findTeacherByUserId(id);
	}

	@Override
	@Transactional
	public Long findStudentByUserId(Long id) {
		return usuarioDao.findStudentByUserId(id);
	}

	@Override
	public List<BigInteger> findSubjectsFromStudent(Long id) {
		List<BigInteger> ids = new ArrayList<>();
		ids = usuarioDao.findSubjectsFromStudent(id);
		return ids;
	}

	@Override
	public void deleteUser(Long id) {
		// TODO Auto-generated method stub
		
	}
	
	public Long findAdminId() {
		return usuarioDao.findAdminId();
	}

	@Override
	@Transactional
	public void setPlataformMoney(Double money) {
		usuarioDao.setPlataformMoney(money);
		
	}

	@Override
	@Transactional
	public Double getAdministraitorMoney() {
		return usuarioDao.getAdministraitorMoney();
	}

}
