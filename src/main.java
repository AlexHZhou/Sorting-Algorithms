import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.Random;
import java.math.*;

public class main {
	
	static long startTime;
	static long endTime;
	
	static int totalTimeInsertion;
	static int totalTimeShell;
	static int totalTimeMerge;
	static int totalTimeQuick;
	static int totalTimeHeap;
	static int totalTimeGeneric;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scan = new Scanner(System.in);
		
		System.out.println("GENERATING LIST OF NUMBERS");
		System.out.print("How many elements should be in the list? ");
		int length = scan.nextInt();
		
		System.out.print("Run it how many times? ");		
		int iterations = scan.nextInt();
		
		int avgDivisor = iterations;
		while (iterations > 0){
			System.out.print("Pass #" + (avgDivisor - iterations + 1) + "...");
			int[] list = GenerateList(length);
			System.out.print("list created...");
			RunAlgorithms(list);
			
			
			iterations--;
			
			System.out.println("sort completed!");
		}
		printResults(avgDivisor);
	}
	
	public static int[] GenerateList(int N){
		int[] list = new int[N];
		//makes a list with length assigned by user
		int indexValue = 0;
		
		Random r = new Random();
		for (int j = 0; j < list.length; j++) {
			list[j] = r.nextInt(N);
		}
		//list with completely random numbers
		CompletlyRandomizeList(list);
//		System.out.print("Randomized list: ");
//		printList(list);
		
		return list;
	}
	
	public static void RunAlgorithms(int[] list){
		//InsertionSort(list);
		
		ShellSort(list);
		
		Integer[] specialCookieMerge = new Integer[list.length];
		for (int i = 0; i < list.length; i++){
			specialCookieMerge[i] = list[i];
		}
		MergeSort(specialCookieMerge);
		//because one of the methods in mergesort cannot use int, only Integer
		
		QuickSort(list);
		
		HeapSort(list);
		
		GenericSort(list);
	}
	
	public static void printResults(int i){
		System.out.println("RESULTS: ");
		System.out.println("----------------------------------------------------- ");

		System.out.println("Insertion sort averaged " + totalTimeInsertion / i + " milliseconds");
		System.out.println("Shell sort averaged " + totalTimeShell / i + " milliseconds");
		System.out.println("Merge sort averaged " + totalTimeMerge / i + " milliseconds");
		System.out.println("Quick sort averaged " + totalTimeQuick / i + " milliseconds");
		System.out.println("Heap sort averaged " + totalTimeHeap / i + " milliseconds");
		System.out.println("Generic Arrays.sort averaged " + totalTimeGeneric / i + " milliseconds");
		
		System.out.println("----------------------------------------------------- ");
	}
	
	public static void InsertionSort( int[] list){
		startTime = System.currentTimeMillis();
		for (int i = 1; i < list.length; i++){
			int j = i - 1;
			int temp = list[i];
			
			while (j >= 0 && temp < list[j]){
				list[j + 1] = list[j];
				j--;
			}
			list [j + 1] = temp;
		}
		endTime = System.currentTimeMillis();
		int time = (int)(endTime - startTime);
		totalTimeInsertion += time;
		
		//System.out.println("Insertion Sort took " + time + " milliseconds");
		//printList(list);
	}
	
	public static void ShellSort(int[] arr) {
		startTime = System.currentTimeMillis();

        int length = arr.length;
        ArrayList<Integer> gapSizes = determinGapSize(arr.length);
        int gapIndex = gapSizes.size() - 1;
        int pass = 1;

        while(gapSizes.get(gapIndex) > 0) {
        	int gap = (int)gapSizes.get(gapIndex);
        	
            for(int i = gap; i < length; i++) {
                int a = arr[i];
                
                int j;
                for(j = i; j >= gap && arr[j - gap] > a; j -= gap) {
                	//two pairs: one is i, the other is j (which is (gap) spaces left of i).
                	//if j - gap's value is greater than i, the value is shifted over to j
                	//(this part is checked in the for loop).
                	//this for loop is done recursively, so the pairs automatically shift downwards
                	//if it is possible to.
                	
                    arr[j] = arr[j - gap];
                    
                }
                arr[j] = a;
            }
//            System.out.print("After pass " + pass + "  : ");
//            printList(arr);
            pass++;
            if (gap == 1) break;
            else gapIndex--;
        }
        endTime = System.currentTimeMillis();
		int time = (int)(endTime - startTime);
		totalTimeShell+= time;
		
//		System.out.println("Shell Sort took " + time + " milliseconds");
//		printList(arr);
    }	
	public static ArrayList<Integer> determinGapSize(int length){
		int index = 1;
		int gapSize = 0;
		ArrayList<Integer> gaps = new ArrayList<Integer>();
		
		while (true){
			gapSize = (int) (Math.pow(2, index) - 1); //uses gap sizes from function 2^k - 1
			if (gapSize > length) {
				return gaps;
			}
			else {
				gaps.add(gapSize);
				index++;
			}
			//if gap size exceeds the size of the list, return the next previous smaller gap size.
			//else, continue increasing the size of the gap
		}
	}
	
	 //This implementation of MergeSort is NOT written by me
    //it is taken (and slightly modified) from hackerrank.com
	public static void MergeSort(Integer[] arr)
	{
		startTime = System.currentTimeMillis();
		
		int[] tmp = new int[arr.length];
		mergeSort(arr, tmp,  0,  arr.length - 1);
		
		endTime = System.currentTimeMillis();
		int time = (int)(endTime - startTime);
		totalTimeMerge += time;
		
//		System.out.println("Merge Sort took " + time + " milliseconds"); 
//		printList(arr);
	}
	private static void mergeSort(Integer[] arr, int[] tmp, int left, int right)
	{
		if( left < right )
		{
			int center = (left + right) / 2;
			mergeSort(arr, tmp, left, center);
			mergeSort(arr, tmp, center + 1, right);
			merge(arr, tmp, left, center + 1, right);
		}
	}
	private static void merge(Integer[] arr, int[] tmp, int left, int right, int rightEnd )
	    {
	        int leftEnd = right - 1;
	        int k = left;
	        int num = rightEnd - left + 1;

	        while(left <= leftEnd && right <= rightEnd)
	            if(arr[left].compareTo(arr[right]) <= 0)
	                tmp[k++] = arr[left++];
	            else
	                tmp[k++] = arr[right++];

	        while(left <= leftEnd)    // Copy rest of first half
	            tmp[k++] = arr[left++];

	        while(right <= rightEnd)  // Copy rest of right half
	            tmp[k++] = arr[right++];

	        // Copy tmp back
	        for(int i = 0; i < num; i++, rightEnd--)
	            arr[rightEnd] = tmp[rightEnd];
	    }

	
	public static void QuickSort(int[] arr){
		startTime = System.currentTimeMillis();
		
		quickSort(arr, 0, arr.length -1);
		
		endTime = System.currentTimeMillis();
		int time = (int)(endTime - startTime);
		totalTimeQuick += time;
		
//		System.out.println("Quick Sort took " + time + " milliseconds"); 
//		printList(arr);
	}
	public static void quickSort(int[] arr, int left, int right)
    {
		while(left < right )
	    {
	        int q=partition(arr,left,right);
	        if (q-left <= right-(q+1))
	        {
	            quickSort(arr,left,q);
	            left=q+1;
	        }
	        else
	        {
	            quickSort(arr,q+1,right);
	            right=q;
	        }
	    }
		
		
	}
    private static int partition(int[] arr, int left, int right) {

        int x = arr[(left+right)/2];
        int i = left-1 ;
        int j = right+1 ;

        while (true) {
            i++;
            while ( i< right && arr[i] < x)
                i++;
            j--;
            while (j>left && arr[j] > x)
                j--;

            if (i < j)
                swap(arr, i, j);
            else
                return j;
        }
    }
    private static void swap(int[] arr, int i, int j) {
        // TODO Auto-generated method stub
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    //This implementation of HeapSort is NOT written by me
    //it is taken (and slightly modified) from hackerrank.com
    //Sorting in non decreasing order
    public static void HeapSort(int[] arr) {
		startTime = System.currentTimeMillis();
    	
		int N = arr.length;
        //creating a heap
        MaxHeap heap = createHeap(arr, N);

        //Repeating the below steps till the size of the heap is 1.
        while(heap.len > 1) {
            //Replacing largest element with the last item of the heap
            swap(heap, 0, heap.len - 1);
            heap.len--;//Reducing the heap size by 1
            heapify(heap, 0);
        }

        endTime = System.currentTimeMillis();
		int time = (int)(endTime - startTime);
		totalTimeHeap += time;
		
		//System.out.println("Heap Sort took " + time + " milliseconds"); 
        //printList(arr);
    }
    public static MaxHeap createHeap(int[] arr, int N) {
        MaxHeap maxheap = new MaxHeap(N, arr);
        int i = (maxheap.len - 2) / 2;

        while(i >= 0) {
            maxheap = heapify(maxheap, i);
            i--;
        }

        return maxheap; 
    }
    public static MaxHeap heapify(MaxHeap maxheap, int N) {
        int largest = N;
        int left = 2 * N + 1; //index of left child
        int right = 2 * N + 2; //index of right child

        if(left < maxheap.len && maxheap.arr[left] > maxheap.arr[largest])        {
            largest = left;
        }

        if(right < maxheap.len && maxheap.arr[right] > maxheap.arr[largest]) {
            largest = right;
        }

        if(largest != N) {
            swap(maxheap, largest, N);
            heapify(maxheap, largest);
        }

        return maxheap;
    }
    public static void swap(MaxHeap maxheap, int i, int j) {
        int temp;
        temp = maxheap.arr[i];
        maxheap.arr[i] = maxheap.arr[j];
        maxheap.arr[j] = temp;
    }
    static class MaxHeap {
        int len;
        int[] arr;
        MaxHeap(int l, int[] a) {
            len = l;
            arr = a;
        }
    }
    
    public static void GenericSort(int[] arr){
    	startTime = System.currentTimeMillis();
    	
    	Arrays.sort(arr);
    	
    	endTime = System.currentTimeMillis();
  		int time = (int)(endTime - startTime);
  		totalTimeGeneric += time;
    }
	
	public static void printList(int[] list){
//		for (int i : list){
//			System.out.print(i + ", ");
//		}
//		System.out.println();
		System.out.println(Arrays.toString(list));
	}
	public static void printList(Integer[] list){	
		System.out.println(Arrays.toString(list));
	}
	
	public static void CompletlyRandomizeList(int[] list){
		Collections.shuffle(Arrays.asList(list));
		//the Arrays.asList is SUPER important!!!
		//enables int[] to be sorted like an Array
	}

}
