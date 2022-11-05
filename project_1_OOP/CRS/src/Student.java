import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Student extends User implements UseStudent,Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8590990826415027687L;
	transient Scanner input;
	private ArrayList<Course> registeredCourse = new ArrayList<Course>();
	
	public Student(Scanner input) {
		this.input = input;
	};
	public ArrayList<Course> getRegisteredCourse() {
		return registeredCourse;
	}

	public void setRegisteredCourse(ArrayList<Course> registeredCourse) {
		this.registeredCourse = registeredCourse;
	}

	public void menu() {
		System.out.println("                     ---------------------");
		System.out.println("                     ||Course Management||");
		System.out.println("----------------------------------------------------------------");
		System.out.println("1  -  View all Courses");
		System.out.println("2  -  View all courses that are not full");
		System.out.println("3  -  Register a course");
		System.out.println("4  -  Withdraw from a course"); 
		System.out.println("5  -  View all courses that the current student is registered in");
		System.out.println("6  -  Exit \n");
		System.out.print("Enter a number 1~6 to take action:");
	}
	
	@Override
	public void viewCourses() {
		Course.title();
		for (Course c : Course.getCourses()) {
			System.out.println(c);
		}
	}

	@Override
	public void viewNotFull() {
		Course.title();
		for (Course c : Course.getCourses()) {
			if (c.getMaxStudent() != c.getCurrentStudent()) {
				System.out.println(c);
			}
		} 
	}

	@Override
	public void registerCourse(Student current) {
		/**Must provide course name, section, and student full name, 
		 * the name will be added to the appropriate course
		 */
		System.out.print("Enter the Course ID:");
		String id = input.next();
		System.out.print("Enter the section number:");
		int section = input.nextInt();
		Course a = null;
		boolean exist = false;
		for (Course c :Course.getCourses()) {
			if (c.getCourseId().equals(id)) {
				if (c.getSectionNumber() == section) {
					a = c;
					exist = true;
					break;
				}else {
					System.out.println("Section not found.");
					return;
				}
			}
		}
		if (!exist) {
			System.out.print("Invalid Course ID.");
			return;
		}
		if (a.getMaxStudent() == a.getCurrentStudent()) {
			System.out.println("The Course is already FULL.");
			return;
		}
		//the current student user can only register a course for him/herself
		//if the name entered is not the current student user's name, end the method
		System.out.print("Enter your Firstname:");
		String fn = input.next();
		System.out.print("Enter your Lastname:");
		String ln = input.next();
		String name = fn+" "+ln;
		if (!this.getFirstName().equals(fn) || !this.getLastName().equals(ln)) {
			System.out.println("The name doesn't match our record.");
			return;
		}
		for (Student s : studentList) {
			if (s.getFirstName().equals(fn) && s.getLastName().equals(ln) && s.getUsername().equals(current.getUsername())) {
				if (!s.getRegisteredCourse().contains(a) && !a.getListOfStudentIds().contains(s.getUsername())) {
					
					s.getRegisteredCourse().add(a);
					a.setCurrentStudent(a.getCurrentStudent()+1);
					a.getListOfNames().add(name);
					a.getListOfStudentIds().add(s.getUsername());
	
					Course.serializeCourseList();// IMPORTANT
					Student.saveStudentList();
					System.out.println("You have successfully registered "+name+
							" into "+a.getCourseName()+" section "+a.getSectionNumber()+".");
					return;
				}
				System.out.println("You have already registered for this class.");
				return;
			}
		}			
		System.out.println("Unable to find Name in our record.");	
	}
	
	@Override
	public void withdraw() {
		/**student will be asked to enter her/his name and 
		 * the course name, then the name of the student 
		 * will be taken off from the given course’s list*/
		System.out.print("Enter the Course ID:");
		String id = input.next();
		System.out.print("Enter the section number:");
		int section = input.nextInt();
		Course a = null;
		boolean exist = false;
		for (Course c :Course.getCourses()) {
			if (c.getCourseId().equals(id)) {
				if (c.getSectionNumber() == section) {
					a = c;
					exist = true;
					break;
				}else {
					System.out.print("Section not found.");
					return;
				}
			}
		}
		if (!exist) {
			System.out.print("Invalid Course ID.");
			return;
		}
		if (a.getListOfNames() == null) {
			System.out.println("The Course is empty. Invalid action.");
			return;
		}
		
		System.out.print("Enter your Firstname:");
		String fn = input.next();
		System.out.print("Enter your Lastname:");
		String ln = input.next();
		String name = fn+" "+ln;
		if (!this.getFirstName().equals(fn) || !this.getLastName().equals(ln)) {
			System.out.println("The name doesn't match our record.");
			return;
		}
		for (Student s : studentList) {
			if (s.getFirstName().equals(fn) && s.getLastName().equals(ln) 
					&& a.getListOfStudentIds().contains(s.getUsername())) {
				if (a.getListOfNames().contains(name)) {
					s.getRegisteredCourse().remove(a);
					a.setCurrentStudent(a.getCurrentStudent()-1);
					a.getListOfNames().remove(name);
					a.getListOfStudentIds().remove(s.getUsername());
					Course.serializeCourseList();// IMPORTANT
					Student.saveStudentList();
					System.out.println("You have successfully removed "+name+
							" from "+a.getCourseName()+" section "+a.getSectionNumber()+".");
					return;
				}
				System.out.println("You haven't registered for this class.");
				return;
			}
		}			
		System.out.println("Unable to find you in our record.");	
	}

	@Override
	public void registeredCourses(Student s) {
		if (s.getRegisteredCourse() != null || s.getRegisteredCourse().size() != 0){
			System.out.println("Following are the courses you've registered:");
			Course.title();
			Course.format(s.getRegisteredCourse());
		}else {System.out.println("You haven't registered in any course.");}
	}
	@Override
	public String toString() {
		return "Student [registeredCourse=" + registeredCourse + ", Username=" + getUsername() + ", Password="
				+ getPassword() + ", FirstName=" + getFirstName() + ", LastName=" + getLastName() + "]";
	}
	
}
