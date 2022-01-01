class LinkedList<T
: Any>: Iterable<T>, Collection<T>, MutableIterable<T>, MutableCollection<T> {

    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    override var size = 0
        private set

    override fun isEmpty(): Boolean = size == 0

    override fun toString(): String {
        if (isEmpty()) {
            return "Empty list"
        } else {
            return head.toString()
        }
    }

    // ITERATOR
    override fun iterator(): LinkedListIterator<T> {
        return LinkedListIterator(this)
    }

    // COLLECTION METHODS
    override fun contains(element: T): Boolean {
        for (item in this) if (item == element) return true
        return false
    }

    // it’s O(n^2)
    override fun containsAll(elements: Collection<T>): Boolean {
        for (item in elements) if (!contains(item)) return false
        return true
    }

    /*
    * Adding a value at the front of the list is known as a push operation. This is also
    * known as head-first insertion
    * */

    fun push(value: T) :LinkedList<T> = apply{
        // create a new node, set the new value, and point the value to the previous node
        head = Node(value = value, next = head)
        if (tail == null) tail = head // if list is empty, new node head and also tail
        size++
    }

    /*The next operation you’ll look at is append. This adds a value at the end of the list,
    which is known as tail-end insertion.
*/

    fun append(value: T): LinkedList<T> = apply {
        // 1
        if( isEmpty() ) {push(value); return@apply}
        // 2 to the end of the list, ask for next position (null at moment) and set the newNode
        val newNode = Node(value = value)
        tail!!.next = newNode
        // 3 Since this is tail-end insertion, yr new node is also the tail of the list
        tail = newNode
        size++
    }

    /*
  This operation inserts a
value at a particular place in the list and requires two steps:
1. Finding a particular node in the list.
2. Inserting the new node after that node.
*/

    // Find the required node
    fun nodeAt (index: Int) : Node<T>? {
        // 1
        var currentNode = head
        var currentIndex = 0

        // 2
        while (currentNode != null && currentIndex < index){
            currentNode = currentNode.next
            currentIndex++
        }

        return currentNode
    }

    // Insert the node
    fun insert(value: T, afterNode: Node<T>): Node<T> {
        // 1 If adding to the index == end of list
        if (tail == afterNode) {
            append(value)
            return tail!!
        }
        // 2
        val newNode = Node(value = value, next = afterNode.next)
        // 3
        afterNode.next = newNode
        size++
        return newNode
    }


    /*
    * Removing a value at the front of the list is often referred to as pop. This operation is
      almost as simple as push()
      *  Returns the value that was removed from the list
    * */

    fun pop(): T? {
        if (isEmpty()) return null
        val result = head?.value

        head = head?.next
        size--
        if (isEmpty()){
            tail = null
        }
        return result
    }

    fun removeLast(): T? {
        //1
        val head = head ?: return null

        //2
        if (head.next == null) return pop()
        //3
        size--
        //4
        var prev = head
        var current = head

        var next = current.next
        while (next != null) {
            prev = current
            current = next
            next = current.next
        }
        //5
        prev.next = null
        tail = prev
        return current.value
    }

    /*The final remove operation is removing a node at a particular point in the list. This is
achieved much like insert(). You’ll first find the node immediately before the node
you wish to remove and then unlink it.*/

    fun removeAfter(node: Node<T>): T? {
        val result = node.next?.value

        if (node.next == tail){
            tail = node
        }

        if (node.next != null) size--

        node.next = node.next?.next
        return result
    }

    // MutableCollecction
    override fun add(element: T): Boolean {
        append(element)
        return true
    }

    override fun addAll(elements: Collection<T>): Boolean {
        for (element in elements) append(element)
        return true
    }

    override fun clear() {
        head = null
        tail = null
        size = 0
    }

    override fun remove(element: T): Boolean {
        // 1
        val iterator = iterator()
        // 2
        while (iterator.hasNext()){
            val item = iterator.next()
            // 3
            if (item == element){
                iterator.remove()
                return true
            }
        }
        return false
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        var result = false
        for (item in elements) result = remove(item) || result
        return result
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        var result = false
        val iterator = this.iterator()
        while (iterator.hasNext()){
            val item = iterator.next()
            if (!elements.contains(item)){
                iterator.remove()
                result = true
            }
        }
        return result
    }
}