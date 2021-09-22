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

import com.backend.api.dao.IStudentDao;
import com.backend.api.entity.StudentEntity;
import com.backend.api.serviceInterface.IStudentServiceInterface;

@Service
public class StudentServiceImpl implements IStudentServiceInterface {

	@Autowired
	private IStudentDao studentDao;

	@Override
	@Transactional(readOnly = true)
	public List<StudentEntity> findAllStudents() {
		return studentDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public StudentEntity findStudent(Long id) {
		return studentDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public StudentEntity saveStudent(StudentEntity student) {
		return studentDao.save(student);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		studentDao.deleteById(id);

	}

	@Transactional
	public void sentEmailToStudent(StudentEntity student, String cleanPassword) {
		String to = student.getEmail();
		String subject = "Bienvenido " + student.getName() + " " + student.getLastName() + " a Mittu\n";
		String emailBody = "" + "Bienvenido " + student.getName() + " " + student.getLastName() + " "
				+ "gracias por registrarte en Mittu, \n tus datos de ingreo son: \n usuario(Correo): "
				+ student.getEmail() + " " + "\n y contraseña: " + cleanPassword + " "
				+ "\n Recuerda no compartir tus datos de ingreso con ninguna persona que no sea de confinza";

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

	@Override
	public String verifyStudentByUsername(String username) {
		return studentDao.findStudentByUsername(username);
	}

	@Override
	@Transactional
	public void disableStudent(Long idUser) {
		studentDao.disableStudent(idUser);
		
	}

	@Override
	@Transactional
	public void enableStudent(Long idUser) {
		studentDao.enableStudent(idUser);
		
	}

	@Override
	@Transactional(readOnly = true)
	public Integer getStudentTime(Long idStudent) {
		return studentDao.getStudentTime(idStudent);
	}

	@Override
	@Transactional
	public void setStudentTime(Integer time, Long StudentId) {
		studentDao.setStudentTime(time, StudentId);		
	}

	@Override
	@Transactional
	public void setStudentMoney(Double money, Long studentId) {
		studentDao.setStudentMoney(money, studentId);
		
	}

	@Override
	public List<StudentEntity> listAllStudentsFilterByDate(String initialDate, String finalDate) {
		return studentDao.listAllStudentsFilterByDate(initialDate, finalDate);
	}

	@Override
	public Long moneyFromStudent(Long studentId) {
		return studentDao.moneyFromStudent(studentId);
	}

}
