class AdjacencyMatrix<T: Any> : Graph<T> {

    private val vertices = arrayListOf<Vertex<T>>()
    private val weights = arrayListOf<ArrayList<Double?>>()

    override fun createVertex(data: T): Vertex<T> {
        val vertex = Vertex(vertices.count(), data)
        vertices.add(vertex)
        weights.forEach {
            it.add(null)
        }
        val row = ArrayList<Double?>(vertices.count())
        repeat(vertices.count()) {
            row.add(null)
        }
        weights.add(row)
        return vertex
    }

    override fun addDirectedEdge(source: Vertex<T>, destination: Vertex<T>, weight: Double?) {
        weights[source.index][destination.index] = weight
    }

    /**To retrieve the outgoing edges for a vertex, you search the row for this vertex in the
    matrix for weights that are not null .
    Every non- null weight corresponds with an outgoing edge. The destination is the
    vertex that corresponds with the column in which the weight was found.*/
    override fun edges(source: Vertex<T>): ArrayList<Edge<T>> {
        val edges = arrayListOf<Edge<T>>()
        (0 until weights.size).forEach {column ->
            val weight = weights[source.index] [column]
            if (weight != null){
                edges.add(Edge(source, vertices[column], weight))
            }
        }
        return edges
    }

    override fun weight(source: Vertex<T>, destination: Vertex<T>): Double? {
        return weights[source.index][destination.index]
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

    override fun toString(): String {
        val verticesDescription = vertices.joinToString("\n") {"${it.index}: ${it.data}"}

        val grid = arrayListOf<String>()
        weights.forEach{
            var row = ""
            (0 until weights.size).forEach { columnIndex ->
                if (columnIndex >= it.size){
                    row += "ø\t\t"
                } else {
                    row += it[columnIndex]?.let { "$it\t" } ?: "ø\t\t"
                }
            }
            grid.add(row)
        }
        val edgesDescription = grid.joinToString("\n")
        return "$verticesDescription\n\n$edgesDescription"
    }

}