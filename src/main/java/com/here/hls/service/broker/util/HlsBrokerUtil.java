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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cloud.servicebroker.exception.ServiceBrokerException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

/**
 * Utility class for HLS Service Broker
 */
@Component
public class HlsBrokerUtil {

    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER= new ObjectMapper();
        OBJECT_MAPPER.registerModule(new JavaTimeModule());
    }

    /**
     * Converts a json object into Map<String,Object>. Returns null if the input is null
     * @param jsonObject json string
     * @return Map<String,Object> from the json string
     * @throws IOException exception
     */
    public Map<String,Object> getMapFromJson(String jsonObject) throws IOException {
        if(Objects.isNull(jsonObject)){
            return null;
        }
        return OBJECT_MAPPER
                .readValue(jsonObject,
                        new TypeReference<Map<String, Object>>(){});
    }

    /**
     * Converts the given json to the target type object
     * @param json input json string
     * @param targetType type of the target object
     * @param <T> return type
     * @return Converted Java Object
     * @throws ServiceBrokerException exception while json parsing
     */
    public <T> T jsonToObject(String json,Class<T> targetType) {
        try{
            return OBJECT_MAPPER.readValue(json,targetType);
        }catch (Exception e){
            throw new ServiceBrokerException(e);
        }
    }

}
