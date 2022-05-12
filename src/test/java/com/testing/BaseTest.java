package com.testing;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.*;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalTime;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    protected RemoteWebDriver webDriver;
    protected String browser;

    @Parameters("browser")
    @BeforeClass
    public void setUp(@Optional("chrome") String browser) throws MalformedURLException {
        this.browser = browser;
        DesiredCapabilities capabilities = generateBrowserCapabilities(browser);

        webDriver = new RemoteWebDriver(new URL("http://172.0.0.2:4444/wd/hub"), capabilities);

        webDriver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        webDriver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
        webDriver.manage().timeouts().setScriptTimeout(15, TimeUnit.SECONDS);

    }

    private DesiredCapabilities generateBrowserCapabilities(String browser) {
        if(browser.toLowerCase().equals("chrome")) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments(new String[]{"--no-sandbox", "--disable-dev-shm-usage"});
            return new DesiredCapabilities(chromeOptions);
        }
        return new DesiredCapabilities(new FirefoxOptions());
    }

    @AfterClass
    public void tearDown() throws Exception {
        takeSnapShot(webDriver, browser + "-" + getClass().getName());
        webDriver.quit();
    }

    protected String getErrorMessage(String message) {
        return message.substring(0, message.indexOf("\n"));
    }

    protected static void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception{
        TakesScreenshot scrShot =((TakesScreenshot)webdriver);
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(fileWithPath + ".png");

        FileUtils.copyFile(SrcFile, DestFile);
    }



}
