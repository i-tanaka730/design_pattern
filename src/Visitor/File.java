public class File extends Entry {

	private String name;

	public File(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void accept(Visitor v) {
		v.visit(this);
	}
}
