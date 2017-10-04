public class Board implements BoardFeatures {

	public int boardSize; // size of one side
	public Cell[][] cellArray;
	public Junction[][] junctArray;
	public Move[] allJuncMoves;
	public GamePiece[] pieces = { new GamePiece(), new GamePiece() };
	public int[] wallsLeft = { 10, 10 };

	public Board(int boardSize) {
		this.boardSize = boardSize;
		cellArray = new Cell[boardSize][boardSize];
		junctArray = new Junction[boardSize - 1][boardSize - 1];
	}

	
	//initializes a board...
	//TODO create another init with a string param and player position params 
	//for testing purposes  
	//TODO get all junction moves and init static array
	public void init() {
		for (int j = 0; j < boardSize; j++) {
			for (int i = 0; i < boardSize; i++) {

				// init cells
				cellArray[i][j] = new Cell();

				// init junctions
				if (j < boardSize - 1 && i < boardSize - 1)
					junctArray[i][j] = new Junction();

			}
		}
		movePiece(null, 4, 0, 0);
		movePiece(null, 4, 8, 1);
	}

	// for use in update and init
	// updates which cells are occupied
	public void movePiece(Cell from, int x, int y, int pID) {
		if(from != null) from.occupied = false;
		cellArray[x][y].occupied = true;
		pieces[pID].setCol(x);
		pieces[pID].setRow(y);
	}

	public void addWall(int x, int y, Orientation O, int pID) {
		wallsLeft[pID]--;
		junctArray[x][y].setOrientation(O);
		if(O == Orientation.HORIZONTAL) {
			if(x < boardSize - 1)	junctArray[x+1][y].horizBlocked = true;
			if(x > 0)				junctArray[x-1][y].horizBlocked = true;
		}
		
		if(O == Orientation.VERTICAL) {
			if(y < boardSize - 1) 	junctArray[x][y+1].vertBlocked = true;
			if(y > 0) 				junctArray[x][y-1].vertBlocked = true;
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
	 * Order for methods
	 * 1 checkPossible
	 * 		- divide into smaller sub-methods
	 * 			-check wall move
	 * 			-check piece move
	 * 2 create getAllMoves method
	 * 		-generate 8 piece moves and pass piece moves and static allJunctionMoves
	 * 3 getPossibleMoves
	 * 		-iterates through all moves and uses checkPossible
	 * 3 
	 * 
	 */
	
	@Override
	public Move[] getAllMoves(int pID) {
		//TODO 
		return null;
	}
	
	@Override
	public Move[] getPossibleMoves(int pID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void performMove(Move m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void undoMove(Move m) {
		// TODO Auto-generated method stub

	}

	@Override
	public double getBoardValue(int pID) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean checkPossible(Move m) {
		// TODO Auto-generated method stub
		
		//if m = PieceMove
		//	check if space in that direction is blocked by a wall
		//	if not, return true
		//if m = WallMove
		//	check if junction is occupied by a wall or is vertBlocked or horizBlocked
		
		//if (m.toString().equals(anObject))
		return false;
	}

	@Override
	public boolean checkGameOver() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
