// Strategy Interface
// Defines a common contract for all sorting algorithms
interface SortingStrategy {
    void sortProcess(int[] arr);
}

// Utility class to keep common array operations reusable
class ArrayUtil {

    // Swaps two elements in the array
    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // Prints the array elements
    static void print(int[] arr) {
        for (int x : arr) {
            System.out.print(x + " ");
        }
        System.out.println();
    }
}

// Concrete Strategy 1: Bubble Sort
// Implements the SortingStrategy interface
class BubbleSort implements SortingStrategy {

    @Override
    public void sortProcess(int[] arr) {
        int n = arr.length;

        // Bubble Sort logic
        // Repeatedly swaps adjacent elements if they are in wrong order
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    ArrayUtil.swap(arr, j, j + 1);
                }
            }
        }

        // Printing result after sorting
        ArrayUtil.print(arr);
    }
}

// Concrete Strategy 2: Quick Sort
// Implements the SortingStrategy interface
class QuickSort implements SortingStrategy {

    @Override
    public void sortProcess(int[] arr) {
        // Initial call to recursive quick sort
        quickSort(arr, 0, arr.length - 1);

        // Printing result after sorting
        ArrayUtil.print(arr);
    }

    // Recursive Quick Sort method
    private void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // Partition the array and get pivot index
            int pivotIndex = partition(arr, low, high);

            // Recursively sort elements before and after pivot
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }

    // Partition method using last element as pivot
    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        // Rearrange elements based on pivot
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                ArrayUtil.swap(arr, i, j);
            }
        }

        // Place pivot in its correct position
        ArrayUtil.swap(arr, i + 1, high);
        return i + 1;
    }
}

// Context class
// Maintains a reference to a SortingStrategy
class Sorter {

    private SortingStrategy sortingStrategy;

    // Constructor injection of strategy
    Sorter(SortingStrategy sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
    }

    // Allows changing strategy at runtime
    public void setSortingStrategy(SortingStrategy sortingStrategy) {
        this.sortingStrategy = sortingStrategy;
    }

    // Delegates sorting work to the selected strategy
    public void sortArray(int[] arr) {
        sortingStrategy.sortProcess(arr);
    }
}

// Client code
public class Main {
    public static void main(String[] args) {

        int[] arr = {5, 2, 8, 1, 3};

        // Using Bubble Sort strategy
        Sorter sorter = new Sorter(new BubbleSort());
        sorter.sortArray(arr);

        // Switching strategy at runtime to Quick Sort
        sorter.setSortingStrategy(new QuickSort());
        sorter.sortArray(arr);
    }
}
