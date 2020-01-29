# Devtools - Selenium WebDriver Integration Project (Java Client)


**Description:**

A Java Client for Devtools with integration focused specially with Selenium WebDriver. The purpose of the project is to expand the boundaries of the selenium webdriver so that it can provide deeper browser automation using devtools integration.

For more information on DevTools, see https://chromedevtools.github.io/devtools-protocol/.
For more information on Selenium, see https://github.com/SeleniumHQ/selenium

The source code is made available under the [Apache 2.0 license]


**Why should use this project?**

1) This is independent Java project that helps you to interact with chrome (with and without Selenium WebDriver) and perform all your devtools operations that is built within the chrome devtools protocol.
2) For every single domain, the Java client is built and by which, the methods, types and event listeners are wrapped and exposed. By which, the integration becomes easier 

**Usage:**

We are in the process of adding this as dependecy to maven and will be published shortly. Stay for the update soon.

**Examples:**

**Selenium 4 Devtools Code for Taking Full Screenshot**

```
package selenium.tests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.Command;
import org.openqa.selenium.devtools.ConverterFunctions;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.Event;
import org.openqa.selenium.devtools.Log;
import org.openqa.selenium.devtools.Target;
import org.openqa.selenium.devtools.Target.SessionId;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableMap;

public class SeleniumDevToolsFullSnap{

	public static void main(String[] args) throws InterruptedException, IOException {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();

		ChromeDriver driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		DevTools devTools = driver.getDevTools();
		devTools.createSession();

		driver.get("https://www.amazon.com/");
		driver.manage().window().maximize();
		
		devTools.send(new Command(
				"Emulation.setDeviceMetricsOverride",
				ImmutableMap.of("deviceScaleFactor", 1,"mobile", false,"width",1400,"height",2000)));



		String snap = devTools.send(new Command<>("Page.captureScreenshot",
				ImmutableMap.of("fromSurface", true), ConverterFunctions.map("data", String.class)));

		System.out.println(snap);

		Base64 decoder = new Base64();
		byte[] imgBytes = decoder.decode(snap.toString());
		FileOutputStream osf = new FileOutputStream(new File("./snap1.png"));
		osf.write(imgBytes);
		osf.flush();
		osf.close();

	}

}


```

**DevTools-Selenium Code for taking Full Page Screenshot**

```
package com.github.qeagle.devtools.examples;

import com.github.qeagle.devtools.protocol.commands.Emulation;
import com.github.qeagle.devtools.protocol.types.page.CaptureScreenshotFormat;
import com.github.qeagle.devtools.protocol.types.page.LayoutMetrics;
import com.github.qeagle.devtools.protocol.types.page.Viewport;
import com.github.qeagle.devtools.services.ChromeDevToolsService;
import com.github.qeagle.devtools.services.ChromeService;
import com.github.qeagle.devtools.services.impl.ChromeServiceImpl;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Takes a full page screenshot. The output screenshot dimensions will be page width x page height,
 * for example when capturing http://amazon.com the output screenshot.
 *
 * @author TestLeaf
 */
public class FullPageScreenshot {

	public static void main(String[] args) {

		// Create chrome launcher.
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

		// Launch chrome using Selenium
		ChromeDriver driver = new ChromeDriver();
		driver.get("https://www.amazon.com/");

		// Get the Google ChromeOptions
		Object capability = driver.getCapabilities().getCapability("goog:chromeOptions");
		Map<String,String> chromeOptions = (Map<String,String>)capability;

		// Connect to the chrome driver service
		ChromeService chromeService = new ChromeServiceImpl(Integer.parseInt(chromeOptions.get("debuggerAddress").replaceAll("\\D","")));	
		ChromeDevToolsService devToolsService = chromeService.createDevToolsService();

		// Take full screen (Integrated with Selenium using Remote Debugger Address
		DevToolsScreenshot.captureFullPageScreenshot(devToolsService, "screenshot.png");

	}

}

```

**DevTools Indepdent Code for taking Full Page Screenshot (Without Using Selenium)**

```
package com.github.qeagle.devtools.examples;

import com.github.qeagle.devtools.protocol.commands.Emulation;
import com.github.qeagle.devtools.protocol.types.page.CaptureScreenshotFormat;
import com.github.qeagle.devtools.protocol.types.page.LayoutMetrics;
import com.github.qeagle.devtools.protocol.types.page.Viewport;
import com.github.qeagle.devtools.services.ChromeDevToolsService;
import com.github.qeagle.devtools.services.ChromeService;
import com.github.qeagle.devtools.services.impl.ChromeServiceImpl;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Takes a full page screenshot. The output screenshot dimensions will be page width x page height,
 * for example when capturing http://amazon.com the output screenshot.
 *
 * @author TestLeaf
 */
public class FullPageScreenshotWithoutSelenium {

	public static void main(String[] args) {

		// Create chrome launcher.
    ChromeLauncher launcher = new ChromeLauncher();

    // Launch chrome either as headless (true) or regular (false).
    ChromeService chromeService = launcher.launch(false);

    // Create empty tab ie about:blank.
    ChromeTab tab = chromeService.createTab();

    // Get DevTools service to this tab
    ChromeDevToolsService devToolsService = chromeService.createDevToolsService(tab);

		// Take full screen (Integrated with Selenium using Remote Debugger Address
		DevToolsScreenshot.captureFullPageScreenshot(devToolsService, "screenshot.png");

	}

}

```

More Examples can be seen [here](https://github.com/testleaf-software/devtools-selenium/tree/master/ChromeDevTools/src/test/java/com/github/qeagle/devtools/examples). 


**Additional Insights**

1) Selenium WebDriver Devtools is in the early stage of wrapping few essential domains. Hence you may have to write your own custom code for non available methods and listeners like above case. 
2) Whereas, in this repo - we have built all the existing chrome devtools domain - including methods, types and listeners in Java and that makes your code more manageable and simplified.
3) This can be independently executed without Selenium WebDriver as well can be integrated with the chrome, which is launched by WebDriver. This provides flexibility for you to choose either one.

**License**
Devtools - Selenium Java Client is licensed under the Apache License, Version 2.0. 
