package my.hiring.pojo.data;

import java.text.NumberFormat;
import java.util.Locale;

import org.json.JSONObject;

/**
 * This is plain old java object , this can be used to cast JSON object directly but I have not done it now. 
 * @author anurag
 *
 */
public class SalaryInfo {
	
	String employeeName;
	String stateCode;
	String noOfDependents;
	// Gross Component
	double weeklySalary;
	double overtimepay;
	// Deductions
	double socSecTax;
	double fedTax;
	double stateTax;
	double unionDues; 
	double depedentCover;	
	// takehome
	double netSalary; 
	
	
	public SalaryInfo() { 
		
	}
	
	public SalaryInfo ( String employeeName, String stateCode) { 
		this.employeeName = employeeName;
		this.stateCode = stateCode;
	}
	
	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public String getNoOfDependents() {
		return noOfDependents;
	}

	public void setNoOfDependents(String noOfDependents) {
		this.noOfDependents = noOfDependents;
	}

	public double getWeeklySalary() {
		return weeklySalary;
	}

	public void setWeeklySalary(double weeklySalary) {
		this.weeklySalary = weeklySalary;
	}

	public double getOvertimepay() {
		return overtimepay;
	}

	public void setOvertimepay(double overtimepay) {
		this.overtimepay = overtimepay;
	}

	public double getSocSecTax() {
		return socSecTax;
	}

	public void setSocSecTax(double socSecTax) {
		this.socSecTax = socSecTax;
	}

	public double getFedTax() {
		return fedTax;
	}

	public void setFedTax(double fedTax) {
		this.fedTax = fedTax;
	}

	public double getStateTax() {
		return stateTax;
	}

	public void setStateTax(double stateTax) {
		this.stateTax = stateTax;
	}

	public double getUnionDues() {
		return unionDues;
	}

	public void setUnionDues(double unionDues) {
		this.unionDues = unionDues;
	}

	public double getDepedentCover() {
		return depedentCover;
	}

	public void setDepedentCover(double depedentCover) {
		this.depedentCover = depedentCover;
	}

	public double getTotalEarning() {
		return (weeklySalary + overtimepay);
	}
	
	public double getTotalDeduction() {
		return  (socSecTax + fedTax + stateTax + unionDues + depedentCover);
	}
	
	public double getNetSalary() {
		return (weeklySalary + overtimepay) - (socSecTax + fedTax + stateTax + unionDues + depedentCover);
	}

	@Override
	public String toString() {
		return "";
	}
	
	public JSONObject getSalaryJSONStub() { 
		JSONObject salaryInfo = new JSONObject();
		NumberFormat nf  = NumberFormat.getCurrencyInstance(Locale.US);
		
		salaryInfo.put("Employee Name", getEmployeeName());
		salaryInfo.put("STATE CODE", getStateCode());
		salaryInfo.put("Weekly Salary", nf.format(getWeeklySalary()));
		salaryInfo.put("Overtime Pay", nf.format(getOvertimepay()));
		salaryInfo.put("Gross Salary", nf.format(getTotalEarning()));
		salaryInfo.put("Social Security Tax", nf.format(getSocSecTax()));
		salaryInfo.put("Federal Tax", nf.format(getFedTax()));
		salaryInfo.put("State Tax", nf.format(getStateTax()));
		salaryInfo.put("Union Dues", nf.format(getUnionDues()));
		salaryInfo.put("Dependent Cover Deduction", nf.format(getDepedentCover()));
		salaryInfo.put("Total Deductions", nf.format(getTotalDeduction()));
		salaryInfo.put("Net Salary", nf.format(getNetSalary()));
		
		return salaryInfo;
	}

}
