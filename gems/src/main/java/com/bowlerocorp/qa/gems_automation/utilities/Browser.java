package com.bowlerocorp.qa.gems_automation.utilities;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.google.common.io.Files;

@SuppressWarnings("unused")
public class Browser extends getProperties {
	public static WebDriver driver;
	protected static Logger logger = Logger.getLogger(Browser.class);

	public Browser() {
		super();
		loadProperties();
	}

	public WebDriver getBrowser(String browserName) throws InterruptedException {
		try {
			if (browserName.equalsIgnoreCase("Firefox")) {
				loadProperties();
				System.setProperty(getFirefoxDriver(), getFirefoxDriverPath());
				driver = new FirefoxDriver();
				logger.info("Firefox Browser Launched Successfully!");
			} else if (browserName.equalsIgnoreCase("Chrome")) {
				System.setProperty(getChromeDriver(), getChromeDriverPath());
				driver = new ChromeDriver();
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
				//driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);



				logger.info("Chrome Browser Launched Successfully!");
			} else if (browserName.equalsIgnoreCase("IE")) {
				System.setProperty(getIEDriver(), getIEDriverPath());
				driver = new InternetExplorerDriver();
				logger.info("IE Browser Launched Successfully!");
			}
		} catch (WebDriverException e) {
			logger.error("Exception in getBrowser() - ", e);
			System.out.println(e.getMessage());
		}
		return driver;
	}

	public static WebDriver getNativeDriver() {
		return driver;
	}

	public static Actions getBuilder() {
		return new Actions(getNativeDriver());
	}

	public static void AcceptAlert() {
		getNativeDriver().switchTo().alert().accept();
	}

	public JavascriptExecutor getJSE() {
		return (JavascriptExecutor) driver;
	}

	public WebDriverWait getWebwait() {
		return new WebDriverWait(getNativeDriver(), 20);
	}

	/*
	 * public WebDriverWait getFluentWait(String[] args) throws InterruptedException
	 * { FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
	 * //Wait<WebDriver> wait = new FluentWait<WebDriver>(driver);
	 * wait.pollingEvery(250, TimeUnit.MILLISECONDS); wait.withTimeout(2,
	 * TimeUnit.MINUTES); wait.ignoring(NoSuchElementException.class); //make sure
	 * that this exception is ignored WebElement foo = wait.until(new
	 * Function<WebDriver,WebElement>() {
	 * 
	 * public WebElement apply(WebDriver driver) {
	 * 
	 * return driver.findElement(By.id("foo"));
	 * 
	 * }
	 * 
	 * });
	 * 
	 * }
	 * 
	 * public void scrollToMobileElement(MobileElement E){
	 * getMJSE().executeScript("arguments[0].scrollIntoView(true);", E); }
	 */

	public WebDriver BrowserLaunch(String BrowserName, String Url) throws MalformedURLException, InterruptedException {
		// if(driver==null){getBrowser(BrowserName);}else{getNativeDriver();}
		if (isBrokenlink(new URL(Url)) != null) {
			System.out.println("Url: " + Url);
			getBrowser(BrowserName).navigate().to(Url);
			getNativeDriver().manage().window().maximize();
			getNativeDriver().manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			getNativeDriver().manage().timeouts().setScriptTimeout(20, TimeUnit.SECONDS);
			getNativeDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

			logger.info("Navigating to the Application URL!");
		}
		return driver;

		/*
		 * driver.manage().timeouts().setScriptTimeout(10, TimeUnit.SECONDS);
		 * driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		 * driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		 */

		/*
		 * WebDriverWait wait=new WebDriverWait(driver,60); WebElement
		 * e=wait.until(ExpectedConditions.presenceOfElementLocated(HP.getRGLogo()));
		 * Actions action=new Actions(driver); action.moveToElement(e); WebElement
		 * tt=wait.until(ExpectedConditions.presenceOfElementLocated(HP.getRGLogotooltip
		 * ())); if(tt.isDisplayed())
		 * Assert.assertEquals(e.getText(),HP.getRGLogotooltip());
		 */
	}

	public static String isBrokenlink(URL url) {
		String response = "";
		HttpURLConnection connection;
		try {
			connection = (HttpURLConnection) url.openConnection();
			connection.connect();
			response = connection.getResponseMessage();
			if (response != null) {
				logger.info("Application URL is working!");
				System.out.println("Application URL is working!");
			}
			connection.disconnect();
		} catch (IOException e) {
			logger.info("Exception in Connecting to URL - ", e);
			e.printStackTrace();
		}
		return response;
	}

	public static void takeSnapShot1(WebDriver webdriver, String fileWithPath) throws Exception {
		TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile = new File(fileWithPath);
		Files.copy(SrcFile, DestFile);
	}

