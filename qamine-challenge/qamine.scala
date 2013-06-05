// qamine.scala
// Qamine Engineering Team Challenge
// 
// Author: Rodrigo Fernandes
// Create: 05-06-2013 20:12


import scala.io.Source.fromInputStream
import java.net.{HttpURLConnection, URL}
import java.io.{OutputStreamWriter}

object Qamine {
    var contact = "rodrigo.fernandes@ist.utl.pt"

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
        var answerURL = new URL("http://powerful-fortress-5090.herokuapp.com/answer")
        var args = "payload=" + payload + "&contact=" + contact + "&id=" + numbers(2)
        
        // Get connection to answer
        var answerConnection = answerURL.openConnection().asInstanceOf[HttpURLConnection]
        answerConnection.setRequestMethod("POST")
        answerConnection.setDoOutput(true)
        
        // Send answer
        var wr = new OutputStreamWriter(answerConnection.getOutputStream())
		wr.write(args);
		wr.flush();
		wr.close();
		
		// Print the answer
        val answerLines = fromInputStream(answerConnection.getInputStream()).getLines
        while(answerLines.hasNext) {
        	println(answerLines.next)
        }
    }
}

