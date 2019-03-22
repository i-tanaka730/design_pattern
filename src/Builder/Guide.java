public class Guide {

	private Builder builder;

	public Guide(Builder builder) {
		this.builder = builder;
	}

	public void construct() {
		builder.makeTitle("バーベキューについて");
		builder.makeString("日時");
		builder.makeItems(new String[]{
			"2019/2/28 (木)",
			"11:00～",
		});
		builder.makeString("場所");
		builder.makeItems(new String[]{
			"xxx市 xxxバーベキュー場",
		});
		builder.makeString("持ち物");
		builder.makeItems(new String[]{
			"タオル",
			"肉",
			"飲み物",
			"(略)",
		});
		builder.close();
	}
}