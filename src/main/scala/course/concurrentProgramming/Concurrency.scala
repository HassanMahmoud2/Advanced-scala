package course.concurrentProgramming

object Concurrency extends App{

  //1. use joins to make the main thread wait working thread to finish
  val thread1 = new Thread(() => {
    println("Thread 1 is running")
    Thread.sleep(2000) // Simulate some work
    println("Thread 1 is done")
  })

  val thread2 = new Thread(() => {
    println("Thread 2 is running")
    Thread.sleep(3000) // Simulate some work
    println("Thread 2 is done")
  })
  // Start both threads
  thread1.start()
  thread2.start()
  // Wait for both threads to finish
  thread1.join()
  thread2.join()
  println("All threads are done")

  /**************************************/

  //2. creating inception threads and print them in reverse order
  def inceptionThreads(maxThreads: Int, iterator: Int = 0): Thread = {
    new Thread(() => {
      if (iterator < maxThreads) {
        val newThread = inceptionThreads(maxThreads, iterator + 1)
        newThread.start()
        newThread.join()
      }
      println(s"Hello from thread $iterator")
    })
  }
  inceptionThreads(50).start()
}
