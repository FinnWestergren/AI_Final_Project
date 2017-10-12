
public class Junction {
	
	//horizBlocked and vertBlocked prevent placement of walls in orientations that they cannot fit into
	
	
	public boolean horizBlocked = false, vertBlocked = false;
	
	
	private Orientation orientation = Orientation.OPEN;

	public Junction(){
		
	}
	
	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
		
	}

	public boolean checkPossible(Orientation O) {
		System.out.println(O + ", " + vertBlocked + " "+ orientation);
		if(this.orientation != Orientation.OPEN) return false;
		if(O == Orientation.VERTICAL) return (!vertBlocked);
		if(O == Orientation.HORIZONTAL) return (!horizBlocked);
		return true;
	}
	
	

}
