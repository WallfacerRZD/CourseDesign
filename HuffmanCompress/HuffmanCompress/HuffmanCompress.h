#pragma once
# ifndef HUFFMAN_COMPRESS_H
# define HUFFMAN_COMPRESS_H

#include"Node.h"
#include<string>


class Node;
class HuffmanCompress {
public:
	void Compress();
	void Decompress();
	HuffmanCompress(const std::string &path);
private:
	// ±‡“Î±Ì
	std::string table[256];
	// ≤È’“ ˜
	const Node *root;
	void BuildTree(const std::string &path);
	void BuildTable();
};



# endif