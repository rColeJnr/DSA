import linkedlist.LinkedList

/*One of the prime use cases for stacks is to facilitate backtracking. If you push a
sequence of values into the stack, sequentially popping the stack will give you the
values in reverse order:*/

fun <T: Any> LinkedList<T>.reverseLinkedListWithStack() {

    val stack = StackImpl<T>()

    for (item in this){
        stack.push(item)
    }

    var node = stack.pop()
    while (node != null) {
        println(node)
        node = stack.pop()
    }
    print(node)
}

/*To check if there are balanced parentheses in the string, you need to go through each
character of the string. When you encounter an opening parenthesis, you’ll push that
into a stack. Then, if you encounter a closing parenthesis, you should pop the stack.*/

fun String.checkParentheses(): Boolean {
    val stack = StackImpl<Char>()
    for (character in this) {
        when (character) {
            '(' -> stack.push(character)
            ')' -> if (stack.isEmpty) {
                return false
            } else {
                stack.pop()
            }
        }
    }
    return stack.isEmpty
}