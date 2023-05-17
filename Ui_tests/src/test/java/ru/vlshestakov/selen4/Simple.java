package ru.vlshestakov.selen4;

import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Простой тест selenium (без АЦК)
 */
public class Simple {

    private static final Logger log4j = LogManager.getLogger();

    private static final String LOGIN_FIELD = "re_Login_userNameField-inputEl";
    private static final String PASSWORD_FIELD = "re_Login_passwordField-inputEl";
    private static final String RE_LOGIN_SUBMIT_BUTTON_BTN = "re_Login_Submit_Button-btnEl";


    static private RemoteWebDriver driver;
    RemoteWebDriver originalDriver;
    private MainPage mainPage;


    //String selenoidUrl = "http://test:123456@srv-at-selenoid-test6:8888/wd/hub";

    String selenoidUrl = "http://172.24.25.205:4444/wd/hub"; //  selenoid srv-doc-at-null

    @BeforeAll
    protected void beforeAll() throws MalformedURLException {
//        String browserVersion = "111.0";
        String browserVersion = "109.0";
//        String browserVersion = "88.0";
//        String browserVersion = "93.0"; // ok
        //String browserVersion = "96.0"; // ok
        //String browserVersion = "97.0"; // ok
//        String browserVersion = "98.0"; // ok
//        String browserVersion = "99.0"; // err
//        String browserVersion = "777.0"; // err
        ChromeOptions options = new ChromeOptions();
        options.setBrowserVersion(browserVersion);
        options.setScriptTimeout(Duration.ofMinutes(5));
        options.setImplicitWaitTimeout(Duration.ofMinutes(5));
        options.setPageLoadTimeout(Duration.ofMinutes(5));
        //options.addArguments("--headless");
        //options.addArguments("--no-sandbox");
        //options.addArguments("--disable-dev-shm-usage");
        //options.addArguments("--shm-size=\"2g\"");
        options.addArguments("--window-size=1920,1080");

        Map<String, Object> selenoidOptions = new HashMap<>();
        selenoidOptions.put("version", browserVersion);
        selenoidOptions.put("enableVNC", true);
        selenoidOptions.put("enableLog", true);
        selenoidOptions.put("name", "123456");
        selenoidOptions.put("sessionTimeut", "5m");

        Map<String, Object> labelsOptions = new HashMap<>();
        labelsOptions.put("manual", "true");
        selenoidOptions.put("labels", labelsOptions);

        options.setCapability("selenoid:options", selenoidOptions);
        URL url = URI.create(selenoidUrl).toURL();
        originalDriver = new RemoteWebDriver(url, options);
//        RemoteWebDriver originalDriver = new RemoteWebDriver(
//                URI.create("http://srv-at-selenoid-test6:8090/wd/hub").toURL(), options);

        driver = new EventFiringDecorator<>(RemoteWebDriver.class, new AzkWebDriverListener()).decorate(originalDriver);
        log4j.info("Используется web-драйвер для selenium grid или selenoid-docker: {}", driver.getCapabilities().getBrowserVersion());

    }

    @AfterAll
    public void tearDown() {
        SessionId sesId = driver.getSessionId();
        SessionId sesIdOrig = originalDriver.getSessionId();
//        sesId.
        if ( sesIdOrig != null) {
            originalDriver.quit();
        }
    }

    @Attachment(value = "Page screenshot", type = "image/png")
    public static byte[] makeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }


    @Test
    @DisplayName("testazk")
    public void testAZK() {
        String testedUrl = "http://172.24.25.205:7053/gz/";  // gz srv-doc-at-null
//        String testedUrl = "http://srv-dopterm51.bft.local/";  //
//        String testedUrl = "http://172.24.16.119/";  //
//        String testedUrl = "http://localhost:8180/";  // local gz
        driver.get(testedUrl);
//        Pause.seconds(20);
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);

        WebElement loginName = wait.until(driver -> {
            return driver.findElement(By.id(LOGIN_FIELD));
        });
        makeScreenshot();

        WebElement passwField = driver.findElement(By.id(PASSWORD_FIELD));
        WebElement buttonSubmit = driver.findElement(By.id(RE_LOGIN_SUBMIT_BUTTON_BTN));
        loginName.sendKeys("admin");
        passwField.sendKeys("admin");
        log4j.info("buttonSubmit.click()");
        buttonSubmit.click();
        Pause.seconds(20);
        logBrowserLogContent(driver);
//        Pause.seconds(20);
        makeScreenshot();
        //driver.executeScript("try { return Ext.isReady } catch (exception) { return false }");
        WebElement el2 = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/div/div[3]/div[3]/div/div[2]/table[1]/tbody/tr/td/div/img[1]"));
        el2.click();
        Pause.seconds(10);
        makeScreenshot();
        WebElement el3 = driver.findElement(By.xpath("/html/body/div[2]/div[2]/div/div/div[3]/div[3]/div/div[2]/table[3]/tbody/tr/td/div/span"));
        el3.click();
        Pause.seconds(10);
        makeScreenshot();
    }



    @Test
    public void search() {
        String testedUrl = "https://www.jetbrains.com/";
        driver.get(testedUrl);
        mainPage = new MainPage(driver);
        Pause.seconds(20);
        mainPage.searchButton.click();

        WebElement searchField = driver.findElement(By.cssSelector("[data-test='search-input']"));
        searchField.sendKeys("Selenium");

        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);

        WebElement submitButton = wait.until(driver -> {
            return driver.findElement(By.cssSelector("button[data-test='full-search-button']"));
        });

        submitButton.click();

        WebElement searchPageField = driver.findElement(By.cssSelector("input[data-test='search-input']"));
        assertEquals("Selenium", searchPageField.getAttribute("value"));
    }

    public boolean logBrowserLogContent(WebDriver drv) {
        List<LogEntry> list = drv.manage().logs().get(LogType.BROWSER).getAll();
        boolean result = false;
        if (list.size() != 0) {
            log4j.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            for (LogEntry logEntry : list) {
                log4j.info("logEntry: {}", logEntry);
            }
            log4j.info("*********************************************************************************");
            result = true;
        }
        return result;
    }
}


