package my.hiring.salary.impl;

import my.hiring.salary.WeeklyEmployee;

public class MississippiEmployee extends WeeklyEmployee {
	
	private static final double STATE_TAX_PERCENT = 5;

	public MississippiEmployee(String ename, int workHour, double payrate,
			int dependents, String state) {
		super(ename, workHour, payrate, dependents, "MS");
	}

	@Override
	public double computeStateTax(double income) {
		return income * ( STATE_TAX_PERCENT /100) ;
	}



}
