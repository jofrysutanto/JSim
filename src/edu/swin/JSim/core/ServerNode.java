/**
 * @author Jofry HS
 * @created_on Aug 21, 2013
 * @package edu.swin.JSim.core
 * 
 */
package edu.swin.JSim.core;

/**
 * @author Asus
 *
 */
public class ServerNode extends NetworkNode {

	/**
	 * 
	 */
	public ServerNode(String name) {
		// TODO Auto-generated constructor stub
		super();
		this.setNodeType(NetworkNodeType.SERVER);
		
		this.setName(name);
	}

}
