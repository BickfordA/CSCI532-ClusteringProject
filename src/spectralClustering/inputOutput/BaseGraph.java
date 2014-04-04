package spectralClustering.inputOutput;

import java.io.FileReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Pattern;

import spectralClustering.TunableParameters;
import spectralClustering.data.*;
import edu.uci.ics.jung.graph.AbstractGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public class BaseGraph {
	private static String _fileName; 
	private static int _numAttributes;
	private static AbstractGraph<BCNode,BCEdge> _graph;
	
	public BaseGraph(){
		TunableParameters parameters = TunableParameters.getInstance();
        _fileName = parameters.getFileName();
        _graph = new UndirectedSparseGraph<BCNode,BCEdge>();
        
        //9 for breast cancer data 
        _numAttributes = parameters.getFileAttributeNum(); 
		
        createGraph();
        
        connectGraph();
        
	}
	
	
	// Parse text file and return data points as vertices in a graph
    private static void createGraph() {
    	TunableParameters parameters = TunableParameters.getInstance();

        Pattern p = Pattern.compile( ",| |\n|\r" );
        try {
            Scanner scan = new Scanner(new FileReader(_fileName));
            scan.useDelimiter(p);
            while( scan.hasNextLine() ) {
                int ID = scan.nextInt();
                //System.out.println(ID);
                double[] attr = new double[_numAttributes];
                for(int i = 0; i < _numAttributes; i++) {
                    String s = scan.next();
                    if( s.equals("?") ){
                        attr[i] = Double.NaN;
                    } else {
                        attr[i] = Integer.valueOf(s);
                    }
                    //System.out.print(attr[i] + " ");
                }
                //System.out.println();
                _graph.addVertex(new BCNode(ID, attr));
                if( scan.hasNextLine() ) {
                    scan.nextLine();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
	}
    
    public static void connectGraph(){
        
        int n = _graph.getVertexCount();
        
        System.out.println("n = " + n );
        
        System.out.println("Calculating Euclidean Distance");
        
        Collection<BCNode> nodes = _graph.getVertices();
        Iterator<BCNode> iti = nodes.iterator();
        while( iti.hasNext() ) {
            BCNode nodei = iti.next();
            Iterator<BCNode> itj = nodes.iterator();
            while( itj.hasNext() ) {
                BCNode nodej = itj.next();
                if( _graph.findEdge(nodei,nodej) == null) {
                    BCEdge e = new BCEdge( nodei.calcEucDistance( nodej ));
                    _graph.addEdge(e,nodei,nodej);
                }
            }
        }
    }
    
    public AbstractGraph<BCNode,BCEdge> getGraph(){
    	return _graph;
    }

}
