package com.bowlerocorp.qa.gems_automation.utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class getProperties {

	final static Logger logger = Logger.getLogger("getProperties");
	public static Properties prop = new Properties();

	@SuppressWarnings("unused")
	public void loadProperties() {
		InputStream input = null;
		try {
			String fileName = "src/main/java/com/bowlerocorp/qa/gems_qa/resources/config.properties";
			/*
			 * Thread currentThread = Thread.currentThread(); ClassLoader contextClassLoader
			 * = currentThread.getContextClassLoader(); input =
			 * contextClassLoader.getResourceAsStream(fileName);
			 */
			input = new FileInputStream(fileName);
			if (input != null) {
				prop.load(input);
				logger.info("Properties File loaded Successfully");
				System.out.println("Properties file loaded successfully!!!");
			} else {
				logger.error("Unable to load Properties File");
				System.out.println("Properties file not found!!!");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getPropertiesFile() {
		return prop.getProperty("propertiesfile");
	}
	
	public String getChromeDriver() {
		return prop.getProperty("chromedriver");
	}

	public String getChromeDriverPath() {
		return prop.getProperty("chromedriverpath");
	}

	public String getFirefoxDriver() {
		return prop.getProperty("firefoxdriver");
	}

	public String getFirefoxDriverPath() {
		return prop.getProperty("firefoxdriverpath");
	}

	public String getIEDriver() {
		return prop.getProperty("iedriver");
	}

	public String getIEDriverPath() {
		return prop.getProperty("iedriverpath");
	}

	public String getURL() {
		return prop.getProperty("url");
	}

	public String getAdminURL() {
		return prop.getProperty("adminurl");
	}

	public String getUserID() {
		return prop.getProperty("userid");
	}

	public String getAdminUserID() {
		return prop.getProperty("adminuserid");
	}

	public String getPasword() {
		return prop.getProperty("password");
	}

	public String getAdminPasword() {
		return prop.getProperty("adminpassword");
	}

	public String getfilePath() {
		return prop.getProperty("testdatafile");
	}

	public String getOutputFilePath() {
		return prop.getProperty("outputfile");
	}

	public String getBrowserName() {
		return prop.getProperty("browserName");
	}

	public static String getAutoItFile() {
		return prop.getProperty("autoitfile");
	}

	public static String getTestDataFile() {
		return prop.getProperty("testdatafile");
	}

	public static String getTaskActivitySheet() {
		return prop.getProperty("taskactivitysheet");
	}
	public static String getEmailActivitySheet() {
		return prop.getProperty("emailactivitysheet");
	}
}