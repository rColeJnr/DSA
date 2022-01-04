
class TreeNode <T>(val value: T) {
    private val children: MutableList<TreeNode<T>> = mutableListOf()

    fun add(child: TreeNode<T>) = children.add(child)

    /*Depth-first traversal
Depth-first traversal starts at the root node and explores the tree as far as possible
along each branch before reaching a leaf and then backtracking.*/
//    This simple code uses recursion to process the next node.
    fun forEachDepthFirst(visit: Visitor<T>) {
        visit(this)
        children.forEach {
            it.forEachDepthFirst(visit)
        }
    }

    /*Level-order traversal
Level-order traversal is a technique that visits each node of the tree based on the
depth of the nodes. Starting at the root, every node on a level is visited before going
to a lower level.*/
    // I didn't understand this
    fun forEachLevelOrder(visit: Visitor<T>) {
        visit(this)
        println("nodeFirst: ${this.value}")
        val queue = ArrayListQueue<TreeNode<T>>()
        children.forEach { queue.enqueue(it) }
        println("queue size  ${queue.count}")
        var node = queue.dequeue()
        println("node: ${node?.value}")
        while (node != null) {
            visit(node)
            node.children.forEach { queue.enqueue(it) }
            println("queue size2: ${queue.count}")
            node = queue.dequeue()
            println("node2: ${node?.value}")
        }
    }

    /*Here, you used your level-order traversal algorithm. Since it visits all nodes, if there
are multiple matches, the last match wins. This means that you’ll get different
objects back depending on what traversal you use.*/
    fun search(value: T): TreeNode<T>? {
        var result: TreeNode<T>? = null

        forEachLevelOrder {
            if (it.value == value) {
                result = it
            }
        }
        return result
    }
//    This algorithm has a time complexity of O(n)
    fun printEachLevel() {
        val queue = ArrayListQueue<TreeNode<T>>()
        var nodesLeftInCurrentLevel = 0
        queue.enqueue(this)
        while (queue.isEmpty.not()) {
            nodesLeftInCurrentLevel = queue.count
            while (nodesLeftInCurrentLevel > 0) {
                val node = queue.dequeue()
                node?.let {
                    print("   ${node.value} ")
                    node.children.forEach { queue.enqueue(it) }
                    nodesLeftInCurrentLevel--
                } ?: break
            }
            println()
        }
    }
}

typealias Visitor<T> = (TreeNode<T>) -> Unit
