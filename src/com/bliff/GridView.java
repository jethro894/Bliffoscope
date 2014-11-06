package com.bliff;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GridView {

	/**
	 * @param args
	 * @throws IOException 
	 */


	public GridView(final char[][] data, final char[][] sample, List<CompareResult> results) throws IOException{
		JFrame frame = new JFrame("Testing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(new GridPanel(data, sample, results));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
		
	}
	
	
	public class GridPanel extends JPanel{

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private int rows;
		private int columns;
		private List<Rectangle> cells;
		private List<Point> selectedCells;
		private List<Point> possibleTargets;
		
		public GridPanel(char[][] data, char[][] sample, List<CompareResult> results) {
			super();
			this.rows = data.length;
			this.columns = data[0].length;
			this.cells = new ArrayList<Rectangle>();
			this.selectedCells = new ArrayList<Point>();
			this.possibleTargets = new ArrayList<Point>();
			this.setPossibleTargets(results, sample);
			this.draw(data);
		}
		
		private void draw(char[][] data){
			for(int i = 0; i < rows; i++){
				for(int j = 0; j < columns; j++){
					if(data[i][j] == '+')
						this.selectedCells.add(new Point(j,i));
				}
			}
			System.out.println(this.selectedCells.size());
		}
		
		private void setPossibleTargets(List<CompareResult> results, char[][] sample){
			int sample_row = sample.length;
			int sample_column = sample[0].length;

			for(CompareResult cr : results){
				for(int i = 0; i < sample_row; i++){
					for(int j = 0; j < sample_column; j++){
						Point cell = new Point(j + cr.x_offset, i + cr.y_offset );
						this.possibleTargets.add(cell);
					}
				}
			}
			
			System.out.println(this.possibleTargets.size());
		}
		
		@Override
        public Dimension getPreferredSize() {
            return new Dimension(600, 600);
        }
		
		@Override
        public void invalidate() {
            super.invalidate();
        }
		
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

            if (this.possibleTargets != null) {
            	for(Point p : this.possibleTargets){
            		int index = p.x + (p.y * this.columns);
                    Rectangle cell = this.cells.get(index);
                    g2d.setColor(Color.ORANGE);
                    g2d.fill(cell);
            	}
            }
            
            if (this.selectedCells != null) {
            	for(Point p : this.selectedCells){
            		int index = p.x + (p.y * this.columns);
                    Rectangle cell = this.cells.get(index);
                    g2d.setColor(Color.BLUE);
                    g2d.fill(cell);
            	}
            }
            
            g2d.setColor(Color.GRAY);
            for (Rectangle cell : this.cells) {
                g2d.draw(cell);
            }

            g2d.dispose();
		}
	}
}
