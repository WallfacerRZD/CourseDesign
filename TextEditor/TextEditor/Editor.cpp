#include"Editor.h"

#include<fstream>
#include<iostream>
#include<sstream>
#include<iterator>
using namespace std;

Editor::Editor(): current(0) {}

void Editor::ReadFrom(const string &path) {
	ifstream in;
	try {
		in.open(path, ifstream::in);
	}
	catch (exception) {
		cout << "文件打开失败!" << endl;
		exit(1);
	}
	string temp;
	while (getline(in, temp)) {
		texts.push_back(temp);
	}
}
void Editor::Add(const string &str) {
	texts.push_back(str);
}

inline
void Editor::Show() const {
	system("cls");
	int index = 0;
	for (auto bg = texts.cbegin(), end = texts.cend();
		bg != end; ++bg) {
		cout << index << ' ' << *bg << endl;
		++index;
	}
}

void Editor::Delete(unsigned index) {
	if (index < texts.size()) {
		auto it = texts.cbegin();
		for (int i = 0; i != index; ++i) {
			++it;
		}
		texts.erase(it);
	}
	else {
		//do something
	}

}


void Editor::InsertInto(const string &str, unsigned index) {
	if (index <= texts.size()) {
		auto it = texts.cbegin();
		for (int i = 0, size = texts.size(); i != index; ++i) {
			++it;
		}
		texts.insert(it, str);
	}
}

void Editor::Save(const string &path) const {
	ofstream out(path, ofstream::out);
	for (auto bg = texts.cbegin(), end = texts.cend(); bg != end; ++bg) {
		out << *bg << endl;
	}
	out.close();
}

void Editor::Replace(const string &str, unsigned index) {
	auto it = texts.begin();
	for (int i = 0; i != index; ++i) {
		++it;
	}
	*it = str;
}

void Editor::Start() {
	system("cls");
	Show();
	string str;
	while(getline(cin, str)) {
		// instruction
		if (str[0] == ':') {
			istringstream in(str);
			string instruction;
			in >> instruction;
			in >> instruction;
			if (instruction == "delete") {
				int index = 0;
				in >> index;
				Delete(index);
				Show();
			}
			else if (instruction == "insert") {
				int index = 0;
				string str;
				in >> index;
				getline(in, str);
				InsertInto(str, index);
				Show();
			}
			else if (instruction == "replace") {
				int index = 0;
				in >> index;
				string str;
				istream_iterator<char> in_it(in), eof;
				// 流迭代器无视空格,去掉用于分割指令的空格
				copy(in_it, eof, back_inserter(str));
				Replace(str, index);
				Show();
			}
			else if (instruction == "wq") {
				string path = "./text.txt";
				in >> path;
				Save(path);
				cout << "保存成功!" << endl;
				return;
			}
			else {
				cout << "无效的指令!!" << endl;
			}
		}
		// text
		else {
			Add(str);
			Show();
		}
	}
}


