public class InPlaceIntHeapSort {
    public static void heapSort(int[] list) {
        // Turn array into max-heap
        for (int i = 1; i < list.length; i++) {
            // Swap new element with parent until parent > new element
            int currentInd = i;
            while (currentInd > 0) {
                int parentInd = (currentInd - 1) / 2;
                // Swap if the current element is greater than its parent
                if (list[currentInd] > list[parentInd]) {
                    int temp = list[currentInd];
                    list[currentInd] = list[parentInd];
                    list[parentInd] = temp;
                }
                else
                    break; // New element is in correct position

                currentInd = parentInd;
            }
        }

        // Repeatedly move root of heap to the end of the list
        int newLen = list.length;  // Length of heap list after each removal
        for (int i = list.length - 1; i > 0; i--) {
            // Swap root with i
            int temp = list[i];
            list[i] = list[0];
            list[0] = temp;
            newLen--;

            // Swap new root with children until heap is reordered
            int currentInd = 0;
            while (currentInd < i) {
                int child1Ind = (currentInd * 2) + 1;
                int child2Ind = (currentInd * 2) + 2;

                // Find the maximum between two children
                if (child1Ind >= newLen) break; // If element is a leaf
                int maxInd = child1Ind;
                if (child2Ind < newLen) {
                    if (list[child2Ind] > list[maxInd]) {
                        maxInd = child2Ind;
                    }
                }

                // Swap if the current element is less than the maximum
                if (list[currentInd] < list[maxInd]) {
                    temp = list[maxInd];
                    list[maxInd] = list[currentInd];
                    list[currentInd] = temp;
                    currentInd = maxInd;
                }
                else
                    break; // Heap is reordered
            }
        }
    }
}
