
/*
 * Simple program to test out the Actor model in Scala using Akka framework.
 * https://alvinalexander.com/scala/simple-scala-akka-actor-examples-hello-world-actors
 * @author: arvind-rs
 * @date: 04/24/2018
 */

import akka.actor.Actor
import akka.actor._
import akka.actor.ActorSystem
import akka.actor.Props

class HelloActor extends Actor {
	def receive = {
		case "hello" => {
			println("Hello world!")
		}
		case _ => println("??")
	}
}

object HelloWorld {
	def main(args: Array[String]) {
		val system = ActorSystem("HelloSystem")
		val helloActor = system.actorOf(Props[HelloActor], name = "helloactor")
		helloActor ! "hello"
		helloActor ! "xvc"
		helloActor ! PoisonPill
		system.shutdown
	}
}
