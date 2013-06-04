#!/bin/bash

# qamine.sh
# Qamine Engineering Team Challenge
#
# Author: Rodrigo Fernandes
# Create: 04-06-2013 01:53

CONTACT=rodrigo.fernandes@ist.utl.pt

# Get first line of the page
wget -qO- http://powerful-fortress-5090.herokuapp.com/challenge > page
PAGE=`head -n 1 page`

# Put input into variables
OPERATOR=`echo ${PAGE} | cut -d' ' -f5`
ORDER=`echo ${PAGE} | cut -d' ' -f7`
NUMBER1=`echo ${PAGE} | cut -d' ' -f6`
NUMBER2=`echo ${PAGE} | cut -d' ' -f8`
ID=`echo ${PAGE} | cut -d' ' -f16`

# Execute the operation
if [ "${OPERATOR}" == "add" ]; then
    PAYLOAD=$(($NUMBER1+$NUMBER2))
elif [ "${OPERATOR}" == "subtract" ]; then
    PAYLOAD=$(($NUMBER2-$NUMBER1))
elif [ "${OPERATOR}" == "multiply" ]; then
    PAYLOAD=$(($NUMBER1*$NUMBER2)) 
elif [ "${OPERATOR}" == "divide" ]; then
    PAYLOAD=$(($NUMBER1/$NUMBER2))
else
    echo "How the hell you got here? :)"
    PAYLOAD=0
fi

echo "[ID:${ID}] ${OPERATOR} ${NUMBER1} ${ORDER} ${NUMBER2} = ${PAYLOAD}"

# Create HTTP/POST args
PAYLOAD=`echo "payload=${PAYLOAD}&contact=${CONTACT}&id=${ID}"`

# Send HTTP/POST
curl --data ${PAYLOAD} http://powerful-fortress-5090.herokuapp.com/answer

