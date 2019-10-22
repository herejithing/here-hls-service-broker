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

package com.here.hls.service.broker.configuration;

import com.here.account.auth.OAuth1ClientCredentialsProvider;
import com.here.account.oauth2.HereAccessTokenProvider;
import com.here.hls.service.broker.configuration.condition.AuthenticationCondition;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Configuration class to create common configuration beans
 */
@Configuration
public class CommonConfiguration {

    @Value("${here.token.endpoint.url}")
    private String tokenEndpointUrl;

    @Value("${here.access.key.id}")
    private String accessKeyId;

    @Value("${here.access.key.secret}")
    private String accessKeySecret;

    /**
     * Creates Spring RestTemplate bean
     * @return RestTemplate
     */
    @Bean
    @Primary
    public RestTemplate restTemplate() {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
    }

    /**
     * Creates a bean of type {@link HereAccessTokenProvider} provided condition in
     * {@link AuthenticationCondition} is true.
     * @return {@link HereAccessTokenProvider} object
     */
    @Bean
    @Conditional(AuthenticationCondition.class)
    public HereAccessTokenProvider hereAccessTokenProvider(){
        return HereAccessTokenProvider.builder()
                .setClientAuthorizationRequestProvider(getOAuth1ClientCredentialsProvider())
                .build();
    }

    private OAuth1ClientCredentialsProvider getOAuth1ClientCredentialsProvider(){
        return new OAuth1ClientCredentialsProvider(tokenEndpointUrl,accessKeyId,accessKeySecret);
    }
}
