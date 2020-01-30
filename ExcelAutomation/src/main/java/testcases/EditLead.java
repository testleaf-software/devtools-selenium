package testcases;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import excel.ReadExcel;

public class EditLead extends ProjectSpecificMethods{
	
	@BeforeTest
	public void setValues() {
		excelFileName = "EditLead";
	}

	@Test(dataProvider="fetchData")
	public void editLead(String phNumber, String cName) throws InterruptedException {
		driver.findElementByLinkText("Find Leads").click();
		driver.findElementByXPath("//span[text()='Phone']").click();
		driver.findElementByXPath("//input[@name='phoneNumber']").sendKeys(phNumber);
		driver.findElementByXPath("//button[text()='Find Leads']").click();
		Thread.sleep(2000);
		driver.findElementByXPath("//div[@class='x-grid3-cell-inner x-grid3-col-partyId']/a").click();
		driver.findElementByLinkText("Edit").click();
		driver.findElementById("updateLeadForm_companyName").sendKeys(cName);
		driver.findElementByName("submitButton").click();
	}
}






