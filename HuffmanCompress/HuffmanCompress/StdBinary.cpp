#include"StdBinary.h"
#include<iostream>

void BinaryStdOut::WriteBit(bool bit) {
	buffer <<= 1;
	if (bit == true) {
		buffer |= 1;
	}
	++shift_count;
	if (shift_count == 8) {
		out << buffer;
		shift_count = 0;
	}
}

void BinaryStdOut::ClearBuffer() {
	if (shift_count > 0) {
		buffer <<= (8 - shift_count);
		out << buffer;
		shift_count = 0;
	}
}

bool BinaryStdOut::ReadBit() {
	return true;
}

void BinaryStdOut::WriteChar(const char ch) {
	if (shift_count == 0) {
		out << ch;
	}
	else {
		for (int i = 0; i < 8; ++i) {
			bool bit = ((ch >> (8 - i - 1)) & 1) == 1;
			WriteBit(bit);
		}
	}
}