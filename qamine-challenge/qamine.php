#!/usr/bin/php

<?php

/*
    qamine.php
    Qamine Engineering Team Challenge
    
    Author: Rodrigo Fernandes
    Create: 04-06-2013 01:40
*/

/* My email */
$contact = "rodrigo.fernandes@ist.utl.pt";


/* Get page */
$page = file('http://powerful-fortress-5090.herokuapp.com/challenge');

/* Create array with the information */
$info = explode(" ", $page[0]);

/* Put input into variables */
$operator = $info[4];
$order = $info[6];
$number1 = $info[5];
$number2 = $info[7];
$id = $info[15]; /* This var has a space ate the end, why? */
$id = intval($id); /* Hack to fix end space in $id */

/* Execute the operation */
switch ($operator) {
    case "add":
        $payload = $number1 + $number2;
        break;
    case "subtract":
        $payload = $number2 - $number1;
        break;
    case "multiply":
        $payload = $number1 * $number2;
        break;
    case "divide":
        $payload = floor($number1 / $number2);
        break;
    default:
        echo "How the hell you got here? :)";
        $payload = 0;
        break; // Just to be sure ;)
} 

echo "[ID:" . $id . "] " . $operator . " " . $number1 . " " . $order . " " . $number2 . " = " . $payload . "<br>";

$c = curl_init();
curl_setopt($c, CURLOPT_URL, 'http://powerful-fortress-5090.herokuapp.com/answer');
curl_setopt($c, CURLOPT_POST, true);
curl_setopt($c, CURLOPT_POSTFIELDS, 'payload=' . $payload . '&contact=' . $contact . '&id=' . $id);
curl_exec ($c);
curl_close ($c);

?>

