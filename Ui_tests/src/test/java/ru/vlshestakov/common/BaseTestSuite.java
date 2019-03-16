package ru.vlshestakov.common;

import ru.vlshestakov.common.Actions;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTestSuite {

    private static final Logger log = LoggerFactory.getLogger(BaseTestSuite.class.getName());

    @BeforeAll
    public static void beforeSuite() {
        Actions.usingSelenide();

    }

    @AfterAll
    public static void afterSuite() {
        log.info("BaseTestSuite::afterSuite()");
        Actions.closeBrowser();
    }

    @BeforeEach
    void beforeEach(TestInfo testInfo) {
        log.info("------------------------ Test method execution: '{}' ------------------------------",
                testInfo.getDisplayName());

    }

    @AfterEach
    void afterEach(TestInfo testInfo) {
        log.info("------------------------ Completion of the test method: {}", testInfo.getDisplayName());

    }
    
}
