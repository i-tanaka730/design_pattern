public class LimitSupport extends Support {

	private int limit;

	public LimitSupport(String name, int limit) {
		super(name);
		this.limit = limit;
	}

	// limit未満なら解決可能
	protected boolean resolve(Trouble trouble) {
		if (trouble.getNumber() < limit) {
			return true;
		} else {
			return false;
		}
	}
}
