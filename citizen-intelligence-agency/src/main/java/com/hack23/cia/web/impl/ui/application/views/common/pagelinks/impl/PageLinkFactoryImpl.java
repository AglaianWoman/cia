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
package com.hack23.cia.web.impl.ui.application.views.common.pagelinks.impl;

import org.springframework.stereotype.Service;

import com.hack23.cia.model.external.riksdagen.person.impl.PersonData;
import com.hack23.cia.model.internal.application.data.committee.impl.ViewRiksdagenCommittee;
import com.hack23.cia.model.internal.application.data.ministry.impl.ViewRiksdagenMinistry;
import com.hack23.cia.model.internal.application.data.party.impl.ViewRiksdagenParty;
import com.hack23.cia.web.impl.ui.application.action.ViewAction;
import com.hack23.cia.web.impl.ui.application.util.UserContextUtil;
import com.hack23.cia.web.impl.ui.application.views.common.pagelinks.api.PageLinkFactory;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.AdminViews;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.ApplicationPageMode;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.CommonsViews;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.UserViews;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Link;

/**
 * The Class PageLinkFactoryImpl.
 */
@Service
public final class PageLinkFactoryImpl implements PageLinkFactory {

	/** The Constant PAGE_SEPARATOR. */
	private static final Character PAGE_SEPARATOR = '/';

	/** The Constant SEARCH. */
	private static final String SEARCH = "Search";

	/** The Constant POLITICIAN. */
	private static final String POLITICIAN = "Politician ";

	/** The Constant PARTY. */
	private static final String PARTY = "Party ";

	/** The Constant PAGE_PREFIX. */
	private static final String PAGE_PREFIX = "#!";

	/** The Constant MINISTRY. */
	private static final String MINISTRY = "Ministry ";

	/** The Constant COMMITTEE. */
	private static final String COMMITTEE = "Committee ";

	/** The Constant ADMIN_AGENT_OPERATIONS_LINK_TEXT. */
	private static final String ADMIN_AGENT_OPERATIONS_LINK_TEXT = "Admin Agent Operations";

	/** The Constant ADMIN_DATA_SUMMARY_LINK_TEXT. */
	private static final String ADMIN_DATA_SUMMARY_LINK_TEXT = "Admin Data Summary";

	/** The Constant POLITICIAN_RANKING_LINK_TEXT. */
	private static final String POLITICIAN_RANKING_LINK_TEXT = "Politician Ranking";

	/** The Constant PARTY_RANKING_LINK_TEXT. */
	private static final String PARTY_RANKING_LINK_TEXT = "Party Ranking";

	/** The Constant COMMITTEE_RANKING_LINK_TEXT. */
	private static final String COMMITTEE_RANKING_LINK_TEXT = "Committee Ranking";

	/** The Constant MINISTRY_RANKING_LINK_TEXT. */
	private static final String MINISTRY_RANKING_LINK_TEXT = "Ministry Ranking";

	/** The Constant COUNTRY_RANKING_LINK_TEXT. */
	private static final String COUNTRY_RANKING_LINK_TEXT = "Country Ranking";

	/** The Constant PARLIAMENT_RANKING_LINK_TEXT. */
	private static final String PARLIAMENT_RANKING_LINK_TEXT = "Parliament Ranking";

	/** The Constant LINK_SEPARATOR. */
	private static final String LINK_SEPARATOR = PAGE_PREFIX;

	/** The Constant MAIN_VIEW_LINK_TEXT. */
	private static final String MAIN_VIEW_LINK_TEXT = "Main View";

	@Override
	public Link createMainViewPageLink() {
		final Link pageLink = new Link(MAIN_VIEW_LINK_TEXT, new ExternalResource(
				LINK_SEPARATOR + CommonsViews.MAIN_VIEW_NAME));
		pageLink.setId(ViewAction.VISIT_MAIN_VIEW.name());
		pageLink.setIcon(FontAwesome.STAR);
		return pageLink;
	}

