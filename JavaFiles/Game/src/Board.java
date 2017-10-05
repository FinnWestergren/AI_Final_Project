import java.util.ArrayList;

public class Board implements BoardFeatures {

	public int boardSize; // size of one side
	public Cell[][] cellArray;
	public Junction[][] junctArray;
	public Move[] allJuncMoves;
	public GamePiece[] pieces = { new GamePiece(), new GamePiece() };
	public int[] wallsLeft = { 10, 10 };
	public static ArrayList<WallMove> allWallMoves = new ArrayList<WallMove>();

	public Board(int boardSize) {
		this.boardSize = boardSize;
		cellArray = new Cell[boardSize][boardSize];
		junctArray = new Junction[boardSize - 1][boardSize - 1];
	}

	// initializes a board...
	// TODO create another init with a string param and player position params
	// for testing purposes
	// TODO get all junction moves and init static array
	public void init() {
		for (int j = 0; j < boardSize; j++) {
			for (int i = 0; i < boardSize; i++) {

				// init cells
				cellArray[i][j] = new Cell();

				// init junctions
				if (j < boardSize - 1 && i < boardSize - 1) {
					junctArray[i][j] = new Junction();
					allWallMoves.add(new WallMove(i, j, Orientation.HORIZONTAL));
					allWallMoves.add(new WallMove(i, j, Orientation.VERTICAL));
				}

			}
		}
		setPiece(4, 0, 0);
		setPiece(4, 8, 1);
	}

	// for use in update and init
	// updates which cells are occupied

	public void setPiece(int x, int y, int pID) {
		cellArray[x][y].occupied = true;
		pieces[pID].setCol(x);
		pieces[pID].setRow(y);
	}

	public void addWall(int x, int y, Orientation O, int pID) {
		wallsLeft[pID]--;
		junctArray[x][y].setOrientation(O);
		if (O == Orientation.HORIZONTAL) {
			if (x < boardSize - 1)
				junctArray[x + 1][y].horizBlocked = true;
			if (x > 0)
				junctArray[x - 1][y].horizBlocked = true;
		}

		if (O == Orientation.VERTICAL) {
			if (y < boardSize - 1)
				junctArray[x][y + 1].vertBlocked = true;
			if (y > 0)
				junctArray[x][y - 1].vertBlocked = true;
		}
	}

	// for use with GameUI that will process graphics
	public String toString() {

		String out = "";

		for (int j = 0; j < boardSize - 1; j++) {
			for (int i = 0; i < boardSize - 1; i++) {
				out += junctArray[i][j].getOrientation().toString().substring(0, 1);
				out += " ";
			}
			out += "\n";
		}
		return out;
	}

	/*
	 * Order for methods 1 checkPossible - divide into smaller sub-methods -check
	 * wall move -check piece move 2 create getAllMoves method -generate 8 piece
	 * moves and pass piece moves and static allJunctionMoves 3 getPossibleMoves
	 * -iterates through all moves and uses checkPossible 3
	 * 
	 */

	public ArrayList<Move> getAllMoves(int pID) {
		ArrayList<Move> mList = new ArrayList<Move>();
		if (wallsLeft[pID] != 0)
			mList.addAll(allWallMoves);
		for (Direction dir : Direction.values()) {
			mList.add(new PieceMove(dir));
		}
		for (Move i : getAllMoves(pID)) {
			if (checkPossible(i, pID))
				mList.add(i);
		}
		return mList;
	}

	@Override
	public ArrayList<Move> getPossibleMoves(int pID) {
		ArrayList<Move> mList = new ArrayList<Move>();
		for (Move i : getAllMoves(pID)) {
			if (checkPossible(i, pID))
				mList.add(i);
		}
		return mList;
	}

	@Override
	public void performMove(Move m, int pID) {
		// TODO Auto-generated method stub
		if (m instanceof PieceMove)
			performPieceMove((PieceMove) m, pID);
		if (m instanceof WallMove)
			performWallMove((WallMove) m, pID);
	}

