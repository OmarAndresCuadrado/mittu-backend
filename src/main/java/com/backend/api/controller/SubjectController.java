package com.backend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.api.entity.SubjectEntity;
import com.backend.api.serviceInterface.ISubjectServiceInterface;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class SubjectController {

	@Autowired
	private ISubjectServiceInterface subjectService;

	@GetMapping("/subject")
	public List<SubjectEntity> findAllSubjects() {
		return subjectService.findAllSubjects();
	}

	@GetMapping("/subject/{id}")
	public SubjectEntity findSubjectById(@PathVariable Long id) {
		return subjectService.findSubjectById(id);
	}

	@PostMapping("/subject")
	public SubjectEntity saveSubject(@RequestBody SubjectEntity subjectEntity) {
		return subjectService.saveSubject(subjectEntity);
	}

	@PutMapping("/subject/{id}")
	public SubjectEntity updateSubject(@RequestBody SubjectEntity subjectEntity, @PathVariable Long id) {
		return subjectService.saveSubject(subjectEntity);
	}

}
