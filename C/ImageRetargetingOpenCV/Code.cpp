#include "stdafx.h"
#include "common.h"
#include <queue>
#include <random>
#include <math.h> 

using namespace std;

//--------------------------------------------------------------------------------------------
// PROJECT
//--------------------------------------------------------------------------------------------

//e(I)= pd(I,x)+pd(I,y) -- pd=PartialDerivative
//image is grayscale image, output is the output of the function
void energyFunction(Mat &image, Mat &output)
{
	Mat dx, dy;
	Mat adx, ady;

	// Gradient X
	Sobel(image, dx, CV_64F, 1, 0, 3, 1, 0, BORDER_DEFAULT);
	convertScaleAbs(dx, adx);

	// Gradient Y
	Sobel(image, dy, CV_64F, 0, 1, 3, 1, 0, BORDER_DEFAULT);
	convertScaleAbs(dy, ady);

	addWeighted(adx, 0.5, ady, 0.5, 0, output);

}

//image(the energy image) to find the seam on
std::vector<Point> findSeam(Mat &image)
{
	Mat minEn(image.size(), CV_8U);

	//copy the first row
	for (int c = 0; c < image.cols; c++) 
	{
		minEn.at<uchar>(0,c) = image.at<uchar>(0, c);
	}
	//M(i,j)=e(i,j)+min(M(i-1, j-1), M(i-1, j), M(i-1,j+1))
	//starts from 1, because you cant go r-1 at 0
	for (int r = 1; r < image.rows; r++) 
	{
		for (int c = 0; c < image.cols; c++) 
		{
			// the min(...)
			if (c == 0)
				minEn.at<uchar>(r, c) = min(minEn.at<uchar>(r - 1, c + 1), minEn.at<uchar>(r - 1, c));
			else 
				if (c == image.cols - 1)
					minEn.at<uchar>(r, c) = min(minEn.at<uchar>(r - 1, c - 1), minEn.at<uchar>(r - 1, c));
				else
					minEn.at<uchar>(r, c) = min(minEn.at<uchar>(r-1, c-1), min(minEn.at<uchar>(r - 1, c), minEn.at<uchar>(r - 1, c+1)));

			//e(i,j)+...
			minEn.at<uchar>(r, c) = minEn.at<uchar>(r, c) + image.at<uchar>(r, c);
		}
	}

	//At the end of this process, the minimum value of the last row in M will indicate the end of the minimal connected vertical seam.
	int min_value = INT_MAX; 
	int min_index = -1;
	for (int c = 0; c < image.cols; c++)
		if (minEn.at<uchar>(image.rows-1, c) < min_value) {
			min_value = minEn.at<uchar>(image.rows - 1, c);
			min_index = c;
		}

	
	std::vector<Point> patherino;

	//pos starting position is the minumum value on the last row
	Point pos(image.rows - 1, min_index);

	patherino.push_back(pos);
	
	while (pos.x > 0)
	{	
		//value gets the minimum from the 3 above
		int value = minEn.at<uchar>(pos.x, pos.y) - image.at<uchar>(pos.x, pos.y);

		int r = pos.x, c = pos.y;

		if (c == 0) 
		{
			if (value == minEn.at<uchar>(r-1, c+1))
				pos = Point(r - 1, c + 1);
			else
				pos = Point(r - 1, c);
		}
		else 
			if (c == image.cols - 1) 
			{
				if (value == minEn.at<uchar>(r-1, c-1))
					pos = Point(r - 1, c - 1);
				else
					pos = Point(r - 1, c);
			}
			else 
			{
				if (value == minEn.at<uchar>(r - 1, c - 1))
					pos = Point(r - 1, c - 1);
				else 
					if (value == minEn.at<uchar>(r - 1, c + 1))
						pos = Point(r - 1, c + 1);
					else
						pos = Point(r - 1, c);
			}
		patherino.push_back(pos);
		
	}

	Point aux;
	for(int i=0; i<(int)patherino.size(); i++)
		for (int j = 1; j < (int)patherino.size(); j++)
			if (patherino.at(i).x > patherino.at(j).x)
			{
				aux = patherino.at(i);
				patherino.at(i) = patherino.at(j);
				patherino.at(j) = aux;
			}

	return patherino;
}

//remove seams for shrinking
void removeSeam(Mat& image, Mat& output, std::vector<Point> seam) {
	Mat aux(image.size(), CV_8UC3);
	for (int r = 0; r < image.rows; r++) 
	{
		for (int c = 0; c < image.cols-1; c++) {
			if (c >= seam.at(r).y)
				aux.at<Vec3b>(r, c) = image.at<Vec3b>(r, c + 1);
			else
				aux.at<Vec3b>(r, c) = image.at<Vec3b>(r, c);
		}
	}
	output = aux.colRange(0, aux.cols-1);
}

