package com.backend.api.serviceImplement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.api.dao.ISubjectDao;
import com.backend.api.entity.SubjectEntity;
import com.backend.api.serviceInterface.ISubjectServiceInterface;

@Service
public class SubjectServiceImpl implements ISubjectServiceInterface {

	@Autowired
	private ISubjectDao subjectDao;

	@Override
	@Transactional(readOnly = true)
	public List<SubjectEntity> findAllSubjects() {
		return subjectDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public SubjectEntity findSubjectById(Long id) {
		return subjectDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public SubjectEntity saveSubject(SubjectEntity subjectEntity) {
		return subjectDao.save(subjectEntity);
	}

	@Override
	public void deleteSubject(Long id) {
		// TODO Auto-generated method stub
		
	}



}
