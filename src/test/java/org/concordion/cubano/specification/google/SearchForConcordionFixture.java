package org.concordion.cubano.specification.google;

import org.concordion.cubano.driver.ui.google.GoogleSearchPage;
import org.concordion.cubano.specification.ConcordionFixture;

public class SearchForConcordionFixture extends ConcordionFixture {

	public String google(String term, String site) {
		GoogleSearchPage googleSearchPage = workflow().openSearch();

		return googleSearchPage.searchFor(term).getSearchResult(site);
	}
}
