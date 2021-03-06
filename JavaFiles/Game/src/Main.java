import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import processing.core.PApplet;

public class Main extends PApplet {

	/*
	 * Sjoerd and Finn Fall 2017 bby!
	 *
	 * This program plays the game Quoridor. The classes are separated in a manner
	 * that allows the best dynamic sizing, testing, and debugging.
	 * 
	 * The graphics are all controlled by a single class, GameUI. It is fed a string
	 * by main from Board.toString
	 * 
	 * TODO design alpha beta agent
	 * 
	 * TODO make a text game tic tac toe that implements BoardFeatures
	 * 
	 * thats a good start
	 */

	/*
	 * The main class contains an instance of the board and an instance of each
	 * player
	 */

	public Board theBoard;
	public Player[] player = new Player[2];
	public GameUI gameUI;
	static final int windowSize = 700, margin = 150, boardSize = 9, cellSize = windowSize / (boardSize + 1);
	static final String[] loadBoard = new String[] { "WinState/" , "1Away/","empty_board.txt"};
	public static int currentMap = 0;
	static  String loadBoardPath = "BoardStrings/" + loadBoard[currentMap];
	static final String net = "Finn_2Layer4/";
	static final String netPath = "../Network/" + net;
	public int currentPlayer = 1;
	
	public int turns = 0;
	public int timer = 10;
	public int startXLocation = 1;
	public int winRequirement = 10;
	int turnLimit = 8;

	static int wins = 1, losses = 0;

	public void setup() {
		size(windowSize + 2 * margin, windowSize);
		background(255, 155, 111);
		gameUI = new GameUI(cellSize, boardSize);
		initGame();
		update();
		updateGraphics();
		frameRate(500);
	}

	public void draw() {

		fill(255, 155, 111, 255);
		noStroke();
		rect(-1, -1, width + 2, height + 1);
		gameUI.draw(this);
		noStroke();
		fill(255 * currentPlayer, 255 * currentPlayer, 255 - (currentPlayer * 255));
		ellipse(margin + (currentPlayer * (windowSize)) - 0.05f * margin, 10, 15, 15);
		if (player[currentPlayer] instanceof AI_Player) {
			performMove(player[currentPlayer].getMove(theBoard));
			//((MachineLearningPlayer) player[1]).printNetwork();
			turns ++;
		}
	}

	public void initGame() {
		theBoard = new Board(boardSize);
		player[1] = new MachineLearningPlayer(1);
		player[0] = new AlphaBetaPlayer(0, 0);
		initBoard();
		initNeuralNet(1);

	}

	private void initNeuralNet(int pID) {
		File layoutFile = new File(netPath + "layout.txt");
		File weightsFile = new File(netPath + "weights.txt");
		((MachineLearningPlayer) player[pID]).init(theBoard, weightsFile, layoutFile);
	}

	private void initBoard() {
		loadBoardPath = "../BoardStrings/" + loadBoard[currentMap];
		File file = new File(loadBoardPath + (startXLocation % 9)); // 
		
		String fileString = "";
		Scanner fileScanner;
		try {

			fileScanner = new Scanner(file);
			while (fileScanner.hasNextLine()) {
				fileString += fileScanner.nextLine() + "\n";
			}
			// System.out.println(fileString);
			theBoard.init(fileString);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void update() {
		

		if (theBoard.checkGameOver()) {
			
			if (theBoard.getWinner() == 1)
				wins++;
			else
				losses++;
			System.out.println(net + " W: " + wins + " L: " + losses);
			turns = 0;
			if(wins % winRequirement == 0) {
				startXLocation ++;
				((MachineLearningPlayer) player[1]).printNetwork();
			}
			
			if(wins > 18 * winRequirement) currentMap = 1;
			
			((MachineLearningPlayer) player[1]).winUpdate(theBoard);
			initBoard();
		}
		
		if(turns > turnLimit) {
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

	public void keyPressed() {

		if (key != CODED)
			return;

		if (player[currentPlayer] instanceof HumanPlayer) {

			CoordinatePair from = theBoard.pieceLocation[currentPlayer];
			CoordinatePair to;
			switch (keyCode) {

			case UP:
				to = from.getNextCoordinatePairFromDirection(Direction.UP);
				break;
			case DOWN:
				to = from.getNextCoordinatePairFromDirection(Direction.DOWN);
				break;
			case LEFT:
				to = from.getNextCoordinatePairFromDirection(Direction.LEFT);
				;
				break;
			case RIGHT:
				to = from.getNextCoordinatePairFromDirection(Direction.RIGHT);
				break;
			default:
				return;
			}
			performMove(new PieceMove(from, to));
		}

	}

	private void performMove(Move move) {
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
		updateGraphics();
	}
}
