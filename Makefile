# Variables
JAVAC = javac
JAVA = java
MAIN = MazeBinaryTree
SRC = $(wildcard *.java)
CLASS_FILES = $(SRC:.java=.class)

# Default target
all: $(CLASS_FILES)

# Compile Java files
%.class: %.java
	$(JAVAC) $<

# Run the program
run: all
	$(JAVA) $(MAIN)

# Clean up class files
clean:
	rm -f *.class

.PHONY: all run clean
