package com.testing;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GoogleTest {

    private static WebDriver webDriver;
    private final static Logger logger = Logger.getLogger(GoogleTest.class.getName());
    private static GooglePage googlePage;
    private static Properties props = new Properties();
    private static DesiredCapabilities capabilities = new DesiredCapabilities(new FirefoxOptions());

    static {
        try {
            props.load(GoogleTest.class.getClassLoader().getResourceAsStream("config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public static void tearDown() {
        webDriver.quit();
    }


    @Test
    public void clickFirstSearchOutputLink() throws Exception {
        runTest(0);
    }

    @Test
    public void clickSecondSearchOutputLink() throws Exception {
        runTest(1);
    }

    @Test
    public void clickThirdSearchOutputLink() throws Exception {
        runTest(2);
    }

    @Test
    public void clickForthSearchOutputLink() throws Exception {
        runTest(3);
    }

    @Test
    public void clickFifthSearchOutputLink() throws Exception {
        runTest(4);
    }

    @Test
    public void clickSixthSearchOutputLink() throws Exception {
        runTest(5);
    }

    @Test
    public void clickSeventhSearchOutputLink() throws Exception {
        runTest(6);
    }

    @Test
    public void clickEightSearchOutputLink() throws Exception {
        runTest(7);
    }

    @Test
    public void clickNinethSearchOutputLink() throws Exception {
        runTest(8);
    }

    @Test
    public void clickLastSearchOutputLink() throws Exception {
        runTest(9);
    }

    public void runTest(int id) throws Exception {
        webDriver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);

        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS) ;
        webDriver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS) ;
        webDriver.manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS) ;

        googlePage = new GooglePage(webDriver);

        webDriver.manage().window().maximize();
        webDriver.get("https://www.google.com");
        googlePage.fillSearchBox("cloud automated testing");

        assertTrue(googlePage.isSearchOutputLinkPresent());
        assertEquals(googlePage.getSearchOutputLinks().size(), 10);

        try {
            googlePage.clickSearchOutputLink(id);
        } catch (Exception e) {
            logger.warning("test-google-" + id + ".png - " + getErrorMessage(e.getMessage()));
            takeSnapShot(webDriver, "test-google-" + id + ".png");
        }
    }

    private String getErrorMessage(String message) {
        return message.substring(0, message.indexOf("\n"));
    }

    public static void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception{
        TakesScreenshot scrShot =((TakesScreenshot)webdriver);
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(fileWithPath);

        FileUtils.copyFile(SrcFile, DestFile);
    }
}