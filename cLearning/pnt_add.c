//指针地址
#include<stdio.h>
#define SIZE 4
int main(){
	short dates[SIZE];//数组名是该数组首元素的地址 dates==&dates[0] *dates==dates[0]
	short *pti;
	short index;
	double bills[SIZE];
	double *ptf;
	pti=dates;
	ptf=bills;
	printf("%23s %15s\n","short","double");
	for(index=0;index<SIZE;index++){
		printf("pointers+%d: %10p %10p\n",index,pti+index,ptf+index);
	}
	dates[0]=3;
	printf("dates=%p=地址\n&dates[0]=%p=地址\n*dates=%d=数值\n*&dates[0]=%d=数值\n",dates,&dates[0],*dates,*&dates[0]);
	//指针和整数相加时，整数会和指针所指向类型的大小相乘，然后把结果和初始地址相加。指针求差计算的是两元素之间的距离，单位与数组类型单位相同。
	return 0;
}
