#! /usr/bin/gnuplot

set terminal pdf
set output  "results/multiplexer/2bit.pdf"
set title "Performance of XCS on 6 bit MUX"
set xlabel "Training instances / * 1000"
set ylabel "Correct percentage / %"
plot [] [0 to 100] "results/multiplexer/2bit.dat" notitle with lines

