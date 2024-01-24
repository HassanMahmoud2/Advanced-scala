package course.concurrentProgramming

import scala.collection.mutable
import scala.language.postfixOps
import scala.util.Random
object ProducerConsumer extends App{
  class SimpleContainer {
    var value: Int = 0
    def isEmpty: Boolean = value == 0
    def set(newValue: Int): Unit = value = newValue
    def get: Int = {
      val result = value
      value = 0
      result
    }
  }

  def prodAndCons(): Unit = {
    val container = new SimpleContainer
    val consumer = new Thread(() => {
      println("consumer is waiting...")
      while (container isEmpty) {
        println("consumer is actively waiting")
      }
      println("consumer consumed " + container.get)
    })

    val producer = new Thread(() => {
      println("producing value...")
      Thread.sleep(500)
      container set 50
      println("I have produced " + 50)
    })
    consumer.start()
    producer.start()
  }
  //prodAndCons()
  def smartProdAndCons(): Unit = {
    val container = new SimpleContainer
    val consumer = new Thread(() => {
      println("consumer is waiting...")
      container.synchronized {
        println("consumer is actively waiting")
        container.wait()
      }
      println("consumer consumed " + container.get)
    })

    val producer = new Thread(() => {
      println("producing value...")
      Thread.sleep(500)
      container.synchronized {
        container set 50
        println("I have produced " + 50)
        container.notify()
      }
    })
    consumer.start()
    producer.start()
  }

  def prodConsLargeBuffer(): Unit = {
    val buffer: mutable.Queue[Int] = new mutable.Queue[Int]
    val capacity = 3

    val consumer = new Thread(() => {
      val random = new Random()
      while (true) {
        buffer.synchronized {
          while (buffer.isEmpty) {
            println("consumer buffer is empty, waiting...")
            buffer.wait()
          }
          val element = buffer.dequeue()
          println("element " + element + " is dequeued by consumer")
          //notifying producer that there is free space in buffer it can continue producing
          buffer.notifyAll()
        }
        Thread.sleep(random.nextInt(500))
      }
    })
    val consumer2 = new Thread(() => {
      val random = new Random()
      while (true) {
        buffer.synchronized {
          while (buffer.isEmpty) {
            println("consumer2 buffer is empty, waiting...")
            buffer.wait()
          }
          val element = buffer.dequeue()
          println("element " + element + " is dequeued by consumer 2")
          //notifying producer that there is free space in buffer it can continue producing
          buffer.notifyAll()
        }
        Thread.sleep(random.nextInt(500))
      }
    })
    val producer = new Thread(() => {
      val random = new Random()
      var element = 0
      while (true) {
        buffer.synchronized {
          if (buffer.size == capacity) {
            println("producer buffer is full, waiting...")
            buffer.wait()
          }
          buffer.enqueue(element)
          println("element " + element + " is produced by producer")
          element += 1
          //notifying consumer that there is an element in buffer so, it can consume it
          buffer.notifyAll()
        }
        Thread.sleep(random.nextInt(500))
      }
    })
    val producer2 = new Thread(() => {
      val random = new Random()
      var element = 0
      while (true) {
        buffer.synchronized {
          if (buffer.size == capacity) {
            println("producer buffer is full, waiting...")
            buffer.wait()
          }
          buffer.enqueue(element)
          println("element " + element + " is produced by producer 2")
          element += 1
          //notifying consumer that there is an element in buffer so, it can consume it
          buffer.notifyAll()
        }
        Thread.sleep(random.nextInt(500))
      }
    })
    producer.start()
    producer2.start()
    consumer.start()
    consumer2.start()
  }
  prodConsLargeBuffer()
}
