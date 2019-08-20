package com.bowlerocorp.qa.gems_qa.functionalTests.EventManagement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import com.bowlerocorp.qa.gems_automation.utilities.TestDataSource;
import com.bowlerocorp.qa.gems_qa.functionalTests.DashBoard.Gems_AppTest;

public class Activities extends Gems_AppTest {
	public static int i;

	Activities() throws Exception {
		GemsLogin();
		gotoEventManagement();
		getJSE().executeScript("arguments[0].click();", getActivities());

	}

	public void CreateTask() throws InterruptedException {
		getJSE().executeScript("arguments[0].click();", getTask());
		getWebwait().until(ExpectedConditions.frameToBeAvailableAndSwitchToIt("contentIFrame1"));
		System.out.println("TestData: "+TestDataSource.testData(getTaskActivitySheet()));
		getSubject().sendKeys(TestDataSource.testData(getTaskActivitySheet()).get(i)+"_"+getTimeStamp());++i;
		getDescription().sendKeys(TestDataSource.testData(getTaskActivitySheet()).get(i)+"_"+getTimeStamp());++i;
		getJSE().executeScript("arguments[0].click();", getRegarding());
		getJSE().executeScript("arguments[0].click();", getLookUp());
		//getJSE().executeScript("arguments[0].click();", getLookUpMoreRecords());
		getJSE().executeScript("arguments[0].click();", getRegValue(TestDataSource.testData(getTaskActivitySheet()).get(i)));
		getNativeDriver().switchTo().defaultContent();
		getJSE().executeScript("arguments[0].click();", getSaveAndClose());
	}

	public void CreateEmail() {
		getJSE().executeScript("arguments[0].click();", getEmail());
		getJSE().executeScript("arguments[0].click();", getEmailTo());
		getEmailTo().sendKeys(TestDataSource.testData(getEmailActivitySheet()).get(i)+"_"+getTimeStamp());++i;

	}

	public void CreateAppointment() {
	}

	public void CreatePhoneCall() {
	}

	public void CreateLetter() {
	}

	public void CreateFax() {
	}

	public void CreateServiceActivity() {
	}

	public void CreateaCampaignResponse() {
	}

	public void CreateReschedulingFeeApproval() {
	}

	public void DeleteActivity() {
	}

	public static void main(String args[]) throws Exception {
		Activities actvts = new Activities();
		actvts.CreateEmail();
		/*
		 * Select drop=new Select(getNativeDriver().findElement(By.xpath("")));
		 * List<WebElement> dropList=drop.getOptions(); for(WebElement e: dropList) {
		 * System.out.println("Drop values: "+e.getText());}
		 */
		
		
		
		
	}

}
