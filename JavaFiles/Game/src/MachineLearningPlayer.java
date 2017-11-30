
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
	private Board oldBoard;
	private File weightsFile, layoutFile;
	
	static double LEARNINGRATE = 0.1;

	public MachineLearningPlayer(int pID) {
		super(pID);
	}

	public void init(Board b, File weightsFile, File layoutFile) {
		this.oldBoard = b;
		this.weightsFile = weightsFile;
		this.layoutFile = layoutFile;
		net = new NeuralNet(b, pID, layoutFile, weightsFile);
		//printNetwork();
	}

	public Move getNextMove(Board b) {
		double temp, max = -1;
		Move m = null;
		ArrayList<Move> allMoves;
		allMoves = b.getPossibleMoves(pID);

		for (int i = 0; i < allMoves.size(); i++) {
			b.performMove(allMoves.get(i), pID);
			temp = net.getOutput(b);
			if (temp > max) {
				max = temp;
				m = allMoves.get(i);
				if(b.checkGameOver()) LEARNINGRATE = 0.4;
				
			}
			b.undoMove(allMoves.get(i), pID);
		}

		return m;
	}

	@Override
	public Move getMove(BoardFeatures b) {
		LEARNINGRATE = 0.1;
		Board newBoard = (Board) b;
		Move aPrime = getNextMove(newBoard);
		double target = net.getOutput(newBoard) + LEARNINGRATE * newBoard.getBoardValue(pID);
		net.getOutput(oldBoard);
		net.updateNetworkWeights(target, LEARNINGRATE);
		oldBoard = newBoard.copy();
		return aPrime;
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
