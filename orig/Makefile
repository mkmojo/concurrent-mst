#−a  [0123]
#Animation mode.
#0   (default) =>
#print run time to standard output, but nothing else
#1 =>
#print list of created, destroyed, and selected (tree) edges, plus run time
#2 =>
#create a GUI that shows the triangulation and MST, and allow the user to
#re-run with additional sets of points
#3 =>
#animate the algorithm on the screen as it runs.
#−n  num
#Number of points.  Default = 50.  More than a couple hundred becomes too dense
#to look good when animated.  You’ll need to run big numbers (more than 10,000)
#to get multi-second execution times.
#−s  num
#Seed for pseudorandom number generator.  Every value of the seed produces a
#different set of points.
#−t  num
#Number of threads (max) that should be running at any given time.  This
#argument is currently unused; it’s it’s here to support your parallelization
#efforts.

all:
	javac MST.java
run: all
	java MST -a 3 -n 50
info: all
	java MST -a 0 -n 50
clean:
	rm -fv *.class

