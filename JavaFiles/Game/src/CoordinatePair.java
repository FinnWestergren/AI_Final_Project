
public class CoordinatePair {

	private int x = 0, y = 0;

	public CoordinatePair(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public CoordinatePair getNextCoordinatePairFromDirection(Direction d) {
		int x2 = x, y2 = y;

		switch (d) {
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

		return new CoordinatePair(x2, y2);

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public CoordinatePair() {
		super();
	}

}
