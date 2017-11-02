
public class Junction {
	
	//horizBlocked and vertBlocked prevent placement of walls in orientations that they cannot fit into
	
	
	
	
	private Orientation orientation = Orientation.OPEN;

	public Junction(){
		
	}
	
	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
		
	}


	

}
