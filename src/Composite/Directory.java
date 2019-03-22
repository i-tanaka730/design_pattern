import java.util.ArrayList;
import java.util.Iterator;

public class Directory extends Entry {

	private String name;
	private ArrayList<Entry> directory = new ArrayList<Entry>();

	public Directory(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Entry add(Entry entry) {
		directory.add(entry);
		return this;
	}

	protected void printList(String prefix) {
		System.out.println(prefix + "/" + name);
		Iterator<Entry> it = directory.iterator();
		while (it.hasNext()) {
			Entry entry = (Entry) it.next();
			entry.printList(prefix + "/" + name);
		}
	}
}
