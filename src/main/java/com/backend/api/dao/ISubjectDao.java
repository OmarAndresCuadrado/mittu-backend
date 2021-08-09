package com.backend.api.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.api.entity.SubjectEntity;

public interface ISubjectDao extends JpaRepository<SubjectEntity, Long> {

}
