package com.tejas.aoc2020.days;

import com.tejas.aoc2020.util.FileUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Day5 {
    public static int part1(String fileName) {
        String[] input = FileUtil.readFileContent(fileName);
        int maxId = 0;
        for(String boardingPass:input) {
            maxId = Math.max(maxId, getSeatId(boardingPass));
        }

        return maxId;
    }

    public static int part2(String fileName) {
        String[] input = FileUtil.readFileContent(fileName);
        Set<Integer> allSeatIds = new HashSet<>();
        Set<Integer> computedSeatIds = new HashSet<>();
        Set<Integer> missingSeatIds = new HashSet<>();
        int seatId;
        for(int i=0;i<128*8;i++) {
            allSeatIds.add(i);
        }
        for(String boardingPass:input) {
           computedSeatIds.add(getSeatId(boardingPass));
        }
        missingSeatIds = allSeatIds.stream().filter(val -> !computedSeatIds.contains(val)).collect(Collectors.toSet());
        for(int seat:missingSeatIds) {
            if(computedSeatIds.contains(seat+1) && computedSeatIds.contains(seat-1)) return seat;
        }

        return -1;
    }

    private static int getSeatId(String boardingPass) {
        int row = 0, col = 0, start = 0, end = 127, mid = 0;
        start = 0;
        end = 127;
        mid = 0;
        //get row value
        for(int i=0;i<7;i++) {
            if(i == 6) {
                mid = boardingPass.charAt(i) == 'F' ? start : end;
            }
            else {
                mid = (start + end)/2;
                if(boardingPass.charAt(i) == 'F') {
                    end = mid;
                }
                else {
                    start = mid + 1;
                }
            }
        }
        row = mid;
        start = 0;
        end = 7;
        //get col value
        for(int i=7;i<10;i++) {
            if(i == 9) {
                mid = boardingPass.charAt(i) == 'L' ? start : end;
            }
            else {
                mid = (start + end) / 2;
                if(boardingPass.charAt(i) == 'L'){
                    end = mid;
                }
                else {
                    start = mid + 1;
                }
            }
        }
        col = mid;
        //System.out.println("row = " + row + ", col = " + col);
        return (row * 8) + col;
    }
}
