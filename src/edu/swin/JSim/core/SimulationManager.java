/**
 * @author Jofry HS
 * @created_on Aug 22, 2013
 * @package edu.swin.JSim.core
 * @description Manages and aware of all the nodes exist in the simulation
 */
package edu.swin.JSim.core;

import java.util.ArrayList;

import edu.swin.JSwin.util.Coord;

public class SimulationManager {
	private ArrayList<NetworkNode> nodes; // All the nodes exist

	private ArrayList<NodeLink> nodeLinks; // The links between the nodes
	
	// Easier to track the boundaries of simulation universe
	private double minLon, minLat;
	private double maxLon, maxLat;
	
	public SimulationManager() {
		nodes = new ArrayList<NetworkNode>();
		nodeLinks = new ArrayList<NodeLink>();
		
		minLon = minLat = maxLon = maxLat = 0;
	}
	
	public void updateDimension() {
		if (nodes.size() > 0) { // Make sure there are nodes in it
			maxLat = minLat = nodes.get(0).getLat();
			maxLon = minLon = nodes.get(0).getLon();
			
			// If only one, that is the boundary
			if (nodes.size() == 1)
			{
				return;
			}
			else
			{
				for(NetworkNode mn : nodes) {
					// IGNORE server for now
					if (!(mn instanceof ServerNode)) {
						if(mn.getLat() > maxLat) maxLat = mn.getLat();
						if(mn.getLat() < minLat) minLat = mn.getLat();
						
						if(mn.getLon() > maxLon) maxLon = mn.getLon();
						if(mn.getLon() < minLon) minLon = mn.getLon();
					}
				}
			}		
			
			System.out.println("Dimension updated: Lat/Lon " + minLat + "," + minLon);
		}
		else { // Empty nodes, no boundaries
			minLon = minLat = maxLon = maxLat = 0;
		}
	}
	

	public ArrayList<NetworkNode> getNodes() {
		return nodes;
	}
	
	public void setNodes(ArrayList<NetworkNode> nodes) {
		this.nodes = nodes;
	}
	public ArrayList<NodeLink> getNodeLinks() {
		return nodeLinks;
	}

	public void setNodeLinks(ArrayList<NodeLink> nodeLinks) {
		this.nodeLinks = nodeLinks;
	}

	public double getMinLon() {
		return minLon;
	}

	public void setMinLon(double minLon) {
		this.minLon = minLon;
	}

	public double getMinLat() {
		return minLat;
	}

	public void setMinLat(double minLat) {
		this.minLat = minLat;
	}

	public double getMaxLon() {
		return maxLon;
	}

	public void setMaxLon(double maxLon) {
		this.maxLon = maxLon;
	}

	public double getMaxLat() {
		return maxLat;
	}

	public void setMaxLat(double maxLat) {
		this.maxLat = maxLat;
	}
 }
