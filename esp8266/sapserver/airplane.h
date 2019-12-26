#ifndef AIRPLANE_H
#define AIRPLANE_H

#define RPROPPIN 4
#define LPROPPIN 5

#include <stdio.h>
#include <stdint.h>

class Airplane
{
	private:
		uint16_t leftprop;
		uint16_t rightprop;
	public:
		Airplane(uint16_t rightprop, uint16_t leftprop);
		void setLeftProp(uint16_t value);
		void setRightProp(uint16_t value);
};

#endif
