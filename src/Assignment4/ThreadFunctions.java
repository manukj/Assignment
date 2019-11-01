package Assignment4;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ThreadFunctions {

	// Initially readData is set to false, as we just began to read the data
	private boolean readData;
	// total Row which is need to stop the Thread
	int totalRow;
	// Items to build an in-memory object
	List<Items> items;
	// to keep track of how many visited Row
	int visitedRow;
	// ResultSet to read data from the database
	private ResultSet resultSet;
	// COnnection to update the tax

	public ThreadFunctions(int totalRow, ResultSet resultSet) {

		this.readData = false;
		this.totalRow = totalRow;
		this.items = new ArrayList<Items>();
		this.visitedRow = -1;
		this.resultSet = resultSet;
	}

	public synchronized void readData() throws Exception {
		while (readData) {
			try {
				System.out.println("read data waiting");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		Items item;
		try {

			resultSet.next();

			// add the data to the list
			item = new Items(resultSet.getString("name"), resultSet.getDouble("price"), resultSet.getInt("quality"),
					resultSet.getString("type"));
			visitedRow++;
			items.add(item);

			System.out.println("read data");
			// the data as be read now its time to notify update tax thread
			readData = true;
			notify();
		} catch (Exception e) {
			throw new Exception("Something went wrong in resultSet");
		}
	}
	
	public synchronized void updateTax() throws Exception
	{
		//if its not read make it wait 
		while(totalRow != visitedRow && !readData)
		{
			try {
				System.out.println("update tax is waiting");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//get the item that is visited
		Items item = items.get(visitedRow);
		item.calculate_tax();
		Double tax = item.getTax();
		//update the tax
		resultSet.updateDouble("tax",tax);
		
		//make readData false so the it can read next data and notify readData thread
		readData = false;
		notify();
	}
	

}