	public File getQAAutomationFilePath() {
		File dir1 = new File(getOutputFilePath() + File.separator + "GemsAutomationReports" + File.separator
				+ LocalDate.now().getDayOfMonth());
		if (!dir1.exists()) {
			dir1.mkdirs();
		} else if (dir1.exists()) {
		}
		return dir1;
	}

	public File getQAAutomationScreenPath() {
		File dir2 = new File(getOutputFilePath() + File.separator + "GemsAutomationScreens" + File.separator
				+ LocalDate.now().getDayOfMonth());
		if (!dir2.exists()) {
			dir2.mkdirs();
		} else if (dir2.exists()) {
		}
		return dir2;
	}

	public URI TakesnapShot() throws Exception {
		TakesScreenshot scrShot = ((TakesScreenshot) driver);
		File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
		File Screen = new File(getQAAutomationScreenPath() + File.separator + "Screen_"
				+ new java.util.Date().toString().replaceAll("[\\W+]", "") + ".png");
		// System.out.println("ScreenPath: "+Screen.toString());
		Files.copy(SrcFile, Screen);
		// SendEmail.EmailwithAttachment(file.toString());
		// }

		return Screen.toURI();
	}
	/*
	 * public String TakeSnapShot(MobileElement mE) throws Exception{
	 * TakesScreenshot scrShot =((TakesScreenshot)getAWebwait().
	 * .until(ExpectedConditions.visibilityOf(mE))); File
	 * SrcFile=scrShot.getScreenshotAs(OutputType.FILE); //File
	 * SrcFile=scrShot.getScreenshotAs(OutputType.FILE); String screenshotLocation =
	 * System.getProperty("user.dir")+File.separator+"screenshots"+File.separator+
	 * LocalDate.now().getDayOfMonth(); String
	 * newScreenshot=screenshotLocation+File.separator+new
	 * java.util.Date().toString().replaceAll("[\\W+]","")+".png"; String
	 * screenshotLocation=System.getProperty("user.dir")+File.separator+
	 * "screenshots"+File.separator+LocalDate.now().getDayOfMonth(); File dir =new
	 * File(screenshotLocation); if (!dir.exists()){dir.mkdir();}else
	 * if(dir.exists()){} String newScreenshot=screenshotLocation+File.separator+new
	 * java.util.Date().toString().replaceAll("[\\W+]","")+".png"; File file=new
	 * File(newScreenshot);
	 * //File.separator+calendar.get(Calendar.DATE)+File.separator+calendar.get(
	 * Calendar.DAY_OF_WEEK)+File.separator+calendar.get(Calendar.HOUR_OF_DAY)+File.
	 * separator+calendar.get(Calendar.MINUTE)+File.separator+calendar.get(Calendar.
	 * SECOND)+".png"); //if(!file.exists()){file.createNewFile();} //File
	 * DestFile=new File(file); Files.copy(SrcFile,file);
	 * //SendEmail.EmailwithAttachment(file.toString()); // } return
	 * file.toString(); }
	 */

	public String getElementScreenshot(WebElement element) throws IOException {
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		BufferedImage fullImg = ImageIO.read(screenshot);
		Point point = element.getLocation();
		System.out.println("point:" + point);
		int getX = point.getX();
		System.out.println("point.getX" + getX);
		int getY = point.getY();
		System.out.println("point.getY" + getY);
		int eleWidth = element.getSize().getWidth();
		System.out.println("eleWidth" + eleWidth);
		int eleHeight = element.getSize().getHeight();
		System.out.println("eleHeight" + eleHeight);
		BufferedImage eScreenshot = fullImg.getSubimage(getX, getY, 0, 0);
		ImageIO.write(eScreenshot, "png", screenshot);
		String screenshotLocation = System.getProperty("user.dir") + File.separator + "screenshots" + File.separator
				+ LocalDate.now().getDayOfMonth();
		File fscreenshotLocation = new File(screenshotLocation);
		String newScreenshot = screenshotLocation + File.separator
				+ new java.util.Date().toString().replaceAll("[\\W+]", "") + ".png";
		File fnewScreenshot = new File(newScreenshot);
		if (!fscreenshotLocation.exists()) {
			fscreenshotLocation.mkdir();
		}
		Files.copy(screenshot, fnewScreenshot);
		return newScreenshot;
	}

