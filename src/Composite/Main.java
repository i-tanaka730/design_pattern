public class Main {
	public static void main(String[] args) {

		Directory workspaceDir = new Directory("workspace");
		Directory compositeDir = new Directory("composite");
		Directory testDir1 = new Directory("test1");
		Directory testDir2 = new Directory("test2");
		workspaceDir.add(compositeDir);
		workspaceDir.add(testDir1);
		workspaceDir.add(testDir2);

		File directory = new File("Directory.java");
		File entity = new File("Entity.java");
		File file = new File("file.java");
		File main = new File("main.java");
		compositeDir.add(directory);
		compositeDir.add(entity);
		compositeDir.add(file);
		compositeDir.add(main);
		workspaceDir.printList();
	}
}
