
public class ToeMain {
	
	public static void main(String[] args) {
		TicTacToeBoard board;
		Player[] player = { new Player(0), new ToeHumanPlayer(1) };
		
		board = new TicTacToeBoard();
		board.init();
		player[0] = new Player("dave, the Toolman, taylor");
		player[1] = new ToeHumanPlayer("HAL9001");
		
		String out = board.toString();
		System.out.println(out);
		
		ToeMove m = new ToeMove(1,2);
		
		System.out.println("\n\n");
		board.performMove(((ToeHumanPlayer) player[1]).getMove(), player[1].pID);
		
		
		out = board.toString();
		System.out.println(out);
	}
}
