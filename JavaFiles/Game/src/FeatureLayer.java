import java.util.ArrayList;

public class FeatureLayer extends NeuralLayer {

	final static int WALLFEAT = 128, CELLFEAT = 81, WALLSLEFTFEAT = 2, MANHATTANFEAT = 2, PLAYERFEAT = 4;
	
	private int pID;
	private Board board;

	public FeatureLayer(Board board, int pID) {
		super(WALLFEAT + CELLFEAT + WALLSLEFTFEAT + MANHATTANFEAT + PLAYERFEAT);
		
		
		this.pID = pID; 
		
		
		nodeList = new ArrayList<Node>();
		for(int i = 0; i < size; i++) {
			nodeList.add(new FeatureNode(0));
		}
		updateFeatures(board);
	}
	
	public void updateFeatures(Board b) {
		this.board = b;
		updateWallFeatures();
		updateCellFeatures();
		updateWallsLeftFeatures();
		updateManhattanFeatures();
		updatePlayerFeatures();
	}

	// sets the first 64 to horizontal, second 64 to vertical
	private void updateWallFeatures() {
		for (int i = 0; i < WALLFEAT / 2; i++) {
			int j = i * 2;
			int horiz = 0 , vert = 0;

			switch (board.junctArray[i % 8][i / 8].getOrientation()) {
			case VERTICAL:
				horiz =  -1;
				vert = -1;
				break;
			case HORIZONTAL:
				horiz = 1; 
				vert = -1;
				break;
			default:
				break;

			}
			FeatureNode hNode = (FeatureNode) nodeList.get(i);
			FeatureNode vNode = (FeatureNode) nodeList.get(j);
			hNode.setFeature(horiz);
			vNode.setFeature(vert);
		}
	}

	private void updateCellFeatures() {
		for (int i = 0; i < CELLFEAT; i++) {
			Cell current = board.cellArray[i % 9][i / 9];
			int index = i + WALLFEAT;
			if (current.occupiedBy == pID)
				((FeatureNode) nodeList.get(index)).setFeature(10);

			else if (current.occupiedBy == -1)
				((FeatureNode) nodeList.get(index)).setFeature(0);

			else
				((FeatureNode) nodeList.get(index)).setFeature(-10);
		}
	}
	
	//first wallsleftfeature is self, then enemy
	private void updateWallsLeftFeatures() {
		int index = WALLFEAT+CELLFEAT;
		((FeatureNode) nodeList.get(index)).setFeature(board.wallsLeft[pID]);
		((FeatureNode) nodeList.get(index + 1)).setFeature(board.wallsLeft[(pID + 1) % 2]);
	}
	
	private void updateManhattanFeatures() {
		int index = WALLFEAT +CELLFEAT + WALLSLEFTFEAT;
		((FeatureNode) nodeList.get(index)).setFeature(board.manhattanDistance(pID));
		((FeatureNode) nodeList.get(index + 1)).setFeature(board.manhattanDistance((pID + 1) % 2));
	}
	
	private void updatePlayerFeatures() {
		int index = WALLFEAT +CELLFEAT + WALLSLEFTFEAT + MANHATTANFEAT;
		((FeatureNode) nodeList.get(index)).setFeature(board.getPlayerYValue(pID));
		((FeatureNode) nodeList.get(index + 1)).setFeature(board.getPlayerYValue((pID + 1) % 2));
		((FeatureNode) nodeList.get(index)).setFeature(board.getPlayerXValue(pID));
		((FeatureNode) nodeList.get(index + 1)).setFeature(board.getPlayerXValue((pID + 1) % 2));
	}
	
	
}
