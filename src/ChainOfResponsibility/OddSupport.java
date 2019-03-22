public class OddSupport extends Support {

	public OddSupport(String name) {
		super(name);
	}

	// 奇数番号の問題であれば解決可能
	protected boolean resolve(Trouble trouble) {
		if (trouble.getNumber() % 2 == 1) {
			return true;
		} else {
			return false;
		}
	}
}
