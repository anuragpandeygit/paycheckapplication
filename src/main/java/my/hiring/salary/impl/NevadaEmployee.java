package my.hiring.salary.impl;

import my.hiring.salary.WeeklyEmployee;

public class NevadaEmployee extends WeeklyEmployee {
	
	private static final double STATE_TAX_PERCENT = 0;


	public NevadaEmployee(String ename, int workHour, double payrate,
			int dependents, String state) {
		super(ename, workHour, payrate, dependents, "NV");
	}
	
	@Override
	public double computeStateTax(double income) {
			return  0 ;
	}


}
