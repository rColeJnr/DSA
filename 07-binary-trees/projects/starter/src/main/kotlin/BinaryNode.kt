import java.lang.Math.max

typealias Visitor<T> = (T) -> Unit
class BinaryNode<T>(val value: T) {

    var leftChild: BinaryNode<T>? = null
    var rightChild: BinaryNode<T>? = null

    override fun toString(): String {
        return diagram(this)
    }

    private fun diagram(node: BinaryNode<T>?, top: String = "", root: String = "", bottom: String = ""): String {
        return node?.let {
            if (node.leftChild == null && node.rightChild == null){
                "$root${node.value}\n"
            } else {
                diagram(node.rightChild, "$top ", "$top┌──", "$top│ ") +
                        root + "${node.value}\n" + diagram(node.leftChild,
                    "$bottom│ ", "$bottom└──", "$bottom ")
            }
        } ?: "${root}null\n"
    }

    /*• If the current node has a left child, recursively visit this child first.
• Then visit the node itself.
• If the current node has a right child, recursively visit this child*/
    fun traverseInOrder(visit: Visitor<T>){
        leftChild?.traverseInOrder(visit)
        visit(value)
        rightChild?.traverseInOrder(visit)
    }

    // Pre-order traversal
    /*• Visits the current node first.
• Recursively visits the left and right child.*/
    fun traversePreOrder(visit: Visitor<T>){
        visit(value)
        leftChild?.traversePreOrder(visit)
        rightChild?.traversePreOrder(visit)
    }

    // Post-order traversal
    /*Recursively visits the left and right child.
• Only visits the current node after the left and right child have been visited
recursively*/
    fun traversePostOrder(visit: Visitor<T> /* = (T) -> kotlin.Unit */){
        leftChild?.traversePostOrder(visit)
        rightChild?.traversePostOrder(visit)
        visit(value)
    }

    // Traversal space and time complexity O(n)

    // The heigh of the tree
    fun height(node: BinaryNode<T>? = this): Int {
        return node?.let {1 + max(height(node.leftChild), height(node.rightChild)) } ?: -1
    }

    // Serialization of a Binary Tree
    fun traversePreOrderWithNull(visit: Visitor<T?> /* = (T) -> kotlin.Unit */){
        visit(value)
        leftChild?.traversePreOrderWithNull(visit) ?: visit(null)
        rightChild?.traversePreOrderWithNull(visit) ?: visit(null)
    }

    //For serialization, you traverse the tree and store the values into an array
    fun serialize(node: BinaryNode<T> = this) : MutableList<T?> {
        val list = mutableListOf<T?>()
        node.traversePreOrderWithNull { list.add(it) }
        return list
    }

    /*The time complexity of the serialization step is O(n). Because you’re creating a new
list, this also incurs an O(n) space cost.*/

    // Deserialization
    fun deserialize(list: MutableList<T?>): BinaryNode<T?>? {
        val rootValue = list.removeAt(list.size - 1) ?: return null
        val root = BinaryNode<T?>(rootValue)

        root.leftChild = deserialize(list)
        root.rightChild = deserialize(list)

        return root
    }

    fun deserializeOptimezed(list: MutableList<T?>): BinaryNode<T?>? {
        return deserialize(list.asReversed())
    }
}