package ru.vlshestakov.testcases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.qameta.allure.Step;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import ru.vlshestakov.common.Actions;
import ru.vlshestakov.common.BaseTestSuite;
import ru.vlshestakov.common.Page;
import ru.vlshestakov.common.Utils;
import ru.vlshestakov.common.Const;

@Tag("auAmazonTest")
public class AmTest extends BaseTestSuite {

	private static final int DP_INDEX_IN_HREF = 4;
	private static final int ASIN_INDEX_IN_HREF = 5;
	private static final int EXPECTED_ASIN_LENGTH = 10;
	private static final int POSITION_NUMBER = 3;
	private static final int LINE_NUMBER = 2;
	private static final int PAGE_NUMBER = 3;
	private static final Logger log = LoggerFactory.getLogger(AmTest.class.getName());

	@BeforeEach
	public void beforeEach() {
		Actions.prepareBrowser();
		Actions.deleteAllCookies();
	}

	static final String URL = "https://www.amazon.com.au";
	static final int COUNT_PRODUCT_ON_LINE = 3;
	int errCount = 0;
	static final String ERROR_LINE = "===================== Ошибка =============================";

	private void addError() {
		errCount++;
	}

	@Test
	public void testOne() {

		open(URL);
		Page.jumpToTargetСategory();

		log.info("Проверяем страницы...");
		checkPages();
		log.info("==========================================================");

		Page.gotoPageResult(4);
		Page.gotoPageResult(3);

		int result_id = calcResultId (PAGE_NUMBER, LINE_NUMBER, POSITION_NUMBER);
		String expectedASIN = Page.getExpectedASIN(result_id);
		log.info("Переходим на страницу с продуктом");
		Page.selectProduct(result_id);
		log.info("Проверяем продукт, ASIN: " + expectedASIN);
		checkCard(expectedASIN);

		if (errCount != 0) {
			log.error("===================== Тест провален ================================");
			String msg = String.format("===================== количество ошибок: %d ================", errCount);
			log.error(msg);
			fail(msg);
		}
	}
	
	@Step
	private void checkPages() {
		for (int i = 1; i <= 5; i++) {
			Page.gotoPageResult(i);
			checkOnePageResult();
		}

	}
	
	@Step
	private void checkOnePageResult() {
		ElementsCollection coll = Page.getElementsOnCurrentPage();

		for (SelenideElement elment : coll) {

			String title = elment.getAttribute("title");
			String clearedTitle = title.replaceAll("[-(),/\\u00A0]", " ");
			String[] splitFromTitle = clearedTitle.split(" ");

			List<String> listWordsFromTitle = Arrays.asList(splitFromTitle);
			String ref = elment.getAttribute("href");
			assertTrue(ref.startsWith(URL + "/"));
			String[] splitFromHref = ref.split("/");
			if (splitFromHref[DP_INDEX_IN_HREF].equals("dp") == false) {
				log.info(ERROR_LINE);
				log.error("Отсутствует название товара с дефисами в ссылке:");
				log.error("-------- Ссылка на продукт -------");
				log.error(ref);
				log.error("-------- Название продукта в заголовке -------");
				log.error(title);
				addError();
				break;
			}

			String asin = splitFromHref[ASIN_INDEX_IN_HREF];
			if (asin.length() != EXPECTED_ASIN_LENGTH) {
				log.error("Размер ASIN не совпадает, ASIN: " + splitFromHref[ASIN_INDEX_IN_HREF]);
			} else {
				assertTrue(Pattern.matches("[A-Z0-9]+", asin), "неверные символы в ASIN");
			}

			String titleFromHrefWithDefis = splitFromHref[3];
			String[] splitTitleFromHref = titleFromHrefWithDefis.split("-");
			List<String> listWordsFromHref = Arrays.asList(splitTitleFromHref);

			List<String> decodedListWordsFromHref = Utils.decodeList(listWordsFromHref);

			if (listWordsFromTitle.containsAll(decodedListWordsFromHref) == false) {
				log.info(ERROR_LINE);
				log.error("Название продукта в ссылке содержит слова, отсутствующие в заголовке");
				log.error("------- Название продукта в ссылке --------");
				log.error(titleFromHrefWithDefis.toString());
				log.error("-------- Название продукта в заголовке -------");
				log.error(title.toString());

				log.error("-------- Список слов в заголовке-------");
				log.error(listWordsFromTitle.toString());
				log.error("------- Список слов в ссылке --------");
				log.error(decodedListWordsFromHref.toString());
				addError();
			}

		}

	}

	
	/**
	 * 
	 * @param pageNumber     номер страницы, нумерация с 1
	 * @param lineNumber     номер строки на странице, нумерация с 1
	 * @param positionNumber номер позиции на строке, нумерация с 1
	 * @return resultId   идентификатор элемента, нумерация с 0
	 */
	private int calcResultId(int pageNumber, int lineNumber, int positionNumber) {
		return (pageNumber - 1) * Const.COUNT_PRODUCT_ON_PAGE + (lineNumber - 1) * COUNT_PRODUCT_ON_LINE
				+ positionNumber - 1;
	}

	@Step
	private void checkCard(String expectedASIN) {

		log.info("Проверяем ASIN");
		String actualASIN = Page.getActualASIN();
		assertEquals(expectedASIN, actualASIN);

		log.info("Проверяем дату");
		Date dateFirstAvailable = Page.getDateFirstAvailable();
		Date dateNow = new Date();
		assertTrue(dateFirstAvailable.before(dateNow));

		log.info("Проверяем отсутствие умляута");
		Page.checkAbsenceUmlaut();
	}


}
