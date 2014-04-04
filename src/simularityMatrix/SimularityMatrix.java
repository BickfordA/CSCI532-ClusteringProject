package simularityMatrix;

import java.util.ArrayList;
import java.util.Collection;

import spectralClustering.TunableParameters;
import spectralClustering.data.BCEdge;
import spectralClustering.data.BCNode;
import edu.uci.ics.jung.graph.AbstractGraph;

public class SimularityMatrix {
	static double[][] _simularityMatrix;
	
	SimularityMatrix(){
		TunableParameters parameters = TunableParameters.getInstance();
		
		//initialize the matrix
		_simularityMatrix = new double[parameters.getDataSetSize()][parameters.getDataSetSize()];
		
	}
	
	private void ConsensusClusteringRound(AbstractGraph<BCNode,BCEdge> minSpanningTree){
		ArrayList<BCNode> startCluster = new ArrayList<BCNode>();
		startCluster.addAll(minSpanningTree.getVertices());
		
		//select two different points at random
		int pointAIdx = 0;
		int pointBIdx = 0;
		
		while(pointAIdx == pointBIdx){
			pointAIdx = (int) Math.random()*_simularityMatrix.length;
			pointBIdx = (int) Math.random()*_simularityMatrix.length;
		}
		
		
		//two clusters
		ArrayList<BCNode> clusterA = new ArrayList<BCNode>();
		clusterA.add(startCluster.remove(pointAIdx));
		
		ArrayList<BCNode> clusterB = new ArrayList<BCNode>();
		clusterB.add(startCluster.remove(pointBIdx));
		
		//grow two subgraphs by adding minimal edges until they meet
		boolean clustersTouch = false;
		
		while(!clustersTouch){
			//find the closest next point for each cluster
			
			BCNode aNewPoint = null;
			double aNewDist = Double.POSITIVE_INFINITY;
			//for all vertices that share an edge
			for(BCNode point: clusterA){
				//update smallest if smaller
				double currentWeight = 0;
				Collection<BCEdge> pointEdges = minSpanningTree.getOutEdges(point);
				for(BCEdge edge: pointEdges){
					currentWeight = edge.getWeight();
					if(currentWeight < aNewDist){
						//if not already in cluster?
						aNewDist = currentWeight;
						aNewPoint = minSpanningTree.getOpposite(point , edge);
					}
				}
			}
			
			BCNode bNewPoint = null;
			double bNewDist = Double.POSITIVE_INFINITY;
			//for all vertices that share an edge
			for(BCNode point: clusterB){
				//update smallest if smaller
				double currentWeight = 0;
				Collection<BCEdge> pointEdges = minSpanningTree.getOutEdges(point);
				for(BCEdge edge: pointEdges){
					currentWeight = edge.getWeight();
					if(currentWeight < bNewDist){
						//if not already in cluster?
						bNewDist = currentWeight;
						bNewPoint = minSpanningTree.getOpposite(point , edge);
					}
				}
			}
			//select the smaller of two points to add
			BCNode selection = null;
			if(bNewDist < aNewDist){
				selection = bNewPoint;
			}else{
				selection = aNewPoint;
			}
			
			
			//check if clusters touch (if point is in other cluster)
			
			
			//if not add the point to the respective cluster
			
			
			
		}
		
		//update similarity for each point based on its partition
		
		
	}


}
