# Air Traffic Control System
## Overview

This is a simple web app that simulates an air traffic control system. The app consists of a collection of REST API endpoints and a simple single page web app that is implemented in React.js.

The main logic resides in the `AqmProcessor` class under the `src/main/java/airtrafficcontrol/processor/` directory. The `aqmRequestProcess` procedure requested in the assessment also lives in that class. All the  methods in the Rest controller directly call that class. 

Since every major class has a top level comment explaining what it does, the code can be followed starting from `AqmProcessor`. 

## Technology Used

This app is built using the following tools. I decided to use these tools based on the description of this role at Binary Fountain. I thought all these tools and languages would be relevant for the role.
- **Java 8**
- **Gradle 4.1** - Build/dependency manager for the server.
- **Spring 4.3.10 and Spring Boot 1.5.6** - A java framework for the server.
- **React 15.3.2** - Front end framework.
- **Node v8.4.0 and npm 5.3.0** - Build management for the frontend.
- **Webpack 1.15.0** - Bundling and ES6 transpiler.
- **Bootstrap 3** - CSS for the UI

## How to run

### Pre-requisites
1. Java 8
2. Node and npm (These are not strictly required for running the app because the bundled JS is already included in this project. They are required for development, however.)

### Steps
##### 1 Build the server

The server can be built using the following commands. The file, `gradlew` is a wrapper for a `gradle` executable. `gradlew` is for Unix based systems. For windows, please use `gradlew.bat`. **NOTE:** This was developed and tested on a Mac OS laptop running macOS Sierra.

```
./gradlew clean
./gradlew build
```

##### 2 Bundle the JS assets (Optional)

This doesn't need to be run unless changes are made to the source JS files. There's already a built directory with bundled JS so, this can be skipped.

```
npm install
npm run build
```

##### 3 Run the server.


```
java -jar build/libs/air-traffic-control-0.1.0.jar
```

##### 4 Navigate to web app.

The app will be listening at `localhost:8080`.

## IDE

This app was developed using the IntelliJ IDEA IDE. To run this in IntelliJ, create a gradle project from existing sources. IntelliJ will detect the `build.gradle` file and set it up as a Gradle application accordingly.

## Tests

There are both unit and integration tests included in this project. They're all under the `src/test/java` directory and can be run separately within an IDE. They can also be run as a whole from the command line using the following command:
```$xslt
./gradlew build
```

## Endpoints

### POST `localhost:8080/api/v1/queue`
This takes the object in the request body and performs the operation described by the object. The request body must contain a `type` field which can be one of [`ENQUEUE`, `DEQUEUE`, `START`]. For `ENQUEUE` requests, an `airCraft` field also needs to be provided representing the aircraft being added into the queue.

- **Sample Request Body** 
```$xslt
{
   "airCraft":{
      "type":"CARGO",
      "size":"SMALL"
   },
   "type" : "ENQUEUE"
}
```
- **Sample Response Body**
```$xslt
{
    "entry": {
        "airCraft": {
            "type": "CARGO",
            "size": "SMALL"
        },
        "requestTime": "2017-09-20T17:17:32.107Z"
    },
    "successful": true
}
```


### GET `localhost:8080/api/v1/queue`

This retrieves the contents of the queue in the server.

- **Sample Response Body**
```$xslt
[
     {
         "airCraft": {
             "type": "PASSENGER",
             "size": "LARGE"
         },
         "requestTime": "2017-09-20T17:41:36.469Z"
     },
     {
         "airCraft": {
             "type": "PASSENGER",
             "size": "LARGE"
         },
         "requestTime": "2017-09-20T17:41:42.339Z"
     },
     {
         "airCraft": {
             "type": "PASSENGER",
             "size": "SMALL"
         },
         "requestTime": "2017-09-20T19:25:26.242Z"
     },
     {
         "airCraft": {
             "type": "CARGO",
             "size": "SMALL"
         },
         "requestTime": "2017-09-20T19:25:12.433Z"
     }
 ]
```


### GET `localhost:8080/api/v1/queue/start`
This is a handy shortcut to the `POST localhost:8080/api/v1/queue` API with the following request body:
```$xslt
{
   "type" : "START"
}
```


### GET `localhost:8080/api/v1/queue/dequeue`
This is a handy shortcut to the `POST localhost:8080/api/v1/queue` API with the following request body:
```$xslt
{
   "type" : "DEQUEUE"
}
```

