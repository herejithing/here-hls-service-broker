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
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(PowerMockRunner.class)
public class CommonConfigurationTest {

    private CommonConfiguration commonConfiguration = new CommonConfiguration();

    @Mock
    private HereAccessTokenProvider.Builder builder;

    @Mock
    private HereAccessTokenProvider hereAccessTokenProvider;

    @Test
    public void getRestTemplate(){
        Assert.assertNotNull(commonConfiguration.restTemplate());
    }

    @Test
    @PrepareForTest(HereAccessTokenProvider.class)
    public void hereAccessTokenProvider(){
        PowerMockito.spy(HereAccessTokenProvider.class);
        Mockito.when(HereAccessTokenProvider.builder()).thenReturn(builder);
        Mockito.when(builder.setClientAuthorizationRequestProvider(Mockito.any(OAuth1ClientCredentialsProvider.class)))
                .thenReturn(builder);
        Mockito.when(builder.build()).thenReturn(hereAccessTokenProvider);
        ReflectionTestUtils.setField(commonConfiguration,"tokenEndpointUrl","www.token.url");
        ReflectionTestUtils.setField(commonConfiguration,"accessKeyId","hAClientId");
        ReflectionTestUtils.setField(commonConfiguration, "accessKeySecret", "hAClientSecret");
        HereAccessTokenProvider resultProvider = commonConfiguration.hereAccessTokenProvider();
        Assert.assertSame(hereAccessTokenProvider,resultProvider);
    }
}
