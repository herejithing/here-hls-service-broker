#!/usr/bin/env bash
# Go should be installed to run the below command.
# Clone or download the github project https://github.com/cpuguy83/go-md2man to get the md2man.go utility
# Path for md2man.go file should be vailable in the PATH variable
go run md2man.go -in ../docs/openshift/openshift-help.md -out ../docs/openshift/help.1