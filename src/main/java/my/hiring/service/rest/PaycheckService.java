package my.hiring.service.rest;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import my.hiring.pojo.data.SalaryInfo;
import my.hiring.salary.EmployeeFactory;
import my.hiring.salary.WeeklyEmployee;

import org.json.JSONArray;
import org.json.JSONObject;



/**
 * REST API that is exposed to generate Weekly Paycheck. Program is reading value from a file called input.txt which is available in system classpath.
 * API gives JSON based output. 
 * 
 * @author anurag
 *
 */
@Path("/PayrollService")
public class PaycheckService {

	// This method is called if XML is request
	@GET
	@Path("/getSalary")
	@Produces(MediaType.APPLICATION_JSON)
	public String sayXMLHello() throws Exception {
		
		/**
		 *  For simplicity reading it from System classpath. But this location can be configured to contextRoot also 
		 *  Ideally we might be having a CSV dump to be read or data stored in persistent or DB
		 */
		
		
		String userPropertyFile = System.getProperty("user.input.file"); 
		System.out.println(userPropertyFile);

		// READING PROCESS  ( currently at once but can be improvised to read in chunks , for producer consumer process) 
		List<String> employeefileRecords = Files.readAllLines(new File(userPropertyFile).toPath(), Charset.defaultCharset() );
		System.out.println(employeefileRecords);
		
		// Process in MULTITHREADED way
		List<JSONObject> processedMap = calculateTaxForEmployee(employeefileRecords);

		//WRITE result as purpose of API, for now just dumping it to JSONArray
		
		JSONArray completeResult = new JSONArray();
		completeResult.put(processedMap);
		
		return completeResult.toString();
	}

	/**
	 * @param recordsFromFile  Reads record read from files in form of List. 
	 * Improvisation possible to read and write concurrently using some sort of blockingqueue.
	 * For simplicity going with reading once and processing parallely and writing once approach  
	 * 
	 * @return The return type could be generic but currently limiting it to JSON since that is what is asked. 
	 */
	public List<JSONObject> calculateTaxForEmployee (List<String> recordsFromFile) { 
		/**
		 *  Need to strict the use of CachePool and could be thought of using fixed one.. 
		 */
		ExecutorService executor = Executors.newCachedThreadPool();

		// Different Data structure can be used here but now going ahead with default one . I want a thread safe List 
		List<JSONObject> resultList = Collections.synchronizedList(new ArrayList<JSONObject>());
		
		/**
		 *  I have thought of this but was thinking we have multiple update to this list so kept it parked
		 *   List<JSONObject> resultList = new CopyOnWriteArrayList<JSONObject>(); 
		 */
		

		for ( String fileToken : recordsFromFile) { 
			WeeklyEmployee employee = EmployeeFactory.getEmployee(fileToken);
			Future<SalaryInfo> future = executor.submit(employee);
			// Now get result from future 
			SalaryInfo salInfo  = null;
			try {
				salInfo = future.get();
				resultList.add(salInfo.getSalaryJSONStub());
			} catch (InterruptedException | ExecutionException e) {
				// Need to do a better way here 
				e.printStackTrace();
			}
		}
		return resultList;
	}
	

}