# DevTools - Selenium WebDriver Integration Project (Java Client)

DevTools-Selenium is a Java library for Integrating Selenium WebDriver and Chrome DevTools.

**Declarative:** DevTools-Selenium makes it painless to call devtools api either independently or through Selenium WebDriver. DevTools-Selenium will efficiently interact with Chrome DevTools Protocol, that allows for tools to instrument, inspect, debug and profile Chromium, Chrome, Edge and other Blink-based browsers.Declarative code make your test script more predictable, simpler to understand, and easier to debug.

**Domain-Based:** Build encapsulated components that assist to call each instrumentation domain like DOM, Debugger, Network etc. Every domain wrapper has all the commands it supports and events it generates. 

**Learn Once, Run Independently or with Selenium:** We don't make assumptions about your test automation stack, so you can integrate the Devtools Features with Selenium WebDriver with single line of code. DevTools-Selenium also offer you to interact with Chrome through devtools API and without using Selenium WebDriver.

For more information on Chrome DevTools, see https://chromedevtools.github.io/devtools-protocol/.
Selenium 4 Devtools API can be found here: https://github.com/SeleniumHQ/selenium/tree/master/java/client/src/org/openqa/selenium/support/devtools

The source code is made available under the [Apache 2.0 license]


**Why should use this project?**

1) This is independent Java project that helps you to interact with chrome (with and without Selenium WebDriver) and perform all your devtools operations that is built within the chrome devtools protocol.
2) For every single domain, the Java client is built and by which, the methods, types and event listeners are wrapped and exposed. By which, the integration becomes easier 

## Usage

Add the following dependency to your `pom.xml`:

```xml
<dependency>
  <groupId>com.qeagle</groupId>
  <artifactId>devtools-selenium</artifactId>
  <version>1.2.1</version>
</dependency>
```
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
