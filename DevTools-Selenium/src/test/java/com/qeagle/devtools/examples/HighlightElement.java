package com.qeagle.devtools.examples;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.remote.SessionId;
import org.qeagle.devtools.webdriver.DevToolsService;

import com.qeagle.devtools.launch.ChromeLauncher;
import com.qeagle.devtools.protocol.commands.*;
import com.qeagle.devtools.protocol.types.dom.Node;
import com.qeagle.devtools.protocol.types.dom.RGBA;
import com.qeagle.devtools.protocol.types.overlay.HighlightConfig;
import com.qeagle.devtools.services.ChromeDevToolsService;
import com.qeagle.devtools.services.ChromeService;
import com.qeagle.devtools.services.impl.ChromeServiceImpl;
import com.qeagle.devtools.services.types.ChromeTab;
import com.qeagle.devtools.utils.HighlightNode;

/**
 * Highlights an elements same way the Chrome inspector does.
 *
 * @author Gopinath
 */
public class HighlightElement {


	public static void main(String[] args) throws InterruptedException {
		
		// Create chrome launcher.
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

		// Launch chrome using Selenium
		ChromeDriver driver = new ChromeDriver();
		driver.get("http://leafground.com");

		// Get the Devtools Service
		ChromeDevToolsService devToolsService = DevToolsService.getDevToolsService(driver);

		// Highlight and clear 
		HighlightNode.highlightNode(devToolsService,"h5");
		
		// Let the highlight be there for a while
		Thread.sleep(2000);
		
		// Good Practice is to clear the highlighted node
		HighlightNode.clearHighlightNode(devToolsService, "h5");

		// Close the browser
		driver.close();
		
		
	}


}
