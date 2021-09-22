package com.backend.api.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

import com.backend.api.entity.CourseEntity;
import com.backend.api.entity.Mensaje;
import com.backend.api.entity.PrivateChannelMesajeEntity;
import com.backend.api.entity.SocketTopicTeacherEntity;
import com.backend.api.entity.TeacherEntity;
import com.backend.api.entity.TimeEntity;
import com.backend.api.serviceInterface.ITeacherServiceInterface;

@CrossOrigin(origins = { "*" })
@Controller
public class SocketController {

	@Autowired
	private ITeacherServiceInterface teacherService;

	private List<CourseEntity> actualValue = new ArrayList<>();

	private String[] colores = { "red", "green", "blue", "magenta", "purple", "orange" };

	// Hacer un socket solamente para recibir los cursos de cada estudiante

	@MessageMapping("/teacher/socket")
	@SendTo("/topic/teacher/socket")
	@Transactional
	public List<CourseEntity> teacherSocket(SocketTopicTeacherEntity socketEntity) {
		SocketTopicTeacherEntity socketTopicTeacherEntity = socketEntity;

		if (socketTopicTeacherEntity.getMensaje().equals("ENABLE_TEACHER")) {
			TeacherEntity teacherFound = new TeacherEntity();
			List<CourseEntity> courseList = new ArrayList<>();
			teacherFound = teacherService.findTeacherById(socketTopicTeacherEntity.getIdTeacher());
			String teacherName = teacherFound.getName().concat(" ").concat(teacherFound.getLastName());
			String urlMeet = teacherFound.getMeetUrl();
			teacherFound.getCourses().forEach((res) -> {
				res.setBusy(false);
				res.setIdTeacher(socketTopicTeacherEntity.getIdTeacher());
				res.setTeacherName(teacherName);
				courseList.add(res);
			});
			actualValue = courseList;
			teacherService.setTeacherAvailable(socketTopicTeacherEntity.getIdTeacher());
			return courseList;

		} else if (socketTopicTeacherEntity.getMensaje().equals("DISABLE_TEACHER")) {
			TeacherEntity teacherFound = new TeacherEntity();
			List<CourseEntity> courseList = new ArrayList<>();
			teacherFound = teacherService.findTeacherById(socketTopicTeacherEntity.getIdTeacher());
			String teacherName = teacherFound.getName().concat(" ").concat(teacherFound.getLastName());
			String urlMeet = teacherFound.getMeetUrl();
			teacherFound.getCourses().forEach((res) -> {
				res.setBusy(true);
				res.setIdTeacher(socketTopicTeacherEntity.getIdTeacher());
				res.setTeacherName(teacherName);
				courseList.add(res);
			});
			actualValue = courseList;
			teacherService.setTeacherBusy(socketTopicTeacherEntity.getIdTeacher());
			return courseList;
		}

		if (socketTopicTeacherEntity.getMensaje().equals("SESION_START")) {
			actualValue.forEach((action) -> {
			});
			return actualValue;
		}
		return null;
	}

	@MessageMapping("/teacher/state/{idTeacher}")
	@SendTo("/topic/teacher/state/{idTeacher}")
	@Transactional
	public Boolean verifyStateOfTeacher(@DestinationVariable Long idTeacher) {
		return teacherService.getStateOfTeacher(idTeacher);
	}

	@MessageMapping("/teacher/state/chat/{idTeacher}/{idStudent}")
	@SendTo("/topic/teacher/state/chat/{idTeacher}/{idStudent}")
	@Transactional
	public Boolean verifyTeacherStateOfChat(@DestinationVariable Long idTeacher, @DestinationVariable Long idStudent,
			String stateForChat) {
		if (stateForChat.equals("true")) {
			teacherService.setTeacherChatBusy(idTeacher);
		} else if (stateForChat.equals("false")) {
			teacherService.setTeacherChatAvailable(idTeacher);
		}

		return null;
	}

	@MessageMapping("/teacher/state/chat/{idTeacher}")
	@SendTo("/topic/teacher/state/chat/{idTeacher}")
	@Transactional
	public Boolean verifyTeacherStateOfChatGet(@DestinationVariable Long idTeacher, String stateForChat) {
		return teacherService.getChatStateFromTeacher(idTeacher);
	}

	@MessageMapping("/private/chat/message/{idTeacher}/{idStudent}")
	@SendTo("/topic/private/chat/message/{idTeacher}/{idStudent}")
	@Transactional
	public Mensaje enviarMensaje(@DestinationVariable Long idTeacher, @DestinationVariable Long idStudent,
			Mensaje mensaje) {
		mensaje.setFecha(new Date().getTime());
		if (mensaje.getTipo().equals("NUEVO_USUARIO")) {
			mensaje.setTexto("Nuevo usuario ");
			mensaje.setColor(colores[new Random().nextInt(colores.length)]);
		}
		return mensaje;
	}

