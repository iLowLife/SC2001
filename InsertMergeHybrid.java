import java.util.Random;

public class HybridInsertMerge {
	public static void main(String[]args){
    	Random rand = new Random();
    	int N = 1000000;
    	int arr[] = new int[N];  
    	for (int i = 0; i<N; i++) {
    		int int_random = rand.nextInt(N); 
    		arr[i] = int_random;
    	}
    	
    	long startTime = System.nanoTime();
    	mergeSort(arr,0,arr.length-1);
    	
    	System.out.println("Hybrid Insert-Merge Sort :");
    	/*for (int i=0; i<arr.length;i++) {
    		System.out.println(arr[i]);
    	} //printing of array 
    	*/
    	
    	long endTime   = System.nanoTime();
    	long totalTime = endTime - startTime;
    	System.out.println(totalTime/1000 + "us (micro second)");
    }
    
    public static void mergeSort(int[] arr, int left, int right) {
    	int mid = (right+left)/2;
    	int S = 64; //Figure out the best S value 
    	if (right-left <=S) { 
    		insertSort(arr,left,right);
    		return;
    	}
    	
    	else if (right-left > 0) {
    		mergeSort(arr, left, mid);
        	mergeSort(arr, mid+1, right);
    	}
    	
    	merge(arr,left,mid,right);
    }
    
    public static void merge(int arr[], int l, int m, int r)
    {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;
 
        /* Create temp arrays */
        int L[] = new int[n1];
        int R[] = new int[n2];
 
        /*Copy data to temp arrays*/
        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];
 
        /* Merge the temp arrays */
 
        // Initial indexes of first and second subarrays
        int i = 0, j = 0;
 
        // Initial index of merged subarray array
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            }
            else {
                arr[k] = R[j];
                j++;
            }
            k++;
        }
 
        /* Copy remaining elements of L[] if any, l */
        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
 
        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }
    
	public static void insertSort(int arr[],int left, int right) {
		for(int i=left;i<right+1;i++) {
			int j = i;
			while(j>left && arr[j-1] > arr[j]) {
				int tmp = arr[j-1];
				arr[j-1] = arr[j];
				arr[j] = tmp;
				j--;
			}
		}
	}
}
