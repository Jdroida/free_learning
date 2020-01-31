#include<iostream>
using namespace std;
void selectionSort(int arr[],int n){
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
int main(){
	int a[10]={10,9,8,7,6,5,4,3,2,1};
	selectionSort(a,10);
	for(int i=0;i<10;i++){
		cout<<a[i]<<"  ";
	}
	cout<<endl;
	return 0;
}
