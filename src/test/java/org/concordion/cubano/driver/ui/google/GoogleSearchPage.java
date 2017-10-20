package org.concordion.cubano.driver.ui.google;

import java.util.List;

import org.concordion.cubano.AppConfig;
import org.concordion.cubano.driver.BrowserBasedTest;
import org.concordion.cubano.driver.ui.PageObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class GoogleSearchPage extends PageObject<GoogleSearchPage> {
	public GoogleSearchPage(BrowserBasedTest test) {
		super(test);
	}

	// @FindBy(id = "gs_lc0")
	@FindBy(css = "#lst-ib")
	WebElement searchBox;

	@FindBy(css = "#gs_lc0 input")
	WebElement searchField;

	@FindBy(name = "btnK")
	WebElement searchButton;

	@FindBy(name = "btnG")
	WebElement searchIconButton;

	// @FindBy(name = "div.g")
	// List<SearchResult> searchResults;

	@Override
	public ExpectedCondition<?> pageIsLoaded(Object... params) {
		return ExpectedConditions.visibilityOf(searchBox);
		// return ExpectedConditions.urlMatches("google");
	}

	public static GoogleSearchPage open(BrowserBasedTest test) {
		test.getBrowser().getDriver().navigate().to(AppConfig.getInstance().getSearchUrl());

		return new GoogleSearchPage(test).newInstance(GoogleSearchPage.class);
	}

	public GoogleSearchPage searchFor(String term) {
		searchField.sendKeys(term);

		WebElement button;

		if (searchButton.isDisplayed()) {
			button = searchButton;
		} else {
			button = searchIconButton;
		}

		capturePage(button);
		button.click();

		waitUntil(ExpectedConditions.numberOfElementsToBeMoreThan(By.cssSelector("div.g"), 2), 3);

		return this;
	}

	public String getSearchResult(String link) {
		List<SearchResult> searchResults = getBrowser().getHtmlElementsLoader(this).findElements(SearchResult.class,
				By.cssSelector("div.g"));

		for (SearchResult searchResult : searchResults) {
			String url = searchResult.url.getText();

			if (url.startsWith(link)) {
				if (url.endsWith("/")) {
					url = url.substring(0, url.length() - 1);
				}

				return url;
			}
		}

		return null;
	}
}
