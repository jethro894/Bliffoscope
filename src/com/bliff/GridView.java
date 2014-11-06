package com.bliff;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

/* generates a GUI to present the comparing result
 * it consists of two main panels: the GridPanel and the ButtonPanel
 * Grid panel reincarnates the data file, and draws the possible targets on data
 * Button panel takes the input of threshold value, and updates the GridPanel
 */

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
}
