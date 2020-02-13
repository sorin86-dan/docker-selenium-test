# Running parallel tests in Docker containers

1. Create a Docker network with a Selenium hub, 2 chrome nodes and 2 firefox nodes.
2. Start running the tests.

It's just as simply as that!

## Linux (Ubuntu)

### Create Docker network for Selenium tests 
To build the Docker network make sure you have a valid Internet connection and run the commands below:
```shell script
 docker network create grid
 docker run -d -p 4444:4444 --net grid --name selenium-hub selenium/hub:3.141.59-zinc
 
 docker run -d --net grid -e HUB_HOST=selenium-hub -v /dev/shm:/dev/shm selenium/node-chrome:3.141.59-zinc
 docker run -d --net grid -e HUB_HOST=selenium-hub -v /dev/shm:/dev/shm selenium/node-firefox:3.141.59-zinc
 docker run -d --net grid -e HUB_HOST=selenium-hub -v /dev/shm:/dev/shm selenium/node-chrome:3.141.59-zinc
 docker run -d --net grid -e HUB_HOST=selenium-hub -v /dev/shm:/dev/shm selenium/node-firefox:3.141.59-zinc
```

### Check the load on the nodes
You need to open Selenium hub page for this. So run commands
```shell script
docker ps
docker inspect <container-id-for-selenium-hub> | grep "IPAddress"
```
and open in browser: **http://<ip-address-from-previous-step>:4444/grid/console**

If you want to open the selenium node containers create the grid with the following commands:
```shell script
 docker network create grid
 docker run -d -p 4444:4444 --net grid --name selenium-hub selenium/hub:3.141.59-zinc
 
 docker run -d -P -p 5901:5900 --net grid -e HUB_HOST=selenium-hub -v /dev/shm:/dev/shm selenium/node-chrome:3.141.59-zinc
 docker run -d -P -p 5902:5900 --net grid -e HUB_HOST=selenium-hub -v /dev/shm:/dev/shm selenium/node-firefox:3.141.59-zinc
 docker run -d -P -p 5903:5900 --net grid -e HUB_HOST=selenium-hub -v /dev/shm:/dev/shm selenium/node-chrome:3.141.59-zinc
 docker run -d -P -p 5904:5900 --net grid -e HUB_HOST=selenium-hub -v /dev/shm:/dev/shm selenium/node-firefox:3.141.59-zinc
```
and open Selenium hub page in browser as explained above. You need to install VNC Viewer and connect to **<ip-address-of-node>:<port-of-node>** where **<ip-address-of-node>** can be taken from the Selenium hub page and **<port-of-node>** is the one specified in the command above with **590x**. 

## Windows

### Create Docker network for Selenium tests
To build the Docker network make sure you have a valid Internet connection and run the commands below:
```shell script
 docker network create grid
 docker run -d -p 4444:4444 --net grid --name selenium-hub selenium/hub:3.141.59-zinc
 
 docker run -d --net grid -e HUB_HOST=selenium-hub -v /c:/dev/shm selenium/node-chrome:3.141.59-zinc
 docker run -d --net grid -e HUB_HOST=selenium-hub -v /c:/dev/shm selenium/node-firefox:3.141.59-zinc
 docker run -d --net grid -e HUB_HOST=selenium-hub -v /c:/dev/shm selenium/node-chrome:3.141.59-zinc
 docker run -d --net grid -e HUB_HOST=selenium-hub -v /c:/dev/shm selenium/node-firefox:3.141.59-zinc
```

### Check the load on the nodes
You need to open Selenium hub page for this. So open in browser: **http://localhost:4444/grid/console**

If you want to open the selenium node containers create the grid with the following commands:
```shell script
 docker network create grid
 docker run -d -p 4444:4444 --net grid --name selenium-hub selenium/hub:3.141.59-zinc
 
 docker run -d -P -p 5901:5900 --net grid -e HUB_HOST=selenium-hub -v /c:/dev/shm selenium/node-chrome:3.141.59-zinc
 docker run -d -P -p 5902:5900 --net grid -e HUB_HOST=selenium-hub -v /c:/dev/shm selenium/node-firefox:3.141.59-zinc
 docker run -d -P -p 5903:5900 --net grid -e HUB_HOST=selenium-hub -v /c:/dev/shm selenium/node-chrome:3.141.59-zinc
 docker run -d -P -p 5904:5900 --net grid -e HUB_HOST=selenium-hub -v /c:/dev/shm selenium/node-firefox:3.141.59-zinc
```
and open Selenium hub page in browser as explained above. You need to install VNC Viewer and connect to **localhost:<port-of-node>** where **<port-of-node>** is the one specified in the command above with **590x**. 


