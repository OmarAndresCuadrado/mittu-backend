package com.backend.api.serviceImplement;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.api.dao.IGrupalCourseDao;
import com.backend.api.entity.GrupalCourseEntity;
import com.backend.api.serviceInterface.IGrupalCourseServiceInterface;

@Service
public class GrupalCourseServiceImpl implements IGrupalCourseServiceInterface {

	@Autowired
	private IGrupalCourseDao GruaplCourseDao;

	@Override
	@Transactional(readOnly = true)
	public List<GrupalCourseEntity> listOfGrupalCourse() {
		return GruaplCourseDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public GrupalCourseEntity findGrupalCourseById(Long id) {
		return GruaplCourseDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public GrupalCourseEntity saveGrupalCourse(GrupalCourseEntity grupalCourseEntity) {
		return GruaplCourseDao.save(grupalCourseEntity);
	}

	@Override
	@Transactional
	public GrupalCourseEntity updateGrupalCourse(Long id, GrupalCourseEntity grupalCourseEntity) {
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<GrupalCourseEntity> getGrupalCoursesByTeacherId(Long idTeacher) {
		return GruaplCourseDao.getGrupalCoursesByTeacherId(idTeacher);
	}

	@Override
	@Transactional(readOnly = true)
	public List<GrupalCourseEntity> searchByGrupalCourseName(String grupalCourseName) {
		return GruaplCourseDao.searchByCourseName(grupalCourseName);
	}

	@Override
	@Transactional
	public void createInscriptionToGrupalCourse(Long courseId, Long studentId) {
		GruaplCourseDao.createInscriptionToGrupalCourse(courseId, studentId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<BigInteger> findGrupalCoursesFromStudent(Long studentId) {
		return GruaplCourseDao.findGrupalCoursesFromStudent(studentId);
	}

}
