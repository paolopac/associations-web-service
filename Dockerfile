# ==============================================================
# ==== Docker File Creazione Immagine Associations-Micro-Service =====
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
RUN apt-get update -y && apt upgrade -y && apt install curl -y

COPY /target/ASSOCIATIONS-WEB-SERVICE-0.3.1-SNAPSHOT.jar th-prj-ms-associations.jar

ENTRYPOINT exec java $JAVA_OPTS $Xmx -XX:+UseSerialGC $Xss -jar th-prj-ms-associations.jar

#Generazione Immagine:
# docker build -t th-prj-ms-associations .

# Upload in dockerhub:

# docker login 

# docker tag 40cf27f3490e3803161045e2b4dbcd0bd59d7b81f76d2e11b4e3f74a727aa251 paoloacqua/th-prj-ms-associations

# docker push paoloacqua/th-prj-ms-associations

