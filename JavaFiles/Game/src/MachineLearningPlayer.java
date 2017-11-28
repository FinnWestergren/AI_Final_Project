	/*
	 * Machine Learner Class
	 * Implements Neural Net, Node, Synapse, Neural Layer...
	 * 
	 * getMove Method overrides Player getMove
	 * getMove will contain SARSA 
	 * 
	 * 	SARSA should...
	 * 	init double max
	 * 	init move m
	 * 	for each action a (get all possible moves)
	 * 		perform action(move) a
	 * 		get Value of s' upon performing action(move) a
	 * 		if value of s' > max, then assign action(move) a to move m
	 * 		
	 * 	
	 */
	import java.io.File;
	import java.io.FileNotFoundException;
	import java.io.PrintWriter;
	import java.util.Scanner;

<<<<<<< HEAD
public class MachineLearningPlayer extends Player implements AI_Player {
	
	private NeuralNet net;
	private File weightsFile, layoutFile;

	public MachineLearningPlayer(int pID) {
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
=======
	public class MachineLearningPlayer extends Player implements AI_Player {
		
		
		private NeuralNet net;
		private File weightsFile, layoutFile;

		public MachineLearningPlayer( int pID) {
			super(pID);
>>>>>>> c6be1cd502175c45eec9eab2bc561cbe54ec93e1
		}
		
		public void init(Board b, File weightsFile, File layoutFile) {
			this.weightsFile = weightsFile;
			this.layoutFile = layoutFile;
			net = new NeuralNet(b, pID, layoutFile, weightsFile);
			printNetwork();
		}
<<<<<<< HEAD
	}
}
=======
		
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
>>>>>>> c6be1cd502175c45eec9eab2bc561cbe54ec93e1
