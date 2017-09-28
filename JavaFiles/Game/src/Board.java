public class Board {

	public int boardSize; // size of one side
	public Cell[][] cellArray;
	public Junction[][] junctArray;

	public Board(int boardSize) {
		this.boardSize = boardSize;
		cellArray = new Cell[boardSize][boardSize];
		junctArray = new Junction[boardSize - 1][boardSize - 1];
	}

	public void init(Player p1, Player p2) {
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {

				// init cells
				cellArray[i][j] = new Cell();

				// init junctions
				if (i < boardSize - 1 && j < boardSize - 1)
					junctArray[i][j] = new Junction();
			}
		}
		setOccupiedCells(p1, p2);
	}

	//for use in update and init
	public void setOccupiedCells(Player p1, Player p2) {
		cellArray[p1.column][p1.row].occupied = true;
		cellArray[p2.column][p2.row].occupied = true;
	}

	
	//for use with an external program that will process graphics
	public String toString() {
		
		/*
		 * should print something like this
			9
			O O O O O O O O 
			O O O O O O O O
			O O O O O O O O
			O O O V H O O O
			O O O O O O O O
			O O O O O O O O
			O O O O O O O O
			O O O O O O O O 
			
			p1: Name = greg2000, row = 4, col = 0
			p2: Name = shrimpy, row = 4, col = 8
		*/
		
		String out = boardSize + "\n";

		for (int i = 0; i < boardSize - 1; i++) {
			for (int j = 0; j < boardSize - 1; j++) {
				out += junctArray[i][j].getOrientation().toString().substring(0,1); 
				out += " ";
			}
			out += "\n";
		}
		return out;
	}
}
