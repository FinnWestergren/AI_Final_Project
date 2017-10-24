import java.util.ArrayList;

public interface BoardFeatures {
	//these are features that Board must implement in order to interact with players
	
	public ArrayList<Move> getPossibleMoves(int pID);
	public void performMove(Move m, int pID);
	public int getBoardValue(int pID);
	public boolean checkGameOver();
	public void undoMove(Move m, int pID);
}
