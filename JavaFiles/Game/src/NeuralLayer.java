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
	
	public void connectBackward(Node prev) {
		for (Node local : nodeList) {
			Synapse s = new Synapse(prev, local, Math.random());
		}
	}
	
<<<<<<< HEAD
	public String toString() {
		String out = "";
		for(Node n : nodeList) out += n.toString() + "\n";
		return out;
	}
	
	
	
=======
>>>>>>> 8450f840f2f7165b3bda49522336b21f214f80a2
}
