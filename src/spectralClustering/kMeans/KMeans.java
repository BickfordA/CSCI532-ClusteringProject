/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spectralClustering.kMeans;

import java.util.ArrayList;
import java.util.List;

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
    
    public KMeans(double[][] data, int k) {
        this.data = data;
        this.k = k;
        
        this.n = data.length;
        this.numAttributes = data[0].length;
        
    }
    
    public int[] calcClusters(){
        
        int[] clusters = new int[n];
        double[][] centers = new double[k][numAttributes];
        
        //pick k centers
        for(int i = 0; i < k; i++){
            int r = i*n/k;
            for( int j = 0; j < numAttributes; j++){
                centers[i][j] = data[r][j];
            }
        }
        
        int changes = 1;
        while( changes != 0 ) {
            // assignment
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
            
            // update
            // recalculate centers
            if( changes != 0 ) {
                ArrayList<Integer>[] clusterPoints = new ArrayList[k];
                for(int i = 0; i< k; i++) {
                    clusterPoints[i] = (ArrayList<Integer>)new ArrayList();
                }

                for(int i = 0; i < n; i++){
                    clusterPoints[clusters[i]].add(i);
                }
                
                for(int i = 0; i < k; i++){ // i is index for cluster
                    double[] sum = new double[numAttributes];
                    for(Integer j : clusterPoints[i]) { // j is index for data
                        for(int p = 0; p< numAttributes; p++){ // p is index for attribute
                            sum[p] += data[j][p];
                        }
                    }
                    for(int p = 0; p< numAttributes; p++){
                        centers[i][p] = sum[p]/clusterPoints[i].size();
                    }
                }
            }
        }
        
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
