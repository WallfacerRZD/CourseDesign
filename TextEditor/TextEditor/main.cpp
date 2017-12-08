#include<iostream>
#include<vector>
#include<string>
#include"Editor.h"
#include<iostream>
#include<fstream>

int main(int argc, char **argv) {
	Editor editor;
	if (argc == 2) {
		const char *path= argv[1];
		editor.ReadFrom(path);
	}
	editor.Start();
	return 0;
}