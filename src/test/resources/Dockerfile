FROM openjdk:8
MAINTAINER Sorin Dan (dansorin2006@gmail.com)

USER root

#=============================================================
# Install Git
#=============================================================
RUN apt-get update && \
     apt-get install git -y

#=============================================================
# Install Maven
#=============================================================
RUN apt-get update && \
     apt-get install maven -y

#=============================================================
# Install Vim
#=============================================================
RUN apt-get update && \
     apt-get install vim -y

#=============================================================
# Clone code
#=============================================================
RUN git clone https://github.com/sorin86-dan/docker-selenium-test.git && \
    cd docker-selenium-test && \
    git pull

#=============================================================
# Build code
#=============================================================
RUN cd docker-selenium-test && \
    mvn clean install -DskipTests