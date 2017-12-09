#pragma once
#ifndef STD_BINARY_H
#define STD_BINARY_H

#include<fstream>

class BinaryStdOut {
public:
	BinaryStdOut(std::ostream &out) :shift_count(0), buffer(char(0x00)), out(out)
	{};
	void WriteBit(bool bit);
	void WriteChar(const char ch);
	void ClearBuffer();
	bool ReadBit();
	char ReadChar();
private:
	int shift_count;
	unsigned char buffer;
	std::ostream &out;

};



#endif // !STD_BINARY_H
