#include<iostream>
#include"BinaryStdOut.h"
void BinaryStdOut::WriteBit(bool bit) {
	buffer <<= 1;
	//std::cout << bit;
	if (bit == true) {
		buffer |= 1;
	}
	++N;
	if (N == 8) {
		ClearBuffer();
	}
}

void BinaryStdOut::WriteInt(const int x) {
	WriteChar((x >> 24) & 0xff);
	WriteChar((x >> 16) & 0xff);
	WriteChar((x >> 8) & 0xff);
	WriteChar((x >> 0) & 0xff);
}

void BinaryStdOut::ClearBuffer() {
	if (N == 0) {
		return;
	}
	if (N > 0) {
		buffer <<= (8 - N);
		out << buffer;
		N = 0;
		buffer = 0;
	}
}

void BinaryStdOut::WriteChar(const unsigned char ch) {
	if (N == 0) {
		out << ch;
	}
	else {
		for (int i = 0; i < 8; ++i) {
			bool bit = ((ch >> (8 - i - 1)) & 1) == 1;
			WriteBit(bit);
		}
	}
}