package ru.vlshestakov.selen4;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MainPage {
    @FindBy(css = "div:nth-child(3) > div > p:nth-child(3) > a")
    public WebElement seeAllToolsButton;
    @FindBy(xpath = "//button[contains(@class, 'main-menu-item__action--button') and text() = 'Developer Tools']")
    public WebElement toolsMenu;
    @FindBy(xpath = "//button[@data-test='site-header-search-action']")
    public WebElement searchButton;
    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
