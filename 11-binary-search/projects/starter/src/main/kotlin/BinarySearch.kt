fun <T: Comparable<T>> ArrayList<T>.binarySearch(
    value: T, range: IntRange = indices
// the indices property
//of ArrayList is used, which covers all valid indexes of the collection
): Int? {
    if (range.first > range.last) return null

    val size = range.last - range.first + 1
    val middle = range.first + size/ 2

/*1. First, you check if the range contains at least one element. If it doesn’t, the
search has failed and you return null.
2. Now that you’re sure you have elements in the range, you find the middle index
in the range.
3. You then compare the value at this index with the value you’re searching for. If
they match, you return the middle index.
4. If not, you recursively search either the left or right half of the collection,
excluding the middle item in both cases.*/
    return when {
        this[middle] == value -> middle
        this[middle] > value -> binarySearch(value, range.first until middle)
        else -> binarySearch(value, (middle + 1)..range.last)
    }
}

fun <T : Comparable<T>> ArrayList<T>.findIndices(
    value: T
): IntRange? {
    val startIndex = startIndex(value, indices) ?: return null
    val endIndex = endIndex(value, indices) ?: return null

    return startIndex until endIndex
}

private fun <T : Comparable<T>> ArrayList<T>.startIndex(
    value: T,
    range: IntRange
): Int? {
    // 1
    val middleIndex = range.start + (range.last - range.start + 1) / 2

    // 2
    if (middleIndex == 0 || middleIndex == size - 1) {
        return if (this[middleIndex] == value) {
            middleIndex
        } else {
            null
        }
    }

    // 3
    return if (this[middleIndex] == value) {
        if (this[middleIndex - 1] != value) {
            middleIndex
        } else {
            startIndex(value, range.start until middleIndex)
        }
    } else if (value < this[middleIndex]) {
        startIndex(value, range.start until middleIndex)
    } else {
        startIndex(value, (middleIndex + 1)..range.last)
    }
}

private fun <T : Comparable<T>> ArrayList<T>.endIndex(
    value: T,
    range: IntRange
): Int? {
    val middleIndex = range.start + (range.last - range.start + 1) / 2

    if (middleIndex == 0 || middleIndex == size - 1) {
        return if (this[middleIndex] == value) {
            middleIndex + 1
        } else {
            null
        }
    }

    return if (this[middleIndex] == value) {
        if (this[middleIndex + 1] != value) {
            middleIndex + 1
        } else {
            endIndex(value, (middleIndex + 1)..range.last)
        }
    } else if (value < this[middleIndex]) {
        endIndex(value, range.start until middleIndex)
    } else {
        endIndex(value, (middleIndex + 1)..range.last)
    }
}
