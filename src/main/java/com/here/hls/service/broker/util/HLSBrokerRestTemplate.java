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

import com.here.account.oauth2.HereAccessTokenProvider;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.servicebroker.exception.ServiceBrokerException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;
import java.util.Objects;

/**
 * A utility class for RestTemplate to invoke REST API calls required for the service broker
 */
@Component
public class HLSBrokerRestTemplate {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Autowired
    @Qualifier("restTemplate")
    private RestTemplate restTemplate;

    @Autowired(required = false)
    private HereAccessTokenProvider hereAccessTokenProvider;

    @Value("${here.app.id}")
    private String hereAppId;

    @Value("${here.app.code}")
    private String hereAppCode;

    private  static final String HERE_APP_ID="app_id";

    private  static final String HERE_APP_CODE="app_code";


    /**
     * Performs an HTTP GET API call for the given URL and return the result in the provide responseType T
     * @throws ServiceBrokerException in case if the HTTP response is not 200 OK
     * @param url GET URL to be called
     * @param responseType Response Type Class
     * @param <T> Type of the response
     * @return Response of responseType T
     */
    public <T> T getForEntity(String url, Class<T> responseType,Map<String, ?> queryParam,Object... uriVariables) {
        ResponseEntity<T> response = restTemplate.exchange(this.buildRequestUrl(url,queryParam), HttpMethod.GET,
                this.createHttpEntity(null),responseType,uriVariables);
        if(this.isResponseNotOk(response)){
            throw new ServiceBrokerException(String.format("HLS Service Broker GET response status: %s, body: %s for url: %s",
                    response.getStatusCodeValue(),response.getBody(), url));
        }
        return response.getBody();
    }

    /**
     * Performs an HTTP PUT API call for the given URL and return the result in the provide responseType T
     * @param url PUT URL to be called
     * @param requestBody PUT request body
     * @param responseType Type of the Response
     * @param uriVariables Optional URI variables
     * @param <T> Response Class Generic
     * @param <R> Request Class Generic
     * @return Response Object of type T
     */
    public <T,R> T putForEntity(String url, R requestBody, Class<T> responseType, Object... uriVariables){
        ResponseEntity<T> response = restTemplate.exchange(this.buildRequestUrl(url,null), HttpMethod.PUT,this.createHttpEntity(requestBody),responseType,uriVariables);
        if(this.isResponseNotOk(response)){
            throw new ServiceBrokerException(String.format("HLS Service Broker PUT response status: %s, body: %s for url: %s",
                    response.getStatusCodeValue(),response.getBody(), url));
        }
        return response.getBody();
    }

    /**
     * Performs an HTTP PATCH API call for the given URL and return the result in the provide responseType T
     * @param url PATCH URL to be called
     * @param requestBody PATCH request body
     * @param responseType Type of the Response
     * @param uriVariables Optional URI variables
     * @param <T> Response Class Generic
     * @param <R> Request Class Generic
     * @return Response Object of type T
     */
    public <T,R> T patchForEntity(String url, R requestBody, Class<T> responseType, Object... uriVariables){
        ResponseEntity<T> response = restTemplate.exchange(this.buildRequestUrl(url,null),
                HttpMethod.PATCH,this.createHttpEntity(requestBody),responseType,uriVariables);
        if(this.isResponseNotOk(response)){
            throw new ServiceBrokerException(String.format("HLS Service Broker PATCH response status: %s, body: %s for url: %s",
                    response.getStatusCodeValue(),response.getBody(), url));
        }
        return response.getBody();
    }

    /**
     * Performs an HTTP DELETE API call for the given URL and return the result in the provide responseType T
     * @param url DELETE URL to be called
     * @param requestBody DELETE request body
     * @param responseType Type of the Response
     * @param uriVariables Optional URI variables
     * @param <T> Response Class Generic
     * @param <R> Request Class Generic
     * @return Response Object of type T
     */
    public <T,R> T deleteForEntity(String url, R requestBody, Class<T> responseType, Map<String, ?> queryParam,Object... uriVariables){
        ResponseEntity<T> response = restTemplate.exchange(this.buildRequestUrl(url,queryParam),
                HttpMethod.DELETE,this.createHttpEntity(requestBody),responseType,uriVariables);
        if(this.isResponseNotOk(response)){
            throw new ServiceBrokerException(String.format("HLS Service Broker DELETE response status: %s, body: %s for url: %s",
                    response.getStatusCodeValue(),response.getBody(), url));
        }
        return response.getBody();
    }

    private <T> boolean isResponseNotOk(ResponseEntity<T> response){
        return HttpStatus.SC_OK != response.getStatusCodeValue();
    }


    private String buildRequestUrl(String url, Map<String,?> queryParam) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        if(Objects.nonNull(queryParam)){
            queryParam.forEach(builder::queryParam);
        }
        if(areAppCredentialsAvailable()) {
            builder.queryParam(HERE_APP_ID,hereAppId);
            builder.queryParam(HERE_APP_CODE,hereAppCode);
        }
        return builder.encode().toUriString();
    }

    private <R> HttpEntity<R> createHttpEntity(R requestBody) {
        HttpHeaders httpHeaders = new HttpHeaders();
        if(Objects.nonNull(hereAccessTokenProvider)) {
            httpHeaders.add(AUTHORIZATION_HEADER, "Bearer " + hereAccessTokenProvider.getAccessToken());
        }
        return new HttpEntity<>(requestBody, httpHeaders);
    }

    private boolean areAppCredentialsAvailable(){
        return Objects.isNull(hereAccessTokenProvider) &&
                !StringUtils.isEmpty(hereAppId) &&
                !StringUtils.isEmpty(hereAppCode);
    }
}