	@Override
	public Link createRegisterPageLink() {
		final Link pageLink = new Link("Register", new ExternalResource(
				LINK_SEPARATOR + CommonsViews.MAIN_VIEW_NAME + PAGE_SEPARATOR + ApplicationPageMode.REGISTER.toString()));
		pageLink.setId(ViewAction.VISIT_REGISTER.name());
		pageLink.setIcon(FontAwesome.USER_PLUS);
		return pageLink;
	}

	@Override
	public Link createLoginPageLink() {
		final Link pageLink = new Link("Login", new ExternalResource(
				LINK_SEPARATOR + CommonsViews.MAIN_VIEW_NAME + PAGE_SEPARATOR + ApplicationPageMode.LOGIN.toString()));
		pageLink.setId(ViewAction.VISIT_LOGIN.name());
		pageLink.setIcon(FontAwesome.SIGN_IN);
		return pageLink;
	}


	@Override
	public Link createCountryRankingViewPageLink() {
		final Link pageLink = new Link(COUNTRY_RANKING_LINK_TEXT,
				new ExternalResource(LINK_SEPARATOR + UserViews.COUNTRY_RANKING_VIEW_NAME));
		pageLink.setId(ViewAction.VISIT_TEST_CHART_VIEW.name());
		pageLink.setIcon(FontAwesome.FLAG);
		return pageLink;
	}

	@Override
	public Link createParliamentViewPageLink() {
		final Link pageLink = new Link(PARLIAMENT_RANKING_LINK_TEXT,
				new ExternalResource(LINK_SEPARATOR + UserViews.PARLIAMENT_RANKING_VIEW_NAME));
		pageLink.setId(ViewAction.VISIT_TEST_CHART_VIEW.name());
		pageLink.setIcon(FontAwesome.INSTITUTION);
		return pageLink;
	}


	@Override
	public Link createMinistryRankingViewPageLink() {
		final Link pageLink = new Link(MINISTRY_RANKING_LINK_TEXT, new ExternalResource(
				LINK_SEPARATOR + UserViews.MINISTRY_RANKING_VIEW_NAME));
		pageLink.setId(ViewAction.VISIT_MINISTRY_RANKING_VIEW.name());
		pageLink.setIcon(FontAwesome.GROUP);
		return pageLink;
	}

	@Override
	public Link createCommitteeRankingViewPageLink() {
		final Link pageLink = new Link(COMMITTEE_RANKING_LINK_TEXT, new ExternalResource(
				LINK_SEPARATOR + UserViews.COMMITTEE_RANKING_VIEW_NAME));
		pageLink.setId(ViewAction.VISIT_COMMITTEE_RANKING_VIEW.name());
		pageLink.setIcon(FontAwesome.GROUP);
		return pageLink;
	}

	@Override
	public Link createPartyRankingViewPageLink() {
		final Link pageLink = new Link(PARTY_RANKING_LINK_TEXT, new ExternalResource(
				LINK_SEPARATOR + UserViews.PARTY_RANKING_VIEW_NAME));
		pageLink.setId(ViewAction.VISIT_PARTY_RANKING_VIEW.name());
		pageLink.setIcon(FontAwesome.GROUP);
		return pageLink;
	}

	@Override
	public Link createPoliticianRankingViewPageLink() {
		final Link pageLink = new Link(POLITICIAN_RANKING_LINK_TEXT,
				new ExternalResource(LINK_SEPARATOR + UserViews.POLITICIAN_RANKING_VIEW_NAME));
		pageLink.setId(ViewAction.VISIT_POLITICIAN_RANKING_VIEW.name());
		pageLink.setIcon(FontAwesome.BUG);
		return pageLink;
	}

	@Override
	public Link createAdminDataSummaryViewPageLink() {
		final Link pageLink = new Link(ADMIN_DATA_SUMMARY_LINK_TEXT,
				new ExternalResource(LINK_SEPARATOR
						+ AdminViews.ADMIN_DATA_SUMMARY_VIEW_NAME));
		pageLink.setId(ViewAction.VISIT_ADMIN_DATA_SUMMARY_VIEW.name());
		pageLink.setIcon(FontAwesome.DATABASE);
		return pageLink;
	}

