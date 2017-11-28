import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class NeuralNet {

	FeatureLayer inputLayer;
	ArrayList<NeuralLayer> layers = new ArrayList<NeuralLayer>();

	public NeuralNet(Board b, int pID, File layout, File weights) {
		inputLayer = new FeatureLayer(b, pID);
		constructNetwork(layout);
		initializeWeightsFromFile(weights);
	}

	// TODO write a get output 0 - 1
	// update method uses backpropagation

	private void constructNetwork(File layout) {
		// get values from layout
		Scanner reader;
		try {
			reader = new Scanner(layout);

			int numLayers, index;
			index = 0;
			int[] nodesPerLayer;

			numLayers = reader.nextInt();
			nodesPerLayer = new int[numLayers];

			while (reader.hasNextInt()) {
				nodesPerLayer[index] = reader.nextInt();
				index++;
			}

			// initialize NeuralLayers
			for (int i = 0; i < numLayers; i++) {
				System.out.println("nodes in layer " + i + ": " + nodesPerLayer[i]);
				layers.add(new NeuralLayer(nodesPerLayer[i]));
			}
			// add feature layer to beginning of hiddenLayers arrayList
			layers.add(0, inputLayer);
			layers.add(new NeuralLayer(1)); // outputLayer

			// connect Layers
			for (int i = layers.size() - 1; i >= 1; i--) {
				for (Node j : layers.get(i - 1).nodeList) {
					System.out.println("layer: " + i + " totalSize: " + layers.get(i - 1).nodeList.size());
					layers.get(i).connectBackward(j);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initializeWeightsFromFile(File weightsFile) {

		try {
			Scanner weightScanner = new Scanner(weightsFile);
			int layerCount = layers.size();
			System.out.println("LayerCount: " + layerCount);
			for (int i = 0; i < layerCount; i++) {
				int layerSize = layers.get(i).size;
				for (int j = 0; j < layerSize; j++) {
					int inListSize = layers.get(i).getNode(j).toList.size();
					for (int k = 0; k < inListSize; k++) {
						System.out.println("i " + i + " j" + j + " k " + k);
						String next = weightScanner.next();
						boolean hardCoded = (next.contains("h"));
						next.replaceAll("h", "");
						layers.get(i).getNode(j).toList.get(k).setWeight(Double.parseDouble(next));
						layers.get(i).getNode(j).toList.get(k).setHardCoded(hardCoded);
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			int layerCount = layers.size();
			for (int i = 0; i < layerCount; i++) {
				int layerSize = layers.get(i).size;
				for (int j = 0; j < layerSize; j++) {
					int inListSize = layers.get(i).getNode(j).toList.size();
					for (int k = 0; k < inListSize; k++) {
						layers.get(i).getNode(j).toList.get(k).setWeight(Math.random());
					}
				}
			}
		}
	}

	
	//I dont know why this thing is sitting here, or if we needed it...
	/*
	public void conectLayer(NeuralLayer Next) {
		for(Node local : nodeList) {
			connectBackward(local);
	*/

	public String weightsToString() {
		String out = "";
		int layerCount = layers.size();
		for (int i = 1; i < layerCount; i++) {
			int layerSize = layers.get(i).size;
			for (int j = 0; j < layerSize; j++) {
				int inListSize = layers.get(i).getNode(j).toList.size();
				for (int k = 0; k < inListSize; k++) {
					out += layers.get(i).getNode(j).toList.get(k).getWeight() + " ";
				}
				out += "\n";
			}
		}
		return out;
	}

	public String layoutToString() {
		String out = "";

		out += layers.size() - 2 + "\n";
		// start at layer 1 to avoid printing the input layer
		for (int i = 1; i < layers.size() - 1; i++) {
			out += layers.get(i).size + " ";
		}
		return out;
	}
	
	public double getOutput(Board b) {
		inputLayer.updateFeatures(b);
		return layers.get(layers.size()-1).getNode(0).activationFunction();
	}

	public void updateNetworkWeights(double target, double alpha) {
		double layerError = 0;
		for (int l = layers.size() - 1; l >= 0; l--) {
			if (l < layers.size() - 1) {
				for(Node n: layers.get(l).nodeList) {
					for(Synapse s: n.toList) {
						s.updateWeight(alpha);
					}
				}
			}
			layerError = layers.get(l).getTotalError(layerError, target);
		}
	}

}
