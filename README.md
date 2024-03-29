# Running parallel tests in Docker containers

1. Create a Docker network with a Selenium hub, 2 chrome nodes and 2 firefox nodes.
2. Start running the tests.

It's just as simply as that!

## Linux (Ubuntu)

### Create Docker network for Selenium tests 
To build the Docker network make sure you have a valid Internet connection and run the commands below:
```shell script
 docker network create --subnet=172.0.0.0/16 grid
 docker run -d -p 4444:4444 --net grid --ip 172.0.0.2 --name selenium-hub selenium/hub:3.141.59-zinc
 
 docker run -d --net grid --ip 172.0.0.3 -e HUB_HOST=selenium-hub -v /dev/shm:/dev/shm selenium/node-chrome:3.141.59-zinc
 docker run -d --net grid --ip 172.0.0.4 -e HUB_HOST=selenium-hub -v /dev/shm:/dev/shm selenium/node-firefox:3.141.59-zinc
 docker run -d --net grid --ip 172.0.0.5 -e HUB_HOST=selenium-hub -v /dev/shm:/dev/shm selenium/node-chrome:3.141.59-zinc
 docker run -d --net grid --ip 172.0.0.6 -e HUB_HOST=selenium-hub -v /dev/shm:/dev/shm selenium/node-firefox:3.141.59-zinc
```

### Check the load on the nodes
You need to open Selenium hub page for this. So run commands
```shell script
docker ps
docker inspect <container-id-for-selenium-hub> | grep "IPAddress"
```
and open in browser: **http://[ip-address-from-previous-step]:4444/grid/console**

If you want to open the selenium node containers create the grid with the following commands:
```shell script
 docker network create --subnet=172.0.0.0/16 grid
 docker run -d -p 4444:4444 --net grid --ip 172.0.0.2 --name selenium-hub selenium/hub:3.141.59-zinc
 
 docker run -d -P -p 5901:5900 --net grid --ip 172.0.0.3 -e HUB_HOST=selenium-hub --link selenium-hub:hub -v /dev/shm:/dev/shm selenium/node-chrome-debug:3.141.59-zinc
 docker run -d -P -p 5902:5900 --net grid --ip 172.0.0.4 -e HUB_HOST=selenium-hub --link selenium-hub:hub -v /dev/shm:/dev/shm selenium/node-firefox-debug:3.141.59-zinc
 docker run -d -P -p 5903:5900 --net grid --ip 172.0.0.5 -e HUB_HOST=selenium-hub --link selenium-hub:hub -v /dev/shm:/dev/shm selenium/node-chrome-debug:3.141.59-zinc
 docker run -d -P -p 5904:5900 --net grid --ip 172.0.0.6 -e HUB_HOST=selenium-hub --link selenium-hub:hub -v /dev/shm:/dev/shm selenium/node-firefox-debug:3.141.59-zinc
```
and open Selenium hub page in browser as explained above. You need to install VNC Viewer and connect to **[ip-address-of-node]:[port-of-node]** where **[ip-address-of-node]** can be taken from the Selenium hub page and **[port-of-node]** is the one specified in the command above with **590x** (the default password is *secret*). 

## Windows

### Create Docker network for Selenium tests
To build the Docker network make sure you have a valid Internet connection and run the commands below:
```shell script
 docker network create --subnet=172.0.0.0/16 grid
 docker run -d -p 4444:4444 --net grid --ip 172.0.0.2 --name selenium-hub selenium/hub:3.141.59-zinc
 
 docker run -d --net grid --ip 172.0.0.3 -e HUB_HOST=selenium-hub -v /c:/dev/shm selenium/node-chrome:3.141.59-zinc
 docker run -d --net grid --ip 172.0.0.4 -e HUB_HOST=selenium-hub -v /c:/dev/shm selenium/node-firefox:3.141.59-zinc
 docker run -d --net grid --ip 172.0.0.5 -e HUB_HOST=selenium-hub -v /c:/dev/shm selenium/node-chrome:3.141.59-zinc
 docker run -d --net grid --ip 172.0.0.6 -e HUB_HOST=selenium-hub -v /c:/dev/shm selenium/node-firefox:3.141.59-zinc
```

### Check the load on the nodes
You need to open Selenium hub page for this. So open in browser: **http://localhost:4444/grid/console**

