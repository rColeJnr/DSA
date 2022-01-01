//  CHALLENGES
// print reversed
fun <T: Any> LinkedList<T>.printInReverse() {
    this.nodeAt(0)?.printInReverse()
}
/*
In the while declaration, you bind the next node to fast. If there’s a next node, you
update fast to the next node of fast, effectively stepping down the list twice. slow
is updated only once. This is also known as the runner technique.*/
fun <T:Any> LinkedList<T>.getMiddle(): Node<T>? {
    var slow = this.nodeAt(0)
    var fast = this.nodeAt(0)

    while (fast != null){
        fast = fast.next
        if (fast != null){
            fast = fast.next
            slow = slow?.next
        }
    }
    return slow
}

/*You can easily reverse a list by using a recursive function that goes to the end of the
list and then starts copying the nodes when it returns, into a new linked list. Here’s
how this function would look like:*/

fun <T: Any> addInReverse(list: LinkedList<T>, node: Node<T>) {
    val next = node.next
    if (next != null) addInReverse(list, next)
    list.append(node.value)
}

/*O(n) time complexity, short and sweet! The only drawback is that you need a new
list, which means that the space complexity is also O(n).*/

fun <T : Any> LinkedList<T>.reversed(): LinkedList<T> {
    val result = LinkedList<T>()
    val head = this.nodeAt(0)
    if (head != null) addInReverse(result, head)
    return result
}
/*The solution to this problem is to continuously pluck nodes from the two sorted lists
and add them to a new list. Since the two lists are sorted, you can compare the next
node of both lists to see which one should be the next one to add to the new list.*/

/*This algorithm has a time complexity of O(m + n), where m is the # of nodes in the
first list, and n is the # of nodes in the second list*/
fun <T : Comparable<T>> LinkedList<T>.mergeSorted(
    otherList: LinkedList<T>
): LinkedList<T> {
    if (this.isEmpty()) return otherList
    if (otherList.isEmpty()) return this

    val result = LinkedList<T>()

    var left = nodeAt(0)
    var right = otherList.nodeAt(0)

    while (left != null && right != null) {
        if (left.value < right.value) left = append(result, left)
        else right = append(result, right)
    }

    while (left != null) {
        left = append(result, left)
    }
    while (right != null) {
        right = append(result, right)
    }

    return result

}

private fun <T: Comparable<T>> append(
    result: LinkedList<T>, node: Node<T>
): Node<T>? {
    result.append(node.value)
    return node.next
}
