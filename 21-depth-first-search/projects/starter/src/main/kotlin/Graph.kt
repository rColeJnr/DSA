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

  fun breadthFirstSearch(source: Vertex<T>): ArrayList<Vertex<T>> {
    val queue = QueueStack<Vertex<T>>()
    val enqueued = ArrayList<Vertex<T>>()
    val visited = ArrayList<Vertex<T>>()
    queue.enqueue(source) // 1
    enqueued.add(source)
    while (true) {
      val vertex = queue.dequeue() ?: break // 2
      visited.add(vertex) // 3
      val neighborEdges = edges(vertex) // 4
      neighborEdges.forEach {
        if (!enqueued.contains(it.destination)) { // 5
          queue.enqueue(it.destination)
          enqueued.add(it.destination)
        }
      }
    }
    return visited
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

  /*1. You continue to check the top of the stack for a vertex until the stack is empty.
You’ve labeled this loop outer so that you have a way to continue to the next
vertex, even within nested loops.
2. You find all the neighboring edges for the current vertex.
3. If there are no edges, you pop the vertex off the stack and continue to the next
one.
4. Here, you loop through every edge connected to the current vertex and check to
see if the neighboring vertex has been seen. If not, you push it onto the stack and
add it to the visited list.
t may seem a bit premature to mark this vertex as visited — you haven’t popped it
yet — but since vertices are visited in the order in which they are added to the stack,
it results in the correct order.
5. Now that you’ve found a neighbor to visit, you continue the outer loop and move
to the newly pushed neighbor.
6. If the current vertex did not have any unvisited neighbors, you know that you
reached a dead end and can pop it off the stack.
Once the stack is empty, the DFS algorithm is complete. All you have to do is return
the visited vertices in the order you visited them.*/
  fun depthFirstSearch(source: Vertex<T>): ArrayList<Vertex<T>> {
    val stack = StackImpl<Vertex<T>>()
    val visited = arrayListOf<Vertex<T>>()
    val pushed = mutableSetOf<Vertex<T>>()

    stack.push(source)
    pushed.add(source)
    visited.add(source)

    outer@ while (true) {
      if (stack.isEmpty) break
      val vertex = stack.peek()!!
      val neighbors = edges(vertex)
      if (neighbors.isEmpty()) {
        stack.pop()
        continue
      }

      for (i in  0 until neighbors.size) {
        val destination = neighbors[i].destination
        if (destination !in pushed) {
          stack.push(destination)
          pushed.add(destination)
          visited.add(destination)
          continue@outer
        }
      }
      stack.pop()
    }

    return visited
  }

  /*1. Insert the source vertex into the queue, and mark it as visited.
2. For every neighboring edge…
3. As long as the adjacent vertex has not been visited yet, continue to dive deeper
down the branch recursively.
Overall, the time complexity for depth-first search is O(V + E).*/
  fun dfs(start: Vertex<T>): ArrayList<Vertex<T>> {
    val visited = arrayListOf<Vertex<T>>()
    val pushed = mutableSetOf<Vertex<T>>()

    depthFirstSearch(start, visited, pushed)

    return visited
  }

  private fun depthFirstSearch(source: Vertex<T>, visited: ArrayList<Vertex<T>>, pushed: MutableSet<Vertex<T>>) {
    pushed.add(source)
    visited.add(source)

    val neighbors = edges(source)
    neighbors.forEach {
      if (it.destination !in pushed){
        depthFirstSearch(it.destination, visited, pushed)
      }
    }
  }

  // Directed graph cycle
  /*1. To initiate the algorithm, first, insert the source vertex.
2. For every neighboring edge…
3. If the adjacent vertex has not been visited before, recursively dive deeper down a
branch to check for a cycle.
4. If the adjacent vertex has been visited before, you’ve found a cycle.
5. Remove the source vertex so that you can continue to find other paths with a
potential cycle.
6. No cycle has been found.
You’re essentially performing a depth-first graph traversal by recursively diving
down one path until you find a cycle. You’re backtracking by popping off the stack to
find another path. The time-complexity is O(V + E).*/
  fun hasCycle(source: Vertex<T>): Boolean {
    val pushed = arrayListOf<Vertex<T>>()
    return hasCycle(source, pushed)
  }

  fun hasCycle(source: Vertex<T>, pushed: ArrayList<Vertex<T>>): Boolean {
    pushed.add(source)

    val neighbors = edges(source)
    neighbors.forEach {
      if (it.destination !in pushed && hasCycle(it.destination, pushed)) return true
      else if (it.destination in pushed) return true
    }
    pushed.remove(source)
    return false

  }

}

enum class EdgeType {
  DIRECTED,
  UNDIRECTED
}