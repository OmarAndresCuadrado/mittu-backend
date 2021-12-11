/**
 * 
 */
package com.backend.api.controller;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.compress.compressors.FileNameUtil;
import org.apache.commons.io.FilenameUtils;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.api.entity.TeacherEntity;

/**
 * @author MrRobot
 *
 */
@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class TestController {
	
	@PostMapping("/test")
	public void sentImage( @RequestParam("accountInformation") MultipartFile accountInformation) {
		System.out.println("valor del archivo " + accountInformation);
		String to = "omarcuadradodev@gmail.com";
		String subject = "Solicitud de retiro de fondos del docente ";
		String emailBody = "Detalles para realizar la transferencia (Adjunto)";
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
		System.out.println("nombre del archivo " + accountInformation.getOriginalFilename());
		String getFileExtention = FilenameUtils.getExtension(accountInformation.getOriginalFilename());
		
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

}
