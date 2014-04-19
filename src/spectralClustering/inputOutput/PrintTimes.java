package spectralClustering.inputOutput;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;

public class PrintTimes {
	boolean _titlesPrinted;
	
	public PrintTimes(){
		_titlesPrinted = false;
	}

	public void print(ArrayList<Double> values, String fileName, ArrayList<String> description) {
		String outString = "";
		if(!_titlesPrinted){
			outString += "\n";
			_titlesPrinted = true;
			for (int i = 0 ; i < description.size(); i ++){
				outString += description.get(i)+",";
			}
			outString += "\n";
		}
		for(int i = 0 ; i < values.size(); i ++){
			outString += values.get(i) + ", "; 
		}
		outString += "\n";
		
		PrintToFile(outString, fileName+".csv");
	}
	
	private void PrintToFile(String output,String filename){
		try  
		{
			FileWriter fstream = new FileWriter(filename, true); //true tells to append data.
		    BufferedWriter out = new BufferedWriter(fstream);
			out.write(output);
			out.close();
		}
		catch (Exception e)
		{
		    System.err.println("Error: " + e.getMessage());
		}
	}


}
