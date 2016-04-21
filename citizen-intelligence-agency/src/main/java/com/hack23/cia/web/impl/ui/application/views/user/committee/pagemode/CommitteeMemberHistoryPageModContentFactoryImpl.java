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

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import com.hack23.cia.model.internal.application.data.committee.impl.ViewRiksdagenCommittee;
import com.hack23.cia.model.internal.application.data.committee.impl.ViewRiksdagenCommitteeRoleMember;
import com.hack23.cia.model.internal.application.data.committee.impl.ViewRiksdagenCommitteeRoleMember_;
import com.hack23.cia.model.internal.application.system.impl.ApplicationEventGroup;
import com.hack23.cia.service.api.DataContainer;
import com.hack23.cia.web.impl.ui.application.action.ViewAction;
import com.hack23.cia.web.impl.ui.application.views.common.labelfactory.LabelFactory;
import com.hack23.cia.web.impl.ui.application.views.common.sizing.ContentRatio;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.CommitteePageMode;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.UserViews;
import com.hack23.cia.web.impl.ui.application.views.pageclicklistener.PageItemPropertyClickListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * The Class CommitteeMemberHistoryPageModContentFactoryImpl.
 */
@Component
public final class CommitteeMemberHistoryPageModContentFactoryImpl extends AbstractCommitteePageModContentFactoryImpl {

	/** The Constant COMMITTEE. */
	private static final String COMMITTEE = "Committee:";

	/** The Constant MEMBER_HISTORY. */
	private static final String MEMBER_HISTORY = "Member History";

	/**
	 * Instantiates a new committee member history page mod content factory
	 * impl.
	 */
	public CommitteeMemberHistoryPageModContentFactoryImpl() {
		super();
	}

	@Override
	public boolean matches(final String page, final String parameters) {
		return NAME.equals(page) && (!StringUtils.isEmpty(parameters)
				&& parameters.contains(CommitteePageMode.MEMBERHISTORY.toString()));
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

			getMenuItemFactory().createCommitteeeMenuBar(menuBar, pageId);

			final Label createHeader2Label = LabelFactory.createHeader2Label(MEMBER_HISTORY);
			panelContent.addComponent(createHeader2Label);

			final DataContainer<ViewRiksdagenCommitteeRoleMember, String> committeeRoleMemberDataContainer = getApplicationManager()
					.getDataContainer(ViewRiksdagenCommitteeRoleMember.class);

			final BeanItemContainer<ViewRiksdagenCommitteeRoleMember> committeeRoleMemberDataSource = new BeanItemContainer<>(
					ViewRiksdagenCommitteeRoleMember.class,
					committeeRoleMemberDataContainer.getAllBy(ViewRiksdagenCommitteeRoleMember_.detail,
							viewRiksdagenCommittee.getEmbeddedId().getDetail()));

			final Grid commmitteeRoleMemberBeanItemGrid = getGridFactory().createBasicBeanItemGrid(
					committeeRoleMemberDataSource, MEMBER_HISTORY,
					new String[] { "roleId", "personId", "firstName", "lastName", "party", "active", "detail",
							"roleCode", "fromDate", "toDate", "totalDaysServed" },
					new String[] { "roleId", "personId", "detail" }, null,
					new PageItemPropertyClickListener(UserViews.POLITICIAN_VIEW_NAME, "personId"), null);

			panelContent.addComponent(commmitteeRoleMemberBeanItemGrid);
			panelContent.setExpandRatio(createHeader2Label, ContentRatio.SMALL);
			panelContent.setExpandRatio(commmitteeRoleMemberBeanItemGrid, ContentRatio.GRID);

			panel.setCaption(COMMITTEE + viewRiksdagenCommittee.getEmbeddedId().getDetail());
			getPageActionEventHelper().createPageEvent(ViewAction.VISIT_COMMITTEE_VIEW, ApplicationEventGroup.USER,
					NAME, parameters, pageId);
		}
		return panelContent;

	}

}