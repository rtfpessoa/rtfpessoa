// qamine.scala
// Qamine Engineering Team Challenge
// 
// Author: Rodrigo Fernandes
// Create: 04-06-2013 09:18


import scala.io.Source.fromInputStream
import java.net.{URLConnection, URL}
import sys.process._

object QamineChallange {
    var contact = "teste@gmail.com"

    def add(m: Int, n: Int) = m + n
    def subtract(m: Int, n: Int) = n - m
    def multiply(m: Int, n: Int) = m * n
    def divide(m: Int, n: Int) = m / n

    def matchOperation(x: String, m: Int, n: Int): Int = x match {
        case "add" => add(m, n)
        case "subtract" => subtract(m, n)
        case "multiply" => multiply(m, n)
        case "divide" => divide(m, n)
    } 

    def main(args: Array[String]) {     
        // Get web page and extract challenge
        val challengeURL = new URL("http://powerful-fortress-5090.herokuapp.com/challenge")
        val challengeConnection = challengeURL.openConnection()
        val pageLines = fromInputStream(challengeConnection.getInputStream).getLines
        var challenge = pageLines.next()
        println("[CHALLENGE: " + challenge + "]")
        
        // Define the define the operation and number regular expressions
        val Operation = "(add|subtract|multiply|divide)".r
        val Numbers = "([0-9]+)".r
        
        // Get the operation and the numbers
        val operation = Operation.findAllIn(challenge).toList
        val numbers = Numbers.findAllIn(challenge).toList

        // Calculate payload
        var payload = matchOperation(operation(0), numbers(0).toInt, numbers(1).toInt)

        // Debug print with all challenge info
        println("[ID:" + numbers(2) + "] " + operation(0) + " " + numbers(0) + " " + numbers(1) + " = " + payload)
        
        // Prepare HTTP/POST adress and arguments
        var urlDest = new URL("http://powerful-fortress-5090.herokuapp.com/answer")
        var args = "payload=" + payload + "&contact=" + contact + "&id=" + numbers(2)
        
        // VERY ugly way of doing HTTP/POST could not use any of java stuff
        // In java.net.URLConnection method to set POST is missing
        // In org.apache classes to use HTTP/POST missing could not import them into the program
        // Needs fix
        var command = "curl -q --data " + args + " http://powerful-fortress-5090.herokuapp.com/answer"
        command.!
    }
}

