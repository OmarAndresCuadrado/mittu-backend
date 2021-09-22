package com.backend.api.serviceInterface;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import com.backend.api.entity.DetailsEntity;
import com.backend.api.entity.GrupalCoursePurchaseEntity;
import com.backend.api.entity.RetirementEntity;
import com.backend.api.entity.StudentEntity;
import com.backend.api.entity.TeacherEntity;
import com.backend.api.entity.TransferEntity;

public interface IExcelInterface {

	public ByteArrayInputStream exportExcel(List<RetirementEntity> retirementList, List<TransferEntity> transferList,
			List<StudentEntity> studentList, List<TeacherEntity> teacherList, List<DetailsEntity> detailsList,
			List<GrupalCoursePurchaseEntity> grupalCoursePurchaseList) throws IOException;

}
