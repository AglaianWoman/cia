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
package com.hack23.cia.web.impl.ui.application.views.user.committee.pagemode;

import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import com.hack23.cia.model.internal.application.data.committee.impl.ViewRiksdagenCommittee;
import com.hack23.cia.model.internal.application.data.committee.impl.ViewRiksdagenCommitteeBallotDecisionPartyEmbeddedId;
import com.hack23.cia.model.internal.application.data.committee.impl.ViewRiksdagenCommitteeBallotDecisionSummary;
import com.hack23.cia.model.internal.application.data.committee.impl.ViewRiksdagenCommitteeBallotDecisionSummary_;
import com.hack23.cia.model.internal.application.system.impl.ApplicationEventGroup;
import com.hack23.cia.service.api.DataContainer;
import com.hack23.cia.web.impl.ui.application.action.ViewAction;
import com.hack23.cia.web.impl.ui.application.views.common.labelfactory.LabelFactory;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.CommitteePageMode;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.UserViews;
import com.hack23.cia.web.impl.ui.application.views.pageclicklistener.PageItemPropertyClickListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * The Class CommitteeBallotDecisionSummaryPageModContentFactoryImpl.
 */
@Component
public final class CommitteeBallotDecisionSummaryPageModContentFactoryImpl
		extends AbstractCommitteePageModContentFactoryImpl {

	/** The Constant COMMITTEE. */
	private static final String COMMITTEE = "Committee:";

	/** The Constant BALLOT_DECISION_SUMMARY. */
	private static final String BALLOT_DECISION_SUMMARY = "Ballot Decision Summary";

	/**
	 * Instantiates a new committee ballot decision summary page mod content
	 * factory impl.
	 */
	public CommitteeBallotDecisionSummaryPageModContentFactoryImpl() {
		super();
	}

	@Override
	public boolean matches(final String page, final String parameters) {
		return NAME.equals(page) && (!StringUtils.isEmpty(parameters)
				&& parameters.contains(CommitteePageMode.BALLOTDECISIONSUMMARY.toString()));
	}

	@Secured({ "ROLE_ANONYMOUS", "ROLE_USER", "ROLE_ADMIN" })
	@Override
	public Layout createContent(final String parameters, final MenuBar menuBar, final Panel panel) {
		final VerticalLayout panelContent = createPanelContent();

		final String pageId = getPageId(parameters);

		final DataContainer<ViewRiksdagenCommittee, String> dataContainer = getApplicationManager()
				.getDataContainer(ViewRiksdagenCommittee.class);

		final ViewRiksdagenCommittee viewRiksdagenCommittee = dataContainer.load(pageId);

		if (viewRiksdagenCommittee != null) {

			getCommitteeMenuItemFactory().createCommitteeeMenuBar(menuBar, pageId);

			LabelFactory.createHeader2Label(panelContent, BALLOT_DECISION_SUMMARY);

			final DataContainer<ViewRiksdagenCommitteeBallotDecisionSummary, ViewRiksdagenCommitteeBallotDecisionPartyEmbeddedId> committeeBallotDecisionPartyDataContainer = getApplicationManager()
					.getDataContainer(ViewRiksdagenCommitteeBallotDecisionSummary.class);

			final List<ViewRiksdagenCommitteeBallotDecisionSummary> decisionPartySummaryList = committeeBallotDecisionPartyDataContainer
					.findOrderedListByProperty(ViewRiksdagenCommitteeBallotDecisionSummary_.org,
							pageId.toUpperCase(Locale.ENGLISH),
							ViewRiksdagenCommitteeBallotDecisionSummary_.createdDate);

			final BeanItemContainer<ViewRiksdagenCommitteeBallotDecisionSummary> committeeBallotDecisionPartyDataSource = new BeanItemContainer<>(
					ViewRiksdagenCommitteeBallotDecisionSummary.class, decisionPartySummaryList);

			getGridFactory().createBasicBeanItemNestedPropertiesGrid(panelContent,
					committeeBallotDecisionPartyDataSource, "Committee Ballot Decision Summary",
					new String[] { "embeddedId.concern", "embeddedId.issue", "embeddedId.id" },
					new String[] { "embeddedId.concern", "embeddedId.issue", "embeddedId.id", "committeeReport", "rm",
							"title", "subTitle", "endNumber", "org", "createdDate", "publicDate", "ballotId",
							"decisionType", "againstProposalParties", "againstProposalNumber", "winner", "ballotType",
							"label", "voteDate", "avgBornYear", "totalVotes", "yesVotes", "noVotes", "abstainVotes",
							"absentVotes", "approved", "noWinner", "percentageYes", "percentageNo", "percentageAbsent",
							"percentageAbstain", "percentageMale" },
					new String[] { "embeddedId" }, "ballotId",
					new PageItemPropertyClickListener(UserViews.BALLOT_VIEW_NAME, "ballotId"), "ballotId");

			panel.setCaption(COMMITTEE + viewRiksdagenCommittee.getEmbeddedId().getDetail());
			getPageActionEventHelper().createPageEvent(ViewAction.VISIT_COMMITTEE_VIEW, ApplicationEventGroup.USER,
					NAME, parameters, pageId);
		}
		return panelContent;

	}

}
