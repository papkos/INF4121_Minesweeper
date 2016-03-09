import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Ranking {

	private final int MAX_PEOPLE_LIMIT = 5;
	private List<Record> records;
	private int last;

	Ranking() {
		records = new ArrayList<>(MAX_PEOPLE_LIMIT);

		last = 0;
	}

	public void recordName(int result) {
		System.out.print("\n Please enter your name -");
		Scanner in = new Scanner(System.in);
		String newName = in.nextLine();
		if ((last == MAX_PEOPLE_LIMIT) && records.get(MAX_PEOPLE_LIMIT - 1).score > result) {
			System.out.println("\nSorry you cannot enter top " + (MAX_PEOPLE_LIMIT) + " players");
			return;
		} else if (last == MAX_PEOPLE_LIMIT) {
			records.set(MAX_PEOPLE_LIMIT - 1, new Record(newName, result));
		} else {
			records.add(new Record(newName, result));
			last++;
		}

		sort();
		show();
	}

	public void show() {
		if (last == 0) {
			System.out.println("Still no results");
			return;
		}
		System.out.println(String.format("%1$2s. %2$-10s\t%3$s", "N", "Name", "Score"));
		for (int i = 0; i < records.size(); i++) {
			Record record = records.get(i);
			System.out.println(String.format("%1$2d. %2$-10s\t%3$s", i + 1, record.name, record.score));
		}
	}

	private void sort() {
		// Sort by score, descending.
		Collections.sort(records, (r1, r2) -> r2.score - r1.score);
	}

	static class Record {
		public final String name;
		public final int score;

		public Record(String name, int score) {
			this.name = name;
			this.score = score;
		}

		@Override
		public String toString() {
			return String.format("%1$-10s\t%2$s", name, score);
		}
	}
}
