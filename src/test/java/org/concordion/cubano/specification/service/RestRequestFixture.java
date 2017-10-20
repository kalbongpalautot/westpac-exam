package org.concordion.cubano.specification.service;

import java.io.IOException;

import org.concordion.cubano.specification.ConcordionFixture;

public class RestRequestFixture extends ConcordionFixture {

	public boolean callService() throws IOException {
		return workflow()
				.restExample()
				.getIPAddress() != null;
	}
}
