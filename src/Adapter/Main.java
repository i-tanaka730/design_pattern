public class Main {
    public static void main(String[] args) {
    	Student student = new HumanAdapter("田中", 25);
    	student.showName();
    	student.showAge();
    }
}
