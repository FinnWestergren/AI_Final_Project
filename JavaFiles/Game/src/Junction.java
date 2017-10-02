
public class Junction {
	
	//horizBlocked and vertBlocked prevent placement of walls in orientations that they cannot fit into
	
	
	public boolean horizBlocked, vertBlocked, occupied;
	
	
	private Orientation orientation = Orientation.OPEN;

	public Junction(){
		
	}
	
	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
		occupied = (orientation != Orientation.OPEN);
	}

	public boolean checkPossible(Orientation O) {
		if(occupied) return false;
		if(O == Orientation.VERTICAL) return vertBlocked;
		if(O == Orientation.HORIZONTAL) return horizBlocked;
		return true;
	}

}
