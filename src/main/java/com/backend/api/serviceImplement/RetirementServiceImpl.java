package com.backend.api.serviceImplement;

import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.backend.api.dao.IRetirementDao;
import com.backend.api.entity.RetirementEntity;
import com.backend.api.entity.TeacherEntity;
import com.backend.api.serviceInterface.IRetirementServiceInterface;
import com.backend.api.serviceInterface.ITeacherServiceInterface;

@Service
public class RetirementServiceImpl implements IRetirementServiceInterface {

	@Autowired
	private IRetirementDao retirementDao;

	@Autowired
	private ITeacherServiceInterface teacherService;

	@Override
	@Transactional(readOnly = true)
	public List<RetirementEntity> listOfRetirments() {
		return retirementDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public RetirementEntity findRetirementById(Long id) {
		return retirementDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public RetirementEntity saveRetirement(RetirementEntity retirement) {
		return retirementDao.save(retirement);
	}

	@Override
	public List<RetirementEntity> listOfRetirementsFilterByDate(String initialDate, String finalDate) {
		return retirementDao.listOfRetirementsFilterByDate(initialDate, finalDate);
	}

	@Override
	@Transactional
	public void updateRetirementOwner(String teacher_identifier) {
		retirementDao.updateRetirementOwner(teacher_identifier);
	}

	@Override
	@Transactional
	public void updateRetirementState(String retirementId) {
		retirementDao.updateRetirementState(retirementId);
	}

	@Override
	@Transactional
	public void sentEmailRetirement(String idReference, Long idTeacher, MultipartFile accountInformation) {
		TeacherEntity teacherFound = teacherService.findTeacherById(idTeacher);
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		String getFileExtention = FilenameUtils.getExtension(accountInformation.getOriginalFilename());
		String to = "mittuoficial@gmail.com";
		String subject = "Solicitud de retiro de fondos del docente " +  teacherFound.getName().concat(" " + teacherFound.getLastName()) + ", Id de referencia: " + idReference ;
		String emailBody = "Detalles para realizar la transferencia (Adjunto)";
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
			helper.addAttachment("DetallesDeCuenta.".concat(getFileExtention), accountInformation);
			mailSender.send(message);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public void sentEmailForChangeStateRetirement(String idReference, Long idTeacher, MultipartFile paymentSupport) {
		TeacherEntity teacherFound = teacherService.findTeacherById(idTeacher);
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		String getFileExtention = FilenameUtils.getExtension(paymentSupport.getOriginalFilename());
		String to = teacherFound.getEmail();
		String subject = "Se ha realizado con exito el pago de la transacción " +  idReference;
		String emailBody = "Se ha realizado exitosamente la transferencia de fondos a tu cuenta bancaria.";
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
			helper.addAttachment("SoporteDeTransferencia.".concat(getFileExtention), paymentSupport);
			mailSender.send(message);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
