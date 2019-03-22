public class Main {
    public static void main(String[] args) {

    	ClassRoom classRoom = new ClassRoom(4);
        classRoom.appendStudent(new Student("田中"));
        classRoom.appendStudent(new Student("山田"));
        classRoom.appendStudent(new Student("鈴木"));
        classRoom.appendStudent(new Student("佐藤"));

        Iterator it = classRoom.iterator();

        while (it.hasNext()) {
            Student student = (Student)it.next();
            System.out.println(student.getName());
        }
    }
}
