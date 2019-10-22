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

package com.here.hls.service.broker.model.his;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.Map;

public class ServiceBindingResponseTest {

    @Test
    public void testBuilderMethods(){
        Map<String,Object> credentials = Collections.singletonMap("key","test-credentials");
        ServiceBindingResponse serviceBindingResponse = ServiceBindingResponse.builder()
                .bindingId("test-binding-id")
                .instanceId("test-instance-id")
                .planId("test-plan-id")
                .serviceId("test-service-id")
                .credentials(credentials)
                .bindingExisted(true)
                .build();
        Assert.assertEquals("test-binding-id",serviceBindingResponse.getBindingId());
        Assert.assertEquals("test-instance-id",serviceBindingResponse.getInstanceId());
        Assert.assertEquals("test-plan-id",serviceBindingResponse.getPlanId());
        Assert.assertEquals("test-service-id",serviceBindingResponse.getServiceId());
        Assert.assertEquals(credentials,serviceBindingResponse.getCredentials());
        Assert.assertTrue(serviceBindingResponse.isBindingExisted());
    }

}
