import java.util.Scanner;

import processing.core.*;

public class GameUI {

	/*
	 * this class is the holder for all graphics classes (below) and redraws the
	 * board based on a simple string input in update()
	 */

	private int cellSize, // size of a cell
			boardSize, // cells per side of the board
			spacing; // size of spaces between cells

	private WallGraphics[] walls = new WallGraphics[20];
	private int wallsSoFar = 0;
	private float P1x, P1y, P2x, P2y;
	int[] wallsLeft = new int[2];
	private float cellMargin = cellSize * 0.1f;
	private Hitbox[][][] hitboxes = new Hitbox[8][8][2]; // 8 X 8 and 2 orientations
	private Move highlightedMove = null;

	public int currentPlayer = 0;

	public GameUI(int cellSize, int boardSize) {
		setCellSize(cellSize);
		setBoardSize(boardSize);
		spacing = cellSize / 10;
		initHitboxes();
	}

	public Move getHighlightedMove() {
		return highlightedMove;
	}

	public void draw(PApplet p) {

		p.pushMatrix();

		p.translate(cellMargin + Main.margin, cellMargin);
		// draw cells (no cell class necessary)
		for (int x = 0; x < boardSize; x++) {
			for (int y = 0; y < boardSize; y++) {
				p.noStroke();
				p.fill(255);
				int top = y * (cellSize + spacing);
				int left = x * (cellSize + spacing);
				p.rect(left, top, cellSize, cellSize);
			}
		}

		// draw walls (class is within this class)
		for (int i = 0; i < wallsSoFar; i++) {
			walls[i].draw(p);
		}

		// draw players(no class necessary)
		drawPlayers(p);
		highlightWallSlots(p);

		p.popMatrix();
	
		drawWallsLeft(p);
	}

	public void drawPlayers(PApplet p) { //written to draw current player on top in case of double move
		if(currentPlayer == 1) {
		p.fill(80, 80, 150);
		p.ellipse(P1x, P1y, cellSize * .7f, cellSize * .7f);
		p.fill(230, 230, 0);
		p.ellipse(P2x, P2y, cellSize * .7f, cellSize * .7f);
		}
		else {
			
			p.fill(230, 230, 0);
			p.ellipse(P2x, P2y, cellSize * .7f, cellSize * .7f);
			p.fill(80, 80, 150);
			p.ellipse(P1x, P1y, cellSize * .7f, cellSize * .7f);
		}

	}

	public void drawWallsLeft(PApplet p) {
		int i = 0;
		float xTranslate, yTranslate, rotate;
		while (i < 2) {
			xTranslate = i * p.width;
			yTranslate = i * p.height;
			rotate = i * (float) Math.PI;
			p.pushMatrix();
			p.translate(xTranslate, yTranslate);
			p.rotate(rotate);
			float divisor = 3.5f;
			float x1 = Main.margin / divisor;
			float x2 = x1 * (divisor - 1);
			float y1 = p.height / 12;
			p.stroke(0);
			p.strokeWeight(5);
			for (int j = wallsLeft[i]; j > 0; j--) {
				float y = (j < 6)? y1*j: y1 * (j + 1);

				p.line(x1, y, x2, y);
			}

			p.popMatrix();
			i++;
		}
	}

	public void updatePlayers(GamePiece p1, GamePiece p2) {
		P1x = (p1.getCol() + 1) * (cellSize * 1.1f) - cellSize * 0.6f;
		P1y = (p1.getRow() + 1) * (cellSize * 1.1f) - cellSize * 0.6f;
		P2x = (p2.getCol() + 1) * (cellSize * 1.1f) - cellSize * 0.6f;
		P2y = (p2.getRow() + 1) * (cellSize * 1.1f) - cellSize * 0.6f;
	}

