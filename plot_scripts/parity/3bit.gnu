#! /usr/bin/gnuplot

set terminal pdf
set output  "results/multiplexer/3bit.pdf"
set title "Ponašanje XCS sustava na 8/1 MUX-u"
set xlabel "Broj testnih primjera / 1000"
set ylabel "Točnost"
plot [] [0 to 100] "results/multiplexer/3bit.dat" notitle with lines

