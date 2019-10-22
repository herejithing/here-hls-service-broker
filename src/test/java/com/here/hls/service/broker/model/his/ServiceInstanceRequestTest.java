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

public class ServiceInstanceRequestTest {

    @Test
    public void testBuilderMethods(){
        ServiceInstanceRequest serviceInstanceRequest = ServiceInstanceRequest.builder()
                .instanceId("test-instance-id")
                .planId("test-plan-id")
                .serviceId("test-service-id")
                .organizationGuid("test-org-guid")
                .spaceGuid("test-space-guid")
                .platform("test-platform")
                .clientId("test-customer")
                .userToken("test-user-token")
                .build();
        Assert.assertEquals("test-instance-id",serviceInstanceRequest.getInstanceId());
        Assert.assertEquals("test-plan-id",serviceInstanceRequest.getPlanId());
        Assert.assertEquals("test-service-id",serviceInstanceRequest.getServiceId());
        Assert.assertEquals("test-org-guid",serviceInstanceRequest.getOrganizationGuid());
        Assert.assertEquals("test-space-guid",serviceInstanceRequest.getSpaceGuid());
        Assert.assertEquals("test-platform",serviceInstanceRequest.getPlatform());
        Assert.assertEquals("test-customer",serviceInstanceRequest.getClientId());
        Assert.assertEquals("test-user-token",serviceInstanceRequest.getUserToken());
    }

}
