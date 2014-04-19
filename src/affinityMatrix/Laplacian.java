//package affinityMatrix;
//
//import java.util.ArrayList;
//
//import Jama.*;
//import spectralClustering.TunableParameters;
//
//public class Laplacian {
//	int _dataSize;
//	Matrix _eiginVectors;
//	double _eiginValues[];
//	double[][] _normalizedLargestLaplacian;
//	
//	
//
//	public Laplacian(AffinityMatrix  input) {
//		TunableParameters params = TunableParameters.getInstance();
//		_dataSize = params.getDataSetSize();
//		int numberOfEV = params.getNumberOfEVectors();
//
//		
//		Matrix diagonal = input.getDiagonalMatrix();
//		Matrix affinity = input.getAffinityMatrix();
//		
//		double[][] laplacian = new double[_dataSize][_dataSize];
//		
//		/**
//		 * L = D^(-1/2) * A * D^(-1/2)
//		 */
//		
//		
//		Matrix laplacianMatrix = invSqrtSym(diagonal).times(affinity).times(invSqrtSym(diagonal)); //new Matrix(laplacian);
//		
//		EigenvalueDecomposition eDecomp = laplacianMatrix.eig();
//		_eiginVectors = eDecomp.getV();
//		_eiginValues = eDecomp.getRealEigenvalues();
//		
//		
//		_normalizedLargestLaplacian = normalizeMatrix(transpose(largestEigenVectors(numberOfEV)));
//		
//	}
//	
//	public double[][] getPoints(){
//		return _normalizedLargestLaplacian;
//	}
//    /**
//     * Should be included in jama?
//     * 
//     * pulled from:
//     * http://www.openimaj.org/apidocs/src-html/org/openimaj/math/matrix/MatrixUtils.html
//     * 
//     * additional info:
//     * http://www.openimaj.org/openimaj-core-libs/core-math/apidocs/org/openimaj/math/matrix/MatrixUtils.html#invSqrtSym(Jama.Matrix)
//     * 
//     * Compute the inverse square root, X, of the symmetric matrix A; A^-(1/2)
//     * 
//     * @param matrix
//     *            the symmetric matrix
//     * @return the inverse sqrt of the matrix
//     */
//    public static Matrix invSqrtSym(Matrix matrix) {
//            // A = V*D*V'
//            final EigenvalueDecomposition evd = matrix.eig();
//            final Matrix v = evd.getV();
//            final Matrix d = evd.getD();
//
//            // sqrt of cells of D and store in-place
//            for (int r = 0; r < d.getRowDimension(); r++) {
//                for (int c = 0; c < d.getColumnDimension(); c++) {
//                       if (d.get(r, c) > 0)
//                                d.set(r, c, 1 / Math.sqrt(d.get(r, c)));
//               }
//        }
//
//        return v.times(d).times(v.transpose());
//	}
//	
//    
//    
//	public double[][] largestEigenVectors(int numberOfEiginVectors ){
//		double[][] eiginVectors = _eiginVectors.getArray();
//		
//		ArrayList<Double> topEiginVals = new ArrayList<Double>();
//		ArrayList<double[]> topEVectors = new ArrayList<double[]>();
//		
//		for(int i = 0; i < numberOfEiginVectors; i ++){
//			double topValue = Double.NEGATIVE_INFINITY;
//			double[] topVector = null;
//			for(int j = 0 ; j < _eiginValues.length; i++){
//				if(_eiginValues[j] > topValue){
//					if(!topEiginVals.contains(_eiginValues[j])){
//						topValue = _eiginValues[j];
//						topVector = eiginVectors[j];
//					}
//				}
//				
//			}
//			if (topVector != null){
//				topEiginVals.add(topValue);
//				topEVectors.add(topVector);
//			}
//			
//		}
//		
//		return arrayListToDoubleArray(topEVectors);
//		
//	}
//	
//	private double[][] normalizeMatrix(double[][] input){
//		double[][] normalized = new double[input.length][input[0].length];
//		for(int i = 0; i < input.length; i ++){
//			double jSum = 0 ; 
//			for(int j = 0 ; j < input.length;j ++){
//				jSum += (input[i][j] * input[i][j]);
//			}
//			jSum = Math.sqrt(jSum);
//			for(int j = 0 ; j < input.length;j ++){
//				normalized[i][j] = input[i][j]/jSum;
//			}
//		}
//		
//		return normalized;
//	}
//	private double[][] transpose(double[][] input){
//		double[][] output = new double[input[0].length][input.length];
//		
//		for(int i = 0; i < input.length; i ++){
//			for(int j = 0; j < input.length; j ++){
//				output[j][i] = input[i][j];
//			}
//		}
//		
//		
//		return output;
//	}
//	private double[][] arrayListToDoubleArray(ArrayList<double[]> input){
//		double[][] output = new double[input.size()][input.get(0).length];
//		
//		//shallow copy
//		for(int i = 0 ; i < input.size(); i++){
//			output[i] = input.get(i);
//		}
//		
//		return output;
//	}
//
//}
