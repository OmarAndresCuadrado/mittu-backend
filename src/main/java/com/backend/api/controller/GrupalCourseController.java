package com.backend.api.controller;

import java.io.File;
import java.math.BigInteger;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.backend.api.entity.GrupalCourseEntity;
import com.backend.api.entity.GrupalCourseInscriptionEntity;
import com.backend.api.serviceImplement.GrupalCourseServiceImpl;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class GrupalCourseController {

	private final Logger log = LoggerFactory.getLogger(GrupalCourseController.class);

	@Autowired
	private GrupalCourseServiceImpl grupalCourseService;

	@GetMapping("/grupal/course")
	public List<GrupalCourseEntity> listOfGrupalCourse() {
		return grupalCourseService.listOfGrupalCourse();
	}

	@GetMapping("/grupal/course/{id}")
	public GrupalCourseEntity findGrupalCourseById(@PathVariable Long id) {
		return grupalCourseService.findGrupalCourseById(id);
	}

	@PostMapping("/grupal/course")
	public GrupalCourseEntity saveGrupalCourse(@RequestBody GrupalCourseEntity grupalCourseEntity) {
		return grupalCourseService.saveGrupalCourse(grupalCourseEntity);

	}

	@PutMapping("/grupal/course/{id}")
	public GrupalCourseEntity updateGrupalCourse(@PathVariable Long id,
			@RequestBody GrupalCourseEntity grupalCourseEntityFromClient) {
		GrupalCourseEntity grupalCourseFound = new GrupalCourseEntity();
		GrupalCourseEntity grupalCourseUpdated = new GrupalCourseEntity();

		System.out.println(grupalCourseEntityFromClient.getName());

		grupalCourseFound = grupalCourseService.findGrupalCourseById(id);
		grupalCourseFound.setName(grupalCourseEntityFromClient.getName());
		grupalCourseFound.setDescription(grupalCourseEntityFromClient.getDescription());
		grupalCourseFound.setClassTime(grupalCourseEntityFromClient.getClassTime());
		grupalCourseFound.setUrlMeet(grupalCourseEntityFromClient.getUrlMeet());
		grupalCourseFound.setPrice(grupalCourseEntityFromClient.getPrice());
		grupalCourseUpdated = grupalCourseFound;

		grupalCourseService.saveGrupalCourse(grupalCourseUpdated);

		// Implementar la actualizacion de la imagen

		System.out.println("He llegado a actualizar el curso grupal");
		return grupalCourseUpdated;
	}

	@GetMapping("/grupal/course/teacher/{idTeacher}")
	public List<GrupalCourseEntity> getGrupalCoursesByTeacherId(@PathVariable Long idTeacher) {
		return grupalCourseService.getGrupalCoursesByTeacherId(idTeacher);
	}

	@GetMapping("/grupal/course/search/{grupalCourseName}")
	public List<GrupalCourseEntity> searchByCourseName(@PathVariable String grupalCourseName) {
		System.out.println(grupalCourseName);
		List<GrupalCourseEntity> listOfGrupalCoursesFoundByName = new ArrayList<>();
		listOfGrupalCoursesFoundByName = this.grupalCourseService.searchByGrupalCourseName(grupalCourseName);
		System.out.println("lista de cursos grupales, " + listOfGrupalCoursesFoundByName);
		return listOfGrupalCoursesFoundByName;
	}

	@PostMapping("/grupal/course/create/inscription")
	public void createInscriptionToGrupalCourse(@RequestBody GrupalCourseInscriptionEntity params) {
		System.out.println("objeto recibido " + params);
		System.out.println("objeto recibido id curso " + params.getCourseId());
		System.out.println("objeto recibido id estudiante" + params.getStudentId());

		try {
			this.grupalCourseService.createInscriptionToGrupalCourse(params.getCourseId(), params.getStudentId());
			
		}  catch (DataAccessException e) {
			log.info(executionTime() + "-Error controlado se ha intendo inscribirse a un curso grupal al cual ya se encuentra inscrito");

		}
		

	}

	@GetMapping("/grupal/course/courses/{idStudent}")
	public List<GrupalCourseEntity> getAllGrupalCoursesFromStudent(@PathVariable Long idStudent) {
		GrupalCourseEntity GrupalCoursesFound = new GrupalCourseEntity();
		List<GrupalCourseEntity> listOfGrupalCoursesFoundAll = new ArrayList<GrupalCourseEntity>();
		List<BigInteger> listOfGrupalCoursesFoundByStudentId = this.grupalCourseService
				.findGrupalCoursesFromStudent(idStudent);

		for (BigInteger bigInteger : listOfGrupalCoursesFoundByStudentId) {
			GrupalCoursesFound = grupalCourseService.findGrupalCourseById(bigInteger.longValue());
			GrupalCoursesFound.setStudentes(new ArrayList<>());
			System.out.println("id que se esta buscando el curso grupal " + bigInteger.longValue());
			listOfGrupalCoursesFoundAll.add(GrupalCoursesFound);
		}

		return listOfGrupalCoursesFoundAll;
	}

	@PostMapping("/grupal/course/upload/image")
	public ResponseEntity<?> uploadGrupalCourseImage(@RequestParam("grupalCourseImage") MultipartFile grupalCourseImage,
			@RequestParam("id") Long id) {
		Map<String, Object> response = new HashMap<>();

		GrupalCourseEntity grupalCourseFound = grupalCourseService.findGrupalCourseById(id);

		if (!grupalCourseImage.isEmpty()) {
			String pictureName = UUID.randomUUID().toString() + "_"
					+ grupalCourseImage.getOriginalFilename().replace(" ", "");
			Path picturePath = Paths.get("grupal-course-images").resolve(pictureName).toAbsolutePath();

			try {
				Files.copy(grupalCourseImage.getInputStream(), picturePath);
			} catch (Exception e) {
				response.put("errorMsg", "Error al subir la imagen del curso");
				log.info(executionTime() + "-Error al subir la imagen");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			String lastGrupalCoursePicture = grupalCourseFound.getPicture();

			if (lastGrupalCoursePicture != null && lastGrupalCoursePicture.length() > 0) {

				Path pictureLastPath = Paths.get("grupal-course-images").resolve(lastGrupalCoursePicture)
						.toAbsolutePath();
				File lastPicture = pictureLastPath.toFile();
				if (lastPicture.exists() && lastPicture.canRead()) {
					lastPicture.delete();
				}
			}

			grupalCourseFound.setPicture(pictureName);
			grupalCourseService.saveGrupalCourse(grupalCourseFound);

		}

		return new ResponseEntity<GrupalCourseEntity>(grupalCourseFound, HttpStatus.OK);
	}

	@GetMapping("/grupal/course/image/{pictureGrupalCourseName:.+}")
	public ResponseEntity<Resource> showPictureCourse(@PathVariable String pictureGrupalCourseName) {

		Path picturePath = Paths.get("grupal-course-images").resolve(pictureGrupalCourseName).toAbsolutePath();
		Resource resource = null;

		try {
			resource = new UrlResource(picturePath.toUri());
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!resource.exists() && !resource.isReadable()) {
			throw new RuntimeException("No se pudo cargar la imagen: " + pictureGrupalCourseName);
		}

		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

		return new ResponseEntity<Resource>(resource, cabecera, HttpStatus.OK);
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
