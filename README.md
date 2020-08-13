# Virus Inspector

## Motivation
"I am building an application where my user will be able to upload files, And I want to make sure there are no executable files. i.e. no virus"

If the above statement is your business need then this application can help you to achieve this.

## Build status
 todo http://www.codeblocq.com/2016/04/Setup-Travis-CI-with-your-node-project/
## High level design 
![Overall Design](overAllDesign.png)

## Tech and Framework used
  
  This is a Spring boot application. The Rest infrastructure port expose the rest endpoints. 
 
  ClamAV Demon is one of the infrastructure port. Where all the scanning logic runs.
  The daemon listens for incoming connections on Unix and/or TCP socket and scans files or directories on demand. More details here http://linux.die.net/man/8/clamd
  Detail about clam AV is available https://www.clamav.net/documents/clam-antivirus-user-manual

  This is using Maven as a build tool.
  This has Cucurmber for BDD and Junit for TDD 

## Setting up clamd server

Clamd should be up and running before running the virus inspector service. 

To start Clamd pull and run [clamd Docker image](https://hub.docker.com/r/mkodockx/docker-clamav).
```
docker run -d -p 3310:3310 mk0x/docker-clamav:alpine
```

## Usage
Virus inspector can be build using build tool like maven
steps: 
```
git clone https://github.com/anantshr/Virus-inspector.git
```
```
cd Virus-inspector
```
update application.yml file if your clamd server is running on other VM.
```
clamd:
  hostname: localhost
  port: 3310
  timeout: 50000
```
from root of your project run maven build.
```
mvn package
```
once build is success starting the REST service is quite straightforward
```
java -jar virus-inspector-0.0.1.jar 
```

## Testing the REST service
```
curl localhost:8080
Application is responding: true

curl --output -F "file=<file>" -X POST localhost:8080/diagnosticReport 
It is a Good File : true
```
## License
ClamAV is licensed under the GNU General Public License, Version 2.
