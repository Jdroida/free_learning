#include<iostream>
#include<queue>
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
//二分搜索树
template<typename Key,typename Value>
class BST{
private:
	struct Node{
		Key key;
		Value value;
		Node *left;
		Node *right;
		Node(Key key,Value value){
			this->key=key;
			this->value=value;
			this->left=NULL;
			this->right=NULL;
		}
	};
	Node *root;
	int count;
public:
	BST(){
		root=NULL;
		count=0;
	}
	~BST(){
		destroy(root);
	}
	Node* getRoot(){
		return root;
	}

	void destroy(Node* node){
		if(node!=NULL){
			destroy(node->left);
			destroy(node->right);
			delete node;
			count--;
		}
	}

	int size(){
		return count;
	}
	bool isEmpty(){
		return count==0;
	}
	void insert(Key key,Value value){
		if(root==NULL)
			root=new Node(key,value);
		count++;
		Node* start=root;
		while(start->left!=NULL||start->right!=NULL){
			//如果比节点小 看看有没有左孩子 如果没有左孩子就加在左孩子上
			//如果比节点大 看看与没有右孩子 如果没有右孩子就加在右孩子上
			//如果有左（右）孩子就迭代下去
			if(key<start->key){
				if(start->left==NULL)
					start->left=new Node(key,value);
				start=start->left;
			}else if(key>start->key){
				if(start->right==NULL)
					start->right=new Node(key,value);
				start=start->right;
			}else{
				start->value=value;
				cout<<"此节点已在树中1"<<endl;
				break;
			}
		}
		if(key<start->key)
			start->left=new Node(key,value);
		else if(key>start->key)
			start->right=new Node(key,value);
		else if(key!=root->key){
			cout<<"此节点已在树中2"<<endl;
			start->value=value;
		}
	}
	void search(Key key){
		if(root==NULL){
			cout<<"现在这是个空树"<<endl;
			return;
		}
		Node* start=root;
		//此处只考虑了非叶子节点的情况
		while(start->left!=NULL||start->right!=NULL){
			if(key<start->key){
				if(start->left==NULL){
					cout<<"找不到节点"<<endl;
					break;
				}
				start=start->left;
			}else if(key>start->key){
				if(start->right==NULL){
					cout<<"找不到节点"<<endl;
					break;
				}
				start=start->right;
			}else{
				cout<<"此节点的value是"<<start->value<<endl;
				break;
			}
		}
		if(key==start->key)
			cout<<"此节点的value是"<<start->value<<endl;

	}
	//前序遍历
	void preOrder(Node* node){
		if(node!=NULL){
			cout<<node->key<<"   ";
			preOrder(node->left);
			preOrder(node->right);
		}
	}
	//中序遍历
	void midOrder(Node* node){
		if(node!=NULL){
			midOrder(node->left);
			cout<<node->key<<"   ";
			midOrder(node->right);
		}
	}
	//后续遍历
	void endOrder(Node *node){
		if(node!=NULL){
			endOrder(node->left);
			endOrder(node->right);
			cout<<node->key<<"   ";
		}
	}
	//层序遍历
	void layerOrder(){
		queue<Node*> q;
		q.push(root);
		while(!q.empty()){
			Node *node=q.front();
			q.pop();
			cout<<node->key<<"   ";
			if(node->left!=NULL)
				q.push(node->left);
			if(node->right!=NULL)
				q.push(node->right);
		}
	}


};
int main(){
	//int arr[]={1,2,3,4,5,6,7};
	//cout<<"我查的元素是7,位置是:"<<binarySearch(arr,7,7)<<endl;
	BST<int,char> bst=BST<int,char>();
	bst.insert(4,'a');
	bst.insert(2,'b');
	bst.insert(6,'c');
	bst.insert(1,'d');
	bst.insert(3,'e');
	bst.insert(5,'f');
	bst.insert(7,'g');
	bst.preOrder(bst.getRoot());
	cout<<endl;
	bst.midOrder(bst.getRoot());
	cout<<endl;
	bst.endOrder(bst.getRoot());
	cout<<endl;
	bst.layerOrder();
	cout<<endl;
	bst.search(5);
	cout<<endl;
	return 0;
}
