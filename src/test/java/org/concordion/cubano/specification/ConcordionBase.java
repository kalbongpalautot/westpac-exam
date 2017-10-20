package org.concordion.cubano.specification;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

import org.concordion.api.AfterExample;
import org.concordion.api.AfterSuite;
import org.concordion.api.ConcordionResources;
import org.concordion.api.extension.Extension;
import org.concordion.api.extension.Extensions;
import org.concordion.api.option.ConcordionOptions;
import org.concordion.api.option.MarkdownExtensions;
import org.concordion.cubano.AppConfig;
import org.concordion.ext.StoryboardExtension;
import org.concordion.ext.TimestampFormatterExtension;
import org.concordion.integration.junit4.ConcordionRunner;
import org.concordion.logback.LogbackAdaptor;
import org.junit.runner.RunWith;

import org.concordion.cubano.driver.BrowserBasedTest;
import org.concordion.cubano.driver.concordion.EnvironmentExtension;
import org.concordion.cubano.driver.http.HttpEasy;
import org.concordion.cubano.driver.web.Browser;

/**
 * Sets up any Concordion extensions or other items that must be shared between index and test fixtures.
 * 
 * NOTE: Test can be run from a Fixture or an Index, any global (@...Suite) methods must be in this class 
 * to ensure the are executed from whichever class initiates the test run.
 */
@RunWith(ConcordionRunner.class)
@ConcordionResources("/customConcordion.css")
@Extensions({ TimestampFormatterExtension.class }) // TODO ADD BACK:, RunTotalsExtension.class })
@ConcordionOptions(markdownExtensions = { MarkdownExtensions.HARDWRAPS, MarkdownExtensions.AUTOLINKS })
public abstract class ConcordionBase implements BrowserBasedTest {
    private static List<Browser> browsers = new ArrayList<Browser>();
    private static ThreadLocal<Browser> browser = new ThreadLocal<Browser>();

    @Extension 
    private final StoryboardExtension storyboard = new StoryboardExtension();

    @Extension
    private final EnvironmentExtension footer = new EnvironmentExtension()
            .withRerunTest("01 - ProcessAndRules-RunSelectedTest")
            .withRerunParameter("token", "ALLOW")
            .withRerunParameter("environment", AppConfig.getInstance().getEnvironment())
            .withRerunParameter("TEST_CLASSNAME", this.getClass().getName().replace(ConcordionBase.class.getPackage().getName() + ".", ""))
            .withRerunParameter("SVN_TAG", EnvironmentExtension.getSubversionUrl())
            .withEnvironment(AppConfig.getInstance().getEnvironment().toUpperCase())
            .withURL(AppConfig.getInstance().getBaseUrl());

// Attempt 1: 
//    @ConcordionScoped(Scope.SPECIFICATION)
//    private ScopedObjectHolder<BrowserListener> browserListenerHolder = new ScopedObjectHolder<BrowserListener>() {
//        @Override
//        protected BrowserListener create() {
//            return new StorycardCreatingBrowserListener(getStoryboard());
//        }
//    };

    static {
        LogbackAdaptor.logInternalStatus();
        AppConfig config = AppConfig.getInstance();
        config.logSettings();

        // Set the proxy rules for all rest requests made during the test run
        HttpEasy.withDefaults()
            .allowAllHosts()
            .trustAllCertificates()
            .baseUrl(config.getBaseUrl());

        if (config.isProxyRequired()) {
            HttpEasy.withDefaults()
                    .proxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(config.getProxyHost(), config.getProxyPort())))
                    .bypassProxyForLocalAddresses(true);

            if (!config.getProxyUser().isEmpty() && !config.getProxyPassword().isEmpty()) {
                HttpEasy.withDefaults().proxyAuth(config.getProxyUser(), config.getProxyPassword());
            }
        }
    }

    @AfterExample
    private final void afterExample() {
        if (browser.get() != null) {
            browser.get().removeScreenshotTaker();
        }
    }

    @AfterSuite
    private final void afterSuite() {
        for (Browser openbrowser : browsers) {
            openbrowser.close();
        }
    }

    @Override
    public Browser getBrowser() {
        if (browser.get() == null) {
            Browser newBrowser = new Browser();
            browser.set(newBrowser);
            browsers.add(newBrowser);
        }

        return browser.get();
    }

    /**
     * @return A reference to the Storyboard extension.
     */
    public StoryboardExtension getStoryboard() {
        return storyboard;
    }
}
