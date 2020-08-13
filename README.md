#Virus Inspector

#Motivation
"I am building an application where my user will be able to upload files, And I want to make sure there are no executable files. i.e. no virus"

If the above statement is your business need then this application can help you to achieve this.

#Build status
 todo http://www.codeblocq.com/2016/04/Setup-Travis-CI-with-your-node-project/
## High level design 
![Overall Design](overAllDesign.png)

#Tech and Framework used
  
  This is a Spring boot application. The Rest infrastructure port expose the rest endpoints. 
 
  ClamAV Demon is one of tfhe infrastructure port. Where all the scanning logic runs.
  The daemon listens for incoming connections on Unix and/or TCP socket and scans files or directories on demand. More details here http://linux.die.net/man/8/clamd
  Detail about clam AV is available https://www.clamav.net/documents/clam-antivirus-user-manual

  This is using Maven as a build tool
  This has Cucurmber for BDD and Junit for TDD 
  
## An example to build on


# Usage

```
  mvn package
```

## Setting up local clamd virtual server


# Testing the REST service```


#License
ClamAV is licensed under the GNU General Public License, Version 2.
