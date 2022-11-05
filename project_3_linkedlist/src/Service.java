import java.time.LocalTime;

public class Service implements IServices{
	LinkedListQueue<Customer> queue;
	int serviceTime;
	long[] timeList;
	int toBeServed;
	long[] idleTime;
	int maxCount;
	int currentCount;
	
	public Service(LinkedListQueue<Customer> queue, int serviceTime){
		this.queue = queue;
		this.serviceTime =serviceTime;
		
	}
	@Override
	public int numberOfCustomerServed() {
		return toBeServed;
	}

	@Override
	public long longestBreakLength() {
		long max = 0;
		for (long i: idleTime) {
			if (i > max) { 
				max = i; 
			}
		}
		return max;
	}

	@Override
	public long totalIdleTime() {
		long total = 0;
		for (long i: idleTime) {
			total += i;
		}
		return total;
	}

	@Override
	public int maxQueue() {
		return maxCount;
	}
	public void waitingTime() {
		toBeServed = queue.size;
		timeList = new long[queue.size];
		idleTime = new long[queue.size];
		maxCount = 0;
		currentCount = 0;
		LocalTime[] endTime = new LocalTime[queue.size];
		//Open time, close time
		LocalTime openTime = LocalTime.of(9, 00, 00);
		LocalTime closeTime = LocalTime.of(17, 00, 00);
		
		//Consider the first customer...
		LocalTime t1 = queue.dequeue().getArrivalTime();
		LocalTime end;
		if (t1.compareTo(openTime)<0) {
			//arrive before 9:00:00
			timeList[0] = openTime.toSecondOfDay() - t1.toSecondOfDay();
			end = openTime.plusSeconds(serviceTime);
			maxCount = 1;
			currentCount = 1;
		}else {
			timeList[0] =  0;
			end = t1.plusSeconds(serviceTime);
		}
		endTime[0] = end;
		//the idle time before the first customer is 0
		idleTime[0] = 0;
		//Consider the rest of the customer
		Customer customer;
		int n = 0;
		while ((customer = queue.dequeue())!=null) {
			LocalTime t = customer.getArrivalTime();
			//The following 2 cases cannot be served:
			//1) person that come after 5pm will be dismissed right away
			if (t.compareTo(closeTime)>0) {
				timeList[++n] = 0;
				idleTime[n]=0;
				endTime[n] = null;
				toBeServed--;
				continue;
			}
			//2) this person came before 5pm but not served before 5pm
			//assume this person wait until the counter closes
			//the waiting time is close time - arrival time(which is before 5pm)
			if (end.compareTo(closeTime) > 0) {
				timeList[++n] = closeTime.toSecondOfDay() - t.toSecondOfDay();
				idleTime[n] = 0;
				endTime[n] = closeTime;
				currentCount ++;
				maxCount = Math.max(currentCount,maxCount);
				toBeServed--;
				continue;
			}
			//The following 2 cases will be served
			//3) end time of the prior does not exceed 5pm
			if (end.compareTo(t) > 0) { 
				timeList[++n] = end.toSecondOfDay()-t.toSecondOfDay(); 
				//idle time is 0 because there's no gap between services  
				idleTime[n] = 0;
				end = end.plusSeconds(serviceTime);
				endTime[n] = end;
				currentCount = 0;
				//when does this person leave the queue?
				for (int i = n - 1; i > 0; i-- ) {
					if (t.compareTo(endTime[i]) < 0) {
						currentCount++;
					}else {
						break;
					}
				}
				maxCount = Math.max(currentCount,maxCount);
			}else {
				timeList[++n] = 0;
				idleTime[n] = t.toSecondOfDay() - end.toSecondOfDay();
				end = t.plusSeconds(serviceTime);
				endTime[n] = end;
				//the longest queue breaks
				currentCount = 0;
			}
			
		}
	}
	@Override
	public long waitingTime(int Id) {
		
		return timeList[Id-1];
	}
}
