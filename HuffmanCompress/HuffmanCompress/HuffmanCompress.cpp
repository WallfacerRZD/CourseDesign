#include"HuffmanCompress.h"
#include<queue>
#include<string>
#include<fstream>
#include<iterator>
#include<iostream>
#include<algorithm>

using namespace std;

struct MyComparison {
	bool operator()(const Node *n1, const Node *n2) {
		return n1->frequency > n2->frequency;
	}
};
const Node* HuffmanCompress::BuildTree(const string &path) {
	// ͳ���ַ�Ƶ��
	const int N = 256;
	int freq[N];
	for (int i = 0; i < N; ++i) {
		freq[i] = 0;
	}
	ifstream in;
	try {
		in.open(path, ifstream::in);
	}
	catch (...) {
		cout << "�ļ���ʧ��!!" << endl;
		in.close();
		exit(1);
	}
	istreambuf_iterator<char> stream_it(in), eof;
	while (stream_it != eof) {
		++freq[*stream_it++];
	}
	in.close();
	// ���Ƶ��
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
	return pq.top();
}

void HuffmanCompress::BuildTable(std::string *table, const Node *node, const std::string &code) {
	if (node->IsLeaf()) {
		table[node->ch] = code;
		return;
	}
	else {
		BuildTable(table, node->left, code + "0");
		BuildTable(table, node->right, code + "1");
	}
}

const std::string* HuffmanCompress::BuildTable(const Node *root) {
	const int N = 256;
	std::string *table = new std::string[N];
	BuildTable(table, root, "");
	return table;
}

const string* HuffmanCompress::GetRawText(const string &path) {
	ifstream in;
	try {
		in.open(path, ifstream::in);
	}
	catch (...) {
		cout << "�ļ���ʧ��!";
		in.close();
		exit(1);
	}
	istreambuf_iterator<char> stream_it(in), eof;
	string *text = new string();
	copy(stream_it, eof, back_inserter(*text));
	in.close();
	return text;
}


void HuffmanCompress::WriteToFile(const string &path, const string *text, const string *table) {
	ofstream out;
	try {
		out.open(path, ofstream::out | ofstream::binary);
	}
	catch (...) {
		cout << "�ļ���ʧ��";
		out.close();
		exit(1);
	}

	// ѹ��
	unsigned char ch;
	int shift_count = 0;
	for (const char &c : *text) {
		const string code = table[c];
		for (const char &x : code) {
			if (x == '1') {
				// ��00000001��,���λ��1
				ch |= 1;
			}
			// ����һλ
			ch <<= 1;
			++shift_count;
			if (shift_count == 7) {
				out << ch;
				shift_count = 0;
			}
		}
	}
	if (shift_count > 1) {
		ch <<= (8 - shift_count);
		out << ch;
	}
	out.close();

}

void HuffmanCompress::Compress(const std::string &path) {
	const string *text = GetRawText(path);
	const Node *root = BuildTree(path);
	const std::string *table = BuildTable(root);
	const int N = 256;

	// �������
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
	//cout << *text;
	WriteToFile("a.txt", text, table);
}