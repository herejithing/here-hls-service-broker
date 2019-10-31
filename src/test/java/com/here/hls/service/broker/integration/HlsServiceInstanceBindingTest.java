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

import com.here.hls.service.broker.model.his.ServiceBindingResponse;
import com.here.hls.service.broker.model.his.ServiceInstanceResponse;
import com.here.hls.service.broker.util.HlsFileUtils;
import org.junit.Assert;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
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

import java.util.HashMap;
import java.util.Map;

class HlsServiceInstanceBindingTest {

    static void createInstanceBindingTest(RestTemplate restTemplate, MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance/{instanceId}/binding"), ArgumentMatchers.eq(HttpMethod.PUT),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<ServiceBindingResponse>>any(),ArgumentMatchers.eq("instance1"),ArgumentMatchers.eq("binding1")))
                .thenReturn(ResponseEntity.ok(createBindingResponse()));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .put("/v2/service_instances/{instanceId}/service_bindings/{bindingId}","instance1","binding1")
                .contentType("application/json")
                .content(HlsFileUtils.readClassPathFileAsString("/integration/bindingCreationRequest.json"))
        ).andReturn();
        Assert.assertEquals(HttpStatus.CREATED.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"credentials\":{\"app_id\":\"test-app-id\",\"app_code\":\"test-app-code\"}}",result.getResponse().getContentAsString());
    }

    static void createInstanceBindingWithoutUserTokenTest(RestTemplate restTemplate, MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance/{instanceId}/binding"), ArgumentMatchers.eq(HttpMethod.PUT),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<ServiceBindingResponse>>any(),ArgumentMatchers.eq("instance1"),ArgumentMatchers.eq("binding1")))
                .thenReturn(ResponseEntity.ok(createBindingResponse()));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .put("/v2/service_instances/{instanceId}/service_bindings/{bindingId}","instance1","binding1")
                .contentType("application/json")
                .content(HlsFileUtils.readClassPathFileAsString("/integration/bindingCreationRequestWithoutUserToken.json"))
        ).andReturn();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"description\":\"Required parameter user_token is not present in the binding creation request.\"}",result.getResponse().getContentAsString());
    }

    static void createInstanceBindingTestException(RestTemplate restTemplate, MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance/{instanceId}/binding"), ArgumentMatchers.eq(HttpMethod.PUT),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<?>>any(),ArgumentMatchers.eq("instance1"),ArgumentMatchers.eq("binding1")))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .put("/v2/service_instances/{instanceId}/service_bindings/{bindingId}","instance1","binding1")
                .contentType("application/json")
                .content(HlsFileUtils.readClassPathFileAsString("/integration/bindingCreationRequest.json"))
        ).andReturn();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"description\":\"There was an error while creating service binding with id binding1 for instance instance1\"}",result.getResponse().getContentAsString());
    }

    static void getInstanceBindingTest(RestTemplate restTemplate, MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance/{instanceId}/binding"), ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<ServiceBindingResponse>>any(),ArgumentMatchers.eq("instance1"),ArgumentMatchers.eq("binding1")))
                .thenReturn(ResponseEntity.ok(createBindingResponse()));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .get("/v2/service_instances/{instanceId}/service_bindings/{bindingId}","instance1","binding1")
        ).andReturn();
        Assert.assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"credentials\":{\"app_id\":\"test-app-id\",\"app_code\":\"test-app-code\"}}",result.getResponse().getContentAsString());
    }

    static void getInstanceBindingTestException(RestTemplate restTemplate, MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance/{instanceId}/binding"), ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<?>>any(),ArgumentMatchers.eq("instance1"),ArgumentMatchers.eq("binding1")))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .get("/v2/service_instances/{instanceId}/service_bindings/{bindingId}","instance1","binding1")
        ).andReturn();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"description\":\"There was an error while getting service binding with id binding1 for instance instance1\"}",result.getResponse().getContentAsString());
    }
    static void getInstanceBindingHttpClientErrorTest(RestTemplate restTemplate, MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance/{instanceId}/binding"), ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<?>>any(),ArgumentMatchers.eq("instance1"),ArgumentMatchers.eq("binding1")))
                .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR,"Service Binding not found.",("{\"code\":\"40404\",\"error\":\"There was an error while getting service instance\",\"timestamp\":\"2019-07-19 16:07\"}").getBytes(),null));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .get("/v2/service_instances/{instanceId}/service_bindings/{bindingId}","instance1","binding1")
        ).andReturn();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"error\":\"40404\",\"description\":\"There was an error while getting service instance\"}",result.getResponse().getContentAsString());
    }
    static void getInstanceBindingTest404Exception(RestTemplate restTemplate,MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance/{instanceId}/binding"), ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<?>>any(),ArgumentMatchers.eq("instance1"),ArgumentMatchers.eq("binding1")))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND,"Service Binding not found.",("{\"code\":\"40404\",\"error\":\"Service Binding not found\",\"timestamp\":\"2019-07-19 16:07\"}").getBytes(),null));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .get("/v2/service_instances/{instanceId}/service_bindings/{bindingId}","instance1","binding1")
        ).andReturn();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"error\":\"40404\",\"description\":\"Service Binding not found\"}",result.getResponse().getContentAsString());
    }

    static void deleteInstanceBindingTest(RestTemplate restTemplate, MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance/{instanceId}/binding"), ArgumentMatchers.eq(HttpMethod.DELETE),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<String>>any(),ArgumentMatchers.eq("instance1"),ArgumentMatchers.eq("binding1")))
                .thenReturn(ResponseEntity.ok("Binding with bindingid: binding1 deleted successfully."));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .delete("/v2/service_instances/{instanceId}/service_bindings/{bindingId}","instance1","binding1")
                .param("service_id","hls-service-id1")
                .param("plan_id","hls-plan-id1")
        ).andReturn();
        Assert.assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());
        Assert.assertEquals("{}",result.getResponse().getContentAsString());
    }

    static void deleteInstanceBindingTestException(RestTemplate restTemplate, MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance/%7BinstanceId%7D/binding"), ArgumentMatchers.eq(HttpMethod.DELETE),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<String>>any(),ArgumentMatchers.eq("instance1"),ArgumentMatchers.eq("binding1")))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .delete("/v2/service_instances/{instanceId}/service_bindings/{bindingId}","instance1","binding1")
                .param("service_id","hls-service-id1")
                .param("plan_id","hls-plan-id1")
        ).andReturn();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"description\":\"There was an error while deleting service binding with id binding1 for instance instance1\"}",result.getResponse().getContentAsString());
    }
    static void deleteInstanceBindingHttpClientErrorTest(RestTemplate restTemplate, MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance/{instanceId}/binding"), ArgumentMatchers.eq(HttpMethod.DELETE),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<String>>any(),ArgumentMatchers.eq("instance1"),ArgumentMatchers.eq("binding1")))
                .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR,"Service Binding not found.",("{\"code\":\"40404\",\"error\":\"Service Binding not found\",\"timestamp\":\"2019-07-19 16:07\"}").getBytes(),null));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .delete("/v2/service_instances/{instanceId}/service_bindings/{bindingId}","instance1","binding1")
                .param("service_id","hls-service-id1")
                .param("plan_id","hls-plan-id1")
        ).andReturn();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"error\":\"40404\",\"description\":\"Service Binding not found\"}",result.getResponse().getContentAsString());
    }
    static void deleteInstanceBindingTest404Exception(RestTemplate restTemplate,MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance/{instanceId}/binding"), ArgumentMatchers.eq(HttpMethod.DELETE),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<String>>any(),ArgumentMatchers.eq("instance1"),ArgumentMatchers.eq("binding1")))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND,"Service Binding not found.",("{\"code\":\"40404\",\"error\":\"Service Binding not found\",\"timestamp\":\"2019-07-19 16:07\"}").getBytes(),null));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .delete("/v2/service_instances/{instanceId}/service_bindings/{bindingId}","instance1","binding1")
                .param("service_id","hls-service-id1")
                .param("plan_id","hls-plan-id1")
        ).andReturn();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"error\":\"40404\",\"description\":\"Service Binding not found\"}",result.getResponse().getContentAsString());
    }

    private static ServiceBindingResponse createBindingResponse(){
        Map<String,Object> credentials = new HashMap<>();
        credentials.put("app_code","test-app-code");
        credentials.put("app_id","test-app-id");
        return ServiceBindingResponse.builder()
                .bindingExisted(false)
                .credentials(credentials)
                .build();
    }

    private static ServiceInstanceResponse serviceInstanceResponse(){
        return ServiceInstanceResponse.builder()
                .serviceId("hls-service-id1")
                .planId("hls-plan-id1")
                .instanceId("instance1")
                .dashBoardUrl("test-dashboard-url")
                .instanceExisted(false)
                .build();
    }

    private static void mockServiceDefinition(RestTemplate restTemplate){
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/definition"), ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<ServiceDefinition>>any(),ArgumentMatchers.anyString()))
                .thenReturn(ResponseEntity.ok(ServiceDefinition.builder()
                                .id("hls-service-id1")
                                .name("test-service-name")
                                .description("Test service")
                                .plans(Plan.builder()
                                        .id("hls-plan-id1")
                                        .name("test-plan")
                                        .description("test plan")
                                        .bindable(true)
                                        .build())
                                .build()));
    }

}
