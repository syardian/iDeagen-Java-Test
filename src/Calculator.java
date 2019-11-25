/*
 * Date		: 25-NOV-2019
 * Author	: Ardiansyah
 * Purpose	: iDeagen Java Test
 */
public class Calculator {
	//Define Bracket and operator parameters as final static.
	private final static String OPEN_BRACKET 		= "(";
	private final static String CLOSE_BRACKET 		= ")";
	private final static String MULTIPLY_OPERATOR 	= "*";
	private final static String DIVIDE_OPERATOR 	= "/";
	private final static String ADD_OPERATOR 		= "+";
	private final static String SUBTRACT_OPERATOR 	= "-";

	public static double calculate(String sum) {
		double result = 0d;

		String[] sumArray = sum.split(" ");

		//If has more than 3 parameters numbers, its consider has brackets or multiple operators.
		if (sumArray.length > 3) {
			if(sum.contains(OPEN_BRACKET)){
				String resultSum = Calculator.doCalculateWithBracket(sum, sumArray); 
				result =+ Calculator.calculate(resultSum);
			}else if(sum.contains(MULTIPLY_OPERATOR) || sum.contains(DIVIDE_OPERATOR)){
				String resultSum = Calculator.doCalculate(sum, sumArray, MULTIPLY_OPERATOR, DIVIDE_OPERATOR); 
				result =+ Calculator.calculate(resultSum);
			}else if(sum.contains(ADD_OPERATOR) || sum.contains(SUBTRACT_OPERATOR)){
				String resultSum = Calculator.doCalculate(sum, sumArray, ADD_OPERATOR, SUBTRACT_OPERATOR); 
				result =+ Calculator.calculate(resultSum);
			}

			// if only has 3 parameters, it's mean 2 number and 1 operator.
		}else if(sumArray.length == 3){
			if(sum.contains(MULTIPLY_OPERATOR)){
				result =+ (Double.parseDouble(sumArray[0]) *  Double.parseDouble(sumArray[2]));
			}else if(sum.contains(DIVIDE_OPERATOR)){
				result =+ (Double.parseDouble(sumArray[0]) /  Double.parseDouble(sumArray[2]));
			}else if(sum.contains(ADD_OPERATOR)){
				result =+ (Double.parseDouble(sumArray[0]) +  Double.parseDouble(sumArray[2]));
			}else if(sum.contains(SUBTRACT_OPERATOR)) {
				result =+ (Double.parseDouble(sumArray[0]) -  Double.parseDouble(sumArray[2]));
			}
		}
		return result;
	}

	//Method to get index of operator, calculation without brackets
	public static int getIndexOfOperator(String[]sumArray, String operator) {
		int index = 0;
		for(int i=0; i<sumArray.length;i++) {
			if(operator.equals(sumArray[i])) {
				index = i;
				break;
			}
		}
		return index;
	}

	//Method to get index of open bracket, It's getting from last index of open bracket in summary to handle if there are nested bracket.
	public static int getIndexOfOpenBracket(String[]sumArray, String operator) {
		int index = 0;
		for(int i=sumArray.length-1; i>=0;i--) {
			if(operator.equals(sumArray[i])) {
				index = i;
				break;
			}
		}
		return index;
	}

	//Method to get index of close bracket, it's getting from first index of close bracket to handle if there are nested bracket.
	public static int getIndexOfCloseBracket(String[]sumArray, String operator) {
		int index = 0;
		for(int i=0; i<sumArray.length;i++) {
			if(operator.equals(sumArray[i])) {
				index = i;
				break;
			}
		}
		return index;
	}

	//Method to do calculation of operation, 2 numbers and 1 operator
	public static String doCalculate(String sum, String[] sumArray, String operator1, String operator2) {
		int index = Calculator.getIndexOfOperator(sumArray, Calculator.getOperator(sum, operator1, operator2));
		String targetSum = sumArray[index-1] + " " + sumArray[index] + " " + (sumArray[index+1]);
		String resultSum = "";
		if(index == 1) {
			String remainSum = sum.replace(targetSum, "");
			resultSum = String.valueOf(Calculator.calculate(targetSum)).concat(remainSum);
		}else if(index == sumArray.length -1){
			String remainSum = sum.replace(targetSum, "");
			resultSum = remainSum.concat(String.valueOf(Calculator.calculate(targetSum)));
		}else {				
			String remainLeft = sum.substring(0, sum.lastIndexOf(targetSum));
			String remainRight = sum.substring(sum.lastIndexOf(targetSum) + targetSum.length());
			resultSum = remainLeft.concat(String.valueOf(Calculator.calculate(targetSum))).concat(remainRight);
		}
		return resultSum;
	}

	//Method to decide which operator have to execute first for the operators has same level, like multiply and divide, add and subtract.
	//For example, among multiply and divide operator, will calculate for operator in left side first.
	public static String getOperator(String sum, String operator1, String operator2) {
		if(sum.indexOf(operator1) != -1 && sum.indexOf(operator2) != -1) {
			if(sum.indexOf(operator1) < sum.indexOf(operator2)) {
				return operator1;
			}else {
				return operator2;
			}
		}else if(sum.indexOf(operator1) != -1) {
			return operator1;
		}else {
			return operator2;
		}
	}

