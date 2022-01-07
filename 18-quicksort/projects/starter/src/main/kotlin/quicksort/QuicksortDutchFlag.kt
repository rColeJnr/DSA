package quicksort

import swapAt

/*1. Whenever you encounter an element that is less than the pivot, move it to index
smaller . This means that all elements that come before this index are less than
the pivot.
2. Index equal points to the next element to compare. Elements that are equal to
the pivot are skipped, which means that all elements between smaller and equal
are equal to the pivot.
3. Whenever you encounter an element that is greater than the pivot, move it to
index larger . This means that all elements that come after this index are greater
than the pivot.
4. The main loop compares elements and swaps them if needed. This continues
until index equal moves past index larger , meaning all elements have been
moved to their correct partition.
5. The algorithm returns indices smaller and larger . These point to the first and
last elements of the middle partition.*/
fun <T: Comparable<T>> MutableList<T>.partitionDutchFlag(
    low: Int,
    high: Int,
    pivotIndex: Int
): Pair<Int, Int> {
    val pivot = this[pivotIndex]
    var smaller = low
    var equal = low
    var larger = high
    while (equal <= larger) {
        if (this[equal] < pivot){
            this.swapAt(smaller, equal)
            smaller += 1
            equal += 1
        } else if (this[equal] == pivot) equal += 1
        else {
            this.swapAt(equal, larger)
            larger -= 1
        }
    }
    return Pair(smaller,larger)
}

fun <T: Comparable<T>> MutableList<T>.quicksortDutchFlag(low: Int, high: Int){
    val middle = partitionDutchFlag(low, high, high)
    this.quicksortDutchFlag(low, middle.first - 1)
    this.quicksortDutchFlag(middle.second, high)
}