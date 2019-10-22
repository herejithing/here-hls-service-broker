/*
 * Copyright (C) 2019 HERE Europe B.V.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * SPDX-License-Identifier: Apache-2.0
 * License-Filename: LICENSE
 */

package com.here.hls.service.broker.configuration.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * HLS Broker Security Configuration
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Value("${here.service.broker.username}")
	private String hlsBrokerUsername;

	@Value("${here.service.broker.password}")
	private String hlsBrokerPassword;

	//Defaulting the role to ADMIN so that its not mandatory to pass during
	//service startup. If it needs to be overridden it can be
	@Value("${here.service.broker.role:ADMIN}")
	private String hlsBrokerRole;

	/**
	 * Configures HttpSecurity configurations
	 * @param http HttpSecurity object
	 * @throws Exception exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	    http.csrf().disable()
				.authorizeRequests()
				.antMatchers("/v2/**").hasRole(hlsBrokerRole)
				.and()
				.httpBasic();
	}

	/**
	 * Creates a bean of InMemoryUserDetailsManager
	 * @return InMemoryUserDetailsManager bean
	 */
	@Bean
	public InMemoryUserDetailsManager userDetailsService() {
		return new InMemoryUserDetailsManager(adminUser());
	}

	private UserDetails adminUser() {
		return User
				.withUsername(hlsBrokerUsername)
				.password(passwordEncoder().encode(hlsBrokerPassword))
				.roles(hlsBrokerRole)
				.build();
	}


	/**
	 * Creates a bean of type PasswordEncoder
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Configures WebSecurity
	 * @param web WebSecurity
	 */
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}
}