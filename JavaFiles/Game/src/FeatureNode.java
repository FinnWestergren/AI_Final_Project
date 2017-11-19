
public class FeatureNode extends Node {
	double feature;
	
	public FeatureNode(double feature) {
		super();
		fromList = null;
		this.feature = feature;
	}
	
	public void setFeature(double feature) {
		this.feature = feature;
	}
	
	/**
	 * Activation function to override activationFunction in Node
	 * 
	 * @return feature
	 */
	public double activationFunction() {
		return feature;
	}
}
