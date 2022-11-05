import java.util.ArrayList;

public interface UseAdmin {
	/**Course Management*/
	public void create();
	public void delete();
	public void edit();
	public void display();
	public void registerStudent();
	/**Reports*/
	public void viewCourses();
	public ArrayList<Course> viewFull();
	public void writeFull();
	public void studentInCourse();
	public void courseOfStudent();
	public void sort();
	
}
