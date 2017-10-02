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

	public GameUI(int cellSize, int boardSize) {
		setCellSize(cellSize);
		setBoardSize(boardSize);
		spacing = cellSize / 10;
	}

	public void draw(PApplet p) {

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

	}

	public void drawPlayers(PApplet p) {
		p.fill(80, 80, 150);
		p.ellipse(P1x, P1y, cellSize * .7f, cellSize * .7f);
		p.fill(230, 230, 0);
		p.ellipse(P2x, P2y, cellSize * .7f, cellSize * .7f);

	}

	public void updatePlayers(GamePiece p1, GamePiece p2) {
		P1x = (p1.getCol() + 1) 	* (cellSize * 1.1f) - cellSize * 0.6f;
		P1y = (p1.getRow() + 1)		* (cellSize * 1.1f) - cellSize * 0.6f;
		P2x = (p2.getCol() + 1)		* (cellSize * 1.1f) - cellSize * 0.6f;
		P2y = (p2.getRow() + 1)		* (cellSize * 1.1f) - cellSize * 0.6f;
	}

	public void update(String BoardIn, GamePiece p1, GamePiece p2) {
		// decided to pass the boards Game Pieces in directly for interaction
		updatePlayers(p1, p2);
		// scans the string coming in and updates walls
		Scanner scan = new Scanner(BoardIn);
		int j = 1;
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
