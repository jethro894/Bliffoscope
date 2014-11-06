Bliffoscope for coding assignment of serverNow

Hang Yin, hy2368@columbia.edu




Files included:

README.txt

Bliffoscope.jar : the runnable file built upon the source code, please place it under the root folder of my source codes to get to the default data and sample files

Source codes : developed in Eclipse, that contains:
	- data/TestData.blf : the test data I received
	- samples/SlimeTorpedo.blf
	- samples/Starship.blf		: the sample data I received
	- codes in src/com/bliff:
		- Test.java
		- MapReader.java
		- Comparer.java
		- CompareResult.java
		- GridPanel.java






How to run:

(1)	Import the source code folder to Eclipse and run the main function in Test.java
(2)	Directly run the Bliffoscope.jar

Both methods mentioned above need 3 input arguments: 
	<data path>: the path and name of the datafile, default value is "data/TestData.blf"
	<sample path>:  the path and name of the samplefile, default value is "samples/SlimeTorpedo.blf"
	<sample name>:	the name of the sample, default value is "SlimeTorpedo"

Please note that each run only compares data with ONE specific sample. I do this because it is easier for GUI representation.
To compare data with different samples, you have to run with different input arguments


Results:

A pop-up GUI frame that draws the data matrix and highlights the possible targets with your input threshold confidence value.
The details of the highlighted possible targets can be found in terminal.
You can set arbitrary value in (0, 1], but lower threshold may cause late response because too many lines to be printed in terminal.

Suggested threshold on given data and slime torpedo: somewhere around 0.65
Suggested threshold on given data and star ship: somewhere around 0.7







Detailed explaination of source codes:


- Test.java: 
/*
 * This is the entrance of the program.
 * It takes 3 input arguments: 
 * 		path to the data file
 * 		path to the sample file
 * 		the name of the sample file
 * If the size of input arguments is not as predicted, it will use the default paths
 * Then it will do the following things:
 * 		packs the arguments
 *		calls the MapReader to read files
 *		calls the Comparer to generate compare results(a similarity matrix)
 *		calls the GridView to present results in the GUI
 */

- MapReader.java:
/*
 * This reader reads the data file and sample file into char[][] matrix
 * WARNING: The data file and sample file MUST BE RECTANGLE (same length at every line)
 * there is no functions to pad or cut patterns in the file
 * 
 * for the data file, it simply reads everything
 * for the sample file, since I found out that the actual patterns are surrounded with whitespace
 * 		so I cut the white space for higher precision
 * if you have a new sample file with no surrounding whitespaces, you have two options:
 * 		- surround it with whitespace, or
 * 		- use readData() to read the sample file in the Test.java
 */

- Comparer.java:
/*
 * Compares data with sample
 * it uses brute force comparing: 
 * 		- at each possible coordinate, comparing the sample file with the portion of data file
 * 		- coordinate defines the upper-left corner of the area in data to be compared with sample
 * 		- divide the number of matched points to total points in sample
 * 		- results the confidence percentage in double 
 * 		- log down the confidence in every possible coordinate
 */

- CompareResult.java:
/*
 * defines the compare result
 * results are printed in terminal
 */

- GridView.java:
/* generates a GUI to present the comparing result
 * it consists of two main panels: the GridPanel and the ButtonPanel
 * Grid panel reincarnates the data file, and draws the possible targets on data
 * Button panel takes the input of threshold value, and updates the GridPanel
 */

- GridPanel.java:
/* the panel that draws the original data file
 * with all the '+' characters in blue, and whitespaced in gray
 * it takes the similarity matrix from Comparer
 * and generates list of CompareResult according to the input threshold value
 * finally it colors the area of possible targets(targets with greater confidence than threshold) in orange
 * you can use the button and textfield in the ButtonPanel to update this panel
 */



Some detailed comments can be found in source code
