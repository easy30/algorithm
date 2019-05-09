#include<stdio.h>
#include<limits.h>
#include<stdlib.h>

#define max(a,b) ((a)>(b)?(a):(b))
#define min(a,b) ((a)<(b)?(a):(b))


int main()
{
	int array[] = {2, 7,-3, 11, 15}, target = -1;
	int maxValue = INT_MIN, minValue = INT_MAX;
	int *leftStart = NULL,*rightStart = NULL;
	// 数组数据长度
	long size = 0;
	size = sizeof(array) / sizeof(array[0]);

	for (int i = 0; i < size; ++i)
	{
		maxValue = max(array[i],maxValue);
		minValue = min(array[i],minValue);
	}
	rightStart = (int *) calloc(1, (maxValue + 1) * sizeof(int));
	if (minValue < 0 )
	{
		leftStart = (int *)calloc(1, (abs(minValue) + 1) * sizeof(int));
	}
	for (int i = 0; i < size; ++i)
	{
		if (array[i] >= 0 ){
			rightStart[array[i]] = i + 1;
		}
		else
		{
			leftStart[abs(array[i])] = i + 1;
		}
	}
	bool exist = false;
	for (int i = 0; i < size; ++i)
	{
		int tmp = target - array[i];
		if (tmp >= 0 && 
			rightStart[tmp] > 0 &&
			rightStart[tmp] - 1 != i) 
		{
			exist = true;
			printf("[%d,%d]\n", i,rightStart[tmp] - 1);
			break;
		}else if (tmp < 0 && 
			leftStart[abs(tmp)] > 0 && 
			leftStart[abs(tmp)] - 1 != i)
		{
			exist = true;
			printf("[%d,%d]\n", i,leftStart[abs(tmp)] - 1);
			break;
		}
	}
	if (!exist)
	{
		printf("%s\n", "none an answer");
	}

	if (rightStart != NULL )
	{
		free(rightStart);
	}
	if (leftStart != NULL ) 
	{
		free(leftStart);
	}

	return 0;
}
