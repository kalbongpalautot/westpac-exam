package org.concordion.cubano.driver.ui.westpac;

import org.concordion.cubano.AppConfig;
import org.concordion.cubano.driver.BrowserBasedTest;
import org.concordion.cubano.driver.ui.PageObject;
import org.concordion.cubano.driver.web.pagefactory.PageObjectAwareHtmlElementDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import ru.yandex.qatools.htmlelements.loader.decorator.HtmlElementLocatorFactory;

public class FXTravelMigrantPage extends PageObject<FXTravelMigrantPage> {
	public FXTravelMigrantPage(BrowserBasedTest test) {
		super(test);
		refreshPageElements();
	}

	@FindBy(id = "ConvertFrom")
	WebElement convertFrom;

	@FindBy(id = "Amount")
	WebElement amount;

	@FindBy(id = "ConvertTo")
	WebElement convertTo;

	@FindBy(id = "convert")
	WebElement convert;

	@Override
	public ExpectedCondition<?> pageIsLoaded(Object... params) {
		return ExpectedConditions.visibilityOf(convert);
	}

	public static FXTravelMigrantPage open(BrowserBasedTest test) {
		test.getBrowser().getDriver().navigate().to(AppConfig.getInstance().getSearchUrl());

		return new FXTravelMigrantPage(test);
	}

	public String convertCurreny(String fromCurrency, String money, String toCurrency) {
		// String fromCurrency = "New Zealand Dollar";
		// String money = "100";
		// String toCurrency = "United States Dollar";

		convertFrom.sendKeys(fromCurrency);
		amount.sendKeys(money);
		convertTo.sendKeys(toCurrency);
		capturePageAndClick(convert, FXTravelMigrantPage.class);

		return null;
	}

	public void refreshPageElements() {
		PageFactory.initElements(
				new PageObjectAwareHtmlElementDecorator(new HtmlElementLocatorFactory(getBrowser().getDriver()), this),
				this);
	}

}
