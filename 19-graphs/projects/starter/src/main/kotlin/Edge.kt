/**o connect two vertices, there must be an edge between them.*/
data class Edge<T: Any>(val source: Vertex<T>, val destination: Vertex<T>, val weight: Double? = null)
