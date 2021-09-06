package com.backend.api.controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.api.entity.BannerEntity;
import com.backend.api.entity.TransferEntity;
import com.backend.api.serviceInterface.IBannerServiceInterface;
import com.backend.api.serviceInterface.ITransferServiceInterface;
import com.backend.api.serviceInterface.IUsuarioInterface;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")
public class TransferController {

	private final Logger log = LoggerFactory.getLogger(StudentController.class);

	@Autowired
	private ITransferServiceInterface transferService;

	@Autowired
	private IUsuarioInterface userService;

	@Autowired
	private IBannerServiceInterface bannerService;

	@GetMapping("/transfer")
	public List<TransferEntity> listOfTransfers() {
		return transferService.listOfTransfers();
	}

	@GetMapping("/transfer/{id}")
	public TransferEntity findTransferById(@PathVariable Long id) {
		return transferService.findTransferById(id);
	}

	@PostMapping("/transfer")
	public TransferEntity createTrasnfer(@RequestBody TransferEntity transfer) {
		log.info("Transaccion realizada a las horas: " + executionTime());
		return transferService.saveTrasnfer(transfer);
	}

	@PostMapping("/transfer/create")
	public void createTrasnferToStudent(@RequestBody TransferEntity transfer) {
		log.info("Transaccion realizada a las horas: " + executionTime());
		System.out.println(transfer.getCost());
		System.out.println(transfer.getFechaDeCreacion());
		System.out.println(transfer.getName());
		System.out.println(transfer.getIdStudent());
		System.out.println(transfer.getTransferCode());
		transferService.createTransfer(transfer.getCost(), transfer.getFechaDeCreacion(), transfer.getName(),
				transfer.getIdStudent(), transfer.getTransferCode());
	}

	public String executionTime() {
		TimeZone timeZone = TimeZone.getTimeZone("GMT");
		SimpleDateFormat date_format = new SimpleDateFormat("dd-MM-yyyy EEEE hh:mm:ss a");
		Date date = new Date();
		String current_date_time = date_format.format(date);
		timeZone = TimeZone.getTimeZone("Asia/Kolkata");
		date_format.setTimeZone(timeZone);
		return current_date_time;
	}

	@GetMapping("/transfer/list/{idStudent}")
	public List<TransferEntity> listOfTransfersFromStudent(@PathVariable Long idStudent) {
		return transferService.listOfTransferFromStudent(idStudent);
	}

	@GetMapping("/transfer/admin")
	public Double plataformMoney() {
		return userService.getAdministraitorMoney();
	}

	@GetMapping("/transfer/banner/{id}")
	public BannerEntity getBannerObject(@PathVariable Long id) {
		BannerEntity banerFound = bannerService.findBanner(id);
		return banerFound;
	}

	@PostMapping("/transfer/banner/upload/image")
	public ResponseEntity<?> uploadStudentImage(@RequestParam("bannerImage") MultipartFile imageBanner,
			@RequestParam("id") Long id, @RequestParam("color") String color) {
		Map<String, Object> response = new HashMap<>();
		BannerEntity bodyToReturn = new BannerEntity();

		BannerEntity bannerFound = bannerService.findBanner(id);

		if (!imageBanner.isEmpty()) {
			String pictureName = UUID.randomUUID().toString() + "_"
					+ imageBanner.getOriginalFilename().replace(" ", "");
			Path picturePath = Paths.get("banner-image").resolve(pictureName).toAbsolutePath();

			try {
				Files.copy(imageBanner.getInputStream(), picturePath);
			} catch (IOException e) {
				response.put("errorMsg", "Error al subir la imagen del banner");
				log.info(executionTime() + "-Error al subir la imagen");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			String lastBannerPicture = bannerFound.getUrlBanner();

			if (lastBannerPicture != null && lastBannerPicture.length() > 0) {

				Path pictureLastPath = Paths.get("banner-image").resolve(lastBannerPicture).toAbsolutePath();
				File lastPicture = pictureLastPath.toFile();
				if (lastPicture.exists() && lastPicture.canRead()) {
					lastPicture.delete();
				}
			}

			bannerFound.setUrlBanner(pictureName);
			bannerFound.setColorBanner(color);
			bannerService.saveBanner(bannerFound);
			bodyToReturn = bannerFound;
		}

		return new ResponseEntity<BannerEntity>(bodyToReturn, HttpStatus.OK);
	}

	@GetMapping("/transfer/banner/upload/image/{pictureBannerName:.+}")
	public ResponseEntity<Resource> showPictureCourse(@PathVariable String pictureBannerName) {

		if (pictureBannerName.isEmpty() || pictureBannerName.length() <= 0 || pictureBannerName == "") {
			pictureBannerName = "defaultBanner.png";
		}

		Path picturePath = Paths.get("banner-image").resolve(pictureBannerName).toAbsolutePath();
		Resource resource = null;

		try {

			resource = new UrlResource(picturePath.toUri());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		if (!resource.exists() && !resource.isReadable()) {
			throw new RuntimeException("No se pudo cargar la imagen: " + pictureBannerName);
		}

		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

		return new ResponseEntity<Resource>(resource, cabecera, HttpStatus.OK);
	}
}