package com.bliff;

import java.io.IOException;

public class Test {

	public static void main(String[] args) throws IOException {
		String data_path = "data/TestData.blf";
		String sample_path1 = "samples/SlimeTorpedo.blf";
		String sample_name1 = "SlimeTorpedo";
		String sample_path2 = "samples/Starship.blf";
		String sample_name2 = "Starship";
		
		compare(data_path, sample_path1, sample_name1);
		compare(data_path, sample_path2, sample_name2);
	}
	
	private static void compare(String datapath, String samplepath, String sample_name) throws IOException{
		MapReader mr = new MapReader();
		char[][] data = mr.readData(datapath);
		char[][] sample = mr.readSample(samplepath);
		double[][] similarity = new Comparer().generateSimilarity(data, sample);
		new GridView(data, sample, similarity, sample_name);
	}
	

}
