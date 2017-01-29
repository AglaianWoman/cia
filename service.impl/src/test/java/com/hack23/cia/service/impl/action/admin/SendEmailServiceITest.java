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
package com.hack23.cia.service.impl.action.admin;

import java.io.IOException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;
import com.hack23.cia.service.api.action.admin.SendEmailRequest;
import com.hack23.cia.service.api.action.admin.SendEmailResponse;
import com.hack23.cia.service.api.action.application.CreateApplicationSessionRequest;
import com.hack23.cia.service.impl.AbstractServiceFunctionalIntegrationTest;

/**
 * The Class SendEmailServiceITest.
 */
public final class SendEmailServiceITest extends AbstractServiceFunctionalIntegrationTest {

	private CreateApplicationSessionRequest createTestApplicationSession;

	private SimpleSmtpServer dumbster;

	@Before
	public void callConfigureMailServer() throws IOException {
		setAuthenticatedAdminuser();
		createTestApplicationSession = createTestApplicationSession();
		dumbster = configureMailServer(createTestApplicationSession);
	}

	@After
	public void callRestoreMailConfiguration() {
		restoreMailConfiguration(createTestApplicationSession,dumbster);
	}


	/**
	 * Test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void sendEmailSuccessTest() throws Exception {
		final CreateApplicationSessionRequest createSessionRequest = createTestApplicationSession();

		final SendEmailRequest serviceRequest = new SendEmailRequest();
		serviceRequest.setSessionId(createSessionRequest.getSessionId());
		serviceRequest.setEmail("info@hack23.com");
		serviceRequest.setSubject("Test Email SendEmailServiceITest");
		serviceRequest.setContent("Test content");

		final SendEmailResponse response = (SendEmailResponse) applicationManager
				.service(serviceRequest);
		assertNotNull(EXPECT_A_RESULT, response);

		final List<SmtpMessage> emails = dumbster.getReceivedEmails();
		assertEquals(emails.size(), 1);
		final SmtpMessage email = emails.get(0);
		assertEquals(email.getHeaderValue("Subject"), serviceRequest.getSubject());
		assertEquals(email.getBody(), serviceRequest.getContent());
		assertEquals(email.getHeaderValue("To"), serviceRequest.getEmail());

	}

}
