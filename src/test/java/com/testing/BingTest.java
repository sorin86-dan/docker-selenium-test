package com.testing;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
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

public class BingTest {

    private static WebDriver webDriver;
    private static BingPage bingPage;

    @BeforeClass
    public static void setUp() throws IOException {
        Properties props = new Properties();
        DesiredCapabilities capabilities = new DesiredCapabilities(new ChromeOptions());

        props.load(BingTest.class.getClassLoader().getResourceAsStream("config.properties"));
        webDriver = new RemoteWebDriver(new URL(props.getProperty("url-chrome")), capabilities);
        bingPage = new BingPage(webDriver);

        webDriver.manage().window().maximize();
    }

    @AfterClass
    public static void tearDown() {
        webDriver.quit();
    }

    @Test
    public void defaultTest() throws Exception {
        webDriver.get("https://www.bing.com");
        bingPage.fillSearchBox("cloud automated testing");
        takeSnapShot(webDriver, "test-bing.png") ;

        assertTrue(bingPage.isSearchOutputLinkPresent());
        assertEquals(bingPage.getSearchOutputLinks().size(), 10);
        bingPage.clickSearchOutputLink(0);
        webDriver.navigate().back();
        bingPage.clickSearchOutputLink(1);
        webDriver.navigate().back();
        bingPage.clickSearchOutputLink(2);
        webDriver.navigate().back();
        bingPage.clickSearchOutputLink(3);
        webDriver.navigate().back();
        bingPage.clickSearchOutputLink(4);
        webDriver.navigate().back();
        bingPage.clickSearchOutputLink(5);
        webDriver.navigate().back();
        bingPage.clickSearchOutputLink(6);
        webDriver.navigate().back();
        bingPage.clickSearchOutputLink(7);
        webDriver.navigate().back();
        bingPage.clickSearchOutputLink(8);
        webDriver.navigate().back();
        bingPage.clickSearchOutputLink(9);
        webDriver.navigate().back();
    }

    public static void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception{
        TakesScreenshot scrShot =((TakesScreenshot)webdriver);
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(fileWithPath);

        FileUtils.copyFile(SrcFile, DestFile);
    }

}