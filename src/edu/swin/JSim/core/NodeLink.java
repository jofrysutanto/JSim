/**
 * @author Jofry HS
 * @created_on Aug 21, 2013
 * @package edu.swin.JSim.core
 * @description Link between two nodes
 */
package edu.swin.JSim.core;

import edu.swin.JSim.connection.Broadband;
import edu.swin.JSim.connection.Network;

/**
 * @author Asus
 *
 */
public class NodeLink {
	private Network network;
	private NetworkNode n1, n2;
	
	public NodeLink(NetworkNode n1, NetworkNode n2)
	{
		this.n1 = n1;
		this.n2 = n2;
		
		this.randomize();
	}
	
	public Network getNetwork() {
		return network;
	}
	public void setNetwork(Network network) {
		this.network = network;
	}
	public NetworkNode getN1() {
		return n1;
	}
	public void setN1(NetworkNode n1) {
		this.n1 = n1;
	}
	public NetworkNode getN2() {
		return n2;
	}
	public void setN2(NetworkNode n2) {
		this.n2 = n2;
	}
	
	// Randomize the properties
	public void randomize() {
		this.network = new Broadband();
	}
	
	// Get useful information
	public String getInfo() {
		String info = n1.getName() + "<---->" + n2.getName() + "\n";
		info += "Network type: " + network.getClass().getSimpleName();
		
		return info;
	}
}
