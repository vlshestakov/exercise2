package ru.vlshestakov.common;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import com.codeborne.selenide.WebDriverRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Actions {

	private static final Logger log = LoggerFactory.getLogger(Actions.class.getName());

	public static WebDriver driver() {
		return WebDriverRunner.getWebDriver();
	}

	public static void usingSelenide() {
		createWebDriver();
	}


	private static void createWebDriver() {

		WebDriverManager.chromedriver().setup();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-extensions", "--test-type", "--ignore-certificate-errors");
		WebDriverRunner.setWebDriver(new ChromeDriver(options));
	}

	public static void startPreparations() {
		log.info("BeforeTest");
	}

	public static void prepareBrowser() {
		maximizeBrowser();
	}

	public static void maximizeBrowser() {
		driver().manage().window().maximize();
	}

	public static void deleteAllCookies() {
		driver().manage().deleteAllCookies();
	}

	public static void closeBrowser() {
		if (driver() != null)
			driver().quit();
	}

	public static void refresh() {
		log.info("Refresh the browser's window");
		driver().navigate().refresh();
	}

}
