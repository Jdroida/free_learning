#include<iostream>
#include<vector>
#include<cassert>
using namespace std;
//稠密图 邻接矩阵
class DenseGraph{
	private:
		int n,m;//n是点数m是边数
		bool directed;
		vector<vector<bool>> g;
	public:
		DenseGraph(int n,bool directed){
			this->n=n;
			this->m=0;
			this->directed=directed;
			for(int i=0;i<n;i++)
				g.push_back(vector<bool>(n,false));
		}
		~DenseGraph(){
		}
		void addEdge(int v,int w){
			assert(v>=0&&v<n);
			assert(w>=0&&w<n);
			if(hasEdge(v,w))
				return;
			g[v][w]=true;
			if(!directed)
				g[w][v]=true;
			m++;
		}
		bool hasEdge(int v,int w){
			assert(v>=0&&v<n);
			assert(w>=0&&w<n);
			return g[v][w];
		}
};
//稀疏图 邻接表
class SparseGraph{
	private:
		int n,m;
		bool directed;
		vector<vector<int>> g;
	public:
		SparseGraph(int n,bool directed){
			this->n=n;
			this->m=0;
			this->directed=directed;
			for(int i=0;i<n;i++)
				g.push_back(vector<int>());
		}
		~SparseGraph(){
		}
		int V(){
			return n;
		}
		int E(){
			return m;
		}
		void addEdge(int v,int w){
			assert(v>=0&&v<n);
			assert(w>=0&&w<n);
			g[v].push_back(w);
			//处理自环边
			if(v!=w&&!directed)
				g[w].push_back(v);
			m++;
		}
		bool hasEdge(int v,int w){
			assert(v>=0&&v<n);
			assert(w>=0&&w<n);
			for(int i=0;i<g[v].size();i++)
				if(g[v][i]==w)
					return true;
			return false;
		}
};
int main(){
	return 0;
}
