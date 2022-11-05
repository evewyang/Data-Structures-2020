import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Admin extends User implements UseAdmin,Serializable{
	/**At every line marked //IMPORTANT,usually within a method that changes 
	*the ArrayList<Course>courses, courses is serialized to a file named 
	*"MyUniveristyCourses.csv". The often saving of serialized file ensure that the 
	*former changes will be recored even if there are wrong operations later that 
	*causes exception. 
	*/
	/**
	 * 
	 */
	private static final long serialVersionUID = 2701961495296942431L;
	Scanner input;

	public Admin(Scanner input) {
		this.setUsername("Admin");
		this.setPassword("Admin001");
		this.input = input;
	}
	//print out the menu with choice of actions
	public void menu() {
		System.out.println("                  ---------------------");
		System.out.println("                  ||Course Management||");
		System.out.println("---------------------------------------------------------------");
		System.out.println("1  -  Create a new course");
		System.out.println("2  -  Delete a course");
		System.out.println("3  -  Edit a course");
		System.out.println("4  -  Display information for a given course");
		System.out.println("5  -  Register a student \n");
		
		System.out.println("                        ----------");
		System.out.println("                        ||Report||");
		System.out.println("---------------------------------------------------------------");
		System.out.println("6  -  View all courses");
		System.out.println("7  -  View all courses that are FULL");
		System.out.println("8  -  Write to a file the list of course that are full");
		System.out.println("9  -  View the name(s) of the student(s) registered in a course");
		System.out.println("10 -  Show a student's registed course(s)");
		System.out.println("11 -  Sort the courses (based on number of student registed)");
		System.out.println("12 -  Exit \n");

		System.out.print("Enter a number 1~6 to take action:");
	}
//Course Management 
	
	//create a course, need to input all the fields of information in a Course Object 
	@Override
	public void create() {
		Course c = new Course();
		System.out.print("Enter the Course Name:");
		c.setCourseName(input.next());

		System.out.print("Enter the Course ID:");
		c.setCourseId(input.next());

		System.out.print("Enter the Section Number:");
		c.setSectionNumber(input.nextInt());

		for (Course a : Course.getCourses()) {
			if (a.getCourseId().equals(c.getCourseId()) && (a.getSectionNumber() == c.getSectionNumber())) {
				System.out.println("This course already exists.");
				return;
			}
		}
		System.out.print("Enter the MAXIMUM student to be added:");
		c.setMaxStudent(input.nextInt());

		c.setCurrentStudent(0);
		c.setListOfNames(new ArrayList<String>());
		c.setListOfStudentIds(new ArrayList<String>());
		System.out.print("Enter the Course Instructor:");
		c.setCourseInstructor(input.next());

		System.out.print("Enetr the Course Location:");
		c.setLocation(input.next());

		Course.getCourses().add(c);
		Course.serializeCourseList();// IMPORTANT
		System.out.println("You have successfully add the course " + c.getCourseName());
		Course.title();
		System.out.println(c);

	}
	//delete a course, by providing course ID and section number(unique). Wrong input will terminate the method
	@Override
	public void delete() {
		System.out.println("Please provide the information of the course to be DELETED:");
		System.out.print("Enter the Course ID:");
		String id = input.next();
		System.out.print("Enter the Section Number:");
		int section = input.nextInt();
		for (Course c :Course.getCourses()) {
			if (c.getCourseId().equals(id)
					&& c.getSectionNumber() == section) {
				Course.getCourses().remove(c);
				Course.serializeCourseList();
				System.out.println("You have successfully removed course with ID " + id + ", section" + section + ".");
				return;
			}
		}
		System.out.println("The course with given ID or Section Number is not found.");
	}
	
	//edit a course, first find it by ID and section number, then recursively ask the user to edit a specific field
	//can quit the method if needed
	@Override
	public void edit() {
		// Find the course
		System.out.println("Enter the ID of the Course to be EDITED:");
		String id = input.next();
		System.out.print("Enter the Section Number of the Course to be EDITED:");
		int section = input.nextInt();
		Course c = null;

		boolean exist = false;
		Set<Integer> set = new HashSet<Integer>();
		for (Course a : Course.getCourses()) {
			if (a.getCourseId().equals(id)) {
				if (a.getSectionNumber() == section) {
					exist = true;
					c = a;
				} else {
					set.add(a.getSectionNumber());
				}
			}
		}

		if (!exist) {
			System.out.println("This course doesn't exist.");
			return;
		}
		// Edit, print out a menu with actions
		while (true) {
			Course.title();
			System.out.println(c);
			System.out.println("Which information of this course do you want to edit?");
			System.out.println("1  -  Maximum Student of the course");
			System.out.println("2  -  Current Student of the course");
			System.out.println("3  -  Students in class");
			System.out.println("4  -  Section Number");
			System.out.println("5  -  Location");
			System.out.println("6  -  List of enrolled student's id's");
			System.out.println("7  -  No change");
			System.out.print("Indicate your action with number 1~6:");
			String act = input.next();

			if ("1".equals(act)) {
				System.out.print("Enter the new Max:");
				c.setMaxStudent(input.nextInt());
			} else if ("2".equals(act)) {
				System.out.print("Enter the new Current # of students:");
				c.setCurrentStudent(input.nextInt());
			} else if ("3".equals(act)) {
				System.out.println("Enter the new list of students, seperated by COMMA:");
				ArrayList<String> arr = new ArrayList<String>(Arrays.asList(input.next().split(",")));
				c.setListOfNames(arr);
			} else if ("4".equals(act)) {
				System.out.print("Enter the new Section Number:");
				int sec = input.nextInt();
				if (set.contains(sec)) {
					System.out.println("Section already exist");
					continue;
				} else {
					c.setSectionNumber(sec);
				}
			} else if ("5".equals(act)) {
				System.out.print("Enter the new Location:");
				c.setLocation(input.next());

			} else if ("6".equals(act)) {
				System.out.println("Enter the new list of student usernames, seperated by COMMA:");
				ArrayList<String> arr = new ArrayList<String>(Arrays.asList(input.next().split(",")));
				c.setListOfStudentIds(arr);;
			} else if ("7".equals(act)) {
				break;
			} else {
				System.out.println("Invalid number of action.");
			}
		}

		Course.serializeCourseList();// IMPORTANT
		System.out.println("You have successfully changed the information of" + c.getCourseName());
		System.out.println("The modified course information is shown below:");
		Course.title();
		System.out.println(c);
	}

	@Override
	public void display() {
		System.out.print("Enter the Course ID:");
		String id = input.next();
		System.out.print("Enter the Section Number:");
		int section = input.nextInt();
		for (Course c : Course.getCourses()) {
			if (c.getCourseId().equals(id)
					&& c.getSectionNumber() == section) {
				System.out.println();
				Course.title();
				System.out.println(c);
				System.out.println();
				Course.serializeCourseList();
				return;
			}
		}
		System.out.println("The course with given ID or Section Number is not found.");
	}
	//auto-generates username and password based on the name of the student
	@Override
	public void registerStudent() {
		Student s = new Student(input);
		System.out.print("Enter the First Name of Student:");
		s.setFirstName(input.next());
		System.out.print("Enter the Last Name of Student:");
		s.setLastName(input.next());
		s.setUsername(createUsername(s));
		s.setPassword(s.getUsername() + "001");
		s.setRegisteredCourse(new ArrayList<Course>());
		System.out.println(s);
		studentList.add(s);
		//System.out.println(studentList);
		saveStudentList();
	}
	//Must taken in account the case that multiple students have the same name=>same initials
	//So their username will be unique with the method below: 
	public String createUsername(Student s) {
		String initials = (s.getFirstName().substring(0, 1) + s.getLastName().substring(0, 1)).toLowerCase();
		int max = 0;
		try {
			// decide based on the file content
			for (int i = 0; i < studentList.size(); i++) {
				if (studentList.get(i).getUsername().substring(0, 2).equals(initials)) {
					max = Math.max(Integer.parseInt(studentList.get(i).getUsername().substring(2)), max);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return initials + (max + 1);//if initial the same, number after initial will add 1 to give unique unsername
	}
//Reports
	@Override
	public void viewCourses() {
		//First, use Course.getCourses() to get ArrayList "courses"
		//Each String name in the listOfNames of Courses is in form firstName+" "+lastName
		Course.title();
		for (Course c : Course.getCourses()) {
			System.out.println(c);
		}
		while (true) {
			System.out.println("Do you want to view the STUDENT&ID LIST of a specific course?");
			System.out.print("Enter Y---to view, Anything else on keyboard---to quit");
			String choice = input.next().toLowerCase();
			if ("y".equals(choice)) {
				boolean exist = false;
				System.out.print("Enter the Course ID:");
				String id = input.next();
				
				Set<Integer> secs = new HashSet<Integer>(); 
				for (Course c : Course.getCourses()) {
					if (id.equals(c.getCourseId())) {
						exist = true;
						secs.add(c.getSectionNumber());
						break;
					}
				}
				if (!exist) {
					System.out.println("Invalid Course ID.");
					continue;
				}
				while(true) {
					System.out.print("Enter the section number:");
					int section = input.nextInt();
					if (secs.contains(section)) {
						for (Course c: Course.getCourses()) {
							if (c.getSectionNumber() == section 
									&& c.getCourseId().equals(id)) {
								if (c.getListOfStudentIds() == null || c.getListOfNames() == null 
										|| c.getListOfStudentIds().size() ==0 || c.getListOfNames().size() == 0 ) {
									System.out.println("Section is empty.");
									return;
								}
								System.out.println("Students:"+c.getListOfNames());
								System.out.println("Id's(Usernames):"+c.getListOfStudentIds());
								return;
							}
						}
					}else {System.out.println("Section not found.");}		
				}		
				
			}else {return;}
		}
		
	}

	@Override
	public ArrayList<Course> viewFull() {
		ArrayList<Course> fullCourse = new ArrayList<Course>();
		for (Course a : Course.getCourses()) {
			if (a.getCurrentStudent() == a.getMaxStudent()) {
				fullCourse.add(a);
			}
		}
		if (fullCourse.size() != 0) {
			System.out.println("Below are the courses that are FULL:");
			Course.title();
			for (Course b: fullCourse) {
				System.out.println(b);
			}
		} else{ System.out.println("All courses are not full yet.");}
		return fullCourse;
	}

	@Override
	public void writeFull() {
		ArrayList<Course> c = viewFull();
		String fullCourseFile = "FullCourseFile.csv";
		File inFile = new File (fullCourseFile);
		BufferedWriter write;
		
		try {
			write = new BufferedWriter(new FileWriter(inFile));
			write.append("Course_Name,Course_Id,Course_Section_Number,Course_Instructor,Course_Location\n");
			for (Course d : c) {
				String line = d.getCourseName()+","+d.getCourseId()+","+d.getSectionNumber()+","+
								d.getCourseInstructor()+","+d.getLocation();
				write.append(line);
			}
			write.close();
			System.out.println("The Courses that are FULL have been written in FullCourseFile.csv !");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void studentInCourse() {
		System.out.println("To view the students in a specific class, you must provide the Course ID & Section Number.");
		System.out.print("Enter the Course ID:");
		String id = input.next();
		System.out.print("Enter the Section Number");
		int section = input.nextInt();
		for (Course c: Course.getCourses()) {
			if (c.getCourseId().equals(id)
					&& c.getSectionNumber() == section) {
				if (c.getListOfNames() == null|| c.getListOfNames().size() == 0) {
					System.out.println("This course is empty.");
					return;
				}
				System.out.println("Students:"+c.getListOfNames());
				return;
			}
		}
		System.out.println("The course with given ID or Section Number is not found.");
		
	}

	@Override
	public void courseOfStudent() {
		System.out.println("To view the student's registered course(s), you must provide the First & Last Names.");
		System.out.print("Enter the student's Firstname:");
		String fn = input.next();
		System.out.print("Enter the student's Lastname:");
		String ln = input.next();
		int count = 0;
		Student a = null;
		for (Student s : studentList) {
			if (s.getFirstName().equals(fn) && s.getLastName().equals(ln)) {
				a=s;
				count++;
			}
		}
		if (count == 1) {
			Course.format(a.getRegisteredCourse());
		}else if (count > 1){
			System.out.println("Found multiple students with the name "+fn+" "+ln+".");
			System.out.print("For further varificaiton, please provide the student's UNIQUE USERNAME:");
			String un = input.next();
			for (Student s: studentList) {
				if (s.getFirstName().equals(fn) && s.getLastName().equals(ln) && s.getUsername().equals(un)) {
					if (s.getRegisteredCourse() == null || s.getRegisteredCourse().size() == 0) {
						System.out.println(fn+" "+ln+"haven't registered for any course.");
						return;
					}
					Course.title();
					Course.format(s.getRegisteredCourse());
				}
			}
		}else {System.out.println(fn+" "+ln+" is not found.");}

	}

	@Override
	public void sort() {	
		//Bubble sort, in descending order based on the # of students registered in a course
		Course temp;
		int size = Course.getCourses().size();
		Course[] courses = new Course[size];
		courses = Course.getCourses().toArray(courses);
		for (int i = 0; i < size; i++) {
			for (int j = i+1; j <size; j++) {
				if (courses[j].getCurrentStudent() > courses[i].getCurrentStudent()) {
					temp = courses[i];
					courses[i] = courses[j];
					courses[j] = temp;
				}
			}
		}
		ArrayList<Course> sorted = new ArrayList<Course>(Arrays.asList(courses));
		Course.setCourses(sorted);
		Course.serializeCourseList();// IMPORTANT
		viewCourses();
	}
	
}
