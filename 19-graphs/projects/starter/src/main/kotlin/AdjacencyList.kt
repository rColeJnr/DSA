/*create an adjacency list by storing a map of arrays. Each
key in the map is a vertex, and in every vertex, the map holds a corresponding array
of edges.*/
class AdjacencyList <T: Any>: Graph<T> {
    private val adjacencies: HashMap<Vertex<T>, ArrayList<Edge<T>>> = hashMapOf()

    /**Here, you create a new vertex and return it. In the adjacency list, you store an empty
    list of edges for this new vertex.*/
    override fun createVertex(data: T): Vertex<T> {
        val vertex = Vertex(adjacencies.count(), data)
        adjacencies[vertex] = ArrayList()
        return vertex
    }

    /**This method creates a new edge and stores it in the adjacency list.*/
    override fun addDirectedEdge(source: Vertex<T>, destination: Vertex<T>, weight: Double?) {
        val edge = Edge(source, destination, weight)
        adjacencies[source]?.add(edge)
    }

    /**Adding an undirected edge is the same as adding two directed edges.*/
    override fun addUndirectedEdge(source: Vertex<T>, destination: Vertex<T>, weight: Double?) {
        addDirectedEdge(source, destination, weight)
        addDirectedEdge(destination, source, weight)
    }

    /**creates either a directed or undirected
    edge.*/
    override fun add(edge: EdgeType, source: Vertex<T>, destination: Vertex<T>, weight: Double?) {
        when (edge) {
            EdgeType.DIRECTED -> addDirectedEdge(source, destination, weight)
            EdgeType.UNDIRECTED -> addDirectedEdge(source, destination, weight)
        }
    }
    /**return the stored edges or an
    empty list if the source vertex is unknown.*/
    override fun edges(source: Vertex<T>): ArrayList<Edge<T>> {
        return adjacencies[source] ?: arrayListOf()
    }

    /**find the first edge from source to destination ; if there is one, you return
    its weight.*/
    override fun weight(source: Vertex<T>, destination: Vertex<T>): Double? {
        return edges(source).firstOrNull { it.destination == destination }?.weight
    }

    override fun toString(): String {
        return buildString {
            adjacencies.forEach { vertex, edges ->
                val edgeString = edges.joinToString { it.destination.data.toString() }
                append("${vertex.data} ---> [ $edgeString ]\n")
            }
        }
    }
}