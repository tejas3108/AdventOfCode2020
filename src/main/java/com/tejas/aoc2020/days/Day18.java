package com.tejas.aoc2020.days;

import com.tejas.aoc2020.util.FileUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Day18 {
    public static Double part1(String fileName) {
        return execute(fileName, true);
    }

    public static Double part2(String fileName) {
        return execute(fileName, false);
    }

    private static Double execute(String fileName, boolean part1) {
        String[] input = FileUtil.readFileContent(fileName);
        Double sum = 0D;
        for(String expr:input) {
            sum += Double.parseDouble(evaluateExpression(expr.trim().replaceAll(" +",""), part1));
        }

        return sum;
    }


    private static String evaluateExpression(String expr, boolean part1) {
        if(expr.indexOf('(') == -1 && expr.indexOf(')') == -1) return part1 ? evaluateSimpleString(expr) : evaluateSimpleString2(expr);
        int openCount=0, openIndex=-1, curIndex=0, closeIndex=-1;
        while(curIndex < expr.length()) {
            char curChar = expr.charAt(curIndex);
            if(curChar == '(') {
                if(openCount == 0) openIndex = curIndex;
                openCount++;
            }
            else if(curChar == ')') {
                openCount--;
                if(openCount == 0) {
                    closeIndex = curIndex;
                    break;
                }
            }
            curIndex++;
        }
        String resolvedString = evaluateExpression(expr.substring(openIndex+1, closeIndex), part1);
        expr = expr.substring(0, openIndex) + resolvedString + expr.substring(closeIndex+1);
        return evaluateExpression(expr, part1);
    }

    private static String evaluateSimpleString2(String expr) {
        Double num1, num2;
        char operator;
        Stack<Double> stack = new Stack<>();
        int i=0, startIndex;
        while(Character.isDigit(expr.charAt(i))) i++;
        num1 = Double.parseDouble(expr.substring(0, i));
        operator = expr.charAt(i++);
        startIndex = i;
        stack.push(num1);
        for(int j=i;j<expr.length();j++) {
            if(!Character.isDigit(expr.charAt(j))) {
                num2 = Double.parseDouble(expr.substring(startIndex, j));
                if(operator == '+') {
                    num2 += stack.pop();
                }
                stack.push(num2);
                operator = expr.charAt(j);
                startIndex = j+1;
            }
        }
        num2 = Double.parseDouble(expr.substring(startIndex));
        if(operator == '+') {
            num2 += stack.pop();
        }
        stack.push(num2);

        num1 = 1D;
        while(!stack.isEmpty()) {
            num1 *= stack.pop();
        }
        return String.format("%.0f", num1);
    }

    private static String evaluateSimpleString(String expr) {
        Double num1, num2;
        char operator;
        int i=0, startIndex;
        while(Character.isDigit(expr.charAt(i))) i++;
        num1 = Double.parseDouble(expr.substring(0, i));
        operator = expr.charAt(i++);
        startIndex = i;
        for(int j=i;j<expr.length();j++) {
            if(!Character.isDigit(expr.charAt(j))) {
                num2 = Double.parseDouble(expr.substring(startIndex, j));
                num1 = evaluate(num1, num2, operator);
                operator = expr.charAt(j);
                startIndex = j+1;
            }
        }
        //handle last number
        num2 = Double.parseDouble(expr.substring(startIndex));
        num1 = evaluate(num1, num2, operator);

        return String.format("%.0f", num1);
    }

    private static Double evaluate(Double num1, Double num2, char operator) {
        switch (operator) {
            case '+': return num1+num2;
            case '*': return num1*num2;
        }
        return -1D;
    }
}
