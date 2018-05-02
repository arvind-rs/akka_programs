
/*
 * Simple calculator program in Scala using Akka framework.
 * @author: arvind-rs
 * @date: 04/24/2018
 */

import akka.actor._
import akka.dispatch._
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.Await

case class Parameters(a: Int, b: Int, c: List[Int])



class CalculatorActor extends Actor {
	def receive = {
		case Parameters(a,b,c) => {sender ! "$a + $b"; c.foreach(println)}
		case "+" => {val (a, b) = getInput; sender ! (a + b)}
		case "-" => {val (a, b) = getInput; sender ! (a - b)}
		case "*" => {val (a, b) = getInput; sender ! (a * b)}
		case "/" => {val (a, b) = getInput; sender ! (a / b).toDouble}
		case (msg,n) => {
			for(i <- 0 until n.asInstanceOf[Int])
				println(msg);
		}
		case _ => println("Invalid choice")
	}
	def add() {
		val (a, b) = getInput
		println(s"$a + $b = " + (a + b))
	}
	def getInput(): (Int, Int) = {
		println("Please enter two numbers ...")
		val a = readLine("> ").toInt
		val b = readLine("> ").toInt
		return (a, b)
	}
}

object SimpleCalculator {
	def main(args: Array[String]) {
		val system = ActorSystem("SimpleCalculator")
		val calcActor = system.actorOf(Props[CalculatorActor], name = "CalculatorActor")
		
		
		//system.shutdown
		//system.stop(calcActor)
		//calcActor ! PoisonPill
		implicit val timeout = Timeout(15 seconds)
		val c = List[Int](4,3,5,6)
		var future = calcActor ? Parameters(5,6, c)
		var result = Await.result(future, timeout.duration)
		println("Response from the actor = " + result)

		try {
			/*future = calcActor ? "+"
		result = Await.result(future, timeout.duration)
		println("Response from the actor = " + result)
		future = calcActor ? "-"
		result = Await.result(future, timeout.duration)
		println("Response from the actor = " + result)
		future = calcActor ? "*"
		result = Await.result(future, timeout.duration)
		println("Response from the actor = " + result)
		future = calcActor ? "/"
		result = Await.result(future, timeout.duration).asInstanceOf[Double]
		println("Response from the actor = " + result)*/

		val printActor1 = system.actorOf(Props[CalculatorActor], name = "PrintActor1")
		val printActor2 = system.actorOf(Props[CalculatorActor], name = "PrintActor2")
		//val printActor1 = system.actorOf(Props[CalculatorActor], name = "PrintActor1")
		printActor1 ! ("hello", 1000)
		printActor2 ! ("world", 1000)
		} catch {
			case ex: Exception => throw new RuntimeException(ex)
		} finally {
			system.shutdown
		}
				
	}
}
