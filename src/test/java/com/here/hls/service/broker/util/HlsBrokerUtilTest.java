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

package com.here.hls.service.broker.util;

import com.here.hls.service.broker.model.his.HISError;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.cloud.servicebroker.exception.ServiceBrokerException;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class HlsBrokerUtilTest {

    private  HlsBrokerUtil hlsBrokerUtil = new HlsBrokerUtil();

    @Test
    public void HlsBrokerUtil() throws IOException {
        Map<String, Object> map = hlsBrokerUtil.getMapFromJson("{" +
                "\"app_code\" : \"test-app-code\"," +
                "\"app_id\" : \"test-app-id\"" +
                "}");
        Assert.assertFalse(CollectionUtils.isEmpty(map));
        Assert.assertEquals("test-app-code",map.get("app_code"));
        Assert.assertEquals("test-app-id",map.get("app_id"));
    }

    @Test
    public void HlsBrokerUtilWithNullInput() throws IOException {
        Assert.assertNull(hlsBrokerUtil.getMapFromJson(null));
    }

    @Test
    public void jsonToObject() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        HISError hisError = hlsBrokerUtil.jsonToObject("{\"code\":\"10404\",\"error\":\"No product found for the client id: L45WkjpGPA2YaFZFjNYu1. Please create a project in developer portal.\",\"timestamp\":\"2019-07-19 16:07\"}", HISError.class);
        Assert.assertNotNull(hisError);
        Assert.assertEquals("10404",hisError.getErrorCode());
        Assert.assertEquals("No product found for the client id: L45WkjpGPA2YaFZFjNYu1. Please create a project in developer portal.",hisError.getErrorMessage());
        Assert.assertEquals(LocalDateTime.parse("2019-07-19 16:07", formatter),hisError.getTimestamp());
    }

    @Test(expected = ServiceBrokerException.class)
    public void jsonToObjectExceptionTestTest() {
      hlsBrokerUtil.jsonToObject("No product found for the client id: L45WkjpGPA2YaFZFjNYu1", HISError.class);
    }

}
