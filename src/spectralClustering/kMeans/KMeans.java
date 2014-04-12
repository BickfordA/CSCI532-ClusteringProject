/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spectralClustering.kMeans;

/**
 * KMeans class
 *
 *
 * @author John
 * @version
 */

public class KMeans {
    
    private double[][] data;
    private int k;
    private int n;
    private int numAttributes;
    private double[][] _centers;
    private int[] _assignments;
    
    public KMeans(double[][] data, int k) {
        this.data = data;
        this.k = k;
        
        this.n = data.length;
        this.numAttributes = data[0].length;
        
        _assignments = calcClusters();
        
    }
    
    public double[][] getCenters(){
    	return _centers;
    }
    
    public int[] getAssignments(){
    	return _assignments;
    }
    
    public int[] calcClusters(){
        int[] clusters = new int[n];
        double[][] centers = new double[k][numAttributes];
        
        //pick k centers
        for(int i = 0; i < k; i++){
            for( int j = 0; j < numAttributes; j++){
                // first k data points for now
                centers[i][j] = data[i][j];
            }
        }
        
        int changes = 1;
        while( changes != 0 ) {
            // for each data point
            changes = 0;
            for(int i = 0; i < n; i++ ) {
                // calculate euclidean distance to each center
                double mind = Double.POSITIVE_INFINITY;
                int cprevious = clusters[i];
                for(int j = 0; j < k; j++ ) {
                    double d = eucDistance(data[i], centers[j]);
                    if( d < mind) {
                        mind = d;
                        clusters[i] = j;
                    }
                }
                // clustering changed
                if( cprevious != clusters[i] ) {
                    changes++;
                }
            }
        }
        _centers = centers;
        return clusters;
    }
    
    private double eucDistance( double[] a, double[] b) {
        
        double sumDSquared = 0.0;
        
        int n = a.length;
        for(int i = 0; i < n; i++) {
            sumDSquared += (a[i]-b[i])*(a[i]-b[i]);
        }
        
        return Math.sqrt(sumDSquared);
    }

}
