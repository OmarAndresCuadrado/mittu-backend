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
import com.backend.api.serviceInterface.ICourseServiceInterface;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class CourseController {

	private final Logger log = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	private ICourseServiceInterface courseService;

	@GetMapping("/course")
	public ResponseEntity<?> getAllCourses() {
		List<CourseEntity> listOfCourses = new ArrayList<>();
		Map<String, Object> response = new HashMap<>();

		try {
			listOfCourses = this.courseService.listOfCourses();

		} catch (DataAccessException e) {
			log.info(executionTime() + "-Error no se han podido conectar con la base de datos");
			response.put("errorMsg", "-Error no se han podido conectar con la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<List<CourseEntity>>(listOfCourses, HttpStatus.OK);
	}

	@GetMapping("/course/{id}")
	public ResponseEntity<?> findCourseById(@PathVariable Long id) {
		CourseEntity courseFound = new CourseEntity();
		Map<String, Object> response = new HashMap<>();

		try {
			courseFound = this.courseService.findCourseById(id);

			if (courseFound == null) {
				response.put("errorMsg", "No existe el curso con el id " + id);
				log.info(executionTime() + "-Error al buscar el curso con el id " + id);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}

		} catch (DataAccessException e) {
			response.put("errorMsg", "Error al realizar la conexion con la base de datos");
			log.info(executionTime() + "-Error no se han podido conectar con la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<CourseEntity>(courseFound, HttpStatus.OK);
	}

	@PostMapping("/course")
	public ResponseEntity<?> createCourse(@Valid @RequestBody CourseEntity courseEntity, BindingResult result) {
		CourseEntity course = new CourseEntity();
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errores de validacion", errors);
			log.info(executionTime() + "-Error de formulario de creacion de curso");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			course = this.courseService.saveCourse(courseEntity);
		} catch (DataAccessException e) {
			response.put("errorMsg", "Error al realizar la conexion con la base de datos");
			log.info(executionTime() + "-Error no se han podido conectar con la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<CourseEntity>(course, HttpStatus.OK);
	}

	@PostMapping("/course/creation")
	public void createCourseInsertion(@RequestBody CourseEntity courseEntity) {
		 this.courseService.insertNewCourse(courseEntity.getName(), courseEntity.getFechaDeCreacion(), null,
				courseEntity.getIdTeacher(), courseEntity.getBusy(), courseEntity.getDescription(),
				courseEntity.getMeetUrlCourse(),courseEntity.getIdTeacher());
	}

	@PutMapping("/course/{id}")
	public ResponseEntity<?> updateCourse(@Valid @RequestBody CourseEntity courseEntity, @PathVariable Long id,
			BindingResult result) {
		CourseEntity courseFound = new CourseEntity();
		CourseEntity courseUpdated = new CourseEntity();
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errores de validacion", errors);
			log.info(executionTime() + "-Error de formulario de creacion de curso");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			courseFound = this.courseService.findCourseById(id);

			if (courseFound == null) {
				response.put("errorMsg", "Error al buscar el curso con el id " + id);
				log.info(executionTime() + "-Error al buscar el curso con el id " + id);
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}

		} catch (DataAccessException e) {
			response.put("errorMsg", "Error al realizar la conexion con la base de datos");
			log.info(executionTime() + "-Error no se han podido conectar con la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		courseFound.setName(courseEntity.getName());
		courseFound.setPicture(courseEntity.getPicture());
		courseFound.setDescription(courseEntity.getDescription());
		courseFound.setMeetUrlCourse(courseEntity.getMeetUrlCourse());
		courseUpdated = courseFound;
		this.courseService.saveCourse(courseUpdated);
		return new ResponseEntity<CourseEntity>(courseUpdated, HttpStatus.OK);
	}

	@PostMapping("/course/enable/{id}")
	public void enableCourse(@PathVariable Long id) {

	}

	@PostMapping("/course/disable/{id}")
	public void disableCourse(@PathVariable Long id) {

	}

	@GetMapping("/course/teacher/course/{idTeacher}")
	public List<CourseEntity> findCoursesOfTeacher(@PathVariable Long idTeacher) {
		List<CourseEntity> listOfCoursesFound = new ArrayList<>();
		listOfCoursesFound = this.courseService.findCoursesOfTeacher(idTeacher);
		return listOfCoursesFound;
	}

	@GetMapping("/course/find/course/name/{courseName}")
	public List<CourseEntity> searchByCourseName(@PathVariable String courseName) {
		List<CourseEntity> listOfCoursesFoundByName = new ArrayList<>();
		listOfCoursesFoundByName = this.courseService.searchByCourseName(courseName);
		return listOfCoursesFoundByName;
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

	@PostMapping("/course/upload/image")
	public ResponseEntity<?> uploadCourseImage(@RequestParam("imageCourse") MultipartFile imageCourse,
			@RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();

		CourseEntity courseFound = courseService.findCourseById(id);

		if (!imageCourse.isEmpty()) {
			String pictureName = UUID.randomUUID().toString() + "_"
					+ imageCourse.getOriginalFilename().replace(" ", "");
			Path picturePath = Paths.get("course-images").resolve(pictureName).toAbsolutePath();

			try {
				Files.copy(imageCourse.getInputStream(), picturePath);
			} catch (IOException e) {
				response.put("errorMsg", "Error al subir la imagen del curso");
				log.info(executionTime() + "-Error al subir la imagen");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			String lastCoursePicture = courseFound.getPicture();

			if (lastCoursePicture != null && lastCoursePicture.length() > 0) {

				Path pictureLastPath = Paths.get("course-images").resolve(lastCoursePicture).toAbsolutePath();
				File lastPicture = pictureLastPath.toFile();
				if (lastPicture.exists() && lastPicture.canRead()) {
					lastPicture.delete();
				}
			}

			courseFound.setPicture(pictureName);
			courseService.saveCourse(courseFound);

		}

		return new ResponseEntity<CourseEntity>(courseFound, HttpStatus.OK);

	}

	@GetMapping("/course/upload/image/{pictureCourseName:.+}")
	public ResponseEntity<Resource> showPictureCourse(@PathVariable String pictureCourseName) {

		Path picturePath = Paths.get("course-images").resolve(pictureCourseName).toAbsolutePath();
		Resource resource = null;

		try {

			resource = new UrlResource(picturePath.toUri());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		if (!resource.exists() && !resource.isReadable()) {
			throw new RuntimeException("No se pudo cargar la imagen: " + pictureCourseName);
		}

		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

		return new ResponseEntity<Resource>(resource, cabecera, HttpStatus.OK);
	}

}
