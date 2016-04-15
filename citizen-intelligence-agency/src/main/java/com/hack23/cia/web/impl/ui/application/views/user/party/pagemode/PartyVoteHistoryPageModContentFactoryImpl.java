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
package com.hack23.cia.web.impl.ui.application.views.user.party.pagemode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import com.hack23.cia.model.internal.application.data.committee.impl.ViewRiksdagenVoteDataBallotPartySummary;
import com.hack23.cia.model.internal.application.data.party.impl.ViewRiksdagenParty;
import com.hack23.cia.service.api.DataContainer;
import com.hack23.cia.web.impl.ui.application.views.common.chartfactory.GenericChartDataManager;
import com.hack23.cia.web.impl.ui.application.views.common.labelfactory.LabelFactory;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.PartyPageMode;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * The Class VoteHistoryPageModContentFactoryImpl.
 */
@Component
public final class PartyVoteHistoryPageModContentFactoryImpl extends AbstractPartyPageModContentFactoryImpl {

	/** The Constant VOTE_HISTORY. */
	private static final String VOTE_HISTORY = "VoteHistory";

	/** The view riksdagen vote data ballot party summary chart data manager. */
	@Autowired
	private GenericChartDataManager<ViewRiksdagenVoteDataBallotPartySummary> viewRiksdagenVoteDataBallotPartySummaryChartDataManager;

	/**
	 * Instantiates a new vote history page mod content factory impl.
	 */
	public PartyVoteHistoryPageModContentFactoryImpl() {
		super();
	}

	@Override
	public boolean matches(final String page, final String parameters) {
		return NAME.equals(page) && parameters.contains(PartyPageMode.VOTEHISTORY.toString());
	}

	@Secured({ "ROLE_ANONYMOUS", "ROLE_USER", "ROLE_ADMIN" })
	@Override
	public Layout createContent(final String parameters, final MenuBar menuBar, final Panel panel) {
		final VerticalLayout panelContent = createPanelContent();

		final String pageId = getPageId(parameters);

		final DataContainer<ViewRiksdagenParty, String> dataContainer = getApplicationManager()
				.getDataContainer(ViewRiksdagenParty.class);

		final ViewRiksdagenParty viewRiksdagenParty = dataContainer.load(pageId);

		if (viewRiksdagenParty != null) {

			getMenuItemFactory().createPartyMenuBar(menuBar, pageId);

			panelContent.addComponent(LabelFactory.createHeader2Label(VOTE_HISTORY));

			final BeanItemContainer<ViewRiksdagenVoteDataBallotPartySummary> partyBallotDataSource = new BeanItemContainer<>(
					ViewRiksdagenVoteDataBallotPartySummary.class,
					viewRiksdagenVoteDataBallotPartySummaryChartDataManager.findByValue(pageId));

			final Grid partynBallotsBeanItemGrid = getGridFactory().createBasicBeanItemGrid(partyBallotDataSource,
					"Ballots", null, null, null, null, null);

			panelContent.addComponent(partynBallotsBeanItemGrid);

			pageCompleted(parameters, panel, pageId, viewRiksdagenParty);
		}
		return panelContent;

	}

}