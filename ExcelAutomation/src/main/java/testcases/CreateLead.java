package testcases;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.yaml.snakeyaml.reader.ReaderException;

import excel.ReadExcel;

public class CreateLead extends ProjectSpecificMethods{
	
	@BeforeTest
	public void setValues() {
		excelFileName = "CreateLead";
	}

	@Test(dataProvider="fetchData")
	public void createLead(String cName, String fName, String lName) {
		driver.findElementByLinkText("Create Lead").click();
		driver.findElementById("createLeadForm_companyName").sendKeys(cName);
		driver.findElementById("createLeadForm_firstName").sendKeys(fName);
		driver.findElementById("createLeadForm_lastName").sendKeys(lName);
		driver.findElementByName("submitButton").click();
	}
}






