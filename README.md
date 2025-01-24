# SSE-CIG
This project demonstrates a Server-Sent Events (SSE) implementation for real-time email notifications, coupled with a client application for receiving these notifications.

## Project Description

This repository contains a server application built with Spring Boot that checks for new emails matching specific criteria and sends notifications via Server-Sent Events (SSE). Additionally, it includes a client application designed for a microcontroller to receive these SSE notifications. The goal is to provide a simple yet functional system for real-time email alerts on an embedded device.

## Installation and Setup

### Server Application
1.  **Java Development Kit (JDK):** Ensure you have JDK 17 or higher installed.
2.  **Maven:** The project uses Maven for dependency management. Make sure it is installed on your system and `mvn` command is available.
3.  **Clone the Repository:**
    ```bash
    git clone https://github.com/cankurttekin/sse-cig.git
    cd sse-cig/server
    ```
4.  **Gmail API Setup** 
    * To enable the Gmail API for the email functionality a project needs to be created in Google Cloud console. The Gmail API needs to be enabled and after generating OAuth 2.0 Client IDs a `credentials.json` file needs to be generated. Follow the instructions described here: [https://developers.google.com/gmail/api/quickstart/java](https://developers.google.com/gmail/api/quickstart/java)
    * Place this file under the resources folder of server project directory (`server/src/main/resources/credentials.json`).
5. **Gmail App Password Setup**
   * For this project to access the user's Gmail account, an app password needs to be created for Gmail. Instructions to create an app password can be found here: [https://support.google.com/accounts/answer/185833?hl=en](https://support.google.com/accounts/answer/185833?hl=en)
   * This app password needs to be added to the EmailService.java file as explained below

### Client Application
1. **Arduino IDE:** Install the Arduino IDE for programming the microcontroller.
2. **ESP8266 Board Support:** Add the ESP8266 board support to the Arduino IDE, if not already present.
3. **Required Libraries:** Install the following libraries via the Arduino IDE's library manager:
   - `WiFi`
   - `ESP8266WiFi`
   - `ArduinoHttpClient`
