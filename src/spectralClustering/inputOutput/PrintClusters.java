/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package spectralClustering.inputOutput;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * PrintClusters class
 *
 *
 * @author John
 * @version
 */

public class PrintClusters {
    
    public PrintClusters(){
        
    }
    
    public void print(int[][] clusters,String filename){
        try {
            FileWriter fstream = new FileWriter(filename+".csv", true); //true tells to append data.
            BufferedWriter out = new BufferedWriter(fstream);
            for(int j = 0; j < clusters[0].length; j++) {
                for(int i = 0; i<clusters.length; i++) {
                    out.write(Integer.toString(clusters[i][j]) + ",");
                }
                out.write("\n");
            }
            out.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

}
