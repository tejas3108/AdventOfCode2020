package com.tejas.aoc2020.days;

import com.tejas.aoc2020.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

public class Day13 {
    public static Double part1(String fileName) {
        String[] input = FileUtil.readFileContent(fileName);

        Double earliestTimeToDepart = Double.parseDouble(input[0]);
        Double earliestBusId = 0D, minimumTimeToWait = Double.MAX_VALUE;
        List<Double> busIds = new ArrayList<>();
        for(String id:input[1].split(",")) {
            if(!id.equals("x")) busIds.add(Double.parseDouble(id));
        }

        for(Double id:busIds) {
            Double curId = id;
            while(curId < earliestTimeToDepart) curId += id;
            if(curId - earliestTimeToDepart < minimumTimeToWait) {
                earliestBusId = id;
                minimumTimeToWait = curId - earliestTimeToDepart;
            }
        }

        return earliestBusId * minimumTimeToWait;
    }
}
