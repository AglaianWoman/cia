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
package com.hack23.cia.web.impl.ui.application.views.common.gridfactory.impl;

import org.springframework.stereotype.Service;
import org.vaadin.gridutil.cell.GridCellFilter;

import com.hack23.cia.web.impl.ui.application.views.common.gridfactory.api.GridFactory;
import com.hack23.cia.web.impl.ui.application.views.common.sizing.ContentRatio;
import com.hack23.cia.web.impl.ui.application.views.pageclicklistener.AbstractPageItemRendererClickListener;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Indexed;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Grid.Column;
import com.vaadin.ui.Grid.SelectionMode;
import com.vaadin.ui.renderers.ButtonRenderer;

/**
 * The Class GridFactoryImpl.
 */
@Service
public final class GridFactoryImpl implements GridFactory {

	/**
	 * Instantiates a new grid factory impl.
	 */
	public GridFactoryImpl() {
		super();
	}

	@Override
	public void createBasicBeanItemGrid(final AbstractOrderedLayout panelContent, final Container.Indexed datasource,
			final String caption, final Object[] columnOrder, final Object[] hideColumns,
			final String idProprty, final AbstractPageItemRendererClickListener<?> listener, final String actionId) {
		createBasicBeanItemNestedPropertiesGrid(panelContent,datasource, caption, null, columnOrder, hideColumns, idProprty, listener, actionId);


	}

	@Override
	public void createBasicBeanItemNestedPropertiesGrid(final AbstractOrderedLayout panelContent,final Indexed datasource, final String caption, final String[] nestedProperties,
			final Object[] columnOrder, final Object[] hideColumns, final String idProprty,
			final AbstractPageItemRendererClickListener<?> listener, final String actionId) {
		final Grid grid = new Grid(datasource);

		grid.setCaption(caption);
		grid.setSelectionMode(SelectionMode.SINGLE);

		createNestedProperties(datasource, nestedProperties);

		configureColumnOrdersAndHiddenFields(columnOrder, hideColumns, grid);

		configureListeners(idProprty, listener, grid);

		grid.setSizeFull();

		grid.setStyleName("Level2Header");

		grid.setImmediate(true);
		grid.setReadOnly(true);

		createGridCellFilter(columnOrder, grid);

		panelContent.addComponent(grid);
		panelContent.setExpandRatio(grid, ContentRatio.GRID);
	}

	/**
	 * Configure column orders and hidden fields.
	 *
	 * @param columnOrder
	 *            the column order
	 * @param hideColumns
	 *            the hide columns
	 * @param grid
	 *            the grid
	 */
	private static void configureColumnOrdersAndHiddenFields(final Object[] columnOrder, final Object[] hideColumns,
			final Grid grid) {
		if (columnOrder != null) {
			grid.setColumnOrder(columnOrder);
		}

		if (hideColumns != null) {
			for (final Object o : hideColumns) {
				grid.removeColumn(o);
			}
		}
	}

	/**
	 * Creates the nested properties.
	 *
	 * @param datasource
	 *            the datasource
	 * @param nestedProperties
	 *            the nested properties
	 */
	private static void createNestedProperties(final Indexed datasource, final String[] nestedProperties) {
		if (nestedProperties != null) {
			for (final String nestedProperty : nestedProperties) {
				final BeanItemContainer<?> dataContainer = (BeanItemContainer<?>) datasource;
				dataContainer.addNestedContainerProperty(nestedProperty);
			}
		}
	}

	/**
	 * Configure listeners.
	 *
	 * @param idProprty
	 *            the id proprty
	 * @param listener
	 *            the listener
	 * @param grid
	 *            the grid
	 */
	private static void configureListeners(final String idProprty, final AbstractPageItemRendererClickListener<?> listener,
			final Grid grid) {
		if (idProprty != null && listener != null) {
			final Column column = grid.getColumn(idProprty);

			column.setRenderer(new ButtonRenderer(listener));

		}

		if (listener != null) {
			grid.addSelectionListener(listener);
		}
	}


	/**
	 * Creates the grid cell filter.
	 *
	 * @param columnOrder
	 *            the column order
	 * @param grid
	 *            the grid
	 */
	private static void createGridCellFilter(final Object[] columnOrder, final Grid grid) {
		if (columnOrder != null) {
			final GridCellFilter gridCellFilter = new GridCellFilter(grid);

			for (final Object column : columnOrder) {
				if (grid.getColumn(column) != null) {
					gridCellFilter.setTextFilter(column.toString(), true, true);
				}
			}

		}
	}

}
