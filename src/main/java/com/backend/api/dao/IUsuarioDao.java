package com.backend.api.dao;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.backend.api.entity.Usuario;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

	public Usuario findByUsername(String username);

	@Modifying(clearAutomatically = true)
	@Query(value = "insert into teacherhouse.usuarios_roles (usuario_id, role_id) values (?1,2)", nativeQuery = true)
	public void findTeacherCreatedAndSetRole(Long userId);

	@Modifying(clearAutomatically = true)
	@Query(value = "insert into teacherhouse.usuarios_roles (usuario_id, role_id) values (?1,1)", nativeQuery = true)
	public void findStudentCreatedAndSetRole(Long userId);

	@Modifying(clearAutomatically = true)
	@Query(value = "update teacherhouse.usuarios SET enabled = false WHERE id = ?1", nativeQuery = true)
	public void disableStudentUser(Long userId);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "update teacherhouse.usuarios SET enabled = true WHERE id = ?1", nativeQuery = true)
	public void enableStudentUser(Long userId);

	@Modifying(clearAutomatically = true)
	@Query(value = "update teacherhouse.usuarios SET enabled = false WHERE id = ?1", nativeQuery = true)
	public void disableTeacherUser(Long userId);
	
	@Modifying(clearAutomatically = true)
	@Query(value = "update teacherhouse.usuarios SET enabled = true WHERE id = ?1", nativeQuery = true)
	public void enableTeacherUser(Long userId);
	
	@Query(value = "select id from teacherhouse.teachers where id_user = ?1", nativeQuery = true)
	public Long findTeacherByUserId(Long userId);
	
	@Query(value = "select id from teacherhouse.students where id_user = ?1", nativeQuery = true)
	public Long findStudentByUserId(Long userId);
	
	@Query(value = "select subject_entity_id from `subjects_students` where student_entity_id = ?1", nativeQuery = true)
	public List<BigInteger> findSubjectsFromStudent(Long userId);

	@Query(value = "select id from teacherhouse.usuarios where email = 'mittuoficial@gmail.com'", nativeQuery = true)
	public Long findAdminId();

	@Modifying(clearAutomatically = true)
	@Query(value = "update teacherhouse.usuarios SET money = ?1 where teacherhouse.usuarios.username = 'mittuoficial@gmail.com'", nativeQuery = true)
	public void setPlataformMoney(Double money);


	@Query(value = "select money from teacherhouse.usuarios where teacherhouse.usuarios.username = 'mittuoficial@gmail.com'", nativeQuery = true)
	public Double getAdministraitorMoney();
}
