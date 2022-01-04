import java.lang.Math.max
import kotlin.math.pow

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

class AVLTree<T : Comparable<T>> {

  var root: AVLNode<T>? = null

  // Updating insertion for AVL
  fun insert(value: T) {
    root = insert(root, value)
  }

  private fun insert(node: AVLNode<T>?, value: T): AVLNode<T> {
    node ?: return AVLNode(value)

    if (value < node.value) {
      node.leftChild = insert(node.leftChild, value)
    } else {
      node.rightChild = insert(node.rightChild, value)
    }
    val balancedNode = balanced(node)
    balancedNode?.height = max(balancedNode?.leftHeight ?: 0, balancedNode?.rightHeight ?: 0) + 1
    return balancedNode
  }

  fun remove(value: T) {
    root = remove(root, value)
  }

  private fun remove(node: AVLNode<T>?, value: T): AVLNode<T>? {
    node ?: return null

    when {
      value == node.value -> {
        // 1
        if (node.leftChild == null && node.rightChild == null) {
          return null
        }
        // 2
        if (node.leftChild == null) {
          return node.rightChild
        }
        // 3
        if (node.rightChild == null) {
          return node.leftChild
        }
        // 4
        node.rightChild?.min?.value?.let {
          node.value = it
        }

        node.rightChild = remove(node.rightChild, node.value)
      }
      value < node.value -> node.leftChild = remove(node.leftChild, value)
      else -> node.rightChild = remove(node.rightChild, value)
    }
    val balancedNode = balanced(node)
    balancedNode.height = max(balancedNode.leftHeight, balancedNode.rightHeight) + 1
    return balancedNode
  }

  override fun toString() = root?.toString() ?: "empty tree"

  fun contains(value: T): Boolean {
    // 1
    var current = root

    // 2
    while (current != null) {
      // 3
      if (current.value == value) {
        return true
      }

      // 4
      current = if (value < current.value) {
        current.leftChild
      } else {
        current.rightChild
      }
    }

    return false
  }

  //LEFT-ROTATION
  private fun leftRotate(node: AVLNode<T>): AVLNode<T> {
    val pivot = node.rightChild!!
    node.rightChild = pivot.leftChild
    pivot.leftChild = node
    node.height = max(node.leftHeight, node.rightHeight) + 1
    return pivot
  }

  //RIGHT-ROTATION
  private fun rightRotate(node: AVLNode<T>): AVLNode<T> {
    val pivot = node.leftChild!!
    node.leftChild = pivot.rightChild
    pivot.rightChild = node
    node.height = max(node.leftHeight, node.rightHeight) + 1
    pivot.height = max(pivot.leftHeight, pivot.rightHeight) + 1
    return pivot
  }

  //RIGHT-LEFT ROTATION
  /*1. You apply a right rotation to 37.
2. Now that nodes 25, 36 and 37 are all right children, you can apply a left rotation
to balance the tree.*/
  private fun rightLeftRotate(node: AVLNode<T>): AVLNode<T> {
    val rightChild = node.rightChild ?: return node
    node.rightChild = rightRotate(rightChild)
    return leftRotate(node)
  }

  //LEFT-RIGHT ROTATION
  /*1. You apply a left rotation to node 10.
2. Now that nodes 25, 15 and 10 are all left children, you can apply a right rotation
to balance the tree.*/
  private fun leftRightRotate(node: AVLNode<T>): AVLNode<T> {
    val leftChild = node.leftChild ?: return node
    node.leftChild = leftRotate(leftChild)
    return rightRotate(node)
  }

  //BALANCE
  /*balanced() inspects the balanceFactor to determine the proper course of action.
All that’s left is to call balanced() at the proper spot*/
  private fun balanced(node: AVLNode<T>): AVLNode<T> {
    return when (node.balanceFactor){
      2 -> {
        if (node.leftChild?.balanceFactor == -1) {
          leftRightRotate(node)
        } else {
          rightRotate(node)
        }
      }
      -2 -> {
        if (node.rightChild?.balanceFactor == 1) {
          rightLeftRotate(node)
        } else {
          leftRotate(node)
        }
      }
      else -> node
    }
  }

  //SOLUTIONS
  // Count leaf
  fun leafNodes(height: Int):Int {
    return 2.0.pow(height).toInt()
  }

  // Count nodes
  fun nodes(height: Int):Int {
    var totalNodes = 0
    (0..height).forEach{currentHeight ->
    totalNodes += 2.0.pow(currentHeight).toInt()
    }
    return totalNodes
  }

 /* Although this certainly gives you the correct answer, there’s a faster way. If you
  examine the results of a sequence of height inputs, you’ll realize that the total
  number of nodes is one less than the number of leaf nodes of the next level.
  The previous solution is O(height) but here’s a faster version of this in O(1):
 */
 fun nodesO1(height: Int): Int {
    return 2.0.pow(height + 1).toInt() - 1
  }
}
