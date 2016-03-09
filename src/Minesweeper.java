import java.util.Scanner;

public class Minesweeper {

	public static void main(String[] args) {
		Ranking ranking = new Ranking();

		printMainMessage();

		Minesweeper game;
		boolean hasNextGame = true;
		while (hasNextGame) {
			game = new Minesweeper(ranking);
			hasNextGame = game.play();
		}

		System.out.println("\nThank you for playing :) Have a nice day!");
	}

	private static void printMainMessage() {
		System.out.println("Welcome to Minesweeper!");
		System.out.println("To play just input some coordinates and try not to step on a mine :)");
		System.out.println("Useful commands:");
		System.out.println("restart - Starts a new game.");
		System.out.println("exit - Quits the game.");
		System.out.println("top - Reveals the top scoreboard.");
		System.out.println("Have Fun !");
	}

	private static String input() {
		Scanner in = new Scanner(System.in);
		return in.nextLine();
	}

	private static String input(String message) {
		if (! message.endsWith(" ") && ! message.endsWith("\t")) {
			message = message + " ";
		}
		System.out.print(message);
		return input();
	}

	private final Ranking ranking;
	private final MineField board;
	private int result = 0;

	private boolean shouldStartAgain;
	private boolean hasNextRound;

	public Minesweeper(Ranking ranking) {
		this.ranking = ranking;

		this.board = new MineField();
	}

	public boolean play() {
		shouldStartAgain = true;
		hasNextRound = true;

		while (hasNextRound) {
			board.show();
			String input = input("\nPlease enter your move (row col): ");
			boolean isCommand = processCommandInput(input);
			if (!isCommand) {
				processCoordinateInput(input);
			}
		}
		return shouldStartAgain;
	}

	/**
	 * Try to parse the input string, and perform the action if it is a command.
	 * @param input The input string typed by the user.
	 * @return {@code true} if the input was processed, {@code false} if not.
	 */
	private boolean processCommandInput(String input) {
		switch (input) {
			case "top":
				ranking.show();
				return true;
			case "restart":
				ranking.recordName(result);
				hasNextRound = false;
				return true;
			case "exit":
				ranking.recordName(result);
				hasNextRound = false;
				shouldStartAgain = false;
				return true;
			default:
				return false;
		}
	}

	private void processCoordinateInput(String input) {
		if (board.legalMoveString(input)) {
			result++;
			if (result == 35) {
				System.out.println("Congratulations you WON the game!");
				ranking.recordName(result);
				hasNextRound = false;
			}
		} else if (board.getBoom()) {
			System.out.println("\nBooooooooooooooooooooooooooooom! You stepped on a mine! " +
					"You survived " + result + " turns");
			ranking.recordName(result);
			hasNextRound = false;
		}
	}

}
