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
package com.hack23.cia.web.impl.ui.application.views.common.converters;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.beanutils.BeanUtils;

import com.vaadin.data.util.converter.Converter;

/**
 * The Class ListPropertyRenderer.
 */
public final class ListPropertyConverter implements Converter<String, List> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The model type. */
	private final Class<List> modelType;

	/** The property. */
	private final String property;

	/** The column. */
	private final String column;

	private final String fallbackColumn;

	/**
	 * Instantiates a new collection property converter.
	 *
	 * @param modelType
	 *            the model type
	 * @param property
	 *            the property
	 * @param column
	 *            the column
	 */
	public ListPropertyConverter(final Class<List> modelType, final String property, final String column) {
		super();
		this.modelType = modelType;
		this.property = property;
		this.column = column;
		this.fallbackColumn = null;
	}

	public ListPropertyConverter(final Class<List> modelType, final String property, final String column,final String fallbackColumn) {
		super();
		this.modelType = modelType;
		this.property = property;
		this.column = column;
		this.fallbackColumn = fallbackColumn;
	}


	/**
	 * Gets the presentation type.
	 *
	 * @return the presentation type
	 */
	@Override
	public Class<String> getPresentationType() {
		return String.class;
	}

	/**
	 * Gets the column.
	 *
	 * @return the column
	 */
	public String getColumn() {
		return column;
	}

	@Override
	public List convertToModel(final String value, final Class<? extends List> targetType, final Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		return new ArrayList<>();
	}

	@Override
	public String convertToPresentation(final List value, final Class<? extends String> targetType, final Locale locale)
			throws com.vaadin.data.util.converter.Converter.ConversionException {
		final StringBuilder stringBuilder = new StringBuilder().append("[ ");

		if (value != null) {
			for (final Object object : value) {
				try {
					final String beanProperty = BeanUtils.getProperty(object, property);

					if (beanProperty != null) {
						stringBuilder.append(beanProperty);
					} else {
						if (fallbackColumn != null) {
							final String beanPropertyFallBack = BeanUtils.getProperty(object, fallbackColumn);
							if (beanPropertyFallBack != null) {
								stringBuilder.append(beanPropertyFallBack);
							}
						}


					}

				} catch (final Exception e) {
					stringBuilder.append(" ");
				}
				stringBuilder.append(" ");
			}
		}

		stringBuilder.append("]");

		return stringBuilder.toString();
	}

	@Override
	public Class<List> getModelType() {
		return modelType;
	}

}
