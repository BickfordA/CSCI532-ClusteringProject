package spectralClustering;

import java.util.ArrayList;

import spectralClustering.inputOutput.BaseGraph;
import spectralClustering.inputOutput.PrintTimes;
import spectralClustering.mst.PrimsMST;
import affinityMatrix.AffinityMatrix;
import affinityMatrix.Laplacian;
import Jama.*;
import spectralClustering.kMeans.KMeans;



public class Main {
    public static void main(String[] args){
    	PrintTimes printer = new PrintTimes();
    	
    	for(int j = 0; j < 10; j ++){
    		
	    	ArrayList<Double> times = new ArrayList<Double>();
	    	ArrayList<String> titles = new ArrayList<String>();
	    	
	    	double startTime = System.currentTimeMillis();
	    	//read in dataset 
	    	BaseGraph rawGraph = new BaseGraph();
	    	//i think this creates a connected graph?
	    	//creating a planar graph might speed things up later on?
	        times.add(System.currentTimeMillis()-startTime);
	        titles.add("build graph");
	    	
	    	//create MST
	    	// find MST using Prim's
	        System.out.println("Finding MST");
	        PrimsMST primsMST =  new PrimsMST(rawGraph.getGraph());
	        
	        times.add(times.get(times.size()-1)-startTime);
	        titles.add("create the MST");
	    	//find relative distances
	        
	    	
	    	//construct affinity matrix
	        
	        System.out.println("Finding affinity matrix");
	        AffinityMatrix am = new AffinityMatrix(primsMST);
	        
	        times.add(times.get(times.size()-1)-startTime);
	        titles.add("find the affinity matrix");
	        
	        //construct laplacian
	        Matrix laplacian = am.getUnnormLaplacian();
	        Matrix U = laplacian.eig().getV();
	        
	        times.add(times.get(times.size()-1)-startTime);
	        titles.add("laplacian");
	        
	        //Laplacian lm = new Laplacian(am);
	        
	        //DataClusterer dc = new DataClusterer()
	        
	        
	        // find eigenvectors associated with k smallest positve eigenvalues;
	        int k = 2;
	        double[][] Uarray = U.getArray();
	        double[][] Uarrayk = new double[Uarray.length][k];
	        for(int i = 0; i < Uarray.length; i++) {
	            System.arraycopy(Uarray[i], 0, Uarrayk[i], 0, k);
	        }
	        
	        times.add(times.get(times.size()-1)-startTime);
	        titles.add("find eigin vectors");
	        
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
	        times.add(times.get(times.size()-1)-startTime);
	        titles.add("cluster analysis");
	        
	        TunableParameters params = TunableParameters.getInstance();
	        printer.print(times , "results" , titles);
    	}
    	
    }

}
