#! /bin/bash

bits=$1

if [ "$#" -ne 1 ]; then
    bits=2
fi

# run project
java -cp target/classes hr.fer.zemris.bachelor.Application $bits

if [ "$?" -ne 0 ]; then
    exit $?
fi

#while [ "$bits" -ge 2 ]
#do
#    gnuplot "plot_scripts/multiplexer/${bits}bit.gnu"
#    bits=$((bits - 1))
#done

