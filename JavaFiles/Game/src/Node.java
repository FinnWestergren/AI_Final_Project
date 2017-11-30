import java.util.ArrayList;

public class Node {

	protected ArrayList<Synapse> toList;
	protected ArrayList<Synapse> fromList;
	public double lastOutput = 0.5, lastErrorCalc=0;

	public Node() {
		toList = new ArrayList<Synapse>();
		fromList = new ArrayList<Synapse>();
	}

	public Node(ArrayList<Synapse> toList, ArrayList<Synapse> fromList) {
		this.toList = toList;
		this.fromList = fromList;
	}

	// feature nodes override activationFuntion

	// calls the get value method in synapse which gets the weighted value of the
	// previous "from node"
	// that value is determined with activationFunction()
	public double activationFunction() {
		double weightedSum = 0;
		double functionValue = 0;

		for (int i = 0; i < fromList.size(); i++) {
			weightedSum += fromList.get(i).getValue();
		}

		functionValue = 1 / (1 + Math.pow(Math.E, -weightedSum));

		lastOutput = functionValue;

		return functionValue;
	}

	//// added methods to add synapses to the lists
	//// we could also make the array lists public and add them that way
	public void addTo(Synapse to) {
		toList.add(to);
	}

	public void addFrom(Synapse from) {
		fromList.add(from);
	}

	public String toString() {
		String out = "";
		for (Synapse s : toList)
			out += s.getWeight() + " ";
		return out;
	}

	public double getErrorPropagation(double target, double errorSum) {
		double gPrime = lastOutput * (1 - lastOutput);
		double errorDeriv;
		if (toList.isEmpty()) {
			errorDeriv = (target - lastOutput) * gPrime;
			System.out.println(gPrime);
			//System.exit(1);
		}

		else {
			double outputSum = 0;
			for (int i = 0; i < fromList.size(); i++) {
				outputSum += fromList.get(i).getValue();
			}
			errorDeriv = (errorSum * outputSum * gPrime);
		}
		lastErrorCalc = errorDeriv;
		return errorDeriv;
	}

	
}
