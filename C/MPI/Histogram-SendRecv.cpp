// MPIHelloWorld.cpp : Defines the entry point for the console application.
//

#include "mpi.h"
#include "stdio.h"
#include "stdlib.h"
#include <fstream>
#include <iostream>
#include <cstring>

using namespace std;

ifstream myfile;

void computeHistogram(int histogram[256], int world_rank, int world_size)
{
	myfile.open("numere.txt");
	int file_size;
	myfile >> file_size;
	int processLength = file_size / world_size;
	int startLocation = processLength * world_rank;//start location
	
	if (file_size != processLength*world_size)
	{
		if (world_rank == world_size - 1)
			processLength = processLength + (file_size - processLength*world_size);
	}
	if (startLocation>0)
	{
		int c;
		while (startLocation > 1)
		{
			myfile >> c;
			startLocation--;
		}
	}
	int nr;
	myfile >> nr;
	for (int i = 0; i < processLength; i++)
	{
		myfile >> nr;
		histogram[nr]++;
	}
	myfile.close();
}

int main(int argc, char* argv[])
{
	//init size
	int file_size;


	//MPI
	MPI_Init(&argc, &argv);
	int world_rank;
	MPI_Comm_rank(MPI_COMM_WORLD, &world_rank);
	int world_size;
	MPI_Comm_size(MPI_COMM_WORLD, &world_size);



	//init histogram array
	int histogram[256];
	for (int i = 0; i < 256; i++)
		histogram[i] = 0;

	if (world_rank == 0)
	{
		//init histogram array
		for (int i = 0; i < 256; i++)
			histogram[i] = 0;

		computeHistogram(histogram, world_rank, world_size);
		int histogramLocal[256];
		for (int i = 1; i < world_size; i++)
		{
			MPI_Recv(&histogramLocal, 256, MPI_INT, i, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
			for (int i = 0; i < 256; i++)
			{
				histogram[i] += histogramLocal[i];
			}
		}

		printf("\n\nSolution:\n");
		for (int i = 0; i < 256; i++)
			printf("%d ", histogram[i]);
	}
	else
	{

		computeHistogram(histogram, world_rank, world_size);
		MPI_Send(&histogram, 256, MPI_INT, 0, 0, MPI_COMM_WORLD);
	}

	MPI_Finalize();
	return 0;
}