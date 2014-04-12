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
    private final int _m;
	private final double _sigma;
	private final int _clusterSeedSize; //number of eigin vectors selected to perform k-means on
	
	
	//initialize the parameters
	private TunableParameters(){
		_sourceDataFilePath  = "breastcancer.txt";
		_sourceDataAttributeNum = 9;
		_sourceDataSetSize = 699; //need to look this up
                _m = 200;
		_sigma = .2;
		_clusterSeedSize = 50;
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
    public int getM(){ return _m; }
    public double getSigma(){ return _sigma; }
    public int getNumberOfEVectors(){ return _clusterSeedSize;};
}
