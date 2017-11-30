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
import java.util.ArrayList;
import java.util.Scanner;

public class MachineLearningPlayer extends Player implements AI_Player {

	private NeuralNet net;
	private Board b;
	private File weightsFile, layoutFile;

	public MachineLearningPlayer(int pID) {
		super(pID);
	}

	public void init(Board b, File weightsFile, File layoutFile) {
		this.b = b;
		this.weightsFile = weightsFile;
		this.layoutFile = layoutFile;
		net = new NeuralNet(b, pID, layoutFile, weightsFile);
		printNetwork();
	}
		
	public Move getNextMove() {
		double temp, max = -1;
		Move m = null;
		ArrayList<Move> allMoves;
		allMoves = b.getAllMoves(pID);
		
		for (int i = 0 ; i < allMoves.size() ; i++) {
			b.performMove(allMoves.get(i), pID);
			temp = net.getOutput(b);
			if (temp > max) {
				max = temp;
				m = allMoves.get(i);
			}
			b.undoMove(allMoves.get(i), pID);
		}
		
		return m;
	}
	
	public Move getMove() {
		
		
		return null;
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
