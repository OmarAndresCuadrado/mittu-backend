package com.backend.api.auth;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()

				// Endpoints students
				.antMatchers(HttpMethod.POST, "/api/student/disable/{userName}").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/api/student/enable/{userName}").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/api/student/get/courses/{idStudent}").hasAnyRole("ADMIN", "STUDENT")
				.antMatchers(HttpMethod.GET, "/api/student").hasAnyRole("TEACHER", "ADMIN")
				.antMatchers(HttpMethod.GET, "/api/student/get/time/{idStudent}")
				.hasAnyRole("STUDENT", "TEACHER", "ADMIN").antMatchers(HttpMethod.POST, "/api/student/teacher/get/time")
				.hasAnyRole("STUDENT", "TEACHER", "ADMIN")
				.antMatchers(HttpMethod.PUT, "/api/student/set/time/{idStudent}")
				.hasAnyRole("STUDENT", "TEACHER", "ADMIN").antMatchers(HttpMethod.GET, "/api/student/{id}")
				.hasAnyRole("STUDENT", "TEACHER", "ADMIN").antMatchers(HttpMethod.PUT, "/api/student/{id}")
				.hasAnyRole("STUDENT", "ADMIN").antMatchers(HttpMethod.DELETE, "/api/student/{id}").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET, "/api/student/transfer/{idStudent}").hasRole("STUDENT")
				.antMatchers(HttpMethod.GET, "/api/student/transfer/{money}/{studentId}").hasRole("STUDENT")
				.antMatchers(HttpMethod.POST, "/api/student/upload/image").hasRole("STUDENT")
				.antMatchers(HttpMethod.GET, "/api/student/teacher/set/grupalCourse/{grupalCourseCost}/{teacherId}/{studentId}").hasRole("STUDENT")
				.antMatchers(HttpMethod.GET, "/api/student/get/class-details/{studentId}").hasRole("STUDENT")
				
				
				// Endpoints feedback
				.antMatchers(HttpMethod.POST, "/api/feed-back").hasRole("STUDENT")
				.antMatchers(HttpMethod.GET, "/api/feed-back").hasRole("ADMIN")

				// Endpoints teachers
				.antMatchers(HttpMethod.GET, "/api/teacher/disable/{idUser}").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET, "/api/teacher/enable/{idUser}").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/api/teacher/upload/image").hasRole("TEACHER")
				.antMatchers(HttpMethod.GET, "/api/teacher").hasAnyRole("TEACHER", "ADMIN", "STUDENT")
				.antMatchers(HttpMethod.PUT, "/api/teacher/{id}").hasRole("TEACHER")
				.antMatchers(HttpMethod.GET, "/api/teacher/get/time/{idTeacher}")
				.hasAnyRole("STUDENT", "TEACHER", "ADMIN").antMatchers(HttpMethod.PUT, "/api/teacher/{id}")
				.hasAnyRole("TEACHER", "ADMIN").antMatchers(HttpMethod.DELETE, "/api/teacher/{id}").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET, "/api/teacher/get/califaction/{idTeacher}")
				.hasAnyRole("TEACHER", "STUDENT")
				.antMatchers(HttpMethod.GET, "/api/teacher/get/califaction-students/{idTeacher}")
				.hasAnyRole("TEACHER", "STUDENT")
				.antMatchers(HttpMethod.POST, "/api/teacher/post/califaction/{idTeacher}").hasRole("TEACHER")
				.antMatchers(HttpMethod.POST, "/api/teacher/post/profile").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET, "/api/teacher/get/class-details/{teachertId}").hasRole("TEACHER")
				

				// Endpoints subjects
				.antMatchers(HttpMethod.POST, "/api/subject").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT, "/api/subject/{id}").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/api/subject/{id}").hasRole("ADMIN")

				// Endpoints courses
				.antMatchers(HttpMethod.POST, "/api/course").hasAnyRole("TEACHER", "ADMIN")
				.antMatchers(HttpMethod.POST, "/api/course/upload/image").hasRole("TEACHER")
				.antMatchers(HttpMethod.POST, "/api/course/creation").hasAnyRole("TEACHER", "ADMIN")
				.antMatchers(HttpMethod.PUT, "/api/course/{id}").hasAnyRole("TEACHER", "ADMIN")
				.antMatchers(HttpMethod.POST, "/api/course/enable/{id}").hasAnyRole("TEACHER", "ADMIN")
				.antMatchers(HttpMethod.POST, "/api/course/disable/{id}").hasAnyRole("TEACHER", "ADMIN")
				.antMatchers(HttpMethod.POST, "/api/course/teacher/course/{idTeacher}").hasRole("TEACHER")

				// Endpoints transfers
				.antMatchers(HttpMethod.POST, "/api/transfer").hasAnyRole("STUDENT", "ADMIN")
				.antMatchers(HttpMethod.GET, "/api/transfer/{id}").hasAnyRole("STUDENT", "ADMIN")
				.antMatchers(HttpMethod.GET, "/api/transfer").hasAnyRole("STUDENT", "ADMIN")
				.antMatchers(HttpMethod.GET, "/api/transfer/list/{idStudent}").hasRole("STUDENT")
				.antMatchers(HttpMethod.POST, "/api/transfer/create").hasRole("STUDENT")
				.antMatchers(HttpMethod.GET, "/api/transfer/admin").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET, "/api/transfer/banner/upload/image").hasRole("ADMIN")


				// Endpoints retirments
				.antMatchers(HttpMethod.POST, "/api/retirement").hasAnyRole("TEACHER", "ADMIN")
				.antMatchers(HttpMethod.GET, "/api/retirement/{id}").hasAnyRole("TEACHER", "ADMIN")
				.antMatchers(HttpMethod.GET, "/api/retirement").hasAnyRole("TEACHER", "ADMIN")
				.antMatchers(HttpMethod.POST, "/api/retirement/excel").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/api/retirement/update/state/{retirementId}").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/api/retirement/new/send/email").hasRole("TEACHER")
				.antMatchers(HttpMethod.POST, "/api/retirement/state/send/email").hasRole("ADMIN")
					

				// Endpoints grupalCourses
				.antMatchers(HttpMethod.GET, "/api/grupal/course").hasAnyRole("TEACHER", "ADMIN")
				.antMatchers(HttpMethod.GET, "/api/grupal/course/{id}").hasRole("TEACHER")
				.antMatchers(HttpMethod.GET, "/api/grupal/course/courses/{idStudent}")
				.hasAnyRole("TEACHER", "ADMIN", "STUDENT").antMatchers(HttpMethod.PUT, "/api/grupal/course/{id}")
				.hasRole("TEACHER").antMatchers(HttpMethod.POST, "/api/grupal/course").hasRole("TEACHER")
				.antMatchers(HttpMethod.GET, "/api/grupal/course/teacher/{idTeacher}").hasRole("TEACHER")
				.antMatchers(HttpMethod.GET, "/api/grupal/course/create/inscription").hasRole("STUDENT")
				.antMatchers(HttpMethod.GET, "/api/grupal/course/upload/image").hasRole("TEACHER")

				// Endpoints payments

				.antMatchers(HttpMethod.GET, "/api/payment").permitAll()
				.antMatchers(HttpMethod.GET, "/api/course/find/course/name/{courseName}").permitAll()
				.antMatchers(HttpMethod.GET, "/api/grupal/course/search/{grupalCourseName}").permitAll()
				.antMatchers(HttpMethod.GET, "/api/subject").permitAll()
				.antMatchers(HttpMethod.GET, "/api/subject/{id}").permitAll()
				.antMatchers(HttpMethod.POST, "/api/teacher").permitAll().antMatchers(HttpMethod.POST, "/api/student")
				.permitAll().antMatchers(HttpMethod.GET, "/api/course").permitAll()
				.antMatchers(HttpMethod.GET, "/api/course/{id}").permitAll()
				.antMatchers(HttpMethod.GET, "/api/course/upload/image/{pictureCourseName:.+}").permitAll()
				.antMatchers(HttpMethod.GET, "/api/grupal/course/image/{pictureGrupalCourseName:.+}").permitAll()
				.antMatchers(HttpMethod.GET, "/api/teacher/upload/image/{pictureTeacherName:.+}").permitAll()
				.antMatchers(HttpMethod.GET, "/api/student/upload/image/{pictureStudentName:.+}").permitAll()
				.antMatchers(HttpMethod.GET, "/api/transfer/banner/upload/image/{pictureBannerName:.+}").permitAll()
				.antMatchers(HttpMethod.GET, "/api/transfer/banner/upload/image/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/transfer/banner/{id}").permitAll()
				.antMatchers(HttpMethod.GET, "/api/student/verification/{userName}").permitAll()
				.antMatchers(HttpMethod.GET, "/api/teacher/get/information").permitAll()
				.antMatchers(HttpMethod.GET, "/api/retirement/excel").permitAll()
				.antMatchers(HttpMethod.GET, "/api/student/getMoney/{studentId}").permitAll()
				.antMatchers(HttpMethod.GET, "/api/teacher/{id}").permitAll()
				.antMatchers("/**").permitAll()

				.anyRequest().authenticated().and().cors().configurationSource(corsConfigurationSource());
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(Arrays.asList("*"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		config.setAllowCredentials(true);
		config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(
				new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}

}
