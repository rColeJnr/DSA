//The Trie class can store collections containing Keys
class Trie<Key: Any> {

    private val root = TrieNode<Key>(key = null, parent = null)

    /*Insert
    Tries work with lists of the Key type. The trie takes the list and represents it as a
    series of nodes in which each node maps to an element in the list.*/
    fun insert(list: List<Key>) {
        var current = root

        list.forEach { element ->
            val child = current.children[element] ?: TrieNode(element, current)
            current.children[element] = child
            current = child
        }

        current.isTerminating = true
        storedLists.add(list)

    }

    /*you traverse the trie in a way similar to insert. You check every element of the
list to see if it’s in the tree. When you reach the last element of the list, it must be a
terminating element. If not, the list wasn’t added to the tree and what you’ve found
is merely a subset of a larger list.*/
    fun contains(list: List<Key>): Boolean {
        var current = root

        list.forEach { element ->
            val child = current.children[element] ?: return false
            current = child
        }
        return current.isTerminating
    }

    /*1. This part should look familiar, as it’s basically the implementation of contains.
You use it here to check if the collection is part of the trie and to point current
to the last node of the collection.
2. You set isTerminating to false so that the current node can be removed by the
loop in the next step.
3. This is the tricky part. Since nodes can be shared, you don’t want to carelessly
remove elements that belong to another collection. If there are no other children
in the current node, it means that other collections do not depend on the current
node.
You also check to see if the current node is a terminating node. If it is, then it
belongs to another collection. As long as current satisfies these conditions, you
continually backtrack through the parent property and remove the nodes.*/
    fun remove(list: List<Key>){
        var current = root
        list.forEach { char ->
            val child = current.children[char] ?: return
            current = child
        }

        if (!current.isTerminating) return

        current.isTerminating = false
        storedLists.remove(list)

        val parent = current.parent
        while(parent != null && current.children.isEmpty() && !current.isTerminating){
            parent.children.remove(current.key)
            current = parent
        }
    }

    /*1. You start by verifying that the trie contains the prefix. If not, you return an empty
list.
2. After you’ve found the node that marks the end of the prefix, you call a recursive
helper method to find all of the sequences after the current node.*/
    fun collections(prefix: List<Key>): List<List<Key>> {
        var current = root
        prefix.forEach { element ->
            val child = current.children[element] ?: return emptyList()
            current = child
        }

        return collections(prefix, current)
    }

    private fun collections(prefix: List<Key>, node: TrieNode<Key>?): List<List<Key>> {
        val results = mutableListOf<List<Key>>()

        if (node?.isTerminating == true) results.add(prefix)

        node?.children?.forEach{(key, node) ->
            results.addAll(collections(prefix + key, node))
        }
        return results
    }

    //SOLUTIONS
    private val storedLists: MutableSet<List<Key>> = mutableSetOf()
    val lists: List<List<Key>>
        get() = storedLists.toList()

    val count: Int
        get() = storedLists.count()

    val isEmpty: Boolean
        get() = storedLists.isEmpty()
}