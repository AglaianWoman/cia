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
package com.hack23.cia.web.impl.ui.application.views.user.document.pagemode;

import java.util.Arrays;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import com.hack23.cia.model.external.riksdagen.dokumentlista.impl.DocumentElement;
import com.hack23.cia.model.internal.application.system.impl.ApplicationEventGroup;
import com.hack23.cia.service.api.action.user.SearchDocumentRequest;
import com.hack23.cia.service.api.action.user.SearchDocumentResponse;
import com.hack23.cia.web.impl.ui.application.action.ViewAction;
import com.hack23.cia.web.impl.ui.application.views.common.pagemode.AbstractPageModContentFactoryImpl;
import com.hack23.cia.web.impl.ui.application.views.common.viewnames.UserViews;
import com.hack23.cia.web.impl.ui.application.views.pageclicklistener.SearchDocumentClickListener;
import com.hack23.cia.web.impl.ui.application.views.pageclicklistener.SearchDocumentResponseHandler;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Layout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 * The Class SearchDocumentPageModContentFactoryImpl.
 */
@Component
public final class SearchDocumentPageModContentFactoryImpl extends AbstractPageModContentFactoryImpl {

	/** The Constant NAME. */
	public static final String NAME = UserViews.SEARCH_DOCUMENT_VIEW_NAME;

	/** The Constant MAX_RESULT_SIZE. */
	private static final int MAX_RESULT_SIZE = 100;

	/**
	 * Instantiates a new search document page mod content factory impl.
	 */
	public SearchDocumentPageModContentFactoryImpl() {
		super();
	}

	@Override
	public boolean matches(final String page, final String parameters) {
		return NAME.equals(page);
	}

	@Secured({ "ROLE_ANONYMOUS", "ROLE_USER", "ROLE_ADMIN" })
	@Override
	public Layout createContent(final String parameters, final MenuBar menuBar, final Panel panel) {
		final VerticalLayout panelContent = createPanelContent();

		final String pageId = getPageId(parameters);

		getMenuItemFactory().createMainPageMenuBar(menuBar);

		final VerticalLayout searchLayout = new VerticalLayout();
		searchLayout.setSizeFull();
		panelContent.addComponent(searchLayout);

		final VerticalLayout searchresultLayout = new VerticalLayout();
		searchresultLayout.setSizeFull();

		final Panel formPanel = new Panel();
		formPanel.setSizeFull();

		searchresultLayout.addComponent(formPanel);

		final FormLayout formContent = new FormLayout();
		formPanel.setContent(formContent);

		panelContent.addComponent(searchresultLayout);

		final SearchDocumentRequest searchRequest = new SearchDocumentRequest();
		searchRequest.setSessionId(RequestContextHolder.currentRequestAttributes().getSessionId());
		searchRequest.setMaxResults(MAX_RESULT_SIZE);
		searchRequest.setSearchExpression("");
		final SearchDocumentResponseHandler handler = new SearchDocumentResponseHandler() {

			/** The Constant serialVersionUID. */
			private static final long serialVersionUID = 1L;

			@Override
			public void handle(final SearchDocumentResponse response) {
				searchresultLayout.removeAllComponents();

				final BeanItemContainer<DocumentElement> documentActivityDataDataDataSource = new BeanItemContainer<>(
						DocumentElement.class, response.getResultElement());

				getGridFactory().createBasicBeanItemGrid(searchresultLayout, documentActivityDataDataDataSource,
						"Document",
						new String[] { "rm", "createdDate", "madePublicDate", "documentType", "subType", "title",
								"subTitle", "status" },
						new String[] { "label", "id", "hit", "relatedId", "org", "tempLabel", "numberValue",
								"systemDate", "kallId", "documentFormat", "documentUrlText", "documentUrlHtml",
								"documentStatusUrlXml", "committeeReportUrlXml" },
						null, null, null);

			}
		};
		final ClickListener searchListener = new SearchDocumentClickListener(searchRequest, handler);
		getFormFactory().addRequestInputFormFields(formContent, new BeanItem<>(searchRequest),
				SearchDocumentRequest.class, Arrays.asList(new String[] { "searchExpression" }), "Search",
				searchListener);

		getPageActionEventHelper().createPageEvent(ViewAction.VISIT_DOCUMENT_VIEW, ApplicationEventGroup.USER, NAME,
				parameters, pageId);

		return panelContent;

	}

}
