package com.test.sikuli.demo;

import static com.test.sikuli.demo.SikuliUtil.screenPageDownUntillVisible;
import static com.test.sikuli.demo.SikuliUtil.screenWaitAndClick;

import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FlashUploadViaSikuli {
	WebDriver driver;

	String currentDir = System.getProperty("user.dir");
	private static Logger logger = Logger
			.getLogger("com.test.sikuli.demo.FlashUploadViaSikuli");
	Screen screen;

	@BeforeClass
	public void initBrowser() {
		logger.info("Launching Browser");
		System
				.setProperty("webdriver.chrome.driver",
						"/Users/mubbashir/Documents/Frameworks/selenium/se2/chromedriver");

		driver = new ChromeDriver();

	}

	@BeforeClass
	public void initScreen() {
		screen = new Screen();
	}

	@Test
	public void uploadViaSikuli(ITestContext c) throws FindFailed, InterruptedException {

		driver.get("http://demo.swfupload.org/v220/overlaydemo/index.php");
		driver.switchTo().window(driver.getWindowHandle());
		logger.info("Setting focus to the window");
		((JavascriptExecutor) driver).executeScript("window.focus()");
		driver.findElement(By.id("btnUpload")).click();
		// User either the below of more sikuli oriented one to open file dialog

		// driver.findElement(By.id("btnUpload")).click();
		logger.info("Asking screen to find and click " + currentDir
				+ "/images/SelectFile.png");
		// String currentDir = System.getProperty("user.dir");

		screenWaitAndClick(screen, currentDir + "/img/SelectFile.png", 0);
		screenWaitAndClick(screen, currentDir + "/img/Selectfileto.png", 10);

		screen.type(null, "/Users/mubbashir/Desktop/", 0);
		screenWaitAndClick(screen, currentDir + "/img/G0.png", 10);
		screenPageDownUntillVisible(screen, currentDir+ "/img/mohen_jo_daro.png", 5);
		logger.info("Folder opened and file visible");
		screen.click(currentDir + "/img/mohen_jo_daro.png", 0);
		screen.click(currentDir + "/img/Open-1.png", 0);
		logger.info("File selected and clicked on open to upload it");
		for (int second = 0;; second++) {
			if (second >= 10) Assert.fail("Timeout");
			try { 
				logger.info(" text: "+ driver.findElement(By.cssSelector("div.progressContainer.blue")).getText());
				if ("mohen_jo_daro_preist.jpeg\nComplete.".equals(
						driver.findElement(By.cssSelector("div.progressContainer.blue")).getText()))
					break; 
				} catch (Exception e) {}
			Thread.sleep(1000);
		}



	}

	@AfterClass
	public void shutdownBrowser() {
		driver.close();
	}

}
