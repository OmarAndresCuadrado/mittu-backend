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
		System.out.println("evento que ha llegado al agente" + socketTopicTeacherEntity.getMensaje());

		if (socketTopicTeacherEntity.getMensaje().equals("ENABLE_TEACHER")) {
			TeacherEntity teacherFound = new TeacherEntity();
			List<CourseEntity> courseList = new ArrayList<>();
			System.out.println("Entrada a enable teacher");
			System.out.println("ID DEL PROFESOR " + socketTopicTeacherEntity.getIdTeacher());
			teacherFound = teacherService.findTeacherById(socketTopicTeacherEntity.getIdTeacher());
			String teacherName = teacherFound.getName().concat(" ").concat(teacherFound.getLastName());
			String urlMeet = teacherFound.getMeetUrl();
			System.out.println("url de meet " + urlMeet);
			System.out.println("NOMBRE DEL PROFESOR " + teacherName);
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
			System.out.println("Entrada a disable teacher");
			System.out.println("ID DEL PROFESOR " + socketTopicTeacherEntity.getIdTeacher());
			teacherFound = teacherService.findTeacherById(socketTopicTeacherEntity.getIdTeacher());
			String teacherName = teacherFound.getName().concat(" ").concat(teacherFound.getLastName());
			String urlMeet = teacherFound.getMeetUrl();
			System.out.println("url de meet " + urlMeet);
			System.out.println("NOMBRE DEL PROFESOR " + teacherName);
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
			System.out.println("entrada el session start" + actualValue);
			actualValue.forEach((action) -> {
				System.out.println("valor actual en foreach" + action.getName());
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
		System.out.println("Llegada al agente dentro del topic chat privado, " + "idTeacher " + idTeacher + "idStudent "
				+ idStudent);
		System.out.println("objeto recibido del front " + stateForChat);
		if (stateForChat.equals("true")) {
			teacherService.setTeacherChatBusy(idTeacher);
		} else if (stateForChat.equals("false")) {
			teacherService.setTeacherChatAvailable(idTeacher);
		}

		return null;
	}

//	Unir estos dos sockets en uno solo para el chat
	@MessageMapping("/teacher/state/chat/{idTeacher}")
	@SendTo("/topic/teacher/state/chat/{idTeacher}")
	@Transactional
	public Boolean verifyTeacherStateOfChatGet(@DestinationVariable Long idTeacher, String stateForChat) {
		System.out.println(
				"entrada al nuevo socket para verificar ele stado del chattttttttttttttttttttttttttttttttttttttt "
						+ idTeacher);
		System.out.println("valor del servicio de retornar estado del chat del profesor, "
				+ teacherService.getChatStateFromTeacher(idTeacher));
		return teacherService.getChatStateFromTeacher(idTeacher);
	}

	@MessageMapping("/private/chat/message/{idTeacher}/{idStudent}")
	@SendTo("/topic/private/chat/message/{idTeacher}/{idStudent}")
	@Transactional
	public Mensaje enviarMensaje(@DestinationVariable Long idTeacher, @DestinationVariable Long idStudent,
			Mensaje mensaje) {
		System.out.println("Entrada al socket de chat privado " + "idTeacher " + idTeacher + " idStudent " + idStudent);
		mensaje.setFecha(new Date().getTime());
		if (mensaje.getTipo().equals("NUEVO_USUARIO")) {
			mensaje.setTexto("Nuevo usuario ");
			mensaje.setColor(colores[new Random().nextInt(colores.length)]);
		}
		return mensaje;
	}

	// Cuando se inicie sesion como profesor se crea un canal propio del profesor
	// Ha este canal llegara el id del estudiante --- llegara cuando se haga un
	// publis que enviara como parametro el id del estudiante que solicita el chat
	@MessageMapping("/private/chanel/teacher/{idTeacher}")
	@SendTo("/topic/private/chanel/teacher/{idTeacher}")
	@Transactional
	public ResponseEntity<?> privateChanelTeacher(@DestinationVariable Long idTeacher,
			PrivateChannelMesajeEntity requestBody) {
		System.out.println("ENTRADA 1");
		System.out.println("valor del request Body " + requestBody.getOnChat());
		try {
			if (requestBody.getOnChat().equals(false)) {
				System.out.println("ME HE CONECTADO AL CANAL DEL CHAT PRIVADO id Del teacher recibido" + idTeacher);
				System.out.println("OBJETO HE RECIBIDO DEL FRONT Y QUE RETORNARE " + requestBody.getIdStudent() + " "
						+ requestBody.getOnChat());
				return new ResponseEntity<PrivateChannelMesajeEntity>(requestBody, HttpStatus.OK);

			} else {
				System.out.println("HE ENTRADO A LA PARTE DE ENVIAR MENSAJES ...");
				System.out.println("VALOR QUE LLEGO DEL FRONT EN MENSAJE TIPO" + requestBody.getTipo());
				System.out.println("VALOR QUE LLEGO DEL FRONT EN MENSAJE TEXT" + requestBody.getTexto());
				requestBody.setFecha(new Date().getTime());
				if (requestBody.getTipo().equals("NUEVO_USUARIO")) {
					requestBody.setTexto("Nuevo usuario ");
					requestBody.setColor(colores[new Random().nextInt(colores.length)]);
				}
				return new ResponseEntity<PrivateChannelMesajeEntity>(requestBody, HttpStatus.OK);
			}

		} catch (Exception e) {
			System.out.println("ENTRADOOO AL CATCH");
			requestBody.setFecha(new Date().getTime());
			return new ResponseEntity<PrivateChannelMesajeEntity>(requestBody, HttpStatus.OK);
		}
	}

	@MessageMapping("/private/chanel/teacher/{idTeacher}/{idStudent}")
	@SendTo("/topic/private/chanel/teacher/{idTeacher}/{idStudent}")
	@Transactional
	public ResponseEntity<?> privateChanelTeacherStudent(@DestinationVariable Long idTeacher,
			PrivateChannelMesajeEntity requestBody) {
		System.out.println("ENTRADA 1");
		System.out.println("valor del request Body " + requestBody.getOnChat());
		try {
			if (requestBody.getOnChat().equals(false)) {
				System.out.println("ME HE CONECTADO AL CANAL DEL CHAT PRIVADO id Del teacher recibido" + idTeacher);
				System.out.println("OBJETO HE RECIBIDO DEL FRONT Y QUE RETORNARE " + requestBody.getIdStudent() + " "
						+ requestBody.getOnChat());
				return new ResponseEntity<PrivateChannelMesajeEntity>(requestBody, HttpStatus.OK);

			} else {
				System.out.println("HE ENTRADO A LA PARTE DE ENVIAR MENSAJES ...");
				System.out.println("VALOR QUE LLEGO DEL FRONT EN MENSAJE TIPO" + requestBody.getTipo());
				System.out.println("VALOR QUE LLEGO DEL FRONT EN MENSAJE TEXT" + requestBody.getTexto());
				requestBody.setFecha(new Date().getTime());
				if (requestBody.getTipo().equals("NUEVO_USUARIO")) {
					requestBody.setTexto("Nuevo usuario ");
					requestBody.setColor(colores[new Random().nextInt(colores.length)]);
				}
				return new ResponseEntity<PrivateChannelMesajeEntity>(requestBody, HttpStatus.OK);
			}

		} catch (Exception e) {
			System.out.println("ENTRADOOO AL CATCH");
			requestBody.setFecha(new Date().getTime());
			return new ResponseEntity<PrivateChannelMesajeEntity>(requestBody, HttpStatus.OK);
		}
	}

	@MessageMapping("/notification/chanel/teacher/{idTeacher}")
	@SendTo("/topic/notification/chanel/teacher/{idTeacher}")
	@Transactional
	public ResponseEntity<?> notificationChanel(@DestinationVariable Long idTeacher,
			@RequestBody SocketTopicTeacherEntity socketEntity) {
		System.out.println("llegada al queue del canal de notificacion");
		System.out.println("id del profesor " + idTeacher);
		System.out.println("mensaje recibido" + socketEntity.getMensaje());

		if (socketEntity.getMensaje().equals("INITIAL_CONNECTION_NOTIFICATION_CHANEL")) {
			System.out.println("conexion inicial del profesor al canal de notificacion");
			String answer = "conxion inicial al canal de notificaciones";
			return new ResponseEntity<String>(answer, HttpStatus.OK);
		}

		if (socketEntity.getMensaje().equals("ASKING_FOR_MEETING")) {
			System.out.println("ENTRE AL CONDICIONAASKING_FOR_MEETING");
			String answer = "ANSWER_ASKING_FOR_MEETING";
			return new ResponseEntity<String>(answer, HttpStatus.OK);

		}

//		if (socketEntity.getMensaje().equals("TEACHER_ACCEPT")) {
//			System.out.println("ENTRE AL CONDICIONAL TEACHER_ACCEPT 1");
//			String answer = "ANSWER_TEACHER_ACCEPT";
//			return new ResponseEntity<String> (answer, HttpStatus.OK);
//		
//		}
//
		if (socketEntity.getMensaje().equals("TEACHER_ACCEPT")) {
			SocketTopicTeacherEntity answer = new SocketTopicTeacherEntity();
			System.out.println("ENTRE AL CONDICIONAL TEACHER_ACCEPT");
			System.out.println("valor del id del estudiante " + socketEntity.getIdStudent());
			System.out.println("valor del id del profesor " + socketEntity.getIdTeacher());
			System.out.println("valor de la url" + socketEntity.getUrlMeetOnQueue());
			answer.setMensaje("ANSWER_TEACHER_ACCEPT");
			answer.setIdTeacher(socketEntity.getIdTeacher());
			answer.setIdStudent(socketEntity.getIdStudent());
			answer.setUrlMeetOnQueue(socketEntity.getUrlMeetOnQueue());
			return new ResponseEntity<SocketTopicTeacherEntity>(answer, HttpStatus.OK);
		}

		if (socketEntity.getMensaje().equals("TEACHER_DENY")) {
			System.out.println("ENTRE AL CONDICIONA TEACHER_DENY");
			String answer = "ANSWER_TEACHER_DENY";
			return new ResponseEntity<String>(answer, HttpStatus.OK);

		}

		if (socketEntity.getMensaje().equals("ESTUDENT_DENY")) {
			System.out.println("ENTRE AL CONDICIONA ESTUDENT_DENY");
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

		System.out.println("evento recibido en timer " + timeObject.getMessage());

		if (timeObject.getMessage().equals("INITAL_TIMER_CONNECTION_STUDENT")) {
			System.out.println("he entrado al evento de conexion inicial retorno un ok");
			timeObject.setMessage("ANSWER_INITAL_TIMER_CONNECTION_STUDENT");
			return new ResponseEntity<TimeEntity>(timeObject, HttpStatus.OK);
		}
		
		if (timeObject.getMessage().equals("INITAL_TIMER_CONNECTION_TEACHER")) {
			System.out.println("he entrado al evento de conexion inicial retorno un ok");
			timeObject.setMessage("ANSWER_INITAL_TIMER_CONNECTION_TEACHER");
			return new ResponseEntity<TimeEntity>(timeObject, HttpStatus.OK);
		}
		
		if (timeObject.getMessage().equals("TIMER_START")) {
			System.out.println("he entrado al evento de iniciar el cronometro");
			timeObject.setMessage("ANSWER_TIMER_START");
			return new ResponseEntity<TimeEntity>(timeObject, HttpStatus.OK);
		}
		
		if (timeObject.getMessage().equals("TIMER_STOP")) {
			System.out.println("he entrado al evento de parar el cronometro");
			// aqui el front ejecuta los metodos de sumar y quitar minutos al profesor y al estudiante
			// tengo que consultar a ambos estudiante y profesor para extraer el tiempo que actulamente ellos tienen para retornarlo en modal sin embargo ya la logica fue ejecutada
			timeObject.setMessage("ANSWER_TIMER_STOP");
			return new ResponseEntity<TimeEntity>(timeObject, HttpStatus.OK);
		}

		return null;
	}

}
