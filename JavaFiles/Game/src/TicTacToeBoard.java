import java.util.ArrayList;

public class TicTacToeBoard implements BoardFeatures {

	public ToeCell[][] cellArray;
	public static final int PLAYER_X = 0;
	public static final int PLAYER_O = 1;
	
	public TicTacToeBoard() {
		cellArray = new ToeCell[3][3];
	}
	
	public void init() {
		for(int i = 0 ; i < cellArray.length ; i++) {
			for(int j = 0 ; j < cellArray.length ; j++) {
				cellArray[i][j] = new ToeCell();
			}
		}
	}
	
	@Override
	public ArrayList<Move> getPossibleMoves(int pID) {
		// TODO Auto-generated method stub
		ArrayList<Move> moveList = new ArrayList<Move>();
		
		for(int i = 0 ; i < cellArray.length ; i++) {
			for(int j = 0 ; j < cellArray.length ; j++) {
				if (checkPossible(new ToeMove(i, j))) {
					moveList.add(new ToeMove(i, j));
				}
			}
		}
		
		return moveList;
	}

	@Override
	public void performMove(Move m, int pID) {
		if (pID == PLAYER_X) {
			
		}
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public double getBoardValue(int pID) {
		// TODO Auto-generated method stub
		
		return 0;
	}

	
	public boolean checkPossible(Move m) {
		return checkToeMove((ToeMove) m);
	}
	
	public boolean checkToeMove(ToeMove m) {
		if (cellArray[m.col][m.row].occupiedX == true || cellArray[m.col][m.row].occupiedO == true)
			return false;
		else 
			return true;
	}

	@Override
	public boolean checkGameOver() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void undoMove(Move m, int pID) {
		// TODO Auto-generated method stub
		
	}

}
