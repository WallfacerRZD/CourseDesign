#pragma once
# ifndef HUFFMAN_COMPRESS_H
# define HUFFMAN_COMPRESS_H

#include"Node.h"
#include<string>


class Node;
class HuffmanCompress {
public:
	void Compress(const std::string &path);
	void Decompress();
	HuffmanCompress() = default;
private:
	const Node* BuildTree(const std::string &path);
	const std::string* BuildTable(const Node *root);
	void BuildTable(std::string *table, const Node *node, const std::string &code);
	const std::string* GetRawText(const std::string &path);
	void WriteToFile(const std::string &path, const std::string *text, const std::string *table);
};



# endif