# Devtools - Selenium WebDriver Integration Project (Java Client)


**Description:**

A Java Client for Devtools with integration focused specially with Selenium WebDriver. 

For more information on DevTools, see https://chromedevtools.github.io/devtools-protocol/.
For more information on Selenium, see https://github.com/SeleniumHQ/selenium

The source code is made available under the [Apache 2.0 license]


**Why should use this project?**

1) This is independent Java project that helps you to interact with chrome (with and without Selenium WebDriver) and perform all your devtools operations that is built within the chrome devtools protocol.
2) For every single domain, the Java client is built and by which, the methods, types and event listeners are wrapped and exposed. By which, the integration becomes easier 


**How does it different than using Selenium 4.X Devtools API*?

1) Selenium WebDriver Devtools is in the early stage of wrapping few essential domains. Hence you may have to write your own custom code for non available methods and listeners
2) Whereas, in this repo - we have built all the existing chrome devtools domain - including methods, types and listeners in Java
3) This can be independently executed without Selenium WebDriver as well can be integrated with the cheome that is launched by WebDriver. This provides flexibility for you to use in either case.


**Usage:**

We are in the process of adding this as dependecy to maven and will be published shortly. Stay for the update soon.

**Examples:**

See examples here: https://github.com/testleaf-software/devtools-selenium/tree/master/ChromeDevTools/src/test/java/com/github/qeagle/devtools/examples

**Running unit tests**

make verify

**License**
Devtools - Selenium Java Client is licensed under the Apache License, Version 2.0. 
