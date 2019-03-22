import java.util.Random;

public class RandomStrategy implements Strategy {

	public Hand nextHand() {
		Random random = new Random();
		return Hand.getHand(random.nextInt(3));
	}
}
