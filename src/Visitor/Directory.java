import java.util.ArrayList;
import java.util.Iterator;

public class Directory extends Entry {

	private String name;
	private ArrayList<Entry> dir = new ArrayList<Entry>();

	public Directory(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Entry add(Entry entry) {
		dir.add(entry);
		return this;
	}

	public Iterator<Entry> iterator() {
		return dir.iterator();
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
