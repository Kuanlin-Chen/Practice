public class MVCPatternDemo
{
	public static void main(String[] args) {

		StudentModel model = writeData();

		StudentView view = new StudentView();

		StudentController controller = new StudentController(model, view);

		controller.updateView();

        //update model data
		controller.setStudentName("John");

		controller.updateView();
	}

	private static StudentModel writeData() {
		StudentModel student = new StudentModel();
		student.setName("Kuanlin");
		student.setRollNo("01");
		return student;
	}
}