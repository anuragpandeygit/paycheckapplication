package my.hiring.salary;


/**
 * @author anurag
 * 
 * through this contract we can define behaviour of Taxble entity , if new tax which is dependent of state or Country in our case Canada 
 * which is coming up. Programmers can use default method if they want to give default value for non applicable ( like for NEVADA it is 0) 
 *
 */
public interface TaxableEntity {
	
	void computeTax();	
	double computeStateTax(double income);
	

}
