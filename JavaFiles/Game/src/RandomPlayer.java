import java.util.ArrayList;

public class RandomPlayer extends Player implements AI_Player {

	public RandomPlayer(int pID) {
		super(pID);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Move getMove(Board board) {
		ArrayList<Move> mList = board.getPossibleMoves(pID);
		double rando = Math.random();
		if(rando > .5) return mList.get((int) (Math.random() * mList.size()));
		return mList.get((int)(Math.random() * Math.min(4, mList.size())) );
		
	}

}
