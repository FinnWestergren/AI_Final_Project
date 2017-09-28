import processing.core.PApplet;

public class Main extends PApplet {
	/*
	 * Sjoerd and Finn Fall 2017 bby!
	 *
	 * This program contains the game Quoridor, 
	 * but contains no graphical elements. 
	 * Only information about the board, the players, 
	 * and 2 AI Agents.
	 * 
	 * The main class contains an instance of the board and an instance of each player
	 */
	
	public static Board theBoard;
	public static Player player1;
	public static Player player2;
	
	public void runGame(){
		//handles all the behind the scenes, including AI and board updates
		int boardSize = Integer.parseInt(args[0]);
		theBoard = new Board(boardSize);
		player1 = new Player(boardSize/2, 0, 1, "");
		player2 = new Player(boardSize/2, boardSize - 1, 2, "greg");
		theBoard.init(player1, player2);
		printBoard();
	}
	
	public static void printBoard() {
		String out = theBoard.toString()
				+ player1.toString()+ "\n" +
				player2.toString();
		System.out.println(out);
	}
}
