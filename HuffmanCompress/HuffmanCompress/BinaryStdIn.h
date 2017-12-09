#pragma once
#ifndef BINARY_STD_IN_H
#define BINARY_STD_IN_H
#include<fstream>
#include<iterator>

class BinaryStdIn {
public:
	BinaryStdIn(std::istream &in):buffer(unsigned char(0x00)), N(8), stream_it(in)
	{
		
		FillBuffer();
	}
	void FillBuffer();
	bool IsEmpty();
	bool ReadBit();
	const unsigned char ReadChar();
	int ReadInt();

private:
	unsigned char buffer;
	// buffer中剩余的比特位
	int N;
	//std::istream &in;
	std::istreambuf_iterator<char> eof;
	std::istreambuf_iterator<char> stream_it;
};

#endif