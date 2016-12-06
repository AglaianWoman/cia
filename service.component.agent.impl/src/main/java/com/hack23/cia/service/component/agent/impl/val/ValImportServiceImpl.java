/*
 * Copyright 2010 James Pether Sörling
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
package com.hack23.cia.service.component.agent.impl.val;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hack23.cia.service.data.api.SwedenPoliticalPartyDAO;
import com.hack23.cia.service.external.val.api.ValApi;
import com.hack23.cia.service.external.val.api.ValApiException;

/**
 * The Class ValImportServiceImpl.
 */
@Component("ValImportService")
@Transactional(propagation = Propagation.REQUIRED)
public final class ValImportServiceImpl implements ValImportService {

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ValImportServiceImpl.class);

	/** The sweden political party dao. */
	@Autowired
	private SwedenPoliticalPartyDAO swedenPoliticalPartyDAO;

	/** The val api. */
	@Autowired
	private ValApi valApi;


	/**
	 * Instantiates a new val import service impl.
	 */
	public ValImportServiceImpl() {
		super();
	}


	@Override
	public void loadPoliticalParties() {
		if (swedenPoliticalPartyDAO.getSize() ==0) {			
			try {
				swedenPoliticalPartyDAO.persist(valApi.getSwedenPoliticalParties());
			} catch (ValApiException e) {
				LOGGER.warn("Problem loading Sweden political parties",e);
			}
			LOGGER.info("Sweden political persisted to database");
		} else {
			LOGGER.info("Sweden political parties already in database");
		}

	}

}
