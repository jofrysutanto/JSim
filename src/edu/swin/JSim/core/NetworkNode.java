/**
 * @author Jofry HS
 * @created_on Aug 21, 2013
 * @package edu.swin.JSim.core
 * 
 * @description Represent the node sitting in the network
 */
package edu.swin.JSim.core;

import edu.swin.JSim.connection.Broadband;
import edu.swin.JSim.connection.Network;

public abstract class NetworkNode extends Node {
	private NetworkNodeType nodeType; // Type of the node
	private Network network;

	public NetworkNodeType getNodeType() {
		return nodeType;
	}

	public void setNodeType(NetworkNodeType nodeType) {
		this.nodeType = nodeType;
	}

	public Network getNetwork() {
		return network;
	}

	public void setNetwork(Network network) {
		this.network = network;
	}
	
	public void randomize(String name) {
		super.randomize(name);
		
		// Randomize Network Type
		this.setNetwork(new Broadband());
	}

	public String getInfo() {
		String info = super.getInfo();
		info += "Network type: " + this.getNetwork().getClass().getSimpleName() + "\n";
		
		return info;
	}
	
}
