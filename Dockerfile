FROM registry.access.redhat.com/ubi8/openjdk-11

ARG JAVA_PACKAGE=java-11-openjdk-headless
ARG JMETER_VERSION=5.4.1
ENV LANG='en_US.UTF-8' LANGUAGE='en_US:en'

USER 0
RUN mkdir -p /opt/redhat/
COPY files/jboss-eap-7.4.3-mp.zip /opt/redhat

RUN chown -R 1001:0 /opt/redhat

# Install java 
RUN microdnf install gzip ${JAVA_PACKAGE} \
    && microdnf update \
    && microdnf clean all \
    && echo "securerandom.source=file:/dev/urandom" >> /etc/alternatives/jre/conf/security/java.security

WORKDIR /opt/redhat/

RUN unzip /opt/redhat/jboss-eap-7.4.3-mp.zip

#add admin admin
COPY mgmt-users.properties /opt/redhat/jboss-eap-7.4/standalone/configuration
COPY target/eap74-microprofile-hello.war /opt/redhat/jboss-eap-7.4/standalone/deployment

RUN chown -R 1001:0 /opt/redhat

USER 1001

EXPOSE 8080
EXPOSE 9990

WORKDIR /opt/redhat/jboss-eap-7.4/bin

ENTRYPOINT ["./standalone.sh" ]
CMD ["-c", "standalone-microprofile.xml", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
# CMD ["sh", "-c", "tail -f /dev/null"]
