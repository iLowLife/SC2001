package Dijkstra;
import java.util.*;

import Dijkstra.g4gexample1.AdjListNode;
//import Lab2.GFGheap.HeapNode;

public class PracticeMode {
	 //final static int V = 8;
	
	static int count = 0;
	//AdjListNode test
	static class AdjListNode { // Like Structure in C
		int vertex, weight;
		AdjListNode(int v, int w)
		{
			vertex = v;
			weight = w;
		}
		int getVertex() { return vertex; }
		int getWeight() { return weight; }
	}
	
    static class HeapNode{
        int vertex;
        int distance;
    }
    
	public static int minDistance(int d[], int S[], int V)	// Array Priority Queue
	{
		// Initialize min value
		int Vertices = V;
		int min = Integer.MAX_VALUE, min_index = -1;

		for (int v = 0; v < Vertices; v++) {
			count++;
			if (S[v] == 0 && d[v] <= min) {
				min = d[v];
				min_index = v;
			}
		}
		return min_index;
	} 
	
    public static int[] dijkstra2(int V, ArrayList<ArrayList<AdjListNode> > graph, int src)
    {
    	int count = 0;
        int[] distance = new int[V];
        for (int i = 0; i < V; i++) {
            distance[i] = Integer.MAX_VALUE;
        }
        distance[src] = 0;
 
        //the default Priority Queue in java in Min-Heap
        PriorityQueue<AdjListNode> pq = new PriorityQueue<>((v1, v2) -> v1.getWeight() - v2.getWeight());
        pq.add(new AdjListNode(src, 0));
 
        while (pq.size() > 0) {
            AdjListNode current = pq.poll();
 
            for (AdjListNode n : graph.get(current.getVertex())) {
                if (distance[current.getVertex()] + n.getWeight() < distance[n.getVertex()]) {
                    distance[n.getVertex()] = n.getWeight() + distance[current.getVertex()];
                    pq.add(new AdjListNode(n.getVertex(),distance[n.getVertex()]));
                }
            }
        }
        // If you want to calculate distance from source to
        // a particular target, you can return
        // distance[target]
        
        
        return distance;
    }
			
    public static int[] dijkstra1(int graph[][], int src, int vertices){
    	int V = vertices;
		//Initialization
		int d[] = new int[V];
		int pi[] = new int[V];
		int S[] = new int [V];
		for(int i=0;i<V;i++) {
			d[i] = Integer.MAX_VALUE;
			pi[i] = -1;
			S[i] = 0;
		}
		
		d[src] = 0;
		
		// Find shortest path for all vertices
		for (int count = 0; count < V - 1; count++) { 
			int u = minDistance(d, S, V); //extract cheapest distance in d[]

			// Mark the picked vertex as processed
			S[u] = 1;
			
			// Do D first, then Pi
			for (int v = 0; v < V; v++)
				if (S[v] == 0 && graph[u][v] != 0
					&& d[u] != Integer.MAX_VALUE
					&& d[u] + graph[u][v] < d[v])
					d[v] = d[u] + graph[u][v];
		}
		return d;
	}
			
	public static void main(String[] args) {

		int N = 50;
		int source = 0;
		

		//algo1
		System.out.println("Dijkstra using Matrix with Array as PQ");
		for (int a = 1;a<N;a++) {
			int[][] matrix = new int[a][a];
			matrix = GenerateGraph(a);
			
			long startTime1 = System.nanoTime();
			int[] distance1 = dijkstra1(matrix, source, a);
	    	long endTime1 = System.nanoTime();
	    	System.out.println(endTime1 - startTime1);
			
			// Printing the Output
//			System.out.println("Vertex " + " Distance from Source (Matrix)");
//			for (int i = 0; i < a; i++) {
//	            System.out.println(i + "             " + distance1[i]);
//	        }
//			for(int i=0; i<a;i++) {
//				for(int j=0; j<a;j++) {
//					System.out.print(matrix[i][j] + " ");
//				}
//				System.out.println("");
//			}
		}

		
		
		//Algo2

		System.out.println("Dijkstra using Adj List with Min Heap as PQ");
		for (int a = 1;a<N;a++) {
			int[][] matrix = new int[a][a];
			matrix = GenerateGraph(a);
			
//			for(int i=0; i<a;i++) {
//				for(int j=0; j<a;j++) {
//					System.out.print(matrix[i][j] + " ");
//				}
//				System.out.println("");
//			}
		
	    	ArrayList<ArrayList<AdjListNode>> adjlist = matrix2list(matrix);
//			for (int i = 0; i < a; i++) {
//				System.out.print(i + "-> ");
//				for(int j = 0; j < adjlist.get(i).size(); j++){
//					System.out.print(adjlist.get(i).get(j).getVertex() + "(" + adjlist.get(i).get(j).getWeight() +") ");
//				}
//				System.out.println("");
//			}

			long startTime2 = System.nanoTime();
			int[] distance2 = dijkstra2(a, adjlist, source);
			long endTime2 = System.nanoTime();
			System.out.println(endTime2 - startTime2);
			
			// Printing the Output
//			System.out.println("Vertex " + " Distance from Source (AdjList)");
//			for (int i = 0; i < a; i++) {
//	            System.out.println(i + "             " + distance2[i]);
//	        }
		}
	}
	
	
	 //Generate random graph in matrix
	static int[][] GenerateGraph(int V) {
		Random r = new Random();
		r.setSeed(30);
		int[][] graph = new int[V][V];
		for(int i=0; i<V;i++) {
			for(int j=0; j<V;j++) {
				if(i==j)
					graph[i][j]=0;	//self to self is 0 distance
				else 
					graph[i][j] = r.nextInt(100);
			}
		}
		return graph;
	}
	
