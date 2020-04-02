package com.testing;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    protected static ThreadLocal<RemoteWebDriver> webDriver = new ThreadLocal<>();
    protected String browser;

    public RemoteWebDriver webDriver() {
        return webDriver.get();
    }

    @Parameters("browser")
    @BeforeClass
    protected void setUp(@Optional("chrome") String browser) throws MalformedURLException {
        DesiredCapabilities capabilities;
        this.browser = browser;
        if(browser.toLowerCase().equals("firefox")) {
            capabilities = new DesiredCapabilities(new FirefoxOptions());
        } else {
            capabilities = new DesiredCapabilities(new ChromeOptions());
        }

//         webDriver.set(new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities));
        webDriver = new RemoteWebDriver(new URL("http://172.18.0.2:4444/wd/hub"), capabilities);

        webDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        webDriver().manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
        webDriver().manage().timeouts().setScriptTimeout(5, TimeUnit.SECONDS);

    }

    @AfterClass
    protected void tearDown() {
        webDriver().quit();
    }

    protected String getErrorMessage(String message) {
        return message.substring(0, message.indexOf("\n"));
    }

    public static void takeSnapShot(WebDriver webdriver, String fileWithPath) throws Exception{
        TakesScreenshot scrShot =((TakesScreenshot)webdriver);
        File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(fileWithPath);

        FileUtils.copyFile(SrcFile, DestFile);
    }



}
