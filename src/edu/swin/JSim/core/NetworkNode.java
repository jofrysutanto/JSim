/**
 * @author Jofry HS
 * @created_on Aug 21, 2013
 * @package edu.swin.JSim.core
 * 
 * @description Represent the node sitting in the network
 */
package edu.swin.JSim.core;

import edu.swin.JSwin.util.Coord;

public abstract class NetworkNode extends Node {
	private Coord coord = null;
	private NetworkNodeType nodeType; // Type of the node
	private int upStream, downStream; // Throughput for each direction (kilobits/sec)
	
	// The following are longitude and latitude range within Australia
	public final static double MIN_LAT = -38.385804;
	public final static double MAX_LAT = -22.766051;
	public final static double MIN_LON = 115.664063;
	public final static double MAX_LON = 146.975098;
	
	public NetworkNode() {
		super();
		this.coord = new Coord();
		this.coord.lat = 0;
		this.coord.lon = 0;
		upStream = downStream = 0;
	}
	
	
	public void setCoord(double lat, double lon) {
		coord.lat = lat;
		coord.lon = lon;
	}
	
	public void setCoordLat(double lat) {
		coord.lat = lat;
	}
	
	public void setCoordLon(double lon) {
		coord.lon = lon;
	}
	
	public double getLon() {
		return coord.lon;
	}
	
	public double getLat() {
		return coord.lat;
	}

	public NetworkNodeType getNodeType() {
		return nodeType;
	}

	public void setNodeType(NetworkNodeType nodeType) {
		this.nodeType = nodeType;
	}
	
	
	public int getUpStream() {
		return upStream;
	}


	public void setUpStream(int upStream) {
		this.upStream = upStream;
	}


	public int getDownStream() {
		return downStream;
	}


	public void setDownStream(int downStream) {
		this.downStream = downStream;
	}


	public void randomize() {
		super.randomize();
		
		// Random longitudate latitude
		this.setCoordLon(Math.random() * (MobileNode.MAX_LON - MobileNode.MIN_LON) + MobileNode.MIN_LON);
		this.setCoordLat(Math.random() * (MobileNode.MAX_LAT - MobileNode.MIN_LAT) + MobileNode.MIN_LAT);
		
		// Random throughput depending on the type
		switch(this.nodeType) {
			case MOBILE_DEVICE:
				downStream = 800;
				upStream = 200;
				break;
			case SERVER:
				downStream = 5000;
				upStream = 5000;
				break;
			default:
				break;
		}
	}

	public String getInfo() {
		String info = super.getInfo();
		
		info += this.getNodeType().toString() + "\n";
		info += "Location (Lat,Lon): " + this.getLat() + "," + this.getLon() + "\n";
		
		
		return info;
	}
	
}
