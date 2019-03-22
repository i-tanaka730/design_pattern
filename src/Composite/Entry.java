public abstract class Entry {

	public abstract String getName();
	protected abstract void printList(String prefix);

	public void printList() {
		printList("");
	}
}
