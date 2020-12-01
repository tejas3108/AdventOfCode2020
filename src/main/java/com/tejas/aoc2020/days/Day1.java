package com.tejas.aoc2020.days;

import com.oracle.tools.packager.IOUtils;
import com.tejas.aoc2020.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Day1 {
    static final int SUM = 2020;

    public static int part1(String fileName) {
        List<Integer> nums = readFile(fileName);
        int start = 0, end = nums.size() - 1;

        while(start < end) {
            if(nums.get(start) + nums.get(end) == SUM) {
                return nums.get(start) * nums.get(end);
            }

            else if (nums.get(start) + nums.get(end) < SUM) {
                start++;
            }

            else end--;
        }

        return 0;
    }

    public static int part2(String fileName) {
        List<Integer> nums = readFile(fileName);

        for(int i=0;i<nums.size()-2;i++) {
            int start = i+1, end = nums.size() - 1;

            while(start < end) {
                if(nums.get(i) + nums.get(start) + nums.get(end) == SUM) {
                    return nums.get(i) * nums.get(start) * nums.get(end);
                }

                else if (nums.get(i) + nums.get(start) + nums.get(end) < SUM) {
                    start++;
                }

                else end--;
            }
        }

        return 0;
    }

    private static List<Integer> readFile(String fileName) {
        String[] input = FileUtil.readFileContent(fileName);

        List<Integer> nums = new ArrayList<>();
        for(String expense:input) {
            nums.add(Integer.parseInt(expense));
        }

        Collections.sort(nums);

        return nums;
    }
}
