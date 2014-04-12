package spectralClustering;

import spectralClustering.inputOutput.BaseGraph;
import spectralClustering.mst.PrimsMST;
import affinityMatrix.AffinityMatrix;
import Jama.*;
import spectralClustering.kMeans.KMeans;



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
        
        //assign clusters based on affinity matrix
        Matrix laplacian = am.getUnnormLaplacian();
        Matrix U = laplacian.eig().getV();
        
        // find eigenvectors associated with k smallest positve eigenvalues;
        int k = 2;
        double[][] Uarray = U.getArray();
        double[][] Uarrayk = new double[Uarray.length][k];
        for(int i = 0; i < Uarray.length; i++) {
            System.arraycopy(Uarray[i], 0, Uarrayk[i], 0, k);
        }
        
        //perform cluster evaluation
        KMeans km = new KMeans(Uarrayk,k);
        int[] clusters = km.calcClusters();
        int count = 0;
        for(int i = 0; i< clusters.length; i++){
            System.out.println((i+1) + ":" + clusters[i]);
            if(clusters[i] == 1) count++;
        }
        System.out.println();
        System.out.println(count);
        System.out.println("done");
        
        /*
    	double[][] A = {{2,1,3},
                        {10,11,12},
                        {3,1,2},
                        {1,1,1},
                        {2,2,2},
                        {10,11,12},
                        {11,9,11},
                        {9,10,13},
                        {-1,-1,-1}
                        };
        KMeans km = new KMeans(A,2);
        int[] clusters = km.calcClusters();
    	for(int i = 0; i < clusters.length; i++) {
            System.out.println( clusters[i] );
        }
        */
    	
    }

}
