fun Trie<Char>.insert(string: String) {
    insert(string.toList())
}

fun Trie<Char>.contains(string: String): Boolean {
    return contains(string.toList())
}

fun Trie<Char>.remove(string: String) {
    remove(string.toList())
}

/*This extension maps the input string into a list of characters, and then maps the lists
in the result of the collections() call back to strings. Neat!*/
fun Trie<Char>.collections(prefix: String): List<String> {
    return collections(prefix.toList()).map {
        it.joinToString(separator = "")
    }
}