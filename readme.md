# Overview

Application is written using:
1. JDK 17.0.4
2. Spring Boot 2.7.3
3. H2 in-memory database

Database is created and initialized with sample content using schema.sql and data.sql files

# How to build 

mvn clean install

# How to run

java -jar  target/task-1.0.0-SNAPSHOT.jar

Application starts on port 8085

# Endpoints

There are two endpoints exposed:
1. localhost:8085/getRewardPoints/{customerId}
    - Returns reward points for given Customer ID  
2. localhost:8085/getRewardPoints
    - Returns reward points for all customers in database

Example response:

    {
      "total": 230,
      "rewardPointsByMonth": {
         "7": 70,
         "8": 70,
         "9": 90
      }
    }

- total: summary value for three months
- rewardPointsByMonth: map with points for given month. "7" is for July, "8" for August and so on