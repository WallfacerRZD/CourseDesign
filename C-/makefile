#
# makefile for TINY
# Borland C Version
# K. Louden 2/3/98
#

CC = gcc

CFLAGS = 

OBJS = main.o util.o scan.o

cm: $(OBJS)
	$(CC) $(CFLAGS) $(OBJS) -o cm

main.o: main.c globals.h util.h scan.h
	$(CC) $(CFLAGS) -c main.c

util.o: util.c util.h globals.h
	$(CC) $(CFLAGS) -c util.c

scan.o: scan.c scan.h util.h globals.h
	$(CC) $(CFLAGS) -c scan.c

clean:
	-rm tiny.exe
	-rm tm.exe
	-rm main.o
	-rm util.o
	-rm scan.o
	-rm parse.o
	-rm symtab.o
	-rm analyze.o
	-rm code.o
	-rm cgen.o
	-rm tm.o

tm: tm.c
	$(CC) $(CFLAGS) -etm tm.c

cm: cm

