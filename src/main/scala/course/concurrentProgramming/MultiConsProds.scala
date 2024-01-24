package course.concurrentProgramming

import scala.collection.mutable
import scala.language.postfixOps
import scala.util.Random
object MultiConsProds extends App {
  class Consumer(id: Int, buffer: mutable.Queue[Int]) extends Thread{
    override def run(): Unit = {
      val random = new Random()
      while(true) {
        buffer.synchronized {
          while (buffer isEmpty) {
            println(s"buffer is empty consumer $id is waiting")
            buffer.wait()
          }
          val element = buffer.dequeue()
          println(s"consumer $id is using $element")
          buffer.notifyAll()
        }
        Thread.sleep(random.nextInt(500))
      }
    }
  }

  class Producer(id: Int, buffer: mutable.Queue[Int], capacity: Int = 3) extends Thread {
    override def run(): Unit = {
      val random = new Random()
      var element = 0
      while(true) {
        buffer.synchronized {
          while (buffer.size == capacity) {
            println(s"buffer is full producer $id is waiting")
            buffer.wait()
          }
          buffer.enqueue(element)
          println(s"producer $id produced $element")
          element += 1
          buffer.notifyAll()
        }
        Thread.sleep(random.nextInt(500))
      }
    }
  }
  def createConsumers(nConsumers: Int, buffer: mutable.Queue[Int], id: Int = 0): Unit = {
    if(nConsumers != 0) {
      println(s"creating consumer with $id id")
      new Consumer(id, buffer).start()
      createConsumers(nConsumers - 1, buffer, id + 1)
    }
  }
  def createProducers(nProducers: Int, buffer: mutable.Queue[Int], id: Int = 0, capacity: Int = 3): Unit = {
    if (nProducers != 0) {
      println(s"creating producer with $id id")
      new Producer(id, buffer, capacity).start()
      createProducers(nProducers - 1, buffer, id + 1, capacity)
    }
  }
  def runProdsCons(nConsumers: Int, nProducers: Int, capacity: Int): Unit = {
    val buffer: mutable.Queue[Int] = new mutable.Queue[Int]
    createConsumers(nConsumers, buffer)
    createProducers(nProducers, buffer, 0, capacity)
  }
  runProdsCons(3, 3, 3)
}
