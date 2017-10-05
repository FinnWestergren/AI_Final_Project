import processing.core.PApplet;

public class Main extends PApplet {
	/*
	 * Sjoerd and Finn Fall 2017 bby!
	 *
	 * This program plays the game Quoridor. The classes
	 * are separated in a manner that allows the best dynamic sizing, 
	 * testing, and debugging. 
	 * 
	 * The graphics are all controlled by a single class, GameUI.
	 * It is fed a string by main from Board.toString
	 * 
	 * TODO finish the auto-generated methods in board, devise a heuristic
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
	public Player[] player = { new Player(), new Player() };
	public GameUI gameUI;
	static final int windowSize = 700, margin = 150, boardSize = 9, cellSize = 70;

	public void setup() {

		size(windowSize + 2 * margin, windowSize);
		background(255, 155, 111);
		gameUI = new GameUI(cellSize, boardSize);
		initGame();
		initGraphics();
	}

	public void draw() {
		pushMatrix();
		float cellMargin = cellSize * 0.1f;
		translate(cellMargin + 150, cellMargin);
		gameUI.draw(this);
		popMatrix();

	}

	public void initGame() {

		theBoard = new Board(boardSize);
		player[0] = new Player("shrimpo 2, the squeakquel");
		player[1] = new Player("greg3001");
		theBoard.init();
		// testing addWall method
		/*  ---> addWall was replaced with performWallMove a private method used in performMove
		theBoard.addWall(2, 2, Orientation.VERTICAL, 1);
		*/

	}

	public void initGraphics() {
		gameUI.update(theBoard.toString(), theBoard.pieces[0], theBoard.pieces[1]);
	}

}
