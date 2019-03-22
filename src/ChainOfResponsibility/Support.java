public abstract class Support {

	// このトラブル解決者の名前
	private String name;
	// たらい回し先
	private Support next;

	public Support(String name) {
		this.name = name;
	}

	public Support setNext(Support next) {
		this.next = next;
		return next;
	}

	// トラブル解決の手順
	public void support(Trouble trouble) {
		if (resolve(trouble)) {
			done(trouble);
		} else if (next != null) {
			next.support(trouble);
		} else {
			fail(trouble);
		}
	}

	public String toString() {
		return "[" + name + "]";
	}

	// 解決用メソッド
	protected abstract boolean resolve(Trouble trouble);

	// 解決
	protected void done(Trouble trouble) {
		System.out.println(trouble + " is resolved by " + this + ".");
	}

	// 未解決
	protected void fail(Trouble trouble) {
		System.out.println(trouble + " cannot be resolved.");
	}
}