	public void scrollWebPageDown() {
		// jse.executeScript("window.scrollBy(0,750)","");
		getJSE().executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public void scrollWebPageUp() {
		getJSE().executeScript("window.scrollTo(0, 0)");
	}

	public void scrollToElement(WebElement E) throws InterruptedException {
		getJSE().executeScript("arguments[0].scrollIntoView(true);", E);
		Thread.sleep(500);
	}

	public void scrollToElement(Select E) {
		getJSE().executeScript("arguments[0].scrollIntoView(true);", E);
	}

	public void highlightElement(WebDriver driver, WebElement element) {
		if (element.isDisplayed()) {
			getJSE().executeScript("arguments[0].setAttribute('style','border:solid 2px red')", element);
		} else
			System.out.println("Element invisible, can't highlight");
	}

	public void highlightElement(WebDriver driver, By by) {
		if (getNativeDriver().findElement(by).isDisplayed()) {
			getJSE().executeScript("arguments[0].setAttribute('style','border:solid 2px red')",
					getNativeDriver().findElement(by));
		} else
			System.out.println("Element invisible, can't highlight");
	}

	public String getTimeStamp() {
		return new java.util.Date().toString().replaceAll("[\\W+]", "").toString();
	}

	public void captureElementImage() throws MalformedURLException, IOException {
		String src = driver.findElement(By.cssSelector("img#imgCaptcha")).getAttribute("src");
		System.out.println("src - " + src);
		URL url = new URL(src);
		driver.get(src);

		RenderedImage image = null;
		image = ImageIO.read(url);
		// URL url = new URL(imageUrl);
		InputStream in = new BufferedInputStream(url.openStream());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int n = 0;
		while (-1 != (n = in.read(buf))) {
			out.write(buf, 0, n);
		}
		out.close();
		in.close();
		byte[] response = out.toByteArray();
		String screenshotLocation = System.getProperty("user.dir") + File.separator + "screenshots" + File.separator
				+ LocalDate.now().getDayOfMonth();
		File fscreenshotLocation = new File(screenshotLocation);
		String newScreenshot = screenshotLocation + File.separator
				+ new java.util.Date().toString().replaceAll("[\\W+]", "") + ".png";
		File fnewScreenshot = new File(newScreenshot);
		if (!fscreenshotLocation.exists()) {
			fscreenshotLocation.mkdir();
		}
		// Files.copy(screen,fnewScreenshot);

		FileOutputStream fos = new FileOutputStream(fnewScreenshot);
		fos.write(response);
		fos.close();
	}

	void captureComponent(Component component) {
		java.awt.Rectangle rect = component.getBounds();

		try {
			String format = "png";
			String fileName = component.getName() + "." + format;
			BufferedImage captureImage = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
			component.paint(captureImage.getGraphics());

			ImageIO.write(captureImage, format, new File(fileName));

			System.out.printf("The screenshot of %s was saved!", component.getName());
		} catch (IOException ex) {
			System.err.println(ex);
		}
	}

	public String getAlphaNumericString(int n) {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz"
				+ "~!@#$%^&*()";
		StringBuilder sb = new StringBuilder(n);
		for (int i = 0; i < n; i++) {
			int index = (int) (AlphaNumericString.length() * Math.random());
			sb.append(AlphaNumericString.charAt(index));
		}
		return sb.toString();
	}

	public boolean retryingFindClick(WebElement e) {
		boolean result = false;
		int attempts = 0;
		while (attempts < 2) {
			try {
				e.click();
				result = true;
				break;
			} catch (StaleElementReferenceException e1) {
			} catch (ElementNotVisibleException e1) {
			}
			attempts++;
		}
		return result;
	}

	protected boolean isElementPresent(WebElement e) {
		try {
			return true;
		} catch (NoSuchElementException ex) {
			return false;
		}
	}

	public static StringBuilder getRandom(int u, int l) {
		// int l=getRandom(1000000000,999999999);
		Random rndm = new Random();
		StringBuilder sbr = new StringBuilder();
		int res = rndm.nextInt(u) + l;
		return sbr.append(res);
	}

	public static void switchWindowHandle() {
		String winHandle = getNativeDriver().getWindowHandle();
		for (String s : getNativeDriver().getWindowHandles()) {
			if (!s.equalsIgnoreCase(winHandle)) {
				getNativeDriver().switchTo().window(s);
			}
		}
	}

	public static RemoteWebDriver getRemoteDriver(String browser) throws MalformedURLException {

		// return new RemoteWebDriver(new URL("http://35.198.240.138:5556/wd/hub"),
		// getBrowserCapabilities(browser));
		FirefoxOptions options = new FirefoxOptions();
		String strFFBinaryPath = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
		options.setBinary(strFFBinaryPath);
		// driver = new FirefoxDriver(options);
		FirefoxProfile profile = new FirefoxProfile();
		profile.setPreference("dom.ipc.plugins.enabled.libflashplayer.so", false);
		options.setProfile(profile);
		return new RemoteWebDriver(new URL("http://35.198.240.138:5556/wd/hub"), options);

	}

}
