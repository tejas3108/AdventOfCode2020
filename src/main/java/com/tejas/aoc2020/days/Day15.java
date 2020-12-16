package com.tejas.aoc2020.days;

import com.tejas.aoc2020.util.FileUtil;

import java.util.HashMap;
import java.util.Map;

public class Day15 {
    public static class MemoryNumber {
        int number;
        int mostRecentlySpoken;
        int secondRecentlySpoken;

        public MemoryNumber(int number, int mostRecentlySpoken) {
            this.number = number;
            this.mostRecentlySpoken = mostRecentlySpoken;
            this.secondRecentlySpoken = -1;
        }

        public void shiftSpokenTurns(int turn) {
            this.secondRecentlySpoken = this.mostRecentlySpoken;
            this.mostRecentlySpoken = turn;
        }

        public int getAge() {
            return this.mostRecentlySpoken - this.secondRecentlySpoken;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getMostRecentlySpoken() {
            return mostRecentlySpoken;
        }

        public void setMostRecentlySpoken(int mostRecentlySpoken) {
            this.mostRecentlySpoken = mostRecentlySpoken;
        }

        public int getSecondRecentlySpoken() {
            return secondRecentlySpoken;
        }

        public void setSecondRecentlySpoken(int secondRecentlySpoken) {
            this.secondRecentlySpoken = secondRecentlySpoken;
        }
    }

    public static int part1(String fileName) {
        return execute(fileName, 2020);
    }

    public static int part2(String fileName) {
        return execute(fileName, 30000000);
    }

    public static int execute(String fileName, int maxTurns) {
        Map<Integer, MemoryNumber> map = new HashMap<>();
        String[] input = FileUtil.readFileContent(fileName);
        int turn = 0, nextSpokenNumber = -1, lastSpokenNumber = -1;
        for(String number:input[0].split(",")) {
            turn++;
            map.put(Integer.parseInt(number), new MemoryNumber(Integer.parseInt(number), turn));
            lastSpokenNumber = Integer.parseInt(number);
        }
        nextSpokenNumber = 0;
        turn += 2;
        while(turn <= maxTurns) {
            lastSpokenNumber = nextSpokenNumber;
            if(map.containsKey(lastSpokenNumber)) {
                map.get(lastSpokenNumber).shiftSpokenTurns(turn-1);
                nextSpokenNumber = map.get(lastSpokenNumber).getAge();
            }
            else {
                map.put(lastSpokenNumber, new MemoryNumber(lastSpokenNumber, turn-1));
                nextSpokenNumber = 0;
            }
            turn++;
        }
        return nextSpokenNumber;
    }
}
