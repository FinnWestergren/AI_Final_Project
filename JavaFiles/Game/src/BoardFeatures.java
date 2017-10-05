
public interface BoardFeatures {
	//these are features that Board must implement in order to interact with players
	
	public Move[] getPossibleMoves(int pID);
	public void performMove(Move m, int pID);
	public void undoMove(Move m);
	public double getBoardValue(int pID);
	public boolean checkPossible(Move m);
	public boolean checkGameOver();
}
