import java.util.Random;

class MineField {

	private final int ROWS = 5;
	private final int COLS = 10;

	private boolean[][] mines, visible;
	private boolean boom;

	MineField() {

		mines = new boolean[ROWS][COLS];
		visible = new boolean[ROWS][COLS];
		boom = false;

		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				mines[row][col] = false;
				visible[row][col] = false;
			}
		}

		int counter2 = 15;
		Random RGenerator = new Random();
		boolean placed;

		while (counter2 > 0) {
			placed = placeMine(new Coord(RGenerator.nextInt(ROWS), RGenerator.nextInt(COLS)));
			if (placed) {
				counter2--;
			}
		}
	}

	/**
	 * Tries to place a mine at the given coordinate.
	 * @param coord Where to place the mine.
	 * @return {@code true} if the mine has been placed,
	 * 		   {@code false} otherwise (there's already a mine there).
	 */
	private boolean placeMine(Coord coord) {
		if (mines[coord.row][coord.col]) {
			return false;
		}

		mines[coord.row][coord.col] = true;
		return true;
	}

	/**
	 * Make all mine positions visible.
 	 */
	private void revealMines() {
		for (int row = 0; row < ROWS; row++) {
			for (int col = 0; col < COLS; col++) {
				if (mines[row][col]) {
					visible[row][col] = true;
				}
			}
		}
	}

	public boolean getBoom() {
		return boom;
	}

	/**
	 * Tries to parse user input as coordinates.
	 * @param input The user input, ideally two integers separated by a space.
	 * @return The parsed coordinates if they are valid, or {@code null} if they aren't.
	 */
	public Coord parseMoveInput(String input) {
		String[] parts = input.split(" ");
		int row;
		int col;
		try {
			row = Integer.parseInt(parts[0]);
			col = Integer.parseInt(parts[1]);
			if (! checkWithinBounds(row, col)) {
				throw new ArrayIndexOutOfBoundsException(
						String.format("Coordinates must be within 0-%1$d for row and 0-%2$d for " +
								"column. Got %3$d and %4$d.",
								ROWS, COLS, row, col)
				);
			}
			return new Coord(row, col);
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return null;
	}

	public boolean legalMoveString(String input) {
		Coord coord = parseMoveInput(input);
		if (coord == null) {
			System.out.println("Invalid move!");
			return false;
		}

		return legalMoveValue(coord);
	}

	private boolean legalMoveValue(Coord coord) {

		if (visible[coord.row][coord.col]) {
			System.out.println("You stepped in already revealed area!");
			return false;
		} else {
			visible[coord.row][coord.col] = true;
		}

		if (mines[coord.row][coord.col]) {
			revealMines();
			boom = true;
			show();
			return false;
		}

		return true;
	}

	public void show() {
		System.out.println("\n    0 1 2 3 4 5 6 7 8 9 ");
		System.out.println("   ---------------------");
		for (int row = 0; row < ROWS; row++) {
			System.out.print(row + " |");
			for (int col = 0; col < COLS; col++) {
				System.out.print(" " + drawChar(new Coord(row, col)));

			}
			System.out.println(" |");
		}
		System.out.println("   ---------------------");
	}

	private char drawChar(Coord coord) {
		int count;
		if (visible[coord.row][coord.col]) {
			if (mines[coord.row][coord.col]) {
				return '*';
			}
			count = countMinesAround(coord);
			return Integer.toString(count).charAt(0);
		} else if (boom) {
			return '-';
		}
		return '?';
	}

	/**
	 * Counts the number of mines around a given cell, within a radius.
	 * @param coord The cell around which the mines are counted.
	 * @return The number of mines found.
	 */
	private int countMinesAround(Coord coord) {
		int count = 0;
		int RADIUS = 1;
		Coord boundTopLeft = new Coord(coord.row - RADIUS, coord.col - RADIUS);
		Coord boundBottomRight = new Coord(coord.row + RADIUS, coord.col + RADIUS);
		for (int row = boundTopLeft.row; row <= boundBottomRight.row; row++) {
			for (int col = boundTopLeft.col; col <= boundBottomRight.col; col++) {
				if (checkWithinBounds(row, col)) {
					if (mines[row][col]) {
						count++;
					}
				}
			}
		}
		return count;
	}

	/**
	 * Checks if the given coordinates are valid board coordinates.
	 * @return {@code true} if they are valid, {@code false} otherwise.
	 */
	private boolean checkWithinBounds(int row, int col) {
		return col >= 0 && col < COLS && row >= 0 && row < ROWS;
	}

	/**
	 * Simple data class for storing board coordinates.
	 */
	static class Coord {
		final int row;
		final int col;

		public Coord(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}
}
