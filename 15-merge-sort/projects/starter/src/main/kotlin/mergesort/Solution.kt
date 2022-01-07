package mergesort

fun <T: Comparable<T>> mergeIterables(
    first: Iterable<T>,
    second: Iterable<T>
): Iterable<T> {

    val result = mutableListOf<T>()
    val firstIterator = first.iterator()
    val secondIterator = second.iterator()

    if (!firstIterator.hasNext()) return second
    if (!secondIterator.hasNext()) return first

    var firstEl = firstIterator.nextOrNull()
    var secondEl = secondIterator.nextOrNull()

    while (firstEl != null && secondEl != null){
        when {
            firstEl < secondEl -> {
                result.add(firstEl)
                firstEl = firstIterator.nextOrNull()
            }
            secondEl < firstEl -> {
                result.add(secondEl)
                secondEl = secondIterator.nextOrNull()
            }
            else -> {
                result.add(firstEl); result.add(secondEl)
                firstEl = firstIterator.nextOrNull()
                secondEl = secondIterator.nextOrNull()
            }
        }
    }

    while (firstEl != null){
        result.add(firstEl)
        firstEl = firstIterator.nextOrNull()
    }
    while (secondEl != null){
        result.add(secondEl)
        secondEl = secondIterator.nextOrNull()
    }

    return result
}

private fun <T> Iterator<T>.nextOrNull(): T? {
    return if (this.hasNext()) this.next() else null
}