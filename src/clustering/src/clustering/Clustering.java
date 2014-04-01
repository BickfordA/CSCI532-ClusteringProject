/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clustering;

/**
 *
 * @author John
 */


import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.graph.util.*;
import java.io.FileReader;
import java.util.Scanner;
import java.util.*;
import java.util.regex.Pattern;

public class Clustering {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Import breast cancer data
        String filename = "breastcancer.txt";
        AbstractGraph<BCNode,BCEdge> compGraph = importBCData(filename);
        
        int n = compGraph.getVertexCount();
        
        System.out.println("n = " + n );
        
        System.out.println("Calculating Euclidean Distance");
        
        Collection<BCNode> nodes = compGraph.getVertices();
        Iterator<BCNode> iti = nodes.iterator();
        while( iti.hasNext() ) {
            BCNode nodei = iti.next();
            Iterator<BCNode> itj = nodes.iterator();
            while( itj.hasNext() ) {
                BCNode nodej = itj.next();
                if( compGraph.findEdge(nodei,nodej) == null) {
                    BCEdge e = new BCEdge( calcEucDistance(nodei,nodej) );
                    compGraph.addEdge(e,nodei,nodej);
                }
            }
        }
        
        // find MST using Prim's
        System.out.println("Finding MST");
        AbstractGraph<BCNode,BCEdge> primMST = prims(compGraph);
        
        System.out.println("done");
        
        /*
        // simple graph example
        Integer[] data1 = {1,2,3};
        Integer[] data2 = {4,5,6};
        
        BCNode n1 = new BCNode(1, data1);
        BCNode n2 = new BCNode(2, data2);
        
        g.addVertex(n1);
        g.addVertex(n2);
        
        Pair n1n2 = new Pair(n1,n2);
        
        BCEdge en1n2 = new BCEdge( 4.0 );
        
        g.addEdge(en1n2, n1, n2);
        
        BCEdge e = (BCEdge) g.findEdge(n2, n1);
        
        
        */
    }
    
    
    // Prims algorithm
    public static AbstractGraph prims( AbstractGraph<BCNode,BCEdge> G) {
        // initialize new MST graph
        AbstractGraph<BCNode,BCEdge> MST = new UndirectedSparseGraph();
        
        // V is the set of all vertices in G
        ArrayList<BCNode> V = new ArrayList();
        V.addAll(G.getVertices());
        Iterator<BCNode> itV = V.iterator();
        BCNode s = itV.next();
        
        // pick starting node
        MST.addVertex(s);
        V.remove(s);
        
        // repeat until V is empty
        while(!V.isEmpty()){
            // Vmst is the set of all vertices in the new MST
            ArrayList<BCNode> Vmst = new ArrayList();
            Vmst.addAll(MST.getVertices());
            Iterator<BCNode> itVmst = Vmst.iterator();
            BCNode umin = s; // temp value
            BCNode vmin = s; // temp value
            double wmin = Double.POSITIVE_INFINITY;
            // for all u in Vmst
            while(itVmst.hasNext()) {
                BCNode u = itVmst.next();
                Iterator<BCNode> itV2 = V.iterator();
                umin = u;
                // find v in V so that E(u,v) is minimal
                while(itV2.hasNext()){
                    BCNode v = itV2.next();
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
            MST.addVertex(vmin);
            // add edge from vmin to umin
            MST.addEdge(new BCEdge(wmin),vmin,umin);
        }
        
        
        return MST;
    }
    
    // calculate the Euclidean distance between two BCNodes
    public static double calcEucDistance(BCNode a, BCNode b) {
        double sumDSquared = 0.0;
        
        int n = Math.max(a.getNumAttributes(), b.getNumAttributes());
        for(int i = 0; i < n; i++) {
            Integer ai = a.getAttribute(i);
            Integer bi = b.getAttribute(i);
            if( ai == null || bi == null ) {
                sumDSquared += 0.0;
            } else {
                sumDSquared += (ai-bi)*(ai-bi);
            }
        }
        
        return Math.sqrt(sumDSquared);
    }
    
    // Parse text file and return data points as vertices in a graph
    public static AbstractGraph<BCNode,BCEdge> importBCData(String filename) {
        
        int n = 9; // number of attributes
        
        AbstractGraph<BCNode,BCEdge> g = new UndirectedSparseGraph();
        
        Pattern p = Pattern.compile( ",| |\n|\r" );
        try {
            Scanner scan = new Scanner(new FileReader(filename));
            scan.useDelimiter(p);
            while( scan.hasNextLine() ) {
                int ID = scan.nextInt();
                //System.out.println(ID);
                Integer[] attr = new Integer[n];
                for(int i = 0; i < n; i++) {
                    String s = scan.next();
                    if( s.equals("?") ){
                        attr[i] = null;
                    } else {
                        attr[i] = Integer.valueOf(s);
                    }
                    //System.out.print(attr[i] + " ");
                }
                //System.out.println();
                g.addVertex(new BCNode(ID, attr));
                if( scan.hasNextLine() ) {
                    scan.nextLine();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
        
        return g;
    }
    
}
