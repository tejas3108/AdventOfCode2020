package com.tejas.aoc2020.days;

import com.tejas.aoc2020.util.FileUtil;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Day14 {
    private static final int MASK_LENGTH = 36;
    public static Double part1(String fileName) {
        String[] input = FileUtil.readFileContent(fileName);
        Double sum = 0D;
        Map<String, Double> map = new HashMap<>();
        String mask = "";
        for(String line:input) {
            if(line.startsWith("mask")) {
                mask = line.split(" ")[2];
            }
            else {
                map.put(line.split(" ")[0], applyMask(mask, Integer.parseInt(line.split(" ")[2])));
            }
        }
        for(Double maskedValue:map.values()) {
            sum += maskedValue;
        }
        return sum;
    }

    private static Double applyMask(String mask, int num) {
        String number = Integer.toBinaryString(num);
        number = addLeadingZeros(number);
        char[] numArray = number.toCharArray();
        for(int i=0;i<MASK_LENGTH;i++) {
            if(mask.charAt(i) == '0' || mask.charAt(i) == '1') {
                numArray[i] = mask.charAt(i);
            }
        }
        number = new String(numArray);
       // System.out.println(Double.longBitsToDouble(new BigInteger(number, 2).longValue()));
        return convertBinaryToDouble(number);
    }

    private static Double convertBinaryToDouble(String number) {
        double result = 0D;
        for(int i=0;i<MASK_LENGTH;i++) {
            if(number.charAt(MASK_LENGTH-1-i) == '1') {
                result += Math.pow(2,i);
            }
        }

        return result;
    }

    private static String addLeadingZeros(String number) {
        StringBuffer sb = new StringBuffer("");
        for(int i=1;i<=MASK_LENGTH-number.length();i++) {
            sb.append('0');
        }
        return sb.append(number).toString();
    }
}
