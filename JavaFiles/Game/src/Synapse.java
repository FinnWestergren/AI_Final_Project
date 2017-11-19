
public class Synapse {
	
	Node to;
	Node from;
	public double weight;
	
	public Synapse() {
		to = new Node();
		from = new Node();
		weight = 0;
	}
	
	public Synapse(Node to, Node from, double weight) {
		this.to = to;
		this.from = from;
		this.weight = weight;
		to.addFrom(this);
		from.addTo(this);
	}
	
	// hey mang, this returns teh weighted value of the "from Node" for this synapse 
	// i figured this would make it easier for the activation function method in Node
	// now the synapses will handle their own weighted values
	//
	// this combined with the activation function method end up being recursive
	public double getValue() {
		
		return from.activationFunction() * weight;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
}

