# Poulet-Numbers
4th Year Project<br/>
A Poulet number p, is an odd composite number such that 2^(p-1) if divided by p gives a remainder of 1.<br/>

The idea of the project was to work with threads. In order to disperse the work of the threads in an equal manner we will take the number of the thread (starting from 1) and we will multiply with 2 and add 1 (THREAD_NUMBER*2+1), this way the first number is 3. Each of these numbers are checked to see if they are Poulet Numbers. In each of the following iterations, each thread will get a new number, this number is incremented by 2*NUMBER_OF_THREADS (it helps performance that we verify only odd numbers if they are composite).<br/>

The project was implemented in 4 formats:
- C using Pthreads
- C using OpenMP
- Java using GMP(for big number calculations)
- Prolog
