package com.bowlerocorp.qa.gems_qa.functionalTests.DashBoard;

import java.io.File;
import java.util.NoSuchElementException;
import org.apache.log4j.Logger;
import org.openqa.selenium.InvalidElementStateException;

import com.bowlerocorp.qa.gems_qa.pageObjects.EventManagement.ActivitiesPage;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class Gems_AppTest extends ActivitiesPage {
	String ExtentReportsFile;
	ExtentReports extentReports;
	ExtentTest extentTest;
	Logger logger;

	public Gems_AppTest() throws Exception {
		this.ExtentReportsFile = new File(
				System.getProperty("user.dir") + File.separator + "ExtentReportsFile" + File.separator
						+ "ExtentReportsFile_" + new java.util.Date().toString().replaceAll("[\\W+]", "") + ".html")
								.toString();
		this.extentReports = new ExtentReports(ExtentReportsFile, false);
		this.logger = Logger.getLogger(Gems_AppTest.class);
		//loadProperties();
	}

	public void GemsLogin() throws Exception {
		try {
			extentTest = extentReports.startTest(new Object() {
			}.getClass().getEnclosingMethod().getName());
			BrowserLaunch(getBrowserName(), getURL());

			/*
			 * getUsername().sendKeys(getUserID()); getPassword().sendKeys(getPasword());
			 * getSignIn().click(); getNavTourCloseButton().click();
			 * highlightElement(getNativeDriver(), getNavTourCloseButton());
			 * getJSE().executeScript("arguments[0].click();", getNavTourCloseButton());
			 * highlightElement(getNativeDriver(), getNavTourCloseButton());
			 */

			extentTest.log(LogStatus.PASS, "<a href=" + TakesnapShot() + ">Gems LogIn Successful!</a>");
			extentReports.endTest(extentTest);
			extentReports.flush();
		} catch (NoSuchElementException e) {
			logger.fatal("Unable to	locate element - ", e);
		} catch (InvalidElementStateException e1) {
			logger.fatal("Unable to	enter text - ", e1);
		}
	}

	public void ExploreDynamics365() throws Exception {
		try {
			extentTest = extentReports.startTest(new Object() {
			}.getClass().getEnclosingMethod().getName());
			GemsLogin();
			getNativeDriver().switchTo().frame("InlineDialog_Iframe");
			getNavTourLearningLink().click();
			switchWindowHandle();
			extentTest.log(LogStatus.PASS, "<a href=" + TakesnapShot() + ">ExploreDynamics365 Successful!</a>");
			extentReports.endTest(extentTest);
			extentReports.flush();
		} catch (NoSuchElementException e) {
			logger.fatal("Unable to	locate element - ", e);
		} catch (InvalidElementStateException e1) {
			logger.fatal("Unable to	enter text - ", e1);
		}
	}

	public void gotoWorkPlace() {
		getNavTourCloseButton().click();
		getMenu().click();
		getWorkPlace().click();

	}

	public void gotoEventManagement() {
		getNativeDriver().switchTo().frame("InlineDialog_Iframe");
		getNavTourCloseButton().click();
		// getNativeDriver().switchTo().defaultContent();
		getJSE().executeScript("arguments[0].click();", getMenu());
		getJSE().executeScript("arguments[0].click();", getEventManagement());
	}

	public void gotoService() {
		getNavTourCloseButton().click();
		getMenu().click();
		getService().click();
	}

	public void gotoMarketing() {
		getNavTourCloseButton().click();
		getMenu().click();
		getMarketing().click();
	}

	public void gotoSettings() {
		getNavTourCloseButton().click();
		getMenu().click();
		getSettings().click();
	}

	public void getTaining() {
		getNavTourCloseButton().click();
		getMenu().click();
		getTraining().click();

	}

	public static void main(String args[]) throws Exception {
		Gems_AppTest GAT = new Gems_AppTest();
		GAT.GemsLogin();
		//GAT.ExploreDynamics365();

	}

}
