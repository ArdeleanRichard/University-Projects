#include<iostream>
#include<omp.h>
#include<gmp.h>
#include <ctime>

#define NR_THREADS 8
#define N 2100000
using namespace std;

int isComposite(int n)
{
	// Corner cases
	if (n <= 1)  return 0;
	if (n <= 3)  return 0;

	// This is checked so that we can skip
	// middle five numbers in below loop
	if (n % 2 == 0 || n % 3 == 0) return 1;

	for (int i = 5; i*i <= n; i = i + 6)
		if (n % i == 0 || n % (i + 2) == 0)
			return 1;
	return 0;
}


int main(int argc, char **argv)
{
	int threadNumber, p;
	clock_t begin = clock();

#pragma omp parallel num_threads(NR_THREADS) private(threadNumber, p)
	{
		mpz_t result, rest;
		mpz_init(result);
		mpz_init(rest);
		threadNumber = omp_get_thread_num() + 1;
		p = 2 * threadNumber + 1;
		while (p<N)
		{
			mpz_ui_pow_ui(result, 2, p - 1);
			mpz_mod_ui(rest, result, p);
			if (isComposite(p) == 1) {
				if (mpz_cmp_ui(rest, 1)== 0) {
					printf("Thread %d found %d\n", threadNumber, p);
				}
			}
			p += NR_THREADS * 2;
		}
	}

	clock_t end = clock();
	double elapsed_secs = (end - begin) / (double)CLOCKS_PER_SEC;
	printf("%lf seconds have passed.\n", elapsed_secs);

	getchar();
	return 0;
}