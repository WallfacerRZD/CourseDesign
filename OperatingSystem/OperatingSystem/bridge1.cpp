#include<windows.h>
#include<iostream>

/*
* 思路:
*      桥的南北入口各设置一个值为1的信号量
*      每次进入入口请求信号量, 进入桥面后释放
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

		std::cout << "进入桥　↑　南\n";
		std::cout << "通过桥↑　　面\n";

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

		std::cout << "离开桥　↑　北\n";

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

		std::cout << "进入桥　↓　北\n";
		std::cout << "通过桥　　↓面\n";

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

		std::cout << "离开桥　↓　南\n";

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