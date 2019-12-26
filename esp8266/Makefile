SKETCHDIR = sapserver/
PORT = /dev/ttyUSB0 
DEVICE = esp8266:esp8266:nodemcu

.PHONY: all clean upload
all: compile upload	

compile:
	arduino-cli compile --fqbn $(DEVICE) $(SKETCHDIR)

upload: compile 
	sudo arduino-cli upload -p $(PORT) --fqbn $(DEVICE) $(SKETCHDIR)

clean:
	rm -rf $(SKETCHDIR)*.elf $(SKETCHDIR)*.bin 
