FROM openjdk:8-jdk-alpine
# ----
# Install Maven
RUN apk add --no-cache curl tar bash git
ARG MAVEN_VERSION=3.3.9
ARG USER_HOME_DIR="/root"
RUN mkdir -p /usr/share/maven && \
curl -fsSL http://apache.osuosl.org/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz | tar -xzC /usr/share/maven --strip-components=1 && \
ln -s /usr/share/maven/bin/mvn /usr/bin/mvn
ENV MAVEN_HOME /usr/share/maven
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"
# speed up Maven JVM a bit

ENV PORT 8080
ENV CLASSPATH /opt/lib

ARG GATEWAY_HOST_ARG
ENV GATEWAY_HOST=$GATEWAY_HOST_ARG

ARG SSO_HOST_ARG
ENV SSO_HOST=$SSO_HOST_ARG

ARG REALM_ARG
ENV REALM=$REALM_ARG

EXPOSE 8080
RUN git clone https://github.com/almerico/activiti-cloud-acceptance-tests.git

CMD [ "cd activiti-cloud-acceptance-tests&&mvn clean install -DskipTests && mvn clean verify" ]
