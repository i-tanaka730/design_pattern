public class Hand {

	public static final int HANDVALUE_GUU = 0;
	public static final int HANDVALUE_CHO = 1;
	public static final int HANDVALUE_PAA = 2;

	public static final Hand[] hand = {
			new Hand(HANDVALUE_GUU),
			new Hand(HANDVALUE_CHO),
			new Hand(HANDVALUE_PAA),
	};

	private int handvalue;

	private Hand(int handvalue) {
		this.handvalue = handvalue;
	}

	public static Hand getHand(int handvalue) {
		return hand[handvalue];
	}

	public boolean isStrongerThan(Hand h) {
		// thisがhより強いときtrue
		return fight(h) == 1;
	}

	private int fight(Hand h) {
		if (this == h) {
			// 引き分け
			return 0;
		} else if ((this.handvalue + 1) % 3 == h.handvalue) {
			// thisの勝ち
			return 1;
		} else {
			// hの勝ち
			return -1;
		}
	}
}