	@Override
	public Link createAdminAgentOperationViewPageLink() {
		final Link pageLink = new Link(ADMIN_AGENT_OPERATIONS_LINK_TEXT, new ExternalResource(
				LINK_SEPARATOR + AdminViews.ADMIN_AGENT_OPERATIONVIEW_NAME));
		pageLink.setId(ViewAction.VISIT_ADMIN_AGENT_OPERATION_VIEW.name());
		pageLink.setIcon(FontAwesome.USER_SECRET);
		return pageLink;
	}

	@Override
	public Link addCommitteePageLink(final ViewRiksdagenCommittee data) {
		final Link pageLink = new Link(COMMITTEE
				+ data.getEmbeddedId().getDetail(), new ExternalResource(PAGE_PREFIX
						+ UserViews.COMMITTEE_VIEW_NAME + PAGE_SEPARATOR + data.getEmbeddedId().getOrgCode()));
		pageLink.setId(ViewAction.VISIT_COMMITTEE_VIEW.name() + PAGE_SEPARATOR
				+ data.getEmbeddedId().getOrgCode());
		pageLink.setIcon(FontAwesome.GROUP);
		return pageLink;
	}

	@Override
	public Link addMinistryPageLink(final ViewRiksdagenMinistry data) {
		final Link pageLink = new Link(MINISTRY + data.getNameId(),
				new ExternalResource(PAGE_PREFIX + UserViews.MINISTRY_VIEW_NAME + PAGE_SEPARATOR
						+ data.getNameId()));
		pageLink.setId(ViewAction.VISIT_MINISTRY_VIEW.name() + PAGE_SEPARATOR
				+ data.getNameId());
		pageLink.setIcon(FontAwesome.GROUP);
		return pageLink;
	}

	@Override
	public Link addPartyPageLink(final ViewRiksdagenParty data) {
		final Link pageLink = new Link(PARTY + data.getPartyName(),
				new ExternalResource(PAGE_PREFIX + UserViews.PARTY_VIEW_NAME + PAGE_SEPARATOR
						+ data.getPartyId()));
		pageLink.setId(ViewAction.VISIT_PARTY_VIEW.name() + PAGE_SEPARATOR
				+ data.getPartyId());
		pageLink.setIcon(FontAwesome.GROUP);
		return pageLink;
	}

	@Override
	public Link createPoliticianPageLink(final PersonData personData) {
		final Link pageLink = new Link(POLITICIAN
				+ personData.getFirstName() + ' '
				+ personData.getLastName(), new ExternalResource(PAGE_PREFIX
						+ UserViews.POLITICIAN_VIEW_NAME + PAGE_SEPARATOR + personData.getId()));
		pageLink.setId(ViewAction.VISIT_POLITICIAN_VIEW.name() + PAGE_SEPARATOR
				+ personData.getId());
		pageLink.setIcon(FontAwesome.BUG);
		return pageLink;
	}

	@Override
	public Link createSearchDocumentViewPageLink() {
		final Link pageLink = new Link(SEARCH, new ExternalResource(PAGE_PREFIX
						+ UserViews.SEARCH_DOCUMENT_VIEW_NAME));
		pageLink.setId(ViewAction.VISIT_DOCUMENT_VIEW.name());
		pageLink.setIcon(FontAwesome.SEARCH);

		return pageLink;
	}

	@Override
	public Link createAdminPagingLink(final String label,final String page, final String pageId, final String pageNr) {
		final Link pageLink = new Link(label,
				new ExternalResource(PAGE_PREFIX + page + PAGE_SEPARATOR
						+ "[" + pageNr + "]"));
		pageLink.setId(page +"ShowPage" + PAGE_SEPARATOR
				+ pageNr);
		pageLink.setIcon(FontAwesome.SERVER);

		return pageLink;
	}

	@Override
	public Link createUserHomeViewPageLink() {
		final Link pageLink = new Link("User account:" + UserContextUtil.getUserNameFromSecurityContext(), new ExternalResource(PAGE_PREFIX
				+ UserViews.USERHOME_VIEW_NAME));
			pageLink.setId(ViewAction.VISIT_USER_HOME_VIEW.name());
			pageLink.setIcon(FontAwesome.USER);
			return pageLink;
	}

}
