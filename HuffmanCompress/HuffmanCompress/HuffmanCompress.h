#pragma once
# ifndef HUFFMAN_COMPRESS_H
# define HUFFMAN_COMPRESS_H

#include"Node.h"
#include<string>
#include<fstream>
#include"BinaryStdIn.h"
#include"BinaryStdOut.h"

class Node;
class HuffmanCompress {
public:
	void Compress(const std::string &path);
	void Decompress(const std::string &path);
	HuffmanCompress() = default;
private:
	const Node* BuildTree(const std::string &path);
	const std::string* BuildTable(const Node *root);
	void BuildTable(std::string *table, const Node *node, const std::string &code);
	const std::string* GetRawText(const std::string &path);
	void WriteTree(BinaryStdOut &binarystdout, const Node *node);
	void WriteToFile(BinaryStdOut &binarystdout, const std::string *text, const std::string *table);
	const Node* ReadTree(BinaryStdIn &stdbinaryin);
	void ShowTree(const Node* root)const;
};



# endif