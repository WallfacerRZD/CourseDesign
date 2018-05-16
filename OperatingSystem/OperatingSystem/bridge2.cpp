#include<pthread.h>
#include<iostream>
#include<semaphore.h>

/*
* ˼·:
*      �ŵ��ϱ���ڸ�����һ��ֵΪ1���ź���
*      ÿ�ν�����������ź���, ����������ͷ�
*/
sem_t northSemaphore;
sem_t southSemaphore;


void* south2NorthTread(void *) {
	for (int i = 0; i < 100; i++) {
		sem_wait(&southSemaphore);
		std::cout << "�����š�������\n";
		std::cout << "ͨ���š�������\n";
		sem_post(&southSemaphore);

		sem_wait(&northSemaphore);
		std::cout << "�뿪�š�������\n";
		sem_post(&northSemaphore);
	}
	return NULL;
}

void* north2SouthTread(void *) {
	for (int i = 0; i < 100; i++) {
		sem_wait(&northSemaphore);
		std::cout << "�����š�������\n";
		std::cout << "ͨ���š�������\n";
		sem_post(&northSemaphore);

		sem_wait(&southSemaphore);
		std::cout << "�뿪�š�������\n";
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