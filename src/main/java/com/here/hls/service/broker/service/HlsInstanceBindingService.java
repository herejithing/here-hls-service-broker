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

package com.here.hls.service.broker.service;

import com.here.hls.service.broker.constant.HLSServiceBrokerConstants;
import com.here.hls.service.broker.model.his.HISError;
import com.here.hls.service.broker.model.his.ServiceBindingRequest;
import com.here.hls.service.broker.model.his.ServiceBindingResponse;
import com.here.hls.service.broker.util.HLSBrokerRestTemplate;
import com.here.hls.service.broker.util.HlsBrokerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.servicebroker.exception.ServiceBrokerException;
import org.springframework.cloud.servicebroker.model.binding.CreateServiceInstanceAppBindingResponse;
import org.springframework.cloud.servicebroker.model.binding.CreateServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.binding.CreateServiceInstanceBindingResponse;
import org.springframework.cloud.servicebroker.model.binding.DeleteServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.binding.DeleteServiceInstanceBindingResponse;
import org.springframework.cloud.servicebroker.model.binding.GetServiceInstanceAppBindingResponse;
import org.springframework.cloud.servicebroker.model.binding.GetServiceInstanceBindingRequest;
import org.springframework.cloud.servicebroker.model.binding.GetServiceInstanceBindingResponse;
import org.springframework.cloud.servicebroker.service.ServiceInstanceBindingService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * InstanceBindingService class for HLS Service Instance Bindings to serve the Open Service Broker
 * endpoints related to binding creation, deletion and retrieval. This implements a proxy service between cloud platform and actual
 * HERE Binding Creation Service.
 */
@Service
@Slf4j
public class HlsInstanceBindingService implements ServiceInstanceBindingService {

    private static final String BINDING_CREATION_URI = "/service/instance/{instanceId}/binding/{bindingId}";

    @Autowired
    private HLSBrokerRestTemplate hlsBrokerRestTemplate;

    @Autowired
    private HlsBrokerUtil hlsBrokerUtil;

    @Value("#{'${here.client.id}' ?: '${here.app.id}'}")
    private String hereClientId;

    @Value("${here.hls.core.integration.host}")
    private String hisHost;

    @Value("${here.hls.core.integration.base.uri}")
    private String hisBaseUri;

    private String hisBindingUrl;

    /**
     * Initializes variables
     */
    @PostConstruct
    protected void init(){
        this.hisBindingUrl = hisHost + hisBaseUri + BINDING_CREATION_URI;
    }

    /**
     * Invokes HIS Service endpoints to create HLS Service Instance Binding and return the response
     * as CreateServiceInstanceBindingResponse object
     * @param request CreateServiceInstanceBindingRequest object
     * @return CreateServiceInstanceBindingResponse object
     */
    @Override
    public CreateServiceInstanceBindingResponse createServiceInstanceBinding(CreateServiceInstanceBindingRequest request) {
        log.info("Binding  creation request : {}",request);
        try{
            ServiceBindingResponse response = hlsBrokerRestTemplate.putForEntity(this.hisBindingUrl,
                    this.createServiceBindingRequest(request), ServiceBindingResponse.class,request.getServiceInstanceId(),request.getBindingId());
            return this.createServiceBindingCreateResponse(response);
        }catch (HttpClientErrorException e) {
            HISError hisError = hlsBrokerUtil.jsonToObject(e.getResponseBodyAsString(), HISError.class);
            log.error("Exception while creating service binding. Error Code: {}, Error Message: {}",
                    hisError.getErrorCode(), hisError.getErrorMessage());
            throw new ServiceBrokerException(hisError.getErrorCode(),hisError.getErrorMessage());
        }catch (Exception e) {
            log.error("Exception while creating service binding with instanceId {} ", request.getServiceInstanceId(), e);
            throw new ServiceBrokerException(String.format("There was an error while creating service binding with id %s for instance %s",request.getBindingId(),request.getServiceInstanceId()));
        }
    }

