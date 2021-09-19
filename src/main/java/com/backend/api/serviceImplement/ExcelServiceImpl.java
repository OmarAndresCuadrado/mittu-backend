package com.backend.api.serviceImplement;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.backend.api.entity.DetailsEntity;
import com.backend.api.entity.RetirementEntity;
import com.backend.api.entity.StudentEntity;
import com.backend.api.entity.TeacherEntity;
import com.backend.api.entity.TransferEntity;
import com.backend.api.serviceInterface.IExcelInterface;

@Service
public class ExcelServiceImpl implements IExcelInterface {

	private XSSFWorkbook workbook;
	private CellStyle cellStyle;
	private CreationHelper createHelper;
	private XSSFSheet sheetGeneral;
	private XSSFSheet sheetRetiros;
	private XSSFSheet sheetTransferencias;
	private XSSFSheet sheetAlumnos;
	private XSSFSheet sheetProfesores;
	private ByteArrayOutputStream outPutStream;

	@Override
	public ByteArrayInputStream exportExcel(List<RetirementEntity> retirementList, List<TransferEntity> transferList,
			List<StudentEntity> studentList, List<TeacherEntity> teacherList, List<DetailsEntity> detailsList)
			throws IOException {

		workbook = new XSSFWorkbook();
		outPutStream = new ByteArrayOutputStream();
		sheetGeneral = workbook.createSheet("General");
		sheetRetiros = workbook.createSheet("Retiros");
		sheetTransferencias = workbook.createSheet("Recargas");
		sheetAlumnos = workbook.createSheet("Alumnos");
		sheetProfesores = workbook.createSheet("Profesores");
		cellStyle = workbook.createCellStyle();
		createHelper = workbook.getCreationHelper();
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/mm/dd"));

		// ***ROWS PAGES AND TITLES***//

		// General page

		Row rowGeneralHeader = sheetGeneral.createRow(0);

		Cell cellGeneralHeader = rowGeneralHeader.createCell(0);
		cellGeneralHeader.setCellValue("Identificador estudiante");

		cellGeneralHeader = rowGeneralHeader.createCell(1);
		cellGeneralHeader.setCellValue("Identificador profesor");

		cellGeneralHeader = rowGeneralHeader.createCell(2);
		cellGeneralHeader.setCellValue("Tiempo de la tutoria");

		cellGeneralHeader = rowGeneralHeader.createCell(3);
		cellGeneralHeader.setCellValue("Pago realizado por el estudiante");

		cellGeneralHeader = rowGeneralHeader.createCell(4);
		cellGeneralHeader.setCellValue("Pago realizado a la plataforma");

		cellGeneralHeader = rowGeneralHeader.createCell(5);
		cellGeneralHeader.setCellValue("Pago realizado al docente");

		cellGeneralHeader = rowGeneralHeader.createCell(6);
		cellGeneralHeader.setCellValue("Nombre del estudiante");

		cellGeneralHeader = rowGeneralHeader.createCell(7);
		cellGeneralHeader.setCellValue("Nombre del profesor");

		cellGeneralHeader = rowGeneralHeader.createCell(8);
		cellGeneralHeader.setCellValue("Fecha de creacion");
		cellGeneralHeader.setCellStyle(cellStyle);

		// Retiros page

		Row rowRetirosHeader = sheetRetiros.createRow(0);

		Cell cellRetirosHeader = rowRetirosHeader.createCell(0);
		cellRetirosHeader.setCellValue("Identificador del retiro");

		cellRetirosHeader = rowRetirosHeader.createCell(1);
		cellRetirosHeader.setCellValue("Costo");

		cellRetirosHeader = rowRetirosHeader.createCell(2);
		cellRetirosHeader.setCellValue("Nombre");

		cellRetirosHeader = rowRetirosHeader.createCell(3);
		cellRetirosHeader.setCellValue("Estado");

		cellRetirosHeader = rowRetirosHeader.createCell(4);
		cellRetirosHeader.setCellValue("Fecha de creacion");
		cellRetirosHeader.setCellStyle(cellStyle);

		// Transferencias page

		Row rowTransferenciasHeader = sheetTransferencias.createRow(0);

		Cell cellTransferenciasHeader = rowTransferenciasHeader.createCell(0);
		cellTransferenciasHeader.setCellValue("Identificador de transferencia");

		cellTransferenciasHeader = rowTransferenciasHeader.createCell(1);
		cellTransferenciasHeader.setCellValue("Costo");

		cellTransferenciasHeader = rowTransferenciasHeader.createCell(2);
		cellTransferenciasHeader.setCellValue("Nombre");

		cellTransferenciasHeader = rowTransferenciasHeader.createCell(3);
		cellTransferenciasHeader.setCellValue("Codigo de transferencia");

		cellTransferenciasHeader = rowTransferenciasHeader.createCell(4);
		cellTransferenciasHeader.setCellValue("Fecha de creacion");
		cellTransferenciasHeader.setCellStyle(cellStyle);

		// Alumnos page

		Row rowAlumnosHeader = sheetAlumnos.createRow(0);

		Cell cellAlumnosHeader = rowAlumnosHeader.createCell(0);
		cellAlumnosHeader.setCellValue("Identificador del estudiante");

		cellAlumnosHeader = rowAlumnosHeader.createCell(1);
		cellAlumnosHeader.setCellValue("Nombre");

		cellAlumnosHeader = rowAlumnosHeader.createCell(2);
		cellAlumnosHeader.setCellValue("Apellido");

		cellAlumnosHeader = rowAlumnosHeader.createCell(3);
		cellAlumnosHeader.setCellValue("Telefono");

		cellAlumnosHeader = rowAlumnosHeader.createCell(4);
		cellAlumnosHeader.setCellValue("Correo");

		cellAlumnosHeader = rowAlumnosHeader.createCell(5);
		cellAlumnosHeader.setCellValue("Fecha de creacion");
		cellAlumnosHeader.setCellStyle(cellStyle);

		// Profesores page

		Row rowProfesoresHeader = sheetProfesores.createRow(0);

		Cell cellProfesoresHeader = rowProfesoresHeader.createCell(0);
		cellProfesoresHeader.setCellValue("Identificador del profesor");

		cellProfesoresHeader = rowProfesoresHeader.createCell(1);
		cellProfesoresHeader.setCellValue("Nombre");

		cellProfesoresHeader = rowProfesoresHeader.createCell(2);
		cellProfesoresHeader.setCellValue("Apellido");

		cellProfesoresHeader = rowProfesoresHeader.createCell(3);
		cellProfesoresHeader.setCellValue("Correo");

		cellProfesoresHeader = rowProfesoresHeader.createCell(4);
		cellProfesoresHeader.setCellValue("Telefono");

		cellProfesoresHeader = rowProfesoresHeader.createCell(5);
		cellProfesoresHeader.setCellValue("Activo en plataforma");

		cellProfesoresHeader = rowProfesoresHeader.createCell(6);
		cellProfesoresHeader.setCellValue("Fecha de creacion");
		cellProfesoresHeader.setCellStyle(cellStyle);

		/* POPULATION DATA TO THE ROWS */

		int rowGeneralCount = 1;
		int rowRetirosCount = 1;
		int rowTransferenciasCount = 1;
		int rowAlumnosCount = 1;
		int rowProfesoresCount = 1;

		// General page

		for (DetailsEntity detail : detailsList) {

			Row rowGeneral = sheetGeneral.createRow(rowGeneralCount++);

			// General page
			Cell cellGeneral = rowGeneral.createCell(0);
			cellGeneral.setCellValue(detail.getIdStudent());
			sheetGeneral.autoSizeColumn(0);

			cellGeneral = rowGeneral.createCell(1);
			cellGeneral.setCellValue(detail.getIdTeacher());
			sheetGeneral.autoSizeColumn(1);

			cellGeneral = rowGeneral.createCell(2);
			cellGeneral.setCellValue(detail.getDuration());
			sheetGeneral.autoSizeColumn(3);

			cellGeneral = rowGeneral.createCell(3);
			cellGeneral.setCellValue(detail.getStudentPayment());
			sheetGeneral.autoSizeColumn(4);

			cellGeneral = rowGeneral.createCell(4);
			cellGeneral.setCellValue(detail.getPlataformEarning());
			sheetGeneral.autoSizeColumn(5);

			cellGeneral = rowGeneral.createCell(5);
			cellGeneral.setCellValue(detail.getTeacherEarning());
			sheetGeneral.autoSizeColumn(6);

			cellGeneral = rowGeneral.createCell(6);
			cellGeneral.setCellValue(detail.getStudentName());
			sheetGeneral.autoSizeColumn(7);

			cellGeneral = rowGeneral.createCell(7);
			cellGeneral.setCellValue(detail.getTeacherName());
			sheetGeneral.autoSizeColumn(8);

			cellGeneral = rowGeneral.createCell(8);
			cellGeneral.setCellValue(detail.getFechaDeCreacion());
			cellGeneral.setCellStyle(cellStyle);
			sheetGeneral.autoSizeColumn(9);

		}

		// Retiros page

		for (RetirementEntity retirementEntity : retirementList) {

			Row rowRetiros = sheetRetiros.createRow(rowRetirosCount++);

			Cell cellRetiros = rowRetiros.createCell(0);
			cellRetiros.setCellValue(retirementEntity.getId());
			sheetRetiros.autoSizeColumn(0);

			cellRetiros = rowRetiros.createCell(1);
			cellRetiros.setCellValue(retirementEntity.getCost());
			sheetRetiros.autoSizeColumn(1);

			cellRetiros = rowRetiros.createCell(2);
			cellRetiros.setCellValue(retirementEntity.getName());
			sheetRetiros.autoSizeColumn(2);

			cellRetiros = rowRetiros.createCell(3);
			cellRetiros.setCellValue(retirementEntity.getAlreadyPaid() == false ? "Pendiente" : "Depositado");
			sheetRetiros.autoSizeColumn(3);

			cellRetiros = rowRetiros.createCell(4);
			cellRetiros.setCellValue(retirementEntity.getFechaDeCreacion());
			sheetRetiros.autoSizeColumn(4);
			cellRetiros.setCellStyle(cellStyle);

		}

		// Transferencias page

		for (TransferEntity transfer : transferList) {

			Row rowTransferencias = sheetTransferencias.createRow(rowTransferenciasCount++);

			Cell cellTransferencias = rowTransferencias.createCell(0);
			cellTransferencias.setCellValue(transfer.getId());
			sheetTransferencias.autoSizeColumn(0);

			cellTransferencias = rowTransferencias.createCell(1);
			cellTransferencias.setCellValue(transfer.getCost());
			sheetTransferencias.autoSizeColumn(1);

			cellTransferencias = rowTransferencias.createCell(2);
			cellTransferencias.setCellValue(transfer.getName());
			sheetTransferencias.autoSizeColumn(2);

			cellTransferencias = rowTransferencias.createCell(3);
			cellTransferencias.setCellValue(transfer.getTransferCode());
			sheetTransferencias.autoSizeColumn(3);

			cellTransferencias = rowTransferencias.createCell(4);
			cellTransferencias.setCellValue(transfer.getFechaDeCreacion());
			sheetTransferencias.autoSizeColumn(4);
			cellTransferencias.setCellStyle(cellStyle);

		}

		for (StudentEntity student : studentList) {

			Row rowAlumnos = sheetAlumnos.createRow(rowAlumnosCount++);

			Cell cellAlumnos = rowAlumnos.createCell(0);
			cellAlumnos.setCellValue(student.getId());
			sheetAlumnos.autoSizeColumn(0);

			cellAlumnos = rowAlumnos.createCell(1);
			cellAlumnos.setCellValue(student.getName());
			sheetAlumnos.autoSizeColumn(1);

			cellAlumnos = rowAlumnos.createCell(2);
			cellAlumnos.setCellValue(student.getLastName());
			sheetAlumnos.autoSizeColumn(2);

			cellAlumnos = rowAlumnos.createCell(3);
			cellAlumnos.setCellValue(student.getPhone());
			sheetAlumnos.autoSizeColumn(3);

			cellAlumnos = rowAlumnos.createCell(4);
			cellAlumnos.setCellValue(student.getEmail());
			sheetAlumnos.autoSizeColumn(4);

			cellAlumnos = rowAlumnos.createCell(5);
			cellAlumnos.setCellValue(student.getFechaDeCreacion());
			sheetAlumnos.autoSizeColumn(5);
			cellAlumnos.setCellStyle(cellStyle);

		}

		// Profesores page
		for (TeacherEntity teacher : teacherList) {

			Row rowProfesores = sheetProfesores.createRow(rowProfesoresCount++);

			Cell cellProfesores = rowProfesores.createCell(0);
			cellProfesores.setCellValue(teacher.getId());
			sheetProfesores.autoSizeColumn(0);

			cellProfesores = rowProfesores.createCell(1);
			cellProfesores.setCellValue(teacher.getName());
			sheetProfesores.autoSizeColumn(1);

			cellProfesores = rowProfesores.createCell(2);
			cellProfesores.setCellValue(teacher.getLastName());
			sheetProfesores.autoSizeColumn(2);

			cellProfesores = rowProfesores.createCell(3);
			cellProfesores.setCellValue(teacher.getEmail());
			sheetProfesores.autoSizeColumn(3);

			cellProfesores = rowProfesores.createCell(4);
			cellProfesores.setCellValue(teacher.getPhone());
			sheetProfesores.autoSizeColumn(4);

			cellProfesores = rowProfesores.createCell(5);
			cellProfesores.setCellValue(teacher.getEnabledPlatform() == false ? "Inactivo" : "Activo");
			sheetProfesores.autoSizeColumn(5);

			cellProfesores = rowProfesores.createCell(6);
			cellProfesores.setCellValue(teacher.getFechaDeCreacion());
			sheetProfesores.autoSizeColumn(6);
			cellProfesores.setCellStyle(cellStyle);

		}

		workbook.write(outPutStream);
		return new ByteArrayInputStream(outPutStream.toByteArray());

	}

}
