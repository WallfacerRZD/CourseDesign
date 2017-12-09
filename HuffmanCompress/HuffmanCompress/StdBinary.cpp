#include"StdBinary.h"

void StdBinary::WriteBit(bool bit) {
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

void StdBinary::ClearBuffer() {
	if (shift_count > 0) {
		buffer <<= (8 - shift_count);
		out << buffer;
		shift_count = 0;
	}
}

bool StdBinary::ReadBit() {

}