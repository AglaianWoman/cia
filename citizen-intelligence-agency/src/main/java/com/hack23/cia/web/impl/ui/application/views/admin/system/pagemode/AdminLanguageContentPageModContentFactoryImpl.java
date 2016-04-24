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
package com.hack23.cia.web.impl.ui.application.views.admin.system.pagemode;

import java.util.Arrays;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import com.hack23.cia.model.internal.application.system.impl.ApplicationEventGroup;
import com.hack23.cia.model.internal.application.system.impl.LanguageContentData;
import com.hack23.cia.service.api.DataContainer;
import com.hack23.cia.web.impl.ui.application.action.ViewAction;
import com.hack23.cia.web.impl.ui.application.views.common.labelfactory.LabelFactory;
import com.hack23.cia.web.impl.ui.application.views.common.sizing.ContentRatio;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.AdminViews;
import com.hack23.cia.web.impl.ui.application.views.pageclicklistener.PageItemPropertyClickListener;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * The Class AdminLanguageContentPageModContentFactoryImpl.
 */
@Component
public final class AdminLanguageContentPageModContentFactoryImpl extends AbstractAdminSystemPageModContentFactoryImpl {

	/** The Constant ADMIN_LANGUAGE_CONTENT. */
	private static final String ADMIN_LANGUAGE_CONTENT = "Admin Language Content";

	/** The Constant NAME. */
	public static final String NAME = AdminViews.ADMIN_LANGUAGE_CONTENT_VIEW_NAME;

	/**
	 * Instantiates a new admin language content page mod content factory impl.
	 */
	public AdminLanguageContentPageModContentFactoryImpl() {
		super();
	}

	@Override
	public boolean matches(final String page, final String parameters) {
		return NAME.equals(page);
	}

	@Secured({ "ROLE_ADMIN" })
	@Override
	public Layout createContent(final String parameters, final MenuBar menuBar, final Panel panel) {
		final VerticalLayout content = createPanelContent();

		final String pageId = getPageId(parameters);

		getMenuItemFactory().createMainPageMenuBar(menuBar);

		final Label createHeader2Label = LabelFactory.createHeader2Label(ADMIN_LANGUAGE_CONTENT);
		content.addComponent(createHeader2Label);
		content.setExpandRatio(createHeader2Label, ContentRatio.SMALL);

		final DataContainer<LanguageContentData, Long> dataContainer = getApplicationManager()
				.getDataContainer(LanguageContentData.class);

		final BeanItemContainer<LanguageContentData> politicianDocumentDataSource = new BeanItemContainer<>(
				LanguageContentData.class, dataContainer.getAll());

		final Grid createBasicBeanItemGrid = getGridFactory().createBasicBeanItemGrid(politicianDocumentDataSource,
				"LanguageContentData",
				new String[] { "hjid", "languageContentType", "refKey", "fromLanguage", "toLanguage", "createdDate",
						"languageValue", "modelObjectVersion" },
				new String[] { "modelObjectId" }, "hjid",
				new PageItemPropertyClickListener(AdminViews.ADMIN_LANGUAGE_CONTENT_VIEW_NAME, "hjid"), null);
		content.addComponent(createBasicBeanItemGrid);

		content.addComponent(createBasicBeanItemGrid);
		content.setExpandRatio(createBasicBeanItemGrid, ContentRatio.GRID);

		if (pageId != null && !pageId.isEmpty()) {

			final LanguageContentData languageContentData = dataContainer.load(Long.valueOf(pageId));

			if (languageContentData != null) {
				getFormFactory().addTextFields(content, new BeanItem<>(languageContentData), LanguageContentData.class,
						Arrays.asList(new String[] { "hjid", "refKey", "fromLanguage", "toLanguage", "createdDate",
								"languageValue", "modelObjectVersion" }));
			}
		}

		getPageActionEventHelper().createPageEvent(ViewAction.VISIT_ADMIN_LANGUAGE_CONTENT_VIEW,
				ApplicationEventGroup.ADMIN, NAME, null, pageId);

		return content;

	}

}