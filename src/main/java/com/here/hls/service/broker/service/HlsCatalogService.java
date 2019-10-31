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

import com.here.hls.service.broker.model.his.HISError;
import com.here.hls.service.broker.util.HLSBrokerRestTemplate;
import com.here.hls.service.broker.util.HlsBrokerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.servicebroker.exception.ServiceBrokerException;
import org.springframework.cloud.servicebroker.model.catalog.Catalog;
import org.springframework.cloud.servicebroker.model.catalog.ServiceDefinition;
import org.springframework.cloud.servicebroker.service.CatalogService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import javax.annotation.PostConstruct;
import java.util.Collections;

/**
 * CatalogService class for HLS Service Catalog to serve the Open Service Broker
 * /v2/catalog endpoint. This implements a proxy service between cloud platform and actual
 * HERE Catalog Service.
 */
@Service
@Slf4j
public class HlsCatalogService implements CatalogService {

    private static final String CATALOG_GET_URI = "/service/catalog";
    private static final String SERVICE_DEFINITION_GET_URI = "/service/definition/{serviceId}";

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

    private String catalogGetUrl;
    private String serviceDefinitionGetUrl;

    /**
     * Initializes variables
     */
    @PostConstruct
    void init(){
        String baseUrl = hisHost + hisBaseUri;
        this.catalogGetUrl = baseUrl + CATALOG_GET_URI;
        this.serviceDefinitionGetUrl = baseUrl + SERVICE_DEFINITION_GET_URI;
    }

    /**
     * Invokes the HERE Service catalog endpoint by passing the clientId
     * @throws ServiceBrokerException if any exception
     * @return Catalog for HLS Service catalog
     */
    @Override
    public Catalog getCatalog() {
        log.info("Serving GET catalog request for clientId: {}", hereClientId);
        try {
            return this.hlsBrokerRestTemplate
                    .getForEntity(this.catalogGetUrl, Catalog.class, Collections.singletonMap("clientOrAppId", hereClientId));
        } catch (HttpClientErrorException e) {
            HISError hisError = hlsBrokerUtil.jsonToObject(e.getResponseBodyAsString(), HISError.class);
            log.error("Exception while retrieving catalog. Error Code: {}, Error Message: {}",
                    hisError.getErrorCode(), hisError.getErrorMessage(),e);
            throw new ServiceBrokerException(hisError.getErrorCode(),hisError.getErrorMessage());
        }catch (Exception e) {
            log.error("Exception while retrieving catalog:", e);
            throw new ServiceBrokerException("There was an error while fetching service catalog");
        }
    }

    /**
     * Invokes the HERE Catalog serve endpoint to fetch ServiceDefinition for the given serviceId
     * @param serviceId service definition id
     * @return ServiceDefinition for the given serviceId
     */
    @Override
    public ServiceDefinition getServiceDefinition(String serviceId) {
        log.info("Serving GET service definition request for serviceId: {}", serviceId);
        try {
            return this.hlsBrokerRestTemplate
                    .getForEntity(this.serviceDefinitionGetUrl,ServiceDefinition.class,Collections.singletonMap("clientOrAppId", hereClientId),serviceId);
        } catch (HttpClientErrorException e) {
            HISError hisError = hlsBrokerUtil.jsonToObject(e.getResponseBodyAsString(), HISError.class);
            log.error("Exception while retrieving service definition with id: {}. Error Code: {}, Error Message: {}",
                    serviceId, hisError.getErrorCode(), hisError.getErrorMessage(),e);
            throw new ServiceBrokerException(hisError.getErrorCode(),hisError.getErrorMessage());
        }catch (Exception e) {
            log.error("Exception while retrieving service definition with id: {} :", serviceId, e);
            throw new ServiceBrokerException(String.format("There was an error while fetching service definition with id %s",serviceId));
        }
    }
}