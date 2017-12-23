import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import processing.core.PApplet;

public class TrainingMain {

	/*
	 * Sjoerd and Finn Fall 2017!
	 *
	 * This program plays the game Quoridor. The classes are separated in a manner
	 * that allows the best dynamic sizing, testing, and debugging.
	 * 
	 * The graphics are all controlled by a single class, GameUI. It is fed a string
	 * by main from Board.toString
	 * 
	 * 
	 * 
	 */

	/*
	 * The main class contains an instance of the board and an instance of each
	 * player
	 */

	public static Board theBoard;
	public static Player[] player = new Player[2];
	public GameUI gameUI;
	static final int windowSize = 700, margin = 150, boardSize = 9, cellSize = windowSize / (boardSize + 1);
	static final String[] loadBoard = new String[] { "WinState/", "1Away/", "empty_board.txt" };
	public static int currentMap = 0;
	static String loadBoardPath = "BoardStrings/";
	static final String net = "Finn_2Layer4/";
	static final String netPath = "Network/" + net;
	public static int currentPlayer = 1;
	public static final boolean GUI_ON = false;

	public static int turns = 0;
	public int timer = 10;
	public static int startXLocation = 1;
	public static int winRequirement = 100;
	static int turnLimit = 8;

	static int wins = 1, losses = 0;

	public static void main(String[] args) {
		if (!GUI_ON) {
			initGame();
			update();
			while (true) {
				if (player[currentPlayer] instanceof AI_Player) {
					performMove(player[currentPlayer].getMove(theBoard));
					// ((MachineLearningPlayer) player[1]).printNetwork();
					turns++;
				}
			}
		} else {
			PApplet p = new Main();
			p.main("Main");
		}

	}

	public static void initGame() {
		theBoard = new Board(boardSize);
		player[1] = new MachineLearningPlayer(1);
		player[0] = new AlphaBetaPlayer(0, 0);
		initBoard();
		initNeuralNet(1);

	}

	private static void initNeuralNet(int pID) {
		File layoutFile = new File(netPath + "layout.txt");
		File weightsFile = new File(netPath + "weights.txt");
		((MachineLearningPlayer) player[pID]).init(theBoard, weightsFile, layoutFile);
	}

	private static void initBoard() {
		String b = loadBoardPath + loadBoard[currentMap];
		if (loadBoard[currentMap].contains("/"))
			b += startXLocation;
		File file = new File(b); //
		String fileString = "";
		Scanner fileScanner;
		try {
			fileScanner = new Scanner(file);
			while (fileScanner.hasNextLine()) {
				fileString += fileScanner.nextLine() + "\n";
			}
			// System.out.println(fileString);
			theBoard.init(fileString);
			((MachineLearningPlayer) player[1]).oldBoard = null;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void update() {

		if (theBoard.checkGameOver()) {

			if (theBoard.getWinner() == 1)
				wins++;
			else
				losses++;
			System.out.println(net + " W: " + wins + " L: " + losses);
			turns = 0;
			if (wins % winRequirement == 0) {
				startXLocation = (startXLocation + 1) % (9);
				((MachineLearningPlayer) player[1]).printNetwork();
			}

			if (wins > 9*3 * winRequirement)
				currentMap = (currentMap + 1) % 2;

			((MachineLearningPlayer) player[1]).winUpdate(theBoard);
			initBoard();
		}

		if (turns > turnLimit) {
			turns = 0;
			System.out.println("Time-Out");

			initBoard();
		}

	}

	public void updateGraphics() {
		if (!theBoard.checkGameOver()) {
			gameUI.currentPlayer = currentPlayer;
			gameUI.update(theBoard.toString(), theBoard.pieceLocation[0], theBoard.pieceLocation[1]);

		}
	}

	public void mousePressed() {

		if (player[currentPlayer] instanceof HumanPlayer) {
			Move m = gameUI.getHighlightedMove();
			performMove(m);
		}
	}

	private static void performMove(Move move) {
		if (move == null)
			return;
		boolean doubleMove = false;
		if (move instanceof PieceMove) {
			if (theBoard.checkOccupied(((PieceMove) move).getTo()))
				doubleMove = true;
		}
		if (player[currentPlayer] instanceof HumanPlayer) {
			if (!theBoard.checkPossible(move, currentPlayer) && !doubleMove)
				System.out.println("wtf mate");
			else {

				theBoard.performMove(move, currentPlayer);

				System.out.println("Player " + (currentPlayer + 1) + "'s manhattan distance: "
						+ theBoard.manhattanDistance(currentPlayer));

				if (!doubleMove)
					currentPlayer = (currentPlayer + 1) % 2;
				update();
			}
		}

		else {
			theBoard.performMove(move, currentPlayer);

			currentPlayer = (currentPlayer + 1) % 2;
			update();
		}
	}

}