	@MessageMapping("/private/chanel/teacher/{idTeacher}")
	@SendTo("/topic/private/chanel/teacher/{idTeacher}")
	@Transactional
	public ResponseEntity<?> privateChanelTeacher(@DestinationVariable Long idTeacher,
			PrivateChannelMesajeEntity requestBody) {
		try {
			if (requestBody.getOnChat().equals(false)) {
				return new ResponseEntity<PrivateChannelMesajeEntity>(requestBody, HttpStatus.OK);

			} else {
				requestBody.setFecha(new Date().getTime());
				if (requestBody.getTipo().equals("NUEVO_USUARIO")) {
					requestBody.setTexto("Nuevo usuario ");
					requestBody.setColor(colores[new Random().nextInt(colores.length)]);
				}
				return new ResponseEntity<PrivateChannelMesajeEntity>(requestBody, HttpStatus.OK);
			}

		} catch (Exception e) {
			requestBody.setFecha(new Date().getTime());
			return new ResponseEntity<PrivateChannelMesajeEntity>(requestBody, HttpStatus.OK);
		}
	}

	@MessageMapping("/private/chanel/teacher/{idTeacher}/{idStudent}")
	@SendTo("/topic/private/chanel/teacher/{idTeacher}/{idStudent}")
	@Transactional
	public ResponseEntity<?> privateChanelTeacherStudent(@DestinationVariable Long idTeacher,
			PrivateChannelMesajeEntity requestBody) {
		try {
			if (requestBody.getOnChat().equals(false)) {
				return new ResponseEntity<PrivateChannelMesajeEntity>(requestBody, HttpStatus.OK);

			} else {
				requestBody.setFecha(new Date().getTime());
				if (requestBody.getTipo().equals("NUEVO_USUARIO")) {
					requestBody.setTexto("Nuevo usuario ");
					requestBody.setColor(colores[new Random().nextInt(colores.length)]);
				}
				return new ResponseEntity<PrivateChannelMesajeEntity>(requestBody, HttpStatus.OK);
			}

		} catch (Exception e) {
			requestBody.setFecha(new Date().getTime());
			return new ResponseEntity<PrivateChannelMesajeEntity>(requestBody, HttpStatus.OK);
		}
	}

	@MessageMapping("/notification/chanel/teacher/{idTeacher}")
	@SendTo("/topic/notification/chanel/teacher/{idTeacher}")
	@Transactional
	public ResponseEntity<?> notificationChanel(@DestinationVariable Long idTeacher,
			@RequestBody SocketTopicTeacherEntity socketEntity) {

		if (socketEntity.getMensaje().equals("INITIAL_CONNECTION_NOTIFICATION_CHANEL")) {
			String answer = "conxion inicial al canal de notificaciones";
			return new ResponseEntity<String>(answer, HttpStatus.OK);
		}

		if (socketEntity.getMensaje().equals("ASKING_FOR_MEETING")) {
			String answer = "ANSWER_ASKING_FOR_MEETING";
			return new ResponseEntity<String>(answer, HttpStatus.OK);

		}

		if (socketEntity.getMensaje().equals("TEACHER_ACCEPT")) {
			SocketTopicTeacherEntity answer = new SocketTopicTeacherEntity();
			answer.setMensaje("ANSWER_TEACHER_ACCEPT");
			answer.setIdTeacher(socketEntity.getIdTeacher());
			answer.setIdStudent(socketEntity.getIdStudent());
			answer.setUrlMeetOnQueue(socketEntity.getUrlMeetOnQueue());
			return new ResponseEntity<SocketTopicTeacherEntity>(answer, HttpStatus.OK);
		}

		if (socketEntity.getMensaje().equals("TEACHER_DENY")) {
			String answer = "ANSWER_TEACHER_DENY";
			return new ResponseEntity<String>(answer, HttpStatus.OK);

		}

		if (socketEntity.getMensaje().equals("ESTUDENT_DENY")) {
			String answer = "ANSWER_ESTUDENT_DENY";
			return new ResponseEntity<String>(answer, HttpStatus.OK);

		}

		return null;
	}

	@MessageMapping("/teacher/student/timer/{idTeacher}/{idStudent}")
	@SendTo("/topic/teacher/student/timer/{idTeacher}/{idStudent}")
	@Transactional
	public ResponseEntity<?> notificationChanel(@DestinationVariable Long idTeacher,
			@DestinationVariable Long idStudent, @RequestBody TimeEntity timeObject) {

		if (timeObject.getMessage().equals("INITAL_TIMER_CONNECTION_STUDENT")) {
			timeObject.setMessage("ANSWER_INITAL_TIMER_CONNECTION_STUDENT");
			return new ResponseEntity<TimeEntity>(timeObject, HttpStatus.OK);
		}

		if (timeObject.getMessage().equals("INITAL_TIMER_CONNECTION_TEACHER")) {
			timeObject.setMessage("ANSWER_INITAL_TIMER_CONNECTION_TEACHER");
			return new ResponseEntity<TimeEntity>(timeObject, HttpStatus.OK);
		}

		if (timeObject.getMessage().equals("TIMER_START")) {
			timeObject.setMessage("ANSWER_TIMER_START");
			return new ResponseEntity<TimeEntity>(timeObject, HttpStatus.OK);
		}

		if (timeObject.getMessage().equals("TIMER_STOP")) {
			timeObject.setMessage("ANSWER_TIMER_STOP");
			return new ResponseEntity<TimeEntity>(timeObject, HttpStatus.OK);
		}

		return null;
	}

}
