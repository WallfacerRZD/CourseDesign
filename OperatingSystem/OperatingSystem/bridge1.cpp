#include<windows.h>
#include<iostream>

/*
* ˼·:
*      �ŵ��ϱ���ڸ�����һ��ֵΪ1���ź���
*      ÿ�ν�����������ź���, ����������ͷ�
*/
CONDITION_VARIABLE northEmpty;
CONDITION_VARIABLE southEmpty;
CRITICAL_SECTION criticalSection;
bool southFull = false;
bool northFull = false;

void south2NorthTread() {
	for (int i = 0; i < 1000; i++) {
		EnterCriticalSection(&criticalSection);
		while (southFull) {
			SleepConditionVariableCS(&southEmpty, &criticalSection, NULL);
		}
		southFull = true;
		//LeaveCriticalSection(&criticalSection);

		std::cout << "�����š�������\n";
		std::cout << "ͨ���š�������\n";

		//EnterCriticalSection(&criticalSection);
		southFull = false;
		LeaveCriticalSection(&criticalSection);

		WakeConditionVariable(&southEmpty);

		EnterCriticalSection(&criticalSection);
		while (northFull) {
			SleepConditionVariableCS(&northEmpty, &criticalSection, NULL);
		}
		northFull = true;
		//LeaveCriticalSection(&criticalSection);

		std::cout << "�뿪�š�������\n";

		//EnterCriticalSection(&criticalSection);
		northFull = false;
		LeaveCriticalSection(&criticalSection);

		WakeConditionVariable(&northEmpty);
	}
}

void north2SouthTread() {
	for (int i = 0; i < 1000; i++) {
		EnterCriticalSection(&criticalSection);
		while (northFull) {
			SleepConditionVariableCS(&northEmpty, &criticalSection, NULL);
		}
		northFull = true;
		//LeaveCriticalSection(&criticalSection);

		std::cout << "�����š�������\n";
		std::cout << "ͨ���š�������\n";

		//EnterCriticalSection(&criticalSection);
		northFull = false;
		LeaveCriticalSection(&criticalSection);

		WakeConditionVariable(&northEmpty);

		EnterCriticalSection(&criticalSection);
		while (southFull) {
			SleepConditionVariableCS(&southEmpty, &criticalSection, NULL);
		}
		southFull = true;
		//LeaveCriticalSection(&criticalSection);

		std::cout << "�뿪�š�������\n";

		//EnterCriticalSection(&criticalSection);
		southFull = false;
		LeaveCriticalSection(&criticalSection);

		WakeConditionVariable(&southEmpty);
	}
}


int main() {
	const int threadNum = 10;
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