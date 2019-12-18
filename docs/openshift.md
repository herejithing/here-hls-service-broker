# Getting Started on OpenShift

High-level architecture diagram for the Service Broker on Red Hat OpenShift.
![Service Broker for HERE Location Services](images/RedHat.png)

## Prerequisites

### HERE Developer Portal Steps
To start using this Service Broker you will need to register on [HERE Developer Portal](https://developer.here.com/sign-up?utm_medium=referral&utm_source=GitHub-Service-Broker&create=Freemium-Basic&keepState=true&step=terms) and acquire OAuth 2.0 credentials by following these steps:
* Sign Up for a HERE Account on the [HERE Developer Portal](https://developer.here.com/sign-up?utm_medium=referral&utm_source=GitHub-Service-Broker&create=Freemium-Basic&keepState=true&step=terms)
![HERE's Freemium Sign-Up](images/Developer_HERE_com_Sign_Up_720p.gif)
* Go to the REST Section and generate an App.
* Once the APP is generated, create an OAuth 2.0 (JSON Web Tokens) credentials under that and download the credentials file.
![HERE's Freemium Sign-Up](images/Developer_HERE_com_API_Key_720.gif)

### Red Hat OpenShift Installation Prerequisites
* [Install](https://docs.openshift.com/enterprise/3.1/cli_reference/get_started_cli.html#installing-the-cli) the OpenShift command line client (oc).

## Installation

```bash
### Create a directory to fetch the installation artefacts
mkdir hlssb
cd hlssb

### Fetch installation artefacts
wget https://raw.githubusercontent.com/heremaps/here-hls-service-broker/v1.0.2/deploy/openshift/deployment.sh
wget https://raw.githubusercontent.com/heremaps/here-hls-service-broker/v1.0.2/deploy/openshift/hls-service-broker.yaml
wget https://raw.githubusercontent.com/heremaps/here-hls-service-broker/v1.0.2/deploy/openshift/parameters.env

### Edit parameters.env and update the following parameters. Leave all other parameters as is.
# 1. HERE_TOKEN_ENDPOINT_URL, HERE_CLIENT_ID, HERE_ACCESS_KEY_ID & HERE_ACCESS_KEY_SECRET from the credentials file downloaded from the prerequisites step.
# 2. Highly recommend to change default BASIC_AUTH_USERNAME and BASIC_AUTH_PASSWORD properties. These credentials are required to register the broker with the OpenShift container catalog.
# 3. Update IMAGEPULLSECRETS parameter to pull the image container from Red Hat image registry
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

##
![OpenShift flow](images/hls-service-broker-openshift-flow.gif)