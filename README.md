# ContactBook-App_spring-boot
A contact book rest api application that supports basic CRUD.

This application requires :
Postgres server installed on your machine. 
Java 1.8 SDK
Maven build tool

Kindly follow the steps to set up database connection properly:

1. Go to src/main/resources
2. Open application.properties
3.  spring.datasource.url=jdbc:postgresql://<yout_host>:<your_port_no>/<your_db>
    spring.datasource.username=<username>
    spring.datasource.password=<password>
    
Run the application as mvn spring-boot:run from command line. 
