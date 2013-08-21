/**
 * @author Jofry HS
 * @created_on Aug 21, 2013
 * @package edu.swin.JSim.ui
 * @description Where the simulation is being drawn. Inherit from JPanel.
 */
package edu.swin.JSim.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JPanel;

import edu.swin.JSim.core.NetworkNode;
import edu.swin.JSim.core.NodeLink;
import edu.swin.JSim.core.ServerNode;
import edu.swin.JSim.core.SimulationManager;

/**
 * @author Asus
 *
 */
public class SimulationCanvas extends JPanel {
	private final static int SCALE_SIZE = 500; // Size down the nodes based on the througput
	
	private int scale = 10; // Default scaling
	private int offsetX = 0;
	private int offsetY = 0;
	private SimulationManager sm;
	
	public SimulationCanvas(SimulationManager sm) {
		super();
		
		this.sm = sm;
	}
	
	private void rescale() {
		offsetX = (int) Math.round(sm.getMinLon() * scale) - 50;
		offsetY = (int) Math.round(sm.getMinLat() * scale) - 50;

        System.out.println("Screensize: " + getSize().height + "\nOffset: " + offsetX + "," + offsetY);
	}
	
	private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(ColourPalette.c_blue());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,    RenderingHints.VALUE_ANTIALIAS_ON);

        Dimension size = getSize();
        Insets insets = getInsets();

        // Height and width where drawable should appear
        int w = size.width - insets.left - insets.right;
        int h = size.height - insets.top - insets.bottom;
        
        this.rescale();
        
        // Draw the links
        for(NodeLink l : sm.getNodeLinks()) {
        	drawNodeLink(g2d, l);
        }
        
        // Draw all the nodes on canvas
        for(NetworkNode n : sm.getNodes()) {
        	// Only for testing, server node always at the center
        	if (!(n instanceof ServerNode))
        	{
        		drawMobileNode(g2d, n);
        	}
        	else
        	{        		
        		drawServerNode(g2d, n);
        	}
        }
    }
	
	// Draw the links between nodes
	private void drawNodeLink(Graphics2D g2d, NodeLink l) {
		Point p1 = getScreenPosition(l.getN1());
		Point p2 = getScreenPosition(l.getN2());
		
		g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
		
	}

	// Draw a node that represent server
	// For now only draw it in the center (no actual location registered for the server)
	private void drawServerNode(Graphics2D g2d, NetworkNode n) {
		Point pos = getScreenPosition(n);
		g2d.setColor(ColourPalette.c_green());
		
		int size = getNodeSize(n);
		g2d.fillOval(pos.x - (size/2) , pos.y - (size/2), size, size);
		
		// Switch back to default colour
		g2d.setColor(ColourPalette.c_blue());
		
		System.out.println("Orig Lon/Lat: " + n.getLon() + "," + n.getLat());
		System.out.println("Draw node " + n.getName() + " (" + pos.x + "," + pos.y + ") ");
	}

	// Draw a node that represent mobile devices
	private void drawMobileNode(Graphics2D g2d, NetworkNode n) {
		Point pos = getScreenPosition(n);
		int size = getNodeSize(n);
		
		g2d.fillOval(pos.x - (size/2) , pos.y - (size/2), size, size);
		
		System.out.println("Orig Lon/Lat: " + n.getLon() + "," + n.getLat());
		System.out.println("Draw node " + n.getName() + " (" + pos.x + "," + pos.y + ") ");
	}
	
	// Retrieve the position already scaled to the screen based on the offset
	private Point getScreenPosition(NetworkNode n) {
		int nodeX = ((int) Math.round(n.getLon() * scale)) - offsetX;
		int nodeY = ((int) Math.round(n.getLat() * scale)) - offsetY;
		
		return new Point(nodeX, nodeY);
	}
	
	// Retrieve the size of the node to be drawn 
	private int getNodeSize(NetworkNode n) {
		 return n.getDownStream() / SCALE_SIZE * scale;
	}

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }
}
