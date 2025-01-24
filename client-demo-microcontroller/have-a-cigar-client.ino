#include <WiFi.h>
#include <WiFiClient.h>
#include <WiFiServer.h>
#include <WiFiUdp.h>

#include <ESP8266WiFi.h>
#include <Servo.h>
#include <ArduinoHttpClient.h>

// Wi-Fi Credentials
const char* ssid = "SSID";
const char* password = "PASS";


// API Details
const char* server = "192.168.1.60";
const int port = 8080;
const char* apiEndpoint = "/sse/have-a-cigar";


// WiFi and HTTP Client
WiFiClient wifiClient;
HttpClient httpClient(wifiClient, server, port);

void setup() {
  Serial.begin(115200);
  
  // Connect to Wi-Fi
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    delay(1000);
    Serial.print(".");
  }
  Serial.println("Wi-Fi connected");

  // Connect to SSE API
  httpClient.get(apiEndpoint);
  Serial.println("SSE request sent");
}

void loop() {
  if (httpClient.available()) {
    String eventLine = httpClient.readStringUntil('\n');
    if (eventLine.startsWith("data:")) {
      String eventData = eventLine.substring(5); 
      eventData.trim();
      Serial.println("Event received: " + eventData);
    }
  }
}
