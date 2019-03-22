public class HumanAdapter implements Student {

	private Human human;

	public HumanAdapter(String name, int age) {
		this.human = new Human("田中", 25);;
    }

	public void showName() {
		human.printName();
    }

	public void showAge() {
		human.printAge();
    }
}
