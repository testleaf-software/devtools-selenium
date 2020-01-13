package com.github.qeagle.devtools.examples;

import com.github.qeagle.devtools.launch.ChromeLauncher;
import com.github.qeagle.devtools.protocol.commands.Network;
import com.github.qeagle.devtools.protocol.commands.Page;
import com.github.qeagle.devtools.services.ChromeDevToolsService;
import com.github.qeagle.devtools.services.ChromeService;
import com.github.qeagle.devtools.services.impl.ChromeServiceImpl;
import com.github.qeagle.devtools.services.types.ChromeTab;
import java.util.Arrays;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Blocks an URLs given a patterns.
 *
 * @author TestLeaf
 */
public class BlockUrlGivenPattern {
	public static void main(String[] args) {
		// Create chrome launcher.
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

		// Launch chrome using Selenium
		ChromeDriver driver = new ChromeDriver();

		// Get the Google ChromeOptions
		Object capability = driver.getCapabilities().getCapability("goog:chromeOptions");
		Map<String,String> chromeOptions = (Map<String,String>)capability;

		// Connect to the chrome driver service
		ChromeService chromeService = new ChromeServiceImpl(Integer.parseInt(chromeOptions.get("debuggerAddress").replaceAll("\\D","")));	
		ChromeDevToolsService devToolsService = chromeService.createDevToolsService();

		// Get the network and enable
		Network network = devToolsService.getNetwork();
		network.enable();

		// Block some urls.
		network.setBlockedURLs(Arrays.asList("**/*.css", "**/*.png", "**/*.svg"));

		// Load the URL now
		driver.get("http://leafground.com");

	
	}
}
