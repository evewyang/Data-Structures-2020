import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Program2 {
	static int serviceTime;
	public static void main(String[] args) throws Exception {
		process(args[0],args[1]);
	}
	public static void process(String name1, String name2) throws Exception {
		File infile1 = new File(name1);
		File infile2 = new File(name2);
		if (!infile1.exists() || !infile2.exists()) {
			throw new Exception("One or more file(s) cannot be found!"); 
		}else {
			LinkedListQueue<Customer> queue = readCustomerFile(infile1);
			try (BufferedReader queries = new BufferedReader(new FileReader(infile2));
					BufferedWriter write = new BufferedWriter(new FileWriter("output.txt"))){
				String inString;
				Service service = new Service(queue, serviceTime);
				service.waitingTime();
				while((inString = queries.readLine()) != null){
					String[] queryArr = inString.trim().split(" ");
					int customerId = 0;
					if (queryArr.length > 1) {
						customerId = Integer.valueOf(queryArr[1]);
						inString = queryArr[0];
					}
					String line = null;
					switch (inString.trim()) {
						case "NUMBER-OF-CUSTOMERS-SERVED":
							line = "NUMBER-OF-CUSTOMERS-SERVED:"+ service.numberOfCustomerServed();
							break;
						case "LONGEST-BREAK-LENGTH":
							line = "LONGEST-BREAK-LENGTH:"+service.longestBreakLength();
							break;
						case "TOTAL-IDLE-TIME":
							line = "TOTAL-IDLE-TIME:"+service.totalIdleTime();
							break;
						case "MAXIMUM-NUMBER-OF-PEOPLE-IN-QUEUE-AT-ANY-TIME":
							line = "MAXIMUM-NUMBER-OF-PEOPLE-IN-QUEUE-AT-ANY-TIME:"+service.maxQueue();
							break;
						case "WAITING-TIME-OF":
							line = "WAITING-TIME-OF "+ customerId+":"+service.waitingTime(customerId);
							break;
					}
					System.out.println(line);
					write.write(line+"\n");
				}
			}
			
		}
		
	}
	//The first line of the customersfile.txt is the constant service time 
	private static LinkedListQueue<Customer> readCustomerFile(File infile1) throws IOException, FileNotFoundException {
		LinkedListQueue<Customer> queue = new LinkedListQueue<Customer>();
		try (BufferedReader info = new BufferedReader(new FileReader(infile1))){	
			String inString;
			serviceTime = Integer.valueOf(info.readLine());
			DateTimeFormatter format = DateTimeFormatter.ofPattern("H:mm:ss");
			//read lines in group of 3
			while ((inString = info.readLine()) != null 
					&& inString.trim().length() == 0) {
				String nextLine = info.readLine();
				String afterLine = info.readLine();
				int id = Integer.valueOf(nextLine.split(":  ")[1]);
				LocalTime time = LocalTime.parse(afterLine.split(": ")[1].trim(),format);
				//assume people doesn't come very early, such as before 8:00:00 am
				//assume people only come in the afternoon from 12pm~19:59:59pm.
				if (time.getHour() < 8 && time.getHour() > 0) {
					time = time.plusHours(12);
				}
				Customer customer = new Customer(id,time);
				queue.enqueue(customer);
			}
		}
		return queue;
	}
}
