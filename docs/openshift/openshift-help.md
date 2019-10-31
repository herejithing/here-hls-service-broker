% HLS-Service-Broker (1) Container Image Pages
% HERE Technologies
% September 17, 2019

# NAME
hls-service-broker \- HLS Service Broker container image

# DESCRIPTION
The hls-service-broker image provides a containerized packaging of the HLS Service Broker.
HERE Location Services (HLS) make it easy for developers to bring enterprise-grade location intelligence to
any applications on your platform. HLS power a number of use cases like Fleet Utilization,
Supply Chain Optimization, Urban Mobility, etc. These services open new location intelligence
opportunities in diverse industries like the Internet of Things, Automotive, Insurance, Advertising,
Mobile Payments, Public Sector, Smart Cities, Telecom, Utilities, and Transportation and Logistics.
HLS includes Geocoder, Search, HERE Location Services, Navigation, Routing, Fleet Telematics,
Positioning and more which solve a range of problems like map visualization, navigation, routing,
geocoding, time zone lookups, geofencing, custom locations, routing, route matching GPS traces, geospatial,
sequencing multiple waypoints, truck routing, positioning, etc.

# USAGE

## Installation Prerequisites

### Acquire HERE app credentials
To start using HLS Service Broker the cluster admin or service operator needs to register on [HERE Developer Portal](https://developer.here.com/plans?utm_medium=referral&utm_source=GitHub-Service-Broker&create=Freemium) and acquire app credentials by following these steps.

* Sign Up using any of the plans available at [Plans](https://developer.here.com/plans?utm_medium=referral&utm_source=GitHub-Service-Broker&create=Freemium) page.
* Create a project
* Generate an app for "REST & XYZ HUB API"
* Copy the APP ID & APP CODE generated.

### OpenShift command line client
* Install the OpenShift command line client (oc) by following [these](https://docs.openshift.com/enterprise/3.1/cli_reference/get_started_cli.html#installing-the-cli) instructions.

## Installation

### Deploy and register the broker

```bash
### Create a directory named hlssb
mkdir hlssb

### Change directory to hlssb
cd hlssb

### Fetch installation artifacts
wget https://raw.githubusercontent.com/heremaps/here-hls-service-broker/v1.0.0/deploy/openshift/deployment.sh
wget https://raw.githubusercontent.com/heremaps/here-hls-service-broker/v1.0.0/deploy/openshift/hls-service-broker.yaml
wget https://raw.githubusercontent.com/heremaps/here-hls-service-broker/v1.0.0/deploy/openshift/parameters.env

### Edit parameters.env and update the following parameters. Leave all other parameters as is.
# 1. HERE_TOKEN_ENDPOINT_URL, HERE_CLIENT_ID, HERE_ACCESS_KEY_ID & HERE_ACCESS_KEY_SECRET from the credentials file downloaded from the prerequisites step.
# 2. Highly recommend to change default BASIC_AUTH_USERNAME and BASIC_AUTH_PASSWORD properties. These credentials are required to register the broker with the OpenShift container catalog.
vi parameters.env

### Change deployment.sh execute permission
chmod +x deployment.sh

### Execute the deployment script
### By default the below script creates a project named hls-sb. If this needs to be changed, modify the deployment.sh file
./deployment.sh

### check that the broker is running:
oc get pods | grep hls-service-broker

### check service broker logs
oc logs $(oc get pods --no-headers -o name | grep hls-service-broker)

### Check if the broker service is up and running
curl <broker-url>/actuator/health

```

## Provisioning - Service Instance Creation
Once the deployment and registration steps are executed, the OpenShift Cluster Service Catalog will show the "HERE Location Services" as a catalog listing.
To create a Service Instance for HERE Location Services, select the "HERE Location Services" listing and follow the instructions.

## Binding - Create credentials
Binding creation option is provided during the instance creation step which can be used to create the binding along with the instance creation.
Alternately, binding can be created separately from the OpenShift Web UI as follows:

* Navigate to the project in which the instance has been created.
* Navigate to the "Applications" menu and select "Provisioned Services".
* Click on the "Create Binding" option and follow the instructions to create the binding.
* Provide an optional binding name in the configuration step. This binding name will be used to name the app created at HERE end for the binding.
* Once binding is done a Secret will be created which can be used to invoke the HLS APIs.

## Un-binding - Delete credentials
When the binding is no longer needed, it can be deleted from the same place it was created at.

## Un-provisioning - Delete instance
When the HLS Service instance is no longer required, it can be deleted from the "Provisioned Services" under "Applications" menu.

# ENVIRONMENT VARIABLES
Followings are the environment variables required to run the image container.

* **Basic Auth Credentials**: Basic auth credentials are used to enable auth security for the service broker available withing the image container.
The basic auth credentials include these two parameters.

```.env
brokerBasicAuthUsername=<provide-username-here>
brokerBasicAuthPassword=<provide-password-here>
```     

* **HERE OAuth 2.0 (JSON Web Tokens) Credentials** : HERE OAuth 2.0 Credentials include below parameters and can be acquired from [HERE Developer Portal](https://developer.here.com/sign-up?utm_medium=referral&utm_source=GitHub-Service-Broker&create=Freemium-Basic&keepState=true&step=terms).
These credentials are required for Broker API authentication in HERE ecosystem. This is the recommended way of authentication.
Please ignore these params for now. They will be used in future.

```.env
hereTokenEndpointUrl=<provide-token-endpoint-url-here>
hereClientId=<provide-client-id-here>
hereAccessKeyId=<provide-access-key-id-here>
hereAccessKeySecret=<provide-access-key-secret-here>
```  

* **HERE App Credentials**: HERE App Credentials include APP_ID and APP_CODE and is an old way of authentication. If you already have APP_ID and APP_ID, the same can be retrieved from [HERE Developer Portal](https://developer.here.com/sign-up?utm_medium=referral&utm_source=GitHub-Service-Broker&create=Freemium-Basic&keepState=true&step=terms).
These credentials are required for Broker API authentication in HERE ecosystem. We highly recommend to switch to a more secure OAuth 2.0 (JSON Web Tokens) based credentials. 
The APP_ID and APP_CODE parameter names are as follows:

```.env
hereAppId=<provide-app-id-here>
hereAppCode=<provide-app-code-here>
```  
* **HERE Integration Service URL**: This is the URL of the integration service the service broker interacts with in order to perform broker life-cycle operations.
The value of the parameter should be https://hls.integration.api.here.com.

```.env
hereHlsIntegrationServiceHostUrl=https://hls.integration.api.here.com
```  


# HISTORY
v1.0.5 - Deployment guidelines changes
v1.0.4 - First generally available broker container image version
v1.0.3 - Submitted for container review
v1.0.2 - Submitted for container review
v1.0.1 - Submitted for container review
v1.0.0 - Submitted for container review

# SECURITY IMPLICATIONS
Running this image container does not require any specific privileges. The image contains a service broker which should be registered 
with OpenShift Container Platform cluster (Service Catalog). This registration requires cluster admin privileges.