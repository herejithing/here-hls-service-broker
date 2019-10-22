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

package com.here.hls.service.broker.configuration.condition;

import com.here.hls.service.broker.exception.OAuthorizationException;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * A {@link Condition} class do decide which type of authentication credentials are provided
 * by the operator. It checks for either Oauth2 client credentials or App Credentials (AppId/AppCode)
 */
@Component
public class AuthenticationCondition implements Condition {

    /**
     * Decide whether client credentials or app credentials are provided by looking for the required properties
     * in the {@link Environment} object
     * @param conditionContext condition context
     * @param annotatedTypeMetadata annotated type metadata
     * @return {@linkplain true} if credentials are available. {@linkplain false} if app credentials are available
     * @throws {@link OAuthorizationException} if neither client credentials nor App credentials are available
     */
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        if(this.areClientCredentialsAvailable(conditionContext.getEnvironment())){
            return true;
        }else if(this.areAppCredentialsAvailable(conditionContext.getEnvironment())){
            return false;
        }else {
            throw new OAuthorizationException("Neither Oauth2 client credentials nor app credentials are provided for authentication.");
        }
    }

    private boolean areClientCredentialsAvailable(Environment environment){
        return !(StringUtils.isEmpty(environment.getProperty("here.token.endpoint.url")) ||
                StringUtils.isEmpty(environment.getProperty("here.client.id")) ||
                StringUtils.isEmpty(environment.getProperty("here.access.key.id")) ||
                StringUtils.isEmpty(environment.getProperty("here.access.key.secret")));
    }

    private boolean areAppCredentialsAvailable(Environment environment){
        return !(StringUtils.isEmpty(environment.getProperty("here.app.id")) ||
                StringUtils.isEmpty(environment.getProperty("here.app.code")));
    }
}
