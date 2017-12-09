#include"BinaryStdIn.h"

void BinaryStdIn::FillBuffer() {
	in >> buffer;
	N = 8;
}

bool BinaryStdIn::IsEmpty() {
	return in.eof();
}

bool BinaryStdIn::ReadBit() {
	--N;
	bool bit = ((buffer >> N) & 1) == 1;
	if (N == 0) {
		FillBuffer();
	}
	return bit;
}

const char BinaryStdIn::ReadChar() {
	if (N == 8) {
		char ch = buffer;
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
		return ch;
	}
}