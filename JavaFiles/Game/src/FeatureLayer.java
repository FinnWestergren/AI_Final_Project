import java.util.ArrayList;

public class FeatureLayer extends NeuralLayer {
	
	final static int WALLFEAT = 128, CELLFEAT = 81, WALLSLEFTFEAT= 2, MANHATTANFEAT = 2;
	
	

	public FeatureLayer(Board board) {
		super(WALLFEAT + CELLFEAT + WALLSLEFTFEAT + MANHATTANFEAT); // Walls, Cells, walls left, ManhattanDistance , respectively
		nodeList = new ArrayList<Node>(size);
		updateWallFeatures(board);
		
	}
	
	
	// sets the first 64 to horizontal, second 64 to vertical
	private void updateWallFeatures(Board board) {
		for (int i = 0; i < WALLFEAT/2; i ++) {
			int j = i * 2;
			FeatureNode horiz, vert;
			switch(board.junctArray[i%8][i/8].getOrientation()) {
			case OPEN:
				horiz = new featureNode(0);
				vert = new featureNode(0);
				break;
			case VERTICAL:
				horiz = new featureNode(-1);
				vert = new featureNode(1);
				break;
			case HORIZONTAL:
				horiz = new featureNode(1);
				vert = new featureNode(-1);
				break;
			default:
				break;
				
			}
			
			nodeList.set(i, horiz);
			nodeList.set(j, vert);
			
		}
	}

}
