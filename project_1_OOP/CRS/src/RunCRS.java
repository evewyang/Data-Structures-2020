import java.util.ArrayList;
import java.util.Scanner;

public class RunCRS {
	public static void main(String[] args) {
		Course.initialize();
		//ArrayList<Course> courseList = Course.getCourses();
		ArrayList<Student> studentList = User.initStudentList();
		System.out.println("Welcome to the NYU Course Registration System!");
		Scanner input = new Scanner(System.in);
		String identity;
		while (true) { 
			System.out.println("Are you a student or the admin?");
			System.out.print("Enter number 1---student; 2---admin; 3---to quit the system:");
			identity = input.next();
			
			if ("1".equals(identity)) {
				Student student = new Student(input);
				int i;
				while (true) {
					System.out.print("Enter your username:");
					String u = input.next();
					boolean exist = false;
					for (i = 0; i < studentList.size();i++) { 
						if (u.equals(studentList.get(i).getUsername())) {
							exist = true;
							break;
						}
					}
					if (!exist) {
						System.out.println("Wrong username");
						continue;
					}else {break;} 
				}
				while (true) {
					System.out.print("Enter your password:");
					String p = input.next();
					if (!p.equals(studentList.get(i).getPassword())) {
						System.out.println("Wrong password.");
						continue;
					}else {break;}
				}
				student.setFirstName(studentList.get(i).getFirstName());
				student.setLastName(studentList.get(i).getLastName());
				System.out.println("Welcome,"+student.getFirstName()+" "+student.getLastName()+"!");
				
				String act;
				while (true) {
					student.menu();
					act = input.next();
					if ("1".equals(act)) { student.viewCourses();} 
					else if ("2".equals(act)) { student.viewNotFull();;} 
					else if ("3".equals(act)) { student.registerCourse(studentList.get(i));}	
					else if ("4".equals(act)) { student.withdraw();;}
					else if ("5".equals(act)) { student.registeredCourses(studentList.get(i));;}
					else if ("6".equals(act)) { 
						input.close();
						student.quit();
					}else {
						System.out.println("Invalid action.");
					}
				}
				
			
				
				
			} else if ("2".equals(identity)){
				Admin admin = new Admin(input);
				while (true) {
					System.out.print("Enter the username:");
					String u = input.next();
					if (!u.equals(admin.getUsername())) {
						System.out.println("Wrong username");
						continue;
					} else { break; }
				}
				while (true) {
					System.out.print("Enter the password:");
					String p = input.next();
					if (!p.equals(admin.getPassword())) {
						System.out.println("Wrong password.");
						continue;
					} else { break; } 
				}
				System.out.println("Welcome! Admin.");
				String act; 
				while(true) {
					admin.menu();
					act = input.next();
					if ("1".equals(act)) { admin.create();} 
					else if ("2".equals(act)) { admin.delete();} 
					else if ("3".equals(act)) { admin.edit();}	
					else if ("4".equals(act)) { admin.display();}
					else if ("5".equals(act)) { admin.registerStudent();}
					else if ("6".equals(act)) { admin.viewCourses();}
					else if ("7".equals(act)) { admin.viewFull();}
					else if ("8".equals(act)) { admin.writeFull();}
					else if ("9".equals(act)) { admin.studentInCourse();}
					else if ("10".equals(act)) { admin.courseOfStudent();}
					else if ("11".equals(act)) { admin.sort();}
					else if ("12".equals(act)) { 
						input.close();
						admin.quit();
					}else {
						System.out.println("Invalid action.");
					}
						
				}
					
			} else if ("3".equals(identity)){ 
				Student temp = new Student(input);
				temp.quit();
			}else {
				System.out.println("Invalid identity.");
				continue; 
			}
		
		}
	}
		
		
}
