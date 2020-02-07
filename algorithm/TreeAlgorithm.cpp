#include<iostream>
using namespace std;
template<typename T>
int binarySearch(T arr[],int n,T target){
	int l=0,r=n;
	int mid=(l+r)/2;
	while(l<r){
		if(arr[mid]==target)
			return mid;
		if(arr[mid]>target){
			r=mid;
		}else{
			l=mid;
		}	
		mid=(l+r)/2;

	}
	return -1;
}
int main(){
	int arr[]={1,2,3,4,5,6,7};
	cout<<"我查的元素是7,位置是:"<<binarySearch(arr,7,7)<<endl;
	return 0;
}
