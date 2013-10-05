/**
 * @author Jofry HS
 * @created_on Aug 21, 2013
 * @package edu.swin.JSim.core
 * 
 * @description Represent basic entity for the simulation
 */
package edu.swin.JSim.core;

import java.util.UUID;

public abstract class Node {
	private String name;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void randomize() {
		//EMPTY
	}

	public String getInfo() {
		String info = "Node name: " + name + "\n";
		
		return info;
	}
}
