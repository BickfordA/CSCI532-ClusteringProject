package spectralClustering;

//Class to store all the tuanble parameters so that they can be easily changed.

public class TunableParameters {
	private static TunableParameters _currentInstance;
	
	/**
	 * 
	 * variables
	 * 
	 */
	//file variables
	private final String _sourceDataFilePath;
	private final int _sourceDataAttributeNum;
	private final int _sourceDataSetSize;
	
	
	
	//initialize the parameters
	private TunableParameters(){
		_sourceDataFilePath  = "breastcancer.txt";
		_sourceDataAttributeNum = 9;
		_sourceDataSetSize = 0; //need to look this up
		
	}
	
	//return the instance, if none create it
	public static TunableParameters getInstance(){
		if(_currentInstance == null)
			_currentInstance = new TunableParameters();
		
		return _currentInstance;
	}
	
	/**
	 * 
	 * Getter Functions for parameters 
	 * 
	 */
	public String getFileName(){ return _sourceDataFilePath; }
	public int getFileAttributeNum(){ return _sourceDataAttributeNum;}
	public int getDataSetSize(){ return _sourceDataSetSize; }
}