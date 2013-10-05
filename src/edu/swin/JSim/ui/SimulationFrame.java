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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import edu.swin.JSim.core.MobileNode;
import edu.swin.JSim.core.NetworkNode;
import edu.swin.JSim.core.Node;
import edu.swin.JSim.core.NodeLink;
import edu.swin.JSim.core.ServerNode;
import edu.swin.JSim.core.SimulationManager;
import edu.swin.JSim.ui.visual.BalloonVisual;
import edu.swin.JSwin.util.MessageConsole;

public class SimulationFrame extends JFrame {
	private Container contentPanel = null;
	private SimulationManager sm;
	
	private boolean useMessageConsole = true;
	
	public SimulationFrame(SimulationManager sm) {
		super();
		
		// SimulationManager contains all the nodes to draw
		this.sm = sm;
		
		this.setTitle("Simulation");
		this.setSize( 500,500 );
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		
		contentPanel = this.getContentPane();
		this.initUI();
		
	}
	
	private void initUI() {
		// Main Content
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setSize(new Dimension(this.getWidth(), this.getHeight()));
		int contentPanelX = contentPanel.getWidth();
		int contentPanelY = contentPanel.getHeight();
		
		// Drawable area
		/*
		SimulationCanvas canvas = new SimulationCanvas(this.sm);
		canvas.setPreferredSize(new Dimension(contentPanelX, (int) Math.round(0.7 * contentPanelY )));*/
		BalloonVisual bv = new BalloonVisual(sm);
		contentPanel.add(bv);
		
		
		// Are we using emssage console?
		if (useMessageConsole) {
			// Console area
			JTextArea consoleTextArea = new JTextArea();
			JScrollPane consoleScrollable = new JScrollPane(consoleTextArea);
			consoleScrollable.setPreferredSize(new Dimension(contentPanelX, (int) Math.round(0.3 * contentPanelY )));
			contentPanel.add( consoleScrollable );
			MessageConsole mc = new MessageConsole(consoleTextArea);
			mc.redirectOut(null, System.out);
			mc.redirectErr(Color.RED, null);
			mc.setMessageLines(100);			
		}
		
		// Pack the main content panel to resize components
		this.pack();
	}
	
	public static void main(String [] args)
	{
		SimulationManager sm = new SimulationManager();
		
		NetworkNode server = new ServerNode("S-1");
		server.setUpStream(2000); server.setDownStream(2000); // Set the default throughput
		// CONSTANT: Melbourne City
		// TODO: Dynamic
		server.setCoord(-37.8136, 144.9631 );
		
		for(int i = 0; i < 5; i++)
		{
			MobileNode mn = new MobileNode("MN-" + i);
			mn.randomize();
			
			sm.getNodeLinks().add(new NodeLink(mn, server));
			sm.getNodes().add(mn);
		}
		
		sm.getNodes().add((NetworkNode) server);
		
		// Set the dimension/boundaries
		sm.updateDimension();
		
		// Make the frame and draw simulation
		SimulationFrame sim = new SimulationFrame(sm);
		sim.setVisible(true);
				
		System.out.println("--- Links ---");
		for(NodeLink n: sm.getNodeLinks()) {
			System.out.println(n.getInfo());
		}	
		
		System.out.println("--- Nodes ---");
		for(Node n : sm.getNodes()) {
			System.out.println(n.getInfo());
		}
	}
}
