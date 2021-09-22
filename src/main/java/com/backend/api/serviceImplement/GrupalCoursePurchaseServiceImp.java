/**
 * 
 */
package com.backend.api.serviceImplement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.backend.api.dao.IGrupalCoursePurchaseDao;
import com.backend.api.entity.GrupalCoursePurchaseEntity;
import com.backend.api.serviceInterface.IGrupalCoursePurchaseInterface;

/**
 * @author MrRobot
 *
 */
@Service
public class GrupalCoursePurchaseServiceImp implements IGrupalCoursePurchaseInterface {

	@Autowired
	private IGrupalCoursePurchaseDao grupalCoursePurchaseDao;

	@Override
	public List<GrupalCoursePurchaseEntity> grupalCoursesPurchases() {
		return grupalCoursePurchaseDao.findAll();
	}

	@Override
	public GrupalCoursePurchaseEntity findGrupalCoursePurchaseById(Long id) {
		return grupalCoursePurchaseDao.findById(id).orElse(null);
	}

	@Override
	public GrupalCoursePurchaseEntity saveGrupalCoursePurchase(GrupalCoursePurchaseEntity grupalCoursePurchaseEntity) {
		return grupalCoursePurchaseDao.save(grupalCoursePurchaseEntity);
	}

	@Override
	public List<GrupalCoursePurchaseEntity> getListOfGrupalCoursesPurchasesFilterByDate(String initalDate, String endDate) {
		return grupalCoursePurchaseDao.getListOfGrupalCoursesPurchasesFilterByDate(initalDate, endDate);
	}

}
