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
package com.hack23.cia.service.component.agent.impl.command;

import java.util.Collection;

import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.apache.activemq.web.BrokerFacadeSupport;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.hack23.cia.model.internal.application.data.impl.DataAgentOperation;
import com.hack23.cia.model.internal.application.data.impl.DataAgentTarget;
import com.hack23.cia.model.internal.application.data.impl.DataAgentWorkOrder;
import com.hack23.cia.service.component.agent.api.DataAgentApi;
import com.hack23.cia.service.component.agent.impl.AbstractServiceComponentAgentFunctionalIntegrationTest;

/**
 * The Class DataAgentApiITest.
 */
public final class DataAgentApiITest extends AbstractServiceComponentAgentFunctionalIntegrationTest {

	/** The data agent api. */
	@Autowired
	private DataAgentApi dataAgentApi;

	/** The broker query. */
	@Autowired
	private BrokerFacadeSupport brokerQuery;

	/**
	 * Import riksdagen data success test.
	 */
	@Test
	@Ignore
	public void importRiksdagenDataSuccessTest() {
		dataAgentApi.execute(new DataAgentWorkOrder().withOperation(DataAgentOperation.IMPORT).withTarget(DataAgentTarget.MODEL_EXTERNAL_RIKSDAGEN));
		waitUntilQueueIsEmpty("riksdagen");
	}

	@Test
	@Ignore
	public void importWorldbankDataSuccessTest() {
		dataAgentApi.execute(new DataAgentWorkOrder().withOperation(DataAgentOperation.IMPORT).withTarget(DataAgentTarget.MODEL_EXTERNAL_WORLDBANK));
		waitUntilQueueIsEmpty("worldbank");
	}

	private void waitUntilQueueIsEmpty(final String queue) {
		try {
			while (!isAllCompleted(brokerQuery.getQueues(), queue)) {
				;
			}
			while (brokerQuery.getBrokerAdmin().getTotalDequeueCount() != brokerQuery.getBrokerAdmin()
					.getTotalEnqueueCount()) {
				;
			}

		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	private boolean isAllCompleted(final Collection<QueueViewMBean> queues, final String queue2) {
		boolean allCompleted = true;
		for (final QueueViewMBean queue : queues) {
			if (queue.getName().toLowerCase().contains("riksdagen")) {
				allCompleted = allCompleted && queue.getEnqueueCount() == queue.getDequeueCount();
			}
		}
		return allCompleted;
	}

}
