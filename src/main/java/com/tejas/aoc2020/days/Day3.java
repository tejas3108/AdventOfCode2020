package com.tejas.aoc2020.days;

import com.tejas.aoc2020.util.FileUtil;

public class Day3 {

    public static int part1(String fileName, int right, int down) {
        String[] input = FileUtil.readFileContent(fileName);
        int cols = input[0].length();
        int rows = input.length;
        int curRow = 0, curCol = 0, treeCount = 0;
        while(curRow < rows) {
            curCol += right;
            curCol %= cols;
            curRow += down;
            if(curRow >= rows) break;
            if(input[curRow].charAt(curCol) == '#') treeCount++;
        }

        return treeCount;
    }

    public static double part2(String fileName) {
        double multiplied = 1;

        multiplied *= part1(fileName, 1, 1);
        multiplied *= part1(fileName, 3, 1);
        multiplied *= part1(fileName, 5, 1);
        multiplied *= part1(fileName, 7, 1);
        multiplied *= part1(fileName, 1, 2);

        return multiplied;
    }
}
