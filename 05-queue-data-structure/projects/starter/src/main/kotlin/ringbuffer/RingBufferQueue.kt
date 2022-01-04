package ringbuffer

import Queue

class RingBufferQueue<T: Any>(size: Int) : Queue<T> {

    private val ringBuffer = RingBuffer<T>(size)

    override fun enqueue(element: T): Boolean {
        ringBuffer.write(element)
        return true
    }

    override fun dequeue(): T? {
        return if (isEmpty) null else ringBuffer.read()
    }

    override val count: Int
        get() = ringBuffer.count

    override fun peek(): T? {
        return ringBuffer.first
    }

    override fun toString(): String = ringBuffer.toString()
}