    /**
     * Invokes HIS Service endpoints to delete HLS Service Instance Binding and return the response
     * as DeleteServiceInstanceBindingResponse object
     * @param request DeleteServiceInstanceBindingRequest object
     * @return DeleteServiceInstanceBindingResponse object
     */
    @Override
    public DeleteServiceInstanceBindingResponse deleteServiceInstanceBinding(DeleteServiceInstanceBindingRequest request) {
        log.info("Delete binding request : {}",request);
        try{
            Map<String,String> params = new HashMap<>();
            params.put(HLSServiceBrokerConstants.SERVICE_ID,request.getServiceDefinitionId());
            params.put(HLSServiceBrokerConstants.PLAN_ID,request.getPlanId());
            this.hlsBrokerRestTemplate.deleteForEntity(this.hisBindingUrl,
                    null, String.class, params,request.getServiceInstanceId(),request.getBindingId());
            return DeleteServiceInstanceBindingResponse.builder().build();
        }catch (HttpClientErrorException e) {
            HISError hisError = hlsBrokerUtil.jsonToObject(e.getResponseBodyAsString(), HISError.class);
            log.error("Exception while deleting service binding. Error Code: {}, Error Message: {}",
                    hisError.getErrorCode(), hisError.getErrorMessage());
            throw new ServiceBrokerException(hisError.getErrorCode(),hisError.getErrorMessage());
        }catch (Exception e) {
            log.error("Exception while deleting service binding with instanceId {} ", request.getServiceInstanceId(), e);
            throw new ServiceBrokerException(String.format("There was an error while deleting service binding with id %s for instance %s",request.getBindingId(),request.getServiceInstanceId()));
        }
    }

    /**
     * Invokes HIS Service endpoints to retrieve HLS Service Instance Binding and return the response
     * as GetServiceInstanceBindingResponse object
     * @param request GetServiceInstanceBindingRequest object
     * @return GetServiceInstanceBindingResponse object
     */
    @Override
    public GetServiceInstanceBindingResponse getServiceInstanceBinding(GetServiceInstanceBindingRequest request) {
        try{
            ServiceBindingResponse response = hlsBrokerRestTemplate.getForEntity(this.hisBindingUrl, ServiceBindingResponse.class,null,request.getServiceInstanceId(),request.getBindingId());
            return this.createServiceBindingGetResponse(response);
        }catch (HttpClientErrorException e) {
            HISError hisError = hlsBrokerUtil.jsonToObject(e.getResponseBodyAsString(), HISError.class);
            log.error("Exception while getting service binding. Error Code: {}, Error Message: {}",
                    hisError.getErrorCode(), hisError.getErrorMessage());
            throw new ServiceBrokerException(hisError.getErrorCode(),hisError.getErrorMessage());
        }
        catch (Exception e) {
            log.error("Exception while getting service binding with instanceId {} ", request.getServiceInstanceId(), e);
            throw new ServiceBrokerException(String.format("There was an error while getting service binding with id %s for instance %s",request.getBindingId(),request.getServiceInstanceId()));
        }
    }

    private GetServiceInstanceBindingResponse createServiceBindingGetResponse(ServiceBindingResponse response) {
        return GetServiceInstanceAppBindingResponse.builder()
                .credentials(response.getCredentials())
                .build();
    }

    private CreateServiceInstanceBindingResponse createServiceBindingCreateResponse(ServiceBindingResponse response) {
        return CreateServiceInstanceAppBindingResponse.builder()
                .bindingExisted(response.isBindingExisted())
                .credentials(response.getCredentials())
                .build();
    }

    private ServiceBindingRequest createServiceBindingRequest(CreateServiceInstanceBindingRequest request) {
        return ServiceBindingRequest.builder()
                .instanceId(request.getServiceInstanceId())
                .bindingId(request.getBindingId())
                .serviceId(request.getServiceDefinitionId())
                .planId(request.getPlanId())
                .parameters(request.getParameters())
                .build();
    }
}
