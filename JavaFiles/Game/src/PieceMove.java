
public class PieceMove extends Move{

	private Direction direction;
	public PieceMove(Direction direction) {
		super();
		this.direction = direction;
	}
	
	public Direction getDirection() {

		return this.direction;

	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}
	
}
