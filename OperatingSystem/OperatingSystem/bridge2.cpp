#include<pthread.h>
#include<iostream>
#include<semaphore.h>

/*
* 思路:
*      桥的南北入口各设置一个值为1的信号量
*      每次进入入口请求信号量, 进入桥面后释放
*/
sem_t northSemaphore;
sem_t southSemaphore;


void* south2NorthTread(void *) {
	for (int i = 0; i < 100; i++) {
		sem_wait(&southSemaphore);
		std::cout << "进入桥　↑　南\n";
		std::cout << "通过桥↑　　面\n";
		sem_post(&southSemaphore);

		sem_wait(&northSemaphore);
		std::cout << "离开桥　↑　北\n";
		sem_post(&northSemaphore);
	}
	return NULL;
}

void* north2SouthTread(void *) {
	for (int i = 0; i < 100; i++) {
		sem_wait(&northSemaphore);
		std::cout << "进入桥　↓　北\n";
		std::cout << "通过桥　　↓面\n";
		sem_post(&northSemaphore);

		sem_wait(&southSemaphore);
		std::cout << "离开桥　↓　南\n";
		sem_post(&southSemaphore);
	}
	return NULL;
}


int main() {
	sem_init(&northSemaphore, 0, 1);
	sem_init(&southSemaphore, 0, 1);

	pthread_t thread1;
	pthread_t thread2;
	pthread_create(&thread1, NULL, north2SouthTread, NULL);
	pthread_create(&thread2, NULL, south2NorthTread, NULL);
	pthread_join(thread1, NULL);
	pthread_join(thread2, NULL);
	system("pause");
	return 0;
}