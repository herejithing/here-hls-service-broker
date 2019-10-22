#!/bin/bash

# Copyright (C) 2019 HERE Europe B.V.
# Licensed under Apache 2.0, see full license in LICENSE
# SPDX-License-Identifier: Apache-2.0

oc new-project hls-sb
CA=`oc get secret -n kube-service-catalog -o go-template='{{ range .items }}{{ if eq .type "kubernetes.io/service-account-token" }}{{ index .data "service-ca.crt" }}{{end}}{{"\n"}}{{end}}' | grep -v '^$' | tail -n 1`
oc process -f hls-service-broker.yaml --param-file=parameters.env -p BROKER_CA_CERT=$CA | oc apply -f -