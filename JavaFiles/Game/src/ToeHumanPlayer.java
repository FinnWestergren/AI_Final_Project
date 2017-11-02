import java.util.Scanner;

public class ToeHumanPlayer extends HumanPlayer {

	private Scanner in;
	
	public ToeHumanPlayer(int pID) {
		super(pID);
		// TODO Auto-generated constructor stub
	}

	public ToeHumanPlayer(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	

	public ToeMove getMove() {
		String input = "";
		Integer number1 = 0;
		Integer number2 = 0;
		
		in = new Scanner(System.in);
		
		System.out.print("Please enter a valid move as a coordinate pair (x,y): ");
		input = in.nextLine();
		System.out.println();
		
		input = input.replaceAll("\\D+","");
		System.out.println("TEST  ::  input (w/ only digits) -->  " + input);     //test statement
		number1 = number1.parseInt(input.substring(0,1));
		number2 = number2.parseInt(input.substring(1));
		
		System.out.println("TEST  ::  number1 --> " + number1 + "   number 2 --> " + number2);
		
		ToeMove m = new ToeMove(number1, number2);
		return m;
	}
}
