
package spectralClustering.mst;

/**
 *
 * @author John
 */


import edu.uci.ics.jung.graph.AbstractGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import java.util.ArrayList;
import java.util.Iterator;
import spectralClustering.data.BCEdge;
import spectralClustering.data.BCNode;

public class PrimsMST {
	AbstractGraph<BCNode,BCEdge> _minimumSpanningTree;
	
	
    public PrimsMST( AbstractGraph<BCNode,BCEdge> G){
	    // initialize new MST graph
    	_minimumSpanningTree = new UndirectedSparseGraph<BCNode,BCEdge>();
	    
	    // V is the set of all vertices in G
	    ArrayList<BCNode> V = new ArrayList<BCNode>();
	    V.addAll(G.getVertices());
            
            // pick starting node
	    BCNode s = V.get(0);
	    _minimumSpanningTree.addVertex(s);
	    V.remove(s);
	    
	    // repeat until V is empty
	    while(!V.isEmpty()){
	        // Vmst is the set of all vertices in the new MST
	        ArrayList<BCNode> Vmst = new ArrayList<BCNode>();
	        Vmst.addAll(_minimumSpanningTree.getVertices());
	        BCNode umin = s; // temp value
	        BCNode vmin = s; // temp value
	        double wmin = Double.POSITIVE_INFINITY;
	        // for all u in Vmst
	        for(BCNode u : Vmst) {
	            // find v in V so that E(u,v) is minimal
	            for(BCNode v : V){
	                if(G.isNeighbor(u,v)) {
	                    BCEdge e = G.findEdge(u,v);
	                    if( e.getWeight() < wmin ) {
	                        wmin = e.getWeight();
	                        vmin = v;
	                        umin = u;
	                    }
	                }
	            }
	        }
	        // remove vmin from V
	        V.remove(vmin);
	        // add vmin to MST
	        _minimumSpanningTree.addVertex(vmin);
	        // add edge from vmin to umin
	        _minimumSpanningTree.addEdge(new BCEdge(wmin),vmin,umin);
	    }

    }
    
    
    public AbstractGraph<BCNode,BCEdge> getMST(){ return _minimumSpanningTree;}
    
  
}
