
package spectralClustering.data;

/**
 * BCNode class
 *
 *
 * @author John
 * @version
 */

public class BCNode {

    // id from original data set
    private int id;
    
    // index value for constructing simularity matrix
    private int index;
    
    private Double[] attributes;
    private int numAttributes;
    
    public BCNode(int id, int index, Double[] attributes) {
        this.id = id;
        this.index = index;
        this.attributes = attributes;
        numAttributes = attributes.length;
    }
    
    public int getId() {
        return id;
    }
    
    public int getIndex() {
        return index;
    }
    
    public Double getAttribute(int index){
        if( index < attributes.length ) {
            return attributes[index];
        } else return null;
    }
    
    public void setAttribute(int index, Double value){
    	if( index < attributes.length ){
    		attributes[index] = value;
    	}//else?
    }
    
    public int getNumAttributes(){
        return numAttributes;
    }
    
    // calculate the Euclidean distance between this node and another
    public double calcEucDistance (BCNode b) {
        double sumDSquared = 0.0;
        
        int n = Math.max(this.getNumAttributes(), b.getNumAttributes());
        for(int i = 0; i < n; i++) {
            Double ai = this.getAttribute(i);
            Double bi = b.getAttribute(i);
            if( ai == null || bi == null ) {
                sumDSquared += 0.0;
            } else {
                sumDSquared += (ai-bi)*(ai-bi);
            }
        }
        
        return Math.sqrt(sumDSquared);
    }
            
            
}
