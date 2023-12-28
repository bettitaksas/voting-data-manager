# Voting Data Manager

## Table of Contents
- [Introduction](#introduction)
- [Demo](#demo)
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
- [Usage](#usage)

## Introduction

The Office supports the work of parliamentary legislators, including tasks such as recording and querying voting data. The task at hand involves creating a Spring Boot-based application with a REST interface to handle the following fictional requirements:  

- Recording the data of a vote  
- Querying the vote cast by a representative in a specific vote  
- Calculating the result of the vote (accepted/rejected)

## Demo

You can find a Postman Collection Json file in the root directory to test the endpoints. It looks like this in action:

![](img/Képernyőkép 2023-12-28 225504.png)
![](img/Képernyőkép 2023-12-28 225836.png)
![](img/Képernyőkép 2023-12-28 225803.png)

## Prerequisites

All prerequisites or dependencies that you need to have installed before you can get started with this project:    

- Java Development Kit (JDK)
- Docker

## Getting Started

Steps you need to take to get this project up and running:

### Building the Project
    
Windows:

    gradlew clean build

Linux:

    ./gradlew clean build

### Create Docker image

    docker build -t your-app-name .

### Start Docker image

    docker run -p 8080:8080 -d your-app-name

## Usage
The VotingProcedureController class provides a RESTful interface for managing voting procedures. Below are the key operations and how to use them:

1. Save Voting Result

   Endpoint: POST /szavazasok/szavazas
 
   Response:  
   Status: 200 OK  
   Body: Details of the saved voting result

2. Get Vote by SzavazasId and Kepviselo

   Endpoint: GET /szavazasok/szavazat?szavazas={szavazasId}&kepviselo={kepviselo}

   Response:  
   Status: 200 OK  
   Body: Details of the vote cast by the specified representative in the given vote

3. Get Voting Result by Szavazas Id

   Endpoint: GET /szavazasok/eredmeny/{szavazasId}

   Response:  
   Status: 200 OK  
   Body: Calculated result of the specified vote

4. Get Voting Procedures by Day

   Endpoint: GET /szavazasok/napi-szavazasok/{day}

   Response:  
   Status: 200 OK  
   Body: List of voting procedures on the specified day

5. Get Average Participation of a Representative

   Endpoint: GET /szavazasok/kepviselo-reszvetel-atlag?kepviselo={kepviselo}&startDate={startDate}&endDate={endDate}

   Response:  
   Status: 200 OK  
   Body: Average participation result for the specified representative within the given date range

Note: In case of errors or not found situations, appropriate HTTP status codes are returned.
