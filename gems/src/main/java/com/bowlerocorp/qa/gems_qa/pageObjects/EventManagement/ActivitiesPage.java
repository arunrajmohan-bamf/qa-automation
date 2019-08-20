package com.bowlerocorp.qa.gems_qa.pageObjects.EventManagement;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.bowlerocorp.qa.gems_qa.pageObjects.DashBoard.GemsHomePage;

public class ActivitiesPage extends GemsHomePage {
	private By activities = By.cssSelector("a#Activities");

	public WebElement getActivities() {
		return getWebwait().until(ExpectedConditions.presenceOfElementLocated(activities));
	}

	private By task = By
			.xpath("//a[@class='ms-crm-Menu-Label']/span[@class='ms-crm-CommandBar-Menu' and contains(text(),'Task')]");

	public WebElement getTask() {
		return getWebwait().until(ExpectedConditions.presenceOfElementLocated(task));
	}

	private By subject = By.xpath("//input[@class='ms-crm-InlineInput ']");

	public WebElement getSubject() {
		return getWebwait().until(ExpectedConditions.visibilityOfElementLocated(subject));
	}

	private By description = By.cssSelector("div#description");

	public WebElement getDescription() {
		return getWebwait().until(ExpectedConditions.elementToBeClickable(description));
	}

	private By regarding = By.xpath("//div[@class='ms-crm-Inline-Value ms-crm-Inline-Lookup']");

	public WebElement getRegarding() {
		return getWebwait().until(ExpectedConditions.visibilityOfElementLocated(regarding));
	}
	private By regardinglabel = By.cssSelector("label#Regarding_label");

	public WebElement getRegardingLabel() {
		return getWebwait().until(ExpectedConditions.elementToBeClickable(regardinglabel));
	}
	private By lookup = By.cssSelector("img#regardingobjectid_i");

	public WebElement getLookUp() {
		return getWebwait().until(ExpectedConditions.visibilityOfElementLocated(lookup));
	}
	private By lookupmorerecords = By.xpath("//span[contains(text(),'Look Up More Records')]");

	public WebElement getLookUpMoreRecords() {
		return getWebwait().until(ExpectedConditions.visibilityOfElementLocated(lookupmorerecords));
	}
	public WebElement getRegValue(String txt) {
		return getWebwait().until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='"+txt+"']")));
	}
	private By save = By.xpath("//a/img[@src='/_imgs/imagestrips/transparent_spacer.gif']");
	public WebElement getSave() {
		return getWebwait().until(ExpectedConditions.elementToBeClickable(save));
	}
	private By email = By.xpath("//span[contains(text(),'Email')]");

	public WebElement getEmail() {
		return getWebwait().until(ExpectedConditions.visibilityOfElementLocated(email));
	}
	private By emailto = By.xpath("//label[@id='To_label']");

	public WebElement getEmailTo() {
		return getWebwait().until(ExpectedConditions.visibilityOfElementLocated(emailto));
	}
	private By saveandclose = By.xpath("//span[text()=' Save & Close ']");

	public WebElement getSaveAndClose() {
		return getWebwait().until(ExpectedConditions.presenceOfElementLocated(saveandclose));
	}
}
