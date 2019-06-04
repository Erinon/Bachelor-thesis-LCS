#! /usr/bin/gnuplot

set terminal pdf font "Helvetica,22"
set output  "results/majority/5bit.pdf"
set xlabel "Broj testnih primjera / 1000"
set ylabel "Točnost"
plot [] [0 to 1.1] "results/majority/5bit.dat" notitle with lines

