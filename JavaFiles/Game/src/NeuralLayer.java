import java.util.ArrayList;

public class NeuralLayer {
	
	protected int size;
	public ArrayList<Node> nodeList = new ArrayList<Node>();
	
	public NeuralLayer(int size) {
		this.size = size ;
		for (int  i = 0; i < size; i ++){
			nodeList.add(new Node());
		}
		nodeList.add(new BiasNode());
	}
	


	public String toString() {
		String out = "";
		for(Node n : nodeList) out += n.toString() + "\n";
		return out;
	}

	public Node getNode(int j) {
		// TODO Auto-generated method stub
		return nodeList.get(j);
	}

	public double getTotalError(double target, double errorSum) {
		double out = 0; 
		for(Node n : nodeList) out += n.getErrorPropagation(target, errorSum);
		return out;
	}
	
}
