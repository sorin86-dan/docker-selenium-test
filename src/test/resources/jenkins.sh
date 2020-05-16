#!/bin/bash

chmod 400 awsKey
ssh -tto StrictHostKeyChecking=no -i awsKey ec2-user@${PUT_HERE_IP_ADDRESS} sudo yum update -y
ssh -tto StrictHostKeyChecking=no -i awsKey ec2-user@${PUT_HERE_IP_ADDRESS} sudo yum install docker -y
ssh -tto StrictHostKeyChecking=no -i awsKey ec2-user@${PUT_HERE_IP_ADDRESS} sudo service docker start
ssh -tto StrictHostKeyChecking=no -i awsKey ec2-user@${PUT_HERE_IP_ADDRESS} sudo usermod -a -G docker ec2-user
ssh -tto StrictHostKeyChecking=no -i awsKey ec2-user@${PUT_HERE_IP_ADDRESS} sudo curl -L "https://github.com/docker/compose/releases/download/1.23.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
ssh -tto StrictHostKeyChecking=no -i awsKey ec2-user@${PUT_HERE_IP_ADDRESS} sudo chmod +x /usr/local/bin/docker-compose
scp -i awsKey local-docker-parallelization/src/test/resources/Dockerfile ec2-user@${PUT_HERE_IP_ADDRESS}:/home/ec2-user/
scp -i awsKey local-docker-parallelization/src/test/resources/docker-compose.yml ec2-user@${PUT_HERE_IP_ADDRESS}:/home/ec2-user/
ssh -tto StrictHostKeyChecking=no -i awsKey ec2-user@${PUT_HERE_IP_ADDRESS} docker-compose up -d
ssh -tto StrictHostKeyChecking=no -i awsKey ec2-user@${PUT_HERE_IP_ADDRESS} docker build -t clone-image .
ssh -tto StrictHostKeyChecking=no -i awsKey ec2-user@${PUT_HERE_IP_ADDRESS} docker run -d --net ec2-user_grid --name automated-code -it clone-image
ssh -tto StrictHostKeyChecking=no -i awsKey ec2-user@${PUT_HERE_IP_ADDRESS} docker exec -w /docker-selenium-test/ automated-code mvn clean test