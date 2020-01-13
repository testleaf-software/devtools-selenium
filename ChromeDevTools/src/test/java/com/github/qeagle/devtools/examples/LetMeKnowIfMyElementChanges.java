package com.github.qeagle.devtools.examples;

import static org.openqa.selenium.devtools.ConverterFunctions.map;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.Command;
import org.openqa.selenium.devtools.Connection;
import org.openqa.selenium.devtools.Console;
import org.openqa.selenium.devtools.ConverterFunctions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.Event;
import org.openqa.selenium.devtools.Log;
import org.openqa.selenium.devtools.Console.ConsoleMessage;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

public class LetMeKnowIfMyElementChanges {

	@Test
	@SuppressWarnings({"unchecked","rawtypes"})
	public void runTest() throws InterruptedException, IOException {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();

		ChromeDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		DevTools devTools = driver.getDevTools();

		devTools.createSession();

		// Load the URL now
		driver.get("http://leaftaps.com/crmsfa/control/findLeads");
		
		driver.findElement(By.name("USERNAME")).sendKeys("DemoSalesManager");
		driver.findElement(By.name("PASSWORD")).sendKeys("crmsfa");
		driver.findElement(By.xpath("//input[@value='Login']")).click();
		
		devTools.send(new Command("DOM.enable",
				ImmutableMap.of()));

		Object root = devTools.send(new Command("DOM.getDocument",
				ImmutableMap.of("depth", -1, "pierce", true), ConverterFunctions.map("root", Object.class)));

		HashMap<String,Object> allProperties = (HashMap<String,Object>)root;
		long rootNode = (long) allProperties.get("nodeId");		
		System.out.println(rootNode);
		
/*		Integer firstResult = devTools.send(new Command<>("DOM.querySelector",
				ImmutableMap.of("nodeId", rootNode, "selector", "div[class='x-grid3-cell-inner x-grid3-col-partyId'] > a"), ConverterFunctions.map("nodeId", Integer.class)));
		
		System.out.println(firstResult);*/
		
/*		devTools.send(new Command<>("DOM.attributeModified",
				ImmutableMap.of("nodeId", firstResult, "name", "", "value", "")));*/
		
		/*devTools.addListener(new Event<ConsoleMessage>(
		        "Console.messageAdded",
		        map("message", ConsoleMessage.class));*/
				
		
		devTools.addListener(new Event<>(
		        "DOM.attributeModified",
		         map("name", String.class)), entry -> {
		        	 System.out.println("DOM Updates: " +entry);
		        });
		
		/*devTools.addListener(new Event<>(
		        "DOM.documentUpdated",
		         map("", String.class)), entry -> {
		        	 System.out.println("Page Loaded" +entry);
		        });*/
		
		driver.findElementByXPath("(//input[@name='firstName'])[3]").sendKeys("Priya");
		driver.findElementByXPath("//button[text()='Find Leads']").click();
		
		Thread.sleep(5000);

		driver.findElementByCssSelector("div[class='x-grid3-cell-inner x-grid3-col-partyId'] > a").click();

		Thread.sleep(5000);

		
		
	}

	
}
