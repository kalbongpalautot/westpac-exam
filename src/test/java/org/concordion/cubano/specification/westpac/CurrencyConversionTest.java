package org.concordion.cubano.specification.westpac;

import org.concordion.cubano.specification.ConcordionFixture;

public class CurrencyConversionTest extends ConcordionFixture {

	public String convertCurrency(String fromCurreny, String amount, String toCurrency) {
		return workflow().openCurrencyConverter().convertCurreny(fromCurreny.trim(),
				amount.toString(), toCurrency.trim());
	}
}