	 //Functions to create, Convert Matrix to Adj List
	static ArrayList<ArrayList<AdjListNode>> matrix2list(int matrix[][]) {
		int v = matrix[0].length; //get length of 1st row
		ArrayList< ArrayList<AdjListNode> > graph = new ArrayList<>(); // Like a more dynamic 2d array
		for (int i = 0; i < v; i++) {
			graph.add(new ArrayList<>()); //initiate empty array list
		}
		for(int i=0; i<v;i++) {
			for(int j=0; j<v;j++) {
				if (matrix[i][j] != 0)
					graph.get(i).add(new AdjListNode(j, matrix[i][j]));	//Row , Column, Weight
			}
		}
		return graph;
	}
	
    static class MinHeap{
        int capacity;
        int currentSize;
        HeapNode[] mH;
        int [] indexes; //will be used to decrease the distance


        public MinHeap(int capacity) {
            this.capacity = capacity;
            mH = new HeapNode[capacity + 1];
            indexes = new int[capacity];
            mH[0] = new HeapNode();
            mH[0].distance = Integer.MIN_VALUE;
            mH[0].vertex=-1;
            currentSize = 0;
        }

        public void display() {
            for (int i = 0; i <=currentSize; i++) {
                System.out.println(" " + mH[i].vertex + "   distance   " + mH[i].distance);
            }
            System.out.println("________________________");
        }

        public void insert(HeapNode x) {
            currentSize++;
            int idx = currentSize;
            mH[idx] = x;
            indexes[x.vertex] = idx;
            bubbleUp(idx);
        }

        public void bubbleUp(int pos) {
            int parentIdx = pos/2;
            int currentIdx = pos;
            while (currentIdx > 0 && mH[parentIdx].distance > mH[currentIdx].distance) {
                HeapNode currentNode = mH[currentIdx];
                HeapNode parentNode = mH[parentIdx];

                //swap the positions
                indexes[currentNode.vertex] = parentIdx;
                indexes[parentNode.vertex] = currentIdx;
                swap(currentIdx,parentIdx);
                currentIdx = parentIdx;
                parentIdx = parentIdx/2;
            }
        }

        public HeapNode extractMin() {
            HeapNode min = mH[1];
            HeapNode lastNode = mH[currentSize];
//            update the indexes[] and move the last node to the top
            indexes[lastNode.vertex] = 1;
            mH[1] = lastNode;
            mH[currentSize] = null;
            sinkDown(1);
            currentSize--;
            return min;
        }

        public void sinkDown(int k) {
            int smallest = k;
            int leftChildIdx = 2 * k;
            int rightChildIdx = 2 * k+1;
            count++;
            if (leftChildIdx < heapSize() && mH[smallest].distance > mH[leftChildIdx].distance) {
                smallest = leftChildIdx;
            }
            if (rightChildIdx < heapSize() && mH[smallest].distance > mH[rightChildIdx].distance) {
                smallest = rightChildIdx;
            }
           
            if (smallest != k) {

                HeapNode smallestNode = mH[smallest];
                HeapNode kNode = mH[k];

                //swap the positions
                indexes[smallestNode.vertex] = k;
                indexes[kNode.vertex] = smallest;
                swap(k, smallest);
                sinkDown(smallest);
            }
        }

        public void swap(int a, int b) {
            HeapNode temp = mH[a];
            mH[a] = mH[b];
            mH[b] = temp;
        }

        public boolean isEmpty() {
            return currentSize == 0;
        }

        public int heapSize(){
            return currentSize;
        }
    }
}
