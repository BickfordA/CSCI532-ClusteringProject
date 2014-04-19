package spectralClustering;

import java.util.ArrayList;

import spectralClustering.inputOutput.BaseGraph;
import spectralClustering.inputOutput.CSVReader;
import spectralClustering.inputOutput.PrintTimes;
import spectralClustering.mst.PrimsMST;
import affinityMatrix.AffinityMatrix;
import Jama.*;
import spectralClustering.inputOutput.PrintClusters;
import spectralClustering.kMeans.KMeans;



public class Main {
    public static void main(String[] args){
    	PrintTimes printer = new PrintTimes();
        PrintClusters cprinter = new PrintClusters();
    	TunableParameters params = TunableParameters.getInstance();
        int runs = 10;
        
        int[][] allClusters = new int[runs][params.getDataSetSize()];
        
    	for(int j = 0; j < runs; j ++){
    		System.out.println("Run: " + j);
                
	    	ArrayList<Double> times = new ArrayList<Double>();
	    	ArrayList<String> titles = new ArrayList<String>();
	    	
	    	double startTime = System.currentTimeMillis();
	    	//read in dataset 
	    	//BaseGraph rawGraph = new BaseGraph();
	    	CSVReader rawGraph = new  CSVReader();
	    	//i think this creates a connected graph?
	    	//creating a planar graph might speed things up later on?
	        times.add(System.currentTimeMillis()-startTime);
	        titles.add("build graph");
	    	
	    	//create MST
	    	// find MST using Prim's
	        System.out.println("Finding MST");
	        PrimsMST primsMST =  new PrimsMST(rawGraph.getGraph());
	        
	        times.add(System.currentTimeMillis()-startTime);
	        titles.add("create the MST");
	    	//find relative distances
	        
	    	
	    	//construct affinity matrix
	        
	        System.out.println("Finding affinity matrix");
	        AffinityMatrix am = new AffinityMatrix(primsMST);
	        
	        times.add(System.currentTimeMillis()-startTime);
	        titles.add("find the affinity matrix");
	        
	        //construct laplacian
	        Matrix laplacian = am.getUnnormLaplacian();
	        Matrix U = laplacian.eig().getV();
	        
	        //times.add(System.currentTimeMillis()-startTime);
	        //titles.add("laplacian");
	        
	        //Laplacian lm = new Laplacian(am);
	        
	        //DataClusterer dc = new DataClusterer()
	        
	        
	        // find eigenvectors associated with k smallest positve eigenvalues;
	        int k = params.getNumberOfClusters();
	        double[][] Uarray = U.getArray();
	        double[][] Uarrayk = new double[Uarray.length][k];
	        for(int i = 0; i < Uarray.length; i++) {
	            System.arraycopy(Uarray[i], 0, Uarrayk[i], 0, k);
	        }
	        
	        times.add(System.currentTimeMillis()-startTime);
	        titles.add("find eigen vectors");
	        
	        //perform cluster evaluation
	        KMeans km = new KMeans(Uarrayk,k);
	        int[] clusters = km.calcClusters();
	        times.add(System.currentTimeMillis()-startTime);
	        titles.add("cluster analysis");
                
                //print clusters
	        int count = 0;
	        for(int i = 0; i < clusters.length; i++){
                    allClusters[j][i] = clusters[i];
	            //System.out.println((i+1) + ":" + clusters[i]);
	            if(clusters[i] == 1) count++;
	        }
	        System.out.println();
	        //System.out.println(count);
	        
	        printer.print(times , "times" , titles);
    	}
        
        cprinter.print(allClusters, "clusters");
        System.out.println("done");
    	
    }

}
