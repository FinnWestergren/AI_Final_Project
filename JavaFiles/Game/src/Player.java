
public class Player {
	
	public int row, column, playerNumber;
	public String name;
	
	//row, col, playerNumber, name
	public Player(int row, int column, int playerNumber, String name) {
		super();
		this.row = row;
		this.column = column;
		this.playerNumber = playerNumber;
		this.name = name;
	}
	
	public String toString() {
		return "p" + playerNumber + ": " + "Name = " + name + ", row = " + row + ", col = " + column; 
	}
	
}
