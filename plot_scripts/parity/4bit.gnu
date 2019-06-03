#! /usr/bin/gnuplot

set terminal pdf font "Helvetica,22"
set output  "results/parity/4bit.pdf"
set title "Ponašanje XCS sustava na 4-bitnom parnom paritetu"
set xlabel "Broj testnih primjera / 1000"
set ylabel "Točnost"
plot [] [0 to 1.1] "results/parity/4bit.dat" notitle with lines

