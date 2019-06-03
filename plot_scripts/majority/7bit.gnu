#! /usr/bin/gnuplot

set terminal pdf font "Helvetica,22"
set output  "results/majority/7bit.pdf"
set title "Ponašanje XCS sustava na 7-bitnom problemu većine"
set xlabel "Broj testnih primjera / 1000"
set ylabel "Točnost"
plot [] [0 to 1.1] "results/majority/7bit.dat" notitle with lines
