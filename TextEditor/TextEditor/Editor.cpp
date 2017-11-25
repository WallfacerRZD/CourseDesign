#include"Editor.h"

#include<fstream>
#include<iostream>
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

void Editor::Delete(int index) {
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

void Editor::Delete(int beginIndex, int endIndex) {
	if (beginIndex < texts.size()) {
		auto bgIt = texts.cbegin();
		for (int i = 0; i != beginIndex; ++i) {
			++bgIt;
		}
		auto endIt = bgIt;
		for (int i = 0, offset = endIndex - beginIndex; i < offset; ++i) {
			if (endIt != texts.cend()) {
				++endIt;
			}
		}
		texts.erase(bgIt, endIt);
	}
	else {
		//do something
	}

}

void Editor::InsertInto(const string &str, int index) {
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

void Editor::Start() {
	string str;
	while (getline(cin, str)) {
		Add(str);
		Show();
	}
}


