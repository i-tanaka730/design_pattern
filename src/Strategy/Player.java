public class Player {

	private String name;
	private Strategy strategy;
	private int wincount;
	private int losecount;
	private int gamecount;

	public Player(String name, Strategy strategy) {
		this.name = name;
		this.strategy = strategy;
	}

	public String getName() {
		return name;
	}

	public Hand nextHand() {
		return strategy.nextHand();
	}

	public void win() {
		wincount++;
		gamecount++;
	}

	public void lose() {
		losecount++;
		gamecount++;
	}

	public void even() {
		gamecount++;
	}

	public String toString() {
		return "[" + name + "] " + gamecount + " gemes, " + wincount + " win, " + losecount + " lose";
	}
}
