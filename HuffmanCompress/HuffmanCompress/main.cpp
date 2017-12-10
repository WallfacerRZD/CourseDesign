#include"HuffmanCompress.h"
#include"Node.h"
#include<iostream>
#include<string>
#include<vector>
#include<fstream>
#include<iterator>


using namespace std;
int main() {
	 //解压后会丢失1-2个字节
	HuffmanCompress test;
	test.Compress("text to compress.txt");
	test.Decompress("out.dat");
	getchar();
	return 0;
}