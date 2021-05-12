package lectures.part3concurrency

object Intro extends App {

  /*
    interface Runnable {
      public void run()
    }
   */
  // JVM threads
  val aThread = new Thread(() => println("Running in parallel"))

  aThread.start() // gives the signal to the JVM to start a JVM thread
  // create a JVM thread => OS thread

}
