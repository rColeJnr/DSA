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

fun main() {

    val graph = AdjacencyMatrix<String>()

    val mozambique = graph.createVertex("Mozambique")
    val angola = graph.createVertex("Angola")
    val zambia = graph.createVertex("Zambia")
    val southAfrica = graph.createVertex("South Africa")
    val nigeria = graph.createVertex("Nigeria")
    val zimbabwe = graph.createVertex("Zimbabwe")
    val caboVerde = graph.createVertex("Cabo Verde")
    val swazi = graph.createVertex("Swazi")
    val tanzania = graph.createVertex("Tanzania")
    val ghana = graph.createVertex("Ghana")

    graph.add(EdgeType.UNDIRECTED, mozambique, angola, 300.0)
    graph.add(EdgeType.UNDIRECTED, mozambique, southAfrica, 200.0)
    graph.add(EdgeType.UNDIRECTED, mozambique, swazi, 200.0)
    graph.add(EdgeType.UNDIRECTED, mozambique, nigeria, 200.0)
    graph.add(EdgeType.UNDIRECTED, mozambique, ghana, 200.0)
    graph.add(EdgeType.UNDIRECTED, nigeria, angola, 450.0)
    graph.add(EdgeType.UNDIRECTED, zimbabwe, nigeria, 300.0)
    graph.add(EdgeType.UNDIRECTED, caboVerde, mozambique, 350.0)
    graph.add(EdgeType.UNDIRECTED, swazi, mozambique, 50.0)
    graph.add(EdgeType.UNDIRECTED, swazi, zambia, 340.0)
    graph.add(EdgeType.UNDIRECTED, southAfrica, nigeria, 550.0)
    graph.add(EdgeType.UNDIRECTED, southAfrica, zimbabwe, 340.0)
    graph.add(EdgeType.UNDIRECTED, angola, caboVerde, 456.0)
    graph.add(EdgeType.UNDIRECTED, tanzania, angola, 245.0)
    graph.add(EdgeType.UNDIRECTED, ghana, tanzania, 645.0)
    graph.add(EdgeType.UNDIRECTED, nigeria, ghana, 645.0)
    graph.add(EdgeType.UNDIRECTED, ghana, nigeria, 645.0)
    graph.add(EdgeType.UNDIRECTED, nigeria, mozambique, 645.0)
    graph.add(EdgeType.UNDIRECTED, ghana, angola, 645.0)
    graph.add(EdgeType.UNDIRECTED, ghana, caboVerde, 645.0)
    graph.add(EdgeType.UNDIRECTED, zambia, caboVerde, 645.0)
    graph.add(EdgeType.UNDIRECTED, zambia, ghana, 645.0)
    graph.add(EdgeType.UNDIRECTED, zambia, tanzania, 645.0)
    graph.add(EdgeType.UNDIRECTED, zambia, zimbabwe, 645.0)

    println(graph)

    println("Mozambique Outgoing Flights:")
    println("--------------------------------")
    graph.edges(mozambique).forEach { edge ->
        println("from: ${edge.source.data} to: ${edge.destination.data}")
    }
}