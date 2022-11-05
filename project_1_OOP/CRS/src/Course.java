import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList; 

public class Course implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5972215688668256230L;
	private String courseName;
	private String courseId;
	private int maxStudent;
	private int currentStudent;
	private ArrayList<String> listOfNames = new ArrayList<String>();
	private ArrayList<String> listOfStudentIds = new ArrayList<String>();
	private String courseInstructor;
	private int sectionNumber;
	private String location;
	private static ArrayList<Course> courses;
	public static final String COURSE_FILE_NAME= "MyUniversityCourses.ser";
	
	public static void initialize() {
		File infile = new File(COURSE_FILE_NAME);
		if (!infile.exists()) {
			File other = new File("MyUniversityCourses.csv");
			//put auto-closable stream in the try(...)
			try (BufferedReader read = new BufferedReader(new FileReader(other))){	
				String inString;
				read.readLine();
				courses = new ArrayList<Course>();
				while ((inString = read.readLine()) != null) {
					String arr[] = inString.split(",");
					Course c = new Course();
					c.setCourseName(arr[0]);
					c.setCourseId(arr[1]);
					c.setMaxStudent(Integer.parseInt(arr[2]));
					c.setCurrentStudent(Integer.parseInt(arr[3]));
					c.setListOfNames(new ArrayList<String>());
					c.setListOfStudentIds(new ArrayList<String>());
					c.setCourseInstructor(arr[5]);
					c.setSectionNumber(Integer.parseInt(arr[6]));
					c.setLocation(arr[7]);
					courses.add(c);
				}	
			
			} catch (Exception e) {
					e.printStackTrace();
			}
			serializeCourseList();
		}else {
			//Auto-close ensure that the i/o close in any circumstances
			try(FileInputStream fis = new FileInputStream(COURSE_FILE_NAME);
				ObjectInputStream ois = new ObjectInputStream(fis)){
			      courses = (ArrayList<Course>)ois.readObject();
			    }
			 catch(Exception cnfe) {
			       cnfe.printStackTrace();
			     }
				

		}
	}

	public static void serializeCourseList() {
		try(FileOutputStream fos = new FileOutputStream(COURSE_FILE_NAME);
			ObjectOutputStream oos = new ObjectOutputStream(fos)){
			oos.writeObject(courses);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Course> getCourses() {
		return courses;
	}
	public static void setCourses(ArrayList<Course> courses) {
		Course.courses = courses;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public int getMaxStudent() {
		return maxStudent;
	}
	public void setMaxStudent(int maxStudent) {
		this.maxStudent = maxStudent;
	}
	public int getCurrentStudent() {
		return currentStudent;
	}
	public void setCurrentStudent(int currentStudent) {
		this.currentStudent = currentStudent;
	}
	public ArrayList<String> getListOfNames() {
		return listOfNames;
	}
	public void setListOfNames(ArrayList<String> listOfNames) {
		this.listOfNames = listOfNames;
	}
	public String getCourseInstructor() {
		return courseInstructor;
	}
	public void setCourseInstructor(String courseInstructor) {
		this.courseInstructor = courseInstructor;
	}
	public int getSectionNumber() {
		return sectionNumber;
	}
	public void setSectionNumber(int sectionNumber) {
		this.sectionNumber = sectionNumber;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public ArrayList<String> getListOfStudentIds() {
		return listOfStudentIds;
	}

	public void setListOfStudentIds(ArrayList<String> listOfStudentIds) {
		this.listOfStudentIds = listOfStudentIds;
	}

	public static void getAllCourses(){ 
		courses = new ArrayList<Course>();
		String line = null;
		String fileName = "MyUniversityCourses.csv";
		try {
			FileReader infile = new FileReader(fileName);
			BufferedReader read= new BufferedReader(infile);
			read.readLine();
			while((line = read.readLine()) != null) {
				String[] data =line.split(",");
			
				Course c = new Course();
				c.setCourseName(data[1]);
				c.setCourseId(data[1]);
				c.setMaxStudent(Integer.valueOf(data[2]));
				c.setCurrentStudent(Integer.valueOf(data[3]));
				c.setCourseInstructor(data[5]);
				c.setSectionNumber(Integer.valueOf(data[6]));
				c.setLocation(data[7]);
				courses.add(c);
				
			}
			read.close();
		}
		catch(FileNotFoundException ex){
			System.out.println( "Unable to open file '" + fileName + "'");
			//the printStackTrace method will print out an error output stream ("What went wrong?" report);
			
			ex.printStackTrace();
		}

		catch (IOException ex) {
			System.out.println( "Error reading file '" + fileName + "'");
			ex.printStackTrace();
		}
		
	}
	
	@Override
	/**this toString method doesn't display studentList, set as default for Course Objects*/
	public String toString() {
		return String.format("%-50s%-15s%-5s%-10s%-30s%-10s%-30s",courseName,courseId,maxStudent,
				currentStudent,courseInstructor,sectionNumber,location);
		 	
	}
	
	public static void title() {
		System.out.printf("%-50s%-15s%-5s%-10s%-30s%-10s%-30s\n","Course Name","courseId","Max#",
				"Current#","Instructor","Section#","Location");
	}
	
	public static void format(ArrayList<Course> courses) {
		for (Course c : courses) {
			System.out.println(c);//this will call the toString() method
		}
	}
}
