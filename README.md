# simplehttpserver
This is a simple http server written in Java for handling GET and POST requests

# Prerequisites
 + Java 
 + Maven 3.x.x

# Setup on linux
Once you have cloned the project to a server you can browse to the root dir and type
`mvn clean test`
This will check to see if all tests are running ok.
After that you can run
`mvn clean install`
`mvn exec:java`
This will start the instance that will allow all IPs to connect on port 8080.

# GET Requests
Data is returned in JSON format and include HTTP status codes.

+ To return all the counters:
`http://someloaction/counters`

+ To return a specific counter:
`http://someloaction/counters/get/insertcounternamehere`

#POST Requests
You can use [Advanced Rest Client](https://chrome.google.com/webstore/detail/advanced-rest-client/hgmloofddffdnphfgcellkdfbfbjeloo)
if you need a simple tool to perform POST requests.

+ Increase the value of a named counter
`http://someloaction/counters/increase`
+ In the header data
`key: insertcounternamehere`

+ Add a new counter
`http://someloaction/counters/add`
+ In the header data
`key: insertcounternamehere`

#Travis
The project now has Travis support. You can view it [here](https://travis-ci.org/poixen/simplehttpserver).