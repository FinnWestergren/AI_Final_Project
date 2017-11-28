import java.util.ArrayList;

public class BiasNode extends Node {

	public BiasNode() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BiasNode(ArrayList<Synapse> toList, ArrayList<Synapse> fromList) {
		super(toList, fromList);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public double activationFunction() {
		return 1;
	}
	
	@Override
	public void addFrom(Synapse from) {
		return;
	}
	
	

}
