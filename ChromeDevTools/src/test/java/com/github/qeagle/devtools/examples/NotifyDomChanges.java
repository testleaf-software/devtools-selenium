package com.github.qeagle.devtools.examples;

import com.github.qeagle.devtools.launch.ChromeLauncher;
import com.github.qeagle.devtools.protocol.commands.Console;
import com.github.qeagle.devtools.protocol.commands.DOM;
import com.github.qeagle.devtools.protocol.commands.LayerTree;
import com.github.qeagle.devtools.protocol.commands.Network;
import com.github.qeagle.devtools.protocol.commands.Page;
import com.github.qeagle.devtools.protocol.events.layertree.LayerTreeDidChange;
import com.github.qeagle.devtools.protocol.support.types.EventHandler;
import com.github.qeagle.devtools.protocol.support.types.EventListener;
import com.github.qeagle.devtools.protocol.types.dom.Node;
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
import org.openqa.selenium.Keys;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Blocks an URLs given a patterns.
 *
 * @author TestLeaf
 */
public class NotifyDomChanges {
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
		driver.get("http://leaftaps.com/crmsfa/control/findLeads");
		
		driver.findElement(By.name("USERNAME")).sendKeys("DemoSalesManager");
		driver.findElement(By.name("PASSWORD")).sendKeys("crmsfa");
		driver.findElement(By.xpath("//input[@value='Login']")).click();

		// Get the DOM
		DOM dom = devToolsService.getDOM();

		// Get the parent Node
		Integer parentNode = dom.getDocument(-1, true).getNodeId();
		
		// Find the element
		Integer firstResult = dom.querySelector(parentNode, "div[class='x-grid3-row   ']");
		
		System.out.println("The first one "+firstResult);
		// Did the element change
		Node firstNode = dom.describeNode(firstResult, null, null, null, null);
		List<String> attributes = firstNode.getAttributes();
		for (String string : attributes) {
			System.out.println(string);
		}
		dom.onAttributeModified(
				event -> {
					Integer firstOne = dom.querySelector(parentNode, "div[class='x-grid3-row   ']");
					Node describeNode = dom.describeNode(firstOne, null, null, null, null);
					if(describeNode.getAttributes().equals(attributes)){
						System.out.println("Matches");
					}
				}
		);
				
		/*// Find the DOM element with specific label
		Integer submit = dom.querySelector(parentNode, "input[class='decorativeSubmit']");

		// Find the attributes of the element
		List<String> attributes = dom.getAttributes(submit);
		for (String eachAttr : attributes) {
			System.out.println(eachAttr);
		}

		// Returns the id of the nearest ancestor that is a relayout boundary.
		Integer ancestor = dom.getRelayoutBoundary(submit);

		// Find the attributes of the element
		attributes = dom.getAttributes(ancestor);
		for (String eachAttr : attributes) {
			System.out.println(eachAttr);
		}*/

		/*Integer drawer = dom.querySelector(parentNode, "#drawerContainer");
		System.out.println(drawer);*/


		//Node node;
		//List<Node> shadowRoots = node.getShadowRoots();
		//System.out.println(shadowRoots.size());
		
		/*
		
		Integer drawer = dom.querySelector(parentNode, "#drawer");
		System.out.println(drawer);
		
		Node describeNode = dom.describeNode(drawer, null, null, null, null);
		List<Node> shadowRoots = describeNode.getShadowRoots();
		System.out.println(shadowRoots.size());
		String nodeName = shadowRoots.get(0).getName();
		System.out.println(nodeName);*/
		
	}




}
