public class ClassRoomIterator implements Iterator {

	private ClassRoom classRoom;

	private int index;

    public ClassRoomIterator(ClassRoom classRoom) {
        this.classRoom = classRoom;
        this.index = 0;
    }

    public boolean hasNext() {
        if (index < classRoom.getLength()) {
            return true;
        } else {
            return false;
        }
    }

    public Object next() {
        Student student = classRoom.getStudentAt(index);
        index++;
        return student;
    }
}
