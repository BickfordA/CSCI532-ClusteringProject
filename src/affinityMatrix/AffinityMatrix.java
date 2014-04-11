package affinityMatrix;

import java.util.ArrayList;
import java.util.Collection;
import spectralClustering.mst.PrimsMST;
import Jama.*;
import spectralClustering.TunableParameters;
import spectralClustering.data.BCEdge;
import spectralClustering.data.BCNode;
import edu.uci.ics.jung.graph.AbstractGraph;

public class AffinityMatrix {
        static int[][] _nMatrix; //Association count
	static double[][] _affinityMatrix;
        static double[][] _diagonalMatrix;
	
	public AffinityMatrix(PrimsMST minSpanningTree){
		TunableParameters parameters = TunableParameters.getInstance();
		int n = parameters.getDataSetSize();
                double sigma = parameters.getSigma();
		//initialize the matrix
		_affinityMatrix = new double[n][n];
                
                // i know it's ridiculous to store n values in n by n matrix
                _diagonalMatrix = new double[n][n];
                
                _nMatrix = new int[n][n];

                 int m = parameters.getM();
	   			 int numberOfThreads = 4;

	    
	    		ClusterThreadHandler(minSpanningTree.getMST() , m , numberOfThreads );
	
	    

                
                // calculate affinity matrix
                for(int i = 0; i < n; i++ ) {
                    for(int j = 0; j < n; j++ ) {
                        _affinityMatrix[i][j] = Math.exp(-(1-((double)_nMatrix[i][j]/m))/sigma);
                    }
                }
                
                // calculate diagonal matrix
                for(int i = 0; i < n; i++ ) {
                    _diagonalMatrix[i][i] = 0.0;
                    for(int j = 0; j < n; j++ ) {
                        if( j != i) _diagonalMatrix[i][j] = 0.0;
                        _diagonalMatrix[i][i] += _affinityMatrix[i][j];
                    }
                }
                
	}
        
        public Matrix getUnnormLaplacian() {
            Matrix A = new Matrix(_affinityMatrix);
            Matrix D = new Matrix(_diagonalMatrix);
            return D.minus(A);
        }
        
		private int[][] ClusterThreadHandler(AbstractGraph<BCNode,BCEdge> minSpanningTree, int rounds, int threadCount){
		int[][] output = new int[_nMatrix.length][_nMatrix.length];
		
		//set these -- get value from somewhere 

		int threadReps = rounds / threadCount ;
		
		ArrayList<Thread> threads = new ArrayList<Thread>();
		ArrayList<int[][]> matricies = new ArrayList<int[][]>(); 
		
		//create and start specified number of threads
		for(int i = 0; i < threadCount; i++){
			int[][] threadMatrix = new int[_nMatrix.length][_nMatrix.length];
			matricies.add(threadMatrix);
			System.out.println("Starting consensus clustering thread :" +i);
			Thread clusterMatrixThread = new Thread(new consensusClusteringRoundThread(minSpanningTree, threadReps, threadMatrix ), Integer.toString(i));
			clusterMatrixThread.start();
			threads.add(clusterMatrixThread);
		}
		
		//wait for threads to finish
		try{
			
			for(int i = 0; i < threadCount; i ++){
				System.out.println("Waiting for thread "+i+" to finish");
				threads.get(i).join();
			}
			
		}catch (InterruptedException e) {
			System.out.println("Thread error");
			e.printStackTrace();
		}
		
		//copy in results
		for(int[][] result: matricies){
			for(int i = 0; i < result.length; i ++){
				for(int j = 0; j < result[i].length; j ++){
					_affinityMatrix[i][j] += result[i][j];
				}
			}
		}
		
		return output;
	}
	
        
        public Matrix getAffinityMatrix(){
            return new Matrix(_affinityMatrix);
        }
        
        public Matrix getDiagonalMatrix(){
            return new Matrix(_diagonalMatrix);
        }


}
