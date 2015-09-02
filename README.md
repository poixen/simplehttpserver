# simplehttpserver
This is a simple http server written in Java for handling GET and POST resquests

# Prerequisites
 + Java 
 + Maven 3.x.x

# Setup on linux
Once you have cloned the project to a server you can browse to the root dir and type
`mvn clean test`
This will check to see if all tests are running ok.
After that you can run
`mvn exec:java`
This will start the instance that will allow all IPs to connect on port 8080.

# GET Requests
You can perform 2 GET requests. The first will return all the counter data
`http://url.com/counters`

The second will return a specific value of the passed in name
`http://url.com/counters/get?key=insertcounternamehere
If there is no key with the name passed, you will be informed.

#POST Requests
You can use [Advanced Rest Client](https://chrome.google.com/webstore/detail/advanced-rest-client/hgmloofddffdnphfgcellkdfbfbjeloo)
if you need a simple tool to perform POST requests.

Increase the value of a named counter
`http://url.com/counters/increase`
In the header data
`key: insertcounternamehere`
If there is no key with the name passed, you will be informed.

Add a new counter
`http://url.com/counters/add`
In the header data
`key: insertcounternamehere`
If there is a problem with adding the new counter, you will be informed.

#Travis
The project now has Travis support. You can view it [here](https://travis-ci.org/poixen/simplehttpserver).