public class Main {
	public static void main(String[] args) {

		Directory workspaceDir = new Directory("workspace");
		Directory compositeDir = new Directory("Visitor");
		Directory testDir1 = new Directory("test1");
		Directory testDir2 = new Directory("test2");
		workspaceDir.add(compositeDir);
		workspaceDir.add(testDir1);
		workspaceDir.add(testDir2);

		File element = new File("Element.java");
		File entity = new File("Entity.java");
		File file = new File("file.java");
		File directory = new File("Directory.java");
		File visitor = new File("Visitor.java");
		File listVisitor = new File("ListVisitor.java");
		File main = new File("main.java");
		compositeDir.add(element);
		compositeDir.add(entity);
		compositeDir.add(file);
		compositeDir.add(directory);
		compositeDir.add(visitor);
		compositeDir.add(listVisitor);
		compositeDir.add(main);

		workspaceDir.accept(new ListVisitor());
	}
}
