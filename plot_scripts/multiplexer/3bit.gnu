#! /usr/bin/gnuplot

set terminal pdf font "Helvetica,22"
set output  "results/multiplexer/11bit.pdf"
set title "Ponašanje XCS sustava na 8/1 MUX-u"
set xlabel "Broj testnih primjera / 1000"
set ylabel "Točnost"
plot [] [0 to 1.1] "results/multiplexer/11bit.dat" notitle with lines

