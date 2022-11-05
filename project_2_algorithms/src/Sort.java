
public class Sort {
	public static final int[] test = { 4, 77, 98, 30, 20, 50, 77, 22, 49, 2 };

	public static void bubbleSortNR(int[] arr) {
		int i, j;
		int size = arr.length;
		int temp;
		for (i = 0; i < size - 1; i++) {
			// as the largest are swapped to the end of array by the end of each for loop
			// clearly the end are gradually sorted, i+1 items
			// we only need to look at array from its start to size - (i+1)
			for (j = 0; j < size - (i + 1); j++) {
				if (arr[j] > arr[j + 1]) {
					temp = arr[j + 1];
					arr[j + 1] = arr[j];
					arr[j] = temp;
					
				}
			}
		}

	}

	public static int[] bubbleSort(int[] arr, int end) {
		// base case
		if (end == 0) {
			return arr;
		}
		for (int j = 0; j < end; j++) {
			if (arr[j] > arr[j + 1]) {
				int temp = arr[j + 1];
				arr[j + 1] = arr[j];
				arr[j] = temp;
			}
		}
		// recursive call
		return bubbleSort(arr, end - 1);

	}

	public static void selectSort(int[] arr) {
		int size = arr.length;
		int temp;
		// for arr.length many times...
		for (int i = 0; i < size-1; i++) {
			int min = i;
			// search the minimum value and keep its index
			for (int j = i; j < size; j++) {
				if (arr[min] > arr[j]) {
					min = j;
				}
			}
			// swap the minimum from its place with the element in the beginning,
			// index i, increment by 1 each time
			// so that we only need to search the (i+1)th smallest from array i+1~length-1
			temp = arr[i];
			arr[i] = arr[min];
			arr[min] = temp;
		}
	}

	public static void insertionSort(int[] arr) {
		int size = arr.length;
		// arr[0] ~ arr[i-1] were sorted
		for (int i = 1; i < size; i++) {
			int temp = arr[i]; // item to-be-inserted
			int j = i;
			// comparison in the sorted part:
			while (j > 0 && temp < arr[j - 1]) {
				arr[j] = arr[j - 1];// shift right by one, temp moving to the front
				// space for shifting is provided by the moving of arr[i]
				j--;
			}
			arr[j] = temp;
		}
	}

	public static void merge(int[] arr, int start1, int end1, 
							 int start2, int end2, int[] temp) {

		int tempPos = start1;
		int count = end2 - start1 + 1;
		while (start1 <= end1 && start2 <= end2) {
			if (arr[start1] <= arr[start2]) {
				temp[tempPos++] = arr[start1++];
			} else {
				temp[tempPos++] = arr[start2++];
			}
		}

		while (start1 <= end1) {
			temp[tempPos++] = arr[start1++];
		}
		while (start2 <= end2) {
			temp[tempPos++] = arr[start2++];
		}
		for (int i = 0; i < count; i++) {
			arr[end2] = temp[end2];
			end2--;
		}
	}

	public static void mergeSort(int[] arr,int[] out, int left, int right) {
		//base case
		if (left >= right) {
			return;
		}

		int[] temp = new int[right + 1];
		int mid = (left + right) / 2;

		mergeSort(arr, temp, left, mid);
		mergeSort(arr, temp, mid + 1, right);

		merge(arr, left, mid, mid + 1, right, temp);
	}
}
