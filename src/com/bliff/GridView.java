package com.bliff;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GridView {

	/**
	 * @param args
	 * @throws IOException 
	 */
	private JTextField threshold;
	private JButton update;

	public GridView(final char[][] data, final char[][] sample, double[][] similarity, String sampleName) throws IOException{
		JFrame frame = new JFrame("Search result for " + sampleName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        
        final GridPanel grid_panel = new GridPanel(data, sample, similarity, sampleName);
        
        JPanel button_panel = new JPanel();
        threshold = new JTextField(5);
        update = new JButton("Update threshold");
        update.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e)
        	  {
        		double thres = 0;
        		try{
        			thres = Double.parseDouble(threshold.getText());
        		}catch(NumberFormatException e1){
        			//do nothing
        		}
        		grid_panel.updateThreshold(thres);
        	    
        	  }
        });
        button_panel.add(threshold);
        button_panel.add(update);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        
        mainPanel.add(grid_panel);
        mainPanel.add(button_panel);
        frame.add(mainPanel);
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
			//this.updateThreshold(0.65);
			
		}
		
		public void updateThreshold(double threshold){
			if(threshold <= 0 || threshold > 1 )
				return;
			this.results = Comparer.generateResult(simi, this.sample_name, threshold);
			this.possibleTargets.clear();
			this.setPossibleTargets(this.results);
			repaint();
		}
		
		private void draw(){
			for(int i = 0; i < rows; i++){
				for(int j = 0; j < columns; j++){
					if(!Character.isWhitespace(this.data[i][j]))
						this.selectedCells.add(new Point(j,i));
				}
			}
		}
		
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
