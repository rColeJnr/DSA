/**Insertion sort is a more useful algorithm. Like bubble sort and selection sort,
insertion sort has an average time complexity of O(n²), but the performance of
insertion sort can vary. The more the data is already sorted, the less work it needs to
do. Insertion sort has a best time complexity of O(n) if the data is already sorted.*/
/*1. Insertion sort requires you to iterate from left to right, once. This loop does that.
2. Here, you run backward from the current index so you can shift left as needed.
3. Keep shifting the element left as long as necessary. As soon as the element is in
position, break the inner loop and start with the next element.*/
fun <T: Comparable<T>> MutableList<T>.insertionSort() {
    if (this.size < 2) return

    for (current in 1 until this.size){
        for (shifting in (1..current).reversed()){
            if (this[shifting] < this[shifting - 1]){
                this.swapAt(shifting, shifting - 1)
            } else break
        }
    }
    println(this)
}