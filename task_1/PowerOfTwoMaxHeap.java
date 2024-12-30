import java.util.ArrayList;
import java.util.List;

public class PowerOfTwoMaxHeap {

    private final int branchingFactor; // Number of children per parent (2^powerOfTwoChildren)
    private final List<Integer> heap; 

    // Constructor
    public PowerOfTwoMaxHeap(int powerOfTwoChildren) {
        if (powerOfTwoChildren < 0) {
            throw new IllegalArgumentException("Power of two children must be non-negative.");
        }
        this.branchingFactor = (int) Math.pow(2, powerOfTwoChildren);
        this.heap = new ArrayList<>();
    }

    // Insert a value into the heap
    public void insert(int value) {
        heap.add(value); // Add value at the end of the list
        bubbleUp(heap.size() - 1); // Restore heap property
    }

    // Pop the maximum value from the heap
    public int popMax() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap is empty.");
        }
        int maxValue = heap.get(0); // Max value is at the root
        int lastValue = heap.remove(heap.size() - 1); // Remove the last element
        if (!heap.isEmpty()) {
            heap.set(0, lastValue); // Replace root with the last element
            bubbleDown(0); // Restore heap property
        }
        return maxValue;
    }

    // Bubble up to restore heap property after insertion
    private void bubbleUp(int index) {
        int parentIndex = getParentIndex(index);
        while (index > 0 && heap.get(index) > heap.get(parentIndex)) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = getParentIndex(index);
        }
    }

    // Bubble down to restore heap property after removing the root
    private void bubbleDown(int index) {
        while (true) {
            int largest = index;
            for (int i = 1; i <= branchingFactor; i++) {
                int childIndex = getChildIndex(index, i);
                if (childIndex < heap.size() && heap.get(childIndex) > heap.get(largest)) {
                    largest = childIndex;
                }
            }
            if (largest != index) {
                swap(index, largest);
                index = largest;
            } else {
                break;
            }
        }
    }

    // Get the index of the parent of a given node
    private int getParentIndex(int childIndex) {
        return (childIndex - 1) / branchingFactor;
    }

    // Get the index of the nth child of a given node
    private int getChildIndex(int parentIndex, int childNumber) {
    return branchingFactor * parentIndex + (childNumber - 1) + 1;
}


    // Swap two elements in the heap
    private void swap(int index1, int index2) {
        int temp = heap.get(index1);
        heap.set(index1, heap.get(index2));
        heap.set(index2, temp);
    }

    // Main method for testing
    public static void main(String[] args) {
        // Test with branching factor 2 (binary heap)
        PowerOfTwoMaxHeap binaryHeap = new PowerOfTwoMaxHeap(1);
        binaryHeap.insert(10);
        binaryHeap.insert(20);
        binaryHeap.insert(15);
        System.out.println(binaryHeap.popMax()); // 20
        System.out.println(binaryHeap.popMax()); // 15
        System.out.println(binaryHeap.popMax()); // 10

        // Test with branching factor 4 (quaternary heap)
        PowerOfTwoMaxHeap quaternaryHeap = new PowerOfTwoMaxHeap(2);
        quaternaryHeap.insert(5);
        quaternaryHeap.insert(30);
        quaternaryHeap.insert(25);
        quaternaryHeap.insert(40);
        quaternaryHeap.insert(35);
        System.out.println(quaternaryHeap.popMax()); // 40
        System.out.println(quaternaryHeap.popMax()); // 35
    }
}
