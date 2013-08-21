/**
 * @author Jofry HS
 * @created_on Aug 21, 2013
 * @package edu.swin.JSim.ui
 * @description Main window where Simulation UI take place
 */
package edu.swin.JSim.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import edu.swin.JSim.core.MobileNode;
import edu.swin.JSim.core.Node;
import edu.swin.JSwin.util.MessageConsole;

public class SimulationFrame extends JFrame {
	private Container contentPanel = null;
	
	public SimulationFrame() {
		super();
		
		this.setTitle("Simulation");
		this.setSize( 500,500 );
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		
		contentPanel = this.getContentPane();
		this.initUI();
	}
	
	private void initUI() {
		// Main Content
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		int contentPanelX = contentPanel.getWidth();
		int contentPanelY = contentPanel.getHeight();
		
		// Drawable area
		SimulationCanvas canvas = new SimulationCanvas();
		canvas.setPreferredSize(new Dimension());
		contentPanel.add(canvas);
		
		// Console area
		JTextArea consoleTextArea = new JTextArea();
		consoleTextArea.setPreferredSize(new Dimension(500, 100));
		contentPanel.add( new JScrollPane( consoleTextArea ) );
		MessageConsole mc = new MessageConsole(consoleTextArea);
		mc.redirectOut();
		mc.redirectErr(Color.RED, null);
		mc.setMessageLines(100);		
	}
	
	public static void main(String [] args)
	{
		SimulationFrame sim = new SimulationFrame();
		sim.setVisible(true);
		
		Node [] nodes = new Node[5];
		
		for(int i = 0; i < nodes.length; i++)
		{
			MobileNode mn = new MobileNode();
			mn.randomize("MN-" + i);
			nodes[i] = mn;
		}
		
		for(Node n: nodes) {
			System.out.println(n.getInfo());
		}	
	}
}
