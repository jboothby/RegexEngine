Name: Joseph Boothby
Date: October 17, 2020
Email: joseph.boothby@wsu.edu

Files included:
Main.java - The main driver program for running the RegexEngine program
NFA.java - The class file that defines the NFA class
State.java - The class file that defines the State class
manifest.mf - The manifest file for running the jar command
regex_postfix.txt - A test file containing some RegEx.
makefile - The makefile for compiling the code
RegexEngineJavadoc - This folder contains all of the Javadocs for this program. Open allclasses-index.html to view the javadocs

Subdirectory JustInCase:
This subdirectory contains a copy of the .jar file that the makefile creates
This is just in case the JDK verson on your computer is before JDK 13, which will cause
the code to not compile as it uses the Advanced Case Statement Syntax that is not supported
in earlier versions. Because Java runs on the JVM, the .jar file should work on any computer.
This is also an alternate way to execute the program on a windows machine without compiling.
Just open a command prompt in the JustInCase directory and run the following command:

java -jar RegexEngine.jar

This can be given command line arguments of a file name just like in the linux run instructions
Example:

java -jar RegexEngine.jar other_test_file.txt

Compilation instructions(linux):
1.) Extract the archive into a folder
2.) Navigate to that folder in your terminal and run the 'make' command
	This command will compile the .java files, and compress them into a .jar
	archive. Then it will run chmod to make the .jar executable
3.) To run the program, type ./RegexEngine.jar
	If provided no command-line arguments, the program will default to using
	regex_postfix.txt. If provided with a command-line argument, the first
	argument must be the name of a file containing regular expressions

Compilation Instructions(Windows):
1.) Extract the archive into a folder
2.) Open the three .java files and regex_postfix.txt using your favorite IDE (like intellj)
3.) Build and Run Main.java

Run Instructions
To run the program, type ./RegexEngine.jar
	If provided no command-line arguments, the program will default to using
	regex_postfix.txt. If provided with a command-line argument, the first
	argument must be the name of a file containing regular expressions

Examples

./RegexEngine.jar
./RegexEngine.jar other_test_file.txt


Additional Information:
In the output from the program, multiple transitions from the same state
will be shown on the same line. This may result in fewer states than a program
that lists each transition as a separate state. However, I feel that my way is more
accurate to how they NFA would be drawn.
Further, the program renames the states to put them in proper numerical order before
printing so that the start state is always the first number, and the accept state is always the last number