//add seam for enlarging
void addSeam(Mat& image, Mat& output, std::vector<Point> seam) {
	for (int r = 0; r < image.rows; r++) {
		for (int c = 0; c < image.cols; c++) {
			if (c > seam.at(r).y)
				output.at<Vec3b>(r, c) = image.at<Vec3b>(r, c - 1);
			else
				if(c < seam.at(r).y)
					output.at<Vec3b>(r, c) = image.at<Vec3b>(r, c);
				else
				{
					if(c-1<0)
						output.at<Vec3b>(r, c) = image.at<Vec3b>(r, c);
					else
					{
						output.at<Vec3b>(r, c)[0] = (image.at<Vec3b>(r, c)[0] + image.at<Vec3b>(r, c - 1)[0]) / 2;
						output.at<Vec3b>(r, c)[1] = (image.at<Vec3b>(r, c)[1] + image.at<Vec3b>(r, c - 1)[1]) / 2;
						output.at<Vec3b>(r, c)[2] = (image.at<Vec3b>(r, c)[2] + image.at<Vec3b>(r, c - 1)[2]) / 2;
					}
					
					if(c+1>255)
						output.at<Vec3b>(r, c + 1) = image.at<Vec3b>(r, c);
					else
					{
						output.at<Vec3b>(r, c + 1)[0] = (image.at<Vec3b>(r, c)[0] + image.at<Vec3b>(r, c + 1)[0]) / 2;
						output.at<Vec3b>(r, c + 1)[1] = (image.at<Vec3b>(r, c)[1] + image.at<Vec3b>(r, c + 1)[1]) / 2;
						output.at<Vec3b>(r, c + 1)[2] = (image.at<Vec3b>(r, c)[2] + image.at<Vec3b>(r, c + 1)[2]) / 2;
					}
					c++;
				}
		}
	}
}

//function that extracts nr number of seams
std::vector<vector<Point>> extractSeams(std::vector<Mat> matVec, int nr)
{
	std::vector<vector<Point>> seamVec;

	for (int l = 0; l < nr; l++)
	{
		Mat last = matVec.at(matVec.size() - 1);
		Mat enOut;
		Mat grayOut;
		cvtColor(last, grayOut, CV_BGR2GRAY);
		energyFunction(grayOut, enOut);
		std::vector<Point> seam = findSeam(enOut);
		seamVec.push_back(seam);

		Mat fout(last.rows, last.cols - 1, CV_8UC3);
		removeSeam(last, fout, seam);

		matVec.push_back(fout);
	}
	return seamVec;
}

//shrinking function
void shrink(Mat& output, std::vector<Mat> matVec, int lines, char seamType)
{
	for (int l = 0; l < lines; l++)
	{
		Mat last = matVec.at(matVec.size() - 1);
		Mat enOut;
		Mat grayOut;
		cvtColor(last, grayOut, CV_BGR2GRAY);
		energyFunction(grayOut, enOut);
		std::vector<Point> seam = findSeam(enOut);

		Mat fout(last.rows, last.cols - 1, CV_8UC3);
		removeSeam(last, fout, seam);

		matVec.push_back(fout);

	}

	if (seamType == 'v')
		output = matVec.at(matVec.size() - 1);
	else
		output = matVec.at(matVec.size() - 1).t();
}

//enlarging function
void enlarge(Mat& output, std::vector<Mat> matVec, int lines, char seamType)
{
	std::vector<vector<Point>> seams = extractSeams(matVec, lines);

	for (int l = 0; l < lines; l++)
	{
		Mat last = matVec.at(matVec.size() - 1);
		Mat enOut;
		Mat grayOut;
		cvtColor(last, grayOut, CV_BGR2GRAY);
		energyFunction(grayOut, enOut);

		Mat fout(last.rows, last.cols + 1, CV_8UC3);
		addSeam(last, fout, seams.at(l));

		matVec.push_back(fout);
	}

	if (seamType == 'v')
		output = matVec.at(matVec.size() - 1);
	else
		output = matVec.at(matVec.size() - 1).t();
}

