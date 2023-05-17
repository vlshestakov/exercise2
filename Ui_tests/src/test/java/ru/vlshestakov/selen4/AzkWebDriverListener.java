package ru.vlshestakov.selen4;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.WebDriverListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Класс подробного логирования selenium
 * Раскомментировать нужные строки при необходимости
 * Дополнительные методы см. в WebDriverListener
 */
public class AzkWebDriverListener implements WebDriverListener {
    private static final Logger log4j = LogManager.getLogger();

    public void beforeGet(WebDriver driver, String url) {
        log4j.info("url-before-get: {}", url);
    }

    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
//        String locator_str = locator == null ? "locator==null" : locator.toString();
//        log4j.info("locator: {}", locator_str);
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
//        String locator_str = locator == null ? "locator==null" : locator.toString();
//        String result_str = result == null ? "WebElement==null" : result.toString();
//        log4j.info("by: {},  result: {}", locator_str, result_str);

    }

    @Override
    public void beforeFindElements(WebDriver driver, By locator) {
//        String locator_str = locator == null ? "locator==null" : locator.toString();
//        log4j.info("locator: {}", locator_str);
    }

    @Override
    public void afterFindElements(WebDriver driver, By locator, List<WebElement> result) {
//        String locator_str = locator == null ? "locator==null" : locator.toString();
//        String result_str = result == null ? "WebElement==null" : result.toString();
//        log4j.info("by: {},  result: {}", locator_str, result_str);
    }

    @Override
    public void beforeFindElement(WebElement element, By locator) {
//        String locator_str = locator == null ? "locator==null" : locator.toString();
//        log4j.info("locator: {}", locator_str);
    }

    @Override
    public void afterFindElement(WebElement element, By locator, WebElement result) {
//        String locator_str = locator == null ? "locator==null" : locator.toString();
//        String result_str = result == null ? "WebElement==null" : result.toString();
//        log4j.info("by: {},  result: {}", locator_str, result_str);
    }

    @Override
    public void beforeFindElements(WebElement element, By locator) {
//        String locator_str = locator == null ? "locator==null" : locator.toString();
//        log4j.info("locator: {}", locator_str);
    }

    @Override
    public void afterFindElements(WebElement element, By locator, List<WebElement> result) {
//        String locator_str = locator == null ? "locator==null" : locator.toString();
//        String result_str = result == null ? "WebElement==null" : result.toString();
//        log4j.info("by: {},  result: {}", locator_str, result_str);
    }

    @Override
    public void beforeExecuteScript(WebDriver driver, String script, Object[] args) {
        log4j.info("script: {}, args: {}", script, args == null ? "args_null:" : args.toString());
    }

    @Override
    public void afterExecuteScript(WebDriver driver, String script, Object[] args, Object result) {
        log4j.info("result: {}", result == null ? "result_null" : result.toString());
        logBrowserLogContent(driver);
    }

    @Override
    public void onError(Object target, Method method, Object[] args, InvocationTargetException e) {
        log4j.info("target: {}, method: {}, args: {}, exception: {}",
                target == null ? "target_null" : target.toString(),
                method.getName(),
                args == null ? "args_null:" : args.toString(),
                e.getMessage()
        );
    }

    @Override
    public void afterAnyWebDriverCall(WebDriver driver, Method method, Object[] args, Object result) {
//        if (logBrowserLogContent(driver)) {
//            log4j.info("method: {}, args: {}, result: {}",
//                    method.getName(),
//                    args == null ? "args_null:" : args.toString(),
//                    result == null ? "result_null" : result.toString()
//            );
//        }
    }

    public boolean logBrowserLogContent(WebDriver drv) {
        boolean result = false;
        if (((RemoteWebDriver) drv).getSessionId() != null) {
            List<LogEntry> list = drv.manage().logs().get(LogType.BROWSER).getAll();
            if (list.size() != 0) {
                log4j.info("-----------------------------------------------------------------------------------------------");
                for (LogEntry logEntry : list) {
                    log4j.info("logEntry: {}", logEntry);
                }
                log4j.info("================================================================================================");
                result = true;
            }
        }
        return result;
    }

}
