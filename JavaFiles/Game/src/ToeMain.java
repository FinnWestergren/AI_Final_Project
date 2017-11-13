
public class ToeMain {
	
	public static void main(String[] args) {
		TicTacToeBoard board;
		Player[] player = new Player[2];
		
		board = new TicTacToeBoard();
		board.init();
		player[0] = new AlphaBetaPlayer(0);
		player[1] = new ToeHumanPlayer(1);
		
		String out = board.toString();
		System.out.println(out);
		
		System.out.println("\n\n");
		
		board.performMove(player[0].getMove(board), player[0].pID);
		
		board.performMove( player[1].getMove(board), player[1].pID);
		
		
		out = board.toString();
		System.out.println(out);
	}
}
