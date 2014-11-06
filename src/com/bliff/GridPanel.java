package com.bliff;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

/* the panel that draws the original data file
 * with all the '+' characters in blue, and whitespaced in gray
 * it takes the similarity matrix from Comparer
 * and generates list of CompareResult according to the input threshold value
 * finally it colors the area of possible targets(targets with greater confidence than threshold) in orange
 * you can use the button and textfield in the ButtonPanel to update this panel
 */
public class GridPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int rows;
	private int columns;
	private char[][] data;
	private char[][] sample;
	private String sample_name;
	private double[][] simi;
	private List<CompareResult> results;
	private List<Rectangle> cells;
	private List<Point> selectedCells;
	private List<Point> possibleTargets;
	
	public GridPanel(char[][] data, char[][] sample, double[][] similarity, String sampleName) {
		super();
		this.rows = data.length;
		this.columns = data[0].length;
		this.simi = similarity;
		this.data = data;
		this.sample = sample;
		this.sample_name = sampleName;
		this.cells = new ArrayList<Rectangle>();
		this.selectedCells = new ArrayList<Point>();
		this.possibleTargets = new ArrayList<Point>();
		this.draw();	
	}
	
	//updates the possible targets with given threshold
	public void updateThreshold(double threshold){
		if(threshold <= 0 || threshold > 1 )
			return;
		this.results = Comparer.generateResult(simi, this.sample_name, threshold);
		this.possibleTargets.clear();
		this.setPossibleTargets(this.results);
		repaint();
	}
	
	//marks the '+' characters in original data matrix in a list of coordinates called selectedCells
	private void draw(){
		for(int i = 0; i < rows; i++){
			for(int j = 0; j < columns; j++){
				if(!Character.isWhitespace(this.data[i][j]))
					this.selectedCells.add(new Point(j,i));
			}
		}
	}
	
	//with the result list, mark the coordinates of all the points in areas
	//all the points mean that all the points covered in the area with same size of sample, and takes the coordinate as its upper-left corner 
	private void setPossibleTargets(List<CompareResult> results){
		int sample_row = this.sample.length;
		int sample_column = this.sample[0].length;

		for(CompareResult cr : results){
			for(int i = 0; i < sample_row; i++){
				for(int j = 0; j < sample_column; j++){
					Point cell = new Point(j + cr.x_offset, i + cr.y_offset );
					this.possibleTargets.add(cell);
				}
			}
		}
		Comparer.printPossible(this.results, this.data, sample_row, sample_column);
	}
	
	//set size of output window
	@Override
    public Dimension getPreferredSize() {
        return new Dimension(600, 600);
    }
	
	@Override
    public void invalidate() {
        super.invalidate();
    }
	
	//paint the components
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        int width = getWidth();
        int height = getHeight();

        int cellWidth = width / this.columns;
        int cellHeight = height / this.rows;

        int xOffset = (width - (this.columns * cellWidth)) / 2;
        int yOffset = (height - (this.rows * cellHeight)) / 2;
        
        //draws the grid with number of rows and columns in data
        if (this.cells.isEmpty()) {
            for (int row = 0; row < this.rows; row++) {
                for (int col = 0; col < this.columns; col++) {
                    Rectangle cell = new Rectangle(
                            xOffset + (col * cellWidth),
                            yOffset + (row * cellHeight),
                            cellWidth,
                            cellHeight);
                    this.cells.add(cell);
                }
            }
        }
        
        //paints the points covered by possible targets in orange
        if (this.possibleTargets != null) {
        	for(Point p : this.possibleTargets){
        		int index = p.x + (p.y * this.columns);
                Rectangle cell = this.cells.get(index);
                g2d.setColor(Color.ORANGE);
                g2d.fill(cell);
        	}
        }
        
        //paints the points of '+' in original data in blue
        if (this.selectedCells != null) {
        	for(Point p : this.selectedCells){
        		int index = p.x + (p.y * this.columns);
                Rectangle cell = this.cells.get(index);
                g2d.setColor(Color.BLUE);
                g2d.fill(cell);
        	}
        }
        
        //paint all the other cells in gray
        g2d.setColor(Color.GRAY);
        for (Rectangle cell : this.cells) {
            g2d.draw(cell);
        }

        g2d.dispose();
	}
}