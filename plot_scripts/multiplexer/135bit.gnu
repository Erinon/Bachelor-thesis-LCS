#! /usr/bin/gnuplot

set terminal pdf
set output  "results/multiplexer/7bit.pdf"
set xlabel "Broj testnih primjera / 1000"
set ylabel "Točnost"
plot [] [0 to 100] "results/multiplexer/7bit.dat" notitle with lines

