
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
	public boolean updateOverride = false;
	
	static double LEARNINGRATE = 0.03;

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
		double epsilon = 0.1;
		double max = -1;
		Move m = null;
		ArrayList<Move> allMoves;
		allMoves = b.getPossibleMoves(pID);
		double rando = Math.random();
		if(rando < epsilon) {
		
			
			return allMoves.get((int)Math.random() * allMoves.size()); 
		}
		

		for (int i = 0; i < allMoves.size(); i++) {
			
			if(b.checkGameOver() && b.getWinner() == pID) {
				
				b.undoMove(allMoves.get(i), pID);
				
				break;
			}
			
			b.performMove(allMoves.get(i), pID);
			double temp = net.getOutput(b);
			//System.out.println(b.toString() + "\n" + temp);
			if (temp >= max) {
				
				max = temp;
				m = allMoves.get(i);
				
				
			}
			
			
			b.undoMove(allMoves.get(i), pID);
		}
		
		return m;
	}

	@Override
	public Move getMove(BoardFeatures b) {
		LEARNINGRATE = 0.03;
		Board newBoard = (Board) b;
		Move aPrime = getNextMove(newBoard);
		double target = net.getOutput(newBoard) + newBoard.getBoardValue(pID);
		net.getOutput(oldBoard);
		if(!updateOverride) net.updateNetworkWeights(target, LEARNINGRATE);
		oldBoard = newBoard.copy();
		updateOverride = false;
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
