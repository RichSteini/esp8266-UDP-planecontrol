#define PACKLEN 3 

#include <ESP8266WiFi.h>
#include <WiFiUdp.h>
#include "airplane.h"

Airplane airplane(0,0); 

const char * ssid = "TestNetwork";
const char * password = "123456789";

WiFiUDP udp;
unsigned int port = 9090;

void setup()
{
	Serial.begin(115200);
	WiFi.softAP(ssid,password);	
	while(Serial.available() == 0);
	udp.begin(port);
	Serial.printf("Now listening at IP %s, UDP port %d\n", WiFi.localIP().toString().c_str(), port);
}

bool checkPacket(int packetsize)
{
	char incomingPacket[packetsize];
	udp.read(incomingPacket, packetsize);	
	Serial.printf("Received: %s from %s on %d\n", incomingPacket,udp.remoteIP().toString().c_str(),udp.remotePort());

	udp.beginPacket(udp.remoteIP(), udp.remotePort());
	char * endptr = NULL;
	long value = strtol(incomingPacket+1,&endptr,10);
	Serial.printf("value: %ld\n",value);

	if(incomingPacket[0] == 'l')
	{
		airplane.setLeftProp(map(value,0,100,0,1024));
		udp.write("T");
		udp.endPacket();
		return true;
	}
	else if(incomingPacket[0] == 'r')
	{
		airplane.setRightProp(map(value,0,100,0,1024));
		udp.write("T");
		udp.endPacket();
		return true;

	}
	else 
	{
		udp.write("F");
		udp.endPacket();
		return false;
	}
	
}

void loop()
{
	int packetSize = udp.parsePacket();
	if(packetSize)
	{
		checkPacket(packetSize);
	}
}
