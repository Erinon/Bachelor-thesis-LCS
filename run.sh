#! /bin/bash

if [ "$#" -ne 2 ]; then
    exit 1
fi

type=$1
bits=$2

# run project
java -cp target/classes hr.fer.zemris.bachelor.Application $type $bits

if [ "$?" -ne 0 ]; then
    exit $?
fi

#while [ "$bits" -ge 2 ]
#do
#    gnuplot "plot_scripts/multiplexer/${bits}bit.gnu"
#    bits=$((bits - 1))
#done

