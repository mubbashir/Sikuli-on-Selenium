package com.test.sikuli.demo;

import static com.test.sikuli.demo.SikuliUtil.screen;
import static com.test.sikuli.demo.SikuliUtil.screenPageDownUntillVisible;
import static com.test.sikuli.demo.SikuliUtil.screenWaitAndClick;

import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.sikuli.script.FindFailed;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class FlashUploadViaSikuli {

    WebDriver driver;

    private static Logger logger = Logger.getLogger("com.test.sikuli.demo.FlashUploadViaSikuli");


    @BeforeClass
    public void initBrowser() {
        logger.info("Launching Browser");
        driver = new FirefoxDriver();

    }



    @Test
    public void uploadViaSikuli(ITestContext context) throws FindFailed, InterruptedException {
        String imageDir = context.getCurrentXmlTest().getParameter("sikuli.image.dir");
        String imageToUploadDir = context.getCurrentXmlTest().getParameter("sikuli.image.to.upload.dir");

        String elementToValidate = context.getCurrentXmlTest().getParameter("selenium.element.to.validate.css.locator");
        String elementToValidateText = context.getCurrentXmlTest().getParameter("selenium.element.to.validate.text");
        int validationWaitTimeOut = Integer.parseInt(context.getCurrentXmlTest().getParameter("selenium.validation.time.out"));


        driver.get(context.getCurrentXmlTest().getParameter("selenium.site.under.test"));

        driver.switchTo().window(driver.getWindowHandle());
        logger.info("Setting focus to the window");
        ((JavascriptExecutor) driver).executeScript("window.focus()");
        driver.findElement(By.id("btnUpload")).click();
        // User either the below of more sikuli oriented one to open file dialog

        // driver.findElement(By.id("btnUpload")).click();
        logger.info("Asking screen to find and click " + imageDir + "SelectFile");
        // String currentDir = System.getProperty("user.dir");

        screenWaitAndClick(imageDir + "/" + context.getCurrentXmlTest().getParameter("sikuli.file.upload.element"), 0);
        screenWaitAndClick(imageDir + "/" + context.getCurrentXmlTest().getParameter("sikuli.browse.file.dialog"), 10);

        screen.type(null, imageToUploadDir, 0);
        screenWaitAndClick(imageDir + "/" + context.getCurrentXmlTest().getParameter("sikuli.browse.file.dialog.go.button"), 10);
        screenPageDownUntillVisible(imageDir + "/" + context.getCurrentXmlTest().getParameter("sikuli.file.to.select"), 5);
        logger.info("Folder opened and file visible");
        screen.click(imageDir + "/" + context.getCurrentXmlTest().getParameter("sikuli.file.to.select"), 0);
        screen.click(imageDir + "/" + context.getCurrentXmlTest().getParameter("sikuli.browse.file.dialog.open.button"), 0);
        logger.info("File selected and clicked on open to upload it");
        logger.info("Validating Element: " + elementToValidate + " To have text: " + elementToValidateText);
        String textOnElement = "";
        for (int second = 0;; second++) {
            if (second >= validationWaitTimeOut) Assert.fail("Timeout");
            try {
                textOnElement = driver.findElement(By.cssSelector(elementToValidate)).getText();
                logger.info(" text on element: " + textOnElement + ", is to be matched with " + elementToValidateText + " = "
                        + textOnElement.matches(elementToValidateText));
                if (textOnElement.matches(elementToValidateText)) break;
            } catch (Exception e) {
            }
            Thread.sleep(1000);
        }

    }

    @AfterClass
    public void shutdownBrowser() {
        driver.close();
    }

}
