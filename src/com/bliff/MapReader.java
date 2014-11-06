package com.bliff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MapReader {

	private List<String> readFile(String filepath) throws IOException{
		File sampleFile = new File(filepath);
		InputStream is = new FileInputStream(sampleFile);
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		String line = null;
		List<String> rows = new ArrayList<String>();
		while((line = br.readLine()) != null){
			rows.add(line);
		}
		
		br.close();
		
		return rows;
	}
	
	private char[][] constructMatrix(List<String> rows){
		int row = rows.size();
		int column = rows.get(0).length();
		char[][] matrix = new char[row][column];
		
		for(int i = 0; i < row; i++){
			String r = rows.get(i);
			for(int j = 0; j < column; j++){
				matrix[i][j] = r.charAt(j);
				//System.out.print(matrix[i][j]);
			}
			//System.out.println();
		}
		
		return matrix;
	}
	
	public char[][] readSample(String filepath) throws IOException{
		List<String> rows = this.readFile(filepath);
		rows.remove(rows.size() - 1);
		rows.remove(0);
		
		for(int i = 0; i < rows.size(); i++){
			StringBuilder sb = new StringBuilder(rows.get(i));
			sb.deleteCharAt(sb.length() - 1);
			sb.deleteCharAt(0);
			rows.set(i, sb.toString());
		}
		
		return this.constructMatrix(rows);
		
	}
	
	public char[][] readData(String filepath) throws IOException{
		List<String> rows = this.readFile(filepath);
		return this.constructMatrix(rows);
	}

}
