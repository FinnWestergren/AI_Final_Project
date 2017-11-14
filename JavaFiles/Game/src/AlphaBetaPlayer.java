import java.util.ArrayList;

public class AlphaBetaPlayer extends Player implements AI_Player {
<<<<<<< HEAD
	int maxDepth = 1;
=======
	int maxDepth =1;

>>>>>>> 8ca67085bac714f6966fdc549b161d718e96fa48
	public AlphaBetaPlayer(int pID) {
		super(pID);
		// TODO Auto-generated constructor stub
	}

	int moveCounter = 0;
	Move[] previousMoves = new Move[2];

	@Override
	public Move getMove(BoardFeatures board) {
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

	public int minValue(BoardFeatures board, int d, int alpha, int beta) {
		int a = alpha;
		int b = beta;
<<<<<<< HEAD

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
=======
		
		if (board.checkGameOver() || d == maxDepth) return board.getBoardValue(pID);
		for(Move m: suggestedMoves(board, d+1,(pID + 1) %2)) {
			board.performMove(m,(pID + 1) %2);
			int v = maxValue(board, d+1, a, b);
			board.undoMove(m, (pID + 1) %2);
			if(v<b) b = v;
			if(b<= a) {
				//System.out.println("pruned");
>>>>>>> 8ca67085bac714f6966fdc549b161d718e96fa48
				break;
			}
		}
		return b;
	}

	public void clearPrevious() {
		previousMoves = new Move[2];
	}

	public int maxValue(BoardFeatures board, int d, int alpha, int beta) {

		int a = alpha;
		int b = beta;

		if (board.checkGameOver() || d == maxDepth)
			return board.getBoardValue(pID);

		for (Move m : suggestedMoves(board, d + 1, pID)) {
			board.performMove(m, pID);
			int v = minValue(board, d + 1, alpha, beta);
			board.undoMove(m, pID);
<<<<<<< HEAD
			if (v > a)
				a = v;
			if (b <= a) {
				// System.out.println("pruned");
=======
			if(v>a) a = v;
			if(b<=a) {
				//System.out.println("pruned");
>>>>>>> 8ca67085bac714f6966fdc549b161d718e96fa48
				break;
			}
		}

		return a;
	}

	public ArrayList<Move> suggestedMoves(BoardFeatures b, int depth, int p) {

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
