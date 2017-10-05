//also not done yet
public class WallMove extends Move {
	private int junctX, junctY;
	private Orientation orientation;
	
	public WallMove(int junctX, int junctY, Orientation orientation) {
		super();
		this.junctX = junctX;
		this.junctY = junctY;
		this.orientation = orientation;
	}

	public int getJunctX() {
		return junctX;
	}

	public int getJunctY() {
		return junctY;
	}

	public Orientation getOrientation() {
		return orientation;
	}
	
}
