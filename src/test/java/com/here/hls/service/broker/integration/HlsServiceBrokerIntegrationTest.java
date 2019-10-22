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

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

/*
Adding @Ignore to this class as this class is used to run test cases
in HlsServiceBrokerIntegrationTestWithAppCreds and
HlsServiceBrokerIntegrationTestWithClientCreds classes.
 */
@Ignore
public class HlsServiceBrokerIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    /*
        *** Catalog API Tests - START ***
     */

    @Test
    public void getCatalogTest() throws Exception {
        HlsServiceCatalogTest.getCatalogTest(restTemplate,mockMvc);
    }

    @Test
    public void getCatalogTestException() throws Exception {
        HlsServiceCatalogTest.getCatalogTestException(restTemplate,mockMvc);
    }

    @Test
    public  void getCatalogTestHttpClientErrorException()throws  Exception{
        HlsServiceCatalogTest.getCatalogTestHttpClientErrorException(restTemplate,mockMvc);
    }

   @Test
    public void getCatalogTest404Exception()throws Exception{
        HlsServiceCatalogTest.getCatalogTest404Exception(restTemplate,mockMvc);
    }

    /*
     *** Catalog API Tests - END ***
     */

    /*
     *** Instance API Tests - START ***
     */

    @Test
    public void createServiceInstanceTest() throws Exception {
        HlsServiceInstanceTest.createInstanceTest(restTemplate,mockMvc);
    }

    @Test
    public void createServiceInstanceTestException() throws Exception {
        HlsServiceInstanceTest.createInstanceTestException(restTemplate,mockMvc);
    }

    @Test
    public  void createInstanceTest404Exception()throws  Exception{
        HlsServiceInstanceTest.createInstanceTest404Exception(restTemplate,mockMvc);
    }

    @Test
    public  void createInstanceHttpClientErrorExceptionTest()throws Exception{
        HlsServiceInstanceTest.createInstanceHttpClientErrorExceptionTest(restTemplate,mockMvc);
    }
    @Test
    public void createServiceInstanceTestWithoutUserToken() throws Exception {
        HlsServiceInstanceTest.createInstanceWithoutUserTokenTest(restTemplate,mockMvc);
    }

    @Test
    public void getServiceInstanceTest() throws Exception {
        HlsServiceInstanceTest.getInstanceTest(restTemplate,mockMvc);
    }

    @Test
    public void getInstanceTest404Exception()throws Exception{
        HlsServiceInstanceTest.getInstanceTest404Exception(restTemplate,mockMvc);
    }
    @Test
    public void getInstanceTestHttpClientErrorException()throws Exception{
        HlsServiceInstanceTest.getInstanceTestHttpClientErrorException(restTemplate,mockMvc);
    }

    @Test
    public void getServiceInstanceTestException() throws Exception {
        HlsServiceInstanceTest.getInstanceTestException(restTemplate,mockMvc);
    }

    @Test
    public void createInstanceTestDefinitionException() throws Exception {
        HlsServiceInstanceTest.createInstanceTestDefinitionException(restTemplate,mockMvc);
    }

    @Test
    public void updateServiceInstanceTest() throws Exception {
        HlsServiceInstanceTest.updateInstanceTest(restTemplate,mockMvc);
    }

    @Test
    public void updateServiceInstanceTestException() throws Exception {
        HlsServiceInstanceTest.updateInstanceTestException(restTemplate,mockMvc);
    }

    @Test
    public void updateServiceInstanceWithoutUserToken() throws Exception {
        HlsServiceInstanceTest.updateInstanceWithoutUserTokenTest(restTemplate,mockMvc);
    }

    @Test
    public void updateInstanceTestHttpClientErrorException()throws Exception{
        HlsServiceInstanceTest.updateInstanceTestHttpClientErrorException(restTemplate,mockMvc);
    }

    @Test
    public void updateInstanceTest404Exception()throws Exception{
        HlsServiceInstanceTest.updateInstanceTest404Exception(restTemplate,mockMvc);
    }
    @Test
    public void deleteServiceInstanceTest() throws Exception {
        HlsServiceInstanceTest.deleteInstanceTest(restTemplate,mockMvc);
    }
    @Test
    public  void deleteInstanceTest404Exception()throws Exception{
        HlsServiceInstanceTest.deleteInstanceTest404Exception(restTemplate,mockMvc);
    }

    @Test
    public void deleteInstanceTestHttpClientErrorException()throws Exception{
        HlsServiceInstanceTest.deleteInstanceTestHttpClientErrorException(restTemplate,mockMvc);
    }

    @Test
    public void deleteServiceInstanceTestException() throws Exception {
        HlsServiceInstanceTest.deleteInstanceTestException(restTemplate,mockMvc);
    }

    /*
     *** Instance API Tests - END ***
     */

    /*
     *** Binding API Tests - START ***
     */

    @Test
    public void createServiceInstanceBindingTest() throws Exception {
        HlsServiceInstanceBindingTest.createInstanceBindingTest(restTemplate,mockMvc);
    }

    @Test
    public void createServiceInstanceBindingTestException() throws Exception {
        HlsServiceInstanceBindingTest.createInstanceBindingTestException(restTemplate,mockMvc);
    }


    @Test
    public void getServiceInstanceBindingTest() throws Exception {
        HlsServiceInstanceBindingTest.getInstanceBindingTest(restTemplate,mockMvc);
    }

    @Test
    public void getServiceInstanceBindingTestException() throws Exception {
        HlsServiceInstanceBindingTest.getInstanceBindingTestException(restTemplate,mockMvc);
    }

    @Test
    public void getInstanceBindingHttpClientErrorTest() throws Exception {
        HlsServiceInstanceBindingTest.getInstanceBindingHttpClientErrorTest(restTemplate,mockMvc);
    }

    @Test
    public void getInstanceBindingTest404Exception() throws Exception {
        HlsServiceInstanceBindingTest.getInstanceBindingTest404Exception(restTemplate,mockMvc);
    }

    @Test
    public void deleteServiceInstanceBindingTest() throws Exception {
        HlsServiceInstanceBindingTest.deleteInstanceBindingTest(restTemplate,mockMvc);
    }

    @Test
    public void deleteServiceInstanceBindingTestException() throws Exception {
        HlsServiceInstanceBindingTest.deleteInstanceBindingTestException(restTemplate,mockMvc);
    }

    @Test
    public void deleteInstanceBindingHttpClientErrorTest() throws Exception {
        HlsServiceInstanceBindingTest.deleteInstanceBindingHttpClientErrorTest(restTemplate,mockMvc);
    }

    @Test
    public void deleteInstanceBindingTest404Exception() throws Exception {
        HlsServiceInstanceBindingTest.deleteInstanceBindingTest404Exception(restTemplate,mockMvc);
    }

    /*
     *** Binding API Tests - END ***
     */

}
