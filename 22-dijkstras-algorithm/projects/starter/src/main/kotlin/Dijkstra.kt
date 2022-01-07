import java.lang.reflect.Array

class Dijkstra<T: Any>(private val graph: AdjacencyList<T>) {

    /**This method takes in the destination vertex along with a dictionary of existing
    paths , and it constructs a path that leads to the destination vertex. Going over the
    code:
    1. Start at the destination vertex.
    2. Create a list of edges to store the path.
    3. As long as you’ve not reached the start case, continue to extract the next edge .
    4. Add this edge to the path.
    5. Set the current vertex to the edge’s source vertex. This moves you closer to the
    start vertex.
    6. Once the while loop reaches the start case, you’ve completed the path and
    return it.*/
    private fun route(destination: Vertex<T>, paths: HashMap<Vertex<T>, Visit<T>>): ArrayList<Edge<T>>{
        var vertex = destination
        val path = arrayListOf<Edge<T>>()

        ricardo@ while (true){
            val visit = paths[vertex] ?: break

            when(visit.type) {
                VisitType.EDGE -> visit.edge?.let {
                    path.add(it)
                    vertex = it.source
                }
                VisitType.START -> break@ricardo
            }
        }
        return path
    }

    /**This method takes in the destination vertex and a dictionary of existing paths , and
it returns the total weight. Going over the code:
1. Construct the path to the destination vertex.
2. sumByDouble sums the weights of all the edges.
    You continue Dijkstra’s algorithm to find the shortest paths until you’ve visited
    all the vertices have been visited. This happens once the priority queue is empty.
    2. For the current vertex , you go through all its neighboring edges.
    3. You make sure the edge has a weight. If not, you move on to the next edge.
    4. If the destination vertex has not been visited before or you’ve found a cheaper
    path, you update the path and add the neighboring vertex to the priority queue.
    Once all the vertices have been visited, and the priority queue is empty, you return
    the map of shortest paths back to the start vertex*/
    private fun distance(
        destination: Vertex<T>,
        paths: HashMap<Vertex<T>, Visit<T>>
    ): Double {
        val path = route(destination, paths)
        return path.sumByDouble { it.weight ?: 0.0 }
    }

    fun shortestPath(start: Vertex<T>): HashMap<Vertex<T>, Visit<T>>{
        val paths: HashMap<Vertex<T>,  Visit<T>> = HashMap()
        paths[start] = Visit(VisitType.START)

        val distanceComparator = Comparator<Vertex<T>>({first, second ->
            (distance(second, paths) - distance(first, paths)).toInt()
        })

        val priorityQueue = ComparatorPriorityQueueImpl(distanceComparator)

        priorityQueue.enqueue(start)

        while (true){
            val vertex = priorityQueue.dequeue() ?: break
            val edges = graph.edges(vertex)

            edges.forEach {
                val weight = it.weight ?: return@forEach

                if (paths[it.destination] == null
                    || distance(vertex, paths) + weight
                    < distance(it.destination, paths)){
                    paths[it.destination] = Visit(VisitType.EDGE, it)
                    priorityQueue.enqueue(it.destination)
                }
            }
        }
        return paths
    }

    /**This simply takes the destination vertex and the map of shortest and returns the
    path to the destination vertex.*/
    fun specificPath(destination: Vertex<T>, paths: HashMap<Vertex<T>, Visit<T>> /* = java.util.HashMap<Vertex<T>, Visit<T>> */): ArrayList<Edge<T>>{
        return route(destination, paths)
    }

    fun getAllShortestsPath(source: Vertex<T>): HashMap<Vertex<T>,  ArrayList<Edge<T>>>{
        val paths = HashMap<Vertex<T>, ArrayList<Edge<T>>>()
        val pathsFromSource = shortestPath(source)

        graph.vertices.forEach {
            val path = specificPath(it, pathsFromSource)
            paths[it] = path
        }

        return paths
    }

}

/*This keeps track of two states:
1. The vertex is the starting vertex.
2. The vertex has an associated edge that leads to a path back to the starting vertex.*/
class Visit<T: Any>(val type: VisitType, val edge: Edge<T>? = null)

enum class VisitType{
    START,
    EDGE
}