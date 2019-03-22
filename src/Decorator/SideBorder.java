public class SideBorder extends Border {

	public SideBorder(Display display) {
		super(display);
	}

	public int getColumns() {
		// 文字数は中身の両側に飾り文字分を加えた数
		return 1 + display.getColumns() + 1;
	}

	public int getRows() {
		// 行数は中身の行数に同じ
		return display.getRows();
	}

	public String getRowText(int row) {
		return "*" + display.getRowText(row) + "*";
	}
}
