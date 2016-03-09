import java.util.Scanner;

public class Minesweeper {

	private static Ranking rank;

	private static boolean continueGame = true;

	public static void main(String[] args) {
		rank = new Ranking();
		mainMessage();

		while (continueGame) {
			gameLoop();
		}

		System.out.println("\nThank you for playing :) Have a nice day!");
	}

	private static void gameLoop() {
		MineField field = new MineField();
		int result = 0;
		boolean playing = true;

		while (playing) {
			field.show();
			System.out.print("\nPlease enter your move (row col): ");
			Scanner in = new Scanner(System.in);
			String input = in.nextLine();

			switch (input) {
				case "top":
					rank.show();
					break;
				case "restart":
					rank.recordName(result);
					playing = false;
					break;
				case "exit":
					rank.recordName(result);
					playing = false;
					continueGame = false;
					break;
				default: {
					if (field.legalMoveString(input)) {
						result++;
						if (result == 35) {
							System.out.println("Congratulations you WON the game!");
							rank.recordName(result);
							playing = false;
						}
					} else if (field.getBoom()) {
						System.out.println("\nBooooooooooooooooooooooooooooom! You stepped on a mine! " +
								"You survived " + result + " turns");
						rank.recordName(result);
						playing = false;
					}
					break;
				}
			}
		}
	}

	private static void mainMessage() {
		System.out.println("Welcome to Minesweeper!");
		System.out.println("To play just input some coordinates and try not to step on a mine :)");
		System.out.println("Useful commands:");
		System.out.println("restart - Starts a new game.");
		System.out.println("exit - Quits the game.");
		System.out.println("top - Reveals the top scoreboard.");
		System.out.println("Have Fun !");
	}
}
