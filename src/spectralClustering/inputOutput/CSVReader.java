package spectralClustering.inputOutput;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;

import edu.uci.ics.jung.graph.AbstractGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import spectralClustering.TunableParameters;
import spectralClustering.data.*;

public class CSVReader {
    private final String _filename;

    /**
     * Public constructor
     */
    public CSVReader()
    {
        this(TunableParameters.getInstance());
    }

    // Constructor with dependency injection
    public CSVReader(TunableParameters instanceParameters)
    {
        _filename = instanceParameters.getFileName();
    }

    
	public AbstractGraph<BCNode,BCEdge> loadDataSet() {
		ArrayList<String[]> rawData = importData(_filename);
		
		BCNode[] dataset = inputToBCNodes(rawData);
		dataset = normalizeData(dataset);
		
		//create graph
		
		AbstractGraph<BCNode, BCEdge> graph = new UndirectedSparseGraph();
	
		for(BCNode currentNode : dataset){
			graph.addVertex(currentNode);
		}
		
		return graph;
	}
	/**
	 * Imports the .csv file located at the input file path
	 * @param filepath - location of the file to be imported
	 * @return an array list of the data in the .csv
	 */
	public ArrayList<String[]> importData(String filepath){
		BufferedReader buffReader = null;
		String line = "";
		ArrayList<String[]> readPatterns = new ArrayList<String[]>();
		
		try {
			buffReader = new BufferedReader(new FileReader(filepath));
			while((line = buffReader.readLine()) != null){
				String[] stringNum = line.split(","); //split at commas
				readPatterns.add(stringNum);
			}
			buffReader.close();
		}
		catch (Exception e){
			System.err.println("Error: "+ e.getMessage());
		}
		return readPatterns;
	}
	/**
	 * Imports a comma separated file of input patterns
	 * @param inputList
	 * @return a double array list of input patterns
	 */
	public BCNode[] inputToBCNodes(ArrayList<String[]> inputList){
		BCNode[] output= new BCNode[inputList.size()];
		Double[] pointHolder;
		
		int id = 0; 
		
		for(int i = 0 ; i < inputList.size(); i ++){
			pointHolder = new Double[inputList.get(0).length];
			for(int j = 0; j < inputList.get(i).length; j ++){
				pointHolder[j] = Double.parseDouble(inputList.get(i)[j]);
			}
			output[i] = new BCNode(id ,pointHolder);
		}
		return output;
	}
	/**
	 * normalizes all of the dimensions of the input dataset 
	 * to the range 0 to 1
	 * @param input -unnormalized dataset
	 * @return	- normalized dataset
	 */
	public BCNode[] normalizeData(BCNode[] input){
		BCNode[] normalized = new BCNode[input.length];
		double high;
		double low;
		for(int i = 0; i < input[0].getNumAttributes();i ++){ //for each dimension of the data points
			low = 0;
			high = 0; 
			for(int j = 0 ; j < input.length; j ++){ //find the min and max values over all datavectors
				//find min
				if(low > input[j].getAttribute(i) || j ==0){
					low = input[j].getAttribute(i);
				}
				//find max
				if(high < input[j].getAttribute(i) || j==0){
					high = input[j].getAttribute(i);
				}
			}
			for(int j = 0 ; j < input.length; j ++){ //Normalize that dimension for all datavectors
				//normalize within range 0 to 1
				input[j].setAttribute(i,(input[j].getAttribute(i) - low)/(high-low));
			}
			
		}
		//copy over normalized data
		for(int i = 0 ; i < normalized.length; i ++){
			normalized[i] =input[i];
		}
		return normalized;
	}
}
