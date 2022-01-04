public class ArrayListQueue<T: Any> : Queue<T>  {
    private val list = arrayListOf<T>()

//    Regardless of the size of the list, enqueueing an element is an O(1) operation. This is
//    because the list has empty space at the back.
    /*Resizing is an O(n) operation. Resizing requires the list to allocate new memory and
    copy all existing data over to the new list. Since this doesn’t happen very often
    (thanks to doubling the size each time), the complexity still works out to be an
    amortized O(1).*/
    override fun enqueue(element: T): Boolean {
        list.add(element)
        return true
    }

//    Removing an element from the front of the queue is an O(n) operation. To dequeue,
//    you remove the element from the beginning of the list. This is always a linear time
//    operation because it requires all of the remaining elements in the list to be shifted in
//    memory.
    override fun dequeue(): T? {
        return if (isEmpty) null else list.removeAt(0)
    }

    override val count: Int
        get() = list.size

    override fun peek(): T? {
        return list.getOrNull(0)
    }

    override fun toString(): String {
        return list.toString()
    }
}