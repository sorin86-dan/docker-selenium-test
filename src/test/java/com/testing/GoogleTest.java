package com.testing;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GoogleTest {

    private static WebDriver webDriver;
    private static GooglePage googlePage;

    @BeforeClass
    public static void setUp() throws IOException {
        Properties props = new Properties();
        DesiredCapabilities capabilities = new DesiredCapabilities(new FirefoxOptions());

        props.load(GoogleTest.class.getClassLoader().getResourceAsStream("config.properties"));
        webDriver = new RemoteWebDriver(new URL(props.getProperty("url-firefox")), capabilities);
        googlePage = new GooglePage(webDriver);

        webDriver.manage().window().maximize();
    }

    @AfterClass
    public static void tearDown() {
        webDriver.quit();
    }

    @Test
    public void defaultTest() throws Exception {
        webDriver.get("https://www.google.com");
        googlePage.fillSearchBox("cloud automated testing");
        takeSnapShot(webDriver, "test-google.png") ;

        assertTrue(googlePage.isSearchOutputLinkPresent());
        assertEquals(googlePage.getSearchOutputLinks().size(), 13);
        googlePage.clickSearchOutputLink(0);
        webDriver.navigate().back();
        googlePage.clickSearchOutputLink(5);
        webDriver.navigate().back();
        googlePage.clickSearchOutputLink(6);
        webDriver.navigate().back();
        googlePage.clickSearchOutputLink(7);
        webDriver.navigate().back();
        googlePage.clickSearchOutputLink(8);
        webDriver.navigate().back();
        googlePage.clickSearchOutputLink(9);
        webDriver.navigate().back();
        googlePage.clickSearchOutputLink(10);
        webDriver.navigate().back();
        googlePage.clickSearchOutputLink(11);
        webDriver.navigate().back();
        googlePage.clickSearchOutputLink(12);
        webDriver.navigate().back();
    }

    public static void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception{
        TakesScreenshot scrShot =((TakesScreenshot)webdriver);
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(fileWithPath);

        FileUtils.copyFile(SrcFile, DestFile);
    }
}