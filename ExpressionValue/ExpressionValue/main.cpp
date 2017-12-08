#include<iostream>
#include"functions.h"


int main(){
    //以空格分割
	std::string expression;
	while (std::getline(std::cin, expression)) {
		std::string PostFixStr = TransforToPostFix(expression);
		std::cout << "PostFixStr: " << PostFixStr << std::endl;
		std::cout << "value: " << GetPostFixVal(PostFixStr) << std::endl;
	}
	system("pause");
    return 0;
}