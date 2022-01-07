/*
 * Copyright (c) 2021 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

interface Graph<T: Any> {

  fun createVertex(data: T): Vertex<T>
  fun addDirectedEdge(source: Vertex<T>, destination: Vertex<T>, weight: Double?)
  fun addUndirectedEdge(source: Vertex<T>, destination: Vertex<T>, weight: Double?) {
    addDirectedEdge(source, destination, weight)
    addDirectedEdge(destination, source, weight)
  }

  fun add(edge: EdgeType, source: Vertex<T>, destination: Vertex<T>, weight: Double?) {
    when (edge) {
      EdgeType.DIRECTED -> addDirectedEdge(source, destination, weight)
      EdgeType.UNDIRECTED -> addUndirectedEdge(source, destination, weight)
    }
  }

  fun edges(source: Vertex<T>): ArrayList<Edge<T>>
  fun weight(source: Vertex<T>, destination: Vertex<T>): Double?

  fun numberOfPaths(source: Vertex<T>, destination: Vertex<T>): Int {
    val numberOfPaths = Ref(0) // 1
    val visited: ArrayList<Vertex<T>> = arrayListOf() // 2
    paths(source, destination, visited, numberOfPaths) // 3
    return numberOfPaths.value
  }

  fun paths(source: Vertex<T>, destination: Vertex<T>, visited: ArrayList<Vertex<T>>, pathCount: Ref<Int>) {
    visited.add(source) // 1
    if (source == destination) { // 2
      pathCount.value += 1
    } else {
      val neighbors = edges(source) // 3
      neighbors.forEach { edge ->
        // 4
        if (!visited.contains(edge.destination)) {
          paths(edge.destination, destination, visited, pathCount)
        }
      }
    }
    // 5
    visited.remove(source)
  }

  abstract val allVertices: ArrayList<Vertex<T>>

  /*1. If there are no vertices, treat the graph as connected.
2. Perform a breadth-first search starting from the first vertex. This will return all
the visited nodes.
3. Go through every vertex in the graph and check to see if it has been visited
before.
The graph is considered disconnected if a vertex is missing in the visited list.*/
  fun isDisconnected(): Boolean {
    val firstVertex = allVertices.firstOrNull() ?: return false

    val visited = breadthFirstSearch(firstVertex)
    allVertices.forEach { if (!visited.contains(it)) return true}
    return false
  }

  /*Performance
When traversing a graph using BFS, each vertex is enqueued once. This has a time
complexity of O(V). During this traversal, you also visit all of the edges. The time it
takes to visit all edges is O(E). This means that the overall time complexity for
breadth-first search is O(V + E).
The space complexity of BFS is O(V) since you have to store the vertices in three
separate structures: queue , enqueued and visited .*/
  fun breadthFirstSearch(source: Vertex<T>): ArrayList<Vertex<T>>{
    val queue = QueueStack<Vertex<T>>()
    val enqueued = mutableSetOf<Vertex<T>>()
    val visited = ArrayList<Vertex<T>>()

    queue.enqueue(source)
    enqueued.add(source)

    while (true) {
      val vertex = queue.dequeue() ?: break
      visited.add(vertex)

      val neighborEdges = edges(vertex)
      neighborEdges.forEach {
        if (!enqueued.contains(it.destination)){
          queue.enqueue(it.destination)
          enqueued.add(it.destination)
        }
      }
    }

    return visited
  }

  /*Recursive*/
  fun bfs(source: Vertex<T>): ArrayList<Vertex<T>> {
    val queue = QueueStack<Vertex<T>>()
    val enqueued = mutableSetOf<Vertex<T>>()
    val visited = arrayListOf<Vertex<T>>()

    queue.enqueue(source)
    enqueued.add(source)

    bfs(queue, enqueued, visited)

    return visited
  }
/*1. We start from the first node we dequeue from the queue of all vertices. Then we
recursively continue to dequeue a vertex from the queue till it’s empty.
2. Mark the vertex as visited.
3. For every neighboring edge from the current vertex .
4. Check to see if the adjacent vertices have been visited before inserting into the
queue.
5. Recursively perform bfs until the queue is empty.
The overall time complexity for breadth-first search is O(V + E).*/
  private fun bfs(queue: QueueStack<Vertex<T>>, enqueued: MutableSet<Vertex<T>>, visited: ArrayList<Vertex<T>>){
    val vertex = queue.dequeue() ?: return
    visited.add(vertex)

    val neighborEdges = edges(vertex)
    neighborEdges.forEach {
      if (!enqueued.contains(it.destination)) {
        queue.enqueue(it.destination)
        enqueued.add(it.destination)
      }
    }
    bfs(queue, enqueued, visited)
  }
}

enum class EdgeType {
  DIRECTED,
  UNDIRECTED
}