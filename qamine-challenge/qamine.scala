// qamine.scala
// Qamine Engineering Team Challenge
// 
// Author: Rodrigo Fernandes
// Create: 05-06-2013 20:12
// Modified: 26-06-2013 20:19

import scala.io.Source.fromInputStream
import java.net.{HttpURLConnection, URL}
import java.io.{OutputStreamWriter}

object Qamine {
    val contact = "rodrigo.fernandes@ist.utl.pt"
      
    def matchOperation(x: String, m: Int, n: Int) = x match {
        case "add" => m + n
        case "subtract" => n - m
        case "multiply" => m * n
        case "divide" => m / n
    }
    
    def sendHTTPPOSTMessage(url: String, args: String) = {
		val answerURL = new URL(url)
		
		// Setup connection to answer the challenge
		val answerConnection = answerURL.openConnection().asInstanceOf[HttpURLConnection]
		answerConnection.setRequestMethod("POST")
		answerConnection.setDoOutput(true)
		
		// Send answer to the challenge
		val wr = new OutputStreamWriter(answerConnection.getOutputStream())
		wr.write(args)
		wr.flush()
		wr.close()
		fromInputStream(answerConnection.getInputStream()).mkString
    }

    def main(args: Array[String]) {     
        // Get web page and extract challenge
        val challengeURL = new URL("http://powerful-fortress-5090.herokuapp.com/challenge")
        val challengeConnection = challengeURL.openConnection
        val challenge = fromInputStream(challengeConnection.getInputStream).getLines().next
        
        // [Debug] Print the challenge information received
        println("[CHALLENGE: " + challenge + "]")
        
        // Define the operations and numbers regular expressions
        val OperationsExpr = "(add|subtract|multiply|divide)".r
        val NumbersExpr = "([0-9]+)".r
        
        // Get the operation and the numbers
        val operation = OperationsExpr.findAllIn(challenge).next
        val numbers = NumbersExpr.findAllIn(challenge).toList
        
        // Calculate payload
        val payload = matchOperation(operation, numbers(0).toInt, numbers(1).toInt)

        // [Debug] Print with all challenge info 
        println("[ID:" + numbers(2) + "] " + operation + " " + numbers(0) + " " + numbers(1) + " = " + payload)
        
        // Prepare HTTP/POST address and arguments
        val answerURL = "http://powerful-fortress-5090.herokuapp.com/answer"
        val args = "payload=" + payload + "&contact=" + contact + "&id=" + numbers(2)
        
        val answer = sendHTTPPOSTMessage(answerURL, args)
        println(answer)
    }
}

