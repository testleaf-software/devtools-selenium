package excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WriteExcel {

	public static void main(String[] args) throws IOException {
		WriteExcel.writeExcelData("Results");
	}

	public static void writeExcelData(String excelName) throws IOException {

		// "./data/"+excelName+".xlsx"

		// Step 1: Create the Workbook (blank)
		XSSFWorkbook wbook = new XSSFWorkbook();

		// get -> create

		// Step 2:Create sheet
		//XSSFSheet sheet = wbook.getSheet("Create Lead");
		XSSFSheet sheet = wbook.createSheet("Report");

		// Step 3: Create row
		XSSFRow headerRow = sheet.createRow(0);

		// Step 4: Create cell 
		XSSFCell cell = headerRow.createCell(0);

		// Step 5: Set the value
		cell.setCellValue("TestCase Name");

		// Step 4: Create cell 
		XSSFCell cell1 = headerRow.createCell(1);

		// Step 5: Set the value
		cell1.setCellValue("Status");
		
		// How to save it
		FileOutputStream fos = new FileOutputStream(new File("./reports/"+excelName+".xlsx"));
		wbook.write(fos);
		
		// Close the workbook
		wbook.close();


	}

}
