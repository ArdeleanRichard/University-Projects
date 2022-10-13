#include "mpi.h"
#include "stdio.h"
#include "stdlib.h"
#include <fstream>
#include <iostream>
#include <cstring>

using namespace std;

ifstream mat;

void convert(int matSize, int submatSize, int* A, int *convA)
{
	int i, j, k, l;
	for (j = 0; j < matSize*matSize; j = j + submatSize * matSize)
	{
		for (i = 0; i < submatSize * matSize; i += submatSize * submatSize)
		{
			for (k = 0; k < submatSize; k++)
			{
				convA[j + i + k] = A[j + i / submatSize + k];
				for(l=1;l<submatSize;l++)
					convA[j + i + k + submatSize*l] = A[j + i / submatSize + k + matSize*l];
			}
		}
	}
}

void deconvert(int matSize, int submatSize, int* convA, int *A)
{
	int i, j, k, l;
	for (j = 0; j < matSize*matSize; j = j + submatSize * matSize)
	{
		for (i = 0; i < submatSize * matSize; i += submatSize * submatSize)
		{
			for (k = 0; k < submatSize; k++)
			{
				A[j + i / submatSize + k] = convA[j + i + k];
				for (l = 1; l<submatSize; l++)
					A[j + i / submatSize + k + matSize*l] = convA[j + i + k + submatSize*l];
			}
		}
	}
}


void MatrixMultiply(int n, int *a, int *b, int *c)
{
	int i, j, k;

	for (i = 0; i<n; i++)
		for (j = 0; j<n; j++)
			for (k = 0; k<n; k++)
				c[i*n + j] += a[i*n + k] * b[k*n + j];
}

void startA(int matSize, int submatSize, int *mat)
{
	int i, j,k,l;
	int* copy = new int[matSize*submatSize];
	for (i = 0; i < matSize; i ++)
	{
		int shift = i/ submatSize;
		int* copy = new int[shift*submatSize];
		for (k = 0; k < shift*submatSize; k++)
		{
			copy[k] = mat[i*matSize + k];
		}
		for (j = 0; j < matSize; j++)
		{
			mat[i*matSize + j] = mat[i*matSize + j + shift*submatSize];
		}
		for (k = 0; k < shift*submatSize; k++)
		{
			mat[i*matSize + (matSize-shift*submatSize)+k] = copy[k];
		}

	}
}

void startB(int matSize, int submatSize, int *mat)
{
	int i, j, k, l;
	int* copy = new int[matSize*submatSize];
	for (j = 0; j < matSize; j++)
	{
		int shift = j / submatSize;
		int* copy = new int[shift*submatSize];
		for (k = 0; k < shift*submatSize; k++)
		{
			copy[k] = mat[j + k*matSize];
		}
		for (i = 0; i < matSize; i++)
		{
			mat[i*matSize + j] = mat[(i+shift*submatSize)*matSize + j ];
		}
		for (k = 0; k < shift*submatSize; k++)
		{
			mat[(k+ (matSize-shift*submatSize))*matSize + j] = copy[k];
		}
	}
}


void shiftB(int matSize, int submatSize, int *B)
{
	int i, j;
	int* copy = new int[matSize*submatSize];
	int k = 0;
	for (i = 0; i < submatSize; i++)
	{
		for (j = 0; j < matSize; j++)
		{
			copy[k++] = B[i*matSize + j];
		}
	}
	for (i = 0; i < matSize - submatSize; i++)
	{
		for (j = 0; j < matSize; j++)
		{
			B[i*matSize + j] = B[(i + submatSize)*matSize + j];
		}
	}
	k = 0;
	for (i = matSize - submatSize; i < matSize; i++)
	{
		for (j = 0; j < matSize; j++)
		{
			B[i*matSize + j] = copy[k++];
		}
	}
}

void shiftA(int matSize, int submatSize, int *A)
{
	int i, j;
	int* copy = new int[matSize*submatSize];
	int k = 0;
	for (j = 0; j < submatSize; j++)
	{
		for (i = 0; i < matSize; i++)
		{
			copy[k++] = A[i*matSize + j];
		}
	}

	for (j = 0; j < matSize - submatSize; j++)
	{
		for (i = 0; i < matSize; i++)
		{
			A[i*matSize + j] = A[i*matSize + (j + submatSize)];
		}
	}

	k = 0;
	for (j = matSize - submatSize; j < matSize; j++)
	{
		for (i = 0; i < matSize; i++)
		{
			A[i*matSize + j] = copy[k++];
		}
	}
}

