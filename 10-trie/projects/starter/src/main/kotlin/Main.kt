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
    "insert and contains" example {
        val trie = Trie<Char>()
        trie.insert("cute")
        if (trie.contains("cute")) {
            println("cute is in the trie")
        }
    }

    "remove" example {
        val trie = Trie<Char>()
        trie.insert("cut")
        trie.insert("cute")
        println("\n*** Before removing ***")
        assert(trie.contains("cut"))
        println("\"cut\" is in the trie")
        assert(trie.contains("cute"))
        println("\"cute\" is in the trie")
        println("\n*** After removing cut ***")
        trie.remove("cut")
        assert(!trie.contains("cut"))
        assert(trie.contains("cute"))
        println("\"cute\" is still in the trie")
    }

    "prefix matching" example {
        val trie = Trie<Char>().apply {
            insert("car")
            insert("card")
            insert("care")
            insert("cared")
            insert("cars")
            insert("carbs")
            insert("carapace")
            insert("cargo")
            insert("carbage")
            insert("carage")
            insert("carol")
            insert("carolla")
            insert("carolina")
        }
        println("\nCollections starting with \"car\"")
        val prefixedWithCar = trie.collections("car")
        println(prefixedWithCar)
        println("\nCollections starting with \"care\"")
        val prefixedWithCare = trie.collections("care")
        println(prefixedWithCare)
    }
}
