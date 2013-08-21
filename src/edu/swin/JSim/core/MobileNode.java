/**
 * @author Jofry HS
 * @created_on Aug 21, 2013
 * @package edu.swin.JSim.core
 * 
 */
package edu.swin.JSim.core;

import edu.swin.JSim.core.NetworkNode;

public class MobileNode extends NetworkNode {
	
	/**
	 * Construct the MobileNode and set the correct type
	 */
	public MobileNode() {
		this.setNodeType(NetworkNodeType.MOBILE_DEVICE);	
	}

	/***
	 * Propagate to its superclass and generate random properties
	 * 
	 * Only name is necessary
	 */
	public void randomize(String name) {
		super.randomize(name);
	}
	
	/***
	 * Get helpful information
	 */
	public String getInfo() {
		String info = super.getInfo();
		info += this.getNodeType().toString() + "\n";
		
		return info;
	}
}
