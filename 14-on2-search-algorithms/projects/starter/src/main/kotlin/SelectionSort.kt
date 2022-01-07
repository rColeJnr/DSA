/**Selection sort only swaps at
the end of each pass.
During each pass, selection sort finds the lowest unsorted value and swaps it into
place
 */
fun <T: Comparable<T>> MutableList<T>.selectionSort
            (showPasses: Boolean = false){
    if(this.size < 2 ) return

    for (current in 0 until this.lastIndex){
        var lowest = current

        for (other in (current + 1) until this.size){
            if (this[other] < this[lowest]) lowest = other
        }
        if (lowest != current) this.swapAt(lowest, current)
        if (showPasses) println(this)
    }
}
