# iDeagen-Java-Test
Java Test for iDeagen - Ardiansyah
25th Nov 2019
Taken times approximately about 2.5 hours

I completed for all following test cases:
1). Calculation with operators.
2). Calculation with brackets.
3). Calculation with nested brackets.

Solution:
1). Maintain the calculation process in 1 class with main method to execute the test case.
2). Class name as "Calculator" with primary method required "double calculate(String sum)" as mentioned in Java Test Instruction. This is recursive method to handle multiple operations.
3). Introduced method "int getIndexOfOperator(String[]sumArray, String operator)" to get index of target operator that have to be calculated. The method used to get index of oprator in sumamry without any brackets inside. Parameters are String array of summary and operator that need to get the index in summary.
4). Introduced "int getIndexOfOpenBracket(String[]sumArray, String operator)", the method to get index of open bracket in summary. It's getting from last index of open bracket in summary to handle if there are nested bracket.
5). Introduced method "int getIndexOfCloseBracket(String[]sumArray, String operator)", the method to get index of close bracket in summary. It's getting from first index of close bracket in summary to handle if there are nested bracket.
6). Introduced method "String doCalculate(String sum, String[] sumArray, String operator1, String operator2)" to do calculation of opertors.
7). Introduced method "String getOperator(String sum, String operator1, String operator2)" to decide which operator have to execute first for the operators has same level, like multiply and divide, add and subtract. For example, among multiply and divide operator, will calculate for operator in left side first.
8). Introduced method "String doCalculateWithBracket(String sum, String[] sumArray)" to handle the summary with brackets and nested bracket before do operator calculation.
9). Created "static void main(String[] args)" method to execute test cases to ensure the logic is working properly.
