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
import com.here.hls.service.broker.model.his.ServiceInstanceRequest;
import com.here.hls.service.broker.model.his.ServiceInstanceResponse;
import com.here.hls.service.broker.util.HLSBrokerRestTemplate;
import com.here.hls.service.broker.util.HlsBrokerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.servicebroker.exception.ServiceBrokerException;
import org.springframework.cloud.servicebroker.model.Context;
import org.springframework.cloud.servicebroker.model.instance.CreateServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.instance.CreateServiceInstanceResponse;
import org.springframework.cloud.servicebroker.model.instance.DeleteServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.instance.DeleteServiceInstanceResponse;
import org.springframework.cloud.servicebroker.model.instance.GetServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.instance.GetServiceInstanceResponse;
import org.springframework.cloud.servicebroker.model.instance.UpdateServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.instance.UpdateServiceInstanceResponse;
import org.springframework.cloud.servicebroker.service.ServiceInstanceService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * InstanceService class for HLS Service Instance to serve the Open Service Broker
 * endpoints related to instance creation,update,deletion and retrieval. This implements a proxy service between cloud platform and actual
 * HERE Instance Creation Service.
 */
@Service
@Slf4j
public class HlsInstanceService implements ServiceInstanceService {

    private static final String HIS_INSTANCE_URI = "/service/instance/{instanceId}";

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

    private String hisInstanceUrl;

    /**
     * Initializes variables
     */
    @PostConstruct
    void init(){
        this.hisInstanceUrl = hisHost + hisBaseUri + HIS_INSTANCE_URI;
    }

    /**
     * Invokes HIS Service endpoints to create HLS Service Instance and return the response
     * as CreateServiceInstanceResponse object
     * @param request HLS service instance creation request
     * @return CreateServiceInstanceResponse HLS Service Instance creation response
     */
    @Override
    public CreateServiceInstanceResponse createServiceInstance(CreateServiceInstanceRequest request) {
        log.info("Instance creation request : {}",request);
        try{
            ServiceInstanceResponse response = hlsBrokerRestTemplate.putForEntity(this.hisInstanceUrl,
                    this.createServiceInstanceRequest(request), ServiceInstanceResponse.class,request.getServiceInstanceId());
            return this.createServiceInstanceCreateResponse(response);
        }catch (HttpClientErrorException e) {
            HISError hisError = hlsBrokerUtil.jsonToObject(e.getResponseBodyAsString(), HISError.class);
            log.error("Exception while creating instance. Error Code: {}, Error Message: {}",
                    hisError.getErrorCode(), hisError.getErrorMessage());
            throw new ServiceBrokerException(hisError.getErrorCode(),hisError.getErrorMessage());
        }catch (Exception e) {
            log.error("Exception while creating service instance with instanceId {} ", request.getServiceInstanceId(), e);
            throw new ServiceBrokerException(String.format("There was an error while creating service instance with id %s",request.getServiceInstanceId()));
        }
    }

    /**
     * Invokes HIS Service endpoints to update HLS Service Instance and return the response
     * as UpdateServiceInstanceResponse object
     * @param request HLS Service instance update request
     * @return UpdateServiceInstanceResponse HLS Service Instance update response
     */
    @Override
    public UpdateServiceInstanceResponse updateServiceInstance(UpdateServiceInstanceRequest request) {
        log.info("Instance update request : {}",request);
        try{
            ServiceInstanceResponse response = hlsBrokerRestTemplate.patchForEntity(this.hisInstanceUrl,
                    this.createServiceInstanceRequest(request), ServiceInstanceResponse.class,request.getServiceInstanceId());
            return this.createServiceInstanceUpdateResponse(response);
        }catch (HttpClientErrorException e) {
            HISError hisError = hlsBrokerUtil.jsonToObject(e.getResponseBodyAsString(), HISError.class);
            log.error("Exception while updating instance. Error Code: {}, Error Message: {}",
                    hisError.getErrorCode(), hisError.getErrorMessage());
            throw new ServiceBrokerException(hisError.getErrorCode(),hisError.getErrorMessage());
        }catch (Exception e) {
            log.error("Exception while updating service instance with instanceId {} ", request.getServiceInstanceId(), e);
            throw new ServiceBrokerException(String.format("There was an error while updating service instance with id %s",request.getServiceInstanceId()));
        }
    }

