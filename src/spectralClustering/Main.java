package spectralClustering;

import spectralClustering.inputOutput.BaseGraph;
import spectralClustering.mst.PrimsMST;
import affinityMatrix.AffinityMatrix;
import affinityMatrix.Laplacian;
import Jama.*;



public class Main {
    public static void main(String[] args){
    	//read in dataset 
    	BaseGraph rawGraph = new BaseGraph();
    	//i think this creates a connected graph?
    	//creating a planar graph might speed things up later on?
        
    	
    	//create MST
    	// find MST using Prim's
        System.out.println("Finding MST");
        PrimsMST primsMST =  new PrimsMST(rawGraph.getGraph());
    	
    	//find relative distances
        
    	
    	//construct affinity matrix
        System.out.println("Finding affinity matrix");
        AffinityMatrix am = new AffinityMatrix(primsMST);
        
        //construct laplacian
        //Matrix laplacian = am.getUnnormLaplacian();
        
        //Matrix U = laplacian.eig().getV();
        
        Laplacian lm = new Laplacian(am);
        
        
        
        System.out.println("done");
    	
    	//assign clusters based on affinity matrix
        
        
    	
    	//perform cluster evaluation
        
    	

    	
    	
    }

}
