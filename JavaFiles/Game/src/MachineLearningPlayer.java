
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
	public Board oldBoard;
	private File weightsFile, layoutFile;
	public boolean updateOverride = false;

	static double LEARNINGRATE = 0.001, EPSILON = 0.2;

	public MachineLearningPlayer(int pID) {
		super(pID);
	}

	public void init(Board b, File weightsFile, File layoutFile) {
		this.oldBoard = b;
		this.weightsFile = weightsFile;
		this.layoutFile = layoutFile;
		net = new NeuralNet(b, pID, layoutFile, weightsFile);
		// printNetwork();
	}

	public Move getNextMove(Board b) {

		double max = -1;
		Move m = null;
		ArrayList<Move> allMoves;
		allMoves = b.getPossibleMoves(pID);
		if (allMoves.isEmpty())
			return null;
		double rando = Math.random();
		if (rando < EPSILON) {

			if (rando > EPSILON / 2)
				return allMoves.get((int) (Math.random() * allMoves.size()));
			return allMoves.get((int) (Math.random() * Math.min(4, allMoves.size())));
		}

		for (int i = 0; i < allMoves.size(); i++) {
			b.performMove(allMoves.get(i), pID);
			if (b.checkGameOver() && b.getWinner() == pID) {

				b.undoMove(allMoves.get(i), pID);
				return allMoves.get(i);
			}

			double temp = net.getOutput(b);
			// System.out.println(b.toString() + "\n" + temp);
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
		Board newBoard = (Board) b;
		Move aPrime = getNextMove(newBoard);
		if (oldBoard == null || aPrime == null) {
			oldBoard = newBoard.copy();
			return aPrime;
			
		}
		double target = net.getOutput(newBoard) + newBoard.getBoardValue(pID);
		net.getOutput(oldBoard);
		if (!updateOverride)
			net.updateNetworkWeights(target, LEARNINGRATE);
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

	public void winUpdate(Board newBoard) {
		double target = net.getOutput(newBoard) + newBoard.getBoardValue(pID);
		net.getOutput(oldBoard);
		net.updateNetworkWeights(target, LEARNINGRATE);
	}
}
