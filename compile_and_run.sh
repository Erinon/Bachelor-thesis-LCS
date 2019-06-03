#! /bin/bash

if [ "$#" -ne 2 ]; then
    exit 1
fi

type=$1
bits=$2

./compile.sh

./run.sh $type $bits

