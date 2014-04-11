package affinityMatrix;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import spectralClustering.TunableParameters;

public class Laplacian {
	int _dataSize;

	public Laplacian(AffinityMatrix  input) {
		TunableParameters params = TunableParameters.getInstance();
		_dataSize = params.getDataSetSize();
		
		
		Matrix diagonal = input.getDiagonalMatrix();
		Matrix affinity = input.getAffinityMatrix();
		
		double[][] laplacian = new double[_dataSize][_dataSize];
		
		/**
		 * L = D^(-1/2) * A * D^(-1/2)
		 */
		
		
		
		
		Matrix laplacianMatrix = new Matrix(laplacian);
		
		
		Matrix eiginValues = laplacianMatrix.eig().getV();
		
		
		
	}
	
	public double[][] largestEigenVectors(int numberOfEiginVectors ){
		double topEVectors[][] = new double[numberOfEiginVectors][_dataSize];
		
		
		
		return topEVectors;
	}

}
