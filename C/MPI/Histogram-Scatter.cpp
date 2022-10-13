#include "stdafx.h"

#include <iostream>
#include <iomanip>
#include <fstream>
#include <sstream>
#include "mpi.h"

using namespace std;

int main(int argc, char* argv[])
{
	MPI_Init(&argc, &argv);

	int world_size;
	MPI_Comm_size(MPI_COMM_WORLD, &world_size);

	int rank;
	MPI_Comm_rank(MPI_COMM_WORLD, &rank);

	ifstream inFile;
	inFile.open("numberss.txt");

	int data_size = 0;
	inFile >> data_size;

	int *data = (int*)malloc(data_size * sizeof(int));

	int batch_size = data_size / world_size;
	int *rec_buff = (int*)malloc(batch_size * sizeof(int));
	for (int i = 0; i < batch_size; i++) {
		rec_buff[i] = -1;
	}

	int histogram[256];
	if (rank == 0) {

		for (int i = 0; i < data_size; i++) {
			inFile >> data[i];
		}


		for (int i = 0; i < 256; i++) {
			histogram[i] = 0;
		}
	}

	

	MPI_Scatter(data, batch_size, MPI_INT, rec_buff, batch_size, MPI_INT, 0, MPI_COMM_WORLD);

	int local_histogram[256];
	for (int i = 0; i < 256; i++) {
		local_histogram[i] = 0;
	}


	for (int i = 0; i < batch_size; i++) {
		if (rec_buff[i] != -1) {
			local_histogram[rec_buff[i]]++;
		}
	}

	MPI_Reduce(local_histogram, histogram, 256, MPI_INT, MPI_SUM, 0, MPI_COMM_WORLD);

	if (rank == 0) {
		if (batch_size * world_size != data_size) {
			int extras = data_size - (batch_size * world_size);
			for (int i = 0; i < extras; i++) {
				histogram[data[data_size - i - 1]]++;
			}
		}
		for (int i = 0; i < 256; i++) {
			printf("%d: %d \n", i, histogram[i]);
		}
	}

	MPI_Finalize();

	return 0;
}