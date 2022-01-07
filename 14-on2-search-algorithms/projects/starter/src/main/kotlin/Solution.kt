fun <T: Comparable<T>> MutableList<T>.rightAlign(element: T) {
    if (this.size < 2) return

    var searchIndex = this.size - 2
    while (searchIndex >= 0){
        if (this[searchIndex] == element){
            var moveIndex = searchIndex
            while (moveIndex < this.size - 1 && this[moveIndex + 1]
             != element){
                swapAt(moveIndex, moveIndex + 1)
            }
        }
        searchIndex--
    }
}

/*1. If there are less than two elements in the list, there’s nothing to do.
2. You leave the last element alone and start from the previous one. Then, you go to
the left (decreasing the index), until you reach the beginning of the list when the
searchIndex is 0.
3. You’re looking for elements that are equal to the one in the function parameter.
4. Whenever you find one, you start shifting it to the right until you reach another
element equal to it or the end of the list. Remember, you already implemented
swapAt() ; don’t forget to increment moveIndex .
5. After you’re done with that element, move searchIndex to the left again by
decrementing it.
The tricky part here is to understand what sort of capabilities you need. Since you
need to make changes to the underlying storage, this function is only available to
MutableList types.
To complete this algorithm efficiently, you need backward index traversal, which is
why you can’t use any generic MutableCollection .
Finally, you also need the elements to be Comparable to target the appropriate
values.
The time complexity of this solution is O(n).*/

/**1. You first sort the list.
2. Start going through it from right to left since you know that the biggest elements
are on the right, neatly sorted.
3. The first one that’s repeated is your solution.
In the end, if you’ve gone through all of the elements and none of them are repeated,
you can return null and call it a day.
The time complexity of this solution is O(n²) because you’ve used sorting.*/
fun <T: Comparable<T>> MutableList<T>.biggestDuplicate(): T? {
    this.selectionSort()
    for (i in this.lastIndex downTo 1) {
        if (this[i] == this[i-1]) return this[i]
    }
    return null
}

// Solution 3
fun <T: Comparable<T>> MutableList<T>.rev(){
    var left = 0
    var right = this.lastIndex

    while (left< right){
        swapAt(left, right)
        left++
        right--
    }
}