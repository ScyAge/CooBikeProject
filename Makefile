

# Nom du fichier Makefile

# Variables
MVN = mvn
PROJECT = mon-projet

# Cibles
.PHONY: all clean compile test package run

all: compile

clean:
	$(MVN) clean

compile:
	$(MVN) compile

test:
	$(MVN) test

package:
	$(MVN) package

run:
	$(MVN) exec:java -Dexec.mainClass="com.example.MainClass"

