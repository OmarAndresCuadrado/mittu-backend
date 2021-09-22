package com.backend.api.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.api.entity.DetailsEntity;
import com.backend.api.entity.EmailRetirementToSentDto;
import com.backend.api.entity.ExcelClass;
import com.backend.api.entity.GrupalCoursePurchaseEntity;
import com.backend.api.entity.RetirementEntity;
import com.backend.api.entity.StudentEntity;
import com.backend.api.entity.TeacherEntity;
import com.backend.api.entity.TransferEntity;
import com.backend.api.serviceImplement.DetailServiceImpl;
import com.backend.api.serviceImplement.ExcelServiceImpl;
import com.backend.api.serviceImplement.RetirementServiceImpl;
import com.backend.api.serviceImplement.StudentServiceImpl;
import com.backend.api.serviceImplement.TeacherServiceImpl;
import com.backend.api.serviceImplement.TransferServiceImpl;
import com.backend.api.serviceInterface.IGrupalCoursePurchaseInterface;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class RetirementController {

	@Autowired
	private RetirementServiceImpl retirementService;

	@Autowired
	private TransferServiceImpl transferService;

	@Autowired
	private StudentServiceImpl studentService;

	@Autowired
	private TeacherServiceImpl teacherService;

	@Autowired
	private DetailServiceImpl detailsService;

	@Autowired
	private ExcelServiceImpl excelService;

	@Autowired
	private IGrupalCoursePurchaseInterface grupalCoursePurchaseService;

	private final Logger log = LoggerFactory.getLogger(StudentController.class);

	@GetMapping("/retirement")
	public List<RetirementEntity> listOfRetirements() {
		return retirementService.listOfRetirments();
	}

	@GetMapping("/retirement/{id}")
	public RetirementEntity findRetirementById(@PathVariable Long id) {
		return retirementService.findRetirementById(id);
	}

	@GetMapping("/retirement/update/state/{retirementId}")
	public void updateRetirementState(@PathVariable String retirementId) {
		retirementService.updateRetirementState(retirementId);
	}

	@PostMapping("/retirement")
	public RetirementEntity createRetirement(@RequestBody RetirementEntity retirement) {
		log.info("Retiro solicitado a las horas: " + executionTime());
		RetirementEntity newRetirement = new RetirementEntity();
		newRetirement = retirementService.saveRetirement(retirement);
		retirementService.updateRetirementOwner(retirement.getTeacherIdentifier().toString());
		teacherService.setTeacherMoney(Double.valueOf(0), retirement.getTeacherIdentifier());
		return newRetirement;
	}

	@PostMapping("/retirement/excel")
	public ResponseEntity<InputStreamResource> createExcel(@RequestBody ExcelClass excelClass,
			HttpServletResponse response) throws IOException {
		String documentName = excelClass.getDocumentName();
		String content = "Content-Disposition";

		if (excelClass.getDocumentName() == null) {
			documentName = ("inline; filename=").concat("MittuReport").concat(".xlsx");
		} else {
			documentName = ("inline; filename=").concat(excelClass.getDocumentName()).concat(".xlsx");
		}
		if (excelClass.getInitialDate() == null) {
			excelClass.setInitialDate("1900-01-01");
		}

		if (excelClass.getFinalDate() == null) {
			excelClass.setFinalDate("3000-01-01");
		}

		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = documentName;

		List<RetirementEntity> retirementList = retirementService
				.listOfRetirementsFilterByDate(excelClass.getInitialDate(), excelClass.getFinalDate());

		List<TransferEntity> transferList = transferService.listOfTransfersFilterByDate(excelClass.getInitialDate(),
				excelClass.getFinalDate());

		List<StudentEntity> studentList = studentService.listAllStudentsFilterByDate(excelClass.getInitialDate(),
				excelClass.getFinalDate());

		List<TeacherEntity> teacherList = teacherService.listOfTeacherFilterByDate(excelClass.getInitialDate(),
				excelClass.getFinalDate());

		List<DetailsEntity> detailsList = detailsService.listOfDetailsFilterByDate(excelClass.getInitialDate(),
				excelClass.getFinalDate());

		List<GrupalCoursePurchaseEntity> grupalCoursePurchaseList = grupalCoursePurchaseService
				.getListOfGrupalCoursesPurchasesFilterByDate(excelClass.getInitialDate(), excelClass.getFinalDate());

		response.setHeader(headerKey, headerValue);
		ByteArrayInputStream bais = excelService.exportExcel(retirementList, transferList, studentList, teacherList,
				detailsList, grupalCoursePurchaseList);

		HttpHeaders headers = new HttpHeaders();
		headers.add(content, documentName);
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(bais));

	}

	@PostMapping("/retirement/new/send/email")
	public void sentEmailForNewRetirement(@RequestBody EmailRetirementToSentDto emailRetirementToSentDto) {
		retirementService.sentEmailRetirement(emailRetirementToSentDto.getIdReference(),
				emailRetirementToSentDto.getIdTeacher(), emailRetirementToSentDto.getAccountDetails());
	}

	@PostMapping("/retirement/state/send/email")
	public void sentEmailForChangeStateRetirement(@RequestBody EmailRetirementToSentDto emailRetirementToSentDto) {
		retirementService.sentEmailForChangeStateRetirement(emailRetirementToSentDto.getIdReference(),
				emailRetirementToSentDto.getIdTeacher());
	}

	public String executionTime() {
		TimeZone timeZone = TimeZone.getTimeZone("GMT");
		SimpleDateFormat date_format = new SimpleDateFormat("dd-MM-yyyy EEEE hh:mm:ss a");
		Date date = new Date();
		String current_date_time = date_format.format(date);
		timeZone = TimeZone.getTimeZone("Asia/Kolkata");
		date_format.setTimeZone(timeZone);
		return current_date_time;
	}
}
