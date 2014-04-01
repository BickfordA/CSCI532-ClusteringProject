/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clustering;

/**
 * BCNode class
 *
 *
 * @author John
 * @version
 */

public class BCNode {

    private int id;
    
    private Integer[] attributes;
    private int numAttributes;
    
    public BCNode(int id, Integer[] attributes) {
        this.id = id;
        this.attributes = attributes;
        numAttributes = attributes.length;
    }
    
    public int getId() {
        return id;
    }
    
    public Integer getAttribute(int index){
        if( index < attributes.length ) {
            return attributes[index];
        } else return null;
    }
    
    public int getNumAttributes(){
        return numAttributes;
    }
            
            
}
