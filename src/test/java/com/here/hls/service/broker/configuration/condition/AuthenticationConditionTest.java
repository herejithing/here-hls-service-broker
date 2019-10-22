package com.here.hls.service.broker.configuration.condition;

import com.here.hls.service.broker.exception.OAuthorizationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.mock.env.MockEnvironment;

import static org.mockito.Mockito.mock;


public class AuthenticationConditionTest {


    private AuthenticationCondition authenticationCondition ;
    private  ConditionContext conditionContext;
    private AnnotatedTypeMetadata annotatedTypeMetadata;

    @Before
    public void setUp() {
        authenticationCondition = new AuthenticationCondition();
        annotatedTypeMetadata = mock(AnnotatedTypeMetadata.class);
        conditionContext = mock(ConditionContext.class);
    }

    @Test
    public  void testAuthenticationCondition(){
        MockEnvironment environment = new MockEnvironment();
        environment.setProperty("here.token.endpoint.url","https://account.api.here.com/oauth2/token");
        environment.setProperty("here.client.id","test-client-id");
        environment.setProperty("here.access.key.id","test-access-key");
        environment.setProperty("here.access.key.secret","test-key-secret");
        environment.setProperty("here.client.grant.type","client_credentials");
        BDDMockito.given(conditionContext.getEnvironment()).willReturn(environment);
        Assert.assertTrue(authenticationCondition.matches(conditionContext,annotatedTypeMetadata));
    }

    @Test(expected = OAuthorizationException.class)
    public  void testAuthenticationConditionTokenEndpointUnavailable(){
        MockEnvironment environment = new MockEnvironment();
        environment.setProperty("here.client.id","test-client-id");
        environment.setProperty("here.access.key.id","test-access-key");
        environment.setProperty("here.access.key.secret","test-key-secret");
        environment.setProperty("here.client.grant.type","client_credentials");
        BDDMockito.given(conditionContext.getEnvironment()).willReturn(environment);
        Assert.assertTrue(authenticationCondition.matches(conditionContext,annotatedTypeMetadata));
    }

    @Test(expected = OAuthorizationException.class)
    public  void testAuthenticationConditionClientIdUnavailable(){
        MockEnvironment environment = new MockEnvironment();
        environment.setProperty("here.token.endpoint.url","https://account.api.here.com/oauth2/token");
        environment.setProperty("here.access.key.id","test-access-key");
        environment.setProperty("here.access.key.secret","test-key-secret");
        environment.setProperty("here.client.grant.type","client_credentials");
        BDDMockito.given(conditionContext.getEnvironment()).willReturn(environment);
        Assert.assertTrue(authenticationCondition.matches(conditionContext,annotatedTypeMetadata));
    }

    @Test(expected = OAuthorizationException.class)
    public  void testAuthenticationConditionAccessKeyUnavailable(){
        MockEnvironment environment = new MockEnvironment();
        environment.setProperty("here.token.endpoint.url","https://account.api.here.com/oauth2/token");
        environment.setProperty("here.client.id","test-client-id");
        environment.setProperty("here.access.key.secret","test-key-secret");
        environment.setProperty("here.client.grant.type","client_credentials");
        BDDMockito.given(conditionContext.getEnvironment()).willReturn(environment);
        Assert.assertTrue(authenticationCondition.matches(conditionContext,annotatedTypeMetadata));
    }

    @Test(expected = OAuthorizationException.class)
    public  void testAuthenticationConditionAccessSecretUnavailable(){
        MockEnvironment environment = new MockEnvironment();
        environment.setProperty("here.token.endpoint.url","https://account.api.here.com/oauth2/token");
        environment.setProperty("here.client.id","test-client-id");
        environment.setProperty("here.access.key.id","test-access-key");
        BDDMockito.given(conditionContext.getEnvironment()).willReturn(environment);
        Assert.assertTrue(authenticationCondition.matches(conditionContext,annotatedTypeMetadata));
    }

    @Test(expected = OAuthorizationException.class)
    public  void testAuthenticationConditionException(){
        MockEnvironment environment = new MockEnvironment();
        BDDMockito.given(conditionContext.getEnvironment()).willReturn(environment);
        authenticationCondition.matches(conditionContext,annotatedTypeMetadata);
    }

    @Test
    public  void testAuthenticationConditionWithAppCreditional(){
        MockEnvironment environment = new MockEnvironment();
        environment.setProperty("here.app.id","test-appId");
        environment.setProperty("here.app.code","test-appCode");
        BDDMockito.given(conditionContext.getEnvironment()).willReturn(environment);
        Assert.assertFalse(authenticationCondition.matches(conditionContext,annotatedTypeMetadata));
    }

    @Test(expected = OAuthorizationException.class)
    public  void testAuthenticationConditionWithAppIdUnavailable(){
        MockEnvironment environment = new MockEnvironment();
        environment.setProperty("here.app.code","test-appCode");
        BDDMockito.given(conditionContext.getEnvironment()).willReturn(environment);
        Assert.assertFalse(authenticationCondition.matches(conditionContext,annotatedTypeMetadata));
    }

    @Test(expected = OAuthorizationException.class)
    public  void testAuthenticationConditionWithAppCodeUnavailable(){
        MockEnvironment environment = new MockEnvironment();
        environment.setProperty("here.app.id","test-appId");
        BDDMockito.given(conditionContext.getEnvironment()).willReturn(environment);
        Assert.assertFalse(authenticationCondition.matches(conditionContext,annotatedTypeMetadata));
    }
}
