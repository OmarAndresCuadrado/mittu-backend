package com.backend.api.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.backend.api.entity.Usuario;
import com.backend.api.serviceInterface.IUsuarioInterface;

@Component
public class InfoAdicionalToken implements TokenEnhancer {

	@Autowired
	private IUsuarioInterface usuarioService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

		Usuario usuario = usuarioService.findByUsername(authentication.getName());
		Map<String, Object> info = new HashMap<>();
	

		info.put("nombre", usuario.getName());
		info.put("apellido", usuario.getLastName());
		info.put("email", usuario.getEmail());
		
		
		Long idUser = usuario.getId();
		System.out.println("id del usuario " + idUser);
		System.out.println("usuario id"+ idUser );
		Long idTeacher = usuarioService.findTeacherByUserId(idUser);
		Long idStudent = usuarioService.findStudentByUserId(idUser);
		Long idAdmin = usuarioService.findAdminId();
		System.out.println("student id"+ idStudent );
		System.out.println("teacher id"+ idTeacher );
		System.out.println("admin id"+ idAdmin );

	
		info.put("teacherId", idTeacher);
		info.put("studentId", idStudent);
		info.put("userId", idUser);
		info.put("adminId", idAdmin);
		

		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
		
		System.out.println("token final " + accessToken);

		return accessToken;
	}

}
