package org.concordion.cubano;

import org.concordion.cubano.driver.web.config.WebDriverConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppConfig extends WebDriverConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

	private String baseUrl;
	private String searchUrl;
	private String dbSchema;
	private String dbUrl;

	private static class Holder {
		static final AppConfig INSTANCE = new AppConfig();
	}

	public static AppConfig getInstance() {
		return Holder.INSTANCE;
	}

	private AppConfig() {
		super();

		baseUrl = getProperty("baseUrl");
		searchUrl = getProperty("searchUrl");

		// loadProperties();

		// TODO - reinstate?
		// releaseProperties();
	}

	public void logSettings() {
		LOGGER.info("Environment:        " + getEnvironment());
		LOGGER.info("url:                " + getBaseUrl());
		// LOGGER.info("Browser: " + getBrowser());

		if (!getBrowserSize().isEmpty()) {
			LOGGER.info("browserSize:        " + getBrowserSize());
		}
	}

	/*
	 * public void loadProperties() { baseUrl = getProperty("baseUrl"); searchUrl =
	 * getProperty("searchUrl");
	 * 
	 * // dbUrl = getProperty("database.url"); // dbSchema =
	 * getProperty("database.schema");
	 * 
	 * }
	 */

	// Application properties
	public String getBaseUrl() {
		return baseUrl;
	}

	public String getSearchUrl() {
		return searchUrl;
	}

	public String getDatabaseUrl() {
		return dbUrl;
	}

	public String getDatabaseSchema() {
		return dbSchema;
	}
}
