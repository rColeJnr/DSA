package quicksort

import swapAt

/*This function takes three arguments:
• the receiver ( this ) is the list you are partitioning.
• low and high set the range within the list you’ll partition. This range will get
smaller and smaller with every recursion.
The function returns the index of the pivot.*/
fun <T: Comparable<T>> MutableList<T>.partitionLomuto(
    low: Int,
    high: Int
): Int {

    val pivot = this[high]
    var i = low
    for (j in low until high){
        if (this[j] <= pivot){
            this.swapAt(i, j)
            i+= 1
        }
    }
    this.swapAt(i, high)
    return i
}
/**Here, you apply Lomuto’s algorithm to partition the list into two regions. You then
recursively sort these regions. Recursion ends once a region has less than two
elements*/
fun<T : Comparable<T>> MutableList<T>.quicksortLomuto(low: Int, high: Int){
    if (low < high) {
        val pivot = this.partitionLomuto(low, high)
        this.quicksortLomuto(low, pivot -1)
        this.quicksortLomuto(pivot + 1, high)

    }
}