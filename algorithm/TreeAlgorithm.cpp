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
		Node *father;
		Node(Key key,Value value){
			this->key=key;
			this->value=value;
			this->left=NULL;
			this->right=NULL;
			this->father=NULL;
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
		count++;
		if(root==NULL){
			root=new Node(key,value);
			return;
		}
		Node* start=root;	
		Node* end=new Node(key,value);
		while(start->left!=NULL||start->right!=NULL){
			//如果比节点小 看看有没有左孩子 如果没有左孩子就加在左孩子上
			//如果比节点大 看看与没有右孩子 如果没有右孩子就加在右孩子上
			//如果有左（右）孩子就迭代下去
			//start必须保持是end的父节点
			if(key<start->key){
				if(start->left==NULL){
					start->left=end;
					break;
				}else
					start=start->left;
			}else if(key>start->key){
				if(start->right==NULL){
					start->right=end;
					break;
				}else
					start=start->right;
			}else{
				cout<<"此节点已存在"<<endl;
				break;
			}
		}
		if(key<start->key){
			start->left=end;
		}else if(key>start->key){
			start->right=end;
		}
		end->father=start;
	}
	Node* search(Key key){
		if(root==NULL){
			cout<<"现在这是个空树"<<endl;
			return NULL;
		}
		Node* start=root;
		//此处只考虑了非叶子节点的情况
		while(start->left!=NULL||start->right!=NULL){
			if(key<start->key){
				if(start->left==NULL){
					cout<<"找不到节点"<<endl;
					return NULL;
				}
				start=start->left;
			}else if(key>start->key){
				if(start->right==NULL){
					cout<<"找不到节点"<<endl;
					return NULL;
				}
				start=start->right;
			}else{
				return start;
			}
		}
		if(key==start->key){
			cout<<"2此节点的value是"<<start->value<<endl;
			cout<<"2此节点的父节点的value是"<<start->
				father->value<<endl;
			return start;
		}else{
			cout<<"找不到节点"<<endl;
			return NULL;
		}

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
			if(node!=NULL)
				cout<<"key:"<<node->key<<"   value:"<<node->value<<"   地址:"<<node<<endl;
			if(node->left!=NULL)
				q.push(node->left);
			if(node->right!=NULL)
				q.push(node->right);
		}
	}
	void remove(Key key){
		//有左孩子没右孩子：左孩子替代被删除节点
		//有右孩子没左孩子：右孩子替代被删除节点
		//既有右孩子也有左孩子：左孩子的最大值或者
		//右孩子的最小值替代被删除节点
		//没有右孩子也没有左孩子：直接删除
		Node* start=search(key);
		if(start==NULL){
			//cout<<"找不到节点"<<endl;
			return;
		}
		if(start->left!=NULL&&start->right==NULL){
			if(start->key<start->father->key)
				start->father->left=start->left;
			else
				start->father->right=start->left;
			//这里如果要释放目标节点所占内存的话
			//应该先找到目标节点的父节点
			//然后存储目标节点的左子树
			//并记录目标节点和父节点的关系（左孩子还是右孩子）
			//然后删除目标节点
			//最后顺着目标节点和父节点的关系（左孩子还是右孩子）
			//把原来存储的左子树插进去
		}else if(start->right!=NULL&&start->left==NULL){
			if(start->key<start->father->key)
				start->father->left=start->right;
			else
				start->father->right=start->right;
			//释放目标节点所占内存方法同上
		}else if(start->left!=NULL&&start->right!=NULL){
			//这里找右孩子的最小值
			Node* minRight=start->right;
			while(minRight->left!=NULL){
				minRight=minRight->left;
			}
			Node* disappear=minRight;//用这个指针删除右孩子最小值
			cout<<"右孩子最小值的key:"<<minRight->key
				<<"    父节点的key:"<<minRight->father->key<<endl;
			//右孩子最小值替代被删除节点的时候
			//右孩子最小值的左孩子是被删除节点的左孩子
			//右孩子最小值的右孩子是被删除节点的右孩子
			//删除最小值之后的结果
			if(start->father!=NULL&&start->key<start->father->key)
				start->father->left=minRight;
			else if(start->father!=NULL&&start->key>start->father->key)
				start->father->right=minRight;
			else 
				root=minRight;
			//在这里删除右孩子的最小值
			if(disappear->key<disappear->father->key){
				disappear->father->left=NULL;
				delete disappear->father->left;
			}else{
				disappear->father->right=NULL;
				delete disappear->father->right;
			}
			minRight->left=start->left;
			minRight->right=start->right;
		}else{
			if(start->key<start->father->key){
				start->father->left=NULL;
			}else if(start->key>start->father->key){
				start->father->right=NULL;
			}
		}
		delete start;
		start=NULL;

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
	bst.remove(7);
	bst.layerOrder();
	cout<<endl;
	return 0;
}