int main(int argc, char *argv[])
{
	int rank, size, matSize = 10, submatSize, i = 0, j = 0, k = 0;
	int nr;

	int *A = new int[matSize * matSize];
	int *B = new int[matSize * matSize];
	int *initialA = new int[matSize * matSize];
	int *initialB = new int[matSize * matSize];
	int *copyC = new int[matSize * matSize];
	int *solutionA = new int[matSize * matSize];
	int *solutionB = new int[matSize * matSize];
	int *solutionC = new int[matSize * matSize];
	int* unorderedC = new int[matSize * matSize];
	int* copyA = new int[matSize*matSize];
	int* copyB = new int[matSize*matSize];
	int* C = new int[matSize*matSize];
	int* convA = new int[matSize*matSize];
	int* deconvA = new int[matSize*matSize];
	int* deconvB = new int[matSize*matSize];

	for (i = 0; i < matSize*matSize; i++)
		solutionC[i] = 0;

	MPI_Init(&argc, &argv);
	//size = number of processes
	MPI_Comm_size(MPI_COMM_WORLD, &size);
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	int sqrtP = (int)sqrt(size);

	// size (nr of processes) = ( n (matrix size) / p (block size) )^2
	submatSize = matSize / sqrt(size);




	if (rank == 0)
	{
		mat.open("A.txt");
		cout << ("A matrix:\n");
		for (i = 0; i<matSize; i++)
		{
			for (j = 0; j<matSize; j++)
			{
				mat >> nr;
				A[i*matSize + j] = nr;
				cout << '\t' << A[i*matSize + j];
			}
			cout << endl;
		}

		mat.close();
		mat.open("B.txt");
		cout << ("\nB matrix:\n");
		for (i = 0; i<matSize; i++)
		{
			for (j = 0; j<matSize; j++)
			{
				mat >> nr;
				B[i*matSize + j] = nr;
				cout << '\t' << B[i*matSize + j];
			}
			cout << endl;
		}
		mat.close();

		MatrixMultiply(matSize, A, B, solutionC);
		cout << ("\SolutionC matrix:\n");
		for (i = 0; i < matSize; i++)
		{
			for (j = 0; j < matSize; j++)
			{
				cout << '\t' << solutionC[i*matSize+j];
			}
			cout << endl;
		}



		startA(matSize, submatSize, A);
		convert(matSize, submatSize, A, initialA);
		cout << ("\nInitialA matrix:\n");
		for (i = 0; i < matSize; i++)
		{
			for (j = 0; j < matSize; j++)
			{
				cout << '\t' << initialA[i*matSize+j];
			}
			cout << endl;
		}


		startB(matSize, submatSize, B);
		cout << ("\nInitialB matrix:\n");
		for (i = 0; i < matSize; i++)
		{
			for (j = 0; j < matSize; j++)
			{
				cout << '\t' << B[i*matSize + j];
			}
			cout << endl;
		}
		convert(matSize, submatSize, B, initialB);
		cout << ("\nInitialB matrix:\n");
		for (i = 0; i < matSize; i++)
		{
			for (j = 0; j < matSize; j++)
			{
				cout << '\t' << initialB[i*matSize + j];
			}
			cout << endl;
		}


		for (i = 0; i<matSize; i++)
			for (j = 0; j < matSize; j++)
			{
				copyA[i*matSize + j] = initialA[i*matSize + j];
				copyB[i*matSize + j] = initialB[i*matSize + j];
			}
	}




	for (i = 0; i < matSize*matSize; i++)
		unorderedC[i] = 0;


	for (int nrShift = 0; nrShift < matSize / submatSize; nrShift++)
	{
		int *localA = new int[submatSize*submatSize];
		int *localB = new int[submatSize*submatSize];
		int *localC = new int[submatSize*submatSize];
		for (i = 0; i < submatSize*submatSize; i++)
			localC[i] = 0;
		for (i = 0; i < matSize*matSize; i++)
			copyC[i] = 0;

		MPI_Scatter(copyA, submatSize*submatSize, MPI_INT, localA, submatSize*submatSize, MPI_INT, 0, MPI_COMM_WORLD);
		MPI_Scatter(copyB, submatSize*submatSize, MPI_INT, localB, submatSize*submatSize, MPI_INT, 0, MPI_COMM_WORLD);

		MatrixMultiply(submatSize, localA, localB, localC);

		MPI_Gather(localC, submatSize*submatSize, MPI_INT, copyC, submatSize*submatSize, MPI_INT, 0, MPI_COMM_WORLD);

		if (rank == 0)
		{
			for (i = 0; i < matSize; i++)
				for (j = 0; j < matSize; j++)
					unorderedC[i*matSize + j] = unorderedC[i*matSize + j] + copyC[i*matSize + j];

			deconvert(matSize, submatSize, copyA, deconvA);
			shiftA(matSize, submatSize, deconvA);
			convert(matSize, submatSize, deconvA, copyA);
			deconvert(matSize, submatSize, copyB, deconvB);
			shiftB(matSize, submatSize, deconvB);
			convert(matSize, submatSize, deconvB, copyB);
		}

	}

	if (rank == 0)
	{
		deconvert(matSize, submatSize, unorderedC, C);
		cout << ("\C matrix:\n");
		for (i = 0; i < matSize; i++)
		{
			for (j = 0; j < matSize; j++)
			{
				cout << '\t' << C[i*matSize+j];
			}
			cout << endl;
		}



	}
	MPI_Finalize();

	return 0;
}
