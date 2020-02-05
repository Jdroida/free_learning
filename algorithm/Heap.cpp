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
		void show(){
			for(int i=1;i<=count;i++)
				cout<<data[i]<<"  ";
		}
};
int main(){
	Heap<int> heap=Heap<int>(100);
	for(int i=0;i<7;i++){
		heap.insert(i+1);
	}
	heap.show();
	cout<<"数量:"<<heap.size()<<endl;
	return 0;
}
