import linkedlist.DoublyLinkedList

class LinkedListQueue<T : Any> : Queue<T> {
    private val list = DoublyLinkedList<T>()

    private var size = 0

    override fun enqueue(element: T): Boolean {
        list.append(element)
        size ++
        return true
    }

    /*Removing from the front of the list is also an O(1) operation. Compared to the
ArrayList implementation, you didn’t have to shift elements one by one. Instead, in
the diagram above, you simply update the next and previous pointers between the
first two nodes of the linked list.*/
    override fun dequeue(): T? {
        val firstNode = list.first ?: return null
        size--
        return list.remove(firstNode)
    }

    override val count: Int
        get() = size

    override fun peek(): T? {
        return list.first?.value
    }

    override fun toString(): String {
        return list.toString()
    }
}