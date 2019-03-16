package ru.vlshestakov.common;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;

import com.codeborne.selenide.ElementsCollection;
import ru.vlshestakov.common.Const;

public class Page {

	
	public static void jumpToTargetСategory() {
		Select selKlass = new Select($(By.id("searchDropdownBox")));
		selKlass.selectByVisibleText("Pet Supplies");
		$(By.className("nav-input")).click();
		$(By.xpath("//li//span[contains(text(),'Cats')]")).should(exist).click();
		$(By.xpath("//span[@class='a-list-item']//span[contains(text(),'Clothing')]")).should(exist).click();
	}

	
	public static void gotoPageResult(int pageNumber) {

		String pagnCur_xpath = String.format("//span[@class='pagnCur'][text()='%d']", pageNumber);
		// проверяем, что уже находимся на нужной странице
		if ($(By.xpath(pagnCur_xpath)).exists())
			return;

		String string_xpath = String.format("//div[@id='pagn']//span[@class='pagnLink']//a[text()='%d']", pageNumber);
		$(By.xpath(string_xpath)).should(exist).click();

		$(By.xpath(pagnCur_xpath)).should(exist);
	}

	public static void selectProduct(int id_result) {
		String xpath_elm = String.format("//*[@id='result_%s']//h2", id_result);
		$(By.xpath(xpath_elm)).should(exist).click();
		$(By.id("detail_bullets_id")).should(exist).scrollTo();
	}

	public static String getExpectedASIN(int id_result) {
		String xpath_elm = String.format("//*[@id='result_%s']", id_result);
		return $(By.xpath(xpath_elm)).should(exist).getAttribute("data-asin");
	}

	public static String getInnerText(int index) {
		String sxpath = String.format("//*[@id='detail_bullets_id']//li[%d]", index);
		String temp = $(By.xpath(sxpath)).getAttribute("innerHTML");
		return temp.substring(temp.lastIndexOf(">") + 1);
	}

	public static ElementsCollection getElementsOnCurrentPage() {
		return $$(
				By.xpath("//li[contains(@id,'result_')]//a[contains(@class,'s-access-detail-page')]"))
				.shouldHaveSize(Const.COUNT_PRODUCT_ON_PAGE);
	}
	
	public static void checkAbsenceUmlaut() {
	assertFalse($(By.xpath("//*[contains(text(),'Ö')]")).exists());
	}
	
	public static  String getActualASIN() {
		return Page.getInnerText(1);
	}

	public static  Date getDateFirstAvailable() {
		String sdate = Page.getInnerText(2);
		return Utils.parseDate(sdate);
	}


}
