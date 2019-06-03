#! /bin/bash

if [ "$#" -ne 2 ]; then
    exit 1
fi

type=$1
bits=$2

# run project
#java -cp target/classes hr.fer.zemris.bachelor.Application $type $bits

if [ "$?" -ne 0 ]; then
    exit $?
fi

br=1

echo "Drawing..."

for file in plot_scripts/$type/*; do
    if [ "$br" -gt $bits ]; then
        break;
    fi
    
    gnuplot "$file"
    
    br=$((br + 1))
done

echo "Done."

#do
#    gnuplot "plot_scripts/multiplexer/${bits}bit.gnu"
#    bits=$((bits - 1))
#done