	public void update(String BoardIn, GamePiece p1, GamePiece p2) {

		// decided to pass the boards Game Pieces in directly for interaction
		updatePlayers(p1, p2);
		// scans the string coming in and updates walls
		Scanner scan = new Scanner(BoardIn);
		int j = 1;
		wallsLeft[0] = scan.nextInt();
		wallsLeft[1] = scan.nextInt();
		wallsSoFar = 0;
		while (j < boardSize) {
			int i = 1;
			while (i < boardSize) {
				String next = scan.next();
				float x = i * (cellSize * 1.1f) - (cellSize * 0.05f); // center point (x,y) of
				float y = j * (cellSize * 1.1f) - (cellSize * 0.05f); // the junction
				if (next.equals("H")) {
					walls[wallsSoFar] = new WallGraphics(Orientation.HORIZONTAL, x, y);
					wallsSoFar++;
				}
				if (next.equals("V")) {

					walls[wallsSoFar] = new WallGraphics(Orientation.VERTICAL, x, y);
					wallsSoFar++;
				}
				i++;
			}
			j++;
		}
	}

	private class WallGraphics { // the class which will interpret a new wall and draw one when called

		float x, y;
		float length = cellSize * 2.1f, thickness = cellSize * 0.1f;
		public Orientation orientation;

		public WallGraphics(Orientation o, float x, float y) {
			// vars x and y must be the center of the junction
			orientation = o;
			this.x = x;
			this.y = y;
		}

		public void draw(PApplet p) {
			switch (orientation) {
			case HORIZONTAL:

				float left = x - length / 2;
				float top = y - thickness / 2;
				p.fill(0);
				p.rect(left, top, length, thickness);
				break;

			case VERTICAL:
				top = y - length / 2;
				left = x - thickness / 2;
				p.fill(0);
				p.rect(left, top, thickness, length);
				break;

			default:
				break;

			}

		}

	}

	public int getCellSize() {
		return cellSize;
	}
	
	public void highlightWallSlots(PApplet p) {
		float xOffset = cellMargin + Main.margin;
		float yOffset = cellMargin;

		
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				for (int k = 0; k < 2; k++) {
					if (hitboxes[i][j][k].withinBounds(p.mouseX - xOffset, p.mouseY - yOffset)) {
						Orientation o = (k == 0) ? Orientation.HORIZONTAL : Orientation.VERTICAL;
						hitboxes[i][j][k].draw(p, o);
						highlightedMove = new WallMove(i, j, o);
						return;
					} highlightedMove =null;
				}
	
	}

	public void initHitboxes() {

		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				for (int k = 0; k < 2; k++) {
					hitboxes[i][j][k] = new Hitbox(i + 1, j + 1, k);
				}

	}

	private class Hitbox {

		float x0, y0, x1, y1, x2, y2;

		public Hitbox(float i, float j, float k) {

			x0 = i * (cellSize * 1.1f) - (cellSize * 0.05f); // center point (x,y) of
			y0 = j * (cellSize * 1.1f) - (cellSize * 0.05f); // the junction
			float length = cellSize * 1.05f, thickness = cellSize * 0.1f;

			if (k == 0) // horizontal hitbox
			{
				x1 = x0 - length / 2;
				x2 = x0 + length / 2;
				y1 = y0 - thickness / 2;
				y2 = y0 + thickness / 2;
			} else {
				x1 = x0 - thickness / 2;
				x2 = x0 + thickness / 2;
				y1 = y0 - length / 2;
				y2 = y0 + length / 2;

			}

		}

		public boolean withinBounds(float x, float y) {
			return (x > x1 && x < x2 && y > y1 && y < y2);
		}

		public void draw(PApplet p, Orientation orientation) {
			float length = cellSize * 2.1f, thickness = cellSize * 0.1f;
			switch (orientation) {
			case HORIZONTAL:

				float left = x0 - length / 2;
				float top = y0 - thickness / 2;
				p.fill(0, 0, 0, 100);
				p.rect(left, top, length, thickness);
				break;

			case VERTICAL:
				top = y0 - length / 2;
				left = x0 - thickness / 2;
				p.fill(0, 0, 0, 100);
				p.rect(left, top, thickness, length);
				break;

			default:
				break;

			}

		}

	}

	public void setCellSize(int cellSize) {
		this.cellSize = cellSize;
	}

	public int getBoardSize() {
		return boardSize;
	}

	public void setBoardSize(int boardSize) {
		this.boardSize = boardSize;
	}

}
