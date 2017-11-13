import java.util.Scanner;

public class Player  {

	/*
	 * this class should be extended by alpha beta, reinforcement learning, and
	 * human players I'm tryna keep this extra simple so we can reimplement and test
	 * on tic tac toe (which we shouldn't need a UI for) 
	 * 
	 * you know, because of the implication
	 */

	public String name;
	public int pID;
	public Player(int pID) {
		this.pID = pID;
		name = null;
	}

	public Player(String name) {
		this.name = name;
	}
	
	public Move getMove(BoardFeatures b) {
		Scanner scan = new Scanner(System.in);
		return new ToeMove(scan.nextInt(), scan.nextInt());
		// TODO Auto-generated method stub
	
	}
	


}
