import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Board implements BoardFeatures {

	public int boardSize; // size of one side
	public Cell[][] cellArray;
	public Junction[][] junctArray;
	public CoordinatePair[] pieceLocation = { new CoordinatePair(), new CoordinatePair() };
	public int[] wallsLeft = { 10, 10 };
	public static ArrayList<WallMove> allWallMoves = new ArrayList<WallMove>();
	public boolean ignoreOccupiedFlag; // used when a player is blocking the only path
	boolean gameOver = false;
	public int winner = -1;

	public Board(int boardSize) {
		this.boardSize = boardSize;
		cellArray = new Cell[boardSize][boardSize];
		junctArray = new Junction[boardSize - 1][boardSize - 1];
		
	}

	// TODO refactor using new coord pair class

	public void init(File readFile) {
		allWallMoves.clear();
		for (int j = 0; j < boardSize; j++) {
			for (int i = 0; i < boardSize; i++) {

				// init cells
				cellArray[i][j] = new Cell();
				if (j < boardSize - 1 && i < boardSize - 1) 
					junctArray[i][j] = new Junction();

				

			}
		}
		initAllWallMoves();
		try {
			Scanner boardScanner = new Scanner(readFile);
			int x0 = boardScanner.nextInt();
			int y0 = boardScanner.nextInt();
			wallsLeft[0] = boardScanner.nextInt();
			int x1 = boardScanner.nextInt();
			int y1 = boardScanner.nextInt();
			wallsLeft[1] = boardScanner.nextInt();
			CoordinatePair location0 = new CoordinatePair(x0, y0);
			CoordinatePair location1 = new CoordinatePair(x1, y1);
			setPiece(location0, 0);
			setPiece(location1, 1);

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

	private void initAllWallMoves() {
		//incorrect!! needs to be fixed
		int count = 0;
		for(int i = (boardSize - 1)/2; i >= 0; i --) {
			for(int j = (boardSize - 1)/2; j >= 0; j --) {
				
				
				int a = (boardSize - 1) - i - 1;
				int b = (boardSize - 1) - j - 1;
				System.out.println(i + " " + a + " count " + count * 4);

				allWallMoves.add(new WallMove(i, j, Orientation.HORIZONTAL));
				allWallMoves.add(new WallMove(i, j, Orientation.VERTICAL));
				
				allWallMoves.add(new WallMove(a, b, Orientation.HORIZONTAL));
				allWallMoves.add(new WallMove(a, b, Orientation.VERTICAL));
				count++;

			}
			
			
		}
		
		allWallMoves.clear();
		count = 0;
		for(int i = boardSize - 2; i >= 0; i --) {
			for(int j = boardSize - 2; j >= 0; j --) {
				System.out.println(count);
				allWallMoves.add(new WallMove(i, j, Orientation.HORIZONTAL));
				allWallMoves.add(new WallMove(i, j, Orientation.VERTICAL));
				count ++;
			}
			
			}
		
		
	}

	// for use in update and init
	// updates which cells are occupied

	public void setPiece(CoordinatePair cp, int pID) {

		int x = cp.getX();
		int y = cp.getY();
		if (y < boardSize && y > -1) { // if not gameover
			cellArray[x][y].occupied = true;

		} else {
			gameOver = true;
		}
		pieceLocation[pID] = cp;
	}

	public void addWall(int x, int y, Orientation O, int pID) {

		junctArray[x][y].setOrientation(O);
	}

	private ArrayList<PieceMove> getAllPieceMoves(int pID) {
		ArrayList<PieceMove> mList = new ArrayList<PieceMove>();
		CoordinatePair from = pieceLocation[pID];

		for (Direction d : Direction.values()) {
			CoordinatePair step1 = from.getNextCoordinatePairFromDirection(d); // first step;
			mList.add(new PieceMove(from, step1));
		}

		return mList;
	}

	private ArrayList<PieceMove> getAllDoublePieceMoves(int pID) {
		ArrayList<PieceMove> mList = new ArrayList<PieceMove>();
		CoordinatePair from = pieceLocation[pID];

		for (Direction d : Direction.values()) {
			CoordinatePair step1 = from.getNextCoordinatePairFromDirection(d); // first step;
			for (Direction d2 : Direction.values()) {
				CoordinatePair step2 = step1.getNextCoordinatePairFromDirection(d2); // second step;
				mList.add(new PieceMove(from, step1, step2));
			}
		}
		return mList;
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
		mList.addAll(getAllDoublePieceMoves(pID));
		mList.addAll(getAllPieceMoves(pID));
		if (wallsLeft[pID] != 0)
			mList.addAll(allWallMoves);

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

	public boolean checkOccupied(CoordinatePair c) {
		if (!withinBounds(c))
			return false;
		return cellArray[c.getX()][c.getY()].occupied;
	}

	private boolean withinBounds(CoordinatePair c) {
		if (c.getX() >= boardSize || c.getX() < 0)
			return false;
		if (c.getY() >= boardSize || c.getY() < 0)
			return false;
		return true;
	}

	public boolean checkPossible(Move m, int pID) {
		// TODO Auto-generated method stub

		if (m instanceof PieceMove)
			return checkPieceMove((PieceMove) m, pID);
		if (m instanceof WallMove)
			return checkWallMove((WallMove) m, pID);

		return false;
	}

	// returns whether piece move works, true for possible
	public boolean checkPieceMove(PieceMove m, int pID) {

		// System.out.println("checking...");

		int x1 = m.getFrom().getX(), y1 = m.getStep1().getY();

		Orientation junct1 = Orientation.OPEN;
		Orientation junct2 = Orientation.OPEN;
		Orientation OrientationInQuestion = Orientation.OPEN;
		
		if (checkOccupied(m.getTo())) return false;

		switch (m.getFirstDirection()) {
		case UP:
			junct1 = getJunctionState(x1 - 1, y1 - 1);
			junct2 = getJunctionState(x1, y1 - 1);
			OrientationInQuestion = Orientation.HORIZONTAL;
			break;
		case DOWN:
			junct1 = getJunctionState(x1 - 1, y1);
			junct2 = getJunctionState(x1, y1);
			OrientationInQuestion = Orientation.HORIZONTAL;
			break;
		case LEFT:
			junct1 = getJunctionState(x1 - 1, y1 - 1);
			junct2 = getJunctionState(x1 - 1, y1);
			OrientationInQuestion = Orientation.VERTICAL;
			break;
		case RIGHT:
			junct1 = getJunctionState(x1, y1 - 1);
			junct2 = getJunctionState(x1, y1);
			OrientationInQuestion = Orientation.VERTICAL;
			break;

		default:
			break;
		}

		int yGoal = (pID == 0) ? -1 : boardSize;

		if (junct1 == OrientationInQuestion || junct2 == OrientationInQuestion)
			return false;

		if (!withinBounds(m.getStep1())) {
			if (m.getTo().getY() != yGoal)
				return false;
		}

		if (m.getStep2() != null) {
			if (checkOccupied(m.getStep1())) {
				PieceMove move2 = new PieceMove(m.getStep1(), m.getStep2());
				return checkPieceMove(move2, pID);
			} else
				return false;
		}

		return true;

	}

	private boolean checkWallMove(WallMove m, int pID) {
		if (wallsLeft[pID] == 0)
			return false;

		int x = m.getJunctX();
		int y = m.getJunctY();
		Orientation o = m.getOrientation();
		// check if placement physically fits

		if (getJunctionState(x, y) != Orientation.OPEN)
			return false;

		switch (o) {

		case HORIZONTAL:
			if (getJunctionState(x + 1, y) == Orientation.HORIZONTAL)
				return false;
			if (getJunctionState(x - 1, y) == Orientation.HORIZONTAL)
				return false;
			break;
		case VERTICAL:
			if (getJunctionState(x, y + 1) == Orientation.VERTICAL)
				return false;
			if (getJunctionState(x, y - 1) == Orientation.VERTICAL)
				return false;
			break;
		}

		performMove(m, pID);
		// check if wall placement makes a full block
		if (manhattanDistance(pID) == -1 || manhattanDistance((pID + 1) % 2) == -1) {
			undoMove(m, pID);
			return false;
		}
		undoMove(m, pID);

		return true;

	}

	@Override
	public void performMove(Move m, int pID) {
		// TODO Auto-generated method stub
		if (m instanceof PieceMove)
			performPieceMove((PieceMove) m, pID);
		if (m instanceof WallMove)
			performWallMove((WallMove) m, pID);
	}

	// moves your feckin piece
	private void performPieceMove(PieceMove m, int pID) {

		if (withinBounds(m.getFrom())) {
			cellArray[m.getFrom().getX()][m.getFrom().getY()].occupied = false;
			setPiece(m.getTo(), pID);
		}
	}

	// puts down a feckin wall
	private void performWallMove(WallMove m, int pID) {

		wallsLeft[pID]--;

		int x = m.getJunctX();
		int y = m.getJunctY();

		Orientation O = m.getOrientation();

		addWall(x, y, O, pID);
	}

	@Override
	public void undoMove(Move m, int pID) {
		gameOver = false;
		if (m instanceof PieceMove)
			undoPieceMove((PieceMove) m, pID);
		if (m instanceof WallMove)
			undoWallMove((WallMove) m, pID);

	}

	private void undoWallMove(WallMove m, int pID) {
		int y = m.getJunctY();
		int x = m.getJunctX();

		junctArray[x][y].setOrientation(Orientation.OPEN);

		wallsLeft[pID]++;
	}

	// ooh look at this method it's fancy and has a switch statement, how magical
	private void undoPieceMove(PieceMove m, int pID) {
		performPieceMove(m.getReverse(), pID);
	}

	@Override
	public int getBoardValue(int pID) {
		// decide heuristic here. for now just this :
		// enemy manhattan distance - self manhattan
		// alphabeta should be trying to maximize this value

		// if the state is a game over state, return arbitrarily large/small values


		int enemyPID = (pID + 1) % 2;
		int self = -manhattanDistance(pID);
		int enemy = manhattanDistance(enemyPID) ;

		return enemy + self - 1;
	}

	// method does a BFS until it finds the goal state
	public int manhattanDistance(int pID) {

		int distSoFar = 0;
		CoordinatePair pStart = new CoordinatePair(pieceLocation[pID].getX(), pieceLocation[pID].getY());

		// goalY is -1 if player 0 (moving up), 9 if player 1 (moving down)
		int goalY = (pID == 0) ? -1 : boardSize;

		// searched keeps track of what the BFS has already looked at
		boolean[][] searched = new boolean[boardSize][boardSize];

		// searchStack is the BFS stack
		MDQueue searchQueue = new MDQueue();
		searchQueue.enqueue(new ManhattanDistanceNode(pStart, distSoFar));

		while (true) {
			ManhattanDistanceNode currentNode;
			// get the next coordinates off the stack if stack has anything left to give
			if (searchQueue.back != searchQueue.front)
				currentNode = searchQueue.dequeue();
			else { // exit if stuck in loop. used for checking if wall placement is legal
				distSoFar = -1;
				break;
			}
			// //System.out.println("popped " + p.x + ", " + p.y);
			distSoFar = currentNode.dist;
			// base case: if player has reached end of board;
			if (currentNode.location.getY() == goalY)
				break;
			pieceLocation[pID] = currentNode.location;
			ArrayList<PieceMove> mList = new ArrayList<PieceMove>();
			mList.addAll(getAllDoublePieceMoves(pID));
			mList.addAll(getAllPieceMoves(pID));
			// iterate through all moves from this point
			for (PieceMove m : mList) {
				if (checkPieceMove(m, pID)) {
					ManhattanDistanceNode moveTo = new ManhattanDistanceNode(m.getTo(), distSoFar + 1);
					// check if next is gameover

					if (moveTo.location.getY() == goalY) {
						pieceLocation[pID] = pStart;
						return distSoFar + 1;
					}
					// check if move has already been looked at

					if (!searched[moveTo.location.getX()][moveTo.location.getY()]) {
						searchQueue.enqueue(moveTo);
						searched[moveTo.location.getX()][moveTo.location.getY()] = true;
					}
				}
			}
		}
		pieceLocation[pID] = pStart;
		return distSoFar;
	}

	@Override
	public boolean checkGameOver() {
		return (!withinBounds(pieceLocation[0]) || !withinBounds(pieceLocation[1]));
	}

	public Orientation getJunctionState(int x, int y) {
		if (x < 0 || x >= boardSize - 1 || y < 0 || y >= boardSize - 1) {

			// //System.out.println(x + " ye " + y);
			return Orientation.OPEN;
		}
		// //System.out.println(junctArray[x][y].getOrientation());
		return junctArray[x][y].getOrientation();
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

	// for use in manhattanDistance and the MDQueue
	private class ManhattanDistanceNode {

		public int dist;
		CoordinatePair location;

		public ManhattanDistanceNode(CoordinatePair location, int dist) {
			this.location = location;
			this.dist = dist;
		}
	}

	// used for breadth first search in manhattan()
	private class MDQueue {

		private ArrayList<ManhattanDistanceNode> stackList = new ArrayList<ManhattanDistanceNode>();
		public int front = 0;
		public int back = 0;

		public MDQueue() {

		}

		public void enqueue(ManhattanDistanceNode c) {
			// //System.out.println("adding " + c.x + ", " + c.y);
			stackList.add(c);
			back++;
		}

		public ManhattanDistanceNode dequeue() {
			// //System.out.println(front + " " + back);
			ManhattanDistanceNode c = stackList.get(front);
			front++;

			return c;
		}
	}
}
