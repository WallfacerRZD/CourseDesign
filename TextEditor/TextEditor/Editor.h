#pragma once
#ifndef EDITOR_H 
#define EDITOR_H


#include<list>
#include<string>

class Editor {
private:
	std::list<std::string> texts;
	int current;
public:
	Editor();
	void ReadFrom(const std::string &path);
	void Start();
	void Show() const;
	void Delete(int index);
	void Delete(int beginIndex, int endIndex);
	void Add(const std::string &str);
	void InsertInto(const std::string &str, int index);
	void Save(const std::string &path) const;
};


#endif