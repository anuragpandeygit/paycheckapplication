package my.hiring.salary;

import my.hiring.salary.impl.HawaiiEmployee;
import my.hiring.salary.impl.MississippiEmployee;
import my.hiring.salary.impl.NevadaEmployee;

public class EmployeeFactory {
	
	  /**
	   * Object are creatred using a factory pattern and based on state ( province ) it gets realized.  
	   * 
	   * @param tokenList example - John Doe,50,12.34,NV,3 
	   * @return WeeklyEmployee Object 
	 */
	public static WeeklyEmployee getEmployee(String tokenList)
	  {
		
		String[] token = tokenList.split(",");
		//Not adding validation here for array size for now. Better way possible here 
		
		String name = token[0];
		int workHour  = Integer.parseInt(token[1]);
		double payrate  = Double.parseDouble(token[2]);
		String stateCode = token[3];
		int dependents  = Integer.parseInt(token[4]);
		// state code is al token location 4.
		switch (stateCode) { 
			case "MS" :
				return new MississippiEmployee(name,workHour,payrate,dependents,stateCode);
			case "HI" : 
				return new HawaiiEmployee(name,workHour,payrate,dependents,stateCode);
			case "NV":
				return new NevadaEmployee(name,workHour,payrate,dependents,stateCode);
				// skipping default case for now. 
			}
	    return null;
	  }

}
