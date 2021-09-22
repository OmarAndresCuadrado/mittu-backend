/**
 * 
 */
package com.backend.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.api.entity.GrupalCoursePurchaseEntity;
import com.backend.api.serviceInterface.IGrupalCoursePurchaseInterface;

/**
 * @author MrRobot
 *
 */
@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class GrupalCoursePurchaseController {

	@Autowired
	private IGrupalCoursePurchaseInterface grupalCoursePurchaseService;

	@GetMapping("/grupal/course/purchase")
	public List<GrupalCoursePurchaseEntity> listOfGrupalCoursePurchase() {
		return grupalCoursePurchaseService.grupalCoursesPurchases();
	}

	@GetMapping("/grupal/course/purchase/{id}")
	public GrupalCoursePurchaseEntity findGrupalCoursePurchaseById(@PathVariable Long id) {
		return grupalCoursePurchaseService.findGrupalCoursePurchaseById(id);
	}

	@PostMapping("/grupal/course/purchase")
	public GrupalCoursePurchaseEntity saveGrupalCoursePurchase(
			@RequestBody GrupalCoursePurchaseEntity grupalCoursePurchaseEntity) {
		return grupalCoursePurchaseService.saveGrupalCoursePurchase(grupalCoursePurchaseEntity);
	}
}
