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
	// 统计字符频率
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
		cout << "文件打开失败!!" << endl;
		in.close();
		exit(1);
	}


	istreambuf_iterator<char> stream_it(in), eof;
	while (stream_it != eof) {
		++freq[*stream_it++];
	}
	in.close();
	// 输出频率
	//cout << "---------频率-----------" << endl;
	//for (int i = 0; i < N; ++i) {
	//	if (freq[i]) {
	//		if (i == '\n') {
	//			cout << "换行" << ": " << freq[i] << endl;
	//		}
	//		else if (i == ' ') {
	//			cout << "空格" << ": " << freq[i] << endl;
	//		}
	//		else {
	//			cout << (char)i << ' ' << freq[i] << endl;
	//		}
	//	}
	//}

	// 构造哈夫曼树
	//auto f = [](const Node *n1, const Node *n2) { return n1->frequency < n2->frequency; };
	// 模板参数不能用lambda
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
		cout << "文件打开失败!";
		in.close();
		exit(1);
	}
	istreambuf_iterator<char> stream_it(in), eof;
	string *text = new string();
	copy(stream_it, eof, back_inserter(*text));
	in.close();
	return text;
}

void HuffmanCompress::WriteTree(BinaryStdOut &stdbinary, const Node *node) {
	if (node->IsLeaf()) {
		stdbinary.WriteBit(true);
		stdbinary.WriteChar(node->ch);
		return;
	}
	else {
		stdbinary.WriteBit(false); 
		WriteTree(stdbinary, node->left);
		WriteTree(stdbinary, node->right);
	}

}

void HuffmanCompress::WriteToFile(BinaryStdOut &stdbinary, const string *text, const string *table) {
	// 压缩
	for (const char &c : *text) {
		const string code = table[c];
		for (const char &x : code) {
			if (x == '1') {
				stdbinary.WriteBit(true);
			}
			else {
				stdbinary.WriteBit(false);
			}
		}
	}
	stdbinary.ClearBuffer();
}

// 从比特流中重建单词查找树
const Node* HuffmanCompress::ReadTree(BinaryStdIn &binary_in) {
	 //叶子节点
	if (binary_in.ReadBit() == true) {
		return new Node(binary_in.ReadChar(), 0, nullptr, nullptr);
	}
	 //内部节点
	else {
		return new Node('\0', 0, ReadTree(binary_in), ReadTree(binary_in));
	}
}

void HuffmanCompress::Compress(const std::string &path) {
	const string *text = GetRawText(path);
	const Node *root = BuildTree(path);

	cout << "-------------tree-----------------" << endl;
	ShowTree(root);
	cout << "-------------tree-----------------" << endl;
	const string *table = BuildTable(root);
	const int N = 256;

	// 输出编码
	//cout << "---------编码-----------" << endl;
	//for (int i = 0; i < N; ++i) {
	//	if (table[i] != "") {
	//		if (i == '\n') {
	//			cout << "换行" << ": " << table[i] << endl;
	//		}
	//		else if (i == ' ') {
	//			cout << "空格" << ": " << table[i] << endl;
	//		}
	//		else {
	//			cout << char(i) << ": " << table[i] << endl;
	//		}
	//	}
	//}
	//cout << "---------编码-----------" << endl;


	//输出到文件
	const string file_name = "out.dat";
	ofstream out(file_name, ofstream::out);
	BinaryStdOut stdbinaryout(out);

	WriteTree(stdbinaryout, root);

	//stdbinaryout.WriteInt(text->size());

	WriteToFile(stdbinaryout, text, table);
	std::cout << "压缩完成!" << std::endl;
	out.close();
}

void HuffmanCompress::Decompress(const std::string &path) {
	ifstream in(path, ifstream::in);
	BinaryStdIn binary_in(in);
	ofstream out("x.txt", ofstream::out);
	const Node *root = ReadTree(binary_in);

	cout << "-------------tree-----------------" << endl;
	ShowTree(root);
	cout << "-------------tree-----------------" << endl;
	//const int N = binary_in.ReadInt();
	//cout << endl
	//	<< N;
	while (!binary_in.IsEmpty()) {
		const Node *p = root;
		while (!p->IsLeaf()) {
			if (binary_in.ReadBit()) {
				p = p->right;
			}
			else {
				p = p->left;
			}
		}
		out << p->ch;
	}
	
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