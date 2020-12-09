package com.tejas.aoc2020.days;

import com.tejas.aoc2020.util.FileUtil;

import java.util.*;

public class Day9 {
    private static int PREAMBLE = 25;
    private static Set<Double> windowNumbers = new HashSet<>();
    public static Double part1(String fileName) {
        List<Double> numbers = readFile(fileName);
        int startIndex = 0, endIndex = PREAMBLE-1;
        for(int i=startIndex;i<=endIndex;i++) {
            windowNumbers.add(numbers.get(i));
        }
        while(endIndex < numbers.size()-1) {
            Double curNumber = numbers.get(endIndex + 1);
            boolean validNumber = false;
            for(int i=startIndex;i<=endIndex;i++) {
                if(windowNumbers.contains(curNumber - numbers.get(i))){
                    validNumber = true;
                    break;
                }
            }
            if(!validNumber) return curNumber;
            windowNumbers.remove(numbers.get(startIndex));
            windowNumbers.add(curNumber);
            startIndex++;
            endIndex++;
        }
        return 0D;
    }

    public static Double part2(String fileName) {
        Double invalidNumber = part1(fileName);

        List<Double> numbers = readFile(fileName);
        Double sum = 0D;
        int startIndex = 0, endIndex = 1;
        sum += numbers.get(startIndex);
        while(endIndex < numbers.size()) {
            if(sum.equals(invalidNumber)) break;
            else if(sum < invalidNumber) {
                sum += numbers.get(endIndex);
                endIndex++;
            }
            else {
                sum -= numbers.get(startIndex);
                startIndex++;
            }
        }
        List<Double> contiguousNumbers = (numbers.subList(startIndex, endIndex));
        Collections.sort(contiguousNumbers);

        return contiguousNumbers.get(0) + contiguousNumbers.get(contiguousNumbers.size() - 1);
    }

    private static List<Double> readFile(String fileName) {
        String[] input = FileUtil.readFileContent(fileName);

        List<Double> nums = new ArrayList<>();
        for(String expense:input) {
            nums.add(Double.parseDouble(expense));
        }

        return nums;
    }
}
