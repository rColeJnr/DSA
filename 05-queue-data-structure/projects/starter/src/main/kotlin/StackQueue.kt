import doublestack.StackImpl

class StackQueue<T: Any> : Queue<T> {
    private val leftStack = StackImpl<T>()
    private val rightStack = StackImpl<T>()

    /*
    * Whenever you enqueue an element, it goes in the right stac
    * when youneed to dequeue an element, you reverse the right stack and place it in the left stack
    * so that you can retriee the element using FIFO.
    * */

    override fun enqueue(element: T): Boolean {
        rightStack.push(element)
        return true
    }

    override fun dequeue(): T? {
        if (leftStack.isEmpty) transferElements()
        return leftStack.pop()
    }

    override val isEmpty: Boolean
        get() = leftStack.isEmpty && rightStack.isEmpty

    override val count: Int
        get() = leftStack.count + rightStack.count

    override fun peek(): T? {
        if (leftStack.isEmpty) transferElements()
        return leftStack.peek()
    }

    private fun transferElements() {
        var nextElement = rightStack.pop()
        while (nextElement != null) {
            leftStack.push(nextElement)
            nextElement = rightStack.pop()
        }
    }

    override fun toString(): String {
        return "Left stack: \n$leftStack \n Right stack:\n$rightStack"
    }
}