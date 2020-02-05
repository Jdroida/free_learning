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
						swap(data[i],data[2*i+1]);
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
		}
};
int main(){
	Heap<int> heap=Heap<int>(100);
	for(int i=0;i<7;i++){
		heap.insert(i+1);
	}
	heap.show();
	cout<<"数量:"<<heap.size()<<endl;
	heap.remove();
	heap.show();
	cout<<"数量:"<<heap.size()<<endl;
	return 0;
}
