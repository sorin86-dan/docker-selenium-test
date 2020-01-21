package com.testing;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class GooglePage extends PageFactory {

    private WebDriver webDriver;
    private final static Logger logger = Logger.getLogger(GooglePage.class.getName());

    private final static String SEARCH_BOX = "//input[@name='q']";
    private final static String SEARCH_OUTPUT_LINK_G = "//h3[@class='LC20lb']";

    @FindBy(how = How.XPATH, using = SEARCH_BOX)
    private WebElement searchBox;

    @FindBy(how = How.XPATH, using = SEARCH_OUTPUT_LINK_G)
    private List<WebElement> searchOutputLinkG;

    public GooglePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);
    }

    public void fillSearchBox(String text) throws InterruptedException {
        logger.info("Filling search box with '" + text + "'");
        searchBox.clear();
        searchBox.sendKeys(text);
        searchBox.submit();
        Thread.sleep(1000);
    }

    public boolean isSearchOutputLinkPresent() throws InterruptedException {
        logger.info("Checking if search output link is present");
        Thread.sleep(1000);
        return searchOutputLinkG.size() > 0 && searchOutputLinkG.get(0).isDisplayed();
    }

    public String getSearchOutputLink(int current) throws InterruptedException {
        logger.info("Retrieving the content of search output link: " + searchOutputLinkG.get(current).getText());
        Thread.sleep(1000);
        return searchOutputLinkG.get(current).getText();
    }

    public void clickSearchOutputLink(int current) throws InterruptedException {
        logger.info("Size: " + searchOutputLinkG.size());
        logger.info("Clicking search output link '" + searchOutputLinkG.get(current).getText() + "'");
        Thread.sleep(1000);
        searchOutputLinkG.get(current).click();
    }

    public List<String> getSearchOutputLinks() throws InterruptedException {
        logger.info("Retrieving search output links");
        Thread.sleep(1000);
        return searchOutputLinkG.stream().map(el -> el.getText()).collect(Collectors.toList());
    }
}
