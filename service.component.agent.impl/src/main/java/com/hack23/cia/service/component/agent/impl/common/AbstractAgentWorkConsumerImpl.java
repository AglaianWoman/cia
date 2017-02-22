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
package com.hack23.cia.service.component.agent.impl.common;

import javax.jms.MessageListener;

import org.springframework.beans.factory.annotation.Autowired;

import com.hack23.cia.service.component.agent.impl.common.jms.JmsSender;


/**
 * The Class AbstractAgentWorkConsumerImpl.
 */
public abstract class AbstractAgentWorkConsumerImpl implements MessageListener {

	@Autowired
	private JmsSender jmsSender;

	/**
	 * Gets the jms template.
	 *
	 * @return the jms template
	 */
	public final JmsSender getJmsSender() {
		return jmsSender;
	}

}
