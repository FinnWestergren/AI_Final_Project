import java.util.ArrayList;

public class AlphaBetaPlayer extends Player implements AI_Player {

	int maxDepth = 3;

	public AlphaBetaPlayer(int pID) {
		super(pID);
		// TODO Auto-generated constructor stub
	}

	int moveCounter = 0;
	Move[] previousMoves = new Move[2];

	@Override
	public Move getMove(Board board) {
		Move out = null;
		int max = Integer.MIN_VALUE;
		int i = 0;
		for (Move m : board.getPossibleMoves(pID)) {
			System.out.println("iteration " + i + "max " + max);
			board.performMove(m, pID);
			int v = minValue(board, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
			board.undoMove(m, pID);
			if (v > max) {
				if (m instanceof PieceMove) {
					if (previousMoves[moveCounter % 2] != null) {
						if (!(((PieceMove) m).equals(previousMoves[moveCounter % 2]))) {
							max = v;
							out = m;
						} else {
							System.out.println();
						}
					} else {
						max = v;
						out = m;
					}
				} else {
					clearPrevious();
					max = v;
					out = m;

				}
			}
			i++;
		}
		previousMoves[moveCounter % 2] = out;
		moveCounter++;
		return out;
	}

	public int minValue(Board board, int d, int alpha, int beta) {
		int a = alpha;
		int b = beta;

		if (board.checkGameOver() || d == maxDepth)
			return board.getBoardValue(pID);
		for (Move m : suggestedMoves(board, d + 1, (pID + 1) % 2)) {
			board.performMove(m, (pID + 1) % 2);
			int v = maxValue(board, d + 1, a, b);
			board.undoMove(m, (pID + 1) % 2);
			if (v < b)
				b = v;
			if (b <= a) {
				// System.out.println("pruned");

				break;
			}
		}
		return b;
	}

	public void clearPrevious() {
		previousMoves = new Move[2];
	}

	public int maxValue(Board board, int d, int alpha, int beta) {

		int a = alpha;
		int b = beta;

		if (board.checkGameOver() || d == maxDepth)
			return board.getBoardValue(pID);

		for (Move m : suggestedMoves(board, d + 1, pID)) {
			board.performMove(m, pID);
			int v = minValue(board, d + 1, alpha, beta);
			board.undoMove(m, pID);

			if (v > a)
				a = v;
			if (b <= a) {
				// System.out.println("pruned");

				break;
			}
		}

		return a;
	}

	public ArrayList<Move> suggestedMoves(Board b, int depth, int p) {

		if (depth == 0 || depth == 1) {
			return b.getPossibleMoves(p);

		}

		int count = 0;
		ArrayList<Move> oldList = b.getPossibleMoves(p);
		ArrayList<Move> mList = new ArrayList<Move>(oldList.size() / depth);
		for (Move m : oldList) {

			if (count < oldList.size() / depth) {
				mList.add(m);
			} else
				break;

			count++;
		}

		return mList;

	}

}