If you want to open the selenium node containers create the grid with the following commands:
```shell script
 docker network create --subnet=172.0.0.0/16 grid
 docker run -d -p 4444:4444 --net grid --ip 172.0.0.2 --name selenium-hub selenium/hub:3.141.59-zinc
 
 docker run -d -P -p 5901:5900 --ip 172.0.0.3 --net grid -e HUB_HOST=selenium-hub --link selenium-hub:hub -v /c:/dev/shm selenium/node-chrome-debug:3.141.59-zinc
 docker run -d -P -p 5902:5900 --ip 172.0.0.4 --net grid -e HUB_HOST=selenium-hub --link selenium-hub:hub -v /c:/dev/shm selenium/node-firefox-debug:3.141.59-zinc
 docker run -d -P -p 5903:5900 --ip 172.0.0.5 --net grid -e HUB_HOST=selenium-hub --link selenium-hub:hub -v /c:/dev/shm selenium/node-chrome-debug:3.141.59-zinc
 docker run -d -P -p 5904:5900 --ip 172.0.0.6 --net grid -e HUB_HOST=selenium-hub --link selenium-hub:hub -v /c:/dev/shm selenium/node-firefox-debug:3.141.59-zinc
```
and open Selenium hub page in browser as explained above. You need to install VNC Viewer and connect to **localhost:[port-of-node]** where **[port-of-node]** is the one specified in the command above with **590x** (the default password is *secret*). 

# Running parallel tests with Kubernetes

1. Create a Kubernetes network with a Selenium hub, 2 Chrome nodes and 2 Firefox nodes.
2. Update the ip and port in BaseTests.java with the value taken from step 1.
3. Start running the tests.

It's just as simply as that!

## Minikube

### Create Kubernetes network for Selenium tests
To build the Kubernetes network make sure you have a valid Internet connection and run the commands below:
```shell script
kubectl apply -f src/test/resources/selenium-node-chrome-replica.yaml
kubectl apply -f src/test/resources/selenium-node-firefox-replica.yaml
kubectl apply -f src/test/resources/selenium-node-hub-deploy.yaml
kubectl apply -f src/test/resources/selenium-node-hub-service.yaml
```

### Check the load on the nodes
You need to open Selenium hub page for this. So run command
```shell script
minikube service selenium-hub --url
```
and open in browser: **http://[ip-address-and-port-from-previous-step]/grid/console**

If you want to check the logs run command
```shell script
minikube dashboard
```

and on the dashboard page go to **Workloads ->Pods**. From there you can choose for which element from Selenium Grid (which pod) you want to see the logs. Click the chosen pod and on the top right click the leftest button (**View Logs**).

## Microk8s

### Create Kubernetes network for Selenium tests
To build the Kubernetes network make sure you have a valid Internet connection, set service type to be NodePort and run the commands below:
```shell script
microk8s kubectl apply -f src/test/resources/selenium-node-hub-deploy.yaml
# Make sure that you have in src/test/resources/selenium-node-hub-service.yaml set the value 'type: LoadBalancer'
microk8s kubectl apply -f src/test/resources/selenium-node-hub-service.yaml
microk8s kubectl apply -f src/test/resources/selenium-node-chrome-replica.yaml
microk8s kubectl apply -f src/test/resources/selenium-node-firefox-replica.yaml
microk8s kubectl port-forward service/selenium-hub 4444:4444
```

### Check the load on the nodes
Open in browser: **http://127.0.0.1/grid/console**

If you want to check the logs run commands
```shell script
microk8s.enable dashboard

# For MicroK8s 1.24 or newer:
microk8s kubectl create token default
# For MicroK8s 1.23 or older:
token=$(microk8s kubectl -n kube-system get secret | grep default-token | cut -d " " -f1)
microk8s kubectl -n kube-system describe secret $token

microk8s kubectl port-forward -n kube-system service/kubernetes-dashboard 10443:443
```

and you can access the dashboard page at the url https://127.0.0.1:10443. In the dashboard page go to **Workloads ->Pods**. From there you can choose for which element from Selenium Grid (which pod) you want to see the logs. Click the chosen pod and on the top right click the leftest button (**View Logs**).

# Blog articles

For more info please check: 
 - https://t3ch5tuff5.wordpress.com/2020/09/21/containerization-in-test-automation-i-containerization-with-ui-testing/;
 - https://t3ch5tuff5.wordpress.com/2022/06/01/%ef%bf%bctest-automation-and-containerization-iv-ui-testing-using-selenium-grid-and-kubernetes/.

