#include <airplane.h>
#include <Arduino.h>

Airplane::Airplane(uint16_t rightprop, uint16_t leftprop)
{
	this->leftprop = leftprop;
	this->rightprop = rightprop;
}

void Airplane::setLeftProp(uint16_t value)
{
	this->leftprop = value;
	analogWrite(LPROPPIN,value);
}

void Airplane::setRightProp(uint16_t value)
{
	this->rightprop = value;
	analogWrite(RPROPPIN,value);
}
