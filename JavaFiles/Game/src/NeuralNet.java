import java.util.ArrayList;
import java.util.Scanner;

public class NeuralNet {
	
	FeatureLayer inputLayer;
	ArrayList<NeuralLayer> layers = new ArrayList<NeuralLayer>();
	
	public NeuralNet(Board b, int pID, String layout) {
		inputLayer = new FeatureLayer(b, pID);
		constructNetwork(layout);
	}
	
	public void constructNetwork(String layout) {
		//get values from layout
		Scanner reader = new Scanner(layout);
		int numLayers, index;
		index = 0;
		int[] nodesPerLayer;
		
		numLayers = reader.nextInt();
		nodesPerLayer = new int[numLayers];
		
		while (reader.hasNextInt()) {
			nodesPerLayer[index] = reader.nextInt();
		}
		
		//initialize NeuralLayers
		for (int i = 0 ; i < numLayers ; i++) {
			layers.add(new NeuralLayer(nodesPerLayer[i]));
		}
		//add feature layer to beginning of hiddenLayers arrayList
		layers.add(0, inputLayer);
		layers.add(new NeuralLayer(1));
		
		
		//connect Layers
		for (int i = layers.size() ; i >= 0 ; i--) {
			for (int j = 0 ; j < nodesPerLayer[i] ; j++) {
				layers.get(i).connectBackward(layers.get(i-1).nodeList.get(j));
			}
		}
	}
	
	//I dont know why this thing is sitting here, or if we needed it...
	/*
	public void conectLayer(NeuralLayer Next) {
		for(Node local : nodeList) {
			connectBackward(local);
		}
	}
	*/

}
