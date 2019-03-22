public class GuuStrategy implements Strategy {

	public Hand nextHand() {
		return Hand.getHand(Hand.HANDVALUE_GUU);
	}
}
