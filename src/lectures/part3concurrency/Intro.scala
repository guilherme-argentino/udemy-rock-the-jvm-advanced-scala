package lectures.part3concurrency

import java.util.concurrent.Executors

object Intro extends App {

  /*
      interface Runnable {
        public void run()
      }
   */
  // JVM threads
  private val runnable = new Runnable {
    override def run(): Unit = println("Running in parallel")
  }
  val aThread = new Thread(runnable)

  aThread.start() // gives the signal to the JVM to start a JVM thread
  // create a JVM thread => OS thread
  runnable.run() // doesn't do anything in parallel!
  aThread.join() // blocks until aThread finishes running

  val threadHello = new Thread(() => (1 to 5).foreach(_ => println("hello")))
  val threadGoodbye = new Thread(() =>
    (1 to 5).foreach(_ => println("goodbye"))
  )

//  threadHello.start()
//  threadGoodbye.start()
  // different runs produce different results!

  // executors
  val pool = Executors.newFixedThreadPool(10)
//  pool.execute(() => println("something in the thread pool"))

//  pool.execute(() => {
//    Thread.sleep(1000)
//    println("done after 1 second")
//  })
//  pool.execute(() => {
//    Thread.sleep(1000)
//    println("almost done")
//    Thread.sleep(1000)
//    println("done after 2 seconds")
//  })

//  pool.shutdownNow()

  pool.shutdown()
  println(pool.isShutdown)

  def runInParallel(): Unit = {
    var x = 0

    val thread1 = new Thread(() => {
      x = 1
    })

    val thread2 = new Thread(() => {
      x = 2
    })

    thread1.start()
    thread2.start()
    println(x)
  }

//  for (_ <- 1 to 10000) runInParallel()
  // race condition

  class BankAccount(var amount: Int) {
    override def toString: String = "" + amount
  }

  def buy(account: BankAccount, thing: String, price: Int): Unit = {
    account.amount -= price
    println("I've bought " + thing)
    println("Muy account is now " + account)
  }

  for (_ <- 1 to 1000) {
    val account = new BankAccount(50000)
    val thread1 = new Thread(() => buy(account, "shoes", 3000))
    val thread2 = new Thread(() => buy(account, "iPhone12", 4000))

    thread1.start()
    thread2.start()
    Thread.sleep(100)
    println()
  }
}
