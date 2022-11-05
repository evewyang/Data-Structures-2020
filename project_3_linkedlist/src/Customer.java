import java.time.LocalTime;

public class Customer {
	private int idNumber;
	private LocalTime arrivalTime;
	
	public Customer(int id, LocalTime time) {
		this.idNumber = id;
		this.arrivalTime = time;	
	}
	
	public int getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(int idNumber) {
		this.idNumber = idNumber;
	}
	public LocalTime getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(LocalTime arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	
}
