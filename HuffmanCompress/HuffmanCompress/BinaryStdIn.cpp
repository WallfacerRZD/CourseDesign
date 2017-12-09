#include"BinaryStdIn.h"
#include<iostream>
#include<iterator>
void BinaryStdIn::FillBuffer() {
	buffer = *stream_it;
	++stream_it;
	N = 8;
}

bool BinaryStdIn::IsEmpty() {
	return stream_it == eof;
}

bool BinaryStdIn::ReadBit() {
	--N;
	bool bit = ((buffer >> N) & 1) == 1;
	//std::cout << bit;
	if (N == 0) {
		FillBuffer();
	}
	return bit;
}

const unsigned char BinaryStdIn::ReadChar() {
	if (N == 8) {
		char ch = buffer;
		//for (int i = 0; i < 8; ++i) {
		//	bool bit = ((ch >> (8 - i - 1)) & 1) == 1;
		//	std::cout << bit;
		//}
		FillBuffer();
		return ch;
	}
	else {
		// combine last n bits of current buffer with first 8-n bits of new buffer
		char ch = buffer;
		ch <<= (8 - N);
		int old_N = N;
		FillBuffer();
		N = old_N;
		ch |= (buffer >> N);
		//for (int i = 0; i < 8; ++i) {
		//	bool bit = ((ch >> (8 - i - 1)) & 1) == 1;
		//	std::cout << bit;
		//}
		return ch;
	}
}

int BinaryStdIn::ReadInt() {
	int x = 0;
	for (int i = 0; i < 4; i++) {
		char c = ReadChar();
		x <<= 8;
		x |= c;
	}
	return x;
}