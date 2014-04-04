package spectralClustering;

import spectralClustering.inputOutput.BaseGraph;
import spectralClustering.mst.PrimsMST;




public class Main {
    public static void main(String[] args){
    	//read in dataset 
    	BaseGraph rawGraph = new BaseGraph();
    	
    	//create MST
    	// find MST using Prim's
        System.out.println("Finding MST");
        
        PrimsMST primsMST =  new PrimsMST(rawGraph.getGraph());
        
        System.out.println("done");
    	
    	//find relative distances
        
    	
    	//construct similarity matrix
        
    	
    	//assign clusters based on similarity matrix
        
    	
    	//perform cluster evaluation
        
    	
    	
        
        
        
        

        
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

}
