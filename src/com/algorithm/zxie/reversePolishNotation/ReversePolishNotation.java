package com.algorithm.zxie.reversePolishNotation;

import java.util.Stack;

/**
 * Evaluate the value of an arithmetic expression in Reverse Polish Notation.
 * Valid operators are +, -, *, /. Each operand may be an integer or another expression.
 * 
 * Some examples:
 * ["2", "1", "+", "3", "*"] -> ((2 + 1) * 3) -> 9
 * ["4", "13", "5", "/", "+"] -> (4 + (13 / 5)) -> 6
 */

public class ReversePolishNotation {

	public static void main(String[] args) {
		String[] arr1 = {"2", "1", "+", "3", "*"};
		String[] arr2 = {"4", "13", "5", "/", "+"};
		
		ReversePolishNotation rpn = new ReversePolishNotation();
		System.out.println(rpn.calRPN(arr1));
		System.out.println(rpn.calRPN(arr2));
	}

	public int calRPN(String[] exp){
		Integer opt1 = 0;
		Integer opt2 = 0;
		Stack<Integer> stack = new Stack<>();
		for(int i=0; i<exp.length; i++){
			switch(exp[i]){
			case "+":
				opt2 = stack.pop();
				opt1 = stack.pop();
				stack.push(new Integer(opt1.intValue() + opt2.intValue()));
				break;
			case "-":
				opt2 = stack.pop();
				opt1 = stack.pop();
				stack.push(new Integer(opt1.intValue() - opt2.intValue()));
				break;
			case "*":
				opt2 = stack.pop();
				opt1 = stack.pop();
				stack.push(new Integer(opt1.intValue() * opt2.intValue()));
				break;
			case "/":
				opt2 = stack.pop();
				opt1 = stack.pop();
				stack.push(new Integer(opt1.intValue() / opt2.intValue()));
				break;
			default:
				stack.push(new Integer(Integer.parseInt(exp[i])));
				break;
			}
		}
		return stack.peek().intValue();
	}
}
