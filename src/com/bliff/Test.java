package com.bliff;

import java.io.IOException;

public class Test {
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

	public static void main(String[] args) throws IOException {
		String data_path;
		String sample_path;
		String sample_name;
		if(args.length != 3){
			data_path = "data/TestData.blf";
			sample_path = "samples/SlimeTorpedo.blf";
			sample_name = "SlimeTorpedo";
		}
		else{
			data_path = args[0];
			sample_path = args[1];
			sample_name = args[2];
		}

		compare(data_path, sample_path, sample_name);
	}
	
	private static void compare(String datapath, String samplepath, String sample_name) throws IOException{
		MapReader mr = new MapReader();
		char[][] data = mr.readData(datapath);
		char[][] sample = mr.readSample(samplepath);
		double[][] similarity = new Comparer().generateSimilarity(data, sample);
		new GridView(data, sample, similarity, sample_name);
	}
	

}
