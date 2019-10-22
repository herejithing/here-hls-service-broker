# Open JDK8 image having apline OS
FROM openjdk:8-jdk-alpine

# Adding labels
LABEL name="HLS-Service-Broker" \
      vendor="HERE Technologies" \
      version="1.0" \
      release="1" \
      summary="Open Service Broker for HERE Location Services" \
      description="HLS Service Broker will enable provisioning of HLS Freemium Credentials directly from your platform on which HLS Service Broker is installed"

# Install bash to the os
RUN apk add --no-cache bash

# Add VOLUME pointing to /tmp to create working directories for tomcat
VOLUME /tmp

# Point DEPENDENCY to target/dependency where the fat jar is unpacked
ARG DEPENDENCY=target/dependency

# Copy the dependency jars
COPY ${DEPENDENCY}/BOOT-INF/lib /broker/lib

# Copy the META-INF directory
COPY ${DEPENDENCY}/META-INF /broker/META-INF

# Copy the class files of the project
COPY ${DEPENDENCY}/BOOT-INF/classes /broker

# Copy the startup script
COPY script/start-broker.sh /usr/local/bin/start-broker.sh

# Assign execute permissions to the startup scrip
RUN chmod +x /usr/local/bin/start-broker.sh

# Start the application
CMD ["start-broker.sh"]
EXPOSE 8080