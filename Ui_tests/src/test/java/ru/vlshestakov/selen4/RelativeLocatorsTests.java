package ru.vlshestakov.selen4;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

//import static org.openqa.selenium.support.locators.RelativeLocator.withTagName;
import static org.openqa.selenium.support.locators.RelativeLocator.with;


public class RelativeLocatorsTests {
    private static final Logger log4j = LogManager.getLogger();


    private WebDriver driver;

    @BeforeAll
    public void setUp(){
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--window-size=1920,1080");
        driver = new ChromeDriver(options);
        driver.get("https://automationbookstore.dev/");
//        driver.manage().window().fullscreen();
        prnsize();
        //driver.manage().window().fullscreen();
        prnsize();
        prnsize();
        prnsize();
        //driver.manage().window().maximize();
        //driver.manage().window().

    }

    public void prnsize(){
        Dimension dim2 = driver.manage().window().getSize();
        log4j.info("{}, {}", dim2.getHeight(), dim2.getWidth());

    }

//  By.tagName(tagName)


    @AfterAll
    public void close(){
        driver.quit();
    }

    @Test
    public void test_book5_is_left_of_book6_and_below_book1(){
        String id = driver.findElement(with(By.tagName("li"))
                        .toLeftOf(By.id("pid6"))
                        .below(By.id("pid1")))
                .getAttribute("id");
        Assertions.assertEquals(id, "pid5");
    }

    @Test
    public void test_book2_is_above_book6_and_right_of_book1(){
        String id = driver.findElement(with( By.tagName("li"))
                        .above(By.id("pid6"))
                        .toRightOf(By.id("pid1")))
                .getAttribute("id");
        Assertions.assertEquals(id, "pid2");
    }

}
