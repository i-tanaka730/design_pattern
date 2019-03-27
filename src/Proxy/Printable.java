public interface Printable {
	// 名前の設定
	public abstract void setPrinterName(String name);
	// 名前の取得
	public abstract String getPrinterName();
	// 文字列表示(プリントアウト)
	public abstract void print(String string);
}
