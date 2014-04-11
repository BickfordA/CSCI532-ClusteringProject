package affinityMatrix;

import java.util.ArrayList;
import java.util.Collection;

import spectralClustering.data.BCEdge;
import spectralClustering.data.BCNode;
import edu.uci.ics.jung.graph.AbstractGraph;

public class consensusClusteringRoundThread implements Runnable {
	static int[][] _threadMatrix;
	static int _rounds; 
	AbstractGraph<BCNode,BCEdge> _minSpanningTree;

	consensusClusteringRoundThread(AbstractGraph<BCNode,BCEdge> minSpanningTree, int rounds, int[][] matrix){
		_minSpanningTree = minSpanningTree;
		_rounds = rounds;
		_threadMatrix = matrix;
	}

	
	public void run() {
		
		for(int i = 0; i < _rounds; i ++){
			ClusterRound(_minSpanningTree);
		}
		
	}
	
	
	private void ClusterRound(AbstractGraph<BCNode,BCEdge> minSpanningTree){
		ArrayList<BCNode> startCluster = new ArrayList<BCNode>();
		startCluster.addAll(minSpanningTree.getVertices());
		
		//select two different points at random
		//int pointAIdx = 0;
		//int pointBIdx = 0;
		
		//two clusters
		int pointAIdx = (int)( Math.random()*startCluster.size());
		ArrayList<BCNode> clusterA = new ArrayList<BCNode>();
		clusterA.add(startCluster.remove(pointAIdx));
		
		int pointBIdx = (int)( Math.random()*startCluster.size());
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
            for( BCNode j : clusterA ) {
                if( i.getIndex() != j.getIndex()){
                	_threadMatrix[i.getIndex()][j.getIndex()]++;
                }
            }
        }

        for( BCNode i : clusterB ){
            for( BCNode j : clusterB ) {
                if( i.getIndex() != j.getIndex()){
                	_threadMatrix[i.getIndex()][j.getIndex()]++;
                }
            }
        }
	}


}
