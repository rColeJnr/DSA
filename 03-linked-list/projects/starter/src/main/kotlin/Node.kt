
// THIS IS A DATA CLASS
data class Node<T : Any>(
    var value: T,
    var next: Node<T>? = null
){
    override fun toString(): String {
        return if (next != null) {
            "$value -> ${next.toString()}"
        } else {
            "$value"
        }
    }
}
/*
* Note: Using T : Any to set an upper bound for the type parameter ensures
* that T will always be a non-nullable type.
* */

/*    this function calls itself on the next node. The terminating
    condition is somewhat hidden in the null-safety operator. If the value of next is
    null, the function stops because there’s no next node on which to call
    printInReverse().*/
fun <T: Any> Node<T>.printInReverse() {
    this.next?.printInReverse()
    if (this.next != null) print(" <- ")
    print(this.value.toString())
}