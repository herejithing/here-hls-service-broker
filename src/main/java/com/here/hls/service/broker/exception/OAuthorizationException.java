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

package com.here.hls.service.broker.exception;

/**
 * Exception class for oauthorization errors during OAUTH based communication for
 * HLS services
 */
public class OAuthorizationException extends RuntimeException {

    /**
     * Constructor with error message
     * @param message error message
     */
    public OAuthorizationException(String message){
        super(message);
    }

    /**
     * Constructor with error message and throwable
     * @param message error message
     * @param throwable throwable object
     */
    public OAuthorizationException(String message, Throwable throwable){
        super(message,throwable);
    }

    /**
     * Constructor with throwable object
     * @param throwable throwable object
     */
    public OAuthorizationException(Throwable throwable){
        super(throwable);
    }

}