    /**
     * Invokes HIS Service endpoints to delete HLS Service Instance and return the response
     * as DeleteServiceInstanceResponse object
     * @param request HLS Service Instance delete request
     * @return DeleteServiceInstanceResponse
     */
    @Override
    public DeleteServiceInstanceResponse deleteServiceInstance(DeleteServiceInstanceRequest request) {
        log.info(" Delete instance  request : {}",request);
        try{
            Map<String,String> params = new HashMap<>();
            params.put(HLSServiceBrokerConstants.SERVICE_ID,request.getServiceDefinitionId());
            params.put(HLSServiceBrokerConstants.PLAN_ID,request.getPlanId());
            this.hlsBrokerRestTemplate.deleteForEntity(this.hisInstanceUrl,
                    null, String.class, params,request.getServiceInstanceId());
            return DeleteServiceInstanceResponse.builder().build();
        }catch (HttpClientErrorException e) {
            HISError hisError = hlsBrokerUtil.jsonToObject(e.getResponseBodyAsString(), HISError.class);
            log.error("Exception while deleting service instance. Error Code: {}, Error Message: {}",
                    hisError.getErrorCode(), hisError.getErrorMessage());
            throw new ServiceBrokerException(hisError.getErrorCode(),hisError.getErrorMessage());
        }
        catch (Exception e) {
            log.error("Exception while deleting service instance with instanceId {} ", request.getServiceInstanceId(), e);
            throw new ServiceBrokerException(String.format("There was an error while deleting service instance with id %s",request.getServiceInstanceId()));
        }
    }


    /**
     * Invokes HIS Service endpoints to retrieve HLS Service Instance and return the response
     * as GetServiceInstanceResponse object
     * @param request HLS Service Instance retrieval request
     * @return GetServiceInstanceResponse
     */
    @Override
    public GetServiceInstanceResponse getServiceInstance(GetServiceInstanceRequest request) {
        log.info("Get Service Instance request :{}",request);
        try{
            ServiceInstanceResponse response = hlsBrokerRestTemplate.getForEntity(this.hisInstanceUrl,
                    ServiceInstanceResponse.class,null,request.getServiceInstanceId());
            return createServiceInstanceGetResponse(response);
        }catch (HttpClientErrorException e) {
            HISError hisError = hlsBrokerUtil.jsonToObject(e.getResponseBodyAsString(), HISError.class);
            log.error("Exception while getting service instance. Error Code: {}, Error Message: {}",
                    hisError.getErrorCode(), hisError.getErrorMessage());
            throw new ServiceBrokerException(hisError.getErrorCode(),hisError.getErrorMessage());
        }
        catch (Exception e) {
            log.error("Exception while creating service instance with instanceId {} ", request.getServiceInstanceId(), e);
            throw new ServiceBrokerException(String.format("There was an error while retrieving service instance with id %s",request.getServiceInstanceId()));
        }
    }


    private GetServiceInstanceResponse createServiceInstanceGetResponse(ServiceInstanceResponse response) {
        return GetServiceInstanceResponse.builder()
                .serviceDefinitionId(response.getServiceId())
                .planId(response.getPlanId())
                .dashboardUrl(response.getDashBoardUrl())
                .build();
    }

    private UpdateServiceInstanceResponse createServiceInstanceUpdateResponse(ServiceInstanceResponse response) {
        return UpdateServiceInstanceResponse.builder()
                .dashboardUrl(response.getDashBoardUrl())
                .build();
    }

    private CreateServiceInstanceResponse createServiceInstanceCreateResponse(ServiceInstanceResponse response) {
        return CreateServiceInstanceResponse.builder()
                .dashboardUrl(response.getDashBoardUrl())
                .instanceExisted(response.isInstanceExisted())
                .build();
    }

    private ServiceInstanceRequest createServiceInstanceRequest(CreateServiceInstanceRequest createRequest) {
        return createServiceInstanceRequest(createRequest.getServiceInstanceId(),
                createRequest.getServiceDefinitionId(),createRequest.getPlanId(),createRequest.getContext());

    }

    private ServiceInstanceRequest createServiceInstanceRequest(UpdateServiceInstanceRequest updateRequest) {
        return createServiceInstanceRequest(updateRequest.getServiceInstanceId(),
                updateRequest.getServiceDefinitionId(),updateRequest.getPlanId(),updateRequest.getContext());
    }

    private ServiceInstanceRequest createServiceInstanceRequest(String serviceInstanceId, String serviceDefinitionId, String planId, Context context){
        String organizationGuid = getContextPropertyValueAsString(context, HLSServiceBrokerConstants.ORGANIZATION_GUID);
        String spaceGuid = getContextPropertyValueAsString(context, HLSServiceBrokerConstants.SPACE_GUID);
        String platform = getContextPropertyValueAsString(context, HLSServiceBrokerConstants.PLATFORM);

        return ServiceInstanceRequest.builder()
                .instanceId(serviceInstanceId)
                .serviceId(serviceDefinitionId)
                .planId(planId)
                .organizationGuid(organizationGuid)
                .spaceGuid(spaceGuid)
                .platform(platform)
                .clientId(this.hereClientId)
                .build();
    }

    private String getContextPropertyValueAsString(Context context,String propertyName){
        return (String) context.getProperties().get(propertyName);
    }
}
