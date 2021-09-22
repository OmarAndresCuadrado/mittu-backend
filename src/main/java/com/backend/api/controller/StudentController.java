package com.backend.api.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.api.entity.CourseEntity;
import com.backend.api.entity.DetailsEntity;
import com.backend.api.entity.GrupalCoursePurchaseOperationResult;
import com.backend.api.entity.StudentEntity;
import com.backend.api.entity.TeacherEntity;
import com.backend.api.entity.TimeEntity;
import com.backend.api.entity.Usuario;
import com.backend.api.serviceInterface.ICourseServiceInterface;
import com.backend.api.serviceInterface.IDetailDaoServiceInterface;
import com.backend.api.serviceInterface.IStudentServiceInterface;
import com.backend.api.serviceInterface.ITeacherServiceInterface;
import com.backend.api.serviceInterface.IUsuarioInterface;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class StudentController {

	private final Logger log = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	IStudentServiceInterface studentService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private IUsuarioInterface usuarioService;

	@Autowired
	private ICourseServiceInterface courseService;

	@Autowired
	private ITeacherServiceInterface teacherService;

	@Autowired
	private IUsuarioInterface userService;

	@Autowired
	private IDetailDaoServiceInterface detailService;

	@GetMapping("/student")
	public ResponseEntity<?> findAllStudents() {
		List<StudentEntity> listModify = new ArrayList<>();
		Map<String, Object> response = new HashMap<>();

		try {
			String emptyPassword = "";
			this.studentService.findAllStudents().forEach((resp) -> {
				resp.setPassword(emptyPassword);
				listModify.add(resp);
			});
			if (listModify.size() == 0 || listModify == null) {
				response.put("errorMsg", "no se han podido enlistar los estudiantes");
				log.info(executionTime() + "-Error no se han podido enlistar los estudiantes");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
		} catch (DataAccessException e) {
			response.put("errorMsg", "Error al realizar la conexion con la base de datos");
			log.info(executionTime() + "-Error no se han podido conectar con la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<StudentEntity>>(listModify, HttpStatus.OK);
	}

	@GetMapping("/student/{id}")
	public ResponseEntity<?> findStudentById(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		StudentEntity studentFound = new StudentEntity();
		String emptyPassword = "";

		try {
			studentFound = studentService.findStudent(id);

			if (studentFound == null) {
				response.put("errorMsg", "No existe el usuario con el id " + id);
				log.info(executionTime() + "-Error al buscar el estudiente con el id " + id);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}

		} catch (DataAccessException e) {
			response.put("errorMsg", "Error al realizar la conexion con la base de datos");
			log.info(executionTime() + "-Error no se han podido conectar con la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		studentFound.setPassword(emptyPassword);
		return new ResponseEntity<StudentEntity>(studentFound, HttpStatus.OK);
	}

	@PostMapping("/student")
	public ResponseEntity<?> saveStudent(@Valid @RequestBody StudentEntity studentEntity, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		StudentEntity studentCreated = new StudentEntity();
		Usuario userStudent = new Usuario();
		Usuario finalUsuario = new Usuario();
		String studentUserNameEmail = "";
		studentUserNameEmail = studentEntity.getEmail();
		studentEntity.setUsername(studentUserNameEmail);

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errores de validacion", errors);
			log.info(executionTime() + "-Error de formulario de creacion de estudiante");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			String cleanPassword = studentEntity.getPassword();
			String encodedPassword = passwordEncoder.encode(cleanPassword);
			studentEntity.setPassword(encodedPassword);
			String username = studentEntity.getUsername();
			String password = studentEntity.getPassword();
			String nombre = studentEntity.getName();
			String apellido = studentEntity.getLastName();
			String email = studentEntity.getEmail();
			String city = studentEntity.getCity();
			userStudent.setUsername(username);
			userStudent.setPassword(password);
			userStudent.setEnabled(true);
			userStudent.setName(nombre);
			userStudent.setLastName(apellido);
			userStudent.setEmail(email);
			userStudent.setMoney(Double.valueOf(0));
			userStudent.setCity(city);
			try {
				finalUsuario = usuarioService.saveUser(userStudent);
			} catch (DataAccessException e) {
				response.put("errorMsg",
						"Error al crear el estudiante el correo " + studentEntity.getEmail() + " " + "esta duplicado");
				log.info(executionTime() + "-Error no se han podido conectar con la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			Long stuendtId = finalUsuario.getId();
			studentEntity.setIdUser(stuendtId);
			studentEntity.setEnabled(true);
			studentEntity.setEnabledPlatform(true);
			studentEntity.setCity(city);
			studentEntity.setMoney(Double.valueOf(0));
			studentEntity.setTermsAndConditions(true);
			studentCreated = studentService.saveStudent(studentEntity);
			usuarioService.findStudentCreatedAndSetRole(stuendtId);
			studentService.sentEmailToStudent(studentEntity, cleanPassword);
			studentService.setStudentTime(0, studentCreated.getId());

		} catch (DataAccessException e) {
			response.put("errorMsg", "Error al realizar la conexion con la base de datos");
			log.info(executionTime() + "-Error no se han podido conectar con la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		studentCreated = null;

		return new ResponseEntity<StudentEntity>(studentCreated, HttpStatus.OK);
	}

	@PutMapping("/student/{id}")
	public ResponseEntity<?> updateStudent(@RequestBody StudentEntity studentEntity, @PathVariable Long id) {
		StudentEntity studentFound = this.studentService.findStudent(id);

		Map<String, Object> response = new HashMap<>();

		if (studentFound == null) {
			response.put("mensaje", "Error: no se pudo editar, el cliente ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			if (studentFound.getName() == null) {
				response.put("mensaje", "Error: no se pudo editar, el estudiante con el ID: "
						.concat(id.toString().concat("el nombre no puede ser vacio")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}

			else if (studentFound.getLastName() == null) {
				response.put("mensaje", "Error: no se pudo editar, el estudiante con el ID: "
						.concat(id.toString().concat("el apellido no puede ser vacio")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}

			else if (studentFound.getPhone() == null) {
				response.put("mensaje", "Error: no se pudo editar, el estudiante con el ID: "
						.concat(id.toString().concat("el telefono no puede ser vacio")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}

			studentFound.setName(studentEntity.getName());
			studentFound.setLastName(studentEntity.getLastName());
			studentFound.setPhone(studentEntity.getPhone());
			studentService.saveStudent(studentFound);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el estudiante en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<StudentEntity>(studentFound, HttpStatus.CREATED);
	}

	// Implementar metodo para encontrar profesor por id
	@PostMapping("/student/disable/{idUser}")
	public ResponseEntity<?> disableStudent(@PathVariable Long idUser) {
		Map<String, Object> response = new HashMap<>();
		Usuario userToBeDisable = new Usuario();

		try {
			userToBeDisable = usuarioService.findUserById(idUser);
			if (userToBeDisable == null) {
				response.put("errorMsg", "Error al buscar el usuario con el id " + idUser);
				log.info(executionTime() + "-Error al buscar el usuario con el id " + idUser);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
		} catch (DataAccessException e) {
			response.put("errorMsg", "Error al realizar la conexion con la base de datos");
			log.info(executionTime() + "-Error no se han podido conectar con la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		usuarioService.disableStudentUser(idUser);
		studentService.disableStudent(idUser);
		response.put("Msg", "ok");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	// Implementar metodo para encontrar profesor por id
	@PostMapping("/student/enable/{idUser}")
	public ResponseEntity<?> enableStudent(@PathVariable Long idUser) {
		Map<String, Object> response = new HashMap<>();
		Usuario userToBeEnable = new Usuario();

		try {
			userToBeEnable = usuarioService.findUserById(idUser);
			if (userToBeEnable == null) {
				response.put("errorMsg", "Error al buscar el usuario con el id " + idUser);
				log.info(executionTime() + "-Error al buscar el usuario con el id " + idUser);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
		} catch (DataAccessException e) {
			response.put("errorMsg", "Error al realizar la conexion con la base de datos");
			log.info(executionTime() + "-Error no se han podido conectar con la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		usuarioService.enableStudentUser(idUser);
		studentService.enableStudent(idUser);
		response.put("Msg", "ok");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	@GetMapping("/student/verification/{userName}")
	public ResponseEntity<?> validateUsername(@PathVariable String userName) {
		Map<String, Object> response = new HashMap<>();
		String emailUsername = studentService.verifyStudentByUsername(userName);
		response.put("username", emailUsername);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
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

	@PostMapping("/student/get/courses/{idStudent}")
	public ResponseEntity<?> getCourses(@PathVariable Long idStudent) {
		List<CourseEntity> coursesFound = new ArrayList<CourseEntity>();
		List<CourseEntity> listOfCoursesAll = new ArrayList<CourseEntity>();
		Long idStudentFound = usuarioService.findStudentByUserId(idStudent);
		List<BigInteger> idsOfSubjects = usuarioService.findSubjectsFromStudent(idStudentFound);

		for (BigInteger bigInteger : idsOfSubjects) {
			coursesFound = courseService.findCoursesFromSubject(bigInteger.longValue());
			coursesFound.forEach(courseFoundObject -> {
				listOfCoursesAll.add(courseFoundObject);
			});
		}

		return new ResponseEntity<List<CourseEntity>>(listOfCoursesAll, HttpStatus.OK);
	}

	@PutMapping("/student/set/time/{idStudent}")
	public Integer getTimeFromStudent(@PathVariable Long idStudent, @RequestBody TimeEntity timeObject) {
		Integer studentTime = studentService.getStudentTime(idStudent);
		Integer newStudentTime = 0;
		newStudentTime = timeObject.getTimeOnTransaction();

		if (studentTime <= 0) {
			studentService.setStudentTime(timeObject.getTimeOnTransaction(), idStudent);
			studentTime = timeObject.getTimeOnTransaction();
			return studentTime;

		} else {
			newStudentTime = (timeObject.getTimeOnTransaction() + studentTime);
			studentService.setStudentTime(newStudentTime, idStudent);
			return newStudentTime;
		}
	}

	@GetMapping("/student/get/time/{idStudent}")
	public Integer getTimeFromStudent(@PathVariable Long idStudent) {

		Integer studentTime = studentService.getStudentTime(idStudent);
		return studentTime;
	}

	@PostMapping("/student/teacher/get/time")
	public ResponseEntity<?> getTimeFromStudentAndTeacher(@RequestBody TimeEntity timeObject) {
		Double operationValue = (double) 0;
		TeacherEntity teacherFound = teacherService.findTeacherById(timeObject.getTeacherId());
		StudentEntity studentFound = studentService.findStudent(timeObject.getStudentId());
		String teacherProfile = teacherFound.getProfile();

		if (teacherProfile.equals("Amateur")) {
			operationValue = (double) 30;
		} else if (teacherProfile.equals("Junior")) {
			operationValue = (double) 25;
		} else if (teacherProfile.equals("Senior")) {
			operationValue = (double) 22;
		} else if (teacherProfile.equals("Master")) {
			operationValue = (double) 20;
		} else if (teacherProfile.equals("Gran Master")) {
			operationValue = (double) 17;
		}

		Long teacherId = timeObject.getTeacherId();
		Long studentId = timeObject.getStudentId();
		Double costPerHour = teacherFound.getHourCost();
		Double costPerMinute = (costPerHour / 60);
		Double operation = (costPerMinute * timeObject.getTimeOnTransaction());
		Double moneyForPlataform = ((operation * operationValue) / 100);
		Double moneyForTeacher = (operation - moneyForPlataform);
		Double actualMoneyForTeacher = teacherFound.getMoney();
		Double actualMoneyForStudent = studentFound.getMoney();
		Double newMoneyForTeacher = actualMoneyForTeacher + moneyForTeacher;
		Double newMoneyForStudent = actualMoneyForStudent - operation;
		Double actualMoneyForAdministraitor = userService.getAdministraitorMoney();
		Double newMoneyForAdministrator = actualMoneyForAdministraitor + moneyForPlataform;
		DetailsEntity detail = new DetailsEntity();
		detail.setIdStudent(studentId);
		detail.setIdTeacher(teacherId);
		detail.setStudentName(studentFound.getName().concat(studentFound.getLastName()));
		detail.setTeacherName(teacherFound.getName().concat(teacherFound.getLastName()));
		detail.setDuration(timeObject.getTimeOnTransaction().toString());
		detail.setStudentPayment(operation.toString());
		detail.setTeacherEarning(moneyForTeacher.toString());
		detail.setPlataformEarning(moneyForPlataform.toString());

		studentService.setStudentMoney(newMoneyForStudent, studentId);
		userService.setPlataformMoney(newMoneyForAdministrator);
		teacherService.setTeacherMoney(newMoneyForTeacher, teacherId);
		detailService.saveDetail(detail);

		return null;
	}

	@GetMapping("/student/transfer/{money}/{studentId}")
	public void setMoneyStudent(@PathVariable Double money, @PathVariable Long studentId) {
		studentService.setStudentMoney(money, studentId);
	}

	@GetMapping("/student/getMoney/{studentId}")
	public Long getMoneyFromStudent(@PathVariable Long studentId) {
		return studentService.moneyFromStudent(studentId);
	}

	@PostMapping("/student/upload/image")
	public ResponseEntity<?> uploadStudentImage(@RequestParam("studentImage") MultipartFile imageStudent,
			@RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();
		StudentEntity bodyToReturn = new StudentEntity();

		StudentEntity studentFound = studentService.findStudent(id);

		if (!imageStudent.isEmpty()) {
			String pictureName = UUID.randomUUID().toString() + "_"
					+ imageStudent.getOriginalFilename().replace(" ", "");
			Path picturePath = Paths.get("student-image").resolve(pictureName).toAbsolutePath();

			try {
				Files.copy(imageStudent.getInputStream(), picturePath);
			} catch (IOException e) {
				response.put("errorMsg", "Error al subir la imagen del estudiante");
				log.info(executionTime() + "-Error al subir la imagen");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			String lastStudentPicture = studentFound.getPicture();

			if (lastStudentPicture != null && lastStudentPicture.length() > 0) {

				Path pictureLastPath = Paths.get("student-image").resolve(lastStudentPicture).toAbsolutePath();
				File lastPicture = pictureLastPath.toFile();
				if (lastPicture.exists() && lastPicture.canRead()) {
					lastPicture.delete();
				}
			}

			studentFound.setPicture(pictureName);
			studentService.saveStudent(studentFound);
			bodyToReturn = studentFound;
			bodyToReturn.setPassword("");
		}

		return new ResponseEntity<StudentEntity>(bodyToReturn, HttpStatus.OK);
	}

	@GetMapping("/student/upload/image/{pictureStudentName:.+}")
	public ResponseEntity<Resource> showPictureCourse(@PathVariable String pictureStudentName) {

		if (pictureStudentName.isEmpty() || pictureStudentName.length() <= 0 || pictureStudentName == "") {
			pictureStudentName = "default.png";
		}

		Path picturePath = Paths.get("student-image").resolve(pictureStudentName).toAbsolutePath();
		Resource resource = null;

		try {

			resource = new UrlResource(picturePath.toUri());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		if (!resource.exists() && !resource.isReadable()) {
			throw new RuntimeException("No se pudo cargar la imagen: " + pictureStudentName);
		}

		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

		return new ResponseEntity<Resource>(resource, cabecera, HttpStatus.OK);
	}

	@GetMapping("/student/teacher/set/grupalCourse/{grupalCourseCost}/{teacherId}/{studentId}")
	public GrupalCoursePurchaseOperationResult grupalCourseTransaction(@PathVariable Double grupalCourseCost,
			@PathVariable Long teacherId, @PathVariable Long studentId) {
		
		GrupalCoursePurchaseOperationResult grupalCoursePurchaseOperationResult = new GrupalCoursePurchaseOperationResult();
		Double grupalCourseCostDouble = grupalCourseCost;
		Double operationValue = (double) 0;

		TeacherEntity teacherFound = teacherService.findTeacherById(teacherId);
		StudentEntity studentFound = studentService.findStudent(studentId);
		String teacherProfile = teacherFound.getProfile();

		if (teacherProfile.equals("Amateur")) {
			operationValue = (double) 30;
		} else if (teacherProfile.equals("Junior")) {
			operationValue = (double) 25;
		} else if (teacherProfile.equals("Senior")) {
			operationValue = (double) 22;
		} else if (teacherProfile.equals("Master")) {
			operationValue = (double) 20;
		} else if (teacherProfile.equals("Gran Master")) {
			operationValue = (double) 17;
		}

		Double moneyForPlataform = (grupalCourseCostDouble * operationValue) / 100;
		Double moneyForTeacher = (grupalCourseCostDouble - moneyForPlataform);
		Double actualMoneyForTeacher = teacherFound.getMoney();
		Double actualMoneyForStudent = studentFound.getMoney();
		Double newMoneyForTeacher = actualMoneyForTeacher + moneyForTeacher;
		Double newMoneyForStudent = actualMoneyForStudent - grupalCourseCostDouble;
		Double actualMoneyForAdministraitor = userService.getAdministraitorMoney();
		Double newMoneyForAdministrator = actualMoneyForAdministraitor + moneyForPlataform;

		studentService.setStudentMoney(newMoneyForStudent, studentId);
		userService.setPlataformMoney(newMoneyForAdministrator);
		teacherService.setTeacherMoney(newMoneyForTeacher, teacherId);
		grupalCoursePurchaseOperationResult.setMoneyForTeacher(moneyForTeacher.toString());
		grupalCoursePurchaseOperationResult.setMoneyForPlataform(moneyForPlataform.toString());

		return grupalCoursePurchaseOperationResult;
	}

	@GetMapping("/student/get/class-details/{studentId}")
	public List<DetailsEntity> getClassesDetails(@PathVariable Long studentId) {

		List<DetailsEntity> listOfDetails = detailService.listOfDetailsFilterByDate("1000-01-01", "3000-01-01");
		List<DetailsEntity> filteredList = listOfDetails.stream().filter(obj -> obj.getIdStudent() == studentId)
				.collect(Collectors.toList());
		return filteredList;
	}

}
