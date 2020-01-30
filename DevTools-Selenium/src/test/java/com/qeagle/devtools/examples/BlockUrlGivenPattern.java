package com.qeagle.devtools.examples;

import com.qeagle.devtools.services.ChromeDevToolsService;
import com.qeagle.devtools.utils.BlockURLs;

import java.util.Arrays;

import org.openqa.selenium.chrome.ChromeDriver;
import org.qeagle.devtools.webdriver.DevToolsService;

/**
 * Blocks an URLs given a patterns.
 *
 * @author Gopinath
 */
public class BlockUrlGivenPattern {
	public static void main(String[] args) {
		// Create chrome launcher.
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

		// Launch chrome using Selenium
		ChromeDriver driver = new ChromeDriver();

		// Get the Devtools Service
		ChromeDevToolsService devToolsService = DevToolsService.getDevToolsService(driver);

		// Block the URL contents
		BlockURLs.blockGivenURLs(devToolsService, Arrays.asList("**/*.css", "**/*.png", "**/*.svg"));

		// Load the URL now
		driver.get("http://leafground.com");

		

	}
}
