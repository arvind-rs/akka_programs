
/*
 * Simple calculator program in Scala using Akka framework.
 * @author: arvind-rs
 * @date: 04/24/2018
 */

import akka.actor._

class CalculatorActor extends Actor {
	def receive = {
		case "+" => println("sum") 
		case _ => println("Invalid choice")
	}
}

object SimpleCalculator {
	def main(args: Array[String]) {
		val system = ActorSystem("SimpleCalculator")
		val calcActor = system.actorOf(Props[CalculatorActor], name = "CalculatorActor")
		calcActor ! "+"
		system.shutdown
	}
}
