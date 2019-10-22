#!/usr/bin/env bash

java -cp broker:broker/lib/* com.here.hls.service.broker.HlsServiceBrokerApplication \
        -DbrokerBasicAuthUsername=${BROKER_BASIC_AUTH_USERNAME} \
        -DbrokerBasicAuthPassword=${BROKER_BASIC_AUTH_PASSWORD} \
        -DbrokerRole=${BROKER_ROLE:=ADMIN} \
        -DhereTokenEndpointUrl=${HERE_TOKEN_ENDPOINT_URL} \
        -DhereClientId=${HERE_CLIENT_ID} \
        -DhereAccessKeyId=${HERE_ACCESS_KEY_ID} \
        -DhereAccessKeySecret=${HERE_ACCESS_KEY_SECRET} \
        -DhereClientGrantType=${HERE_CLIENT_GRANT_TYPE:=client_credentials} \
        -DhereDevRelHisHostUrl=${HERE_DEVREL_HIS_HOST_URL:=https://www.here.osb.integration.com}\
        -DhereAppId=${HERE_APP_ID}\
        -DhereAppCode=${HERE_APP_CODE}