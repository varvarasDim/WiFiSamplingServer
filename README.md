# WiFiSamplingServer
The Server of the WiFiSampling application

Server receives the data from the client (WiFiSamplingClient repo) and puts the on a graph. The JFreeChart library is being used in order to make the chart. At the WiFiServer.java file at the final static variable WIFI_NETWORK there is the network that the user chooses to show on the graph. The filtering is being made wht use of the ssid and is pretty simple. The port is 8731 and if you choose to change it you must also update it at the WiFiSamplingClient repo in order for the communication to take place.
