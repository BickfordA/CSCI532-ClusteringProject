package spectralClustering;

import java.util.ArrayList;


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
        int runs = 1;
        
        int[][] allClusters = new int[runs][params.getDataSetSize()];
        int[][] allKMClusters = new int[runs][params.getDataSetSize()];
        
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
                System.out.println("find eigen vectors");
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
	        titles.add("Finding Eigen Vectors");
	        
	        //perform cluster evaluation
	        KMeans km = new KMeans(Uarrayk,k);
	        int[] clusters = km.calcClusters();
	        times.add(System.currentTimeMillis()-startTime);
	        titles.add("cluster analysis");
                
                //print clusters
	        for(int i = 0; i < clusters.length; i++){
                    allClusters[j][i] = clusters[i];
	        }
	        
	        printer.print(times , "times" , titles);
                
                // plain ol kmeans on normalized data
                KMeans kmog = new KMeans(rawGraph.getDataAs2DArray(),k);
                int[] kMeansClusters = kmog.calcClusters();
	        for(int i = 0; i < clusters.length; i++){
                    allKMClusters[j][i] = kMeansClusters[i];
	        }
                
    	}
        // print clusters
        cprinter.print(allClusters, "clusters");
        cprinter.print(allKMClusters, "kmeans");
        
        
        System.out.println("done");
    	
    }

}
