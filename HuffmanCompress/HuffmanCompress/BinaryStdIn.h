#pragma once
#ifndef BINARY_STD_IN_H
#define BINARY_STD_IN_H
#include<fstream>


class BinaryStdIn {
public:
	BinaryStdIn(std::istream &in):buffer(char(0x00)), N(8), in(in)
	{}
	void FillBuffer();
	bool IsEmpty();
	bool ReadBit();
	const char ReadChar();
	
private:
	char buffer;
	// buffer��ʣ��ı���λ
	int N;
	std::istream &in;
};

#endif