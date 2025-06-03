#include<iostream>
#include<ctime>
#include<cassert>
using namespace std;
template<typename T>
class Heap{
	private:
		T* data;
		int count;
		int capacity;
	public:
		Heap(int capacity){
			data=new T[capacity+1];
			count=0;
			this->capacity=capacity;
		}
		Heap(T arr[],int n){
			data=new T[n+1];
			capacity=n;
			count=n;
			for(int i=0;i<n;i++)
				data[i+1]=arr[i];
			for(int j=count/2;j>=1;j--){
				int i=j;
				while(2*i<=count){
				//如果有右孩子并且左右孩子里大的那个比根节点更大 交换
					if(2*i+1<=count){
						int biggerIndex=data[i*2]>data[i*2+1]?2*i:i*2+1;
						if(data[biggerIndex]>data[i]){
							swap(data[i],data[biggerIndex]);
							i=biggerIndex;
						}else//如果左右孩子都比根节点小 终点
							break;
					}else {//没有右孩子的时候只要和左孩子比较即可
						if(data[i]<data[2*i]){
							swap(data[i],data[2*i]);
							i=2*i;
						}else
							break;
					}
				}
			}
		}
		~Heap(){
			delete[] data;
		}
		int size(){
			return count;
		}
		bool isEmpty(){
			return count==0;
		}
		void insert(T item){
			assert(count+1<=capacity);
			data[count+1]=item;
			count++;
			int tempCount=count;
			while(tempCount>1&&data[tempCount]>data[tempCount/2]){
				swap(data[tempCount],data[tempCount/2]);
				tempCount=tempCount/2;
			}
		}
		T remove(){
			assert(count>0);
			T item=data[1];
			swap(data[1],data[count]);
			count--;
			int i=1;
			while(2*i<=count){
				//如果有右孩子并且左右孩子里大的那个比根节点更大 交换
				if(2*i+1<=count){
					int biggerIndex=data[i*2]>data[i*2+1]?2*i:i*2+1;
					if(data[biggerIndex]>data[i]){
						swap(data[i],data[biggerIndex]);
						i=biggerIndex;
					}else//如果左右孩子都比根节点小 终点
						break;
				}else {//没有右孩子的时候只要和左孩子比较即可
					if(data[i]<data[2*i]){
						swap(data[i],data[2*i]);
						i=2*i;
					}else
						break;
				}
			}
			return item;
		}
		void show(){
			for(int i=1;i<=count;i++)
				cout<<data[i]<<"  ";
			cout<<endl;
		}
};
int main(){
	int arr[]={1,2,3,4,5,6,7};
	Heap<int> heap=Heap<int>(arr,7);
	heap.show();
	return 0;
}
