import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Board implements BoardFeatures {

	public int boardSize; // size of one side
	public Cell[][] cellArray;
	public Junction[][] junctArray;
	public Move[] allJuncMoves;
	public GamePiece[] pieces = { new GamePiece(), new GamePiece() };
	public int[] wallsLeft = { 10, 10 };
	public static ArrayList<WallMove> allWallMoves = new ArrayList<WallMove>();
	private boolean ignoreOccupiedFlag; // used when a player is blocking the only path

	public Board(int boardSize) {
		this.boardSize = boardSize;
		cellArray = new Cell[boardSize][boardSize];
		junctArray = new Junction[boardSize - 1][boardSize - 1];
	}

	// initializes a board...
	// TODO create another init with a string param and player position params
	// for testing purposes
	// TODO get all junction moves and init static array
	public void init(File readFile) {
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
		try {
			Scanner boardScanner = new Scanner(readFile);
			int x0 = boardScanner.nextInt();
			int y0 = boardScanner.nextInt();
			wallsLeft[0] = boardScanner.nextInt();
			int x1 = boardScanner.nextInt();
			int y1 = boardScanner.nextInt();
			wallsLeft[1] = boardScanner.nextInt();

			setPiece(x0, y0, 0);
			setPiece(x1, y1, 1);

			for (int j = 0; j < boardSize - 1; j++)
				for (int i = 0; i < boardSize - 1; i++) {
					String junct = boardScanner.next();
					// //System.out.println(junct);
					switch (junct) {
					case "O":
						junctArray[i][j].setOrientation(Orientation.OPEN);
						break;
					case "H":
						junctArray[i][j].setOrientation(Orientation.HORIZONTAL);
						break;
					case "V":
						junctArray[i][j].setOrientation(Orientation.VERTICAL);
						break;
					default:
						break;
					}
				}
			boardScanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// for use in update and init
	// updates which cells are occupied

	public void setPiece(int x, int y, int pID) {
		
		if (y < boardSize && y > -1) //if not gameover
		cellArray[x][y].occupied = true;
		pieces[pID].setCol(x);
		pieces[pID].setRow(y);
		
	}

	public void addWall(int x, int y, Orientation O, int pID) {

		if (O == Orientation.HORIZONTAL) {
			if (x < boardSize - 2)
				junctArray[x + 1][y].horizBlocked = true;
			if (x > 1)
				junctArray[x - 1][y].horizBlocked = true;
		}

		if (O == Orientation.VERTICAL) {
			if (y < boardSize - 2)
				junctArray[x][y + 1].vertBlocked = true;
			if (y > 1)
				junctArray[x][y - 1].vertBlocked = true;
		}
		junctArray[x][y].setOrientation(O);
	}

	// for use with GameUI that will process graphics
	public String toString() {

		String out = "";

		out += wallsLeft[0] + " " + wallsLeft[1] + "\n";

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
		int x2 = x;
		int y2 = y;

		switch (m.getDirection()) {
		case UP:
			y2--;
			break;
		case DOWN:
			y2++;
			break;
		case LEFT:
			x2--;
			break;
		case RIGHT:
			x2++;
			break;
		}
		setPiece(x2, y2, pID);

		cellArray[x][y].occupied = false;
	}

	private void performWallMove(WallMove m, int pID) {

		wallsLeft[pID]--;

		int x = m.getJunctX();
		int y = m.getJunctY();

		Orientation O = m.getOrientation();

		addWall(x, y, O, pID);
	}

	@Override
	public void undoMove(Move m, int pID) {
		if (m instanceof PieceMove)
			undoPieceMove((PieceMove) m, pID);
		if (m instanceof WallMove)
			undoWallMove((WallMove) m, pID);
	}

	private void undoWallMove(WallMove m, int pID) {
		int y = m.getJunctY();
		int x = m.getJunctX();

		if (m.getOrientation() == Orientation.VERTICAL) {

			if (y > 1)
				junctArray[x][y - 1].vertBlocked = false;
			if (y < boardSize - 2)
				junctArray[x][y + 1].vertBlocked = false;

		}

		if (m.getOrientation() == Orientation.HORIZONTAL) {

			if (x > 1)
				junctArray[x - 1][y].horizBlocked = false;
			if (x < boardSize - 2)
				junctArray[x + 1][y].horizBlocked = false;

		}

		junctArray[x][y].setOrientation(Orientation.OPEN);

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
	public int getBoardValue(int pID) {
		// decide heuristic here. for now just this :
		//  enemy manhattan distance - self manhattan
		// alphabeta should be trying to maximize this value
		
		// if the state is a game over state, return arbitrarily large/small values

		if(pieces[pID].getRow() == -1 || pieces[pID].getRow() == boardSize) return Integer.MAX_VALUE;
		if(pieces[(pID + 1) % 2].getRow() == -1 || pieces[(pID + 1) % 2].getRow() == boardSize) return Integer.MIN_VALUE;
		
		int enemyPID = (pID + 1) % 2;
		int selfManhattan = manhattanDistance(pID);
		int enemyManhattan = manhattanDistance(enemyPID);
		
		

		return enemyManhattan - selfManhattan;
	}

	// method does a BFS until it finds the goal state
	public int manhattanDistance(int pID) {

		int distSoFar = 0;
		int xStart = pieces[pID].getCol();
		int yStart = pieces[pID].getRow();

		// goalY is -1 if player 0 (moving up), 9 if player 1 (moving down)
		int goalY = (pID == 0) ? -1 : boardSize;

		// searched keeps track of what the BFS has already looked at
		boolean[][] searched = new boolean[boardSize][boardSize];

		// searchStack is the BFS stack
		CPQueue searchQueue = new CPQueue();
		searchQueue.enqueue(new CoordinatePair(xStart, yStart, distSoFar));

		int x;
		int y;

		int count = 0;
		while (true) {
			CoordinatePair p;
			// get the next coordinates off the stack if stack has anything left to give
			if (searchQueue.back != searchQueue.front)
				p = searchQueue.dequeue();
			else { // exit if stuck in loop. used for checking if wall placement is legal
				distSoFar = -1;
				break;
			}
			// //System.out.println("popped " + p.x + ", " + p.y);
			x = p.x;
			y = p.y;
			distSoFar = p.dist;
			// base case: if player has reached end of board;
			if (y == goalY)
				break;
			pieces[pID].setCol(x);
			pieces[pID].setRow(y);

			// iterate through all moves from this point
			for (Direction dir : Direction.values()) {
				PieceMove m = new PieceMove(dir);
				// check if move is legal
				if (checkPieceMove(m, pID)) {
					CoordinatePair moveTo = getCoordinatePairFromMove(m, pID);
					// check if next is gameover

					if (moveTo.y != goalY) {
						// check if move has already been looked at
						if (!searched[moveTo.x][moveTo.y]) {
							moveTo.dist = distSoFar + 1;
							searchQueue.enqueue(moveTo);
							searched[moveTo.x][moveTo.y] = true;
						}

						// if goal state found
					} else {
						moveTo.dist = distSoFar + 1;
						searchQueue.enqueue(moveTo);
						break;
					}
				}
			}

			count++;

		}
		pieces[pID].setCol(xStart);
		pieces[pID].setRow(yStart);
		return distSoFar;
	}

	// returns the coordinate pair that a move brings you to
	public CoordinatePair getCoordinatePairFromMove(PieceMove m, int pID) {
		int x = pieces[pID].getCol();
		int y = pieces[pID].getRow();

		switch (m.getDirection()) {
		case UP:
			y--;

			break;
		case DOWN:
			y++;

			break;
		case LEFT:
			x--;

			break;
		case RIGHT:
			x++;

			break;

		default:
			break;

		}
		return new CoordinatePair(x, y);
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
		if (wallsLeft[pID] == 0)
			return false;

		int x = m.getJunctX();
		int y = m.getJunctY();
		Orientation o = m.getOrientation();
		// check if placement physically fits
		if (junctArray[x][y].checkPossible(o)) {
			performMove(m, pID);
			// check if wall placement makes a full block
			ignoreOccupiedFlag = true;
			if (manhattanDistance(pID) == -1 || manhattanDistance((pID + 1) % 2) == -1) {
				ignoreOccupiedFlag = false; 
				undoMove(m, pID);
				return false;
			}
			undoMove(m, pID);
		}
		ignoreOccupiedFlag = false;
		return (junctArray[x][y].checkPossible(o));
	}

	@Override
	public boolean checkGameOver() {
		// TODO return whether game over;
		return false;
	}

	// returns whether piece move works, true for possible
	public boolean checkPieceMove(PieceMove m, int pID) {

		//System.out.println("checking...");

		int x1 = pieces[pID].getCol();
		int y1 = pieces[pID].getRow();

		Orientation OrientationInQuestion = Orientation.OPEN;

		int x2 = x1;
		int y2 = y1;

		Orientation junct1 = Orientation.OPEN;
		Orientation junct2 = Orientation.OPEN;

		switch (m.getDirection()) {
		case UP:
			y2--;
			junct1 = getJunctionState(x1 - 1, y1 - 1);
			junct2 = getJunctionState(x1, y1 - 1);
			OrientationInQuestion = Orientation.HORIZONTAL;
			break;
		case DOWN:
			y2++;
			junct1 = getJunctionState(x1 - 1, y1);
			junct2 = getJunctionState(x1, y1);
			OrientationInQuestion = Orientation.HORIZONTAL;
			break;
		case LEFT:
			x2--;
			junct1 = getJunctionState(x1 - 1, y1 - 1);
			junct2 = getJunctionState(x1 - 1, y1);
			OrientationInQuestion = Orientation.VERTICAL;
			break;
		case RIGHT:
			x2++;
			junct1 = getJunctionState(x1, y1 - 1);
			junct2 = getJunctionState(x1, y1);
			OrientationInQuestion = Orientation.VERTICAL;
			break;

		default:
			break;
		}

		if (x2 == -1 || x2 == boardSize)
			return false;

		if (y2 == -1 && pID == 0)
			return true;
		if (y2 == boardSize && pID == 1)
			return true;

		if (y2 == -1 && pID == 1)
			return false;
		if (y2 == boardSize && pID == 0)
			return false;

		//System.out.println("checking " + x2 + ", " + y2);
		if (cellArray[x2][y2].occupied && !ignoreOccupiedFlag)
			return false;

		return (junct1 != OrientationInQuestion && junct2 != OrientationInQuestion);

	}

	public Orientation getJunctionState(int x, int y) {
		if (x < 0 || x >= boardSize - 1 || y < 0 || y >= boardSize - 1) {

			// //System.out.println(x + " ye " + y);
			return Orientation.OPEN;
		}
		// //System.out.println(junctArray[x][y].getOrientation());
		return junctArray[x][y].getOrientation();
	}

	// simple struct for listing 2 coordinate ints in one type, for use in BFS. also
	// contains a distance element;
	private class CoordinatePair {
		public int x, y, dist;

		public CoordinatePair(int x, int y, int dist) {
			this.x = x;
			this.y = y;
			this.dist = dist;
		}

		public CoordinatePair(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	// used for breadth first search in manhattan()
	private class CPQueue {

		private ArrayList<CoordinatePair> stackList = new ArrayList<CoordinatePair>();
		public int front = 0;
		public int back = 0;

		public CPQueue() {

		}

		public void enqueue(CoordinatePair c) {
			// //System.out.println("adding " + c.x + ", " + c.y);
			stackList.add(c);
			back++;
		}

		public CoordinatePair dequeue() {

			// //System.out.println(front + " " + back);
			CoordinatePair c = stackList.get(front);
			front++;

			return c;
		}

	}

}
