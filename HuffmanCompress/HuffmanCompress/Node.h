#pragma once
#ifndef NODE_H
#define NODE_H


#include"HuffmanCompress.h"

class HuffmanCompress;
struct MyComparison;

class Node {
	friend class HuffmanCompress;
	friend struct MyComparison;
public:
	Node(char c, int f, const Node *left, const Node *right) :
		ch(c), frequency(f), left(left), right(right)
	{}

	bool IsLeaf() const {
		return left == nullptr && right == nullptr;
	}

private:
	const Node *left, *right;
	unsigned char ch;
	int frequency;
};









#endif