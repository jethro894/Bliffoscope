package com.bliff;
/*
 * defines the compare result
 * results are printed in terminal
 */
public class CompareResult {
	String target_type;
	int x_offset;
	int y_offset;
	double confidence;
	
	public CompareResult(String target_type, int x_offset, int y_offset,
			double confidence) {
		super();
		this.target_type = target_type;
		this.x_offset = x_offset;
		this.y_offset = y_offset;
		this.confidence = confidence;
	}
}
