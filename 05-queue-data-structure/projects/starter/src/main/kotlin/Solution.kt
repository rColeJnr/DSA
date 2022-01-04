import doublestack.StackImpl

// Reverse Data
fun <T: Any> Queue<T>.reverse() {
    val aux = StackImpl<T>()

    var next = this.dequeue()
    while (next != null){
        aux.push(next)
        next = this.dequeue()
    }

    next = aux.pop()
    while (next != null) {
        this.enqueue(next)
        next = aux.pop()
    }
}