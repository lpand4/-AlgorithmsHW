package org.example;

public class Main {
    public static void main(String[] args) {
        int[] testArray = createArray(10);
        int[] copyTestArray = testArray.clone();
        heapSort(testArray);
        System.out.println("Unsorted:");
        printArray(copyTestArray);
        System.out.println("\nSorted:");
        printArray(testArray);
    }
    public static int[] createArray(int length){
        int[] array = new int[length];
        for (int i = 0; i < array.length; i++) {
            array[i] =(int) (Math.random()*100);
        }
        return array;
    }

    private static void heapify(int[] array, int arraySize, int index){
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        int largest = index;

        if(left < arraySize && array[left] > array[largest]){
            largest = left;
        }
        if(right < arraySize && array[right] > array[largest]){
            largest = right;
        }
        if(largest != index){
            int temp = array[index];
            array[index] = array[largest];
            array[largest] = temp;

            heapify(array, arraySize, largest);
        }
    }

    public static void heapSort(int[] array){
        for (int i = array.length/2 - 1; i >=0 ; i--) {
            heapify(array, array.length, i);
        }
        for (int i = array.length - 1; i >=0 ; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            heapify(array,i,0);
        }
    }

    public static void printArray(int arr[])
    {
        int n = arr.length;
        for (int i=0; i<n; ++i)
            System.out.print(arr[i]+" ");
        System.out.println();
    }
}