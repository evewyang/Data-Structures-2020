import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2425560995355849042L;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	protected static ArrayList<Student> studentList = new ArrayList<Student>();
	public static final String STUDENT_LIST = "studentList.ser";

	public void quit() {
		System.out.print("Thank you for using the Course Registration System.\nGoodbye!");
		// TODO serialization
		System.exit(0);
	}

	public static ArrayList<Student> initStudentList() {
		File inFile = new File(STUDENT_LIST);
		if (!inFile.exists()) {
			return studentList;
		}
		try {
			FileInputStream fis = new FileInputStream(STUDENT_LIST);
			ObjectInputStream ois = new ObjectInputStream(fis);
			studentList = (ArrayList<Student>) ois.readObject();
			ois.close();
			fis.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
		return studentList;
	}

	public static void saveStudentList() {
		try {
			FileOutputStream fos = new FileOutputStream(STUDENT_LIST);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(studentList);
			oos.close();
			fos.close();
			System.out.println("Serialization completed.");
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	
	public String toString() {
		return "User [username=" + username + ", password=" + password + ", firstName=" + firstName + ", lastName="
				+ lastName + "]";
	}

}