	///The method to handle brackets and nested brackets of operations.
	public static String doCalculateWithBracket(String sum, String[] sumArray) {
		//find index of multiply		
		int indexOpenBracket = Calculator.getIndexOfOpenBracket(sumArray, OPEN_BRACKET);
		int indexCloseBracket = Calculator.getIndexOfCloseBracket(sumArray, CLOSE_BRACKET);
		String targetBracket = sum.substring(sum.lastIndexOf(OPEN_BRACKET),sum.indexOf(CLOSE_BRACKET)+1);
		String targetSum ="";
		for (int i = indexOpenBracket+1; i<indexCloseBracket ; i++) {
			targetSum = targetSum + sumArray[i];
			if(i < indexCloseBracket-1) {
				targetSum = targetSum + " ";
			}
		}
		String resultSum = "";		
		String[] bracketArray = targetSum.split(" ");
		if(targetSum.contains(OPEN_BRACKET)){
			targetSum =  doCalculateWithBracket(targetSum, bracketArray);
		}
		if(indexOpenBracket == 0 && indexCloseBracket == sumArray.length -1) {
			resultSum = String.valueOf(Calculator.calculate(targetSum));
		}else if(indexOpenBracket == 0) {
			String remainSum = sum.replace(targetBracket, "");
			resultSum = String.valueOf(Calculator.calculate(targetSum)).concat(remainSum);
		}else if(indexCloseBracket == sumArray.length -1){
			String remainSum = sum.replace(targetBracket, "");
			resultSum = remainSum.concat(String.valueOf(Calculator.calculate(targetSum)));
		}else{				
			String remainLeft = sum.substring(0, sum.lastIndexOf(targetBracket));
			String remainRight = sum.substring(sum.lastIndexOf(targetBracket) + targetBracket.length());
			resultSum = remainLeft.concat(String.valueOf(Calculator.calculate(targetSum))).concat(remainRight);
		}		
		return resultSum;
	}

	//Main method to test the test cases
	public static void main(String[] args) {
		//Test Case
		String sum1 = "1 + 1"; 
		Double resultSum1_ = Calculator.calculate(sum1);
		System.out.println("sum1 		: " + sum1); 
		System.out.println("Expected Result	: 2.0");
		System.out.println("Actual Result	: " + String.format ("%.1f", resultSum1_));
		System.out.println("=====================================");

		String sum3 = "1 + 2 + 3";
		Double resultSum3_ = Calculator.calculate(sum3);
		System.out.println("sum3 		: " + sum3);
		System.out.println("Expected Result	: 6.0");
		System.out.println("Actual Result	: " + String.format ("%.1f", resultSum3_));	
		System.out.println("=====================================");

		String sum7 = "1 + 1 * 3";
		Double resultSum7_ = Calculator.calculate(sum7);
		System.out.println("sum7 		: " + sum7);
		System.out.println("Expected Result	: 4.0");
		System.out.println("Actual Result	: " + String.format ("%.1f", resultSum7_));	
		System.out.println("=====================================");

		String sum8 = "( 11.5 + 15.4 ) + 10.1";
		Double resultSum8_ = Calculator.calculate(sum8);
		System.out.println("sum8 		: " + sum8);
		System.out.println("Expected Result	: 37.0");
		System.out.println("Actual Result	: " + String.format ("%.1f", resultSum8_));	
		System.out.println("=====================================");

		String sum9 = "23 - ( 29.3 - 12.5 )";
		Double resultSum9_ = Calculator.calculate(sum9);
		System.out.println("sum9 		: " + sum9);
		System.out.println("Expected Result	: 6.2");
		System.out.println("Actual Result	: " + String.format ("%.1f", resultSum9_));	
		System.out.println("=====================================");

		String sum10 = "10 - ( 2 + 3 * ( 7 - 5 ) )";
		Double resultSum10_ = Calculator.calculate(sum10);
		System.out.println("sum10 		: " + sum10);
		System.out.println("Expected Result	: 2.0");
		System.out.println("Actual Result	: " + String.format ("%.1f", resultSum10_));	
		System.out.println("=====================================");

		String additional1 = "12 * 4 - 2 + 2 + 3 - 2";
		Double resultAdditional1_ = Calculator.calculate(additional1);
		System.out.println("Additional Test1: " + additional1);
		System.out.println("Expected Result	: 49.0");
		System.out.println("Actual Result	: " + String.format ("%.1f", resultAdditional1_));	
		System.out.println("=====================================");

		String additional2 = "12 - ( 2 + 3 * ( 7 - 5 ) ) + 2";
		Double resultAdditional2_ = Calculator.calculate(additional2);
		System.out.println("Additional Test2: " + additional2);
		System.out.println("Expected Result	: 6.0");
		System.out.println("Actual Result	: " + String.format ("%.1f", resultAdditional2_));	
		System.out.println("=====================================");

	}
}
