
public class ToeMain {
	
	public static void main(String[] args) {
		TicTacToeBoard board;
		Player[] player = { new Player(), new Player() };
		
		board = new TicTacToeBoard();
		board.init();
		player[0] = new Player("dave, the Toolman, taylor");
		player[1] = new Player("HAL9001");
		
		String out = board.toString();
		System.out.println(out);
		
		ToeMove m = new ToeMove(1,2);
		
		System.out.println("\n\n");
		board.performMove(m, 0);
		
		out = board.toString();
		System.out.println(out);
	}
}
