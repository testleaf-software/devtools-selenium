package com.qeagle.devtools.examples;

import com.qeagle.devtools.protocol.commands.Emulation;
import com.qeagle.devtools.protocol.types.page.CaptureScreenshotFormat;
import com.qeagle.devtools.protocol.types.page.LayoutMetrics;
import com.qeagle.devtools.protocol.types.page.Viewport;
import com.qeagle.devtools.services.ChromeDevToolsService;
import com.qeagle.devtools.services.ChromeService;
import com.qeagle.devtools.services.impl.ChromeServiceImpl;
import com.qeagle.devtools.utils.FullScreenshot;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeDriver;
import org.qeagle.devtools.webdriver.DevToolsService;

/**
 * Takes a full page screenshot. The output screenshot dimensions will be page width x page height
 * 
 *
 * @author Gopinath
 */
public class FullPageScreenshot {


	public static void main(String[] args) {

		// Create chrome launcher.
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

		// Launch chrome using Selenium
		ChromeDriver driver = new ChromeDriver();
		driver.get("https://www.amazon.in/ref=cs_503_link/");

		// Get the Devtools Service
		ChromeDevToolsService devToolsService = DevToolsService.getDevToolsService(driver);

		// Take full screen
		FullScreenshot.captureFullPageScreenshot(devToolsService, "screenshot.png");

		// Close the browser
		driver.close();

	}


}
