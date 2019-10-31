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

class HlsServiceInstanceTest {

    static void createInstanceTest(RestTemplate restTemplate, MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance"), ArgumentMatchers.eq(HttpMethod.PUT),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<ServiceInstanceResponse>>any(),ArgumentMatchers.eq("instance1")))
                .thenReturn(ResponseEntity.ok(ServiceInstanceResponse.builder()
                        .build()));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .put("/v2/service_instances/{instanceId}","instance1")
                .contentType("application/json")
                .content(HlsFileUtils.readClassPathFileAsString("/integration/instanceCreationRequest.json"))
        ).andReturn();
        Assert.assertEquals(HttpStatus.CREATED.value(),result.getResponse().getStatus());
        Assert.assertEquals("{}",result.getResponse().getContentAsString());
    }

    static void createInstanceWithoutUserTokenTest(RestTemplate restTemplate, MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance"), ArgumentMatchers.eq(HttpMethod.PUT),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<ServiceInstanceResponse>>any(),ArgumentMatchers.eq("instance1")))
                .thenReturn(ResponseEntity.ok(ServiceInstanceResponse.builder()
                        .build()));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .put("/v2/service_instances/{instanceId}","instance1")
                .contentType("application/json")
                .content(HlsFileUtils.readClassPathFileAsString("/integration/instanceCreationRequestWithoutUserToken.json"))
        ).andReturn();
        Assert.assertEquals(HttpStatus.CREATED.value(),result.getResponse().getStatus());
        Assert.assertEquals("{}",result.getResponse().getContentAsString());
    }

    static void createInstanceTestException(RestTemplate restTemplate, MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance"), ArgumentMatchers.eq(HttpMethod.PUT),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<?>>any(),ArgumentMatchers.eq("instance1")))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .put("/v2/service_instances/{instanceId}","instance1")
                .contentType("application/json")
                .content(HlsFileUtils.readClassPathFileAsString("/integration/instanceCreationRequest.json"))
        ).andReturn();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"description\":\"There was an error while creating service instance with id instance1\"}",result.getResponse().getContentAsString());
    }

    static void createInstanceTest404Exception(RestTemplate restTemplate,MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance"), ArgumentMatchers.eq(HttpMethod.PUT),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<?>>any(),ArgumentMatchers.eq("instance1")))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND,"There already exist an instance.",("{\"code\":\"30404\",\"error\":\"There already exist an instance\",\"timestamp\":\"2019-07-19 16:07\"}").getBytes(),null));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .put("/v2/service_instances/{instanceId}","instance1")
                .contentType("application/json")
                .content(HlsFileUtils.readClassPathFileAsString("/integration/instanceCreationRequest.json"))
        ).andReturn();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"error\":\"30404\",\"description\":\"There already exist an instance\"}",result.getResponse().getContentAsString());
    }

    static void createInstanceHttpClientErrorExceptionTest(RestTemplate restTemplate,MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance"), ArgumentMatchers.eq(HttpMethod.PUT),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<?>>any(),ArgumentMatchers.eq("instance1")))
                .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR,"There already exist an instance.",("{\"code\":\"30404\",\"error\":\"There already exist an instance\",\"timestamp\":\"2019-07-19 16:07\"}").getBytes(),null));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .put("/v2/service_instances/{instanceId}","instance1")
                .contentType("application/json")
                .content(HlsFileUtils.readClassPathFileAsString("/integration/instanceCreationRequest.json"))
        ).andReturn();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"error\":\"30404\",\"description\":\"There already exist an instance\"}",result.getResponse().getContentAsString());
    }

    static void getInstanceTest(RestTemplate restTemplate, MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance"), ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<ServiceInstanceResponse>>any(),ArgumentMatchers.eq("instance1")))
                .thenReturn(ResponseEntity.ok(serviceInstanceResponse()));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .get("/v2/service_instances/{instanceId}","instance1")
        ).andReturn();
        Assert.assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"plan_id\":\"hls-plan-id1\",\"dashboard_url\":\"test-dashboard-url\",\"parameters\":{},\"service_id\":\"hls-service-id1\"}",result.getResponse().getContentAsString());
    }
    static void getInstanceTestHttpClientErrorException(RestTemplate restTemplate, MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance"), ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<?>>any(),ArgumentMatchers.eq("instance1")))
                .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR,"There was an error while getting service instance.",("{\"code\":\"30404\",\"error\":\"There was an error while getting service instance\",\"timestamp\":\"2019-07-19 16:07\"}").getBytes(),null));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .get("/v2/service_instances/{instanceId}","instance1")
        ).andReturn();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"error\":\"30404\",\"description\":\"There was an error while getting service instance\"}",result.getResponse().getContentAsString());
    }
    static void getInstanceTest404Exception(RestTemplate restTemplate,MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance"), ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<?>>any(),ArgumentMatchers.eq("instance1")))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND,"There already exist an instance.",("{\"code\":\"30404\",\"error\":\"There already exist an instance\",\"timestamp\":\"2019-07-19 16:07\"}").getBytes(),null));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .get("/v2/service_instances/{instanceId}","instance1")
        ).andReturn();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"error\":\"30404\",\"description\":\"There already exist an instance\"}",result.getResponse().getContentAsString());
    }
    static void getInstanceTestException(RestTemplate restTemplate, MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance"), ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<?>>any(),ArgumentMatchers.eq("instance1")))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .get("/v2/service_instances/{instanceId}","instance1")
        ).andReturn();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"description\":\"There was an error while retrieving service instance with id instance1\"}",result.getResponse().getContentAsString());
    }

    static void createInstanceTestDefinitionException(RestTemplate restTemplate, MockMvc mockMvc) throws Exception {
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/definition"), ArgumentMatchers.eq(HttpMethod.GET),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<?>>any(),ArgumentMatchers.anyString()))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance"), ArgumentMatchers.eq(HttpMethod.PUT),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<?>>any(),ArgumentMatchers.eq("instance1")))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .put("/v2/service_instances/{instanceId}","instance1")
                .contentType("application/json")
                .content(HlsFileUtils.readClassPathFileAsString("/integration/instanceCreationRequest.json"))
        ).andReturn();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"description\":\"There was an error while fetching service definition with id hls-service-id1\"}",result.getResponse().getContentAsString());
    }

    static void updateInstanceTest(RestTemplate restTemplate, MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance"), ArgumentMatchers.eq(HttpMethod.PATCH),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<ServiceInstanceResponse>>any(),ArgumentMatchers.eq("instance1")))
                .thenReturn(ResponseEntity.ok(ServiceInstanceResponse.builder()
                        .build()));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .patch("/v2/service_instances/{instanceId}","instance1")
                .contentType("application/json")
                .content(HlsFileUtils.readClassPathFileAsString("/integration/instanceCreationRequest.json"))
        ).andReturn();
        Assert.assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());
        Assert.assertEquals("{}",result.getResponse().getContentAsString());
    }

    static void updateInstanceTestHttpClientErrorException(RestTemplate restTemplate, MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance"), ArgumentMatchers.eq(HttpMethod.PATCH),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<?>>any(),ArgumentMatchers.eq("instance1")))
                .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR,"There already exist an instance.",("{\"code\":\"30404\",\"error\":\"There already exist an instance\",\"timestamp\":\"2019-07-19 16:07\"}").getBytes(),null));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .patch("/v2/service_instances/{instanceId}","instance1")
                .contentType("application/json")
                .content(HlsFileUtils.readClassPathFileAsString("/integration/instanceCreationRequest.json"))
        ).andReturn();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"error\":\"30404\",\"description\":\"There already exist an instance\"}",result.getResponse().getContentAsString());
    }
    static void updateInstanceTest404Exception(RestTemplate restTemplate,MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance"), ArgumentMatchers.eq(HttpMethod.PATCH),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<?>>any(),ArgumentMatchers.eq("instance1")))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND,"There already exist an instance.",("{\"code\":\"30404\",\"error\":\"There already exist an instance\",\"timestamp\":\"2019-07-19 16:07\"}").getBytes(),null));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .patch("/v2/service_instances/{instanceId}","instance1")
                .contentType("application/json")
                .content(HlsFileUtils.readClassPathFileAsString("/integration/instanceCreationRequest.json"))
        ).andReturn();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"error\":\"30404\",\"description\":\"There already exist an instance\"}",result.getResponse().getContentAsString());
    }

    static void updateInstanceTestException(RestTemplate restTemplate, MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance"), ArgumentMatchers.eq(HttpMethod.PATCH),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<?>>any(),ArgumentMatchers.eq("instance1")))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .patch("/v2/service_instances/{instanceId}","instance1")
                .contentType("application/json")
                .content(HlsFileUtils.readClassPathFileAsString("/integration/instanceCreationRequest.json"))
        ).andReturn();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"description\":\"There was an error while updating service instance with id instance1\"}",result.getResponse().getContentAsString());
    }

    static void updateInstanceWithoutUserTokenTest(RestTemplate restTemplate, MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance"), ArgumentMatchers.eq(HttpMethod.PATCH),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<ServiceInstanceResponse>>any(),ArgumentMatchers.eq("instance1")))
                .thenReturn(ResponseEntity.ok(ServiceInstanceResponse.builder()
                        .build()));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .patch("/v2/service_instances/{instanceId}","instance1")
                .contentType("application/json")
                .content(HlsFileUtils.readClassPathFileAsString("/integration/instanceCreationRequestWithoutUserToken.json"))
        ).andReturn();
        Assert.assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());
        Assert.assertEquals("{}",result.getResponse().getContentAsString());
    }

    static void deleteInstanceTest(RestTemplate restTemplate, MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance"), ArgumentMatchers.eq(HttpMethod.DELETE),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<String>>any(),ArgumentMatchers.eq("instance1")))
                .thenReturn(ResponseEntity.ok("Instance with instanceId: instance1 deleted successfully."));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .delete("/v2/service_instances/{instanceId}","instance1")
                .param("service_id","hls-service-id1")
                .param("plan_id","hls-plan-id1")
        ).andReturn();
        Assert.assertEquals(HttpStatus.OK.value(),result.getResponse().getStatus());
        Assert.assertEquals("{}",result.getResponse().getContentAsString());
    }
    static void deleteInstanceTestHttpClientErrorException(RestTemplate restTemplate, MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance"), ArgumentMatchers.eq(HttpMethod.DELETE),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<String>>any(),ArgumentMatchers.eq("instance1")))
                .thenThrow(new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR,"Unable to delete the service instance.",("{\"code\":\"30404\",\"error\":\"Unable to delete the service instance\",\"timestamp\":\"2019-07-19 16:07\"}").getBytes(),null));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .delete("/v2/service_instances/{instanceId}","instance1")
                .param("service_id","hls-service-id1")
                .param("plan_id","hls-plan-id1")
        ).andReturn();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"error\":\"30404\",\"description\":\"Unable to delete the service instance\"}",result.getResponse().getContentAsString());
    }
    static void deleteInstanceTest404Exception(RestTemplate restTemplate,MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance"), ArgumentMatchers.eq(HttpMethod.DELETE),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<String>>any(),ArgumentMatchers.eq("instance1")))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND,"Unable to delete the service instance.",("{\"code\":\"30404\",\"error\":\"Unable to delete the service instance\",\"timestamp\":\"2019-07-19 16:07\"}").getBytes(),null));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .delete("/v2/service_instances/{instanceId}","instance1")
                .param("service_id","hls-service-id1")
                .param("plan_id","hls-plan-id1")
        ).andReturn();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"error\":\"30404\",\"description\":\"Unable to delete the service instance\"}",result.getResponse().getContentAsString());
    }
    static void deleteInstanceTestException(RestTemplate restTemplate, MockMvc mockMvc) throws Exception {
        mockServiceDefinition(restTemplate);
        BDDMockito.when(restTemplate.exchange(ArgumentMatchers.contains("/integration/api/v1/service/instance"), ArgumentMatchers.eq(HttpMethod.DELETE),
                ArgumentMatchers.any(),ArgumentMatchers.<Class<String>>any(),ArgumentMatchers.eq("instance1")))
                .thenReturn(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        MvcResult result =  mockMvc.perform(MockMvcRequestBuilders
                .delete("/v2/service_instances/{instanceId}","instance1")
                .param("service_id","hls-service-id1")
                .param("plan_id","hls-plan-id1")
        ).andReturn();
        Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(),result.getResponse().getStatus());
        Assert.assertEquals("{\"description\":\"There was an error while deleting service instance with id instance1\"}",result.getResponse().getContentAsString());
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
