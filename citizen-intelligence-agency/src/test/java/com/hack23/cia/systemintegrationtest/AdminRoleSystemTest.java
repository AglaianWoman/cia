/*
 * Copyright 2014 James Pether Sörling
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *	$Id$
 *  $HeadURL$
*/
package com.hack23.cia.systemintegrationtest;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;

import com.hack23.cia.model.internal.application.data.impl.DataAgentOperation;
import com.hack23.cia.model.internal.application.data.impl.DataAgentTarget;
import com.hack23.cia.web.impl.ui.application.action.ViewAction;
import com.hack23.cia.web.impl.ui.application.views.common.pagelinks.api.PageModeMenuCommand;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.AdminViews;

/**
 * The Class AdminRoleSystemTest.
 */
public final class AdminRoleSystemTest extends AbstractRoleSystemTest {

	/** The Constant NO_WEBDRIVER_EXIST_FOR_BROWSER. */
	private static final String NO_WEBDRIVER_EXIST_FOR_BROWSER = "No webdriver exist for browser:";

	/**
	 * Instantiates a new admin role system test.
	 *
	 * @param browser
	 *            the browser
	 */
	public AdminRoleSystemTest(final String browser) {
		super(browser);
	}

	/**
	 * Browsers strings.
	 *
	 * @return the collection
	 */
	@Parameters(name = "AdminRoleSiteTest{index}: browser({0})")
	public final static Collection<String[]> browsersStrings() {
		return Arrays.asList(new String[][] { { "chrome" } });
		// return Arrays.asList(new Object[][] { { "firefox" },{ "chrome" }, {
		// "htmlunit-firefox" },{ "htmlunit-ie11" },{ "htmlunit-chrome" } });
	}


