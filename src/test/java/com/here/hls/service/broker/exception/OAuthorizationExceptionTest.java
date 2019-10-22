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

import org.junit.Assert;
import org.junit.Test;

public class OAuthorizationExceptionTest {

    @Test
    public void constructorTestWithMessage(){
        OAuthorizationException oAuthorizationException = new OAuthorizationException("error-message");
        Assert.assertEquals("error-message", oAuthorizationException.getMessage());
        Assert.assertNull(oAuthorizationException.getCause());
    }

    @Test
    public void constructorTestWithMessageAndThrowable(){
        RuntimeException runTimeException = new RuntimeException();
        OAuthorizationException oAuthorizationException = new OAuthorizationException("error-message",runTimeException);
        Assert.assertEquals("error-message", oAuthorizationException.getMessage());
        Assert.assertSame(runTimeException,oAuthorizationException.getCause());
    }

    @Test
    public void constructorTestWithThrowable(){
        RuntimeException runTimeException = new RuntimeException();
        OAuthorizationException oAuthorizationException = new OAuthorizationException(runTimeException);
        Assert.assertEquals("java.lang.RuntimeException",oAuthorizationException.getMessage());
        Assert.assertSame(runTimeException,oAuthorizationException.getCause());
    }

}
