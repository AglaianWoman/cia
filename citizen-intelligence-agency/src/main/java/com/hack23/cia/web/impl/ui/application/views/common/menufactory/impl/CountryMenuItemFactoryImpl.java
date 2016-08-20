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
package com.hack23.cia.web.impl.ui.application.views.common.menufactory.impl;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hack23.cia.model.internal.application.data.impl.ViewWorldbankIndicatorDataCountrySummary;
import com.hack23.cia.model.internal.application.data.impl.WorldbankIndicatorDataCountrySummaryEmbeddedId;
import com.hack23.cia.service.api.ApplicationManager;
import com.hack23.cia.service.api.DataContainer;
import com.hack23.cia.web.impl.ui.application.views.common.menufactory.api.ApplicationMenuItemFactory;
import com.hack23.cia.web.impl.ui.application.views.common.menufactory.api.CountryMenuItemFactory;
import com.hack23.cia.web.impl.ui.application.views.common.pagelinks.api.PageModeMenuCommand;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.PageMode;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.UserViews;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;

/**
 * The Class CountryMenuItemFactoryImpl.
 */
@Service
public final class CountryMenuItemFactoryImpl extends AbstractMenuItemFactoryImpl implements CountryMenuItemFactory {

	/** The Constant COMMAND22. */
	private static final PageModeMenuCommand COMMAND22 = new PageModeMenuCommand(UserViews.COUNTRY_RANKING_VIEW_NAME, PageMode.PAGEVISITHISTORY);


	/** The Constant COMMAND18. */
	private static final PageModeMenuCommand COMMAND18 = new PageModeMenuCommand(UserViews.COUNTRY_RANKING_VIEW_NAME, PageMode.OVERVIEW);

	/** The Constant BY_SOURCE. */
	private static final String BY_SOURCE = "By Source";

	/** The Constant BY_TOPIC. */
	private static final String BY_TOPIC = "ByTopic";

	/** The Constant COUNTRY_INDICATORS_SWEDEN. */
	private static final String COUNTRY_INDICATORS_SWEDEN = "Country Indicators, Sweden";

	/** The Constant COUNTRY_RANKING_TEXT. */
	private static final String COUNTRY_RANKING_TEXT = "Counry Ranking";

	/** The Constant OVERVIEW_TEXT. */
	private static final String OVERVIEW_TEXT = "Overview";

	/** The application manager. */
	@Autowired
	private ApplicationManager applicationManager;

	/** The Constant PAGE_VISIT_HISTORY_TEXT. */
	private static final String PAGE_VISIT_HISTORY_TEXT = "Page Visit History";

	/** The application menu item factory. */
	@Autowired
	private ApplicationMenuItemFactory applicationMenuItemFactory;

	/**
	 * Instantiates a new country menu item factory impl.
	 */
	public CountryMenuItemFactoryImpl() {
		super();
	}

	@Override
	public void createCountryTopicMenu(final MenuBar menuBar) {
		initApplicationMenuBar(menuBar);

		applicationMenuItemFactory.addRankingMenu(menuBar);


		createCountryTopicMenu( menuBar.addItem(COUNTRY_RANKING_TEXT, FontAwesome.SERVER, null));

	}

	/**
	 * Adds the sources and indicators to menu.
	 *
	 * @param countryIndicators
	 *            the country indicators
	 * @param sourceIndicatorMap
	 *            the source indicator map
	 */
	private void addSourcesAndIndicatorsToMenu(final MenuItem countryIndicators,
			final Map<String, List<ViewWorldbankIndicatorDataCountrySummary>> sourceIndicatorMap) {

		final Map<String, List<ViewWorldbankIndicatorDataCountrySummary>> sortedIndicatorMap = sourceIndicatorMap
				.entrySet().stream().sorted((e1, e2) -> e1.getKey().compareTo(e2.getKey()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		for (final Entry<String, List<ViewWorldbankIndicatorDataCountrySummary>> entry : sortedIndicatorMap
				.entrySet()) {

			final MenuItem sourceItems = countryIndicators.addItem(entry.getKey(), null, null);

			final List<ViewWorldbankIndicatorDataCountrySummary> sortedEntries = entry.getValue().stream()
					.sorted((e1, e2) -> e1.getIndicatorName().compareTo(e2.getIndicatorName()))
					.collect(Collectors.toList());

			for (final ViewWorldbankIndicatorDataCountrySummary indciatorSummary : sortedEntries) {
				sourceItems.addItem(indciatorSummary.getIndicatorName(),
						new PageModeMenuCommand(UserViews.COUNTRY_RANKING_VIEW_NAME, PageMode.INDICATORS,
								indciatorSummary.getEmbeddedId().getIndicatorId()));
			}
		}

	}

	@Override
	public void createCountryTopicMenu(final MenuItem charts) {
		
		
		charts.addItem(OVERVIEW_TEXT, FontAwesome.LINE_CHART,
				COMMAND18);



		final DataContainer<ViewWorldbankIndicatorDataCountrySummary, WorldbankIndicatorDataCountrySummaryEmbeddedId> indicatorDataCountrSummaryDailyDataContainer = applicationManager
				.getDataContainer(ViewWorldbankIndicatorDataCountrySummary.class);

		final Map<String, List<ViewWorldbankIndicatorDataCountrySummary>> sourceIndicatorMap = indicatorDataCountrSummaryDailyDataContainer
				.getAll().parallelStream()
				.filter(t -> t != null && t.getSourceValue() != null && t.getEndYear() > 2010 && t.getDataPoint() > 10)
				.collect(Collectors.groupingBy(t -> t.getSourceValue()));

		final Map<String, List<ViewWorldbankIndicatorDataCountrySummary>> topicIndicatorMap = indicatorDataCountrSummaryDailyDataContainer
				.getAll().parallelStream()
				.filter(t -> t != null && t.getSourceValue() != null && t.getEndYear() > 2010 && t.getDataPoint() > 10)
				.flatMap(t -> Arrays.asList(t.getTopics().split(";")).stream()
						.map(topic -> new AbstractMap.SimpleEntry<>(topic, t)))

				.collect(Collectors.groupingBy(e -> e.getKey(),
						Collectors.mapping(v -> v.getValue(), Collectors.toList())));

		final MenuItem countryIndicators = charts.addItem(COUNTRY_INDICATORS_SWEDEN, FontAwesome.LINE_CHART, null);

		final MenuItem byTopicItem = countryIndicators.addItem(BY_TOPIC,FontAwesome.LINE_CHART, null);

		final MenuItem bySourceItem = countryIndicators.addItem(BY_SOURCE,FontAwesome.LINE_CHART, null);

		addSourcesAndIndicatorsToMenu(byTopicItem, topicIndicatorMap);
		addSourcesAndIndicatorsToMenu(bySourceItem, sourceIndicatorMap);

		charts.addItem(PAGE_VISIT_HISTORY_TEXT, FontAwesome.LINE_CHART,
				COMMAND22);
		
	}
}
