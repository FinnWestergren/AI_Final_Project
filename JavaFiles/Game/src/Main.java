import java.io.File;

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
	static final int windowSize = 700, margin = 150, boardSize = 9, cellSize = 70;
	static final String loadBoard = "empty_board.txt";
	static final String loadBoardPath = "../BoardStrings/" + loadBoard;
	public int currentPlayer = 0;

	public void setup() {

		initGame();
		updateGraphics();
	}

	public void draw() {
		fill(255, 155, 111, 255);
		noStroke();
		rect(-1, -1, width + 2, height + 1);
		gameUI.draw(this);
		noStroke();
		fill(255 * currentPlayer,255 * currentPlayer,255 - (currentPlayer *255));
		ellipse(margin  + (currentPlayer *(windowSize)) - 0.05f*margin, 10,15,15);

	}

	public void initGame() {
		size(windowSize + 2 * margin, windowSize);
		background(255, 155, 111);
		gameUI = new GameUI(cellSize, boardSize);
		theBoard = new Board(boardSize);
		player[0] = new HumanPlayer(0);
		player[1] = new HumanPlayer(1);
		theBoard.init(new File(loadBoardPath));
		// testing addWall method
		/*
		 * ---> addWall was replaced with performWallMove a private method used in
		 * performMove theBoard.addWall(2, 2, Orientation.VERTICAL, 1);
		 */

	}

	public void updateGraphics() {
		gameUI.update(theBoard.toString(), theBoard.pieces[0], theBoard.pieces[1]);
	}

	public void mousePressed() {

		if (player[currentPlayer] instanceof HumanPlayer) {
			Move m = gameUI.getHighlightedMove();
			PerformMove(m);
		}
	}

	public void keyPressed() {

		if (key != CODED)
			return;

		if (player[currentPlayer] instanceof HumanPlayer) {
			PieceMove m = new PieceMove(Direction.UP);
			switch (keyCode) {

			case UP:
				m.setDirection(Direction.UP);
				break;
			case DOWN:
				m.setDirection(Direction.DOWN);
				break;
			case LEFT:
				m.setDirection(Direction.LEFT);
				break;
			case RIGHT:
				m.setDirection(Direction.RIGHT);
				break;
			}

			PerformMove(m);
		}

	}

	private void PerformMove(Move move) {
		if (move == null)
			return;

		if (theBoard.checkPossible(move, currentPlayer)) {

			theBoard.performMove(move, currentPlayer);

			updateGraphics();
			System.out.println(
					"Player " + (currentPlayer + 1) + "'s value of board: " + theBoard.getBoardValue(currentPlayer));
			currentPlayer = (currentPlayer + 1) % 2;
		}
	}
}
