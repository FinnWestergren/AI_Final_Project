
public class ToeMove extends Move {
	int row, col;
	
	public ToeMove(int row, int col) {
		this.col = col;
		this.row = row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void setCol(int col) {
		this.col = col;
	}

}
