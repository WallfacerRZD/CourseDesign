#include<pthread.h>
#include<iostream>

int x = 0;
pthread_mutex_t mutex;

void* hello(void *) {
	pthread_mutex_lock(&mutex);
	std::cout << "------------" << std::endl;
	std::cout << "hwllo" << std::endl;
	std::cout << "hllo" << std::endl;
	std::cout << "hlo" << std::endl;
	std::cout << "hwll" << std::endl;
	pthread_mutex_unlock(&mutex);
	return NULL;
}

int main() {
	pthread_mutex_init(&mutex, NULL);
	pthread_t threads[20];
	for (int i = 0; i < 10; i++) {
		pthread_create(threads+i, NULL, hello, NULL);
	}
	for (int i = 0; i < 10; i++) {
		pthread_join(threads[i], NULL);
	}
	system("pause");
	return 0;
}
