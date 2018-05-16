#include<windows.h>
#include<iostream>

/*
* ˼·:
*      �ŵ��ϱ���ڸ�����һ����������, һ������ֵ, ��ʾ�Ƿ�����
*      ÿ�ν�����ڼ���Ƿ�����, ���˾���������Ӧ������������
*      û�˾ͽ������, �뿪���ʱ�������������·�ڶ�Ӧ�����������ϵ��߳�
*/
CONDITION_VARIABLE northEmpty;
CONDITION_VARIABLE southEmpty;
CRITICAL_SECTION criticalSection;
bool southFull = false;
bool northFull = false;

void south2NorthTread() {
	for (int i = 0; i < 500; i++) {
		EnterCriticalSection(&criticalSection);
		while (southFull) {
			SleepConditionVariableCS(&southEmpty, &criticalSection, NULL);
		}
		southFull = true;

		std::cout << "�����š�������\n";
		std::cout << "ͨ���š�������\n";
		southFull = false;
		LeaveCriticalSection(&criticalSection);

		WakeConditionVariable(&southEmpty);

		EnterCriticalSection(&criticalSection);
		while (northFull) {
			SleepConditionVariableCS(&northEmpty, &criticalSection, NULL);
		}
		northFull = true;

		std::cout << "�뿪�š�������\n";

		northFull = false;
		LeaveCriticalSection(&criticalSection);

		WakeConditionVariable(&northEmpty);
	}
}

void north2SouthTread() {
	for (int i = 0; i < 500; i++) {
		EnterCriticalSection(&criticalSection);
		while (northFull) {
			SleepConditionVariableCS(&northEmpty, &criticalSection, NULL);
		}
		northFull = true;
		std::cout << "�����š�������\n";
		std::cout << "ͨ���š�������\n";

		northFull = false;
		LeaveCriticalSection(&criticalSection);

		WakeConditionVariable(&northEmpty);

		EnterCriticalSection(&criticalSection);
		while (southFull) {
			SleepConditionVariableCS(&southEmpty, &criticalSection, NULL);
		}
		southFull = true;
		std::cout << "�뿪�š�������\n";

		southFull = false;
		LeaveCriticalSection(&criticalSection);

		WakeConditionVariable(&southEmpty);
	}
}


int main() {
	InitializeConditionVariable(&northEmpty);
	InitializeConditionVariable(&southEmpty);
	InitializeCriticalSection(&criticalSection);
	HANDLE thread1 = CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)north2SouthTread, NULL, 0, NULL);
	HANDLE thread2 = CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)south2NorthTread, NULL, 0, NULL);
	WaitForSingleObject(thread1, INFINITE);
	WaitForSingleObject(thread2, INFINITE);

	system("pause");
	return 0;
}