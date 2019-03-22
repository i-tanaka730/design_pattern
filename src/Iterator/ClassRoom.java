public class ClassRoom implements Aggregate {

	private Student[] students;

	private int last = 0;

	public ClassRoom(int maxsize) {
        this.students = new Student[maxsize];
    }

	public Student getStudentAt(int index) {
        return students[index];
    }

	public void appendStudent(Student student) {
        this.students[last] = student;
        last++;
    }

	public int getLength() {
        return last;
    }

	public Iterator iterator() {
        return new ClassRoomIterator(this);
    }
}
