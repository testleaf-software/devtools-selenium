package com.github.qeagle.devtools.examples;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.remote.SessionId;

import com.github.qeagle.devtools.launch.ChromeLauncher;
import com.github.qeagle.devtools.protocol.commands.*;
import com.github.qeagle.devtools.protocol.types.dom.Node;
import com.github.qeagle.devtools.protocol.types.dom.RGBA;
import com.github.qeagle.devtools.protocol.types.overlay.HighlightConfig;
import com.github.qeagle.devtools.services.ChromeDevToolsService;
import com.github.qeagle.devtools.services.ChromeService;
import com.github.qeagle.devtools.services.impl.ChromeServiceImpl;
import com.github.qeagle.devtools.services.types.ChromeTab;

/**
 * Highlights an elements same way the Chrome inspector does.
 *
 * @author TestLeaf
 */
public class HighlightElement {


	public static void main(String[] args) throws InterruptedException {
		// Create chrome launcher.
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

		// Launch chrome using Selenium
		ChromeDriver driver = new ChromeDriver();
		driver.get("http://leafground.com");

		// Get the Google ChromeOptions
		Object capability = driver.getCapabilities().getCapability("goog:chromeOptions");
		Map<String,String> chromeOptions = (Map<String,String>)capability;

		// Connect to the chrome driver service
		ChromeService chromeService = new ChromeServiceImpl(Integer.parseInt(chromeOptions.get("debuggerAddress").replaceAll("\\D","")));	
		ChromeDevToolsService devToolsService = chromeService.createDevToolsService();

		// Highlight and clear 
		highlightNode(devToolsService,"h5");
		Thread.sleep(2000);
		clearHighlightNode(devToolsService, "h5");
		
		// Click using Selenium
		driver.findElementByCssSelector("h5").click();


	}

	private static void highlightNode(
			ChromeDevToolsService devToolsService,String selector) {

		// Enable DOM and overlay
		devToolsService.getDOM().enable();
		devToolsService.getOverlay().enable();


		HighlightConfig highlightConfig = new HighlightConfig();

		highlightConfig.setBorderColor(rgba(255, 229, 153, 0.66));
		highlightConfig.setContentColor(rgba(111, 168, 220, 0.66));
		highlightConfig.setCssGridColor(rgb(75, 0, 130));
		highlightConfig.setEventTargetColor(rgba(255, 196, 196, 0.66));
		highlightConfig.setMarginColor(rgba(246, 178, 107, 0.66));
		highlightConfig.setPaddingColor(rgba(147, 196, 125, 0.55));
		highlightConfig.setShapeColor(rgba(96, 82, 117, 0.8));
		highlightConfig.setShapeMarginColor(rgba(96, 82, 127, 0.6));

		highlightConfig.setShowExtensionLines(true);
		highlightConfig.setShowInfo(true);
		highlightConfig.setShowRulers(true);
		highlightConfig.setShowStyles(false);


		final DOM dom = devToolsService.getDOM();
		final Overlay overlay = devToolsService.getOverlay();
		final Integer nodeId =
				dom.querySelector(devToolsService.getDOM().getDocument().getNodeId(), selector);
		overlay.highlightNode(highlightConfig, nodeId, null, null, null);


	}

	private static void clearHighlightNode(
			ChromeDevToolsService devToolsService, String selector) {
		final DOM dom = devToolsService.getDOM();
		final Overlay overlay = devToolsService.getOverlay();
		final Integer nodeId =
				dom.querySelector(devToolsService.getDOM().getDocument().getNodeId(), selector);
		overlay.highlightNode(new HighlightConfig(), nodeId, null, null, null);

	}

	private static RGBA rgba(int r, int g, int b, double a) {
		RGBA result = new RGBA();
		result.setR(r);
		result.setG(g);
		result.setB(b);
		result.setA(a);
		return result;
	}

	private static RGBA rgb(int r, int g, int b) {
		RGBA result = new RGBA();
		result.setR(r);
		result.setG(g);
		result.setB(b);
		return result;
	}
}
