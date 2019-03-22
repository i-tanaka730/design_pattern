public class File extends Entry {

	private String name;

	public File(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	protected void printList(String prefix) {
		System.out.println(prefix + "/" + name);
	}
}
