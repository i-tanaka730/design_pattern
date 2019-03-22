import java.util.Iterator;

public class ListVisitor extends Visitor {

	// 現在注目しているディレクトリ名
	private String currentdir = "";

	// ファイルを訪問したときに呼ばれる
	public void visit(File file) {
		System.out.println(currentdir + "/" + file);
	}

	// ディレクトリを訪問したときに呼ばれる
	public void visit(Directory directory) {
		System.out.println(currentdir + "/" + directory);
		String savedir = currentdir;
		currentdir = currentdir + "/" + directory.getName();
		Iterator<Entry> it = directory.iterator();
		while (it.hasNext()) {
			Entry entry = (Entry) it.next();
			entry.accept(this);
		}
		currentdir = savedir;
	}
}
