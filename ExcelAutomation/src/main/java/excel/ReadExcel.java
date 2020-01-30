package excel;

import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {
	
	public static void main(String[] args) throws IOException {
		ReadExcel.getExcelData("EditLead");
	}

	public static String[][] getExcelData(String excelName) throws IOException {
		
		// Pre-Req : Create an excel and store in a common project folder
		// Know the path and name of the file
		
		// Step 1: Open the Workbook
		XSSFWorkbook wbook = new XSSFWorkbook("./data/"+excelName+".xlsx");
		
		// Step 2: Go to the sheet
		//XSSFSheet sheet = wbook.getSheet("Create Lead");
		XSSFSheet sheet = wbook.getSheetAt(0);
		
		// Find the row count (without the header - only the data)
		int rowCount = sheet.getLastRowNum();
		
		// Find the cell count
		XSSFRow headerRow = sheet.getRow(0);
		int cellCount = headerRow.getLastCellNum();
		
		// Create an array
		String[][] data = new String[rowCount][cellCount];
		
		for (int j = 1; j <= rowCount; j++) {
			// Step 3: Go to the row
			XSSFRow dataRow = sheet.getRow(j);
			for (int i = 0; i < cellCount; i++) {
				// Step 4: Go to the cell 
				XSSFCell cell = dataRow.getCell(i);
				// Step 5: Print the content of that cell
				String content = cell.getStringCellValue();
				System.out.println("value: " + content);
				data[j-1][i] = content;
				
			} 
		}
		
		// Close the workbook
		wbook.close();
		
		return data;
		
	}

}
