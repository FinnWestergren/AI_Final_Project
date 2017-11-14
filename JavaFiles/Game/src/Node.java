import java.util.ArrayList;

public class Node {

	private ArrayList<Synapse> toList;
	private ArrayList<Synapse> fromList;
	
	public Node() {
		
	}
	
	public Node(ArrayList<Synapse> toList, ArrayList<Synapse> fromList) {
		this.toList = toList;
		this.fromList = fromList;
	}
	
	// feature nodes override activationFuntion
	
	//calls the get value method in synapse which gets the weighted value of the previous "to node"
	// that value is determined with activationFunction()
	public double activationFunction() {
		double weightedSum = 0;
		double functionValue = 0;
		
		for (int i = 0 ; i < toList.size() ; i++) {
			weightedSum += toList.get(i).getValue();
		}
		
		functionValue = 1 /(1 + Math.pow(Math.E, -weightedSum));
		
		return functionValue;
	}
	
	////   added methods to add synapses to the lists
	////   we could also make the array lists public and add them that way
	public void addTo(Synapse to) {
		toList.add(to);
	}
	
	public void addFrom(Synapse from) {
		fromList.add(from);
	}
}
