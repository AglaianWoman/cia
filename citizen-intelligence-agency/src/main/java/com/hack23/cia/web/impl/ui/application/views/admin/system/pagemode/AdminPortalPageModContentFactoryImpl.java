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
import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;

import com.hack23.cia.model.internal.application.system.impl.ApplicationEventGroup;
import com.hack23.cia.model.internal.application.system.impl.Portal;
import com.hack23.cia.model.internal.application.system.impl.Portal_;
import com.hack23.cia.service.api.DataContainer;
import com.hack23.cia.web.impl.ui.application.action.ViewAction;
import com.hack23.cia.web.impl.ui.application.views.common.labelfactory.LabelFactory;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.AdminViews;
import com.hack23.cia.web.impl.ui.application.views.pageclicklistener.PageItemPropertyClickListener;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;



/**
 * The Class AdminPortalPageModContentFactoryImpl.
 */
@Component
public final class AdminPortalPageModContentFactoryImpl extends AbstractAdminSystemPageModContentFactoryImpl {

	/** The Constant ADMIN_PORTAL. */
	private static final String ADMIN_PORTAL = "Admin Portal";

	/** The Constant NAME. */
	public static final String NAME = AdminViews.ADMIN_PORTAL_VIEW_NAME;

	/**
	 * Instantiates a new admin agency page mod content factory impl.
	 */
	public AdminPortalPageModContentFactoryImpl() {
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
		final int pageNr= getPageNr(parameters);

		getMenuItemFactory().createMainPageMenuBar(menuBar);

		LabelFactory.createHeader2Label(content,ADMIN_PORTAL);

		final DataContainer<Portal, Long> dataContainer = getApplicationManager().getDataContainer(Portal.class);

		final List<Portal> pageOrderBy = dataContainer.getPageOrderBy(pageNr,DEFAULT_RESULTS_PER_PAGE,Portal_.portalName);

		createPagingControls(content,NAME,pageId, pageOrderBy.size(), pageNr, DEFAULT_RESULTS_PER_PAGE);


		getGridFactory().createBasicBeanItemGrid(content, Portal.class,
				pageOrderBy,
				"Portal",
				new String[] { "hjid", "portalName", "description", "portalType", "googleMapApiKey",
						"modelObjectVersion" }, new String[] { "hjid", "modelObjectId", "googleMapApiKey", "modelObjectVersion" },
				new PageItemPropertyClickListener(AdminViews.ADMIN_PORTAL_VIEW_NAME, "hjid"), null, null);

		if (pageId != null && !pageId.isEmpty()) {

			final Portal portal = dataContainer.load(Long.valueOf(pageId));

			if (portal != null) {

				getFormFactory().addFormPanelTextFields(content, portal, Portal.class,
						Arrays.asList("portalName", "description", "portalType",
								"googleMapApiKey"));
			}
		}

		getPageActionEventHelper().createPageEvent(ViewAction.VISIT_ADMIN_PORTAL_VIEW, ApplicationEventGroup.ADMIN,
				NAME, null, pageId);

		return content;

	}

}
