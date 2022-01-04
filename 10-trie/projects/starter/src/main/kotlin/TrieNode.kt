/*
1.key holds the data for the node. This is optional because the root node of the trie
has no key.
2. A TrieNode holds a reference to its parent. This reference simplifies remove()
later on.
3. In binary search trees, nodes have a left and right child. In a trie, a node needs to
hold multiple different elements. You’ve declared a children map to help with
that.
4. As discussed earlier, isTerminating acts as an indicator for the end of a
collection.*/
class TrieNode<Key: Any>(var key: Key?, var parent: TrieNode<Key>?) {
    val children: HashMap<Key, TrieNode<Key>> = HashMap()

    var isTerminating = false


}