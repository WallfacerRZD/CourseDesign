#include<pthread.h>
#include<stdio.h>
int x = 0;
pthread_mutex_t mutex;

void hello() {
	pthread_mutex_lock(&mutex);
	x++;
	printf("hello, world!\n");
	pthread_mutex_unlock(&mutex);
	return;
}

int main() {
	pthread_mutex_init(&mutex, NULL);
	pthread_t threads[20];
	for (int i = 0; i < 20; i++) {
		pthread_create(threads+i, NULL, hello, NULL);
	}
	for (int i = 0; i < 20; i++) {
		pthread_join(threads[i], NULL);
	}
	printf("%d", x);
	system("pause");
	return 0;
}
