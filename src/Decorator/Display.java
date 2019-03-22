public abstract class Display {

	// 横の文字数を得る
	public abstract int getColumns();
	// 縦の行数を得る
	public abstract int getRows();
	// 指定した行の文字列を得る
	public abstract String getRowText(int row);

	public void show() {
		for (int i = 0; i < getRows(); i++) {
			System.out.println(getRowText(i));
		}
	}
}
