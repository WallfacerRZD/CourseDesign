#include"HuffmanCompress.h"
#include"Node.h"
#include<iostream>
#include<string>
#include<vector>
#include<fstream>
#include<iterator>


using namespace std;
int main() {
	HuffmanCompress test;
	test.Compress("text.txt");
	test.Decompress("a.txt");
	getchar();
	return 0;
}