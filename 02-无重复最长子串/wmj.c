
/*author 马伟杰 */

#include <stdlib.h>
#include <string.h>
#include <stdio.h>
#include <limits.h>
#include <stdbool.h>
#include <time.h>

#define max(a,b) ((a)>(b)?(a):(b))
// #define min(a,b) ((a)<(b)?(a):(b))

int main(int argc, char const *argv[])
{
	struct timespec start_time,end_time;
	clock_gettime(CLOCK_PROCESS_CPUTIME_ID, &start_time);  
	/*
		CLOCK_REALTIME:系统实时时间,随系统实时时间改变而改变,即从UTC1970-1-1 0:0:0开始计时,
	中间时刻如果系统时间被用户改成其他,则对应的时间相应改变
	　　CLOCK_MONOTONIC:从系统启动这一刻起开始计时,不受系统时间被用户改变的影响
	　　CLOCK_PROCESS_CPUTIME_ID:本进程到当前代码系统CPU花费的时间
	　　CLOCK_THREAD_CPUTIME_ID:本线程到当前代码系统CPU花费的时间
	*/
	char *p_start= "abcdefghijkabcdefghijklmn\0", *p_position = NULL ;
	const int cacheSize = 256 * sizeof(unsigned char);
	char *p_cache = malloc(cacheSize);
	int max_sub = 0;
	// start time
	

	while(true)
	{
		p_position = p_start;
		if ('\0' == *p_position)
		{
			break;
		}
		// 清零
		memset((void *)p_cache , 0 , cacheSize);
		while(true)
		{
			if('\0' == *p_position || p_cache[*p_position] == 1)
			{
				int sub_length = (int)(p_position - p_start);
				max_sub = max(sub_length,max_sub);
				break;
			}
			else 
			{
				p_cache[*p_position] = 1;
				++p_position;
			}
			
		}
		++p_start;
	}
	printf("无重复字符的最长=%d\n", max_sub);
	free((void *)p_cache);

	//end time
	clock_gettime(CLOCK_PROCESS_CPUTIME_ID, &end_time); 
	printf("使用CPU的时间:%ld秒:%ld纳秒\n",end_time.tv_sec - end_time.tv_sec, end_time.tv_nsec - start_time.tv_nsec );
	return 0;
}
/*
answer:
无重复字符的最长=14
使用CPU的时间:0秒:39000纳秒
*/
