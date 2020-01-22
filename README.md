# Selenium tests with Docker

1. Run commands from [docker-selenium-grid](src/test/resources/docker-selenium-grid) to start selenium hub, a chrome node and a firefox node.
2. Run `docker inspect [containerID]` on selenium-hub to retrieve ip address (where containerID is retrieved from `docker ps`  command).
3. Open in browser `http://[ip-selenium-hub]:4444/grid/console` and retrieve the ip from chrome node and firefox node.
4. Set in [config.properties](src/test/resources/config.properties) the ip for chrome and firefox node.
5. Start running the tests.