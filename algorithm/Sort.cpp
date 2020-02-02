#include<iostream>
#include"Student.h"
#include"SortTestHelper.h"
using namespace std;
template<typename T>
void selectionSort(T arr[],int n){
	for(int i=0;i<n;i++){
		int minIndex=i;
		for(int j=i+1;j<n;j++){
			if(arr[j]<arr[minIndex]){
			minIndex=j;
			}
		}
		swap(arr[i],arr[minIndex]);
	}
}
template<typename T>
void insertionSort(T arr[],int n){
	for(int i=1;i<n;i++){
		T e=arr[i];
		int j;
		for(j=i;j>0&&arr[j-1]>e;j--){
			arr[j]=arr[j-1];
		}
		arr[j]=e;
	}
}
template<typename T>
void bubbleSort(T arr[],int n){
	for(int i=0;i<n;i++){
		for(int j=0;j<n-i;j++){
			if(arr[j]<arr[j-1])
				swap(arr[j],arr[j-1]);
		}
	}
}
template<typename T>
void shellSort(T arr[],int n){
	int gap=n/2;
	while(gap>1){
		for(int i=0;i<gap;i++){
			if(arr[i]>arr[i+gap])
				swap(arr[i],arr[i+gap]);
		}
		gap=gap/2;
	}
	insertionSort(arr,n);
}
template<typename T>
void mergeSort(T arr[],int l,int r){
	if(r-l<1)
		return;//递归终点
	int m=(l+r)/2;
	mergeSort(arr,l,m);
	mergeSort(arr,m+1,r);
	T temp[r-l+1];
	for(int i=l;i<r+1;i++)
		temp[i-l]=arr[i];//开辟临时数组
	int i=l,j=m+1;
	for(int k=l;k<r+1;k++){//k是最终下标
		if(i>m){
			arr[k]=temp[j-l];
			j++;
		}else if(j>r){
			arr[k]=temp[i-l];
			i++;
		}
		else if(temp[i-l]<temp[j-l]){
			arr[k]=temp[i-l];
			i++;
		}else{
			arr[k]=temp[j-l];
			j++;
		}
	}
}

int main(){
	int a[10]={10,9,8,7,6,5,4,3,2,1};
	selectionSort(a,10);
	for(int i=0;i<10;i++){
		cout<<a[i]<<"  ";
	}
	cout<<endl;
	float b[4]={4.4,3.3,2.2,1.1};
	selectionSort(b,4);
	for(int i=0;i<4;i++){
		cout<<b[i]<<"  ";
	}
	cout<<endl;
	string c[4]={"d","c","b","a"};
	selectionSort(c,4);
	for(int i=0;i<4;i++){
		cout<<c[i]<<"  ";
	}
	cout<<endl;
	Student d[4]={{"d",90},{"c",100},{"b",95},{"a",96}};
	selectionSort(d,4);
	for(int i=0;i<4;i++){
		cout<<d[i];
	}
	cout<<endl;
	int n=30000;
	int *arr=SortTestHelper::generateNearlyOrderedArray(n,1000);
	int *arr2=SortTestHelper::copyIntArray(arr,n);
	int *arr3=SortTestHelper::generateRandomArray(10,1,100);
	mergeSort(arr3,0,9);
	SortTestHelper::printArray(arr3,10);
	//selectionSort(arr,n);
	//SortTestHelper::printArray(arr,n);
	SortTestHelper::testSort("Insertion Sort",insertionSort,arr,n);
	SortTestHelper::testSort("Selection Sort",selectionSort,arr2,n);
	cout<<endl;			        
      	delete[] arr;
	delete[] arr2;
	delete[] arr3;
	return 0;
}
