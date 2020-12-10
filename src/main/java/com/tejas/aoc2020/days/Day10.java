package com.tejas.aoc2020.days;

import com.tejas.aoc2020.util.FileUtil;

import java.util.*;

public class Day10 {
    public static int part1(String fileName) {
        List<Integer> adapters = readFile(fileName);
        int oneDifference = 0, threeDifference = 0;
        Collections.sort(adapters);
        if(adapters.get(0) == 1) {
            oneDifference++;
        }
        else if(adapters.get(0) == 3) {
            threeDifference++;
        }
        for(int i=1;i<adapters.size();i++) {
            if(adapters.get(i) - adapters.get(i-1) == 1) {
                oneDifference++;
            }
            if(adapters.get(i) - adapters.get(i-1) == 3) {
                threeDifference++;
            }
        }

        return oneDifference * ++threeDifference;
    }

    public static Double part2(String fileName) {
        List<Integer> adapters = readFile(fileName);
        Collections.sort(adapters);
        int maxDiffAllowed = 3;
        Map<Integer, Double> dp = new HashMap<>();
        adapters.add(adapters.get(adapters.size()-1) + 3);
        adapters.add(0, 0);
        dp.put(adapters.get(0), 1D);
        for(int i=1;i<adapters.size();i++){
            Double waysToArrange = 0D;
            for(int j=1;j<=maxDiffAllowed;j++) {
                if(dp.containsKey(adapters.get(i) - j)) {
                    waysToArrange += dp.get(adapters.get(i) - j);
                }
            }
            dp.put(adapters.get(i), waysToArrange);
        }
        return dp.get(adapters.get(adapters.size() - 1));
    }

    private static List<Integer> readFile(String fileName) {
        String[] input = FileUtil.readFileContent(fileName);

        List<Integer> nums = new ArrayList<>();
        for(String expense:input) {
            nums.add(Integer.parseInt(expense));
        }

        return nums;
    }
}