	/**
	 * Site admin agency test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void siteAdminAgencyTest() throws Exception {
		final WebDriver driver = getWebDriver();
		assertNotNull(NO_WEBDRIVER_EXIST_FOR_BROWSER + browser, driver);

		final UserPageVisit userPageVisit = new UserPageVisit(driver, browser);

		loginAsAdmin(userPageVisit);

		userPageVisit.visitDirectPage(new PageModeMenuCommand(AdminViews.ADMIN_AGENCY_VIEW_NAME, ""));
		assertTrue("Expect content",userPageVisit.checkHtmlBodyContainsText("Agency"));

		final String pageId = clickFirstRowInGrid(userPageVisit);

		userPageVisit.validatePage(new PageModeMenuCommand(AdminViews.ADMIN_AGENCY_VIEW_NAME, pageId));

	}

	/**
	 * Site admin portal test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void siteAdminPortalTest() throws Exception {
		final WebDriver driver = getWebDriver();
		assertNotNull(NO_WEBDRIVER_EXIST_FOR_BROWSER + browser, driver);

		final UserPageVisit userPageVisit = new UserPageVisit(driver, browser);
		loginAsAdmin(userPageVisit);

		userPageVisit.visitDirectPage(new PageModeMenuCommand(AdminViews.ADMIN_PORTAL_VIEW_NAME, ""));
		assertTrue("Expect content",userPageVisit.checkHtmlBodyContainsText("Portal"));

		final String pageId = clickFirstRowInGrid(userPageVisit);

		userPageVisit.validatePage(new PageModeMenuCommand(AdminViews.ADMIN_PORTAL_VIEW_NAME, pageId));

	}

	/**
	 * Site admin application configuration test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void siteAdminApplicationConfigurationTest() throws Exception {
		final WebDriver driver = getWebDriver();
		assertNotNull(NO_WEBDRIVER_EXIST_FOR_BROWSER + browser, driver);

		final UserPageVisit userPageVisit = new UserPageVisit(driver, browser);
		loginAsAdmin(userPageVisit);

		userPageVisit
				.visitDirectPage(new PageModeMenuCommand(AdminViews.ADMIN_APPLICATIONS_CONFIGURATION_VIEW_NAME, ""));
		assertTrue("Expect content",userPageVisit.checkHtmlBodyContainsText("Application Configuration"));

		final String pageId = clickFirstRowInGrid(userPageVisit);

		userPageVisit
				.validatePage(new PageModeMenuCommand(AdminViews.ADMIN_APPLICATIONS_CONFIGURATION_VIEW_NAME, pageId));

		userPageVisit.updateConfigurationProperty("Update Configuration.propertyValue", String.valueOf(false));

		userPageVisit.validatePage(new PageModeMenuCommand(AdminViews.ADMIN_APPLICATIONS_CONFIGURATION_VIEW_NAME, pageId));

	}

	/**
	 * Site admin country test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void siteAdminCountryTest() throws Exception {
		final WebDriver driver = getWebDriver();
		assertNotNull(NO_WEBDRIVER_EXIST_FOR_BROWSER + browser, driver);

		final UserPageVisit userPageVisit = new UserPageVisit(driver, browser);
		loginAsAdmin(userPageVisit);

		userPageVisit.visitDirectPage(new PageModeMenuCommand(AdminViews.ADMIN_COUNTRY_VIEW_NAME, ""));
		assertTrue("Expect content",userPageVisit.checkHtmlBodyContainsText("Country"));

		final String pageId = clickFirstRowInGrid(userPageVisit);

		userPageVisit.validatePage(new PageModeMenuCommand(AdminViews.ADMIN_COUNTRY_VIEW_NAME, pageId));

	}

	/**
	 * Site admin useraccount test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void siteAdminUseraccountTest() throws Exception {
		final WebDriver driver = getWebDriver();
		assertNotNull(NO_WEBDRIVER_EXIST_FOR_BROWSER + browser, driver);

		final UserPageVisit userPageVisit = new UserPageVisit(driver, browser);
		loginAsAdmin(userPageVisit);

		userPageVisit.visitDirectPage(new PageModeMenuCommand(AdminViews.ADMIN_USERACCOUNT_VIEW_NAME, ""));
		assertTrue("Expect content",userPageVisit.checkHtmlBodyContainsText("Useraccount"));

		final String pageId = clickFirstRowInGrid(userPageVisit);

		userPageVisit.validatePage(new PageModeMenuCommand(AdminViews.ADMIN_USERACCOUNT_VIEW_NAME, pageId));

	}

	/**
	 * Site admin language test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void siteAdminLanguageTest() throws Exception {
		final WebDriver driver = getWebDriver();
		assertNotNull(NO_WEBDRIVER_EXIST_FOR_BROWSER + browser, driver);

		final UserPageVisit userPageVisit = new UserPageVisit(driver, browser);
		loginAsAdmin(userPageVisit);

		userPageVisit.visitDirectPage(new PageModeMenuCommand(AdminViews.ADMIN_LANGUAGE_VIEW_NAME, ""));
		assertTrue("Expect content",userPageVisit.checkHtmlBodyContainsText("Language"));

		final String pageId = clickFirstRowInGrid(userPageVisit);
		userPageVisit.validatePage(new
		PageModeMenuCommand(AdminViews.ADMIN_LANGUAGE_VIEW_NAME, pageId));

	}

	/**
	 * Site admin language content test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void siteAdminLanguageContentTest() throws Exception {
		final WebDriver driver = getWebDriver();
		assertNotNull(NO_WEBDRIVER_EXIST_FOR_BROWSER + browser, driver);

		final UserPageVisit userPageVisit = new UserPageVisit(driver, browser);
		loginAsAdmin(userPageVisit);

		userPageVisit.visitDirectPage(new PageModeMenuCommand(AdminViews.ADMIN_LANGUAGE_CONTENT_VIEW_NAME, ""));
		assertTrue("Expect content",userPageVisit.checkHtmlBodyContainsText("Language Content"));

		final String pageId = clickFirstRowInGrid(userPageVisit);
		userPageVisit.validatePage(new
		PageModeMenuCommand(AdminViews.ADMIN_LANGUAGE_CONTENT_VIEW_NAME,
		pageId));

	}

	/**
	 * Site admin application session test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void siteAdminApplicationSessionTest() throws Exception {
		final WebDriver driver = getWebDriver();
		assertNotNull(NO_WEBDRIVER_EXIST_FOR_BROWSER + browser, driver);

		final UserPageVisit userPageVisit = new UserPageVisit(driver, browser);
		loginAsAdmin(userPageVisit);

		userPageVisit.visitDirectPage(new PageModeMenuCommand(AdminViews.ADMIN_APPLICATIONS_SESSION_VIEW_NAME, ""));
		assertTrue("Expect content",userPageVisit.checkHtmlBodyContainsText("Application Session"));

		final String pageId = clickFirstRowInGrid(userPageVisit);

		userPageVisit.validatePage(new PageModeMenuCommand(AdminViews.ADMIN_APPLICATIONS_SESSION_VIEW_NAME, pageId));

	}

	/**
	 * Site admin application event test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void siteAdminApplicationEventTest() throws Exception {
		final WebDriver driver = getWebDriver();
		assertNotNull(NO_WEBDRIVER_EXIST_FOR_BROWSER + browser, driver);

		final UserPageVisit userPageVisit = new UserPageVisit(driver, browser);
		loginAsAdmin(userPageVisit);

		userPageVisit.visitDirectPage(new PageModeMenuCommand(AdminViews.ADMIN_APPLICATIONS_EVENTS_VIEW_NAME, ""));
		assertTrue("Expect content",userPageVisit.checkHtmlBodyContainsText("Application Action Event"));

		final String pageId = clickFirstRowInGrid(userPageVisit);

		userPageVisit.validatePage(new PageModeMenuCommand(AdminViews.ADMIN_APPLICATIONS_EVENTS_VIEW_NAME, pageId));

	}

	/**
	 * Site admin monitoring failed access test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test(timeout = 20000)
	public void siteAdminMonitoringFailedAccessTest() throws Exception {
		final WebDriver driver = getWebDriver();
		assertNotNull(NO_WEBDRIVER_EXIST_FOR_BROWSER + browser, driver);

		final UserPageVisit userPageVisit = new UserPageVisit(driver, browser);

		userPageVisit.visitDirectPage(new PageModeMenuCommand(AdminViews.ADMIN_MONITORING_VIEW_NAME, ""));
		assertTrue("Expect this content", userPageVisit.checkHtmlBodyContainsText("Access denided:adminmonitoring"));

		// assertTrue("Expect this content",
		// userPageVisit.getIframesHtmlBodyAsText().contains("Access
		// denided:adminmonitoring"));
	}

	/**
	 * Site admin monitoring success test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void siteAdminMonitoringSuccessTest() throws Exception {
		final WebDriver driver = getWebDriver();
		assertNotNull(NO_WEBDRIVER_EXIST_FOR_BROWSER + browser, driver);

		final UserPageVisit userPageVisit = new UserPageVisit(driver, browser);

		loginAsAdmin(userPageVisit);

		userPageVisit.visitDirectPage(new PageModeMenuCommand(AdminViews.ADMIN_MONITORING_VIEW_NAME, ""));
		assertTrue("Expect this content", userPageVisit.checkHtmlBodyContainsText("Admin Monitoring"));

		assertFalse("Dont expect this content",
				userPageVisit.getIframesHtmlBodyAsText().contains("Login with Username and Password"));
	}


	/**
	 * Visit admin data summary view.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void visitAdminDataSummaryViewTest() throws Exception {
		final WebDriver driver = getWebDriver();
		assertNotNull(NO_WEBDRIVER_EXIST_FOR_BROWSER + browser, driver);

		final UserPageVisit userPageVisit = new UserPageVisit(driver, browser);

		loginAsAdmin(userPageVisit);

		userPageVisit.visitDirectPage(new PageModeMenuCommand(AdminViews.ADMIN_DATA_SUMMARY_VIEW_NAME, ""));
	}

	/**
	 * Site admin test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void siteAdminAgentOperationTest() throws Exception {
		final WebDriver driver = getWebDriver();
		assertNotNull(NO_WEBDRIVER_EXIST_FOR_BROWSER + browser, driver);

		final UserPageVisit userPageVisit = new UserPageVisit(driver, browser);

		loginAsAdmin(userPageVisit);

		userPageVisit.visitDirectPage(new PageModeMenuCommand(AdminViews.ADMIN_AGENT_OPERATIONVIEW_NAME, ""));


		userPageVisit.verifyViewActions(new ViewAction[] {ViewAction.VISIT_MAIN_VIEW,ViewAction.START_AGENT_BUTTON });

		final List<String> actionIdsBy = userPageVisit.getActionIdsBy(ViewAction.START_AGENT_BUTTON);
		assertTrue(actionIdsBy.size() > 0);


		userPageVisit.performAdminAgentOperation(DataAgentTarget.MODEL_EXTERNAL_RIKSDAGEN, DataAgentOperation.IMPORT);

	}



}
