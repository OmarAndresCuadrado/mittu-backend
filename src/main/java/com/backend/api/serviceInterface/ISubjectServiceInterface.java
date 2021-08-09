package com.backend.api.serviceInterface;

import java.util.List;

import com.backend.api.entity.SubjectEntity;

public interface ISubjectServiceInterface {

	public List<SubjectEntity> findAllSubjects();

	public SubjectEntity findSubjectById(Long id);

	public SubjectEntity saveSubject(SubjectEntity subjectEntity);

	public void deleteSubject(Long id);

}