void project() {

	char fname[MAX_PATH];

	while (openFileDlg(fname))
	{
		Mat image = imread(fname, 1);
		Mat energia(image.size(), CV_8UC1);
		Mat seam(image.size(), CV_8UC3);
		
		//GRAY IMAGE
		Mat gray;
		cvtColor(image, gray, CV_BGR2GRAY);
		
		//ENERGY IMAGE
		energyFunction(gray, energia);

		//SEAM IMAGE
		for (int i = 0; i < image.rows; i++)
			for (int j = 0; j < image.cols; j++)
			{
				seam.at<Vec3b>(i, j)[0] = image.at<Vec3b>(i, j)[0];
				seam.at<Vec3b>(i, j)[1] = image.at<Vec3b>(i, j)[1];
				seam.at<Vec3b>(i, j)[2] = image.at<Vec3b>(i, j)[2];
			}

		std::vector<Point> path = findSeam(energia);

		for (int k = 0; k<path.size(); k++)
		{
			seam.at<Vec3b>(path.at(k).x, path.at(k).y)[0] = 0;
			seam.at<Vec3b>(path.at(k).x, path.at(k).y)[1] = 0;
			seam.at<Vec3b>(path.at(k).x, path.at(k).y)[2] = 255;
		}


		//OPERATIONS
		char operation;
		std::cout << "Simple Operation or Resize or Content Amplification (s/r/c?)" << endl;
		do
		{
			std::cin >> operation;
		} while (operation != 's' && operation != 'r' && operation != 'c');

		Mat output;

		
		//SIMPLE OPERATION
		if(operation=='s')
		{ 
			//INPUTS
			char type;
			std::cout << "Enlarge or Shrink (e/s?)" << endl;
			do
			{
				std::cin >> type;
			} while (type != 'e' && type != 's');
		
			std::cout << "Vertical or Horizontal seams (v/h?)"<<endl;
			char seamType;
			do
			{
				std::cin >> seamType;
			} while(seamType !='h' && seamType !='v');

			int lines;
			std::cout << "Modify by:" << endl;
			do
			{
				std::cin >> lines;
			} while (lines<0 || lines>image.cols / 2);
		


			std::vector<Mat> matVec;
		
			if (seamType == 'v')
			{
				matVec.push_back(image);
			}
			else
			{
				matVec.push_back(image.t());
			}

			//SHRINKING
			if(type=='s')
			{ 
				shrink(output, matVec, lines, seamType);
			}
			//ENLARGING
			else
			{
				enlarge(output, matVec, lines, seamType);
			}

			

		}
		//RESIZE
		if (operation == 'r')
		{
			int newRows, newCols;
			std::cout << "Current size: " << image.rows << "x" << image.cols << endl;
			std::cout << "New size: " << endl;
			std::cout << "Rows: ";
			std::cin >> newRows;
			std::cout << "Cols: ";
			std::cin >> newCols;

			std::vector<Mat> matVec1, matVec2;
			Mat inter;

			matVec1.push_back(image);
			if (image.rows>newRows)
				shrink(inter, matVec1, image.rows - newRows, 'v');
			if (image.rows<newRows)
				enlarge(inter, matVec1, newRows - image.rows, 'v');
			if (image.rows == newRows)
				inter = image;

			matVec2.push_back(inter.t());
			if (image.cols>newCols)
				shrink(output, matVec2, image.cols - newCols, 'h');
			if (image.cols<newCols)
				enlarge(output, matVec2, newCols - image.cols, 'h');
			if (image.cols == newCols)
				output = inter;
		}
		//CONTENT AMPLIFICATION
		Mat interE, interSV, interSH;
		if (operation == 'c')
		{
			//ALGORITHM 1
			std::vector<Mat> matVec1, matVec2, matVec3, matVec4;

			//ENLARGE by 50%
			resize(image, interE, Size(image.cols*3/2, image.rows * 3 / 2));

			//SHRINK by 50%
			matVec3.push_back(interE);
			shrink(interSV, matVec3, image.cols/ 2, 'v'); //output first param
			matVec4.push_back(interSV.t());
			shrink(interSH, matVec4, image.rows / 2, 'h');

			output = interSH;

			//ALGORITHM 2
			/*
			std::vector<Mat> matVec1, matVec2, matVec3, matVec4;

			//ENLARGE by 50%
			resize(image, interE, Size(image.cols * 3 / 2, image.rows * 3 / 2));

			//SHRINK by 50%
			int max = max(image.rows / 2, image.cols / 2);
			int r = image.rows / 2;
			int c = image.cols / 2;
			while (max > 0)
			{
				Mat inter;
				if (max == max(image.rows / 2, image.cols / 2))
					inter = interE;
				else
					inter = interSH;
				if (r > 0 && c>0)
				{
					matVec3.push_back(inter);
					shrink(interSV, matVec3, 1, 'v'); //output first param

					matVec4.push_back(interSV.t());
					shrink(interSH, matVec4, 1, 'h');
				}
				if (c <= 0)
				{
					matVec4.push_back(interSH.t());
					shrink(interSH, matVec4, 1, 'h');


				}
				if (r <= 0)
				{
					matVec3.push_back(interSH);
					shrink(interSH, matVec3, 1, 'v'); //output first param
				}

				r--;
				c--;
				max--;

			}
			output = interSH;
			*/
		}

		
		
		//imwrite("energy.jpg", energia);
		//imwrite("contentAmp.jpg", output);

		imshow("Initial", image);
		imshow("EnergyFunction", energia);
		imshow("Seam", seam);
		imshow("Output", output);
		waitKey(0);
	}
}



//--------------------------------------------------------------------------------------------
// MAIN
//--------------------------------------------------------------------------------------------
int main()
{
	int op;
	do
	{
		system("cls");
		destroyAllWindows();
		printf("Menu:\n");
		printf(" 2018 - PROJECT\n");
		printf(" 0 - Exit\n\n");
		printf("Option: ");
		scanf("%d",&op);
		switch (op)
		{
			//PROJECT
			case 2018:
				project();
				break;
		}
	}
	while (op!=0);
	return 0;
}