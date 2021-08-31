# ==============================================================
# ==== Docker File Creazione Immagine Eureka-Web-Service =====
# ==============================================================

FROM openjdk:11-jre-slim
LABEL maintainer="Paolo Acquaviva <paoloacqua@hotmail.it>"

ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS
# ARG Xmx # Abilitare nel caso si voglia resettare, rivalorizzare tale valore in fase di build
# ARG Xss # Abilitare nel caso si voglia resettare, rivalorizzare tale valore in fase di build
ENV Xmx=-XX:MaxRAM=98m Xss=-Xss512k
ENV LC_ALL it_IT.UTF-8
ENV LANG it_IT.UTF-8
ENV LANGUAGE it_IT.UTF-8

WORKDIR /webapi

VOLUME ["/logs"]

RUN apt-get update -y && apt-get install -y locales locales-all

COPY /target/ASSOCIATIONS-WEB-SERVICE-0.2.1-SNAPSHOT.jar associations-web-service.jar

ENTRYPOINT exec java $JAVA_OPTS $Xmx -XX:+UseSerialGC $Xss -jar associations-web-service.jar

#Generazione Immagine:
# docker build -t associations-web-service .

# Upload in dockerhub:

# docker login 

# docker tag 5ac3be4713ab paoloacqua/associations-web-service

# docker push paoloacqua/associations-web-service

