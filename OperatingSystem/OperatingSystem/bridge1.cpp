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
		while (southFull) {
			SleepConditionVariableCS(&southEmpty, NULL, NULL);
		}

		EnterCriticalSection(&criticalSection);
		southFull = true;
		std::cout << "�����š�������\n";
		std::cout << "ͨ���š�������\n";
		southFull = false;
		LeaveCriticalSection(&criticalSection);
		WakeConditionVariable(&southEmpty);
		while (northFull) {
			SleepConditionVariableCS(&northEmpty, NULL, NULL);
		}
		EnterCriticalSection(&criticalSection);
		northFull = true;
		std::cout << "�뿪�š�������\n";
		northFull = false;
		LeaveCriticalSection(&criticalSection);
		WakeConditionVariable(&northEmpty);
	}
}

void north2SouthTread() {
	for (int i = 0; i < 1000; i++) {
		while (northFull) {
			SleepConditionVariableCS(&northEmpty, NULL, NULL);
		}
		EnterCriticalSection(&criticalSection);
		northFull = true;
		std::cout << "�����š�������\n";
		std::cout << "ͨ���š�������\n";
		northFull = false;
		LeaveCriticalSection(&criticalSection);
		WakeConditionVariable(&northEmpty);

		while (southFull) {
			SleepConditionVariableCS(&southEmpty, NULL, NULL);
		}
		EnterCriticalSection(&criticalSection);
		southFull = true;
		std::cout << "�뿪�š�������\n";
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
	HANDLE n2Sthreads[threadNum];
	HANDLE s2Nthreads[threadNum];
	for (int i = 0; i < threadNum; i++) {
		n2Sthreads[i] = CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)north2SouthTread, NULL, 0, NULL);
	}
	for (int i = 0; i < threadNum; i++) {
		s2Nthreads[i] = CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)south2NorthTread, NULL, 0, NULL);
	}
	for (int i = 0; i < threadNum; i++) {
		WaitForSingleObject(n2Sthreads[i], INFINITE);
		WaitForSingleObject(s2Nthreads[i], INFINITE);
	}
	system("pause");
	return 0;
}