#pragma once
#ifndef NODE_H
#define NODE_H


#include"HuffmanCompress.h"

class HuffmanCompress;
class Node {
	friend HuffmanCompress;
public:
	Node(char c, int f, const Node *left, const Node *right) :
		ch(c), frequency(f), left(left), right(right)
	{}

	bool IsLeaf() const {
		return left == nullptr && right == nullptr;
	}

	bool operator<(const Node &rhs) const {
		return frequency < rhs.frequency;
	}
	int frequency;
	char ch;
private:
	const Node *left, *right;
};









#endif