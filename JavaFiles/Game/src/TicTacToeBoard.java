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
				cellArray[i][j].occupied = false;
			}
		}
	}
	
	public String toString() {
		String out = "";
		
		for(int i = 0 ; i < cellArray.length ; i++) {
			for(int j = 0 ; j < cellArray.length ; j++) {
				
				if (cellArray[i][j].occupied == false) {
					out += "   ";
				}
				if (cellArray[i][j].occupiedO == true) {
					out += " O ";
				}
				if (cellArray[i][j].occupiedX == true) {
					out += " X ";
				}
				out += " | ";
			}
			if (i != 2)
				out += "\n---------------------\n";
		}
		
		return out;
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
		int x, y;
		x = ((ToeMove) m).getCol();
		y = ((ToeMove) m).getRow();
		
		if (pID == PLAYER_X) {
			cellArray[x][y].occupiedX = true;
			cellArray[x][y].occupied = true;
		}
		if (pID == PLAYER_O) {
			cellArray[x][y].occupiedO = true;
			cellArray[x][y].occupied = true;
		}
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
		if (cellArray[m.col][m.row].occupied == true)
			return false;
		else 
			return true;
	}

	@Override
	public boolean checkGameOver() {
		for (int i = 0 ; i < cellArray.length ; i++) {
			for (int j = 0 ; j < cellArray.length ; j++) {
				if (cellArray[i][j].occupied == false)
					return false;
			}
		}
		
		return true;
	}

	@Override
	public void undoMove(Move m, int pID) {
		// TODO Auto-generated method stub
		
	}

}
