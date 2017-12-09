#pragma once
#ifndef STD_BINARY_H
#define STD_BINARY_H

#include<fstream>


class StdBinary {
public:
	StdBinary(std::ostream &out) :shift_count(0), buffer(char(0x00)), out(out)
	{};
	void WriteBit(bool bit);
	void ClearBuffer();
	bool ReadBit();
	char ReadChar();
private:
	int shift_count;
	unsigned char buffer;
	std::ostream &out;

};



#endif // !STD_BINARY_H
