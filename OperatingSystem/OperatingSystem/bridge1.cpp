#include<windows.h>
#include<iostream>

/*
* 思路:
*      桥的南北入口各设置一个条件变量, 一个布尔值, 表示是否有人
*      每次进入入口检测是否有人, 有人就阻塞在相应的条件变量上
*      没人就进入入口, 离开入口时唤醒阻塞在这个路口对应的条件变量上的线程
*/
CONDITION_VARIABLE northEmpty;
CONDITION_VARIABLE southEmpty;
CRITICAL_SECTION northCriticalSection;
CRITICAL_SECTION southCriticalSection;
bool southFull = false;
bool northFull = false;

void south2NorthTread() {
	for (int i = 0; i < 500; i++) {
		EnterCriticalSection(&southCriticalSection);
		while (southFull) {
			SleepConditionVariableCS(&southEmpty, &southCriticalSection, NULL);
		}
		southFull = true;

		std::cout << "进入桥　↑　南\n";
		std::cout << "通过桥↑　　面\n";
		southFull = false;
		LeaveCriticalSection(&southCriticalSection);

		WakeConditionVariable(&southEmpty);

		EnterCriticalSection(&northCriticalSection);
		while (northFull) {
			SleepConditionVariableCS(&northEmpty, &northCriticalSection, NULL);
		}
		northFull = true;

		std::cout << "离开桥　↑　北\n";

		northFull = false;
		LeaveCriticalSection(&northCriticalSection);

		WakeConditionVariable(&northEmpty);
	}
}

void north2SouthTread() {
	for (int i = 0; i < 500; i++) {
		EnterCriticalSection(&northCriticalSection);
		while (northFull) {
			SleepConditionVariableCS(&northEmpty, &northCriticalSection, NULL);
		}
		northFull = true;
		std::cout << "进入桥　↓　北\n";
		std::cout << "通过桥　　↓面\n";

		northFull = false;
		LeaveCriticalSection(&northCriticalSection);

		WakeConditionVariable(&northEmpty);

		EnterCriticalSection(&southCriticalSection);
		while (southFull) {
			SleepConditionVariableCS(&southEmpty, &southCriticalSection, NULL);
		}
		southFull = true;
		std::cout << "离开桥　↓　南\n";

		southFull = false;
		LeaveCriticalSection(&southCriticalSection);

		WakeConditionVariable(&southEmpty);
	}
}


int main() {
	InitializeConditionVariable(&northEmpty);
	InitializeConditionVariable(&southEmpty);
	InitializeCriticalSection(&northCriticalSection);
	InitializeCriticalSection(&southCriticalSection);
	HANDLE thread1 = CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)north2SouthTread, NULL, 0, NULL);
	HANDLE thread2 = CreateThread(NULL, 0, (LPTHREAD_START_ROUTINE)south2NorthTread, NULL, 0, NULL);
	WaitForSingleObject(thread1, INFINITE);
	WaitForSingleObject(thread2, INFINITE);

	system("pause");
	return 0;
}