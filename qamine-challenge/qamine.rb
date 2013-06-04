#!/usr/bin/ruby

# qamine.rb
# Qamine Engineering Team Challenge
#
# Author: Rodrigo Fernandes
# Create: 04-06-2013 01:18

require 'open-uri'
require 'net/http'
require 'uri'

# My email :)
contact = "rodrigo.fernandes@ist.utl.pt"

# Get page and split information
contents = open('http://powerful-fortress-5090.herokuapp.com/challenge').read.split(' ')

# Put input into variables
operator = contents[4]
order = contents[6]
number1 = contents[5]
number2 = contents[7]
id = contents[15]

# Execute the operation
case operator
when "add"
    payload = number1.to_i + number2.to_i  
when "subtract"
    payload = number2.to_i - number1.to_i
when "multiply"
    payload = number1.to_i * number2.to_i    
when "divide"
    payload = number1.to_i / number2.to_i   
else
    payload = 0
    puts "How the hell you got here? :)"
end

# Debug output
puts "[ID:" + id + "] " + operator + " " + number1 + " " + order + " " + number2 + " = " + payload.to_s

# Send HTTP/POST request
uri = URI.parse('http://powerful-fortress-5090.herokuapp.com/answer')
response = Net::HTTP.post_form(uri, {'payload' => payload, 'contact' => contact, 'id' => id})

# Print response
puts response.body

