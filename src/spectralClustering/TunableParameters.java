package spectralClustering;

//Class to store all the tunable parameters so that they can be easily changed.

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
	private final int _numberOfClusters;
	
	
	//initialize the parameters
	private TunableParameters(){
		//_sourceDataFilePath  = "breastcancer.txt";
		//_sourceDataAttributeNum = 9;
		//_sourceDataSetSize = 699; 
                //_numberOfClusters = 2;
            
//                _sourceDataFilePath  = "data/breast_cancer_wisconsin/BreastCancerWithoutClassification.csv";
//		_sourceDataAttributeNum = 30;
//		_sourceDataSetSize = 569; 
//                _numberOfClusters = 2;
		
//		_sourceDataFilePath  = "data/Cardiotocography/CTG_no_class_labels.csv";
//		_sourceDataAttributeNum = 21;
//		_sourceDataSetSize = 2126; 
//                _numberOfClusters = 3;
		
//		_sourceDataFilePath  = "data/ClimateModel/ClimateModel_no_class_labels.csv";
//		_sourceDataAttributeNum = 18;
//		_sourceDataSetSize = 540; 
//		_numberOfClusters = 2;
                        
//		_sourceDataFilePath  = "data/Hill-Vally Data Set/HillVally_no_class_labels.csv";
//		_sourceDataAttributeNum = 100;
//		_sourceDataSetSize = 1212;
//                _numberOfClusters = 2;
		
//		_sourceDataFilePath  = "data/Ionosphere/ionosphere_no_class_labels.csv";
//		_sourceDataAttributeNum = 33;
//		_sourceDataSetSize = 351;
//                _numberOfClusters = 2;
		
//		_sourceDataFilePath  = "data/Knowledge/knowledge_no_class_lables.csv";
//		_sourceDataAttributeNum = 5;
//		_sourceDataSetSize = 258; 
//                _numberOfClusters = 4;
            
//		_sourceDataFilePath  = "data/ToySets/path_based1_no_class_labels.csv";
//		_sourceDataAttributeNum = 2;
//		_sourceDataSetSize = 300; 
//                _numberOfClusters = 3;
                
                _sourceDataFilePath  = "data/ToySets/spiral_no_class_labels.csv";
		_sourceDataAttributeNum = 2;
		_sourceDataSetSize = 312; 
                _numberOfClusters = 3;
		
		_m = 200;
		_sigma = 1;
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
    public int getNumberOfClusters(){ return _numberOfClusters;}
}
