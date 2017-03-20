package my.hiring.salary;

import java.util.concurrent.Callable;

import my.hiring.pojo.data.SalaryInfo;

/**
 * This is principle area where tax computation is happening. While computing the taxes it has been thought to make it open for modification via Subclass to collect 
 * areawise taxes and behaviour
 * 
 * Suppose a new province or new country is asking for other algorithm to calculate tax. This is possible via inheritance. 
 * 
 * @author anurpandey
 *
 */
public abstract class WeeklyEmployee implements TaxableEntity, Callable<SalaryInfo> {
	
	String ename = null;
	int workHour;
	double payrate;
	int dependents;
	String state;
	
	// Caution - much of below are configurable constants and can be read from a config file.
	// For now defining them as constants 
	
	public static final int STANDARD_WEEKLY_HOURS = 40;
	public static final double OVERTIME_PAY_FACTOR = 1.5;
	public static final double SOCIAL_SEC_TAX_FACTOR = 6;
	public static final double FEDERAL_TAX_FACTOR = 14;
	public static final double UNION_DUES = 10.0;
	public static final double DEPENDENT_COVER_AMOUNT = 35;
	public static final int DEPEDENTS_EXCEED_COVER = 3; 
	
	private SalaryInfo salInfo; 
	
	

	public WeeklyEmployee(String ename, int workHour, double payrate,
			int dependents, String state) {
		super();
		this.ename = ename;
		this.workHour = workHour;
		this.payrate = payrate;
		this.dependents = dependents;
		this.state = state;
	}



	@Override
	public void computeTax() {
		
		salInfo = new SalaryInfo(ename, state);
		
		double salary = 0;

		/**
		 *  Calculate Gross Salary 
		 *  
		 *  Component 1 - noOfHours ( standard = 40 ) * payRate;
		 *  Component 2 (calculate only if applicable )  - overTimeHourse ( total-standard) * 1.5 * payrate
		 *   
		 */

		
		salary = ( workHour > STANDARD_WEEKLY_HOURS ) ? STANDARD_WEEKLY_HOURS * payrate : workHour * payrate;
		salInfo.setWeeklySalary(salary);
		
		double overtime = ( workHour > STANDARD_WEEKLY_HOURS ) ? (workHour - STANDARD_WEEKLY_HOURS) *  OVERTIME_PAY_FACTOR * payrate : 0;
		salInfo.setOvertimepay(overtime);
		
		double grossSalary  = salary  + overtime;
		
		
		/**
		 *  CalCulate all deductions example
		 *  
		 * SocialSecurityTax; 
		 * Federal Tax 
		 * StateTax 
		 * Union Dues 
		 * Tax to cover dependents beyond a threshold 
		 */

		salInfo.setSocSecTax(grossSalary * ( SOCIAL_SEC_TAX_FACTOR / 100));
		salInfo.setFedTax(grossSalary * ( FEDERAL_TAX_FACTOR / 100));
		salInfo.setStateTax(computeStateTax(grossSalary));
		salInfo.setUnionDues(UNION_DUES);
		if ( dependents >= DEPEDENTS_EXCEED_COVER)
			salInfo.setDepedentCover(DEPENDENT_COVER_AMOUNT);
		
		System.out.println(salInfo);
			
	
	}
	
	/* (non-Javadoc)
	 * @see java.util.concurrent.Callable#call()
	 * Simply perform Tax computation and get back... 
	 */
	@Override
	public SalaryInfo call() throws Exception {
		computeTax();
		return salInfo;
	}	

}
