#! /usr/bin/gnuplot

set terminal pdf font "Helvetica,22"
set output  "results/carry/10bit.pdf"
set xlabel "Broj testnih primjera / 1000"
set ylabel "Točnost"
plot [] [0 to 1.1] "results/carry/10bit.dat" notitle with lines

