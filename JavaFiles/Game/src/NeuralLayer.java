import java.util.ArrayList;

public class NeuralLayer {
	
	protected int size;
	public ArrayList<Node> nodeList = new ArrayList<Node>();
	
	public NeuralLayer(int size) {
		this.size = size;
		for (int  i = 0; i < size; i ++){
			nodeList.add(new Node());
		}
	}
	
	public void connectInList(Node prev) {
		for (Node local : nodeList) {
			Synapse s = new Synapse(prev, local, Math.random());
		}
	}
	
	
}
