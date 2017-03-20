package my.hiring.salary.impl;

import my.hiring.salary.WeeklyEmployee;

public class HawaiiEmployee extends WeeklyEmployee {
	
	private static final double STATE_TAX_PERCENT = 11;

	public HawaiiEmployee(String ename, int workHour, double payrate,
			int dependents, String state) {
		super(ename, workHour, payrate, dependents, "HI");
	}
	
	@Override
	public double computeStateTax(double income) {
		return income * (STATE_TAX_PERCENT/100);
	}

}
