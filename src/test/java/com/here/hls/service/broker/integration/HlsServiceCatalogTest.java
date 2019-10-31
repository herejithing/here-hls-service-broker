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

package com.here.hls.service.broker.integration;

import org.junit.Assert;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.cloud.servicebroker.model.catalog.Catalog;
import org.springframework.cloud.servicebroker.model.catalog.Plan;
import org.springframework.cloud.servicebroker.model.catalog.ServiceDefinition;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

class HlsServiceCatalogTest {

    static void getCatalogTest(RestTemplate restTemplate,MockMvc mockMvc) throws Exception {
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/catalog"), ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<Catalog>>any()))
                .thenReturn(ResponseEntity.ok(Catalog.builder()
                        .serviceDefinitions(ServiceDefinition.builder()
                                .id("test-service-id")
                                .name("test-service-name")
                                .description("Test service")
                                .plans(Plan.builder()
                                        .id("test-plan-id")
                                        .name("test-plan")
                                        .description("test plan")
                                        .bindable(true)
                                        .build())
                                .build())
                        .build()));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/v2/catalog"))
                .andReturn();
        Assert.assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"services\":[{\"id\":\"test-service-id\",\"name\":\"test-service-name\",\"description\":\"Test service\",\"bindable\":false,\"plans\":[{\"id\":\"test-plan-id\",\"name\":\"test-plan\",\"description\":\"test plan\",\"bindable\":true,\"free\":true}]}]}",result.getResponse().getContentAsString());
    }

    static void getCatalogTestException(RestTemplate restTemplate,MockMvc mockMvc) throws Exception {
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/catalog"), ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<?>>any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/v2/catalog"))
                .andReturn();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"description\":\"There was an error while fetching service catalog\"}",result.getResponse().getContentAsString());
    }

    static void getCatalogTestHttpClientErrorException(RestTemplate restTemplate,MockMvc mockMvc) throws Exception {
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/catalog"), ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<?>>any()))
                .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR,"No product found.",("{\"code\":\"10404\",\"error\":\"No product found.\",\"timestamp\":\"2019-07-19 16:07\"}").getBytes(),null));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/v2/catalog"))
                .andReturn();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"description\":\"There was an error while fetching service catalog\"}",result.getResponse().getContentAsString());
    }

    static void getCatalogTest404Exception(RestTemplate restTemplate,MockMvc mockMvc) throws Exception {
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/catalog"), ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<?>>any()))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND,"No product found.",("{\"code\":\"10404\",\"error\":\"No product found.\",\"timestamp\":\"2019-07-19 16:07\"}").getBytes(),null));
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/v2/catalog"))
                .andReturn();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"description\":\"404 No product found.\"}",result.getResponse().getContentAsString());
    }
}
