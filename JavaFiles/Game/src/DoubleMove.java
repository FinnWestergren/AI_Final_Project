
public class DoubleMove extends Move {

	private PieceMove firstMove, secondMove;

	

	public DoubleMove(PieceMove firstMove, PieceMove secondMove) {
		super();
		this.firstMove = firstMove;
		this.secondMove = secondMove;
	}

	public PieceMove getFirstMove() {
		return firstMove;
	}

	public void setFirstMove(PieceMove firstMove) {
		this.firstMove = firstMove;
	}

	public PieceMove getSecondMove() {
		return secondMove;
	}

	public void setSecondMove(PieceMove secondMove) {
		this.secondMove = secondMove;
	}


	
}
