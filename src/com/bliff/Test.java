package com.bliff;

import java.io.IOException;
import java.util.List;

public class Test {

	public static void main(String[] args) throws IOException {

		MapReader mr = new MapReader();
		char[][] data = mr.readData("data/TestData.blf");
		char[][] sample1 = mr.readSample("samples/SlimeTorpedo.blf");
		char[][] sample2 = mr.readSample("samples/Starship.blf");
		//System.out.println(data.length);
		//System.out.println(data[0].length);
		Comparer c1 = new Comparer();
		double[][] similarity1 = c1.generateSimilarity(data, sample1);
		List<CompareResult> results1 = c1.generateResult(similarity1, "Torpedo", 0.65);
		//new Comparer().compare(data, sample2, "Starship", 0.71);
		
		new GridView(data, sample1, results1);
	}
	

}
