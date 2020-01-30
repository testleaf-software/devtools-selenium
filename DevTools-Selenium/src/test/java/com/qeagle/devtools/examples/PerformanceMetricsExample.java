package com.qeagle.devtools.examples;

import java.util.List;

import org.openqa.selenium.chrome.ChromeDriver;
import org.qeagle.devtools.webdriver.DevToolsService;

import com.qeagle.devtools.launch.ChromeLauncher;
import com.qeagle.devtools.protocol.commands.Network;
import com.qeagle.devtools.protocol.commands.Page;
import com.qeagle.devtools.protocol.commands.Performance;
import com.qeagle.devtools.protocol.types.performance.Metric;
import com.qeagle.devtools.services.ChromeDevToolsService;
import com.qeagle.devtools.services.ChromeService;
import com.qeagle.devtools.services.types.ChromeTab;
import com.qeagle.devtools.utils.NetworkPerformance;

public class PerformanceMetricsExample {
	
	 public static void main(String[] args) throws InterruptedException {
		// Create chrome launcher.
			System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

			// Launch chrome using Selenium
			ChromeDriver driver = new ChromeDriver();

			// Get the Devtools Service
			ChromeDevToolsService devToolsService = DevToolsService.getDevToolsService(driver);
			
			// Measure Performance
			NetworkPerformance.getPerformanceMetrics(devToolsService);
			
			// Load Page
			driver.get("https://selenium.dev/");
			
			
		  }

}
