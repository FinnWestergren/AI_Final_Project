
public class Player {

	/*
	 * this class should be extended by alpha beta, reinforcement learning, and
	 * human players I'm tryna keep this extra simple so we can reimplement and test
	 * on tic tac toe (which we shouldn't need a UI for) 
	 * 
	 * you know, because of the implication
	 */

	public String name;

	public Player() {
		name = null;
	}

	public Player(String name) {
		this.name = name;
	}

}
