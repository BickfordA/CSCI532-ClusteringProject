package spectralClustering.kMeans;

import spectralClustering.TunableParameters;

public class DataClusterer {
	int[] _clusterAssignment;

	public DataClusterer(double[][] data , double[][] lapPoints) {
		TunableParameters params = TunableParameters.getInstance();
		int numClusters = params.getNumberOfClusters();
		
		_clusterAssignment = new int[data.length];
		
		KMeans evClusters =  new KMeans(lapPoints, numClusters);
		//double[][] clusters = evClusters.getCenters();
		//int[] assignments = evClusters.getAssignments();
		
		/*
		 * Each point s_i is assigned to cluster j  if and only if row i of the matrix Y (laPoints)
		 * was assigned to cluster j;
		 */
		
		for(int i = 0; i < data.length; i ++){
		//	_clusterAssignment[i] = assignments[i];
		}
		
	}
	
	public int[]dataAssignments(){
		return _clusterAssignment; 
	}

}
