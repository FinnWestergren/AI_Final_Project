import java.util.ArrayList;

public class BiasNode extends Node {

	public BiasNode() {
		super();
		// TODO Auto-generated constructor stub
		lastOutput = 1;
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
