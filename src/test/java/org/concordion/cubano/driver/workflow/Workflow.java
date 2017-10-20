package org.concordion.cubano.driver.workflow;

import org.concordion.cubano.driver.services.ExampleRestApi;
import org.concordion.cubano.driver.ui.google.GoogleSearchPage;

import org.concordion.cubano.driver.BrowserBasedTest;

public class Workflow {
	private final BrowserBasedTest test;

	public Workflow(BrowserBasedTest test) {
		this.test = test;
	}

	public GoogleSearchPage openSearch() {
		return GoogleSearchPage.open(test);
	}

	public ExampleRestApi restExample() {
		return new ExampleRestApi();
	}
}
