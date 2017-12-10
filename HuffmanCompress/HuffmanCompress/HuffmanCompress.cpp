#include"HuffmanCompress.h"
#include"BinaryStdOut.h"
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
	//cout << "---------Ƶ��-----------" << endl;
	//for (int i = 0; i < N; ++i) {
	//	if (freq[i]) {
	//		if (i == '\n') {
	//			cout << "����" << ": " << freq[i] << endl;
	//		}
	//		else if (i == ' ') {
	//			cout << "�ո�" << ": " << freq[i] << endl;
	//		}
	//		else {
	//			cout << (char)i << ' ' << freq[i] << endl;
	//		}
	//	}
	//}

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

void HuffmanCompress::WriteTree(BinaryStdOut &stdbinaryout, const Node *node) {
	if (node->IsLeaf()) {
		stdbinaryout.WriteBit(true);
		stdbinaryout.WriteChar(node->ch);
		return;
	}
	else {
		stdbinaryout.WriteBit(false); 
		WriteTree(stdbinaryout, node->left);
		WriteTree(stdbinaryout, node->right);
	}
}

void HuffmanCompress::WriteToFile(BinaryStdOut &stdbinaryout, const string *text, const string *table) {
	// ѹ��
	for (const char &c : *text) {
		const string code = table[c];
		for (const char &x : code) {
			if (x == '1') {
				stdbinaryout.WriteBit(true);
			}
			else {
				stdbinaryout.WriteBit(false);
			}
		}
	}
	stdbinaryout.ClearBuffer();
}

// �ӱ��������ؽ����ʲ�����
const Node* HuffmanCompress::ReadTree(BinaryStdIn &binary_in) {
	 //Ҷ�ӽڵ�
	if (binary_in.ReadBit() == true) {
		return new Node(binary_in.ReadChar(), 0, nullptr, nullptr);
	}
	 //�ڲ��ڵ�
	else {
		return new Node('\0', 0, ReadTree(binary_in), ReadTree(binary_in));
	}
}

void HuffmanCompress::Compress(const std::string &path) {
	cout << "compress begin..." << endl;
	const string *text = GetRawText(path);
	const Node *root = BuildTree(path);

	//cout << "-------------tree-----------------" << endl;
	//ShowTree(root);
	//cout << "-------------tree-----------------" << endl;
	const string *table = BuildTable(root);
	const int N = 256;

	// �������
	//cout << "---------����-----------" << endl;
	//for (int i = 0; i < N; ++i) {
	//	if (table[i] != "") {
	//		if (i == '\n') {
	//			cout << "����" << ": " << table[i] << endl;
	//		}
	//		else if (i == ' ') {
	//			cout << "�ո�" << ": " << table[i] << endl;
	//		}
	//		else {
	//			cout << char(i) << ": " << table[i] << endl;
	//		}
	//	}
	//}
	//cout << "---------����-----------" << endl;


	//������ļ�
	const string file_name = "out.dat";
	ofstream out(file_name, ofstream::out);
	BinaryStdOut stdbinaryout(out);

	WriteTree(stdbinaryout, root);

	//stdbinaryout.WriteInt(text->size());

	WriteToFile(stdbinaryout, text, table);
	std::cout << "compress end..." << std::endl;
	out.close();
}

void HuffmanCompress::Decompress(const std::string &path) {
	ifstream in(path, ifstream::in);
	BinaryStdIn binary_in(in);
	ofstream out("depressed from out.txt", ofstream::out);
	cout << "decompress begin..." << endl;
	const Node *root = ReadTree(binary_in);


	//const unsigned int N = binary_in.ReadInt();
	//cout << N;
	//for (int i = 0; i < 2000; ++i) {
	//	const Node *p = root;
	//	while (!binary_in.IsEmpty() && !p->IsLeaf()) {
	//		if (binary_in.ReadBit()) {
	//			p = p->left;
	//		}
	//		else {
	//			p = p->right;
	//		}
	//	}
	//	out << p->ch;
	//}

	while (!binary_in.IsEmpty()) {
		const Node *p = root;
		while (!binary_in.IsEmpty() && !p->IsLeaf()) {
			if (binary_in.ReadBit()) {
				p = p->left;
			}
			else {
				p = p->right;
			}
		}
		out << p->ch;
	}
	out.close();
	cout << "decompress end..." << endl;
	
}

void HuffmanCompress::ShowTree(const Node* root)const {
	if (root->IsLeaf()) {
		cout << root->ch << " ";
	}
	else {
		ShowTree(root->left);
		ShowTree(root->right);
	}
}