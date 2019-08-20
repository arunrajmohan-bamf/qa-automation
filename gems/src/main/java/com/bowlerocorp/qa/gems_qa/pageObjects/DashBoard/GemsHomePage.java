package com.bowlerocorp.qa.gems_qa.pageObjects.DashBoard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.bowlerocorp.qa.gems_automation.utilities.Browser;

public class GemsHomePage extends Browser {

	private By gemsheading = By.xpath("//div[contains(text(),'Explore Dynamics 365')]");

	public WebElement getGemsHeading() {
		return getWebwait().until(ExpectedConditions.visibilityOfElementLocated(gemsheading));
	}

	private By navtourclosebutton = By.xpath("//a[@href='javascript:close();']");

	public WebElement getNavTourCloseButton() {
		return getWebwait().until(ExpectedConditions.presenceOfElementLocated(navtourclosebutton));
	}

	private By navtourlearninglink = By.cssSelector("a.navTourLearningLink");

	public WebElement getNavTourLearningLink() {
		return getWebwait().until(ExpectedConditions.presenceOfElementLocated(navtourlearninglink));
	}

	private By user = By.cssSelector("a#navTabButtonUserInfoUserNameId");

	public WebElement getUser() {
		return getWebwait().until(ExpectedConditions.presenceOfElementLocated(user));
	}

	private By menu = By.cssSelector("span#TabWorkplace");

	public WebElement getMenu() {
		return getWebwait().until(ExpectedConditions.presenceOfElementLocated(menu));
	}

	private By workplace = By.xpath("//span[@class='navActionButtonLabel' and text()='Workplace']");

	public WebElement getWorkPlace() {
		return getWebwait().until(ExpectedConditions.presenceOfElementLocated(workplace));
	}

	private By eventmanagement = By.xpath("//span[@class='navActionButtonLabel' and text()='Event Management']");

	public WebElement getEventManagement() {
		return getWebwait().until(ExpectedConditions.presenceOfElementLocated(eventmanagement));
	}

	private By service = By.xpath("//span[@class='navActionButtonLabel' and text()='Service']");

	public WebElement getService() {
		return getWebwait().until(ExpectedConditions.presenceOfElementLocated(service));
	}

	private By marketing = By.xpath("//span[@class='navActionButtonLabel' and text()='Marketing']");

	public WebElement getMarketing() {
		return getWebwait().until(ExpectedConditions.presenceOfElementLocated(marketing));
	}

	private By settings = By.xpath("//span[@class='navActionButtonLabel' and text()='Settings']");

	public WebElement getSettings() {
		return getWebwait().until(ExpectedConditions.presenceOfElementLocated(settings));
	}

	private By training = By.xpath("//span[@class='navActionButtonLabel' and text()='Training']");

	public WebElement getTraining() {
		return getWebwait().until(ExpectedConditions.presenceOfElementLocated(training));
	}

}