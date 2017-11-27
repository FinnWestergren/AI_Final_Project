import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MachineLearningPlayer extends Player implements AI_Player {
	
	
	private NeuralNet net;
	private File weightsFile, layoutFile;

	public MachineLearningPlayer( int pID) {
		super(pID);
	}
	
	public void init(Board b, File weightsFile, File layoutFile) {
		this.weightsFile = weightsFile;
		this.layoutFile = layoutFile;
		net = new NeuralNet(b, pID, layoutFile, weightsFile);
		printNetwork();
	}
	
	public void printNetwork() {
		String layout = net.layoutToString();
		String weights = net.weightsToString();
		try {
			PrintWriter layoutPrinter = new PrintWriter(layoutFile);
			layoutPrinter.write(layout);
			layoutPrinter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			PrintWriter weightPrinter = new PrintWriter(weightsFile);
			weightPrinter.write(weights);
			weightPrinter.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
}