package edu.swin.JSim.ui.visual;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

import javax.swing.JApplet;

import org.apache.commons.collections15.Factory;

import edu.swin.JSim.core.NetworkNode;
import edu.swin.JSim.core.NodeLink;
import edu.swin.JSim.core.SimulationManager;
import edu.uci.ics.jung.algorithms.layout.BalloonLayout;
import edu.uci.ics.jung.graph.DelegateForest;
import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Forest;
import edu.uci.ics.jung.graph.Tree;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.transform.MutableTransformer;
import edu.uci.ics.jung.visualization.transform.MutableTransformerDecorator;

public class BalloonVisual extends JApplet {
	
	/**
     * the visual component and renderer for the graph
     */
    private VisualizationViewer<NetworkNode,NodeLink> vv;
    
    private VisualizationServer.Paintable rings;
    
    private BalloonLayout<NetworkNode,NodeLink> radialLayout;
    
    private SimulationManager sm;
    
    private Forest<NetworkNode, NodeLink> graph;
    
    public BalloonVisual(SimulationManager sm) {
    	this.sm = sm;
    	// Add the graph data into the visual
    	seed();
    	radialLayout = new BalloonLayout<NetworkNode, NodeLink>(graph);
    	radialLayout.setSize(new Dimension(900,900));
    	// Inital customisation on how to render vertices and edges
    	vv = new VisualizationViewer<NetworkNode, NodeLink>(radialLayout, new Dimension(600,600));
    	vv.setBackground(Color.white);
    	vv.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line());
    	vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
    	
    	// Adding zoom support
    	Container content = getContentPane();
    	final GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);
    	content.add(panel);

    	// Mouse & keyboard listener to interact with the graph
    	final DefaultModalGraphMouse graphMouse = new DefaultModalGraphMouse();
    	vv.setGraphMouse(graphMouse);
    	vv.addKeyListener(graphMouse.getModeKeyListener());
    	
    	// Draw circular ring around the vertices
    	rings = new Rings(radialLayout);
    	vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.LAYOUT).setToIdentity();
    	vv.addPreRenderPaintable(rings);
    	vv.repaint();
    }
    
    public void seed() {
    	graph = new DelegateForest<NetworkNode,NodeLink>();
    	
    	for(NetworkNode n: sm.getNodes()) {
    		graph.addVertex(n);
    	}
    	
    	for(NodeLink nl: sm.getNodeLinks()) {
    		graph.addEdge(nl, nl.getN1(), nl.getN2());
    	}
    	
    	/*graph.addVertex("A0");
       	graph.addEdge(1, "A0", "B0");
       	graph.addEdge(2, "A0", "B1");
       	graph.addEdge(3, "A0", "B2");
       	
       	graph.addEdge(4, "B0", "C0");
       	graph.addEdge(5, "B0", "C1");
       	graph.addEdge(6, "B0", "C2");
       	graph.addEdge(7, "B0", "C3");

       	graph.addEdge(8, "C2", "H0");
       	graph.addEdge(9, "C2", "H1");

       	graph.addEdge(10, "B1", "D0");
       	graph.addEdge(11, "B1", "D1");*/
    }
    
    class Rings implements VisualizationServer.Paintable {
    	
    	BalloonLayout<NetworkNode,NodeLink> layout;
    	
    	public Rings(BalloonLayout<NetworkNode,NodeLink> layout) {
    		this.layout = layout;
    	}
    	
		public void paint(Graphics g) {
			g.setColor(Color.gray);
		
			Graphics2D g2d = (Graphics2D)g;

			Ellipse2D ellipse = new Ellipse2D.Double();
			for(NetworkNode v : layout.getGraph().getVertices()) {
				Double radius = layout.getRadii().get(v);
				System.out.println(radius);
				if(radius == null) continue;
				Point2D p = layout.transform(v);
				ellipse.setFrame(-radius, -radius, 2*radius, 2*radius);
				AffineTransform at = AffineTransform.getTranslateInstance(p.getX(), p.getY());
				Shape shape = at.createTransformedShape(ellipse);
				
				MutableTransformer viewTransformer =
					vv.getRenderContext().getMultiLayerTransformer().getTransformer(Layer.VIEW);
				
				if(viewTransformer instanceof MutableTransformerDecorator) {
					shape = vv.getRenderContext().getMultiLayerTransformer().transform(shape);
				} else {
					shape = vv.getRenderContext().getMultiLayerTransformer().transform(Layer.LAYOUT,shape);
				}

				g2d.draw(shape);
			}
		}

		public boolean useTransform() {
			return true;
		}
    }
}
