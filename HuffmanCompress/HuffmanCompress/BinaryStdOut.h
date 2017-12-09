#pragma once
#ifndef STD_BINARY_H
#define STD_BINARY_H

#include<fstream>

class BinaryStdOut {
public:
	BinaryStdOut(std::ostream &out) :N(0), buffer(unsigned char(0x00)), out(out)
	{};
	void WriteBit(bool bit);
	void WriteChar(const unsigned char ch);
	void WriteInt(int x);
	void ClearBuffer();
private:
	int N;
	unsigned char buffer;
	std::ostream &out;

};



#endif // !STD_BINARY_H
