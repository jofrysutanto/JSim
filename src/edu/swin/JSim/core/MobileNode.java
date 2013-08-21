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
	public MobileNode(String name) {
		this.setNodeType(NetworkNodeType.MOBILE_DEVICE);
		
		this.setName(name);
	}

	/***
	 * Propagate to its superclass and generate random properties
	 * 
	 */
	public void randomize() {
		super.randomize();
	}
	
	/***
	 * Get helpful information
	 */
	public String getInfo() {
		String info = super.getInfo();
		
		return info;
	}
}
