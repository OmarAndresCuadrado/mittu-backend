package com.backend.api.controller;

import java.io.File;
import java.io.IOException;
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

import com.backend.api.entity.CalificationClass;
import com.backend.api.entity.GrupalCourseEntity;
import com.backend.api.entity.ProfileClass;
import com.backend.api.entity.StudentEntity;
import com.backend.api.entity.TeacherEntity;
import com.backend.api.entity.TeacherInformationClass;
import com.backend.api.entity.Usuario;
import com.backend.api.serviceInterface.IGrupalCourseServiceInterface;
import com.backend.api.serviceInterface.ITeacherServiceInterface;
import com.backend.api.serviceInterface.IUsuarioInterface;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class TeacherController {

	private final Logger log = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	private ITeacherServiceInterface teacherService;

	@Autowired
	private IGrupalCourseServiceInterface grupalCourseService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private IUsuarioInterface usuarioService;

	@GetMapping("/teacher")
	public ResponseEntity<?> listAllTeachers() {
		List<TeacherEntity> listModify = new ArrayList<>();
		Map<String, Object> response = new HashMap<>();

		try {
			String emptyPassword = "";
			this.teacherService.listOfTeachers().forEach((resp) -> {
				resp.setPassword(emptyPassword);
				listModify.add(resp);
			});
			if (listModify.size() == 0 || listModify == null) {
				response.put("errorMsg", "no se han podido enlistar los profesores");
				log.info(executionTime() + "-Error no se han podido enlistar los profesores");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
		} catch (DataAccessException e) {
			response.put("errorMsg", "Error al realizar la conexion con la base de datos");
			log.info(executionTime() + "-Error no se han podido conectar con la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<List<TeacherEntity>>(listModify, HttpStatus.OK);
	}

	@GetMapping("/teacher/{id}")
	public ResponseEntity<?> findTeacherById(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		TeacherEntity teacherFound = new TeacherEntity();
		String emptyPassword = "";

		try {
			teacherFound = teacherService.findTeacherById(id);

			if (teacherFound == null) {
				response.put("errorMsg", "No existe el usuario con el id " + id);
				log.info(executionTime() + "-Error al buscar el profesor con el id " + id);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}

		} catch (DataAccessException e) {
			response.put("errorMsg", "Error al realizar la conexion con la base de datos");
			log.info(executionTime() + "-Error no se han podido conectar con la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		teacherFound.setPassword(emptyPassword);
		return new ResponseEntity<TeacherEntity>(teacherFound, HttpStatus.OK);
	}

	@PostMapping("/teacher")
	public ResponseEntity<?> saveTeacher(@Valid @RequestBody TeacherEntity teacherEntity, BindingResult result) {
		Map<String, Object> response = new HashMap<>();
		TeacherEntity teacherCreated = new TeacherEntity();
		Usuario userTeacher = new Usuario();
		Usuario finalUsuario = new Usuario();
		String teacherUserNameEmail = "";
		teacherUserNameEmail = teacherEntity.getEmail();
		teacherEntity.setUsername(teacherUserNameEmail);

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errores de validacion", errors);
			log.info(executionTime() + "-Error de formulario de creacion de profesor");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			String cleanPassword = teacherEntity.getPassword();
			String encodedPassword = passwordEncoder.encode(cleanPassword);
			teacherEntity.setPassword(encodedPassword);
			String username = teacherEntity.getUsername();
			String password = teacherEntity.getPassword();
			String nombre = teacherEntity.getName();
			String apellido = teacherEntity.getLastName();
			String email = teacherEntity.getEmail();
			String city = teacherEntity.getCity();
			userTeacher.setCity(city);
			userTeacher.setUsername(username);
			userTeacher.setPassword(password);
			userTeacher.setEnabled(false);
			userTeacher.setName(nombre);
			userTeacher.setLastName(apellido);
			userTeacher.setEmail(email);
			userTeacher.setMoney(Double.valueOf(0));

			try {
				finalUsuario = usuarioService.saveUser(userTeacher);
				teacherService.sentEmailToTeacher(teacherEntity, cleanPassword);
			} catch (DataAccessException e) {
				response.put("errorMsg",
						"Error al crear el profesor el correo " + teacherEntity.getEmail() + " " + "esta duplicado");
				log.info(executionTime() + "-Error no se han podido conectar con la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			System.out.println("id del usuario creado para el profesor" + finalUsuario.getId());
			Long teacherId = finalUsuario.getId();
			teacherEntity.setIdUser(teacherId);
			teacherEntity.setEnabled(false);
			teacherEntity.setEnabledPlatform(false);
			teacherEntity.setMeetUrl("");
			teacherEntity.setProfile("Amateur");
			teacherEntity.setMoney(Double.valueOf(0));
			teacherEntity.setCity(city);
			teacherEntity.setTermsAndConditions(true);
			teacherEntity.setBusy(true);
			teacherEntity.setCalification(Double.valueOf(0));
			teacherEntity.setCountStudent(0);
			teacherEntity.setDescription("");
			teacherEntity.setEducation("");
			teacherEntity.setHourCost(Double.valueOf(0));
			teacherEntity.setPicture("");
			teacherCreated = teacherService.saveTeacher(teacherEntity);
			usuarioService.findTeacherCreatedAndSetRole(teacherId);
			System.out.println("id del profesor al que le envio la actualizacion de tiempo " + teacherCreated.getId());
			teacherService.setTeacherTime(0, teacherCreated.getId());

		} catch (DataAccessException e) {
			response.put("errorMsg", "Error al realizar la conexion con la base de datos");
			log.info(executionTime() + "-Error no se han podido conectar con la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<TeacherEntity>(teacherCreated, HttpStatus.OK);
	}

	@PutMapping("/teacher/{id}")
	public ResponseEntity<?> updateTeacher(@RequestBody TeacherEntity teacherEntity, @PathVariable Long id) {
		TeacherEntity teacherFound = this.teacherService.findTeacherById(id);

		Map<String, Object> response = new HashMap<>();

		if (teacherFound == null) {
			response.put("mensaje", "Error: no se pudo editar, el profesor con ID: "
					.concat(id.toString().concat(" no existe en la base de datos!")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		try {

			if (teacherEntity.getName() == null) {
				response.put("mensaje", "Error: no se pudo editar, el profesor con ID: "
						.concat(id.toString().concat("el nombre no puede ser vacio")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}

			else if (teacherEntity.getLastName() == null) {
				response.put("mensaje", "Error: no se pudo editar, el profesor con ID: "
						.concat(id.toString().concat("el apellido no puede ser vacio")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}

			else if (teacherEntity.getPhone() == null) {
				response.put("mensaje", "Error: no se pudo editar, el profesor con ID: "
						.concat(id.toString().concat("el telefono no puede ser vacio")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}

			else if (teacherEntity.getMeetUrl() == null) {
				response.put("mensaje", "Error: no se pudo editar, el profesor con: "
						.concat(id.toString().concat("el meet no puede ser vacio")));
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
			}

			teacherFound.setName(teacherEntity.getName());
			teacherFound.setLastName(teacherEntity.getLastName());
			teacherFound.setPhone(teacherEntity.getPhone());
			teacherFound.setMeetUrl(teacherEntity.getMeetUrl());
			teacherService.saveTeacher(teacherFound);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el profesor en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<TeacherEntity>(teacherFound, HttpStatus.CREATED);
	}

	// Implementar metodo para encontrar profesor por id
	@GetMapping("/teacher/disable/{idUser}")
	public ResponseEntity<?> disableTeacher(@PathVariable Long idUser) {
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
		teacherService.disableTeacher(idUser);
		response.put("Msg", "ok");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

	}

	// Implementar metodo para encontrar profesor por id
	@GetMapping("/teacher/enable/{idUser}")
	public ResponseEntity<?> enableTeacher(@PathVariable Long idUser) {
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
		teacherService.enableTeacher(idUser);
		response.put("Msg", "ok");
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

	@PostMapping("/teacher/upload/image")
	public ResponseEntity<?> uploadTeacherImage(@RequestParam("teacherImage") MultipartFile imageTeacher,
			@RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();
		TeacherEntity bodyToReturn = new TeacherEntity();

		TeacherEntity teacherFound = teacherService.findTeacherById(id);

		if (!imageTeacher.isEmpty()) {
			String pictureName = UUID.randomUUID().toString() + "_"
					+ imageTeacher.getOriginalFilename().replace(" ", "");
			Path picturePath = Paths.get("teacher-image").resolve(pictureName).toAbsolutePath();

			try {
				Files.copy(imageTeacher.getInputStream(), picturePath);
			} catch (IOException e) {
				response.put("errorMsg", "Error al subir la imagen del profesor");
				log.info(executionTime() + "-Error al subir la imagen");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			String lastTeacherPicture = teacherFound.getPicture();

			if (lastTeacherPicture != null && lastTeacherPicture.length() > 0) {

				Path pictureLastPath = Paths.get("teacher-image").resolve(lastTeacherPicture).toAbsolutePath();
				File lastPicture = pictureLastPath.toFile();
				if (lastPicture.exists() && lastPicture.canRead()) {
					lastPicture.delete();
				}
			}

			teacherFound.setPicture(pictureName);
			teacherService.saveTeacher(teacherFound);
			bodyToReturn = teacherFound;
			bodyToReturn.setCourses(new ArrayList<>());
			bodyToReturn.setPassword("");

		}

		return new ResponseEntity<TeacherEntity>(bodyToReturn, HttpStatus.OK);
	}

	@GetMapping("/teacher/upload/image/{pictureTeacherName:.+}")
	public ResponseEntity<Resource> showPictureCourse(@PathVariable String pictureTeacherName) {

		if(pictureTeacherName.isEmpty() || pictureTeacherName.length() <= 0) {
			pictureTeacherName = "default.png";
		}
		
		Path picturePath = Paths.get("teacher-image").resolve(pictureTeacherName).toAbsolutePath();
		Resource resource = null;

		try {

			resource = new UrlResource(picturePath.toUri());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		if (!resource.exists() && !resource.isReadable()) {
			throw new RuntimeException("No se pudo cargar la imagen: " + pictureTeacherName);
		}

		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

		return new ResponseEntity<Resource>(resource, cabecera, HttpStatus.OK);
	}

	// metodo para obtener minutos del profesor
	@GetMapping("/teacher/get/time/{idTeacher}")
	public Integer getTimeFromStudent(@PathVariable Long idTeacher) {

		Integer teacherTime = teacherService.getTeacherTime(idTeacher);
		System.out.println("tiempo recuperado " + teacherTime);
		return teacherTime;
	}

	@GetMapping("/teacher/get/califaction/{idTeacher}")
	public Double getTeacherCalification(@PathVariable Long idTeacher) {
		return teacherService.getTeacherCalification(idTeacher);
	}

	@GetMapping("/teacher/get/califaction-students/{idTeacher}")
	public Double getTeacherCalificationStudents(@PathVariable Long idTeacher) {
		return teacherService.getTeacherCalificationStudents(idTeacher);
	}

	@PostMapping("/teacher/post/califaction")
	public void updateTeacherCalification(@RequestBody CalificationClass calificationParameters) {
		teacherService.updateTeacherCalification(calificationParameters.getCalifaction(),
				calificationParameters.getStudentCount(), calificationParameters.getTeacherId());
	}

	@PostMapping("/teacher/post/profile")
	public void updateTeacherCalification(@RequestBody ProfileClass profileClass) {
		teacherService.updateProfileTeacher(profileClass.getProfile(), profileClass.getTeacherId());
	}

	@GetMapping("/teacher/get/information")
	public List<TeacherInformationClass> getTeachersInformation() {
		List<TeacherInformationClass> bodyResponseList = new ArrayList<TeacherInformationClass>();
		List<TeacherEntity> teachers = new ArrayList<TeacherEntity>();
		teachers = teacherService.listOfTeachers();
		teachers.forEach(obj -> {
			TeacherInformationClass bodyResponse = new TeacherInformationClass();
			bodyResponse.setName(obj.getName());
			bodyResponse.setLastName(obj.getLastName());
			bodyResponse.setPhone(obj.getPhone());
			bodyResponse.setPicture(obj.getPicture());
			bodyResponse.setHourCost(obj.getHourCost());
			bodyResponse.setProfile(obj.getProfile());
			bodyResponse.setEducation(obj.getEducation());
			bodyResponse.setDescription(obj.getDescription());
			bodyResponse.setCalification(obj.getCalification());
			bodyResponse.setCountStudent(obj.getCountStudent());
			bodyResponse.setEmail(obj.getEmail());
			bodyResponse.setCourses(obj.getCourses());
			bodyResponse.setBusy(obj.getBusy());
			List<GrupalCourseEntity> grupalCoursesFound = grupalCourseService.getGrupalCoursesByTeacherId(obj.getId());
			grupalCoursesFound.forEach(objCourse -> objCourse.setStudentes(new ArrayList<StudentEntity>()));
			bodyResponse.setGrupalCourses(grupalCoursesFound);
			bodyResponseList.add(bodyResponse);
		});

		return bodyResponseList;
	}

}
