
public class FeatureNode extends Node {
	double feature;
	
	public FeatureNode(double feature) {
		super();
		fromList = null;
		this.feature = feature;
	}
	
	public void setFeature(double feature) {
		this.feature = feature;
		lastOutput = feature;
	}
	
	/**
	 * Activation function to override activationFunction in Node
	 * 
	 * @return feature
	 */
	
	@Override
	public double activationFunction() {
		//System.out.println("FEATURE CALL: " + feature );
		return feature;
		
	}
	

	
}
