# BSPQ20-E4
![9f2d5def-bced-4419-b239-b7130270cc02_200x200](https://user-images.githubusercontent.com/43065732/80421676-25b52580-88dd-11ea-870e-861f738dead6.png)
[![Build Status](https://travis-ci.org/SPQ19-20/BSPQ20-E4.svg?branch=master)](https://travis-ci.org/SPQ19-20/BSPQ20-E4)
# About the project

EasyFilmin is a platform where film lovers can share their taste in films. Users can use it as a diary to:
* Record their opinions about films and rate them
* Keep track of films they have seen
* Create watchlists

## Prerequisites
- [Java JDK 12](https://www.oracle.com/java/technologies/javase/jdk12-archive-downloads.html)
- [MySQL](https://dev.mysql.com/downloads/mysql/)
- [MySQL Workbench](https://www.mysql.com/products/workbench/)
- [Maven](https://maven.apache.org/download.cgi)

🠮Also the following libraries should be added to *pom.xml* file for the project to work: **Jetty, JUnit, OpenCSV, MySQL, Datanucleus, Log4J, Jersey, Jacoco, ContiPerf, DOxygen.**

# Building and running the project
These are the steps that must be followed in order to succesfully build and run the project:

*cmd's must be opened in the directory where* ***pom.xml*** *is located.*
### 1- Compile the project:
```mvn clean compile```
### 2- Execute .sql script in MySQL WorkBench:
Click on the thunder icon and refesh tables.
### 3- Create SQL schema: 
```mvn datanucleus:schema-create```
### 4- Run Web Server: 
```mvn jetty:run```
### 5- Run App as Admin: 
```mvn exec:java -Padmin``` 

(**ONLY** in case data from CSV files has not been previously stored)

*open another cmd window to run client side*
### 6- Run Client App:
```mvn exec:java -Pclient``` 

It executes Client's main and the Register/Login window will show up.

### 7- Run Unit Tests:
```mvn test``` 
### 8- Generate site:
```mvn site``` 
- To check *Jacoco results*: go to ***target\site\jacoco\index.html***
- To check *ContiPerf results*: go to ***target\contiperf-report\index.html***
- To check *DOxygen documentation*: go to ***target\site\doxygen\html\index.html*** *(after creating DOxygen report)*

### 9- Create DOxygen documentation:
- To generate doxygen reports:
``` mvn doxygen:report``` 
- To copy generated html directory into docs folder:
``` mvn validate``` 
- To remove generated target files including dir docs with html code:
``` mvn clean```

This project contains 3 SystemMessage properties files for it to be translated into **Spanish, English or Basque.**

# Authors
- **Ander Eguiluz:** [eguiwow](https://github.com/eguiwow)
- **Marcos Barcina:** [Marcoos01](https://github.com/Marcoos01)
- **Elena Alonso:** [elenalonso](https://github.com/elenalonso)
- **Borja Díaz de Otazu:** [Borjados](https://github.com/Borjados)




