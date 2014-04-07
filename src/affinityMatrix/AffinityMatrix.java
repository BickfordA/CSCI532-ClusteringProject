package affinityMatrix;

import java.util.ArrayList;
import java.util.Collection;
import spectralClustering.mst.PrimsMST;

import spectralClustering.TunableParameters;
import spectralClustering.data.BCEdge;
import spectralClustering.data.BCNode;
import edu.uci.ics.jung.graph.AbstractGraph;

public class AffinityMatrix {
    static int[][] _nMatrix; //Association count
	static double[][] _affinityMatrix;
	
	public AffinityMatrix(PrimsMST minSpanningTree){
		TunableParameters parameters = TunableParameters.getInstance();
		
		//initialize the matrix
		_affinityMatrix = new double[parameters.getDataSetSize()][parameters.getDataSetSize()];
                
        _nMatrix = new int[parameters.getDataSetSize()][parameters.getDataSetSize()];
        
        for(int i = 0; i < parameters.getM(); i++){
            System.out.println("Round: "+ i);
            ConsensusClusteringRound(minSpanningTree.getMST());
        }
		
	}
	
	private void ConsensusClusteringRound(AbstractGraph<BCNode,BCEdge> minSpanningTree){
		ArrayList<BCNode> startCluster = new ArrayList<BCNode>();
		startCluster.addAll(minSpanningTree.getVertices());
		
		//select two different points at random
		int pointAIdx = 0;
		int pointBIdx = 0;
		
			


		
		
		//two clusters
		pointAIdx = (int)( Math.random()*startCluster.size());
		ArrayList<BCNode> clusterA = new ArrayList<BCNode>();
		clusterA.add(startCluster.remove(pointAIdx));
		
		pointBIdx = (int)( Math.random()*startCluster.size());
		ArrayList<BCNode> clusterB = new ArrayList<BCNode>();
		clusterB.add(startCluster.remove(pointBIdx));
		
		//grow two subgraphs by adding minimal edges until they meet
		boolean clustersTouch = false;
		
		while(!clustersTouch){
			//find the closest next point for each cluster
			
			BCNode aNewPoint = null;
			double aNewDist = Double.POSITIVE_INFINITY;
			//for all vertices that share an edge
			for(BCNode point: clusterA) {
				//update smallest if smaller
				double currentWeight = 0.0;
				Collection<BCEdge> pointEdges = minSpanningTree.getOutEdges(point);
				for(BCEdge edge: pointEdges){
					currentWeight = edge.getWeight();
					if(currentWeight < aNewDist){
						//if not already in cluster
						if(!clusterA.contains(minSpanningTree.getOpposite(point , edge))){
							aNewDist = currentWeight;
							aNewPoint = minSpanningTree.getOpposite(point , edge);
						}
					}
				}
			}
			
			BCNode bNewPoint = null;
			double bNewDist = Double.POSITIVE_INFINITY;
			//for all vertices that share an edge
			for(BCNode point: clusterB) {
				//update smallest if smaller
				double currentWeight = 0.0;
				Collection<BCEdge> pointEdges = minSpanningTree.getOutEdges(point);
				for(BCEdge edge: pointEdges){
					currentWeight = edge.getWeight();
					if(currentWeight < bNewDist){
						//if not already in cluster
						if(!clusterB.contains(minSpanningTree.getOpposite(point , edge))){
							bNewDist = currentWeight;
							bNewPoint = minSpanningTree.getOpposite(point , edge);
						}
					}
				}
			}
                        
                        
			//select the smaller of two points to add
			//BCNode selection = null;
			if(bNewDist < aNewDist){
                //check if clusters touch (if point is in other cluster)
                if( clusterA.contains(bNewPoint) ) {
                        clustersTouch = true;
                //if not add the point to the respective cluster
                } else {
                		startCluster.remove(bNewPoint);
                        clusterB.add(bNewPoint);
                }
			} else {
	            //check if clusters touch (if point is in other cluster)
	            if( clusterB.contains(aNewPoint) ) {
	                    clustersTouch = true;
	            //if not add the point to the respective cluster
	            } else {
	            	startCluster.remove(aNewPoint);
	                    clusterA.add(aNewPoint);
	            }
			}
		}
		
        //update similarity for each point based on its partition
        for( BCNode i : clusterA ){
            //clusterA.remove(i);
            for( BCNode j : clusterA ) {
                _nMatrix[i.getIndex()][j.getIndex()]++;
               // _nMatrix[j.getIndex()][i.getIndex()]++;
            }
        }

        for( BCNode i : clusterB ){
            //clusterB.remove(i);
            for( BCNode j : clusterB ) {
                _nMatrix[i.getIndex()][j.getIndex()]++;
                //_nMatrix[j.getIndex()][i.getIndex()]++;
            }
        }
		
	}


}
