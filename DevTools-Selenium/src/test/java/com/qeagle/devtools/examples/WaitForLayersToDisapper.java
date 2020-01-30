package com.qeagle.devtools.examples;

import com.qeagle.devtools.launch.ChromeLauncher;
import com.qeagle.devtools.protocol.commands.LayerTree;
import com.qeagle.devtools.protocol.commands.Network;
import com.qeagle.devtools.protocol.commands.Page;
import com.qeagle.devtools.protocol.events.layertree.LayerTreeDidChange;
import com.qeagle.devtools.protocol.support.types.EventHandler;
import com.qeagle.devtools.protocol.support.types.EventListener;
import com.qeagle.devtools.protocol.types.layertree.Layer;
import com.qeagle.devtools.services.ChromeDevToolsService;
import com.qeagle.devtools.services.ChromeService;
import com.qeagle.devtools.services.impl.ChromeServiceImpl;
import com.qeagle.devtools.services.types.ChromeTab;
import com.qeagle.devtools.services.types.EventListenerImpl;
import com.qeagle.devtools.utils.LayerChange;

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
import org.qeagle.devtools.webdriver.DevToolsService;

/**
 * Blocks an URLs given a patterns.
 *
 * @author Gopinath
 */
public class WaitForLayersToDisapper {
	public static void main(String[] args) throws InterruptedException, ExecutionException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {

		// Create chrome launcher.
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

		// Launch chrome using Selenium
		ChromeDriver driver = new ChromeDriver();

		// Get the Devtools Service
		ChromeDevToolsService devToolsService = DevToolsService.getDevToolsService(driver);
		
		// Load the URL now
		driver.get("https://raphaelfabeni.com/css-loader/");

		// Get the Layers 
		LayerTree layers = LayerChange.enableLayers(devToolsService);

		// Click on loader
		driver.findElement(By.id("loader-border")).click();

		// Wait for the layers for reset
		LayerChange.waitUntilLayerChanged(layers);
		
		// Reset the layers
		LayerChange.disableLayers(devToolsService);
		
		// Click on the next element
		driver.findElement(By.id("loader-bar-rounded")).click();


	}




}
