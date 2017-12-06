#pragma once
#ifndef NODE_H
#define NODE_H
#include"HuffmanCompress.h"

class HuffmanCompress;
class Node {
	friend HuffmanCompress;
public:
	Node(char c, int f, const Node *left, const Node *right):
		ch(c), frequency(f), left(left), right(right)
	{}
private:
	const Node *left, *right;
	char ch;
	int frequency;
};









#endif