package com.github.qeagle.devtools.examples;

import com.github.qeagle.devtools.launch.ChromeLauncher;
import com.github.qeagle.devtools.protocol.commands.Emulation;
import com.github.qeagle.devtools.protocol.commands.Page;
import com.github.qeagle.devtools.protocol.types.page.CaptureScreenshotFormat;
import com.github.qeagle.devtools.protocol.types.page.LayoutMetrics;
import com.github.qeagle.devtools.protocol.types.page.Viewport;
import com.github.qeagle.devtools.services.ChromeDevToolsService;
import com.github.qeagle.devtools.services.ChromeService;
import com.github.qeagle.devtools.services.impl.ChromeServiceImpl;
import com.github.qeagle.devtools.services.types.ChromeTab;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Takes a full page screenshot. The output screenshot dimensions will be page width x page height,
 * for example when capturing http://github.com the output screenshot image will be 1200 × 6598.
 *
 * @author TestLeaf
 */
public class FullPageScreenshot {

	private static void captureFullPageScreenshot(
			ChromeDevToolsService devToolsService, String outputFilename) {
		final LayoutMetrics layoutMetrics = devToolsService.getPage().getLayoutMetrics();

		final double width = layoutMetrics.getContentSize().getWidth();
		final double height = layoutMetrics.getContentSize().getHeight();

		final Emulation emulation = devToolsService.getEmulation();
		emulation.setDeviceMetricsOverride(
				Double.valueOf(width).intValue(), Double.valueOf(height).intValue(), 1.0d, Boolean.FALSE);

		Viewport viewport = new Viewport();
		viewport.setScale(1d);

		// You can set offset with X, Y
		viewport.setX(0d);
		viewport.setY(0d);

		// Set a width, height of a page to take screenshot at
		viewport.setWidth(width);
		viewport.setHeight(height);

		storeAsFile(
				outputFilename,
				devToolsService.getPage().captureScreenshot(CaptureScreenshotFormat.PNG, 100, viewport, Boolean.TRUE));
	}

	public static void main(String[] args) {

		// Create chrome launcher.
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

		// Launch chrome using Selenium
		ChromeDriver driver = new ChromeDriver();
		driver.get("https://www.amazon.in/ref=cs_503_link/");

		// Get the Google ChromeOptions
		Object capability = driver.getCapabilities().getCapability("goog:chromeOptions");
		Map<String,String> chromeOptions = (Map<String,String>)capability;

		// Connect to the chrome driver service
		ChromeService chromeService = new ChromeServiceImpl(Integer.parseInt(chromeOptions.get("debuggerAddress").replaceAll("\\D","")));	
		ChromeDevToolsService devToolsService = chromeService.createDevToolsService();

		// Take full screen
		captureFullPageScreenshot(devToolsService, "screenshot.png");


	}

	private static void storeAsFile(String fileName, String data) {
		FileOutputStream fileOutputStream = null;
		try {
			File file = new File(fileName);
			fileOutputStream = new FileOutputStream(file);
			fileOutputStream.write(Base64.getDecoder().decode(data));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.flush();
					fileOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
