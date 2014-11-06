package com.bliff;

import java.util.ArrayList;
import java.util.List;

/*
 * Compares data with sample
 * it uses brute force comparing: 
 * 		- at each possible coordinate, comparing the sample file with the portion of data file
 * 		- coordinate defines the upper-left corner of the area in data to be compared with sample
 * 		- divide the number of matched points to total points in sample
 * 		- results the confidence percentage in double 
 * 		- log down the confidence in every possible coordinate
 */
public class Comparer {

	/*	generate the similarity matrix
	*	each entry represents the confidence of comparing the area with sample,
	*	where the area takes similarity[i][j] as its upper left corner
	*	
	*	so the similarity matrix has (data_row - sample_row) rows and (data_column - sample_column) columns
	*/
	public double[][] generateSimilarity(char[][] data, char[][] sample){
		int data_row = data.length;
		int data_column = data[0].length;
		
		int sample_row = sample.length;
		int sample_column = sample[0].length;
		
		if(data_row < sample_row)
			return null;
		else if(data_column < sample_column)
			return null;
	
		int y_length = data_row - sample_row;
		int x_length = data_column - sample_column;
		
		double[][] similarity = new double[y_length][x_length];
		
		
		for(int i = 0; i < y_length; i++){
			for(int j = 0; j < x_length; j++){
				similarity[i][j] = calculateSimilarity(i, j, data, sample);
			}
		}
		return similarity;
	}

	//	compare the specified area with sample matrix
	private double calculateSimilarity(int i, int j, char[][] data, char[][] sample){
		int sample_row = sample.length;
		int sample_column = sample[0].length;
		
		int total_pixels = sample_row*sample_column;
		int matched_pixels = 0;
		
		for(int m = 0; m < sample_row; m++){
			for(int n = 0; n < sample_column; n++){
				if(data[m + i][n + j] == sample[m][n])
					matched_pixels++;
			}
		}
		return (double)matched_pixels/(double)total_pixels;
	}
	
	//	given a specified threshold, find the coordinates with greater confidence
	public static List<CompareResult> generateResult(double[][] similarity, String sample_name, double threshold){
		List<CompareResult> results = new ArrayList<CompareResult>();
		int y_length = similarity.length;
		int x_length = similarity[0].length;
		
		for(int i = 0; i < y_length; i++){
			for(int j = 0; j < x_length; j++){
				if(similarity[i][j] >= threshold)
					results.add(new CompareResult(sample_name, j, i, similarity[i][j]));
			}
		}
		return results;
	}
	
	//	print the results in the result list to the terminal
	public static void printPossible(List<CompareResult> results, char[][] data, int sample_row, int sample_column){
		System.out.println("==========================================");
		if(!results.isEmpty())
			System.out.println("Finding matches for sample: " + results.get(0).target_type);
		for(CompareResult cr: results){
			System.out.println("Pattern found in: (" + cr.x_offset + "," + cr.y_offset + ")");
			System.out.println("Confident: " + cr.confidence);
			for(int i = 0; i < sample_row; i++){
				for(int j = 0; j < sample_column; j++){
					System.out.print(data[i + cr.y_offset][j + cr.x_offset]);
				}
				System.out.println();
			}
		}
		System.out.println("==========================================");
	}
}
