
public class Board {

	public int boardSize; //size of one side
	public Cell[][] cellArray;
	public Junction[][] junctArray;

	
	public Board (int boardSize) {
		this.boardSize = boardSize;
		cellArray = new Cell[boardSize][boardSize];
		junctArray = new Junction[boardSize-1][boardSize-1];
	}
	
	public void init(){
		for (int i = 0 ; i < boardSize; i++){
			for (int j = 0 ; j < boardSize; j ++){
				
				//init cells
				cellArray[i][j] = new Cell();
				
				//init junctions
				if(i < boardSize - 1 && j < boardSize - 1) junctArray[i][j] = new Junction();
			}
		}
	}
}
