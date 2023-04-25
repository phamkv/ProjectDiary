## ProjectDiary - Personal Project

A diary application to create posts for each day. On each day, the posts from previous years are shown as well. Built with Spring Boot (Java), Svelte (HTML, CSS, JavaScript) and PostgreSQL. Deployed on fly.io.

Check out the live demo deployment at [projectdiary.phamkv.de](https://projectdiary.phamkv.de/)

## Project Screenshots

#### Example:   

<img src="https://user-images.githubusercontent.com/40146623/234266484-b6bf0c16-42c2-40e2-9c7a-19c9082abf27.png" width="400">
<img src="https://user-images.githubusercontent.com/40146623/234266489-929d1872-9594-46fc-a994-94389b403633.png" width="400">
<img src="https://user-images.githubusercontent.com/40146623/234266510-be58dd92-c931-4258-bd10-fdda106018d8.png" width="400">
<img src="https://user-images.githubusercontent.com/40146623/234266514-f533a7a8-921c-49aa-ba4d-ecd081017d2e.png" width="400">

## Installation and Setup Instructions

Clone down this repository. You will need Maven installed globally on your machine.  

Installation:

`mvn clean verify`

To Run Test Suite:  

`mvn test`  

To Start Server:

Make sure to setup a running PostgreSQL instance. Change credentials in backend/src/main/resources/application-local.properties (Recommended: Use the postgres Docker image)

`java -jar backend/target/backend-0.0.1-SNAPSHOT.jar --spring.profiles.active=local`  

To Visit App:

`localhost:8080` 

## Project Status

This project is currently in development. It has all the main functionalities. Users can sign up for an account and create posts for specific days. The date can be selected using a calendar picker. Functionality to edit existing posts as well as more test suites are in progress.