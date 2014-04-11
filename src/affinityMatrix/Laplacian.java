package affinityMatrix;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;
import Jama.*;
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
		
		
		Matrix laplacianMatrix = invSqrtSym(diagonal).times(affinity).times(invSqrtSym(diagonal)); //new Matrix(laplacian);
		
		
		Matrix eiginValues = laplacianMatrix.eig().getV();
		
		
		
	}
    /**
     * Should be included in jama?
     * 
     * pulled from:
     * http://www.openimaj.org/apidocs/src-html/org/openimaj/math/matrix/MatrixUtils.html
     * 
     * additional info:
     * http://www.openimaj.org/openimaj-core-libs/core-math/apidocs/org/openimaj/math/matrix/MatrixUtils.html#invSqrtSym(Jama.Matrix)
     * 
     * Compute the inverse square root, X, of the symmetric matrix A; A^-(1/2)
     * 
     * @param matrix
     *            the symmetric matrix
     * @return the inverse sqrt of the matrix
     */
    public static Matrix invSqrtSym(Matrix matrix) {
            // A = V*D*V'
            final EigenvalueDecomposition evd = matrix.eig();
            final Matrix v = evd.getV();
            final Matrix d = evd.getD();

            // sqrt of cells of D and store in-place
            for (int r = 0; r < d.getRowDimension(); r++) {
                for (int c = 0; c < d.getColumnDimension(); c++) {
                       if (d.get(r, c) > 0)
                                d.set(r, c, 1 / Math.sqrt(d.get(r, c)));
               }
        }

        return v.times(d).times(v.transpose());
	}
	
    
    
	public double[][] largestEigenVectors(int numberOfEiginVectors ){
		double topEVectors[][] = new double[numberOfEiginVectors][_dataSize];
		
		
		
		return topEVectors;
	}

}
