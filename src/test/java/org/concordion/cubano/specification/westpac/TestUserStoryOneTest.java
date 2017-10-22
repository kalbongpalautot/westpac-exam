package org.concordion.cubano.specification.westpac;

import org.concordion.cubano.driver.ui.westpac.FXTravelMigrantPage;
import org.concordion.cubano.specification.ConcordionFixture;

public class TestUserStoryOneTest extends ConcordionFixture {
	private FXTravelMigrantPage fxTravelMigrantPage;

	public void convertCurrencyWithNoAmount() {

		fxTravelMigrantPage = workflow().openCurrencyConverter();
		fxTravelMigrantPage.convertCurreny("New Zealand Dollar", "", "United States Dollar");
	}

	public String getErrorMessage() {
		return fxTravelMigrantPage.getErrorMessage();
	}
}
