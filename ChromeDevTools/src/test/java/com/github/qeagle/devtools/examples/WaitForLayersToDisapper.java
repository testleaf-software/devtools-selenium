package com.github.qeagle.devtools.examples;

import com.github.qeagle.devtools.launch.ChromeLauncher;
import com.github.qeagle.devtools.protocol.commands.LayerTree;
import com.github.qeagle.devtools.protocol.commands.Network;
import com.github.qeagle.devtools.protocol.commands.Page;
import com.github.qeagle.devtools.protocol.events.layertree.LayerTreeDidChange;
import com.github.qeagle.devtools.protocol.support.types.EventHandler;
import com.github.qeagle.devtools.protocol.support.types.EventListener;
import com.github.qeagle.devtools.protocol.types.layertree.Layer;
import com.github.qeagle.devtools.services.ChromeDevToolsService;
import com.github.qeagle.devtools.services.ChromeService;
import com.github.qeagle.devtools.services.impl.ChromeServiceImpl;
import com.github.qeagle.devtools.services.types.ChromeTab;
import com.github.qeagle.devtools.services.types.EventListenerImpl;

import java.lang.Thread.State;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.print.attribute.standard.Sides;

import org.omg.Messaging.SyncScopeHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Blocks an URLs given a patterns.
 *
 * @author TestLeaf
 */
public class WaitForLayersToDisapper {
	public static void main(String[] args) throws InterruptedException, ExecutionException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {

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

		// Load the URL now
		driver.get("https://raphaelfabeni.com/css-loader/");

		// Get the Layers 
		LayerTree layers = devToolsService.getLayerTree();
		layers.enable();

		driver.findElement(By.id("loader-border")).click();
			
		layers.onLayerTreeDidChange(
				event -> {
					System.out.println(event.getLayers().size());
				});
		
		waitUntilLayerChanged();
		
		driver.findElement(By.id("loader-bar-rounded")).click();
		

	}

	private static void waitUntilLayerChanged() {
		try {
			boolean bRunning = true;
			Thread.sleep(500);
			int runningThreads = Thread.getAllStackTraces().keySet().size();

			while(bRunning) {
				if(runningThreads > Thread.getAllStackTraces().keySet().size())
					bRunning = false;
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("I am done");
	}


}
