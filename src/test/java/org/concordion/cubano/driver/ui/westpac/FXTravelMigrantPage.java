package org.concordion.cubano.driver.ui.westpac;

import org.concordion.cubano.AppConfig;
import org.concordion.cubano.driver.BrowserBasedTest;
import org.concordion.cubano.driver.ui.PageObject;
import org.concordion.cubano.driver.web.ChainExpectedConditions;
import org.concordion.cubano.driver.web.PageHelper;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FXTravelMigrantPage extends PageObject<FXTravelMigrantPage> {
	public FXTravelMigrantPage(BrowserBasedTest test) {
		super(test, AppConfig.getInstance().getPropertyAsInteger("webdriver.defaultTimeout", "5"));
	}

	private static final String TITLE = "Currency converter";

	@FindBy(css = "#main")
	WebElement currentPage;

	@FindBy(id = "ConvertFrom")
	WebElement convertFrom;

	@FindBy(id = "Amount")
	WebElement amount;

	@FindBy(id = "ConvertTo")
	WebElement convertTo;

	@FindBy(id = "convert")
	WebElement convert;

	@FindBy(css = "#resultsdiv > em")
	WebElement message;

	@FindBy(css = "#errordiv")
	WebElement errorMessage;

	@Override
	public ExpectedCondition<?> pageIsLoaded(Object... params) {
		return ChainExpectedConditions.with(ExpectedConditions.textToBePresentInElement(currentPage, TITLE))
				.and(ExpectedConditions.frameToBeAvailableAndSwitchToIt("westpac-iframe"));
	}

	public static FXTravelMigrantPage open(BrowserBasedTest test) {
		test.getBrowser().getDriver().navigate().to(AppConfig.getInstance().getBaseUrl());

		return new FXTravelMigrantPage(test);
	}

	public FXTravelMigrantPage convertCurreny(String fromCurrency, String money, String toCurrency) {
		String iframe = "westpac-iframe";

		// switch to the iframe
		if (!PageHelper.getCurrentFrameNameOrId(getBrowser().getDriver()).equalsIgnoreCase(iframe)) {
			getBrowser().getDriver().switchTo().frame(iframe);
		}

		convertFrom.sendKeys(fromCurrency);
		amount.sendKeys(money);
		convertTo.sendKeys(toCurrency);

		return capturePageAndClick(convert, FXTravelMigrantPage.class);
	}

	public String getMessage() {
		capturePage(message, "Currency Converter");
		return message.getText();
	}

	public String getErrorMessage() {
		capturePage(errorMessage);
		return errorMessage.getText();
	}

	@Override
	public void refreshPageElements() {
		// PageFactory.initElements(
		// new PageObjectAwareHtmlElementDecorator(new
		// HtmlElementLocatorFactory(getBrowser().getDriver()), this),
		// this);

		PageFactory.initElements(getBrowser().getDriver(), this);

	}

}
