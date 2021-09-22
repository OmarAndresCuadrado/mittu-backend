package com.backend.api.serviceImplement;

import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.api.dao.ITeacherDao;
import com.backend.api.entity.TeacherEntity;
import com.backend.api.serviceInterface.ITeacherServiceInterface;

@Service
public class TeacherServiceImpl implements ITeacherServiceInterface {

	@Autowired
	private ITeacherDao teacherDao;

	@Override
	@Transactional(readOnly = true)
	public List<TeacherEntity> listOfTeachers() {
		return teacherDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public TeacherEntity findTeacherById(Long id) {
		return teacherDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public TeacherEntity saveTeacher(TeacherEntity teacherEntity) {
		return teacherDao.save(teacherEntity);
	}

	@Override
	@Transactional
	public void deleteTeacher(Long id) {
		teacherDao.deleteById(id);

	}

	@Override
	@Transactional
	public void disableTeacher(Long idUser) {
		teacherDao.disableTeacher(idUser);

	}

	@Override
	@Transactional
	public void enableTeacher(Long idUser) {
		teacherDao.enableTeacher(idUser);
	}

	@Override
	public Boolean getStateOfTeacher(Long idTeacher) {
		return teacherDao.getStateOfTeacher(idTeacher);
	}

	@Override
	public void setTeacherBusy(Long idTeacher) {
		teacherDao.setTeacherBusy(idTeacher);

	}

	@Override
	public void setTeacherAvailable(Long idTeacher) {
		teacherDao.setTeacherAvailable(idTeacher);

	}

	@Override
	public void setTeacherChatBusy(Long idTeacher) {
		teacherDao.setTeacherChatBusy(idTeacher);
	}

	@Override
	public void setTeacherChatAvailable(Long idTeacher) {
		teacherDao.setTeacherChatAvailable(idTeacher);

	}

	@Override
	public Boolean getChatStateFromTeacher(Long idTeacher) {
		return teacherDao.getChatStateFromTeacher(idTeacher);
	}

	@Transactional
	@Override
	public void sentEmailToTeacher(TeacherEntity teacher, String cleanPassword) {
		String to = teacher.getEmail();
		String subject = "Bienvenido " + teacher.getName() + " " + teacher.getLastName() + " a Mittu\n";
		String emailBody = "" + "Bienvenido a Mittu " + teacher.getName() + " " + teacher.getLastName() + " "
				+ "estás a un paso más de ser parte de nuestro grandioso equipo de Docentes, \n tus datos de ingreo son: \n usuario(Correo): "
				+ teacher.getEmail() + " " + "\n y contraseña: " + cleanPassword + " "
				+ "\n Recuerda que tendras que esperar a que el administrador active tu cuenta.";

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);

		mailSender.setUsername("mittuoficial@gmail.com");
		mailSender.setPassword("zatjbjbavvsejthj");

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper;

		try {
			helper = new MimeMessageHelper(message, true);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(emailBody);
			mailSender.send(message);

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	@Transactional(readOnly = true)
	@Override
	public Integer getTeacherTime(Long idTeacher) {
		Integer teacherTime = teacherDao.getTeacherTime(idTeacher);
		return teacherTime;
	}

	@Transactional
	@Override
	public void setTeacherTime(Integer time, Long teachertId) {
		teacherDao.setTeacherTime(time, teachertId);
	}

	@Transactional
	@Override
	public void setTeacherMoney(Double money, Long teacherId) {
		teacherDao.setTeacherMoney(money, teacherId);

	}

	@Override
	public Double getTeacherCalification(Long teacherId) {
		return teacherDao.getTeacherCalification(teacherId);
	}

	@Override
	public Double getTeacherCalificationStudents(Long teacherId) {
		return teacherDao.getTeacherCalificationStudents(teacherId);
	}

	@Override
	@Transactional
	public void updateTeacherCalification(Double califaction, Integer studentCount, Long teacherId) {
		teacherDao.updateTeacherCalification(califaction, studentCount, teacherId);
	}

	@Override
	@Transactional
	public void updateProfileTeacher(String profile, Long teacherId) {
		teacherDao.updateProfileTeacher(profile, teacherId);		
	}

	@Override
	public List<TeacherEntity> listOfTeacherFilterByDate(String initialDate, String finalDate) {
		return teacherDao.listOfTeacherFilterByDate(initialDate, finalDate);
	}

}
