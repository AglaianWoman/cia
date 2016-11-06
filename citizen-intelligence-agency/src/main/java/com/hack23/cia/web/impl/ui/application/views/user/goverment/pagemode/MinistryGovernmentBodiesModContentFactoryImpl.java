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
package com.hack23.cia.web.impl.ui.application.views.user.goverment.pagemode;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import com.hack23.cia.model.internal.application.data.ministry.impl.ViewRiksdagenMinistry;
import com.hack23.cia.model.internal.application.system.impl.ApplicationEventGroup;
import com.hack23.cia.service.api.DataContainer;
import com.hack23.cia.service.external.esv.api.EsvApi;
import com.hack23.cia.service.external.esv.api.GovernmentBodyAnnualSummary;
import com.hack23.cia.web.impl.ui.application.action.ViewAction;
import com.hack23.cia.web.impl.ui.application.views.common.chartfactory.api.GovernmentBodyChartDataManager;
import com.hack23.cia.web.impl.ui.application.views.common.labelfactory.LabelFactory;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.MinistryPageMode;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * The Class MinistryGovernmentBodiesModContentFactoryImpl.
 */
@Component
public final class MinistryGovernmentBodiesModContentFactoryImpl extends AbstractMinistryPageModContentFactoryImpl {

	private static final String GOVERNMENT_BODIES = "Government bodies";

	/** The Constant MINISTRY. */
	private static final String MINISTRY = "Ministry:";
	
	@Autowired
	private EsvApi esvApi;
	
	@Autowired
	private GovernmentBodyChartDataManager governmentBodyChartDataManager;

	/**
	 * Instantiates a new ministry government bodies mod content factory impl.
	 */
	public MinistryGovernmentBodiesModContentFactoryImpl() {
		super();
	}

	@Override
	public boolean matches(final String page, final String parameters) {
		return NAME.equals(page) && (!StringUtils.isEmpty(parameters) && parameters.contains(MinistryPageMode.GOVERNMENT_BODIES.toString()));
	}

	@Secured({ "ROLE_ANONYMOUS", "ROLE_USER", "ROLE_ADMIN" })
	@Override
	public Layout createContent(final String parameters, final MenuBar menuBar, final Panel panel) {
		final VerticalLayout panelContent = createPanelContent();

		final String pageId = getPageId(parameters);

		final DataContainer<ViewRiksdagenMinistry, String> dataContainer = getApplicationManager()
				.getDataContainer(ViewRiksdagenMinistry.class);

		final ViewRiksdagenMinistry viewRiksdagenMinistry = dataContainer.load(pageId);

		if (viewRiksdagenMinistry != null) {

			getMinistryMenuItemFactory().createMinistryMenuBar(menuBar, pageId);

			LabelFactory.createHeader2Label(panelContent,GOVERNMENT_BODIES);
			
			List<GovernmentBodyAnnualSummary> dataPerMinistry = esvApi.getDataPerMinistryAndYear(viewRiksdagenMinistry.getNameId(),2016);
			
			
			final BeanItemContainer<GovernmentBodyAnnualSummary> dataSource = new BeanItemContainer<>(GovernmentBodyAnnualSummary.class,dataPerMinistry);

			getGridFactory().createBasicBeanItemGrid(panelContent, dataSource,
					"Government bodies 2016",
					new String[] { "name", "headCount", "annualWorkHeadCount", "orgNumber",
							"govermentBodyId", "mCode", "consecutiveNumber","comment","ministry" }, new String[] { "vat","comment","year"} , null, null, null);

			
			governmentBodyChartDataManager.createMinistryGovernmentBodyHeadcountSummaryChart(panelContent, viewRiksdagenMinistry.getNameId());
			
			governmentBodyChartDataManager.createMinistryGovernmentBodyHeadcountSummaryChart(panelContent);
			
			panel.setCaption(MINISTRY + viewRiksdagenMinistry.getNameId());
			getPageActionEventHelper().createPageEvent(ViewAction.VISIT_MINISTRY_VIEW, ApplicationEventGroup.USER, NAME,
					parameters, pageId);

		}

		return panelContent;

	}

}
