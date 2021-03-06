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
package com.hack23.cia.web.impl.ui.application.util;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hack23.cia.model.internal.application.user.impl.UserAccount;
import com.vaadin.server.Page;

/**
 * The Class UserContextUtil.
 */
public final class UserContextUtil {

	/**
	 * Instantiates a new user context util.
	 */
	private UserContextUtil() {
		super();
	}

	/**
	 * Gets the user name from security context.
	 *
	 * @return the user name from security context
	 */
	public static String getUserNameFromSecurityContext() {

		String result=null;

		final SecurityContext context = SecurityContextHolder.getContext();
		if (context != null) {
			final Authentication authentication = context.getAuthentication();
			if (authentication != null) {
				final Object principal = authentication.getPrincipal();

				if (principal instanceof UserAccount) {
					final UserAccount userAccount = (UserAccount) principal;
					result = userAccount.getUsername();
				}
			}
		}

		return result;
	}


	/**
	 * Gets the user id from security context.
	 *
	 * @return the user id from security context
	 */
	public static String getUserIdFromSecurityContext() {

		String result=null;

		final SecurityContext context = SecurityContextHolder.getContext();
		if (context != null) {
			final Authentication authentication = context.getAuthentication();
			if (authentication != null) {
				final Object principal = authentication.getPrincipal();

				if (principal instanceof UserAccount) {
					final UserAccount userAccount = (UserAccount) principal;
					result = userAccount.getUserId();
				}
			}
		}

		return result;
	}


	/**
	 * Gets the user internal id from security context.
	 *
	 * @return the user internal id from security context
	 */
	public static Long getUserInternalIdFromSecurityContext() {

		Long result=null;

		final SecurityContext context = SecurityContextHolder.getContext();
		if (context != null) {
			final Authentication authentication = context.getAuthentication();
			if (authentication != null) {
				final Object principal = authentication.getPrincipal();

				if (principal instanceof UserAccount) {
					final UserAccount userAccount = (UserAccount) principal;
					result = userAccount.getHjid();
				}
			}
		}

		return result;
	}


	/**
	 * Gets the request url.
	 *
	 * @param current
	 *            the current
	 * @return the request url
	 */
	public static String getRequestUrl(final Page current) {
		if (current != null) {
			return current.getLocation().toString();

		} else {
			final HttpServletRequest httpRequest=((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
			return httpRequest.getRequestURL().toString();
		}
	}

	/**
	 * Allow role in security context.
	 *
	 * @param role
	 *            the role
	 * @return true, if successful
	 */
	public static boolean allowRoleInSecurityContext(final String role) {

		boolean result = false;

		final SecurityContext context = SecurityContextHolder.getContext();
		if (context != null && context.getAuthentication() != null) {
			final Collection<? extends GrantedAuthority> authorities = context.getAuthentication().getAuthorities();

			for (final GrantedAuthority grantedAuthority : authorities) {
				if (role.equalsIgnoreCase(grantedAuthority.getAuthority())) {
					result = true;
				}
			}
		}
		return result;
	}


}
