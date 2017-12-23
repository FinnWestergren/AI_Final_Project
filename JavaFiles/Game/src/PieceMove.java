
public class PieceMove extends Move {

	private CoordinatePair from, // the previous location
			step1, // the location to move to
			step2 = null; // implies step 1 is an intermediate location (used for double jumps)

	public PieceMove(CoordinatePair from, CoordinatePair to) {
		super();
		this.from = from;
		this.step1 = to;

	}

	public PieceMove(CoordinatePair from, CoordinatePair step1, CoordinatePair step2) {
		super();
		this.from = from;
		this.step1 = step1;
		this.step2 = step2;

	}

	public CoordinatePair getStep1() {
		return step1;
	}

	public CoordinatePair getFrom() {
		return from;
	}

	public CoordinatePair getStep2() {
		return step2;
	}

	public Direction getFirstDirection() {
		if (from.getX() < step1.getX())
			return Direction.RIGHT;
		if (from.getX() > step1.getX())
			return Direction.LEFT;
		if (from.getY() > step1.getY())
			return Direction.UP;
		if (from.getY() < step1.getY())
			return Direction.DOWN;
		return null;
	}

	public CoordinatePair getTo() {
		if (step2 == null)
			return step1;
		return step2;
	}

	public PieceMove getReverse() {
		return new PieceMove(getTo(), from);
	}

	public boolean equals(Move m) {
		if (!(m instanceof PieceMove))
			return false;
		PieceMove pm = (PieceMove) m;
		return (pm.getTo() == getTo() && pm.getFrom() == getFrom());
	}

}