	private void performPieceMove(PieceMove m, int pID) {
		int x = pieces[pID].getCol();
		int y = pieces[pID].getRow();

		if ((m).getDirection() == Direction.UP) {
			pieces[pID].setCol(x);
			pieces[pID].setRow(y + 1);
			cellArray[x + 1][y].occupied = true;
		}
		if ((m).getDirection() == Direction.DOWN) {
			pieces[pID].setCol(x);
			pieces[pID].setRow(y - 1);
			cellArray[x - 1][y].occupied = true;
		}
		if ((m).getDirection() == Direction.LEFT) {
			pieces[pID].setCol(x - 1);
			pieces[pID].setRow(y);
			cellArray[x][y - 1].occupied = true;
		}
		if ((m).getDirection() == Direction.RIGHT) {
			pieces[pID].setCol(x + 1);
			pieces[pID].setRow(y);
			cellArray[x][y + 1].occupied = true;
		}

		cellArray[x][y].occupied = false;
	}

	private void performWallMove(WallMove m, int pID) {
		wallsLeft[pID]--;

		int x = m.getJunctX();
		int y = m.getJunctY();
		Orientation O = m.getOrientation();

		junctArray[x][y].setOrientation(O);
		if (O == Orientation.HORIZONTAL) {
			if (x < boardSize - 1)
				junctArray[x + 1][y].horizBlocked = true;
			if (x > 0)
				junctArray[x - 1][y].horizBlocked = true;
		}

		if (O == Orientation.VERTICAL) {
			if (y < boardSize - 1)
				junctArray[x][y + 1].vertBlocked = true;
			if (y > 0)
				junctArray[x][y - 1].vertBlocked = true;
		}
	}

	@Override
	public void undoMove(Move m, int pID) {
		if (m instanceof PieceMove)
			undoPieceMove((PieceMove) m, pID);
		if (m instanceof WallMove)
			undoWallMove((WallMove) m, pID);
	}

	private void undoWallMove(WallMove m, int pID) {
		junctArray[m.getJunctX()][m.getJunctY()].setOrientation(Orientation.OPEN);
		wallsLeft[pID]++;
	}

	private void undoPieceMove(PieceMove m, int pID) {
		switch (m.getDirection()) {
		case UP:
			performPieceMove(new PieceMove(Direction.DOWN), pID);
			break;
		case DOWN:
			performPieceMove(new PieceMove(Direction.UP), pID);
			break;
		case LEFT:
			performPieceMove(new PieceMove(Direction.RIGHT), pID);
			break;
		case RIGHT:
			performPieceMove(new PieceMove(Direction.LEFT), pID);
			break;
		}
	}

	@Override
	public double getBoardValue(int pID) {
		// TODO Return Heuristic Value of Board!

		return 0;
	}

	public boolean checkPossible(Move m, int pID) {
		// TODO Auto-generated method stub

		if (m instanceof PieceMove)
			return checkPieceMove((PieceMove) m, pID);
		if (m instanceof WallMove)
			return checkWallMove((WallMove) m, pID);

		return false;
	}

	private boolean checkWallMove(WallMove m, int pID) {

		int x = m.getJunctX();
		int y = m.getJunctY();
		Orientation o = m.getOrientation();

		return (junctArray[x][y].checkPossible(o));
	}

	@Override
	public boolean checkGameOver() {

		return false;
	}

	// returns whether piece move works, true for possible
	public boolean checkPieceMove(PieceMove m, int pID) {

		int x1 = pieces[pID].getCol();
		int y1 = pieces[pID].getRow();

		int x2 = x1;
		int y2 = y1;

		Orientation junct1 = Orientation.OPEN;
		Orientation junct2 = Orientation.OPEN;

		switch (m.getDirection()) {
		case UP:
			y2--;
			junct1 = getJunctionState(x1 - 1, y1 - 1);
			junct2 = getJunctionState(x1, y1 - 1);
			break;
		case DOWN:
			y2++;
			junct1 = getJunctionState(x1 - 1, y1);
			junct2 = getJunctionState(x1, y1);
			break;
		case LEFT:
			x2--;
			junct1 = getJunctionState(x1 - 1, y1 - 1);
			junct2 = getJunctionState(x1 - 1, y1);
			break;
		case RIGHT:
			x2++;
			junct1 = getJunctionState(x1, y1 - 1);
			junct2 = getJunctionState(x1, y1);
			break;

		default:
			break;
		}

		if (y2 == -1 && pID == 0)
			return true;
		if (y2 == boardSize && pID == 1)
			return true;

		if (cellArray[x2][y2].occupied)
			return false;

		return (junct1 == Orientation.OPEN && junct2 == Orientation.OPEN);
	}

	public Orientation getJunctionState(int x, int y) {
		if (x < 0 || x > boardSize - 1 || y < 0 || y > boardSize - 1)
			return Orientation.OPEN;
		else
			return junctArray[x][y].getOrientation();
	}

}
