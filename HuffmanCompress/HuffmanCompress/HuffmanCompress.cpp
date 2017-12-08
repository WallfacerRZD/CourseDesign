#include"HuffmanCompress.h"
#include<queue>
#include<string>
#include<fstream>
#include<iterator>
#include<iostream>

using namespace std;

struct MyComparison {
	bool operator()(const Node *n1, const Node *n2) {
		return n1->frequency > n2->frequency;
	}
};
void HuffmanCompress::BuildTree(const string &path) {
	// ͳ���ַ�Ƶ��
	const int N = 256;
	int freq[N];
	for (int i = 0; i < N; ++i) {
		freq[i] = 0;
	}
	ifstream in;
	try {
		in.open(path);
	}
	catch (...) {
		cout << "�ļ���ʧ��!!" << endl;
		exit(1);
	}
	istreambuf_iterator<char> stream_it(in), eof;
	while (stream_it != eof) {
		++freq[*stream_it++];
	}
	cout << "---------Ƶ��-----------" << endl;
	for (int i = 0; i < N; ++i) {
		if (freq[i]) {
			if (i == '\n') {
				cout << "����" << ": " << freq[i] << endl;
			}
			else if (i == ' ') {
				cout << "�ո�" << ": " << freq[i] << endl;
			}
			else {
				cout << (char)i << ' ' << freq[i] << endl;
			}
		}
	}

	// �����������
	//auto f = [](const Node *n1, const Node *n2) { return n1->frequency < n2->frequency; };
	// ģ�����������lambda
	using MyPQ = priority_queue < const Node*, vector<const Node*>, MyComparison>;
	MyPQ pq;
	for (int i = 0; i < N; ++i) {
		if (freq[i]) {
			pq.push(new Node((char)i, freq[i], nullptr, nullptr));
		}
	}
	while (pq.size() > 1) {
		const Node *node1 = pq.top();
		pq.pop();
		const Node *node2 = pq.top();
		pq.pop();
		const Node *newNode = new Node('\0', node1->frequency + node2->frequency, node1, node2);
		pq.push(newNode);
	}
	root = pq.top();
}

HuffmanCompress::HuffmanCompress(const std::string &path) {
	BuildTree(path);
	BuildTable(root, "");
	const int N = 256;
	cout << "---------����-----------" << endl;
	for (int i = 0; i < N; ++i) {
		if (table[i] != "") {
			if (i == '\n') {
				cout << "����" << ": " << table[i] << endl;
			}
			else if (i == ' ') {
				cout << "�ո�" << ": " << table[i] << endl;
			}
			else {
				cout << char(i) << ": " << table[i] << endl;
			}
		}
	}
}


void HuffmanCompress::BuildTable(const Node *node, const std::string &code) {
	if (node->IsLeaf()) {
		table[node->ch] = code;
		return;
	}
	else {
		BuildTable(node->left, code + "0");
		BuildTable(node->right, code + "1");
	}
}