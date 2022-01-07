// This is called an extension function
fun MutableList<Int>.radixSort() {
    val base = 10

    var done = false
    var digits = 1

    while (!done) {
        done = true
        /**1. You instantiate the buckets using a two-dimensional list. You create as many
buckets as the base you’re using, which is ten in this case.
2. You place each number in the correct bucket.
3. You update digits to the next digit you want to inspect and update the list using
the contents of buckets . flatten() flattens the two-dimensional list to a one-
dimensional list, as if you’re emptying the buckets into the list.*/
        val buckets = MutableList<MutableList<Int>>(base){ mutableListOf() }
        this.forEach { number ->
            var remainingPart = number / digits
            val digit = remainingPart % base
            buckets[digit].add(number)
            println(buckets)
            if (remainingPart > 0) done =false
        }
        digits *= base

        this.clear()
        this.addAll(buckets.flatten())
    }
}