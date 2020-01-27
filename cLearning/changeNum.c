#include<stdio.h>
void switchNumByForm(int a,int b);
void switchNumByLocation(int *a,int *b);
int main(){
	int a=1,b=2;
	switchNumByLocation(&a,&b);
	printf("a=%d,b=%d\n",a,b);
	return 0;
}
void switchNumByForm(int a,int b){
	int temp=a;
	a=b;
	b=temp;
}
void switchNumByLocation(int *a,int *b){
	int temp=*a;
	*a=*b;
	*b=temp;
}
