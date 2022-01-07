/*1. numberOfPaths keeps track of the number of paths found between the source
and destination .
2. visited is an ArrayList that keeps track of all the vertices visited.
3. paths is a recursive helper function that takes in four parameters. The first two
parameters are the source and destination vertices. visited tracks the vertices
visited, and numberOfPaths tracks the number of paths found. These last two
parameters are modified within paths .*/
fun numberOoofPaths(
    source: Vertex<T>,
    destination: Vertex<T>
): Int {
    val numberOfPaths = Ref(0)
    val visited: MutableSet<Vertex<Element>> = mutableSetOf()
    paths(source, destination, visited, numberOfPaths)
    return numberOfPaths.value
}

data class Ref<T: Any>(var value: T)

/*To get the paths from the source to destination :
1. Initiate the algorithm by marking the source vertex as visited.
2. Check to see if the source is the destination . If it is, you have found a path, so
increment the count by one.
3. If the destination has not be found, get all of the edges adjacent to the source
vertex.
4. For every edge, if it has not been visited before, recursively traverse the
neighboring vertices to find a path to the destination vertex.
5. Remove the source vertex from the visited list so that you can continue to find
other paths to that node.
You’re doing a depth-first graph traversal. You recursively dive down one path until
you reach the destination, and back-track by popping off the stack. The time-
complexity is O(V + E).*/
fun paths(
    source: Vertex<T>,
    destination: Vertex<T>,
    visited: MutableSet<Vertex<T>>,
    pathCount: Ref<Int>
) {
    visited.add(source)
    if (source == destination) {
        pathCount.value += 1
    } else {
        val neighbors = edges(source)
        neighbors.forEach {edge ->
            if (edge.destination !in visited) {
                paths(edge.destination, destination, visited, pathCount)
            }
        }
    }
    visited.remove(source)
}