#pragma once
# ifndef HUFFMAN_COMPRESS_H
# define HUFFMAN_COMPRESS_H

#include"Node.h"
#include<string>
#include<fstream>
#include"StdBinary.h"

class Node;
class HuffmanCompress {
public:
	void Compress(const std::string &path);
	void Decompress();
	HuffmanCompress() = default;
private:
	const Node* BuildTree(const std::string &path);
	const std::string* BuildTable(const Node *root);
	const Node* ReadTree(StdBinary &stdbinary);
	void BuildTable(std::string *table, const Node *node, const std::string &code);
	const std::string* GetRawText(const std::string &path);
	void WriteBit(std::ostream &out, unsigned char * buferr, bool bit);
	void WriteTree(StdBinary &stdbinary, const Node *node);
	void WriteToFile(const std::string &path, const std::string *text, const std::string *table);
};



# endif