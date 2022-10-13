#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <pthread.h>
#include <math.h>
#include <time.h>
#include <gmp.h>

#define BILLION 1E9
#define NR_THREADS 8
#define N 6750000	

int isComposite(int n)
{
    // Corner cases
    if (n <= 1)  return 0;
    if (n <= 3)  return 0;

    // This is checked so that we can skip
    // middle five numbers in below loop
    if (n%2 == 0 || n%3 == 0) return 1;

    for (int i=5; i*i<=n; i=i+6)
        if (n%i == 0 || n%(i+2) == 0)
            return 1;
    return 0;
}

void *worker(int threadNumber) {
    	int p = threadNumber*2+1;
	
	mpz_t result, rest;
	mpz_init(result);
	mpz_init(rest);
    
    	while(p<N)
    	{
		mpz_ui_pow_ui(result, 2, p-1);
		mpz_mod_ui(rest, result, p);
        	if (isComposite(p) == 1) {
            		if(mpz_cmp_ui(rest, 1)==0) {
                		printf("Thread %d found %d\n", threadNumber, p);
            		}
        	}
        	p += NR_THREADS*2;
    	}
    	pthread_exit("valoare");
}

int main (void) {
    	pthread_t *th = malloc(sizeof(pthread_t)*NR_THREADS);
    
    	struct timespec start, finish; 
    	clock_gettime(1, &start);
    
    	for(int i=0; i<NR_THREADS; i++)
    	{
        	pthread_create(&th[i], NULL, worker, i+1);
    	}

    	for(int i=0; i<NR_THREADS; i++)
    	{
        	pthread_join(th[i], (void**) NULL);
    	}    

    	clock_gettime(1, &finish); 
    	/* Compute the execution time*/
    	double accum = ( finish.tv_sec - start.tv_sec ) + ( finish.tv_nsec - start.tv_nsec )/ BILLION;
    	printf( "TIME: %lf\n", accum );
    
    	return 0;
}
